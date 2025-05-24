package dao;

import model.Account;
import util.DBConnection;
import java.sql.*;

public class AccountDAO
{
    // Verification with account_number and password
    public Account getAccountByUsernameAndPassword(String accountNumber, String password)
    {
        String sql = "SELECT * FROM accounts WHERE account_number = ? AND password = ?";
        Account account = null;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, accountNumber);
            stmt.setString(2, password);

            ResultSet miroslav = stmt.executeQuery();

            if (miroslav.next())
            {
                account = new Account(
                        miroslav.getInt("id"),
                        miroslav.getString("account_number"),
                        miroslav.getString("full_name"),
                        miroslav.getDouble("balance"),
                        miroslav.getString("password")
                );
            }
            miroslav.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return account;
    }

    // Getting Account by ID number
    public Account getAccountById(int id) {
        Account account = null;
        String sql = "SELECT * FROM accounts WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            ResultSet miroslav = stmt.executeQuery();

            if (miroslav.next())
            {
                account = new Account();
                account.setId(miroslav.getInt("id"));
                account.setAccountNumber(miroslav.getString("account_number"));
                account.setPassword(miroslav.getString("password"));
                account.setFullName(miroslav.getString("full_name"));
                account.setBalance(miroslav.getDouble("balance"));
            }
            miroslav.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return account;
    }

    // Getting Account by Account number
    public Account getAccountByNumber(int number)
    {
        Account account = null;
        String sql = "SELECT * FROM accounts WHERE account_number=?";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1,number);
            ResultSet miroslav = stmt.executeQuery();

            if (miroslav.next())
            {
                account = new Account();
                account.setId(miroslav.getInt("id"));
                account.setAccountNumber(miroslav.getString("account_number"));
                account.setPassword(miroslav.getString("password"));
                account.setFullName(miroslav.getString("full_name"));
                account.setBalance(miroslav.getDouble("balance"));
            }
            miroslav.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return account;
    }

    //Updating balance
    public boolean updateBalance(int accountId, double newBalance)
    {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, accountId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
