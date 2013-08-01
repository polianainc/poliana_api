package poliana.data.repositories;

import poliana.data.models.test.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestRespository extends MongoRepository<Person, Long> {
    List<Person> findAll();
    Person save(Person person);
}
