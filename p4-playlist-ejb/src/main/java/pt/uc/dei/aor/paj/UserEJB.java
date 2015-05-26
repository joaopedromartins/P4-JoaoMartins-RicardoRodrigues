package pt.uc.dei.aor.paj;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
public class UserEJB implements UserEJBRemote {

	@PersistenceContext(name = "Utilizador")
	private EntityManager em;

	public UserEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void populate() {
		User filipa = new User("Filipa", "123", "filipapedrosa@gmail.com");
		em.persist(filipa);
		em.persist(new User("Marisa", "456", "marisaisimoes@gmail.com"));
		Playlist playlist = new Playlist(filipa, "playlist1");
		em.persist(playlist);
		Music music = new Music("musicTitle", filipa);
		PlaylistEntry entry = new PlaylistEntry(playlist, music, 1);
		em.persist(music);
		em.persist(entry);
	}

	@Override
	public List<User> getUsers() {
		// List<String> usernames = new LinkedList<>();

		Query q = em.createQuery("from User u");
		List<User> users = q.getResultList();

		// for (User u : users) {
		// usernames.add(u.toString());
		// }

		return users;
	}

}