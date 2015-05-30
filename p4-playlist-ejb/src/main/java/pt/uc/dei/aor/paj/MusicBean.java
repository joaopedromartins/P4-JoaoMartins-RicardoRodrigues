package pt.uc.dei.aor.paj;

public class MusicBean {
	private String title;
	private String author;
	private String album;
	private String genre;
	private int duration;
	private String filename;
	private int year;
	private int id;
	
	public MusicBean(String title, String author, String album, String genre,
			int duration, String filename, int year, int id) {
		this.title = title;
		this.author = author;
		this.album = album;
		this.genre = genre;
		this.duration = duration;
		this.filename = filename;
		this.year = year;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getFilename() {
		return System.getProperty("user.dir")+"\\music\\"+filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Override
	public String toString() {
		return title+" (author:"+author+", album:"+album+")";
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
