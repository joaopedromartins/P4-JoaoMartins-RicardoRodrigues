package pt.uc.dei.aor.paj;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


@Named
@RequestScoped
public class Login {
	@EJB
	private UserEJBRemote userEJB;
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
		userEJB.populate();
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