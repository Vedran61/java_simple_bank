package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import util.DBConnection;

public class transactionDAO
{

    public void logTransaction(int accountId, String type, double amount)
    {
        String sql = "INSERT INTO transaction_history (account_id, type, amount) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, accountId);
            stmt.setString(2, type);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}