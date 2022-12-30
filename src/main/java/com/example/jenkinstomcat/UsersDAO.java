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
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                User user = new User(id, name, surname, age);
                users.add(user);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public User getUserById(long id) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM users WHERE id=?");

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            int age = resultSet.getInt("age");

            user = new User(id, name, surname, age);

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public void save(User user) {

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO users(name, surname, age) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setInt(3, user.getAge());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("user wasn't saved");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            }
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(User updateUser) {

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE users SET name=?, surname=?, age=? WHERE id=?",
                            Statement.RETURN_GENERATED_KEYS);

            long id = updateUser.getId();

            preparedStatement.setString(1, updateUser.getName());
            preparedStatement.setString(2, updateUser.getSurname());
            preparedStatement.setInt(3, updateUser.getAge());
            preparedStatement.setLong(4, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0){
                throw new SQLException("user wasn't updated");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                updateUser.setId(generatedKeys.getLong("id"));
            }

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(long id) {

        try {
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM users WHERE id = %d", id);
            statement.executeUpdate(query);
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
