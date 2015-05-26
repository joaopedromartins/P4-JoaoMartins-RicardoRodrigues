package pt.uc.dei.aor.paj;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class PlaylistEntry implements Serializable {

	private static final long serialVersionUID = 6748053767087618078L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private int position;

	@ManyToOne
	private Playlist playlist;
	
	@ManyToOne
	private Music music;
	
	
	public PlaylistEntry() {
		super();
	}


	public PlaylistEntry(Playlist playlist, Music music, int position) {
		this.playlist = playlist;
		this.music = music;
		this.position = position;
	}
   
}
