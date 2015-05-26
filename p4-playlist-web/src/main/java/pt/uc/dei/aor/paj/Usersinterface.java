package pt.uc.dei.aor.paj;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named 
@SessionScoped
public class Usersinterface implements Serializable {
	private static final long serialVersionUID = -8310185641498834904L;

	private String username;
	private String email;
	private String password;
	private String cpassword;
	private String msgerro;
	private String userLogged;
	private HttpSession session;

	@Inject 
	private SigninEJB signin;
	
	@Inject 
	private LoginEJB login;

	public Usersinterface() {
		this.setUserLogged(null);
		this.session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	//Getter e Setter associados à variável username
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	//Getter e Setter associados à variável username
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	//Getter e Setter associados à variável password
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	//Getter e Setter associados à variável cpassword
	public String getCpassword() {
		return cpassword;
	}
	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	//Getter associados à variável userlogged
	public String getUserLogged() {
		//System.out.println("get user logges="+userLogged);
		return userLogged;
	}
	public void setUserLogged(String userLogged) {
		this.userLogged = userLogged;
		//System.out.println("set user logges="+userLogged);
	}

	//Getter associados à variável msgerro
	public String getMsgerro() {
		return msgerro;
	}
	public void setMsgerro(String msgerro) {
		this.msgerro = msgerro;
	}

	//funcao para efectuar logout
	public String userlogout() {
		this.setUsername(null);
		this.setPassword(null);
		this.setUserLogged(null);
		session.setAttribute("loggedin", false);
		return "/resources/paginas/login";
	}

	
	//funcao para efectuar registo de utilizador
	public String usersignup() {
		signin.register(username, password, cpassword, email);
		//mantem na pagina signup
		return "signup";

	}
	
	//funcao para efectuar login
	public String userlogin() {
		if ( login.validateUser(username, password) ) {
			setUserLogged(username);
			setMsgerro(null);
			session.setAttribute("loggedin", true);
			return "/resources/secure/jukebox";
		} else {
			setUserLogged(null);
			setMsgerro("Erro: Utilizador ou password inválido(s)!");
			return "login";
		}
	}
}

