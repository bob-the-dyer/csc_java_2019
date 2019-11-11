package ru.compscicenter.java_2019.lesson_09;


import org.hsqldb.util.DatabaseManagerSwing;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.*;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _1_InMemoryDatabaseTest {

    @Test
    public void testsDriver() {
        Stream<Driver> drivers = DriverManager.drivers();
        assertEquals(__, drivers.count());
        assertEquals(__, drivers.findFirst().get().getClass().getName());
    }

    @Test
    public void createTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:myDb", "sa", "sa")) {
            Statement statement = connection.createStatement();
            String tableSql =
                    "CREATE TABLE IF NOT EXISTS employees (" +
                            "emp_id int PRIMARY KEY, " +
                            "name varchar(30)," +
                            "position varchar(30), " +
                            "salary double" +
                            ")";
            assertFalse(statement.execute(tableSql));
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void initTable() {
        createTable();
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:myDb", "sa", "sa")) {
            Statement statement = connection.createStatement();
            String initSql1 =
                    "INSERT INTO employees (emp_id, name, position, salary) " +
                            "VALUES (1, 'john', 'developer', 2000)";
            statement.execute(initSql1);
            String initSql2 =
                    "INSERT INTO employees (emp_id, name, position, salary) " +
                            "VALUES (2, 'lee', 'tester', 1800)";
            statement.execute(initSql2);
            String initSql3 =
                    "INSERT INTO employees (emp_id, name, position, salary) " +
                            "VALUES (3, 'cooper', 'manager', 2200)";
            statement.execute(initSql3);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void selectFromTable() {
        initTable();

        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:myDb", "sa", "sa")) {
            Statement statement = connection.createStatement();
            String selectSql = "SELECT * FROM employees";
            ResultSet resultSet = statement.executeQuery(selectSql);

            StringBuilder ids = new StringBuilder();
            StringBuilder names = new StringBuilder();
            StringBuilder positions = new StringBuilder();
            StringBuilder salaries = new StringBuilder();

            while (resultSet.next()) {
                ids.append(resultSet.getInt("emp_id"));
                names.append(resultSet.getString("name"));
                positions.append(resultSet.getString("position"));
                salaries.append(resultSet.getDouble("salary"));
            }

            assertEquals(__, ids.toString());
            assertEquals(__, names.toString());
            assertEquals(__, positions.toString());
            assertEquals(__, salaries.toString());

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void updateInTable() {
        initTable();
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:myDb", "sa", "sa")) {

            Statement statement = connection.createStatement();
            String updateSql = "UPDATE employees SET position = 'senior developer' WHERE name = 'john'";
            int updated = statement.executeUpdate(updateSql);

            assertEquals(__, updated);

            String selectSql = "SELECT position FROM employees WHERE name = 'john'";
            ResultSet resultSet = statement.executeQuery(selectSql);
            StringBuilder names = new StringBuilder();
            while (resultSet.next()) {
                names.append(resultSet.getString("position"));
            }

            assertEquals(__, names.toString());

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteFromTable() {
        initTable();
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:myDb", "sa", "sa")) {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM employees WHERE salary < ?");
            statement.setDouble(1, 2000D);

            int updated = statement.executeUpdate();

            assertEquals(__, updated);

            PreparedStatement statement2 = connection.prepareStatement("SELECT count(*) FROM employees WHERE salary < ?");
            statement2.setDouble(1, 2000D);

            ResultSet resultSet = statement2.executeQuery();
            StringBuilder count = new StringBuilder();
            while (resultSet.next()) {
                count.append(resultSet.getString(1));
            }

            assertEquals(__, count.toString());

        } catch (SQLException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void metadataFromDatabase() throws InterruptedException {
        initTable();

        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:myDb", "sa", "sa")) {

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet resultSet1 = metaData.getSchemas();

            StringBuilder schemas = new StringBuilder();
            StringBuilder catalogs = new StringBuilder();

            while (resultSet1.next()) {
                schemas.append(resultSet1.getString(1));
                schemas.append("|");
                catalogs.append(resultSet1.getString(2));
                catalogs.append("|");
            }

            assertEquals(__, schemas.toString());
            assertEquals(__, catalogs.toString());

            ResultSet resultSet2 = metaData.getTables("PUBLIC", "PUBLIC", null, null);

            StringBuilder tables = new StringBuilder();

            ResultSetMetaData resultSetMetaData = resultSet2.getMetaData();

            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet2.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    tables.append(resultSetMetaData.getColumnName(i))
                            .append("=>")
                            .append(resultSetMetaData.getColumnTypeName(i))
                            .append("=")
                            .append(resultSet2.getObject(i))
                            .append("|");
                }
                tables.append("\n");
            }

            assertEquals(__, tables.toString());

        } catch (SQLException e) {
            fail(e.getMessage());
        }

        openDbManagerAndWaitFor1min();

        return;
    }

    private void openDbManagerAndWaitFor1min() throws InterruptedException {
        DatabaseManagerSwing.main(
                new String[]{"--url", "jdbc:hsqldb:mem:myDb", "--user", "sa", "--password", "sa"});
        Thread.sleep(1000 * 60);
    }

    @Test
    public void manualTransactionCommit() throws InterruptedException {
        initTable();

        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:myDb", "sa", "sa")) {

            String updatePositionSql = "UPDATE employees SET position=? WHERE emp_id=?";
            PreparedStatement pstmt1 = connection.prepareStatement(updatePositionSql);
            pstmt1.setString(1, "lead developer");
            pstmt1.setInt(2, 1);

            String updateSalarySql = "UPDATE employees SET salary=? WHERE emp_id=?";
            PreparedStatement pstmt2 = connection.prepareStatement(updateSalarySql);
            pstmt2.setDouble(1, 3000D);
            pstmt2.setInt(2, 1);

            boolean autoCommit = connection.getAutoCommit();
            try {
                connection.setAutoCommit(false);
                pstmt1.executeUpdate();
                pstmt2.executeUpdate();
                connection.commit();
            } catch (SQLException exc) {
                connection.rollback();
                throw exc;
            } finally {
                connection.setAutoCommit(autoCommit);
            }

            PreparedStatement pstmt3 = connection.prepareStatement("SELECT position FROM employees WHERE salary = ?");
            pstmt3.setDouble(1, 3000D);

            ResultSet resultSet = pstmt3.executeQuery();
            StringBuilder positions = new StringBuilder();
            while (resultSet.next()) {
                positions.append(resultSet.getString(1));
            }

            assertEquals(__, positions.toString());

        } catch (SQLException e) {
            fail(e.getMessage());
        }

        openDbManagerAndWaitFor1min();
    }

    @Test
    public void manualTransactionRollback() throws InterruptedException {
        initTable();

        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:myDb", "sa", "sa")) {

            String updatePositionSql = "UPDATE employees SET position=? WHERE emp_id=?";
            PreparedStatement pstmt1 = connection.prepareStatement(updatePositionSql);
            pstmt1.setString(1, "lead developer");
            pstmt1.setInt(2, 1);

            String updateSalarySql = "UPDATE employees SET salary=? WHERE emp_id=?";
            PreparedStatement pstmt2 = connection.prepareStatement(updateSalarySql);
            pstmt2.setDouble(1, 3000D);
            pstmt2.setInt(2, 1);

            boolean autoCommit = connection.getAutoCommit();
            try {
                connection.setAutoCommit(false);
                pstmt1.executeUpdate();
                pstmt2.executeUpdate();
                connection.rollback();
            } catch (SQLException exc) {
                connection.rollback();
                throw exc;
            } finally {
                connection.setAutoCommit(autoCommit);
            }

            PreparedStatement pstmt3 = connection.prepareStatement("SELECT position FROM employees WHERE salary = ?");
            pstmt3.setDouble(1, 3000D);

            ResultSet resultSet = pstmt3.executeQuery();
            StringBuilder positions = new StringBuilder();
            while (resultSet.next()) {
                positions.append(resultSet.getString(1));
            }

            assertEquals(__, positions.toString());

        } catch (SQLException e) {
            fail(e.getMessage());
        }

        openDbManagerAndWaitFor1min();
    }

}
