package ru.clevertec.controllers;

import ru.clevertec.util.db.DatabaseInitializer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DatabaseInitializerServlet", urlPatterns = {"/init"}, loadOnStartup = 1)
public class DatabaseInitializerServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            DatabaseInitializer databaseInitializer = new DatabaseInitializer();
            databaseInitializer.initializeDatabase();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
