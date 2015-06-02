package pt.uc.dei.aor.paj;


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
