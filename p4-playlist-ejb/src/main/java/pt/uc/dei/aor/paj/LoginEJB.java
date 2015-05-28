package pt.uc.dei.aor.paj;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class LoginEJB {
	@PersistenceContext(name = "Utilizador")
	private EntityManager em;

    public LoginEJB() {
        // TODO Auto-generated constructor stub
    }

    
	public User findUserByUsername(String username) {
		TypedQuery<User> q = em.createQuery("from User u where u.name like :username", User.class);
    	q.setParameter("username", username);
    	
    	List<User> users = q.getResultList();
    	if (users.isEmpty()) return null;
    	
    	return users.get(0);
	}
	
	public User findUserByEmail(String email) {
		TypedQuery<User> q = em.createQuery("from User u where u.email like :email", User.class);
    	q.setParameter("email", email);
    	
    	List<User> users = q.getResultList();
    	if (users.isEmpty()) return null;
    	
    	return users.get(0);
	}
	
	public boolean validateUser(String login, String password) {
		String query;
		if (login.contains("@")) query = "from User u where u.email like :login and u.password like :password";
		else query = "from User u where u.name like :login and u.password like :password"; 
		
		Query q = em.createQuery(query);
		q.setParameter("login", login);
		q.setParameter("password", password);
		
		List<User> users = q.getResultList();
		
		if (users.isEmpty()) return false;
		return true;
	}


	
}
