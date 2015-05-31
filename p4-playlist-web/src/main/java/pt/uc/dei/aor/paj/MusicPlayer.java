package pt.uc.dei.aor.paj;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class MusicPlayer {
	@Inject 
	private PlayerEJB player;
	
	public void play(String filename) {
		player.play(filename);
	}
}


//Media media = new Media(resource.toString());
//MediaPlayer mediaPlayer = new MediaPlayer(media);
//mediaPlayer.play();