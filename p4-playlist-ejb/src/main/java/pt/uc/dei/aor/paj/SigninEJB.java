package pt.uc.dei.aor.paj;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class SigninEJB {
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
    	if (username.contains("@") || !email.contains("@")) return null;
    	
    	if (loginEJB.findUserByUsername(username) != null) return null;
    	
    	if (loginEJB.findUserByEmail(email) != null) return null;
    	
    	if (username.length() <= 2) return null;
    	
    	if (!password.equals(confirm)) return null;
    	
    	String masked = crypt.encrypt(password, username);
    	
    	if (crypt != null) {
    		User u = new User(username, masked, email);
    		em.persist(u);
    		UserDTO dto = new UserDTO(username, email);
    		return dto;
    	}
    	return null;
    }
    
    
    


	public boolean delete(String username) {
    	em.createQuery("delete from User u where u.name like :username").
		setParameter("username", username).executeUpdate();
    	
    	return true;
    }
    
    public UserDTO update(String username, String password, String confirm, String email) {
    	if (password.equals(confirm)) {
    		TypedQuery<User> q = em.createQuery("from User u where u.name like :username", User.class).setParameter("username", username);
    		User u = q.getResultList().get(0);

    		u.setEmail(email);
    		u.setName(username);
    		u.setPassword(crypt.encrypt(password, username));

    		em.persist(u);
    		UserDTO dto = new UserDTO(username, email);
   
    		return dto;
    	}
    	return null;
    	
    }
    
   
}
