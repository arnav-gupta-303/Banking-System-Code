package App;

import domain.Account;
import service.BankService;
import service.impl.BankServiceconsoleimpl;

import java.awt.*;
import java.lang.classfile.constantpool.DoubleEntry;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.out.println("Welcome to Console Bank");
        BankService bankService=new BankServiceconsoleimpl();
        boolean  running=true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("""
                        1) Open Account
                        2) Deposit
                        3) Withdraw
                        4) Transfer
                        5) Account Statement
                        6) List Accounts
                        7) Search Accounts by Customer Name
                        8) Display Transactions
                        0) Exit
                """);
            System.out.print("CHOOSE : ");
            String choice =scanner.nextLine().trim();
            System.out.println("CHOOSEN : "+ choice);
            switch (choice){
                case "0" -> running=false;
                case "1" -> OpenAccount(scanner,bankService);
                case "2" -> Deposit(scanner,bankService);
                case "3" -> Withdraw(scanner,bankService);
                case "4" -> Transfer(scanner,bankService);
                case "5" -> AccountStatement(scanner,bankService);
                case "6" -> listAccounts(scanner,bankService);
                case "7" -> searchAccountByCustomerName(scanner,bankService);
            }
        }
    }

    private static void OpenAccount(Scanner scanner,BankService bankService) {
        boolean checkforcorrectvalues=true;
        System.out.println("Enter Your name : ");
        String name=scanner.nextLine().trim();
        System.out.println("Enter Your Email : ");
        String email=scanner.nextLine().trim();
        String type = "";
        while(checkforcorrectvalues){
            System.out.println("Enter account type (SAVINGS/CURRENT) : ");
            type=scanner.nextLine().trim().toUpperCase();
            if(type.equals("CURRENT") || type.equals("SAVINGS")){
                checkforcorrectvalues=false;
            }
        }
        System.out.println("Initial deposit (Optional, blank for 0) : ");
        String amountstr=scanner.nextLine().trim();
        Double DepositAmount=Double.valueOf(amountstr);
        String Account_No = bankService.openAccount(name, email, type);
        System.out.println("Account Opened : "+Account_No);
        if(DepositAmount>0){
            bankService.deposit(Account_No,DepositAmount,"Initial Deposit");
        }
    }

    private static void Deposit(Scanner scanner,BankService bankService) {
        System.out.println("Enter your Account Number : ");
        String accountNo=scanner.nextLine().trim();
        System.out.println("Enter the amount to be deposited : ");
        Double AmountToBeDeposited=Double.valueOf(scanner.nextLine().trim());
        bankService.deposit(accountNo,AmountToBeDeposited,"Deposit");
        System.out.println("Deposited");
    }

    private static void Withdraw(Scanner scanner,BankService bankService) {
        System.out.println("Enter your AccountNo : ");
        String AccountNo=scanner.nextLine().trim();
        System.out.println("Enter the Amount to withdraw : ");
        Double amount=Double.valueOf(scanner.nextLine().trim());
        bankService.withdraw(AccountNo,amount);
    }

    private static void Transfer(Scanner scanner,BankService bankService) {
        System.out.println("From Account: ");
        String from = scanner.nextLine().trim();
        System.out.println("To Account: ");
        String to = scanner.nextLine().trim();
        System.out.println("Amount: ");
        Double amount = Double.valueOf(scanner.nextLine().trim());
        bankService.transfer(from, to, amount, "Transfer");
    }

//    private static void AccountStatement(Scanner scanner) {
//    }

    private static void listAccounts(Scanner scanner,BankService bankService) {
        bankService.ListAccounts().forEach(a->{
            System.out.println(a.getAccountNo() + " | "+a.getAccountType()+" | "+a.getBalance());
        });
    }

    private static void searchAccountByCustomerName(Scanner scanner,BankService bankService) {
        System.out.println("Enter Customer name : ");
        String name=scanner.nextLine().trim();
        bankService.searchAccountByCustomerName(name);
    }

    private static void AccountStatement(Scanner scanner,BankService bankService) {
        System.out.println("Enter the AccountNo to see transaction : ");
        String AccountNo=scanner.nextLine().trim();
        bankService.AccountStatement(AccountNo);
    }
}
