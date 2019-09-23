package home.Task_22;

import home.Task_22.dao.Dao;
import home.Task_22.model.User;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        User userAlex = new User("Alex", 22);
        User userDen = new User("Den", 27);
        User userMax = new User("Max", 23);
        User userCarl = new User("Carl", 24);
        User userSam = new User("Sam", 19);
        User userTom = new User("Tom", 32);
        User userPhilip = new User("Philip", 14);

        try {
            Dao dao = new Dao();

            dao.addUser(userAlex);
            dao.addUser(userDen);
            dao.addUser(userMax);
            dao.addUser(userCarl);
            dao.addUser(userSam);
            dao.addUser(userTom);
            dao.addUser(userPhilip);

            System.out.println(".getAllUsers(): " + dao.getAllUsers());
            System.out.println(".getUser(id:1): " + dao.getUser(1) + "\n");

            dao.updateUser(new User(1,"David", 55));
            System.out.println(".updateUser(id:1): " + dao.getAllUsers() + "\n");

            dao.removeUserByName("Carl");
            System.out.println("removed \"Carl\" (id = 4): " + dao.getAllUsers());

            dao.removeUser(1);
            dao.removeUser(3);
            dao.removeUser(6);
            System.out.println("removed users id: 1, 3, 6: " + dao.getAllUsers() + "\n");

            dao.removeAll();
            System.out.println(".removeAll(): " + dao.getAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
