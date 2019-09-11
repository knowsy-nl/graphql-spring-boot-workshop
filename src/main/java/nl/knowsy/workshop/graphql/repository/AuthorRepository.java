package nl.knowsy.workshop.graphql.repository;

import nl.knowsy.workshop.graphql.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    @Query("from Author a where a.firstName=:firstName")
    public Iterable<Author> findByFirstname(@Param("firstName") String firstName);

    @Query("from Author a where a.lastName=:lastName")
    public Iterable<Author> findByLastname(@Param("lastName") String lastName);

    /*@Query("from Author a where a.age=:age")
    public Iterable<Author> findByAge(@Param("lastname") Integer age);*/
}
