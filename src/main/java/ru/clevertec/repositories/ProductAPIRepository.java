package ru.clevertec.repositories;

import ru.clevertec.entities.Product;
import ru.clevertec.util.JPA;
import ru.clevertec.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public class ProductAPIRepository implements ProductsRepository<Product> {
    JPA jpa;

    public ProductAPIRepository(List<Product> jpa) {
        this.jpa = new JPAUtil();
    }

    @Override
    public boolean create(Product product) {
        EntityManager entityManager = jpa.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("create ModelProduct (Model= :model " +
                "and Price = :price and Amount= : amount)");
        query.setParameter("name", product.getName());
        query.setParameter("price",product.getPrice());
        query.setParameter("amount", product.getAmount());
        query.setParameter("isDiscount", product.isDiscount());
//        List<Product> queryResultList = query.getResultList();
//        entityManager.persist(queryResultList);
        transaction.commit();
        return true;
    }

    @Override
    public Optional<List<Product>> read(long id) {
        EntityManager entityManager = jpa.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery
                ("select id from ModelProduct where id= :id");
        query.setParameter("id", id);
        List<Product> resultList = query.getResultList();
        resultList.forEach(System.out::println);
        Optional<List<Product>> resultListModelProduct = Optional.ofNullable(resultList);
        transaction.commit();
        return resultListModelProduct;
    }

    @Override
    public boolean update (Product product, Long id) {
        EntityManager entityManager = jpa.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery(
                "update ModelProduct set name= :name and price= :price and amount= :amount where " +
                "id = :id");
        query.setParameter("id",id);
        query.setParameter("name", product.getName());
        query.setParameter("price", product.getPrice());
        query.setParameter("amount", product.getAmount());

//        List<Product> queryResultList = query.getResultList();
//        entityManager.persist(queryResultList);
        transaction.commit();

        return true;
    }

    @Override
    public boolean delete(Long id) {
        EntityManager entityManager = jpa.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("delete ModelProduct where id= :id");
        query.setParameter("id", id);
        List queryResultList = query.getResultList();
        queryResultList.forEach(System.out::println);
        query.executeUpdate();
        transaction.commit();
        return false;
    }

    @Override
    public List<Product> readAll(Pageable pageable) {
        EntityManager entityManager = jpa.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        TypedQuery<Product> query = entityManager.createQuery
                ("select m from ModelProduct as m join fetch m.product", Product.class);
        List<Product> resultList = query.getResultList();
        transaction.commit();
        return resultList;
    }
}

