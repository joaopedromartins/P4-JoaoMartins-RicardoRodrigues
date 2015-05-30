package pt.uc.dei.aor.paj;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class MusicList implements Serializable {

	private static final long serialVersionUID = -1464208213574475442L;
	
	@Inject
	private MusicEJB ejb;
	
	private List<MusicDTO> musics;
	private String searchField = "";
	private List<MusicDTO> filteredMusicList = new ArrayList<>();
	private String searchType = "all";
	
	@PostConstruct
	public void init() {
		setMusics(ejb.getMusicList());
	}

	public List<MusicDTO> getMusics() {
		return musics;
	}

	public void setMusics(List<MusicDTO> musics) {
		this.musics = musics;
	}
	
	public void addMusic(MusicDTO m) {
		musics.add(m);
	}
	
	public void searchMusic() {
		if (searchField.trim().equals("") || searchType.equals("all")) {
			filteredMusicList.clear();
			filteredMusicList.addAll(musics);
		}
		else {
			if (searchType.equals("title"))
				filteredMusicList = ejb.findMusicListByTitle(searchField);
			else 
				filteredMusicList = ejb.findMusicListByArtist(searchField);
		}
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public List<MusicDTO> getFilteredMusicList() {
		return filteredMusicList;
	}

	public void setFilteredMusicList(List<MusicDTO> filteredMusicList) {
		this.filteredMusicList = filteredMusicList;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	
}
