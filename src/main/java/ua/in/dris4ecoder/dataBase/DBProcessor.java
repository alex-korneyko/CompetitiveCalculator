package ua.in.dris4ecoder.dataBase;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Alex Korneyko on 18.05.2016.
 */
public class DBProcessor {
    private Connection connection;

    public DBProcessor() throws SQLException {
        DriverManager.registerDriver(new FabricMySQLDriver());
        System.out.println("Database driver successfully registered.");
    }

    public Connection getConnection(String url, String userName, String password) throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(url, userName, password);

        System.out.println("Connection with database " + url + " established.");

        CheckDataBase.check(url, userName, password);

        return connection;

    }
}
