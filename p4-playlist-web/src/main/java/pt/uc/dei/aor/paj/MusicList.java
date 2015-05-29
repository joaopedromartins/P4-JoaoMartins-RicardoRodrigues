package pt.uc.dei.aor.paj;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class MusicList implements Serializable {

	private static final long serialVersionUID = -1464208213574475442L;
	
	@Inject
	private MusicEJB ejb;
	
	private List<MusicBean> musics;
	private String searchField;
	private List<MusicBean> filteredMusicList;
	
	@PostConstruct
	public void init() {
		setMusics(ejb.getMusicList());
	}

	public List<MusicBean> getMusics() {
		return musics;
	}

	public void setMusics(List<MusicBean> musics) {
		this.musics = musics;
	}
	
	public void addMusic(MusicBean m) {
		musics.add(m);
	}
	
	public void searchMusic() {
		if (searchField.trim().equals("")) {
			filteredMusicList.clear();
			filteredMusicList.addAll(musics);
		}
		else {
			filteredMusicList = ejb.findMusicListByArtistOrTitle(searchField, searchField);
		}
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public List<MusicBean> getFilteredMusicList() {
		return filteredMusicList;
	}

	public void setFilteredMusicList(List<MusicBean> filteredMusicList) {
		this.filteredMusicList = filteredMusicList;
	}
	
	
}
