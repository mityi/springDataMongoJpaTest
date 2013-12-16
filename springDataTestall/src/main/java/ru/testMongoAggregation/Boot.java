package ru.testMongoAggregation;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import com.mongodb.DBObject;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Boot implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	@Autowired
	MongoOperations mongoOperation;

	public static void main(String[] args) {
		SpringApplication.run(Boot.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		createPerson("Oliver", "Gierke");
		createPerson("Sasha", "Gray");
		createPerson("Sasha", "V");

		List<Person> lastNameResults = repository.findByLastName("Gierke");
		println(lastNameResults.toArray());
		List<Person> firstNameResults = repository.findByFirstNameLike("Sa*");
		println(firstNameResults.toArray());

		Aggregation agg = newAggregation(unwind("lastName"),
				project("firstName", "lastName"),
				match(Criteria.where("firstName").in("Sasha")),
				group("lastName", "firstName").count().as("count-L-"),
				project("count-L-", "lastName", "firstName"),// .and().previousOperation(),
				sort(Direction.ASC, "lastName"), limit(7));
		AggregationResults<DBObject> results = mongoOperation.aggregate(agg,
				"person", DBObject.class);
		List<DBObject> cP = results.getMappedResults();
		println(cP.toArray());

				
	}

	public void createPerson(String firstName, final String lastName) {
		@SuppressWarnings("serial")
		Person person = new Person(firstName, new ArrayList<String>() {
			{
				add(lastName);
				add(lastName);

				add(lastName + "-LAST");

			}
		});
		person = repository.save(person);
	}

	public static void println(Object... objs) {
		System.out.println(Arrays.toString(objs));
	}
}
