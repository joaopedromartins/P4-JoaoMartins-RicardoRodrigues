package pt.uc.dei.aor.paj;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	
    public SigninEJB() {
        // TODO Auto-generated constructor stub
    }

    
    public boolean register(String username, String password, String confirm, String email) {
    	if (username.contains("@") || !email.contains("@")) return false;
    	
    	if (loginEJB.findUserByUsername(username) != null) return false;
    	
    	if (loginEJB.findUserByEmail(email) != null) return false;
    	
    	if (username.length() <= 2) return false;
    	
    	if (!password.equals(confirm)) return false;
    	
    	String crypt = getCrypt(password);
    	if (crypt != null) {
    		em.persist(new User(username, password, email));
    		return true;
    	}
    	return false;
    }
    
    
    private String getCrypt(String password) {
    	MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			return bytesToHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
    	
	}


	public boolean delete(String username) {
    	em.createQuery("delete from User u where u.name like :username").
		setParameter("username", username).executeUpdate();
    	
    	return true;
    }
    
    public boolean update(String username, String password, String confirm, String email) {
    	if (password.equals(confirm)) {
    		TypedQuery<User> q = em.createQuery("from User u where u.name like :username", User.class).setParameter("username", username);
    		User u = q.getResultList().get(0);

    		u.setEmail(email);
    		u.setName(username);
    		u.setPassword(password);

    		em.persist(u);

    		return true;
    	}
    	return false;
    	
    }
    
    public static String bytesToHex(byte[] bytes) {
    	final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
