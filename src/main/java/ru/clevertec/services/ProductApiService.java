package ru.clevertec.services;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.entities.Product;
import ru.clevertec.repositories.ProductsRepository;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ProductApiService implements ProductService<Product> {
    private final ProductsRepository productRepository;

    public ProductApiService(ProductsRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean create(Product product) {
        return productRepository.create(product);
    }

    @Override
    public Product read(long id) {
        Object read = productRepository.read(id);
        return (Product) read;
    }

    @Override
    public List<Object> getCheck(List<Long> id, List<Long> amount, String discount) {
        List<Product> productList = getProducts(id, amount);
        List<String> sumCheck = new ArrayList<>();
        List<Object> objectList = new ArrayList<>();

        Consumer<Product> productConsumer = getProductConsumer(productList);

        double sum = productList.stream()
                .map(Product::getSum)
                .mapToDouble(f -> f).sum();


        setDiscountInProduct( discount, productList, productConsumer);

        double sumAfterDiscount = productList.stream()
                .map(Product::getSum)
                .mapToDouble(f -> f).sum();

        List<Object> collect = Stream.concat(productList.stream(), sumCheck.stream())
                .collect(Collectors.toList());
        collect.add("sum:");
        collect.add(sum);
        collect.add("sumAfterDiscount:");
        collect.add(sumAfterDiscount);
        return collect;
    }


    @Override
    public boolean update(Product product, Long id) {
        Object read = read(id);
        if (Objects.nonNull(read)){
            productRepository.update(product, id);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Object read = read(id);
        if (Objects.nonNull(read)) {
            productRepository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> readAll(int page, int pageSize) {
        return productRepository.readAll(page, pageSize);
    }



    private void setDiscountInProduct( String discount, List<Product> productList, Consumer<Product> productConsumer) {
        productList.stream()
                .filter(Product::isDiscount)
                .collect(Collectors.toList());
        if (discount.equals("CARD-1234")) {
            if (productList.size() >= 5) {
                productList.stream()
                        .peek(productConsumer)
                        .peek(product -> {
                            double sumProduct = product.getSum();
                            double sum = sumProduct * 0.9;
                            double rounded = Math.round(100 * sum) / 100.0;
                            product.setSum(rounded);
                        })
                        .forEach(productConsumer);
            }
        }
    }


    private List<Product> getProducts(List<Long> id, List<Long> amount) {
        int x = 0;
        List<Product> productList = new ArrayList<>();
        for (Long w : id) {
            Product read = (Product)read(w);
            read.setAmount(amount.get(x++));
            productList.add(read);
        }
        return productList;
    }

    private Consumer<Product> getProductConsumer(List<Product> productList) {
        Consumer<Product> productConsumer =
                product -> Comparator.naturalOrder();
        productList.stream()
                .peek(productConsumer)
                .peek(product -> {
                    Long amounts = product.getAmount();
                    double price = product.getPrice();
                    product.setSum(amounts * price);
                })
                .forEach(productConsumer);
        return productConsumer;
    }

}
