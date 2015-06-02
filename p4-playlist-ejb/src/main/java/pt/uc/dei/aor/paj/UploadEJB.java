package pt.uc.dei.aor.paj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.Part;
import javax.sound.sampled.AudioFileFormat;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

@Stateless
public class UploadEJB {
	@PersistenceContext(name="Utilizador")
	private EntityManager em;
	
	@Inject
	private LoginEJB loginEJB;
	
	public MusicDTO upload(Part part, String title, String author, String album, String genre, String username, int year) {
		String filename = title+"_"+author+"_"+album+".mp3";
		try {
			String ext = part.getSubmittedFileName().split("\\.")[1];
			if (!ext.equals("mp3")) return null;
			
			InputStream in = part.getInputStream();
			File outFile = new File("music/"+filename);
			OutputStream out = new FileOutputStream(outFile);
			
			byte[] buffer = new byte[4096];          
	        int bytesRead = 0;  
	        while(true) {                          
	            bytesRead = in.read(buffer);  
	            if(bytesRead > 0) {  
	            	out.write(buffer, 0, bytesRead);  
	            }else {  
	                break;  
	            }                         
	        }  
	        out.close();  
	        in.close();
	        
	        AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(outFile);
			Map<String, Object> properties = baseFileFormat.properties();

			int duration = (int) ((long)properties.get("duration")/1000000);
			
	        Music m = new Music(title, author, album, genre, filename, duration, loginEJB.findUserByUsername(username), year);
	        em.persist(m);
	        
			return new MusicDTO(title, author, album, genre, convertMinutes(duration), filename, year, m.getId()); 
		}
		catch (Exception e) {
			return null;
		}
		
	}

	public boolean editMusic(int id, String title, String author, String album,
			String genre, int year) {
		TypedQuery<Music> q = em.createQuery("from Music m where m.id = :id", Music.class);
		q.setParameter("id", id);
		Music m = q.getResultList().get(0);
		
		m.setTitle(title);
		m.setAuthor(author);
		m.setAlbum(album);
		m.setGenre(genre);
		m.setYear(year);
		
		em.persist(m);
		return true;
	}

	public boolean removeMusic(int id) {
		Query q = em.createQuery("delete from Music m where m.id = :id");
		q.setParameter("id", id).executeUpdate();
		
		return true;
		
	}

	public boolean isEditable(int id, String userLogged) {
		TypedQuery<User> qU = em.createQuery("from User u where u.name = :username", User.class);
		qU.setParameter("username", userLogged);
		User u = qU.getSingleResult();
		
		TypedQuery<Music> qM = em.createQuery("from Music m where m.id = :id and m.user = :user", Music.class);
		qM.setParameter("id", id).setParameter("user", u);
		
		List<Music> list = qM.getResultList();
		return !list.isEmpty();
	}
	
	private String convertMinutes(int sec) {
		String m = String.valueOf(sec%60);
		if (m.length() == 1) m = "0"+m;
		return sec/60+"m"+m+"s";
	}
}
