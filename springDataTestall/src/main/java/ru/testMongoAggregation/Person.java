package ru.testMongoAggregation;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Person {

	@Id
	private String id;

	private String firstName;
	private List<String> lastName;

	public Person() {
	}

	public Person(String firstName, List<String> lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format("Person{id=%s, firstName='%s', lastName='%s'}",
				id, firstName, Arrays.toString(lastName.toArray()));
	}

}
