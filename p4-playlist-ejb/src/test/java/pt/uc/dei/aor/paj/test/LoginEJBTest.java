package pt.uc.dei.aor.paj.test;

import javax.inject.Inject;

import static org.mockito.Mockito.*;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import pt.uc.dei.aor.paj.LoginEJB;

public class LoginEJBTest {
	@Mock
	private EntityManager em;
	
	@InjectMocks
	private LoginEJB ejb;
	
	@Before
	public void init() {
		Query q = null;
		when(em.createQuery(anyString())).thenReturn(q);
	}
	
//	@Test
//	public void findUserByUsername_should_return_null_with_inexistent_user() {
//		assertThat(ejb.findUserByUsername("palerma"), is(equalTo(null)));
//	}
}
