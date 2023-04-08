package ru.clevertec.services;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.entities.Product;
import ru.clevertec.repositories.ProductsRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


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
    public Object read(long id) {
        Optional read = productRepository.read(id);
        if (read.isEmpty()){
            return read.get();
        }
        return null;
    }


//    @Override
//    public List<Object> getCheck(List<Long> id, List<Long> amount, Long idDiscount, String discount) {
//        List<Product> productList = getProducts(id, amount);
//        List<String> sumCheck = new ArrayList<>();
//        List<Object> objectList = new ArrayList<>();
//
//        Consumer<Product> productConsumer = getProductConsumer(productList);
//
//        double sum = productList.stream()
//                .map(Product::getSum)
//                .mapToDouble(f -> f).sum();
//
//
//        setDiscountInProduct(idDiscount, discount, productList, productConsumer);
//
//        double sumAfterDiscount = productList.stream()
//                .map(Product::getSum)
//                .mapToDouble(f -> f).sum();
//
//        List<Object> collect = Stream.concat(productList.stream(), sumCheck.stream())
//                .collect(Collectors.toList());
//        collect.add("sum:");
//        collect.add(sum);
//        collect.add("sumAfterDiscount:");
//        collect.add(sumAfterDiscount);
//        return collect;
//    }


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
    public List<Product> readAll(Pageable pageable) {
        return productRepository.readAll(pageable);
    }



//    private void setDiscountInProduct(Long idDiscount, String discount, List<Product> productList, Consumer<Product> productConsumer) {
//        productList.stream()
//                .filter(product -> product.getMetaInfProduct().isDiscount())
//                .collect(Collectors.toList());
//        if (discount.isEmpty() | Objects.nonNull(discountService.read(idDiscount))) {
//            if (productList.size() >= 5) {
//                productList.stream()
//                        .peek(productConsumer)
//                        .peek(product -> {
//                            double sumProduct = product.getSum();
//                            double sum = sumProduct * 0.9;
//                            double rounded = Math.round(100 * sum) / 100.0;
//                            product.setSum(rounded);
//                        })
//                        .forEach(productConsumer);
//            }
//        }
//    }
//
//
//    private List<Product> getProducts(List<Long> id, List<Long> amount) {
//        int x = 0;
//        List<Product> productList = new ArrayList<>();
//        for (Long w : id) {
//            Product read = read(w);
//            read.setAmount(amount.get(x++));
//            productList.add(read);
//        }
//        return productList;
//    }
//
//    private Consumer<Product> getProductConsumer(List<Product> productList) {
//        Consumer<Product> productConsumer =
//                product -> Comparator.naturalOrder();
//        productList.stream()
//                .peek(productConsumer)
//                .peek(product -> {
//                    Long amounts = product.getAmount();
//                    double price = product.getPrice();
//                    product.setSum(amounts * price);
//                })
//                .forEach(productConsumer);
//        return productConsumer;
//    }

}
