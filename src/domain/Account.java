package domain;

public class Account    {
    private String accountNo;
    private String CustomerID;
    private Double balance;//Using Double Wrapper class not double
    private String accountType;
    private String name;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Account(String name,String accountNo, String customerID, Double balance, String accountType) {
        this.accountNo = accountNo;
        CustomerID = customerID;
        this.balance = balance;
        this.accountType = accountType;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
