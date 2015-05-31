package pt.uc.dei.aor.paj;

import java.io.File;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;



@Stateless
@LocalBean
public class PlayerEJB {
	
	public PlayerEJB() {
        // TODO Auto-generated constructor stub
    }
    
    public boolean play(String filename) {
    	File f = new File("Touch me_The Doors_L.A. Woman.mp3");
    	
    	return false;
	}

}
