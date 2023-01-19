package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.*;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import guru.springframework.jdbc.repositories.AuthorRepository;
import guru.springframework.jdbc.repositories.BookRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by jt on 8/28/21.
 */
@ActiveProfiles("local")
@DataJpaTest
//@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@Import({BookDaoHibernate.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookDaoHibernateTests  {
//    @Autowired
//    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

//    @Autowired
//    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;




    @Test
    void testFindAllBooks() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0,10));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }


    @Test
    void testFindAllBooksSortByTitle() {
        List<Book> books = bookDao.findAllBooksSortByTitle(PageRequest.of(0,10,
                Sort.by(Sort.Order.desc("title"))));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }


//    @Test
//    void testDeleteBook() {
//        Book book = new Book();
//        book.setIsbn("1234");
//        book.setPublisher("Self");
//        book.setTitle("my book");
//        Book saved = bookDao.saveNewBook(book);
//
//        bookDao.deleteBookById(saved.getId());
//
//        Book deleted = bookDao.getById(saved.getId());
//
//        assertThat(deleted).isNull();
//    }

//    @Test
//    void testUpdateBook() {
//        Book book = new Book();
//        book.setIsbn("1234");
//        book.setPublisher("Self");
//        book.setTitle("my book");
//
//        Author author = new Author();
//        author.setId(3L);
//
//        book.setAuthorId(author.getId());
//        Book saved = bookDao.saveNewBook(book);
//
//        saved.setTitle("New Book");
//        bookDao.updateBook(saved);
//
//        Book fetched = bookDao.getById(saved.getId());
//
//        assertThat(fetched.getTitle()).isEqualTo("New Book");
//    }
//
//    @Test
//    void testSaveBook() {
//        Book book = new Book();
//        book.setIsbn("1234");
//        book.setPublisher("Self");
//        book.setTitle("my book");
//
//        Author author = new Author();
//        author.setId(3L);
//
//        book.setAuthorId(author.getId());
//        Book saved = bookDao.saveNewBook(book);
//
//        assertThat(saved).isNotNull();
//    }

//    @Test
//    void testfindBookByTitle() {
//        Book book = bookDao.findBookByTitle("Clean Code");
//
//        assertThat(book).isNotNull();
//    }

//    @Test
//    void findBookByISBN(){
//        Book book = new Book();
//        book.setIsbn("1234" + RandomString.make());
//        book.setTitle("ISBN Test");
//
//        Book saved = bookDao.saveNewBook(book);
//        Book fetched = bookDao.findByISBN(book.getIsbn());
//        assertThat(fetched).isNotNull();
//    }

    @Test
    void testGetBook() {
        Book book = bookDao.getById(1L);

        assertThat(book.getId()).isNotNull();
    }

//    @Test
//    void testFindBookByTitleNameCriteria()
//    {
//        Book book = bookDao.findBookByTitleNameCriteria("Clean Code");
//
//        assertThat(book).isNotNull();
//    }
//
//    @Test
//    void testFindBookByTitleNative()
//    {
//        Book book = bookDao.findBookByTitleNative("Clean Code");
//
//        assertThat(book).isNotNull();
//    }
//
//    @Test
//    void testFindAllAuthors() {
//        List<Author> authors = authorDao.findAll();
//
//        assertThat(authors).isNotNull();
//        assertThat(authors.size()).isGreaterThan(0);
//    }
//
//    @Test
//    void testListAuthorByLastNameLike(){
//        List<Author> authors = authorDao.listAuthorByLastNameLike("Wall");
//
//        assertThat(authors).isNotNull();
//        assertThat(authors.size()).isGreaterThan(0);
//    }

//    @Test
//    void testDeleteAuthor() {
//        Author author = new Author();
//        author.setFirstName("john");
//        author.setLastName("t");
//
//        Author saved = authorDao.saveNewAuthor(author);
//
//
//        authorDao.deleteAuthorById(saved.getId());
//
//        Author deleted = authorDao.getById(saved.getId());
//
//        assertThat(deleted).isNull();
//        assertThat(authorDao.getById(saved.getId()));
//
//    }

//    @Test
//    void testUpdateAuthor() {
//        Author author = new Author();
//        author.setFirstName("john");
//        author.setLastName("t");
//
//        Author saved = authorDao.saveNewAuthor(author);
//
//        saved.setLastName("Thompson");
//        Author updated = authorDao.updateAuthor(saved);
//
//        assertThat(updated.getLastName()).isEqualTo("Thompson");
//    }
//
//    @Test
//    void testSaveAuthor() {
//        Author author = new Author();
//        author.setFirstName("John");
//        author.setLastName("Thompson");
//        Author saved = authorDao.saveNewAuthor(author);
//
//        assertThat(saved).isNotNull();
//        assertThat(saved.getId()).isNotNull();
//    }
//
//    @Test
//    void testGetAuthorByName() {
//        Author author = authorDao.findAuthorByName("Craig", "Walls");
//
//        assertThat(author).isNotNull();
//    }
//
//    @Test
//    void testGetAuthor() {
//
//        Author author = authorDao.getById(3L);
//
//        assertThat(author).isNotNull();
//
//    }

//    @Test
//    void testGetAuthorByNameCriteria(){
//        Author author = authorDao.findAuthorByNameCriteria("Craig", "Walls");
//
//        assertThat(author).isNotNull();
//    }
//
//    @Test
//    void testFindAuthorByNameNative(){
//        Author author = authorDao.findAuthorByNameNative("Craig", "Walls");
//
//        assertThat(author).isNotNull();
//    }
}