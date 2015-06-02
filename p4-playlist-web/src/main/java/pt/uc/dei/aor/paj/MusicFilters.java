package pt.uc.dei.aor.paj;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class MusicFilters implements Serializable {
	private static final long serialVersionUID = -7293711775757988503L;

	private List<String> activeFilters;
	private List<String> filters = Arrays.asList(new String[]{"album", "author", "genre", "title", "year"});
	private String filter = "title";
	private String field;
	private List<MusicDTO> listMusics;
	
	@Inject
	private MusicEJB ejb;
	
	public MusicFilters() {
		activeFilters = Arrays.asList(new String[]{null, null, null, null, null});
	}
	
	@PostConstruct
	public void init() {
		listMusics = ejb.getFilteredMusicList(activeFilters, filters);
	}
	
	
	public void addFilter() {
		if (field == null || field.equals("") || field.contains(":")) return;

		for (int i = 0; i < activeFilters.size(); i++) {
			if (filters.get(i).equals(filter)) {
				activeFilters.set(i, field);
				break;
			}
		}
		
		listMusics = ejb.getFilteredMusicList(activeFilters, filters);
		field = "";
	}
	
	
	public void removeFilter(String i) {
		int index = Integer.parseInt(i);
		int counter = 0;
		for (int j = 0; j < activeFilters.size(); j++) {
			if (activeFilters.get(j) != null && activeFilters.get(j).length() > 0) {
				if (counter == index) {
					activeFilters.set(j, null);
					break;
				}
				counter++;
			}
		}
		
		listMusics = ejb.getFilteredMusicList(activeFilters, filters);
		field = "";
	}

	
	public List<String> getFilters() {
		return filters;
	}

	public void setFilters(List<String> filters) {
		this.filters = filters;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public List<String> getActiveFilters() {
		List<String> list = new LinkedList<>();
		for (int i = 0; i < activeFilters.size(); i++) {
			if (activeFilters.get(i) != null) {
				list.add(filters.get(i)+": "+activeFilters.get(i));
			}
		}
		
		return list;
	}

	public List<MusicDTO> getListMusics() {
		return listMusics;
	}

	public void setListMusics(List<MusicDTO> listMusics) {
		this.listMusics = listMusics;
	}
	
	
}
