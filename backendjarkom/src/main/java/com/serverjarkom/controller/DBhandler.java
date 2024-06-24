package com.serverjarkom.controller;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;
import com.serverjarkom.env.Env;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBhandler {

    // private static HikariConfig config = new HikariConfig();
    // private static HikariDataSource ds ;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public DBhandler() throws SQLException {
        Driver dbJarkom = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(dbJarkom);
        getConnection();
        createStatemend(connection);
    }

    private void getConnection() throws SQLException {
        connection = DriverManager.getConnection(Env.DB_URL, Env.DB_USERNAME, Env.DB_PASSWORD);
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

    // public ResultSet getRoom() throws SQLException {
    // resultSet = statement.executeQuery("CALL showrooms()");
    // return resultSet;
    // }
    
    public ResultSet dbRegister(String idClient, String username, String password, String clientName)
            throws SQLException {
        resultSet = statement.executeQuery("CALL register(\"" + idClient + "\", \"" + username + "\", \"" + password
                + "\", \"" + clientName + "\")");
        return resultSet;
    }

    public ResultSet dbCredential(String username, String password) throws SQLException {
        resultSet = statement.executeQuery("CALL credential(\"" + username + "\", \"" + password + "\")");
        return resultSet;
    }

    public ResultSet dblistClient() throws SQLException {
        resultSet = statement.executeQuery("CALL listClient()");
        return resultSet;
    }

    public ResultSet dblistRoom() throws SQLException {
        resultSet = statement.executeQuery("CALL listRoom()");
        return resultSet;
    }

    public ResultSet dbListJoinedRoom(String idClient) throws SQLException {
        resultSet = statement.executeQuery("CALL listJoinedRoom(\"" + idClient + "\")");
        return resultSet;
    }

    public ResultSet dbCreateRoom(String roomName, String roomCode, String roomOwner) throws SQLException {
        resultSet = statement
                .executeQuery("CALL createRoom(\"" + roomName + "\", \"" + roomCode + "\", \"" + roomOwner + "\")");
        return resultSet;
    }

    public ResultSet dbJoinRoom(String roomName, String idClient) throws SQLException {
        resultSet = statement.executeQuery("CALL joinRoom(\"" + roomName + "\", \"" + idClient + "\")");
        return resultSet;
    }

    public ResultSet dbLeaveRoom(String roomName, String idClient) throws SQLException {
        resultSet = statement.executeQuery("CALL leaveRoom(\"" + roomName + "\", \"" + idClient + "\")");
        return resultSet;
    }

    public ResultSet dbDeleteRoom(String roomName) throws SQLException {
        resultSet = statement.executeQuery("CALL deleteRoom(\"" + roomName + "\")");
        return resultSet;
    }

    public ResultSet dbLogin(String username, String password) throws SQLException{
        resultSet = statement.executeQuery("CALL login(\"" + username + "\",\"" + password + "\")");
        return resultSet;
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }

}
