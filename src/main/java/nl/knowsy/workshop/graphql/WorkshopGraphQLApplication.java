package nl.knowsy.workshop.graphql;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.core.GraphQLErrorHandler;
import nl.knowsy.workshop.graphql.exception.GraphQLErrorAdapter;
import nl.knowsy.workshop.graphql.model.Author;
import nl.knowsy.workshop.graphql.model.Book;
import nl.knowsy.workshop.graphql.repository.AuthorRepository;
import nl.knowsy.workshop.graphql.repository.BookRepository;
import nl.knowsy.workshop.graphql.resolver.AuthorResolver;
import nl.knowsy.workshop.graphql.resolver.BookResolver;
import nl.knowsy.workshop.graphql.resolver.Mutation;
import nl.knowsy.workshop.graphql.resolver.Query;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
public class WorkshopGraphQLApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkshopGraphQLApplication.class, args);
    }

    @Bean
    public GraphQLErrorHandler errorHandler() {
        return new GraphQLErrorHandler() {
            @Override
            public List<GraphQLError> processErrors(List<GraphQLError> errors) {
                List<GraphQLError> clientErrors = errors.stream()
                        .filter(this::isClientError)
                        .collect(Collectors.toList());

                List<GraphQLError> serverErrors = errors.stream()
                        .filter(e -> !isClientError(e))
                        .map(GraphQLErrorAdapter::new)
                        .collect(Collectors.toList());

                List<GraphQLError> e = new ArrayList<>();
                e.addAll(clientErrors);
                e.addAll(serverErrors);
                return e;
            }

            protected boolean isClientError(GraphQLError error) {
                return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
            }
        };
    }

    @Bean
    public BookResolver bookResolver(AuthorRepository authorRepository) {
        return new BookResolver(authorRepository);
    }

    @Bean
    public AuthorResolver authorResolver(AuthorRepository authorRepository, BookRepository bookRepository) {
        return new AuthorResolver(authorRepository, bookRepository);
    }

    @Bean
    public Query query(AuthorRepository authorRepository, BookRepository bookRepository) {
        return new Query(authorRepository, bookRepository);
    }

    @Bean
    public Mutation mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
        return new Mutation(authorRepository, bookRepository);
    }

    @Bean
    public CommandLineRunner demo(AuthorRepository authorRepository, BookRepository bookRepository) {
        return (args) -> {
            Author author = Author.builder().firstName("Herbert").lastName("Schildt").build();
            authorRepository.save(author);

            bookRepository.save(
                    Book.builder()
                            .title("Java: A Beginner's Guide, Sixth Edition")
                            .isbn("0071809252")
                            .authors(Collections.singleton(author))
                            .pageCount(728)
                            .build()
            );

            author = Author.builder().firstName("Douglas").lastName("Adams").build();
            authorRepository.save(author);

            bookRepository.save(
                    Book.builder()
                            .title("The Ultimate Hitchhiker's Guide to the Galaxy")
                            .isbn("0345453743")
                            .authors(Collections.singleton(author))
                            .pageCount(832)
                            .build()
            );

            Set<Author> authors = new HashSet<>();
            author = Author.builder().firstName("Ian").lastName("Goodfellow").build();
            authors.add(author);
            authorRepository.save(author);
            author = Author.builder().firstName("Yoshua").lastName("Bengio").build();
            authors.add(author);
            authorRepository.save(author);
            author = Author.builder().firstName("Aaron").lastName("Courville").build();
            authors.add(author);
            authorRepository.save(author);

            bookRepository.save(
                    Book.builder()
                            .title("Deep Learning")
                            .isbn("0262035618")
                            .authors(authors)
                            .pageCount(800)
                            .build()
            );
        };
    }
}
