package pt.uc.dei.aor.paj.test;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.paj.LoginEJB;
import pt.uc.dei.aor.paj.User;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.eq;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class LoginEJBTest {
	// mocked query
		private TypedQuery<User> mockedQuery;
		
		// classe a ser testada
		private LoginEJB ejb;
		
		@Before
		public void init() {
			ejb = new LoginEJB();
			
			// inject do mock EntityManager
			// isto não me parece bem (tive que criar o setter do Entity manager, só para testar)
			ejb.setEm(mock(EntityManager.class));
			
			// criar o mock do query
			mockedQuery = mock(TypedQuery.class);
	    }
		
		
		@Test
		public void findUserByUsername_should_return_null_with_inexistent_user() {
			// isto é normal
			when(mockedQuery.getResultList()).thenReturn(new ArrayList<User>());
			
			// aqui é o que não me parece bem
			// tive que colocar o getter do EntityManager 
			// (no link que me sugeriu tinha algo como, ebj.em.createQuery(args))
			// logo assumi que o em teria de ser public no exemplo deles
	        when(ejb.getEm().createQuery(anyString(), eq(User.class))).thenReturn(mockedQuery);
	        
	        // isto é normal
	        Assert.assertThat(ejb.findUserByUsername("testUser"), is(equalTo(null)));
		}
		
		// outra nota é que a estrutura em camadas faz com que cada um dos métodos seja bastante simples
		// isto é, têm alguma lógica de negócio, seguida de um query
		// logo neste caso particular estou a testar algo demasiado simples que nem precisaria de teste provavelmente
		// (estou a testar simplesmente se a base de dados devolver uma lista vazia, que o método devolve null)
		// estou a assumir que nestes testes unitários não testamos o acesso à base de dados, correcto?
}
