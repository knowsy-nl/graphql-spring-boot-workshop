package nl.knowsy.workshop.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import nl.knowsy.workshop.graphql.exception.BookNotFoundException;
import nl.knowsy.workshop.graphql.model.Author;
import nl.knowsy.workshop.graphql.model.Book;
import nl.knowsy.workshop.graphql.repository.AuthorRepository;
import nl.knowsy.workshop.graphql.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Mutation implements GraphQLMutationResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author newAuthor(String firstName, String lastName) {
        Author author = Author.builder().firstName(firstName).lastName(lastName).build();
        authorRepository.save(author);
        return author;
    }

    public Book newBook(String title, String isbn, Set<Author> authors, Integer pageCount) {
        Book book = Book.builder().title(title).isbn(isbn).authors(authors).pageCount(pageCount).build();
        bookRepository.save(book);
        return book;
    }

    public boolean deleteBook(Long id) {
        bookRepository.deleteById(id);
        return true;
    }

    public Book updateBookPageCount(Integer pageCount, Long id) {
        return bookRepository.findById(id).<BookNotFoundException>orElseThrow(() -> {
            throw new BookNotFoundException("The book to be updated was found", id);
        });
    }
}
