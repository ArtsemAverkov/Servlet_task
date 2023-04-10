package ru.clevertec.services;


import java.util.List;

public interface ProductService <Product>{
    boolean create(Product product);
    Object read (long id) throws Exception;
    List<Object> getCheck (List<Long> id, List<Long> amount, String discount);
    boolean update (Product product, Long id);
    boolean delete (Long id);
    List<Product> readAll (int page, int pageSize);
}
