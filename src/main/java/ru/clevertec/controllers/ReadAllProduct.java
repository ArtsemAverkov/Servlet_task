package ru.clevertec.controllers;

import org.apache.log4j.Logger;
import ru.clevertec.entities.Product;
import ru.clevertec.repositories.ProductAPIRepository;
import ru.clevertec.repositories.ProductsRepository;
import ru.clevertec.services.ProductApiService;
import ru.clevertec.services.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/readAllProduct"})
public class ReadAllProduct extends HttpServlet {
    private final Logger logger = Logger.getLogger(ReadAllProduct.class);
    private final List<Product> products = new ArrayList<>();

    private final ProductsRepository<Product> modelProductRepository
            = new ProductAPIRepository(products);
    private final ProductService<Product> modelProductService =
            new ProductApiService(modelProductRepository);


    public static final String PRODUCT_PAGE ="pages/product/List_product.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        final RequestDispatcher requestDispatcher = req.getRequestDispatcher(PRODUCT_PAGE);
        List<Product> products = modelProductService.readAll(Pageable.ofSize(0).withPage(0));

        logger.info("ReadAllProduct :" + products);
         req.setAttribute("product", products);
        requestDispatcher.forward(req,resp);

    }
}
