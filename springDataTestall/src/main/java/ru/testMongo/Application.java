package ru.testMongo;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ru.testMongo.model.Man;
import ru.testMongo.repository.ManRepository;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

	@Autowired
	private ManRepository repository;
	
	@Autowired
	MongoOperations mongoOperation;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
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
