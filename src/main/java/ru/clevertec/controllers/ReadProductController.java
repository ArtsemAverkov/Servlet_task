package ru.clevertec.controllers;

import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import ru.clevertec.entities.Product;
import ru.clevertec.repositories.ProductAPIRepository;
import ru.clevertec.repositories.ProductsRepository;
import ru.clevertec.services.ProductApiService;
import ru.clevertec.services.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
@WebServlet(urlPatterns = "/readProduct")
public class ReadProductController extends HttpServlet {
    private final Logger logger = Logger.getLogger(ReadProductController.class);
    private final List<Product> products = new ArrayList<>();

    private final ProductsRepository<Product> modelProductRepository
            = new ProductAPIRepository(products);
    private final ProductService<Product> modelProductService =
            new ProductApiService(modelProductRepository);
    private static final String PRODUCT_LIST_PATH = "/product/read";

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        final Long id = Long.valueOf(req.getParameter("id"));
        Product product = new Product(id);
        logger.info(" ReadProductController" + product);
      modelProductService.read(id);


        final RequestDispatcher requestDispatcher = req.getRequestDispatcher(PRODUCT_LIST_PATH);
        requestDispatcher.forward(req, resp);

    }
}
