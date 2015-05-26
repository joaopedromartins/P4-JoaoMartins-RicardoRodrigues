package pt.uc.dei.aor.paj;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class Login {
	@EJB
	private UserEJBRemote userEJB;
	
	@Inject
	private SigninEJB signinEJB;
	
	private String username;
	private String password;

	public Login() {
		super();
		
	}
	
	
	public Login(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public void populate(){
		signinEJB.register("joão", "123", "123", "j@j.com");
		signinEJB.register("ricardo", "123", "123", "r@j.com");
	}

	public List<User> getUsers() {
		return userEJB.getUsers();
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}