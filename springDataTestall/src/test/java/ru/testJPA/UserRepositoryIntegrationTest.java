package ru.testJPA;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.testJPA.model.User;
import ru.testJPA.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigJpaTestApp.class)
public class UserRepositoryIntegrationTest extends Assert {

	@Autowired
	UserRepository repository;

	@Test
	public void sampleTestCase() {
		String inf = "FFFFFF";
		System.out.println(repository);
		User fooBar = new User("FooBar", inf);
		fooBar = repository.save(fooBar);

		User js = new User("JS", "GC");
		js = repository.save(js);

		List<User> result = repository.findByInf(inf);
		
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getId(), fooBar.getId());
	}
}
