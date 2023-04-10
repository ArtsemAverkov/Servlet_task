package ru.clevertec.repositories;


import java.util.List;
import java.util.Optional;


public interface ProductsRepository<Product> {
    boolean create(Product product);
    Product read (long id);
    boolean update (Product product, Long id);
    boolean delete (Long id);
    List<Product> readAll (int page, int pageSize);
}
