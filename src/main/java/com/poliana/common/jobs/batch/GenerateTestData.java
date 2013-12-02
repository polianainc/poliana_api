package com.poliana.common.jobs.batch;

import org.h2.tools.Csv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author David Gilmore
 * @date 10/24/13
 */
@Component
public class GenerateTestData {
    @Autowired
    private DataSource impalaDataSource;
    @Autowired
    private JdbcTemplate impalaTemplate;

    public void generateAllTablesByDatabase(String database) {
        List<String> tables = getAllTablesByDatabase(database);
        for(String table: tables)
            generateTestData(database,table);
    }

    public List<String> getAllTablesByDatabase(String database) {

        List<String> tables = new LinkedList<>();
        try {
            DatabaseMetaData md = impalaDataSource.getConnection().getMetaData();
            ResultSet rs = md.getTables(null, database, "%", null);
            while (rs.next()) {
                tables.add(rs.getString(3));
            }
        } catch (SQLException e) {}

        return tables;
    }

    public void generateTestData(String database, String table) {
        String query = "SELECT * FROM " + database +
            "." + table + " LIMIT 10";
        try {
            ResultSet rs = getResultSet(query);
            new Csv().write("data/" + database + "." + table + ".csv", rs, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getResultSet(String query) throws SQLException {
        Connection conn = impalaDataSource.getConnection();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
