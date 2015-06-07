package pt.uc.dei.aor.paj.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.paj.EncryptEJB;
import pt.uc.dei.aor.paj.Music;
import pt.uc.dei.aor.paj.MusicDTO;
import pt.uc.dei.aor.paj.MusicEJB;
import pt.uc.dei.aor.paj.User;


@RunWith(MockitoJUnitRunner.class)
public class MusicEJBTest {
	@Mock
	EntityManager em;

	@Mock
	TypedQuery<Music> mockedQuery;
	
	@InjectMocks
	MusicEJB ejb;

	@Before
	public void init() {
		when(mockedQuery.getResultList()).thenReturn(new ArrayList<Music>());
	}
	
	
	@Test
	public void getMusicList_should_work_correctly() {
		final String QUERY = "from Music";

		// mock invocations
		when(em.createQuery(QUERY, Music.class)).thenReturn(mockedQuery);

		// invoke EJB
		List<MusicDTO> musicList = ejb.getMusicList();

		// verify that all methods have been called once
		verify(mockedQuery).getResultList();
		verify(em).createQuery(QUERY, Music.class);

		Assert.assertThat(musicList.size(), is(equalTo(0)));
	}
	
	
	@Test
	public void findMusicListByTitle_should_work_correctly() {
		final String QUERY = "from Music m where lower(m.title) like :title";
		String title = "musicTitle";
		
		when(em.createQuery(QUERY, Music.class)).thenReturn(mockedQuery);
		
		ejb.findMusicListByTitle(title);
		
		verify(mockedQuery).getResultList();
		verify(mockedQuery).setParameter("title", "%"+title.toLowerCase()+"%");
	}
	
	
	@Test
	public void findMusicListByArtist_should_work_correctly() {
		final String QUERY = "from Music m where lower(m.author) like :artist";
		String artist = "artist";
		
		when(em.createQuery(QUERY, Music.class)).thenReturn(mockedQuery);
		
		ejb.findMusicListByArtist(artist);
		
		verify(mockedQuery).getResultList();
		verify(mockedQuery).setParameter("artist", "%"+artist.toLowerCase()+"%");
	}
	
	
	
}
