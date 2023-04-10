package ru.clevertec.repositories;

import ru.clevertec.connect.Connect;
import ru.clevertec.connect.ConnectPostgresql;
import ru.clevertec.entities.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductAPIRepository implements ProductsRepository<Product> {

    Connect getConnection;

    public ProductAPIRepository() {
        this.getConnection = new ConnectPostgresql();
    }

    @Override
    public boolean create(Product product) {
        try(Connection conn = getConnection.connect()){
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT INTO Product (name, price, amount, isDiscount)VALUES (?,?,?,?)");
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getAmount());
            statement.setBoolean(4,product.isDiscount());
            return statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Product read(long id) {
    Product product = null;
        try(Connection conn = getConnection.connect()){
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product WHERE id=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Long amount = resultSet.getLong("amount");
                boolean isDiscount = resultSet.getBoolean("isDiscount");
                product = new Product(name, price, amount, isDiscount);
            }
            return product;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update (Product product, Long id) {
        try (Connection conn = getConnection.connect()) {
            PreparedStatement statement = conn.prepareStatement
                    ("UPDATE Product SET name=?, price=?, amount=?, isDiscount=? WHERE id=?");
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getAmount());
            statement.setBoolean(4, product.isDiscount());
            statement.setLong(5, id);
            return statement.execute();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        try(Connection conn = getConnection.connect()){
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Product WHERE id=?");
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product> readAll(int page, int pageSize) {
        List<Product> products = new ArrayList<>();
        try(Connection conn = getConnection.connect()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT*FROM Product LIMIT ?, ?");
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    Long amount = resultSet.getLong("amount");
                    boolean isDiscount = resultSet.getBoolean("isDiscount");
                products.add(new Product(name, price, amount, isDiscount));
                System.out.println(products);
            }
            return products;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}


