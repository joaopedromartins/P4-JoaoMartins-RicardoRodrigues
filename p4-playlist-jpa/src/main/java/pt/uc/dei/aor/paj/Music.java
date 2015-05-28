package pt.uc.dei.aor.paj;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Music implements Serializable {

	private static final long serialVersionUID = -5753633708037205353L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String title;
	@Column
	private String author;
	@Column
	private String album;
	@Column
	private String genre;
	@Column
	private String filename;
	@Column
	private int duration;
	@Column 
	private int year;

	@ManyToOne
	private User user;
	
	public Music() {
		super();
	}

	

	public Music(String title, String author, String album,
			String genre, String filename, int duration, User user, int year) {
		this.title = title;
		this.author = author;
		this.album = album;
		this.genre = genre;
		this.filename = filename;
		this.duration = duration;
		this.user = user;
		this.year = year;
	}



	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getAlbum() {
		return album;
	}

	public String getGenre() {
		return genre;
	}

	public String getFilename() {
		return filename;
	}

	public int getDuration() {
		return duration;
	}

	public User getUser() {
		return user;
	}
   
	public int getYear() { return year; }
	
	public void setYear(int year) { this.year = year; }
}
