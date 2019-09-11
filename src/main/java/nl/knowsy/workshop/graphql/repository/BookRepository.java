package nl.knowsy.workshop.graphql.repository;

import nl.knowsy.workshop.graphql.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
