package banking_system;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SQL_Manager {

    private final String fileName;

    public SQL_Manager(String fileName) {
        this.fileName = fileName;
        this.createTable();
    }

    public Connection connect() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(this.fileName);
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void createTable() {

        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "id INTEGER PRIMARY KEY,\n"
                + "number TEXT NOT NULL,\n"
                + "pin TEXT NOT NULL,\n"
                + "balance INTEGER DEFAULT 0\n"
                + ");";
        try (Connection connection = this.connect()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(int id, String number, String pin) {
        String sql = "INSERT INTO card(id, number, pin) VALUES(?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, number);
            pstmt.setString(3, pin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String select(String number, String pin) {
        String sql = "SELECT id, number, pin, balance FROM card WHERE number = ? AND pin = ?";
        String result = null;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, pin);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result = rs.getInt("id") + " " +
                        rs.getString("number") + " " +
                        rs.getString("pin") + " " +
                        rs.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String selectRecipient(String number) {
        String sql = "SELECT id, number, pin, balance FROM card WHERE number = ?";
        String result = null;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result = rs.getInt("id") + " " +
                        rs.getString("number") + " " +
                        rs.getString("pin") + " " +
                        rs.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int updateBalance(String number, int income, boolean plusOrMinus) {
        String sql = plusOrMinus ? "UPDATE card SET balance = balance + ? WHERE number = ?" : "UPDATE card SET balance = balance - ? WHERE number = ?";
        int result = 0;
        try (Connection conn = this.connect();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, income);
            pstm.setString(2, number);
            result = pstm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public int delete(String number) {
        String sql = "DELETE FROM card WHERE number = ?";
        int result = 0;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}