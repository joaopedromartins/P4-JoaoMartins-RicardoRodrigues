package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class Playlist implements Serializable {
	private static final long serialVersionUID = -6391474697846239044L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String title;

	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy="playlist")
	private List<PlaylistEntry> entries;
	
	
	public Playlist() {
		super();
	}


	public Playlist(User user, String title) {
		this.user = user;
		this.title = title;
	}
   
}
