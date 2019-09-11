package nl.knowsy.workshop.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import nl.knowsy.workshop.graphql.model.Author;
import nl.knowsy.workshop.graphql.model.Book;
import nl.knowsy.workshop.graphql.repository.AuthorRepository;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BookResolver implements GraphQLResolver<Book> {
    private Logger logger = LoggerFactory.getLogger(BookResolver.class);

    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors(Book book) {
        return StreamSupport.stream(
                authorRepository.findAllById(
                        book.getAuthors()
                                .stream()
                                .mapToLong(Author::getId)
                                .boxed().collect(Collectors.toList()))
                        .spliterator(), false)
                .collect(Collectors.toList());
    }
}
