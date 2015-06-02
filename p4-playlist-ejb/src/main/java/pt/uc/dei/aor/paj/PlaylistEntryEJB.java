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

import pt.uc.dei.aor.paj.Playlist;
import pt.uc.dei.aor.paj.User;

@Stateless
@LocalBean
public class PlaylistEntryEJB {

	@PersistenceContext(name = "Utilizador")
	private EntityManager em;
	
	@Inject
	private LoginEJB loginEJB;
		
	public PlaylistEntryEJB() {
		// TODO Auto-generated constructor stub
	}
	
	
	public List<PlaylistMusicDTO> findMusicsByUsernameAndPlaylistName(String username, String playlistname) {
		//System.out.println("findMusicsByUsernameAndPlaylistName");
		//System.out.println("username: "+username);
		//System.out.println("playlistname"+playlistname);
		if (username.length() <= 2) {
			//System.out.println("username < 2 : "+username);
			return null;
		}
    	
    	if (playlistname.length() <= 2) {
    		//System.out.println("playlistname < 2 : "+playlistname);
    		return null;
    	}
    	
    	//testar se existe o utilizador com esse nome
    	User loggedUser = loginEJB.findUserByUsername(username);
    	//System.out.println("user id : "+loggedUser.getId());
    	//System.out.println("user name : "+loggedUser.getName());
    	//System.out.println("user email : "+loggedUser.getEmail());
    	if ( loggedUser == null) {
    		//System.out.println("loggeduser = null ");
    		return null;
    	}
    	
    	//testar se exite playlist com esse nome
		TypedQuery<Playlist> q = em.createQuery("from Playlist l where l.user = :user and l.title like :title", Playlist.class);
		q.setParameter("user", loggedUser);
		q.setParameter("title", playlistname);
		List<Playlist> l = q.getResultList();
    	if (l.isEmpty()) {
    		//System.out.println("Playlist is empty! ");
    		return null;
    	} else {
    		//System.out.println("Before TypedQuery!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    		//System.out.println("User: "+loggedUser);
    		//System.out.println("playlistname: "+playlistname);
    		TypedQuery<PlaylistEntry> lm = em.createQuery("select ple "
					+ "from PlaylistEntry ple "
					+ "inner join ple.playlist pl " 
					+ "where pl.user = :user"
					+ "	and pl.title like :title", PlaylistEntry.class);
			lm.setParameter("user", loggedUser);
			lm.setParameter("title", playlistname);
			List<PlaylistEntry> result = lm.getResultList();
			
			
			
			List<PlaylistMusicDTO> retorno = new ArrayList<PlaylistMusicDTO>();
			for (PlaylistEntry i: result) {
				//System.out.println("id="+i);
				retorno.add(new PlaylistMusicDTO(i.getMusicTitle(), i.getMusicAuthor() , 
					i.getMusicAlbum(), i.getMusicGenre(), i.getMusicDuration(), i.getMusicYear(), i.getMusicId(), i.getPosition() ) );
			}
    		
			//System.out.println("AFTER TypedQuery!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    		return retorno;
    	}
	}
}