package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class BookDaoJDBCTemplate implements BookDao{

    private final JdbcTemplate jdbcTemplate;

    public BookDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findAllBooks() {

        return jdbcTemplate.query("SELECT * FROM Book", getBookMapper());
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return jdbcTemplate.query("SELECT * FROM BOOK limit ? offset ?", getBookMapper(), pageSize, offset);
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM BOOK limit ? offset ?", getBookMapper(), pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        String sql = "SELECT * FROM BOOK WHERE publisher = 'Addison Wesley' order by title " + pageable
                .getSort().getOrderFor("title").getDirection().name() + " limit ? offset ?";

        System.out.println(sql);
        return jdbcTemplate.query(sql, getBookMapper(), pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public Book getById(Long Id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Book WHERE ID = ?", getBookMapper(), Id);
    }
//    @Override
//    public Book findBookByTitle(String title) {
//        return jdbcTemplate.queryForObject("SELECT * FROM BOOK WHERE TITLE = ?", getBookMapper(), title);
//
//    }

    @Override
    public Book saveNewBook(Book book) {

        jdbcTemplate.update("INSERT INTO BOOK (title, isbn, publisher, author_id) VALUES (?,?,?,?)", book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId());

        Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return this.getById(createdId);

    }

    @Override
    public Book updateBook(Book book) {

        jdbcTemplate.update("UPDATE BOOK SET TITLE = ?, ISBN = ?, PUBLISHER = ?, AUTHOR_ID = ? WHERE ID = ?",
                book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId(), book.getId());

        return this.getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE FROM BOOK WHERE ID = ?", id);

    }

    private RowMapper<Book> getBookMapper(){
        return new BookMapper();
    }

}
