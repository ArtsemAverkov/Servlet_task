package ru.clevertec.filter;

import ru.clevertec.entities.Product;
import ru.clevertec.repositories.ProductAPIRepository;
import ru.clevertec.repositories.ProductsRepository;
import ru.clevertec.services.ProductApiService;
import ru.clevertec.services.ProductService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@WebFilter(urlPatterns = "/getCheck")
public class GetProductFilter implements Filter {
    private final ProductsRepository<Product> productRepository
            = new ProductAPIRepository();
    private final ProductService<Product> productService =
            new ProductApiService(productRepository);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final List<Long> id = Collections.singletonList(Long.parseLong(request.getParameter("id")));
        for (Long l: id) {
            try {
                Object read = productService.read(l);
                if (Objects.isNull(read)){
                    RequestDispatcher requestDispatcher =
                            request.getRequestDispatcher("/pages/errors/ErrorReadProduct.jsp");
                    requestDispatcher.forward(request, response);

                }else {
                    chain.doFilter(request, response);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {

    }
}
