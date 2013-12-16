package ru.testMongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Document
public class Man {

	@Id
	private String id;
	private String name;

	public Man() {
		setName("NoName");
	}

	public Man(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Man{");
		sb.append("Id: ").append(getId()).append(", ").append("Name: ")
				.append(getName()).append("}");
		return sb.toString();
	}
}
