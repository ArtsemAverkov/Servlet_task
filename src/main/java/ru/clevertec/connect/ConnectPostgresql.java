package ru.clevertec.connect;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;


public class ConnectPostgresql implements Connect {
    private Connection connection;
    private final Logger logger = Logger.getLogger(ConnectPostgresql.class);

    private String url;
    private String username;
    private String password;
    private String driver;

    public ConnectPostgresql() {
        try {
            InputStream input = new FileInputStream(new File("config.yml"));
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(input);
            Map<String, Object> databaseConfig = (Map<String, Object>) config.get("datasource");
             url = databaseConfig.get("url").toString();
             username = databaseConfig.get("username").toString();
             password = databaseConfig.get("password").toString();
             driver = databaseConfig.get("driver").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
        logger.info("Connection to DB successful!");
        return connection;
    }

    @Override
    public boolean close() {
        try {
            connection.close();
            logger.info("Connection to Store DB closed!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}