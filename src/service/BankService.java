package service;

import domain.Account;

import java.util.List;

public interface BankService {
    String openAccount(String name,String email,String type);
    List<Account> ListAccounts();

    void deposit(String accountNo, Double amountToBeDeposited, String deposit);

    void AccountStatement(String accountNo);

    void withdraw(String accountNo, Double amount);

    void searchAccountByCustomerName(String name);

    void transfer(String from, String to, Double amount, String transfer);

}