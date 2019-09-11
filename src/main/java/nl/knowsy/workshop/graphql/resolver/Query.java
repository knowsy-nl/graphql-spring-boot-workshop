package nl.knowsy.workshop.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import nl.knowsy.workshop.graphql.model.Author;
import nl.knowsy.workshop.graphql.model.Book;
import nl.knowsy.workshop.graphql.repository.AuthorRepository;
import nl.knowsy.workshop.graphql.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Query implements GraphQLQueryResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Query(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Iterable<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> findAuthorsByFirstname(String firstname) {
        return StreamSupport.stream(authorRepository.findByFirstname(firstname)
                .spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Author> findAuthorsByLastname(String lastname) {
        return StreamSupport.stream(authorRepository.findByLastname(lastname)
                .spliterator(), false)
                .collect(Collectors.toList());
    }

    public long countBooks() {
        return bookRepository.count();
    }
    public long countAuthors() {
        return authorRepository.count();
    }
}
