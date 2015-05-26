package pt.uc.dei.aor.paj;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class LoginEJB
 */
@Stateless
@LocalBean
public class LoginEJB {
	@PersistenceContext(name = "Utilizador")
	private EntityManager em;

    public LoginEJB() {
        // TODO Auto-generated constructor stub
    }

    
	public User findUserByUsername(String username) {
		Query q = em.createQuery("from User u where u.name like :username");
    	q.setParameter("username", username);
    	
    	List<User> users = q.getResultList();
    	if (users.isEmpty()) return null;
    	
    	return users.get(0);
	}
	
	public User findUserByEmail(String email) {
		Query q = em.createQuery("from User u where u.email like :email");
    	q.setParameter("email", email);
    	
    	List<User> users = q.getResultList();
    	if (users.isEmpty()) return null;
    	
    	return users.get(0);
	}
}
