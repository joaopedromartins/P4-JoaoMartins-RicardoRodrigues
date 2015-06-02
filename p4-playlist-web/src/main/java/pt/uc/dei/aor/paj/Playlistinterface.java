package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named 
@SessionScoped
public class Playlistinterface implements Serializable {
	private static final long serialVersionUID = 2832340869627136905L;

	private String msgerro;
	private String username;
	private String playlistname;
	private String playlistnewname;
	private String operacao;
	private List<String> listaplaylistnames;
	private List<PlaylistMusicDTO> listaplaylistmusics;
	private PlaylistMusicDTO selectedmusic;
	
	@Inject
	private UserSession loggeduser;

	@Inject 
	private PlaylistEJB playlist;
	
	@Inject 
	private PlaylistEntryEJB playlistEntry;
	
	public Playlistinterface() {
		operacao="criar";
		msgerro="";
	}

	
	//Getter associados à variável operacao
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	//Getter associados à variável playlistnewname
	public String getPlaylistnewname() {
		return playlistnewname;
	}
	public void setPlaylistnewname(String playlistnewname) {
		this.playlistnewname = playlistnewname;
	}

	//Getter associados à variável playlistname
	public String getPlaylistname() {
		return playlistname;
	}
	public void setPlaylistname(String playlistname) {
		this.playlistname = playlistname;
	}


	//Getter associados à variável username
	public String getUsername() {
		this.username=loggeduser.getUsername();
		return username;
	}

	//Getter associados à lista Listaplaylistnames
	public List<String> getListaplaylistnames() {
		this.listaplaylistnames=playlist.listPlaylist(loggeduser.getUsername());
		return listaplaylistnames;
	}
	
	//Getter associados à lista Listaplaylistmusics
	public List<PlaylistMusicDTO> getListaplaylistmusics() {
		this.listaplaylistmusics = playlistEntry.findMusicsByUsernameAndPlaylistName( loggeduser.getUsername(), playlistname);
		return listaplaylistmusics;
	}

	//Getter associados ao DTO selectedmusic
	public PlaylistMusicDTO getSelectedmusic() {
		return this.selectedmusic;
	}


	//Getter  associados à variável msgerro
	public String getMsgerro() {
		return msgerro;
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

	//metodo para criar uma playlist
	public String criaplaylist() {
		//System.out.println("Adicionar playlist:" + this.playlistname + "\t do user: "+this.username);
		if (playlist.addPlaylist( loggeduser.getUsername(), playlistname) ) {
			msgerro="Adicionada a playlist: "+playlistname;
			playlistname=null;
		} else {
			msgerro="ERRO ao adicionar a playlist: "+playlistname;
		}
		
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
		if (playlist.delPlaylist( loggeduser.getUsername(), playlistname) ) {
			msgerro="Apagada a playlist: "+playlistname;
			setPlaylistname(null);
		} else {
			msgerro="ERRO ao apagar a playlist: "+playlistname;
		}
		return "/resources/secure/playlist?faces-redirect=true";
	}

	
	//metodo para seleccionar uma playlist
	public void selectplaylist(ActionEvent ae) {
		//atribui o nome da playlist correspondente ao botao da linha seleccionada
		playlistname = (String)ae.getComponent().getAttributes().get("selectedline");
		//apaga musica seleccionada
		selectedmusic=null;
	}
	
	//metodo para seleccionar uma musica da playlist
	public void selectmusic(ActionEvent ae) {
		System.out.println("seleccionar uma musica da playlist:");
		//atribui o nome da playlist correspondente ao botao da linha seleccionada
		int selectedmusicid = (int)(ae.getComponent().getAttributes().get("selectedmusicline"));
		System.out.println("id="+selectedmusicid);
		for (PlaylistMusicDTO i:listaplaylistmusics) {
			if (selectedmusicid==i.getId()) {
				this.selectedmusic=i;
				System.out.println("selectedmusic:\n"+selectedmusic);
			}
		}
	}
	
	
	//metodo para apagar uma musica da playlist
	public void renameplaylist() {
		
	}
	
	//metodo para apagar uma musica da playlist
	public void delmusicfromplaylist(ActionEvent ae) {

	}
	
	//metodo para mover para cima uma musica na playlist
	public void moveupmusicfromplaylist(ActionEvent ae) {

	}
	
	//metodo para mover para cima uma musica na playlist
	public void movedownmusicfromplaylist(ActionEvent ae) {

	}
	
}
