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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(urlPatterns = "/product/create")
public class CreateProductController extends HttpServlet {
    private final Logger logger = Logger.getLogger(CreateProductController.class);
    private final ProductsRepository<Product> productRepository
            = new ProductAPIRepository();
    private final ProductService<Product> productService =
            new ProductApiService(productRepository);

    private static final String PRODUCT_LIST_PATH = "/pages/product/Create_Product.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String name = req.getParameter("name");
        final int price = Integer.parseInt(req.getParameter("price"));
        final int amount = Integer.parseInt(req.getParameter("amount"));
        final boolean isDiscount = Boolean.parseBoolean((req.getParameter("isDiscount")));
        final Product product = new Product(name ,price, amount, isDiscount);

        logger.info("CreateProductController" + product);
        productService.create(product);

        final RequestDispatcher requestDispatcher = req.getRequestDispatcher(PRODUCT_LIST_PATH);
        requestDispatcher.forward(req, resp);
    }
}
