package pt.uc.dei.aor.paj;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class MusicEJB {

	@PersistenceContext(name="utilizadores")
	private EntityManager em;
	
    public MusicEJB() {
        // TODO Auto-generated constructor stub
    }

    
    public List<MusicBean> getMusicList() {
    	TypedQuery<Music> q = em.createQuery("from Music", Music.class);
    	
    	List<Music> list = q.getResultList();
    	List<MusicBean> result = new ArrayList<>();
    	for (Music m : list) {
    		result.add(new MusicBean(m.getTitle(), m.getAuthor(), m.getAlbum(), m.getGenre(), m.getDuration(), m.getFilename(), m.getYear(), m.getId()));
    	}
    	
    	return result;
    }
    
    
    public MusicBean insertMusic() {
    	User u = new User("user", "email", "password");
    	Music m = new Music("title", "author", "album", "genre", "filename", 300, u, 1967);
    	em.persist(u);
    	em.persist(m);
    	
    	MusicBean mb = new MusicBean(m.getTitle(), m.getAlbum(), m.getAlbum(), m.getGenre(), m.getDuration(), m.getFilename(), m.getYear(), m.getId());
    	return mb;
    }
    
    
    public List<MusicBean> findMusicListByTitle(String title) {
    	TypedQuery<Music> q = em.createQuery("from Music m where lower(m.title) like :title", Music.class);
    	q.setParameter("title", "%"+title.toLowerCase()+"%");
    	List<Music> list = q.getResultList();
    	
    	List<MusicBean> result = new ArrayList<>();
    	for (Music m : list) {
    		result.add(new MusicBean(m.getTitle(), m.getAuthor(), m.getAlbum(), m.getGenre(), m.getDuration(), m.getFilename(), m.getYear(), m.getId()));
    	}
    	
    	return result;
    }
    
    public List<MusicBean> findMusicListByArtist(String artist) {
    	TypedQuery<Music> q = em.createQuery("from Music m where lower(m.author) like :artist", Music.class);
    	q.setParameter("artist", "%"+artist.toLowerCase()+"%");
    	List<Music> list = q.getResultList();
    	
    	List<MusicBean> result = new ArrayList<>();
    	for (Music m : list) {
    		result.add(new MusicBean(m.getTitle(), m.getAuthor(), m.getAlbum(), m.getGenre(), m.getDuration(), m.getFilename(), m.getYear(), m.getId()));
    	}
    	
    	return result;
    }
    
    
}
