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
	
	public boolean delMusicfromPlaylistName(String username, String playlistname, int musicid) {
		
		System.out.println("Before TypedQuery delMusicfromPlaylistName !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		//testar se o campo utilizador esta preenchido
		if (username.length() <= 2) {
			return false;
		}
    	
		//testar se o campo nome da playlist esta preenchido
    	if (playlistname.length() <= 2) {
    		return false;
    	}
    	
    	//testar se o id da musica e inteiro positivo
    	if (musicid<1) {
    		return false;
    	}
    	
    	//testar se existe a utilizador com esse nome
    	User loggedUser = loginEJB.findUserByUsername(username);
    	if ( loggedUser == null) {
    		return false;
    	}
    	
    	//testar se existe playlist com esse nome
		TypedQuery<Playlist> q = em.createQuery("from Playlist l where l.user = :user and l.title like :title", Playlist.class);
		q.setParameter("user", loggedUser);
		q.setParameter("title", playlistname);
		List<Playlist> l = q.getResultList();
    	if (l.isEmpty()) {
    		return false;
    	}
    	
    	
    	System.out.println("Music id: "+musicid);
    	//testar se existe uma musica com esse id
		TypedQuery<Music> qm = em.createQuery("from Music m where m.id = :musicid ", Music.class);
		qm.setParameter("musicid", musicid);
		List<Music> musica = qm.getResultList();
    	if (musica.isEmpty()) {
    		System.out.println("Musica invalida: ID="+musicid);
    		return false;
    	}
    	
    	//seleccionar id da playlistentry
    	TypedQuery<Integer> lm = em.createQuery("select ple.id "
				+ "from PlaylistEntry ple "
				+ "inner join ple.playlist pl " 
				+ "where pl.user = :user"
				+ "	and pl.title like :title"
				+ " and ple.music.id = :musicid", Integer.class);
		lm.setParameter("user", loggedUser);
		lm.setParameter("title", playlistname);
		lm.setParameter("musicid", musicid);
		List<Integer> result = lm.getResultList();
		
		//apagar playlistentry com o id seleccionado
    	em.createQuery("delete from PlaylistEntry ple where ple.id = :pleid ").
    	setParameter("pleid", result).executeUpdate();
    	
		System.out.println("teste remove entry----------------------------------------");
		for (int i: result) {
			System.out.println(i);
		}
		
		//.......................................
		
		return true;
	}
}