package ru.clevertec.repositories;


import java.util.List;
import java.util.Optional;


public interface ProductsRepository<Product> {
    boolean create(Product product);
    Optional<List<Product>> read (long id);
   // List<Object> getCheck (List<Long> id, List<Long> amount, Long idDiscount, String discount);
    boolean update (Product product, Long id);
    boolean delete (Long id);
    List<Product> readAll (int page, int pageSize);
}
