package com.xoff.chessvger.dao;

public class UtilDao {

    public static String getCountQuery(String tableName) {
        return String.format("SELECT count(*) FROM %s ", tableName);

    }

    public static String getAllQuery(String tableName) {
        return String.format("SELECT * FROM %s ", tableName);
    }

    public static String getAllPaginatedQuery(String tableName) {
        return String.format("SELECT * FROM %s LIMIT ? OFFSET ?", tableName);
    }

    public static String findById(String tableName) {
        return findById(tableName, "id");

    }

    public static String findById(String tableName, String idParam) {
        return String.format("SELECT * FROM %s WHERE %s = ?", tableName, idParam);

    }

}
