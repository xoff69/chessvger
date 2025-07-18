package com.xoff.chessvger.dao;


import com.xoff.chessvger.util.FileUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// TODO gestion des exceptions
// TODO gestion des databases: comment on switche
// TODO gestion des exceptions
// TODO gestion des databases: comment on switche

@Slf4j
public class CommonDao {
    public static final String COMMON_SCHEMA = "common";
    public static final String SCHEMA_TENANT_PATTERN = "tenant_%s";

    private static final String CHECK_SCHEMA_EXISTS_SQL =
            "SELECT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = ?)";
    private static final String CREATE_SCHEMA_SQL =
            "CREATE SCHEMA IF NOT EXISTS %s";


    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("connexion DB :" + e);
        }
        // System.out.println("connexion DB :" + "jdbc:postgresql://" + Main.getDBHost() + "/chessvger");


    }
    static {
        try {
            Class.forName("com.clickhouse.jdbc.ClickHouseDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("connexion DB :" + e);
        }
    }

    public static void executeSqlFromFile(Connection connection, String filename, String schema) {
        log.info("executeSqlFromFile: " + filename + " schema: " + schema);
        String queryFromFile = FileUtils.read(filename);
        long count = queryFromFile.chars().filter(c -> c == '%').count(); // nombre de %
Object[] args = new Object[(int) count];
Arrays.fill(args, schema);
String sql = String.format(queryFromFile, args);
        CommonDao.executeQuery(connection, sql);
    }

    public static void executeSqlFromFile(Connection connection, String filename) {
        String queryFromFile = FileUtils.read(filename);
        CommonDao.executeQuery(connection, queryFromFile);
    }

    private static final Map<String, HikariDataSource> mapDatasource = new HashMap();

    private CommonDao() {
    }

    public static Connection getConnection() throws SQLException {
        return getConnection("chessvger");
    }

    /**
     * the schema must be in the query itself
     *
     * @param databaseName
     * @return
     * @throws SQLException
     */
    // TODO
    private static final String dbhost = "db_chessvger";

    public static Connection getConnection(String databaseName) throws SQLException {
        if (!mapDatasource.containsKey(databaseName)) {
            HikariConfig config = new HikariConfig();
            // url: jdbc:postgresql://localhost/chessvger?currentSchema=common
            config.setJdbcUrl("jdbc:postgresql://" + dbhost + "/" + databaseName);
            config.setUsername("chessvger");
            config.setPassword("chessvger");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.setAutoCommit(true);

            mapDatasource.put(databaseName, new HikariDataSource(config));
        }
        return mapDatasource.get(databaseName).getConnection();
    }

    public static boolean createSchemaIfNotExists(Connection connection, String schemaName)
            throws Exception {
        if (!schemaExists(connection, schemaName)) {
            createSchema(connection, schemaName);
        }
        return true;
    }

    private static boolean schemaExists(Connection connection, String schemaName) throws Exception {
        try (PreparedStatement stmt = connection.prepareStatement(CHECK_SCHEMA_EXISTS_SQL)) {
            stmt.setString(1, schemaName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        return false;
    }

    private static void createSchema(Connection connection, String schemaName) throws Exception {
        String sql = String.format(CREATE_SCHEMA_SQL, schemaName);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            log.info("Schema created: " + schemaName);
        }
    }


    public static void executeQuery(Connection connection, String query) {

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            log.info("Query executed: " + query);
        } catch (Exception e) {
            log.error("Error executeQuery " + query + " " + e.getMessage());

        }
    }

    public static void createDatabasePg(Connection connection, String databaseName) throws Exception {


        try (Statement statement = connection.createStatement()) {

            // Requête SQL pour créer une nouvelle base de données
            String sql = "CREATE DATABASE " + databaseName;

            // Exécution de la requête
            statement.executeUpdate(sql);
            log.info("ChessDatabase created : " + databaseName);

        } catch (SQLException e) {
            log.error("Erreur lors de la création de la base de données : " + e.getMessage());
        }
    }
 public static Connection getConnectionClickHouse()throws SQLException{
     String url = "jdbc:clickhouse://clickhouse:8123/default";

     Properties props = new Properties();
     props.setProperty("user", "user");
     props.setProperty("password", "password");

     Connection connectionClickHouse = DriverManager.getConnection(url, props);
     return connectionClickHouse;
 }
}