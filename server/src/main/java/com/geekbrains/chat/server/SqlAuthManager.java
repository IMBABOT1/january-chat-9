package com.geekbrains.chat.server;

import java.sql.*;

public class SqlAuthManager implements AuthManager {

    private static Connection connection;
    private static Statement stmt;

    @Override
    public void start() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String pass) {
        String result = "";
        try {
            ResultSet rs = stmt.executeQuery("SELECT nickname FROM users WHERE login like " + "'" + login + "'" + "AND password like " + "'" + pass + "'");
            result = rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public void changeNick(String newNick, String oldNick) {
        try {
            stmt.executeUpdate("UPDATE users SET nickname = " + "'" + newNick + "'" + " WHERE nickname = " + "'" + oldNick + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
