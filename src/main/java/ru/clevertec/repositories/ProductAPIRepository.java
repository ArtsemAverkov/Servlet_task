package ru.clevertec.repositories;

import ru.clevertec.entities.Product;
import ru.clevertec.util.JPA;
import ru.clevertec.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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
        Query query = entityManager.createQuery("create Product (name= :name " +
                "and price = :price and amount= : amount and isDiscount= :isDiscount)");
        query.setParameter("name", product.getName());
        query.setParameter("price",product.getPrice());
        query.setParameter("amount", product.getAmount());
        query.setParameter("isDiscount", product.isDiscount());
        transaction.commit();
        return true;
    }

    @Override
    public Optional<List<Product>> read(long id) {
        EntityManager entityManager = jpa.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery
                ("select id from Product where id= :id");
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
                "update Product set name= :name and price= :price and amount= :amount where " +
                "id = :id");
        query.setParameter("id",id);
        query.setParameter("name", product.getName());
        query.setParameter("price", product.getPrice());
        query.setParameter("amount", product.getAmount());
        transaction.commit();
        return true;
    }

    @Override
    public boolean delete(Long id) {
        EntityManager entityManager = jpa.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("delete Product where id= :id");
        query.setParameter("id", id);
        List queryResultList = query.getResultList();
        queryResultList.forEach(System.out::println);
        query.executeUpdate();
        transaction.commit();
        return false;
    }

    @Override
    public List<Product> readAll(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        EntityManager entityManager = jpa.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("SELECT * FROM Product LIMIT ?, ?");
        query.setParameter(1, offset);
        query.setParameter(2, pageSize);
        List resultList = query.getResultList();
        transaction.commit();
        return resultList;
    }
}

