package pt.uc.dei.aor.paj.test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static org.mockito.Mockito.*;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.paj.LoginEJB;
import pt.uc.dei.aor.paj.User;

@RunWith(MockitoJUnitRunner.class)
public class LoginEJBTest {
	
	private LoginEJB ejb;
	
	@Before
	public void init() {
		ejb = new LoginEJB();
		ejb.setEm(mock(EntityManager.class));
		TypedQuery<User> mockedQuery = mock(TypedQuery.class);
        when(mockedQuery.getResultList()).thenReturn(new ArrayList<User>());
        when(ejb.getEm().createQuery(anyString(), eq(User.class))).thenReturn(mockedQuery);
	}
	
	
	@Test
	public void findUserByUsername_should_return_null_with_inexistent_user() {
		
		assertThat(ejb.findUserByUsername("palerma"), is(equalTo(null)));
	}
}
