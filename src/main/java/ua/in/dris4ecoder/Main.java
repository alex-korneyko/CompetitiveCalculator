package ua.in.dris4ecoder;

import spark.Spark;
import ua.in.dris4ecoder.dataBase.DBProcessor;
import ua.in.dris4ecoder.parsers.ParseArgs;
import ua.in.dris4ecoder.web.MainPage;
import ua.in.dris4ecoder.web.Registration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Alex Korneyko on 15.05.2016.
 */
public class Main {

    public static Connection connection;
    public static Set<Integer> authorizedUsers;

    public static void main(String[] args) {

        try {
            DBProcessor db = new DBProcessor();
            connection = db.getConnection(ParseArgs.dbUrl(args), ParseArgs.dbUserName(args), ParseArgs.dbUserPass(args));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        int port = ParseArgs.port(args);
        if (port != 0) Spark.port(port);

        MainPage.startPage();
        Registration.registrationPages();
    }
}
