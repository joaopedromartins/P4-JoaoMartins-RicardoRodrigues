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

	@ManyToOne
	private User user;
	
	public Music() {
		super();
	}

	public Music(String title, User user) {
		this.user = user;
		this.title = title;
	}
   
}
