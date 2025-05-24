package components;

import dao.AccountDAO;
import dao.transactionDAO;
import model.Account;

public class transactions
{

    private static String updateBalance(Account account, double amount, String type)
    {
        double newBalance;

        if(type.equals("deposit"))
        {
            newBalance = account.getBalance() + amount;
        }
        else if(type.equals("withdraw"))
        {
            newBalance = account.getBalance() - amount;
        }
        else
        {
            return "An error occurred while updating balance in the database.";
        }

        account.setBalance(newBalance);

        AccountDAO dao = new AccountDAO();
        transactionDAO transactionDAO = new transactionDAO();

        boolean success = dao.updateBalance(account.getId(), newBalance);

        if (success)
        {
            transactionDAO.logTransaction(account.getId(), type, amount);
            return "Success! New balance: ₺" + newBalance;
        }
        else
        {
            return "An error occurred while updating balance in the database.";
        }
    }

    public static String deposit(Account account, double amount)
    {
        return updateBalance(account, amount, "deposit");
    }

    public static String withdraw(Account account, double amount)
    {
        return updateBalance(account, amount, "withdraw");
    }
}
