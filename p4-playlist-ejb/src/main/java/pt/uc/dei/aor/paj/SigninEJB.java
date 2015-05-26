package pt.uc.dei.aor.paj;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class SigninEJB {
	@PersistenceContext(name = "Utilizador")
	private EntityManager em;

	@Inject
	private LoginEJB loginEJB;
	
    public SigninEJB() {
        // TODO Auto-generated constructor stub
    }

    
    public boolean register(String username, String password, String confirm, String email) {
    	if (username.contains("@") || !email.contains("@")) return false;
    	
    	if (loginEJB.findUserByUsername(username) != null) return false;
    	
    	if (loginEJB.findUserByEmail(email) != null) return false;
    	
    	if (username.length() <= 2) return false;
    	
    	if (!password.equals(confirm)) return false;
    	
    	em.persist(new User(username, password, email));
    	return true;
    }
}
