package pt.uc.dei.aor.paj;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    	
    	System.out.println("EJB Logged user: "+loggedUser);
    	if ( loggedUser == null) return false;
    	Playlist newPlaylist = new Playlist(loggedUser , playlistname);
    	System.out.println("EJB New Playlist: "+newPlaylist);
    	em.persist( newPlaylist );
    	
    	return true;
    }
	
	
		

}