package ru.testMongoAggregation;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {

	List<Person> findByFirstNameLike(String firstName);

	List<Person> findByLastName(String lastName);

}