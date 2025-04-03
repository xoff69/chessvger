package com.xoff.chessvger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDao {

    private static final String INSERT_USER = "INSERT INTO " + CommonDao.COMMON_SCHEMA + ".users (login, description, password, profil,tenant_id)\n" +
            "VALUES     (?, ?, ?, ?,?) ON CONFLICT (login) DO NOTHING;";

    public static final String SELECT_LOGIN_PASSWORD = "SELECT id, login, description, password, date_created, date_updated, profil, tenant_id " +
            "FROM common.users WHERE login = ? AND password = ?";


    public static final String SELECT_BY_LOGIN =
            "SELECT id, login, description, password, date_created, date_updated, profil, tenant_id " +
                    "FROM common.users WHERE login = ?";

    public static void createUser(Connection connection,
                                  String login, String description, String password, boolean profil, int tenantId) throws Exception {

        try (PreparedStatement stmt = connection.prepareStatement(INSERT_USER)) {
            stmt.setString(1, login);
            stmt.setString(2, description);
            stmt.setString(3, password);
            stmt.setBoolean(4, profil);
            stmt.setInt(5, tenantId);
            stmt.executeUpdate();
            System.out.println("createUser: " + login);
        } catch (Exception e) {
            System.out.println("Error createUser: " + login + " " + e.getMessage());
        }
    }
}
