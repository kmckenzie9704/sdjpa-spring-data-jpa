package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BookDaoHibernate implements BookDao {

    private final EntityManagerFactory emf;

    public BookDaoHibernate(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        EntityManager em = getEntityManager();
        try{
            String hql = "SELECT b FROM Book b order by b.title " + pageable.getSort().getOrderFor("title").getDirection().name();
            TypedQuery<Book> query = em.createQuery(hql, Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        EntityManager em = getEntityManager();
        try{
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return null;
    }

    @Override
    public List<Book> findAllBooks() {
        return null;
    }

//    @Override
//    public Book findBookByTitle(String title) {
//        return null;
//    }

    //    @Override
//    public List<Book> findAll() {
//        EntityManager em =  getEntityManager();
//        try{
//            TypedQuery<Book> typedQuery = em.createNamedQuery("book_find_all", Book.class);
//            return typedQuery.getResultList();
//        }
//        finally {
//            em.close();
//        }
//    }
//
//    @Override
//    public Book findByISBN(String isbn) {
//
//        EntityManager em = getEntityManager();
//        try {
//            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
//            query.setParameter("isbn", isbn);
//
//            Book book = query.getSingleResult();
//            return book;
//        }finally{
//            em.close();
//        }
//    }

    @Override
    public Book getById(Long id) {
        EntityManager em = getEntityManager();
        Book book = getEntityManager().find(Book.class, id);
        em.close();
        return book;
    }

//    @Override
//    public Book findBookByTitle(String title) {
//        EntityManager em = getEntityManager();
//
//        try{
//            TypedQuery<Book> query = em.createNamedQuery("book_find_by_title", Book.class);
//            query.setParameter("title", title);
//
//            Book book = query.getSingleResult();
//            return book;
//
//        }finally {
//            em.close();
//
//        }
//
//    }

//    @Override
//    public Book findBookByTitleNameCriteria(String title) {
//        EntityManager em = getEntityManager();
//
//        try {
//            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
//            Root<Book> root = criteriaQuery.from(Book.class);
//
//            ParameterExpression<String> titleParam = criteriaBuilder.parameter(String.class);
//
//            Predicate titlePred = criteriaBuilder.equal(root.get("title"), titleParam);
//
//            criteriaQuery.select(root).where(titlePred);
//
//            TypedQuery<Book> typedQuery = em.createQuery(criteriaQuery);
//            typedQuery.setParameter(titleParam, title);
//
//            return typedQuery.getSingleResult();
//        }finally {
//            em.close();
//        }
//    }
//
//
//    @Override
//    public Book findBookByTitleNative(String title) {
//        EntityManager em = getEntityManager();
//
//        try {
//            Query query = em.createNativeQuery("SELECT * FROM Book b WHERE b.title = :title", Book.class);
//            query.setParameter("title", title);
//
//
//            return (Book) query.getSingleResult();
//        }finally {
//            em.close();
//        }
//
//    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.flush();
        em.getTransaction().commit();
        em.close();

        return book;
    }

    @Override
    public Book updateBook(Book book) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.merge(book);
        em.flush();
        em.clear();
        Book saveBook = em.find(Book.class, book.getId());
        em.getTransaction().commit();
        em.close();
        return saveBook;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Book book = em.find(Book.class, id);
        em.remove(book);
        em.flush();
        em.getTransaction().commit();
        em.close();

    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

}