package nl.knowsy.workshop.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import nl.knowsy.workshop.graphql.model.Author;
import nl.knowsy.workshop.graphql.model.Book;
import nl.knowsy.workshop.graphql.repository.AuthorRepository;
import nl.knowsy.workshop.graphql.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AuthorResolver implements GraphQLResolver<Author> {
    private Logger logger = LoggerFactory.getLogger(AuthorResolver.class);

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public AuthorResolver(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(Author author) {
        return StreamSupport.stream(
                bookRepository.findAllById(
                        author.getBooks()
                                .stream()
                                .mapToLong(Book::getId)
                                .boxed().collect(Collectors.toList()))
                        .spliterator(), false)
                .collect(Collectors.toList());
    }
}
