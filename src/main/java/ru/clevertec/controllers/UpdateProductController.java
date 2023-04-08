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

@WebServlet(urlPatterns = "/updateProduct")
public class UpdateProductController extends HttpServlet {
    private final Logger logger = Logger.getLogger(UpdateProductController.class);
    private final List<Product> products = new ArrayList<>();

    private final ProductsRepository<Product> modelProductRepository
            = new ProductAPIRepository(products);
    private final ProductService<Product> modelProductService =
            new ProductApiService(modelProductRepository);
    private static final String PRODUCT_UPDATE = "/pages/product/Update_Product.jsp";
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Long id = Long.valueOf(req.getParameter("id"));

        final String newName = req.getParameter("name");
        final int newPrice = Integer.parseInt(req.getParameter("price"));
        final int newAmount = Integer.parseInt(req.getParameter("amount"));
        final boolean isDiscount = Boolean.parseBoolean((req.getParameter("isDiscount")));
        Product product = new Product(newName, newPrice, newAmount, isDiscount);
        modelProductService.update(product,id);

        logger.info("UpdateProductController" +product);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(PRODUCT_UPDATE);
        requestDispatcher.forward(req,resp);



    }
}
