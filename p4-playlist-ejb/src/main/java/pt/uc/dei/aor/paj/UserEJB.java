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
public class UserEJB implements UserEJBRemote{

    /**
     * Default constructor. 
     */
	@PersistenceContext(name="Utilizador")
	EntityManager em;
	
	
    public UserEJB() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void populate(){
    	em.persist(new User("Filipa","123","filipapedrosa@gmail.com"));
    	em.persist(new User("Marisa","456","marisaisimoes@gmail.com"));
    }

	@Override
	public List<User> getUsers() {
//		List<String> usernames = new LinkedList<>();
		
		Query q = em.createQuery("from User u");
		List<User> users = q.getResultList();
		
//		for (User u : users) {
//			usernames.add(u.toString());
//		}
		
		return users;
	}
    
    
    
    

}