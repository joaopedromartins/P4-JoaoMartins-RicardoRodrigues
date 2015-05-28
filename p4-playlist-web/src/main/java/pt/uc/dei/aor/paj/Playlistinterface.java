package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
	private String operacao;
	private List<String> listaplaylistnames;
	
	@Inject
	private Usersinterface loggeduser;

	@Inject 
	private PlaylistEJB playlist;

	public Playlistinterface() {
		operacao="criar";
	}

	
	//Getter associados à variável operacao
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

	//Getter associados à variável Listaplaylistnames
	public List<String> getListaplaylistnames() {
		this.listaplaylistnames=playlist.listPlaylist(loggeduser.getUsername());
		return listaplaylistnames;
	}
	public void setListaplaylistnames(List<String> listaplaylistnames) {
		this.listaplaylistnames = listaplaylistnames;
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
		//System.out.println("Adicionar playlist:" + this.playlistname + "\t do user: "+this.username);
		playlist.addPlaylist( loggeduser.getUserLogged(), playlistname);
		
		return "/resources/secure/playlist?faces-redirect=true";
	}
	
	//metodo para editar uma playlist
	public String editaplaylist() {
		System.out.println("Editar playlist:" + this.playlistname + "\t do user: "+this.username);
		//playlist.editPlaylist( loggeduser.getUserLogged(), playlistname);
		
		return "/resources/secure/playlist?faces-redirect=true";
	}
	
	//metodo para apagar uma playlist
	public String apagaplaylist() {
		System.out.println("Apagar playlist:" + this.playlistname + "\t do user: "+this.username);
		if (playlist.delPlaylist( loggeduser.getUserLogged(), playlistname) ) {
			setMsgerro("Apagada a playlist: "+playlistname);
			setPlaylistname(null);
		} else {
			setMsgerro("ERRO ao apagar a playlist: "+playlistname);
		}
		return "/resources/secure/playlist?faces-redirect=true";
	}

	
	//metodo para seleccionar uma playlist
	public String selectplaylist(ActionEvent ae) {
		//atribui o nome da playlist correspondente ao botao linha seleccionada
		this.playlistname = (String)ae.getComponent().getAttributes().get("line");
		
		return "/resources/secure/playlist?faces-redirect=true";
	}
	
	
}
