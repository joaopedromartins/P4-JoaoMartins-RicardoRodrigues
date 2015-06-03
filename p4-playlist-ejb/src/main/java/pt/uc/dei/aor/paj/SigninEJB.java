package pt.uc.dei.aor.paj;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@LocalBean
public class SigninEJB {
	private static final Logger logger = LoggerFactory.getLogger(SigninEJB.class);
	
	@PersistenceContext(name = "Utilizador")
	private EntityManager em;

	@Inject
	private LoginEJB loginEJB;
	
	@Inject
	private EncryptEJB crypt;
	
    public SigninEJB() {
        // TODO Auto-generated constructor stub
    }

    
    public UserDTO register(String username, String password, String confirm, String email) {
    	String masked = crypt.encrypt(password, username);
    	
    	if (crypt != null) {
    		User u = new User(username, masked, email);
    		em.persist(u);
    		UserDTO dto = new UserDTO(username, email);
    		return dto;
    	}
    	return null;
    }
    
    
    


	public boolean delete(String username, String password) {
		String cryptPass = crypt.encrypt(password, username);
		
		TypedQuery<User> qU = em.createQuery("from User u where u.name like :username and u.password like :password", User.class);
		qU.setParameter("username", username);
		qU.setParameter("password", cryptPass);
		List<User> users = qU.getResultList();
		
		User u;
		if (!users.isEmpty()) u = users.get(0);
		else return false;
		
		// music removal
		TypedQuery<Music> qM = em.createQuery("from Music m where m.user = :user", Music.class);
		qM.setParameter("user", u);
		List<Music> listMusic = qM.getResultList();
		
		for (Music m : listMusic) {
			m.setUser(null);
			em.persist(m);
		}
		
		// playlist removal
		TypedQuery<Playlist> qP = em.createQuery("from Playlist p where p.user = :user", Playlist.class);
		qP.setParameter("user", u);
		List<Playlist> playlists = qP.getResultList();
		
		for (Playlist p : playlists) {
			// delete TODO
		}
		
		em.remove(u);
    	
    	return true;
    }
    
    
   public UserDTO updateUsername(String oldUsername, String username, String password) {
	   String cryptPass = crypt.encrypt(password, oldUsername);
	   TypedQuery<User> q = em.createQuery("from User u where u.name like :username and u.password like :password", User.class)
			   .setParameter("username", oldUsername).setParameter("password", cryptPass);
	   
	   List<User> users = q.getResultList();
	   if (users.isEmpty()) return null;
	   
	   User u = users.get(0);
	   cryptPass = crypt.encrypt(password, username);
	   u.setName(username);
	   u.setPassword(cryptPass);
	   em.merge(u);
	   
	   UserDTO dto = new UserDTO(username, u.getEmail());
	   
	   return dto;
   }
   
   public UserDTO updateEmail(String oldUsername, String email, String password) {
	   String cryptPass = crypt.encrypt(password, oldUsername);
	   TypedQuery<User> q = em.createQuery("from User u where u.name like :username and u.password like :password", User.class)
			   .setParameter("username", oldUsername).setParameter("password", cryptPass);
	   
	   List<User> users = q.getResultList();
	   if (users.isEmpty()) return null;
	   
	   User u = users.get(0);
	   u.setEmail(email);
	   em.merge(u);
	   
	   UserDTO dto = new UserDTO(oldUsername, email);
	   
	   return dto;
   }


   public void updatePassword(String username, String oldPassword,
		   String password) {
	   String cryptPass = crypt.encrypt(oldPassword, username);
	   TypedQuery<User> q = em.createQuery("from User u where u.name like :username and u.password like :password", User.class)
			   .setParameter("username", username).setParameter("password", cryptPass);
	   
	   List<User> users = q.getResultList();
	   if (users.isEmpty()) return;
	   
	   User u = users.get(0);
	   cryptPass = crypt.encrypt(password, username);
	   u.setPassword(cryptPass);
	   em.merge(u);
	   
   }
}
