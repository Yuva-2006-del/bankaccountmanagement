package service;

import dao.DatabaseConnection;
import java.sql.*;

public class BankService {

    public boolean createAccount(String name, double initialBalance) {
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO accounts(holder_name, balance) VALUES (?, ?)");
            ps.setString(1, name);
            ps.setDouble(2, initialBalance);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deposit(int accNo, double amount) {
        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(
                "UPDATE accounts SET balance = balance + ? WHERE account_no = ?");
            ps1.setDouble(1, amount);
            ps1.setInt(2, accNo);
            int rows = ps1.executeUpdate();

            if (rows > 0) {
                PreparedStatement ps2 = con.prepareStatement(
                    "INSERT INTO transactions(account_no, type, amount) VALUES (?, 'deposit', ?)");
                ps2.setInt(1, accNo);
                ps2.setDouble(2, amount);
                ps2.executeUpdate();

                con.commit();
                return true;
            }
            con.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean withdraw(int accNo, double amount) {
        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement check = con.prepareStatement(
                "SELECT balance FROM accounts WHERE account_no=?");
            check.setInt(1, accNo);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getDouble(1) >= amount) {
                PreparedStatement ps1 = con.prepareStatement(
                    "UPDATE accounts SET balance = balance - ? WHERE account_no = ?");
                ps1.setDouble(1, amount);
                ps1.setInt(2, accNo);
                ps1.executeUpdate();

                PreparedStatement ps2 = con.prepareStatement(
                    "INSERT INTO transactions(account_no, type, amount) VALUES (?, 'withdraw', ?)");
                ps2.setInt(1, accNo);
                ps2.setDouble(2, amount);
                ps2.executeUpdate();

                con.commit();
                return true;
            }
            con.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getBalance(int accNo) {
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT balance FROM accounts WHERE account_no=?");
            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getTransactions(int accNo) {
        StringBuilder sb = new StringBuilder();
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM transactions WHERE account_no=? ORDER BY txn_time DESC");
            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sb.append("Txn ID: ").append(rs.getInt("txn_id"))
                  .append(", Type: ").append(rs.getString("type"))
                  .append(", Amount: ").append(rs.getDouble("amount"))
                  .append(", Time: ").append(rs.getTimestamp("txn_time"))
                  .append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
