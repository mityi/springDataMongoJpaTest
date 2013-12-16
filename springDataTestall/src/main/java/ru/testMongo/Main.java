package ru.testMongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ru.testMongo.conf.ConfigurationMongo;
import ru.testMongo.model.Man;

public class Main {

	public static void main(String[] args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				ConfigurationMongo.class);
		
		MongoOperations mongoOperation = (MongoOperations) ctx
				.getBean("mongoTemplate");
		
//		ManRepository repository = ctx.getBean(ManRepository.class); //Not found //WTF
		
		
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
	}

	public static void println(Object o) {
		System.out.println(o);
	}

}
