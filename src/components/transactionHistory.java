package components;
/// CLASSES ///
import java.sql.Connection;
import java.sql.PreparedStatement;
import util.DBConnection;
///////////////
import java.util.*;
import java.lang.*;
import java.sql.*;
import java.text.*;

public class transactionHistory
{

    public List<List<String>> getStructuredHistoryByAccountId(int accountId)
    {
        List<List<String>> history = new ArrayList<>();
        String sql = "SELECT id, type, amount, date FROM transaction_history WHERE account_id = ? ORDER BY date DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                Timestamp date = rs.getTimestamp("date");

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

                String formattedDate = dateFormat.format(date);
                String formattedTime = timeFormat.format(date);

                history.add(Arrays.asList(
                        String.valueOf(id),            // Transaction ID
                        type,                          // Transaction Type
                        String.format("₺%.2f", amount),// Transaction Amount
                        formattedDate,                 // Transaction Date
                        formattedTime                  // Transaction Time
                ));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return history;
    }
}
