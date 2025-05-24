package components;

import dao.AccountDAO;
import dao.transactionDAO;
import model.Account;

public class moneyTransfer
{
    public static String transfer(Account sender, Account receiver, double amount)
    {
        if(amount <= 0)
        {
            return "Please enter a positive amount.";
        }
        else if (sender.getBalance() < amount)
        {
            return "Insufficient funds in account.";
        }
        //No <<ELSE>> end of this loop, if there is an else, it does mean program will end after this loop by all odds

        double senderNewBalance = sender.getBalance() - amount;
        double receiverNewBalance = receiver.getBalance() + amount;

        AccountDAO dao = new AccountDAO();
        boolean senderUpdate = dao.updateBalance(sender.getId(), senderNewBalance);
        boolean receiverUpdate = dao.updateBalance(receiver.getId(), receiverNewBalance);

        if(senderUpdate && receiverUpdate)
        {
            transactionDAO log = new transactionDAO();

            log.logTransaction(sender.getId(), "Transfer Out",amount);
            log.logTransaction(receiver.getId(), "Transfer in",amount);

            return "Transfer completed succesfully!";
        }
        else
        {
            return "An error occurred during the transfer process.";
        }

    }

}
