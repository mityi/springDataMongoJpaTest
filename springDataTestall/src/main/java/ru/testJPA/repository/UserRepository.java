package ru.testJPA.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.testJPA.model.User;

public interface UserRepository extends MongoRepository<User, Long> /*CrudRepository*/{
	List<User> findByInf(String inf);
}