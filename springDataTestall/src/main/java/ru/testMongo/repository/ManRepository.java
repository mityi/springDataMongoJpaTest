package ru.testMongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.testMongo.model.Man;

public interface ManRepository extends MongoRepository<Man, String> {
    public Man findByName(String firstName);
    public List<Man> findByNameNot(String lastName);
}
