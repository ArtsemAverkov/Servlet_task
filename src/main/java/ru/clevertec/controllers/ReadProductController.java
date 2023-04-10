package ru.clevertec.controllers;

import org.apache.pdfbox.io.IOUtils;
import ru.clevertec.entities.Product;
import ru.clevertec.repositories.ProductAPIRepository;
import ru.clevertec.repositories.ProductsRepository;
import ru.clevertec.services.ProductApiService;
import ru.clevertec.services.ProductService;
import ru.clevertec.services.pdfWriter.PdfWriteApiService;
import ru.clevertec.services.pdfWriter.PdfWriteService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@WebServlet(urlPatterns = "/getCheck")
public class ReadProductController extends HttpServlet {
    private PdfWriteService pdfWriteService;

    public ReadProductController(PdfWriteService pdfWriteService) {
        this.pdfWriteService = new PdfWriteApiService();
    }

    private final List<Product> products = new ArrayList<>();
    private final ProductsRepository<Product> modelProductRepository
            = new ProductAPIRepository(products);
    private final ProductService<Product> modelProductService =
            new ProductApiService(modelProductRepository);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final List<Long> id = Collections.singletonList(Long.parseLong(req.getParameter("id")));
        final List<Long> amount = Collections.singletonList(Long.parseLong(req.getParameter("amount")));
        final String isDiscount = (req.getParameter("isDiscount"));
            List<Object> check = modelProductService.getCheck(id, amount, isDiscount);
            pdfWriteService.writeToPdfFile(check);
            InputStream inputStream = new FileInputStream("updated_Clevertec_Template.pdf");
            byte[] contents = IOUtils.toByteArray(inputStream);
        resp.setContentType("application/pdf");
        resp.setHeader("Content-disposition", "attachment; updated_Clevertec_Template.pdf");
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.write(contents);
        outputStream.flush();
        outputStream.close();
    }
}
