package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class AuthorDaoJDBCTemplate implements AuthorDao{

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Author> findAllAuthors() {
        return jdbcTemplate.query("SELECT * FROM Author", getAuthorMapper());
    }

    @Override
    public List<Author> findAllAuthorSortByLastName(String lastName, Pageable pageable) {
//        String sql = "SELECT * FROM Author where  last_name = ?  order by first_name  " + pageable
//                .getSort().getOrderFor("firstname").getDirection().name() + "  limit ?  offset ? ";

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * FROM Author WHERE LAST_NAME = ? ");
        if(pageable.getSort().getOrderFor("firstname") != null){
            sb.append("order by first_name " ).append(pageable.getSort().getOrderFor("firstname").getDirection().name());
        }

        sb.append(" limit ? offset ?");

//        System.out.println(sql);
        return jdbcTemplate.query(sb.toString(), getAuthorMapper(), lastName, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }


    private RowMapper<Author> getAuthorMapper(){
        return new AuthorMapper();
    }


}
