package pt.uc.dei.aor.paj;

import java.time.LocalDate;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


@Named
@RequestScoped
public class Uploader {
	@Inject
	private UploadEJB ejb;
	
	@Inject
	private UserSession userSession;
	
	@Inject 
	private MusicList musicList;
	
	@Inject
	private MusicFilters musicFilter;
	
	private Part file;
	
	private String title;
	private String author;
	private String album;
	private String genre;
	private int year = LocalDate.now().getYear();
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Uploader() {}
	
	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}
	
	
	public void upload() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String path = session.getServletContext().getContextPath();
		ejb.upload(file, title, author, album, genre, userSession.getUsername(), year, path);
		musicFilter.updateList();
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	
	public void edit(int id) {
		if (ejb.editMusic(id, title, author, album, genre, year)) {
			for (MusicDTO m : musicList.getMusics()) {
				if (m.getId() == id) {
					m.setAlbum(album);
					m.setAuthor(author);
					m.setTitle(title);
					m.setYear(id);
					m.setGenre(genre);
					break;
				}
			}
		}
	}
	
	public void remove(int id) {
		if (ejb.removeMusic(id)) {
			for (MusicDTO m : musicFilter.getListMusics()) {
				if (m.getId() == id) {
					musicList.getMusics().remove(m);
					break;
				}
			}
			musicFilter.updateList();
		}
	}
	
	public boolean isEditable(int id) {
		return ejb.isEditable(id, userSession.getUsername());
	}
	
}
