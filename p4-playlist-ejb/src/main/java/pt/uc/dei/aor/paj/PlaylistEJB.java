package pt.uc.dei.aor.paj;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class PlaylistEJB {

	@PersistenceContext(name = "Utilizador")
	private EntityManager em;
	
	@Inject
	private LoginEJB loginEJB;
	
	public PlaylistEJB() {
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean addPlaylist(String username, String playlistname) {
    	if (username.length() <= 2) return false;
    	if (playlistname.length() <= 2) return false;
    	User loggedUser = loginEJB.findUserByUsername(username);
    	if ( loggedUser == null) return false;
    	
    	//testar se ja exite playlist com esse nome
    	TypedQuery<Playlist> q = em.createQuery("from Playlist l where l.user = :user and l.title like :title", Playlist.class);
		q.setParameter("user", loggedUser);
		q.setParameter("title", playlistname);
		List<Playlist> l = q.getResultList();
		
		
    	if (l.isEmpty()) { 
    		//adiciona playlist
        	Playlist newPlaylist = new Playlist(loggedUser , playlistname);
        	em.persist( newPlaylist );
        	return true;
    	} else {
			//for (Playlist i:l) {
			//   System.out.println(i);
			//}
    		return false;
    	}
    	
    }
	
	
	public boolean delPlaylist(String username, String playlistname) {
    	if (username.length() <= 2) return false;
    	if (playlistname.length() <= 2) return false;
    	
    	//testar se existe o utilizador com esse nome
    	User loggedUser = loginEJB.findUserByUsername(username);
    	if ( loggedUser == null) return false;
    	
    	//testar se exite playlist com esse nome
		TypedQuery<Playlist> q = em.createQuery("from Playlist l where l.user = :user and l.title like :title", Playlist.class);
		q.setParameter("user", loggedUser);
		q.setParameter("title", playlistname);
		List<Playlist> l = q.getResultList();
    	if (l.isEmpty()) return false;
    	
		//apagar playlist
    	em.createQuery("delete from Playlist l where l.user = :user and l.title like :title").
		setParameter("user", loggedUser).
    	setParameter("title", playlistname).executeUpdate();
    	
    	return true;
    }
	
	
	public List<String> listPlaylist(String username) {
    	
    	if (username.length() <= 2) return null;
    	User loggedUser = loginEJB.findUserByUsername(username);
    	
    	//System.out.println("EJB list Logged user: "+loggedUser);
    	if ( loggedUser == null) {
    		return null;
    	}
    	
    	TypedQuery<String> q = em.createQuery("select title from Playlist l where l.user = :user", String.class);
		q.setParameter("user", loggedUser);
		List<String> l = q.getResultList();
		
    	return l;
    }	
	
	public Playlist findPlaylistByUsernameAndPlaylistName(String username, String playlistname) {
		if (username.length() <= 2) return null;
    	if (playlistname.length() <= 2) return null;
    	
    	//testar se existe o utilizador com esse nome
    	User loggedUser = loginEJB.findUserByUsername(username);
    	if ( loggedUser == null) return null;
    	
    	//testar se exite playlist com esse nome
		TypedQuery<Playlist> q = em.createQuery("from Playlist l where l.user = :user and l.title like :title", Playlist.class);
		q.setParameter("user", loggedUser);
		q.setParameter("title", playlistname);
		List<Playlist> l = q.getResultList();
    	if (l.isEmpty()) {
    		return null;
    	} else {
    		return l.get(0);
    	}
	}
	
	public List<PlaylistMusicDTO> findMusicsByUsernameAndPlaylistName(String username, String playlistname) {
		if (username.length() <= 2) return null;
    	if (playlistname.length() <= 2) return null;
    	
    	//testar se existe o utilizador com esse nome
    	User loggedUser = loginEJB.findUserByUsername(username);
    	if ( loggedUser == null) return null;
    	
    	//testar se exite playlist com esse nome
		TypedQuery<Playlist> q = em.createQuery("from Playlist l where l.user = :user and l.title like :title", Playlist.class);
		q.setParameter("user", loggedUser);
		q.setParameter("title", playlistname);
		List<Playlist> l = q.getResultList();
    	if (l.isEmpty()) {
    		return null;
    	} else {
    		///////...................................
    		TypedQuery<PlaylistMusicDTO> lm = em.createQuery("select m.title title,"
    				+ "	m.author author, m.album album, m.genre genre, m.duration duration,"
    				+ " m.year year, m.id id, ple.position order"
    				+ "from playlistentry ple, playlist pl, music m"
    				+ "where pl.user like :user"
    				+ "	and pl.title like :title"
    				+ "	and pl.id = ple.playlist_id"
    				+ "	and m.id = ple.music_id"
    				+ "	order by ple.position", PlaylistMusicDTO.class);
    		q.setParameter("user", loggedUser);
    		q.setParameter("title", playlistname);
    		return lm.getResultList();
    	}
	}
	
}