package pt.uc.dei.aor.paj;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class MusicEJB {

	@PersistenceContext(name="utilizadores")
	private EntityManager em;
	
	@Inject
	private LoginEJB loginEJB;
	
	
    public MusicEJB() {
        // TODO Auto-generated constructor stub
    }

    
    public List<MusicDTO> getMusicList() {
    	TypedQuery<Music> q = em.createQuery("from Music", Music.class);
    	
    	List<Music> list = q.getResultList();
    	List<MusicDTO> result = new ArrayList<>();
    	for (Music m : list) {
    		result.add(new MusicDTO(m.getTitle(), m.getAuthor(), m.getAlbum(), m.getGenre(), convertMinutes(m.getDuration()), m.getFilename(), m.getYear(), m.getId()));
    	}
    	
    	return result;
    }
    
    
    public List<MusicDTO> findMusicListByTitle(String title) {
    	TypedQuery<Music> q = em.createQuery("from Music m where lower(m.title) like :title", Music.class);
    	q.setParameter("title", "%"+title.toLowerCase()+"%");
    	List<Music> list = q.getResultList();
    	
    	List<MusicDTO> result = new ArrayList<>();
    	for (Music m : list) {
    		result.add(new MusicDTO(m.getTitle(), m.getAuthor(), m.getAlbum(), m.getGenre(), convertMinutes(m.getDuration()), m.getFilename(), m.getYear(), m.getId()));
    	}
    	
    	return result;
    }
    
    public List<MusicDTO> findMusicListByArtist(String artist) {
    	TypedQuery<Music> q = em.createQuery("from Music m where lower(m.author) like :artist", Music.class);
    	q.setParameter("artist", "%"+artist.toLowerCase()+"%");
    	List<Music> list = q.getResultList();
    	
    	List<MusicDTO> result = new ArrayList<>();
    	for (Music m : list) {
    		result.add(new MusicDTO(m.getTitle(), m.getAuthor(), m.getAlbum(), m.getGenre(), convertMinutes(m.getDuration()), m.getFilename(), m.getYear(), m.getId()));
    	}
    	
    	return result;
    }


	public List<MusicDTO> getFilteredMusicList(List<String> activeFilters, List<String> filters, String username) {
		User u = loginEJB.findUserByUsername(username);
		String query = "from Music m where m.user = :user";
		
		int index = 0;
		while (index < activeFilters.size()) {
			if (activeFilters.get(index) != null && activeFilters.get(index).length() > 0) {
				query += " and lower(m."+filters.get(index)+") like :"+filters.get(index);
			}
			index++;
		}
		
		TypedQuery<Music> q = em.createQuery(query, Music.class);
		
		index = 0;
		while (index < activeFilters.size()) {
			if (activeFilters.get(index) != null && activeFilters.get(index).length() > 0) {
				q.setParameter(filters.get(index), "%"+activeFilters.get(index).toLowerCase()+"%");
			}
			index++;
		}
		q.setParameter("user", u);

		List<Music> list = q.getResultList();
    	
    	List<MusicDTO> result = new ArrayList<>();
    	for (Music m : list) {
    		result.add(new MusicDTO(m.getTitle(), m.getAuthor(), m.getAlbum(), m.getGenre(), convertMinutes(m.getDuration()), m.getFilename(), m.getYear(), m.getId()));
    	}
    	
    	return result;
	}
    
    
	private String convertMinutes(int sec) {
		String m = String.valueOf(sec%60);
		if (m.length() == 1) m = "0"+m;
		return sec/60+"m"+m+"s";
	}


	 
}
