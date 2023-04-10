package ru.clevertec.util.db;

import org.yaml.snakeyaml.Yaml;
import ru.clevertec.connect.Connect;
import ru.clevertec.connect.ConnectPostgresql;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Map;

public class DatabaseInitializer {
    Connect getConnection;

    public DatabaseInitializer() {
        this.getConnection = new ConnectPostgresql();
    }

    public void initializeDatabase() throws IOException, SQLException {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = getClass().getResourceAsStream("/config.yml")) {
            Map<String, Object> config = yaml.load(inputStream);
            Map<String, Object> databaseConfig = (Map<String, Object>) config.get("database");
            boolean initialize = (boolean) databaseConfig.get("initialize");
            String schemaPath = (String) databaseConfig.get("schema");
            String dataPath = (String) databaseConfig.get("data");
            if (initialize) {
                executeSqlFromFile(schemaPath);
                executeSqlFromFile(dataPath);
            }
        }
    }

    private void executeSqlFromFile(String path) throws IOException, SQLException {
        String query = new String(Files.readAllBytes(Paths.get(URI.create(path))));
        try (Connection connect = getConnection.connect();
             PreparedStatement stmt = connect.prepareStatement(query)) {
            stmt.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

