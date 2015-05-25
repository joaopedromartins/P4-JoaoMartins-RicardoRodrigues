package pt.uc.dei.aor.paj;

import java.util.List;


public interface UserEJBRemote {
	 public void populate();
	 
	 public List<User> getUsers();
}