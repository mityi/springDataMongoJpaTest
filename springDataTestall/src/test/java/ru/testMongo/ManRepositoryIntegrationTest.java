package ru.testMongo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.testMongo.conf.ConfigurationMongo;
import ru.testMongo.model.Man;
import ru.testMongo.repository.ManRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigurationMongo.class)
public class ManRepositoryIntegrationTest extends Assert {

	@Autowired
	private ManRepository repository;

	@Autowired
	private MongoOperations mongoOperation;

	@Test
	public void sampleTestCase() {

		repository.deleteAll();

		Man man = new Man("foo");

		mongoOperation.save(man);
		println("1.Save = " + man);

		Query searchNameFooBar = new Query(
				(Criteria.where("name").in(new ArrayList() {
					{
						add("foo");
						add("bar");
					}
				})));

		Man manInDB = mongoOperation.findOne(searchNameFooBar, Man.class);
		println("2.FindOne = " + manInDB);

		mongoOperation.updateFirst(searchNameFooBar,
				Update.update("name", "bar"), Man.class);
		Man manUF = mongoOperation.findOne(searchNameFooBar, Man.class);
		println("3.UpdateFirst = " + manUF);

		mongoOperation.remove(searchNameFooBar, Man.class);
		List<Man> list = mongoOperation.findAll(Man.class);
		println("4.Delete " + list.size());

		assertEquals(list.size(), 0);

		repository.deleteAll();

		// save a couple of customers
		repository.save(new Man("Alice"));
		repository.save(new Man("Bob"));

		// fetch all customers
		println("---Mans found with findAll():");
		for (Man customer : repository.findAll()) {
			println(customer);
		}

		println("---Mans found with findByName(Alice):");
		println(repository.findByName("Alice"));

		println("---Mans found with findByNameNot(Bob):");
		for (Man customer : repository.findByNameNot("Bob")) {
			println(customer);
		}

	}

	public static void println(Object o) {
		System.out.println(o);
	}
}
