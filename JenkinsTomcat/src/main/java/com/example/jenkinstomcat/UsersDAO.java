package com.example.jenkinstomcat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {

    private Connection connection;

    public UsersDAO() {
        connection = getConnection();
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM Users");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                User user = new User(id, name, age);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public User getUserById(long id) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM User WHERE id=?");

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");

            user = new User(id, name, age);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public void save(User user) {

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO Users VALUES (?, ?)");

            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(long id, User updateUser) {
        boolean result;

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE Users SET name=?, age=? WHERE id=?");

            preparedStatement.setString(1, updateUser.getName());
            preparedStatement.setInt(2, updateUser.getAge());

            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public boolean delete(long id) {
        boolean result;

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM Users WHERE id=?");

            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager
                    .getConnection(
                            "jdbc:postgresql://192.168.0.190:5432/andersen_db",
                            "valera",
                            "163425");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}
