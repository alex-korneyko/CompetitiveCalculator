package ua.in.dris4ecoder.dataBase;

import ua.in.dris4ecoder.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.in.dris4ecoder.dataBase.UserQueryResult.USER_FOUND;
import static ua.in.dris4ecoder.dataBase.UserQueryResult.USER_NOT_FOUND;
import static ua.in.dris4ecoder.dataBase.UserQueryResult.WRONG_USER_PASSWORD;

/**
 * Created by Alex Korneyko on 18.05.2016.
 */
public class Users {

    public static UserQueryResult login(String userName, String userPassword) throws SQLException {

        String query = "SELECT * FROM competitive_calculator.users";
        PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getString("UserName").equals(userName)) {
                if (resultSet.getString("UserPassword").equals(userPassword)) {
                    System.out.println("User '" + userName + "' is logged in");
                    return USER_FOUND;
                } else {
                    System.out.println("User '" + userName + "' wrong password!");
                    return WRONG_USER_PASSWORD;
                }
            }
        }

        System.out.println("User '" + userName + "' not found!");
        return USER_NOT_FOUND;
    }

    public static boolean logout(String userName, String userPassword) {

        return false;
    }

    public static boolean register(String userName, String userPassword) throws SQLException {
        if (login(userName, userPassword) == USER_FOUND || login(userName, userPassword) == WRONG_USER_PASSWORD)
            return false;

        final String addUserQuery = "INSERT INTO competitive_calculator.users (UserName, UserPassword, UserRights) VALUES (?,?,?)";
        PreparedStatement preparedStatement = Main.connection.prepareStatement(addUserQuery);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, userPassword);
        preparedStatement.setString(3, "user");
        preparedStatement.execute();

        System.out.println("The new user is registered (" + userName + ")");
        return true;
    }
}
