package home.Task_22.dao;

import home.Task_22.Const;
import home.Task_22.Storage;
import home.Task_22.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao implements Storage {

    private Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Dao() throws SQLException {
        connection = DriverManager.getConnection(Const.JDBC_URL, Const.USER, Const.PASSWORD);
        maybeCreateUsersTable();
    }

    private void maybeCreateUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String request = "CREATE TABLE IF NOT EXISTS users \n" +
                    "(id SERIAL, \n" +
                    "name varchar(100),\n" +
                    "age int, PRIMARY KEY (id));";
            statement.execute(request);
        }
    }

    @Override
    public void removeAll() {
        try (Statement statement = connection.createStatement()) {
            String request = "DELETE FROM users";
            statement.execute(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(int id) {
        try {
            String request = String.format("DELETE FROM users WHERE id=?");
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserByName(String name) {
        try {
            String request = "DELETE FROM users WHERE name=?";
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) {
        try {
            String request = "INSERT INTO users(name, age) VALUES(?, ?)";
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setString(1, user.getName());
            statement.setInt(2, user.getAge());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            String request = "UPDATE users SET name = ? , age = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setString(1, user.getName());
            statement.setInt(2, user.getAge());
            statement.setInt(3, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(int id) {
        User user = null;
        try {
            String request = "SELECT id, name, age FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int userID = 0;
            while (resultSet.next() && id != userID) {
                userID = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int age = resultSet.getInt(3);
                user = new User(userID, name, age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String request = "SELECT id, name, age FROM users";
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int age = resultSet.getInt(3);
                users.add(new User(id, name, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
