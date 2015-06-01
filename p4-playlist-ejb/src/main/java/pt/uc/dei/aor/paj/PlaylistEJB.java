package pt.uc.dei.aor.paj;

import java.util.ArrayList;
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
		System.out.println("findMusicsByUsernameAndPlaylistName");
		System.out.println("username: "+username);
		System.out.println("playlistname"+playlistname);
		if (username.length() <= 2) {
			System.out.println("username < 2 : "+username);
			return null;
		}
    	
    	if (playlistname.length() <= 2) {
    		System.out.println("playlistname < 2 : "+playlistname);
    		return null;
    	}
    	
    	//testar se existe o utilizador com esse nome
    	User loggedUser = loginEJB.findUserByUsername(username);
    	System.out.println("user id : "+loggedUser.getId());
    	System.out.println("user name : "+loggedUser.getName());
    	System.out.println("user email : "+loggedUser.getEmail());
    	if ( loggedUser == null) {
    		System.out.println("loggeduser = null ");
    		return null;
    	}
    	
    	//testar se exite playlist com esse nome
		TypedQuery<Playlist> q = em.createQuery("from Playlist l where l.user = :user and l.title like :title", Playlist.class);
		q.setParameter("user", loggedUser);
		q.setParameter("title", playlistname);
		List<Playlist> l = q.getResultList();
    	if (l.isEmpty()) {
    		System.out.println("Playlist is empty! ");
    		return null;
    	} else {
    		System.out.println("Before TypedQuery!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    		///////...................................
			//TypedQuery<PlaylistEntry> lm = em.createQuery("select ple "
			//		+ "from PlaylistEntry ple "
			//		+ "inner join ple.playlist pl "
			//		+ "where pl.user = :user"
			//		+ "	and pl.title like :title"
			//		+ "	order by ple.position", PlaylistEntry.class);
			//lm.setParameter("user", loggedUser);
			//lm.setParameter("title", playlistname);
			//List<PlaylistEntry> result = lm.getResultList();
			//
			//for (PlaylistEntry i: result) {
			//	System.out.println("id="+i);
			//}
    		System.out.println("User: "+loggedUser);
    		System.out.println("playlistname: "+playlistname);
    		TypedQuery<PlaylistEntry> lm = em.createQuery("select ple "
					+ "from PlaylistEntry ple "
					+ "inner join ple.playlist pl " 
					+ "where pl.user = :user"
					+ "	and pl.title like :title", PlaylistEntry.class);
			lm.setParameter("user", loggedUser);
			lm.setParameter("title", playlistname);
			List<PlaylistEntry> result = lm.getResultList();
			
			
			//ALTERAR OU APAGAR
			//public PlaylistMusicDTO(PlaylistEntry ple) {
			//	this.title = "aa";
			//	this.author = "aa";
			//	this.album = "aa";
			//	this.genre = "aa";
			//	this.duration = 0;
			//	this.year = 1920;
			//	this.id = 100;
			//	this.order = 120;
			//}
			
			List<PlaylistMusicDTO> retorno = new ArrayList<PlaylistMusicDTO>();
			for (PlaylistEntry i: result) {
				System.out.println("id="+i);
				retorno.add(new PlaylistMusicDTO(i.getMusicTitle(), i.getMusicAuthor() , 
					i.getMusicAlbum(), i.getMusicGenre(), i.getMusicDuration(), i.getMusicYear(), i.getMusicId(), i.getPosition() ) );
			}
    		
			System.out.println("AFTER TypedQuery!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    		return retorno;
    	}
	}
	
}