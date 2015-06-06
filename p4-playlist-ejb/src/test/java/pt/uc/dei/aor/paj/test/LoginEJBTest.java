package pt.uc.dei.aor.paj.test;

import java.util.ArrayList;



import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import pt.uc.dei.aor.paj.LoginEJB;
import pt.uc.dei.aor.paj.User;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.anyString;
import static org.hamcrest.Matchers.nullValue;


@RunWith(MockitoJUnitRunner.class)
public class LoginEJBTest {
	@Mock
	EntityManager em;
	
	@Mock
	TypedQuery<User> mockedQuery;
		
	@InjectMocks
	LoginEJB ejb;
	
		@Before
		public void init() {
			
	    }
		
		
		@Test
		public void findUserByUsername_should_return_null_with_inexistent_user() {
			final String QUERY = "from User u where u.name like :username";
			
			// mock invocations
	        when(mockedQuery.getResultList()).thenReturn(new ArrayList<User>());

	        when(em.createQuery(QUERY, User.class)).thenReturn(mockedQuery);

	        // invoke EJB
	        User utilizador = ejb.findUserByUsername("testUser");

	        // verify that all methods have been called once
	        verify(mockedQuery).setParameter("username", "testUser");
	        verify(mockedQuery).getResultList();
	        verify(em).createQuery(QUERY, User.class);

	        Assert.assertThat(utilizador, is(nullValue()));
		}
		
		
}
