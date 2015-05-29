package pt.uc.dei.aor.paj;

public class PlaylistMusicDTO {
	private String title;
	private String author;
	private String album;
	private String genre;
	private int duration;
	private int year;
	private int id;
	private int order;
	
	public PlaylistMusicDTO(String title, String author, String album,
			String genre, int duration, int year, int id,
			int order) {
		super();
		this.title = title;
		this.author = author;
		this.album = album;
		this.genre = genre;
		this.duration = duration;
		this.year = year;
		this.id = id;
		this.order = order;
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
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}
