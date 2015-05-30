package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.ArrayList;
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

	//Getter associados à variável playlistname
	public String getPlaylistname() {
		return playlistname;
	}
	public void setPlaylistname(String playlistname) {
		this.playlistname = playlistname;
	}

	//Getter associados à lista Listaplaylistnames
	public List<String> getListaplaylistnames() {
		this.listaplaylistnames=playlist.listPlaylist(loggeduser.getUsername());
		return listaplaylistnames;
	}
	
	//Getter associados à lista Listaplaylistmusics
		public List<PlaylistMusicDTO> getListaplaylistmusics() {
			//this.listaplaylistmusics=    ( playlist.listPlaylistmusic(loggeduser.getUsername(), playlistname) );
			
			//teste
			ArrayList<PlaylistMusicDTO> testlist = new ArrayList<PlaylistMusicDTO>();
			
			for (int i=1; i<10; i++) {
				testlist.add( new PlaylistMusicDTO("title"+i, "author"+i, "album"+i,
				"genre"+i, (int)(10.0*Math.random()), (2015-i), i, i) );
			}
			
			return testlist;
		}

	//Getter associados ao DTO selectedmusic
	public PlaylistMusicDTO getSelectedmusic() {
		return selectedmusic;
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
	}
	
	//metodo para seleccionar uma playlist
	public void selectmusic(ActionEvent ae) {
		//atribui o nome da playlist correspondente ao botao da linha seleccionada
		selectedmusic = (PlaylistMusicDTO)ae.getComponent().getAttributes().get("selectedmusicline");
	}
	
}
