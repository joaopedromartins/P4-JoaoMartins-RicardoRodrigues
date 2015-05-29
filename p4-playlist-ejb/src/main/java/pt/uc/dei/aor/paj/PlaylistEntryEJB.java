package pt.uc.dei.aor.paj;

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
		
	public PlaylistEntryEJB() {
		// TODO Auto-generated constructor stub
	}
	
	public List<PlaylistMusicDTO> findMusicsByPlaylist(Playlist playlist) {
		
    	if (playlist==null) return null;
    	
		//TypedQuery<PlaylistMusicDTO> q = em.createQuery("from PlaylistEntry pl where pl.playlist = :playlist order by position", PlaylistMusicDTO.class);
		//q.setParameter("playlist", playlist);
		//List<PlaylistMusicDTO> l = q.getResultList();
    	
		//return l;
		return null;
	}
	

}