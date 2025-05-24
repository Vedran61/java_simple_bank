package model;

public class Account
{
    private int id;
    private String accountNumber;
    private String fullName;
    private double balance;
    private String password;

    public Account()
    {

    }

    public Account(int id, String accountNumber, String fullName, double balance, String password)
    {
        this.id = id;
        this.accountNumber = accountNumber;
        this.fullName = fullName;
        this.balance = balance;
        this.password = password;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", balance=" + balance +
                ", password='" + password + '\'' +
                '}';
    }
}
