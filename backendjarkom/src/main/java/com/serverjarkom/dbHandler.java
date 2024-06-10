package com.serverjarkom;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;
import com.serverjarkom.env.env;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class dbHandler {

    // private static HikariConfig config = new HikariConfig();
    // private static HikariDataSource ds ;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public dbHandler() throws SQLException {
        Driver dbJarkom = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(dbJarkom);
        getConnection();
        createStatemend(connection);
    }

    private void getConnection() throws SQLException {
        connection = DriverManager.getConnection(env.DB_URL, env.DB_USERNAME, env.DB_PASSWORD);
    }

    private Statement createStatemend(Connection con) throws SQLException {
        return statement = con.createStatement();
    }

    private void closeStatement() throws SQLException {
        statement.close();
    }

    public ResultSet executeQuery(String string) throws SQLException {
        resultSet = statement.executeQuery(string);
        return resultSet;
    }

    public ResultSet getRoom() throws SQLException {
        resultSet = statement.executeQuery("CALL showrooms()");
        return resultSet;
    }

    public ResultSet dbCredential(String username, String password) throws SQLException {
        resultSet = statement.executeQuery("CALL credential(\"" + username + "\", \"" + password + "\")");
        return resultSet;
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }

}
