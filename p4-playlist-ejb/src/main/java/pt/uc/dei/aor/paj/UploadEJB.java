package pt.uc.dei.aor.paj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Part;

@Stateless
public class UploadEJB {
	@PersistenceContext(name="Utilizador")
	private EntityManager em;
	
	@Inject
	private LoginEJB loginEJB;
	
	public MusicBean upload(Part part, String title, String author, String album, String genre, String username, int year) {
		Path folder = Paths.get("/");
		String filename = title+"_"+author+"_"+album+".mp3";
		try {
			String ext = part.getSubmittedFileName().split("\\.")[1];
			if (!ext.equals("mp3")) return null;
			
			InputStream in = part.getInputStream();
			File outFile = new File("C:/Users/ricardo/Documents/"+filename);
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
	        
	        Music m = new Music(title, author, album, genre, filename, 100, loginEJB.findUserByUsername(username), year);
	        em.persist(m);
	        
			return new MusicBean(title, author, album, genre, 100, filename, year); 
		}
		catch (Exception e) {
			return null;
		}
		
	}
}
