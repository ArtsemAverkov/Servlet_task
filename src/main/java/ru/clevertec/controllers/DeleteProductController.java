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
@WebServlet(urlPatterns = {"/delete"})
public class DeleteProductController extends HttpServlet {
    private final Logger logger = Logger.getLogger(DeleteProductController.class);
    private final ProductsRepository<Product> productRepository
            = new ProductAPIRepository();
    private final ProductService<Product> productService =
            new ProductApiService(productRepository);

   private static final String PRODUCT_DELETE = "/pages/product/Delete_Product.jsp";

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        final Long id = Long.valueOf(req.getParameter("id"));
        final Product product = new Product(id);
        logger.info("DeleteProductController" + product);
        productService.delete(id);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(PRODUCT_DELETE);
        requestDispatcher.forward(req,resp);
    }
}

