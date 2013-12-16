package ru.testJPA;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import ru.testJPA.model.User;
import ru.testJPA.repository.UserRepository;

public class MainJpa {

	public static void main(String[] args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				ConfigJpaTestApp.class);
		UserRepository repository = ctx.getBean(UserRepository.class);

		String inf = "FFFFFF";
		
		EntityManager em = ((EntityManagerFactory) ctx.getBean("entityManagerFactory")).createEntityManager();
		TypedQuery<User> query = em
				.createNamedQuery("User.findAll", User.class);
		List<User> results = query.getResultList();
		for (User u : results) {
			println("** " + u);
		}
		
		User fooBar = new User("FooBar0", inf);
		fooBar = repository.save(fooBar);

		User fooBar2 = new User("FooBar2", inf);
		fooBar2 = repository.save(fooBar2);

		User fooBar1 = new User("FooBar1", inf);
		fooBar1 = repository.save(fooBar1);

		Direction direction = Direction.DESC;
		Order orders = new Order(direction, "name");
		Sort sort = new Sort(orders);
		List<User> resultSort = repository.findAll(sort);
		for (User u : resultSort) {
			println(u);
		}

		List<User> result = repository.findByInf(inf);
		println("findByInf " + result.size());
		for (User u : result) {
			println(u);
		}

		List<User> resultAll = repository.findAll();
		for (User u : resultAll) {
			repository.delete(u);
		}

	}

	public static void println(Object o) {
		System.out.println(o);
	}
}
