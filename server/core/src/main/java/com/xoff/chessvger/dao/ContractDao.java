package com.xoff.chessvger.dao;

import com.xoff.chessvger.model.Contract;
import com.xoff.chessvger.model.UserTenant;

import java.sql.Connection;

public class ContractDao {

    public static final String INSERT_CONTRACT = "INSERT INTO \"+ADMIN_SCHEMA+\".contract (price, durationDay, startDate, endDate, databaseId)\n" +
            "VALUES (100000, 30, '2024-01-01T00:00:00Z', '2024-01-31T00:00:00Z', 1);";

    public static Contract getDefaultContract() {
        // TODO
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void linkUserToContract(UserTenant userTenant, Contract contract) {
        //  throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void insertDefautContract(Connection connection) {// TODO
        // throw new UnsupportedOperationException("Not yet implemented");
    }
}
