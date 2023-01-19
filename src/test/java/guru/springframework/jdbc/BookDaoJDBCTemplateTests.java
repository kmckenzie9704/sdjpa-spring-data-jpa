package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.dao.BookDaoJDBCTemplate;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
class BookDaoJDBCTemplateTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    BookDao bookDAO;

    @BeforeEach
    void setUp(){
        bookDAO = new BookDaoJDBCTemplate(jdbcTemplate);
    }


    @Test
    void testFindAllBooks(){
        List<Book> books = bookDAO.findAllBooks();

        assertThat(books).isNotNull();

        assertThat(books.size()).isGreaterThan(3);
    }

    @Test
    void testFindAllBooksPage1(){
        List<Book> books = bookDAO.findAllBooks(10,0);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage2(){
        List<Book> books = bookDAO.findAllBooks(10,10);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage10(){
        List<Book> books = bookDAO.findAllBooks(10,100);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }


    @Test
    void testFindAllBooksPage1_pageable(){
        List<Book> books = bookDAO.findAllBooks(PageRequest.of(0, 10));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage2_pageable(){
        List<Book> books = bookDAO.findAllBooks(PageRequest.of(1,10,
                Sort.by(Sort.Order.desc("title"))));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage10_pageable(){
        List<Book> books = bookDAO.findAllBooks( PageRequest.of(10, 10,
                Sort.by(Sort.Order.desc("title"))));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }


    @Test
    void testFindAllBooksPage1_SortByTitle(){
        List<Book> books = bookDAO.findAllBooksSortByTitle(PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("title"))));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }



    @Test
    void testGetBookById(){
        Book book = bookDAO.getById(1L);

        assertThat(book).isNotNull();
    }
//    @Test
//    void testGetBookByTitle(){
//        Book book = bookDAO.findBookByTitle("Clean Code");
//
//        assertThat(book).isNotNull();
//    }

    @Test
    void testSaveNewBook(){
        Book book = new Book();
        book.setTitle("A New Book");
        book.setIsbn("1231552");
        book.setPublisher("Doubleday");
        Author author = new Author();
        author.setId(3L);

        book.setAuthorId(author.getId());
        Book saved = bookDAO.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testUpdateBook(){
        Book book = new Book();
        book.setTitle("A");
        book.setIsbn("1231552");
        book.setPublisher("Doubleday");

        Author author = new Author();
        author.setId(3L);

        book.setAuthorId(author.getId());

        Book saved = bookDAO.saveNewBook(book);

        assertThat(saved).isNotNull();

        saved.setTitle("A New Book");
        Book updated = bookDAO.updateBook(saved);

        assertThat(updated.getTitle()).isEqualTo("A New Book");
    }

    @Test
    void testDeleteBook(){
        Book book = new Book();
        book.setTitle("A New Book");
        book.setIsbn("1231552");
        book.setPublisher("Doubleday");
        Book saved = bookDAO.saveNewBook(book);

        assertThat(saved).isNotNull();

        bookDAO.deleteBookById(saved.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            bookDAO.getById(saved.getId());
        });
    }
}
