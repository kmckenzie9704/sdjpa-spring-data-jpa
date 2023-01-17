package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.AuthorDaoJDBCTemplate;
import guru.springframework.jdbc.domain.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
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
public class AuthorDaoJDBCTemplateTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    AuthorDao authorDAO;

    @BeforeEach
    void setUp(){
        authorDAO = new AuthorDaoJDBCTemplate(jdbcTemplate);
    }


    @Test
    void testFindAllAuthors(){
        List<Author> authors = authorDAO.findAllAuthors();

        assertThat(authors).isNotNull();

        assertThat(authors.size()).isGreaterThan(3);
    }

    @Test
    void testFindAllAuthorPage1_pageable(){
        List<Author> authors = authorDAO.findAllAuthorSortByLastName( "Smith", PageRequest.of(0, 10, Sort.by(Sort.Order.desc("firstname"))));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
    }

    @Test
    void testFindAllAuthorPage2_pageable(){
        List<Author> authors = authorDAO.findAllAuthorSortByLastName("Smith", PageRequest.of(1,10, Sort.by(Sort.Order.desc("firstname"))));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
    }

    @Test
    void testFindAllAuthorsPage10_pageable(){
        List<Author> authors = authorDAO.findAllAuthorSortByLastName("Smith",  PageRequest.of(10, 10, Sort.by(Sort.Order.desc("firstname"))));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(0);
    }
}
