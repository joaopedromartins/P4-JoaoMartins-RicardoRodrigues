package pt.uc.dei.aor.paj;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named 
@SessionScoped
public class Playlistinterface implements Serializable {
	private static final long serialVersionUID = 2832340869627136905L;
	
	private String msgerro;
	private String username;
	private String playlistname;
	private String playlistID;
	private String operacao;
	
	@Inject
	private Usersinterface loggeduser;

	@Inject 
	private PlaylistEJB playlist;

	public Playlistinterface() {
		operacao="criar";
	}

	
	//Getter associados à variável userloggedID
	public String getUserLogged() {
		return username;
	}
	public void setUserLogged(String userLoggedID) {
		this.username = userLoggedID;
	}

	//Getter associados à variável playlistID
	public String getPlaylistID() {
		return playlistID;
	}
	public void setPlaylistID(String playlistID) {
		this.playlistID = playlistID;
	}

	//Getter associados à variável playlistname
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	//Getter associados à variável playlistname
	public String getPlaylistname() {
		return playlistname;
	}
	public void setPlaylistname(String playlistname) {
		this.playlistname = playlistname;
	}


	//Getter associados à variável msgerro
	public String getMsgerro() {
		return msgerro;
	}
	public void setMsgerro(String msgerro) {
		this.msgerro = msgerro;
	}

	//metodo que identifica se radiobotton esta activo na posicao criar 
	public boolean typecriar() {
		if (operacao.equals("criar")) {
			return true;
		} else {
			return false;
		}
	}
	
	//metodo que identifica se radiobotton esta activo na posicao editar 
	public boolean typeeditar() {
		if (operacao.equals("editar")) {
			return true;
		} else {
			return false;
		}
	}

	//metodo que identifica se radiobotton esta activo na posicao criar 
	public boolean typeapagar() {
		if (operacao.equals("apagar")) {
			return true;
		} else {
			return false;
		}
	}

	//metodo para criar uma playlist
	public String criaplaylist() {
		System.out.println("Adicionar playlist:" + this.playlistname + "\t do user: "+this.username);
		playlist.addPlaylist( loggeduser.getUserLogged(), playlistname);
		
		return "playlist";
	}

}
