package service.impl;

import CustomException.AccountNotFoundException;
import CustomException.InsufficientBalanceAccount;
import domain.Account;
import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.Transactionrepo;
import service.BankService;

import java.time.LocalDateTime;
import java.util.*;

public class BankServiceconsoleimpl implements BankService {
    AccountRepository accountRepository = new AccountRepository();
    Transactionrepo transactionrepo = new Transactionrepo();

    @Override
    public String openAccount(String name, String email, String type) {
        String Customer_ID = UUID.randomUUID().toString();
        String Account_No = getStringAccountNumber();
        Account account = new Account(name, Account_No, Customer_ID, (double) 0, type);
        accountRepository.save(account);
        return Account_No;
    }

    private String getStringAccountNumber() {
        int size = (accountRepository.findAll()).size();
        return String.format("AC%06d", size + 1);
    }

    @Override
    public List<Account> ListAccounts() {
        return accountRepository.findAll().stream()
                .sorted((a, b) -> a.getAccountNo().compareTo(b.getAccountNo())).toList();
    }

    @Override
    public void deposit(String accountNo, Double amountToBeDeposited, String note) {
        try{
            Account DepositingAccount = accountRepository.findtheaccount(accountNo)
                    .orElseThrow(() -> new RuntimeException("Account Not Found : " + accountNo));
            DepositingAccount.setBalance(DepositingAccount.getBalance() + amountToBeDeposited);
            Transaction transaction = new Transaction(UUID.randomUUID().toString(), Type.DEPOSIT, amountToBeDeposited, LocalDateTime.now(), note);
            Transactionrepo.add(transaction, accountNo);}
        catch (AccountNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void AccountStatement(String accountNo) {
        accountRepository.findtheaccount(accountNo)
                .orElseThrow(() -> new RuntimeException("Account Not Found : " + accountNo));
        Transactionrepo.showTransactions(accountNo);

    }

    @Override
    public void withdraw(String accountNo, Double amount) {
        Account AccountFromWhichMoneyToBeWithdraw = accountRepository.findtheaccount(accountNo)
                .orElseThrow(() -> new RuntimeException("Account Not Found : " + accountNo));
        if (AccountFromWhichMoneyToBeWithdraw.getAccountType().equals("CURRENT")) {
            AccountFromWhichMoneyToBeWithdraw.setBalance(AccountFromWhichMoneyToBeWithdraw.getBalance() - amount);
            System.out.println(amount + " Succefully Withdarwn from your account");
            //Transaction
            Transaction transaction = new Transaction(UUID.randomUUID().toString(), Type.WITHDRAW, amount, LocalDateTime.now(), "Withdraw");
            Transactionrepo.add(transaction, AccountFromWhichMoneyToBeWithdraw.getAccountNo());
        } else {
//            if(AccountFromWhichMoneyToBeWithdraw.getBalance()>amount){
//                AccountFromWhichMoneyToBeWithdraw.setBalance(AccountFromWhichMoneyToBeWithdraw.getBalance() - amount);
//            }
//            else{
//                throw
//            }
            try {
                withdrawAmount(amount, AccountFromWhichMoneyToBeWithdraw);
                //Transaction
                Transaction transaction = new Transaction(UUID.randomUUID().toString(), Type.WITHDRAW, amount, LocalDateTime.now(), "Withdraw");
                Transactionrepo.add(transaction, AccountFromWhichMoneyToBeWithdraw.getAccountNo());
            } catch (InsufficientBalanceAccount e) {
//            System.out.println(e.getAmount());
                System.out.println(e);
            }
        }
    }

    @Override
    public void searchAccountByCustomerName(String name) {
        Account account = accountRepository.findtheaccountByName(name).get();
        if (account != null) {
            System.out.println("Found the account");
            System.out.println(account.getAccountNo() + " | " + account.getAccountType() + " | " + account.getBalance() + " | " + account.getName()
                    + " | " + account.getCustomerID());
        } else {
            System.out.println("Account Not Found");
        }
    }

    @Override
    public void transfer(String fromAcc, String toAcc, Double amount, String note) {
        // Check if the source and destination accounts are the same
        if (fromAcc.equals(toAcc)) {
            System.out.println("Transfer Failed: Source and destination accounts are the same. No action taken.");
            return; // Exit the method early
        }

        // 1. Find the accounts
        Account accountFrom = accountRepository.findtheaccount(fromAcc)
                .orElseThrow(() -> new RuntimeException("Source Account Not Found: " + fromAcc));
        Account accountTo = accountRepository.findtheaccount(toAcc)
                .orElseThrow(() -> new RuntimeException("Destination Account Not Found: " + toAcc));

        // 2. Withdraw from the source account
        try {
            // Assuming you have a helper method like this for cleaner logic:
            // processWithdrawal(accountFrom, amount);

            // --- Start of Withdrawal Logic (reusing the logic from the previous response) ---
            // For Savings accounts, check balance before withdrawal
            if (accountFrom.getAccountType().equals("SAVINGS") && accountFrom.getBalance() < amount) {
                throw new InsufficientBalanceAccount(amount);
            }

            // Update balance (assuming Current accounts allow overdraft based on your original withdraw logic)
            accountFrom.setBalance(accountFrom.getBalance() - amount);
            // --- End of Withdrawal Logic ---

            // 3. Deposit to the destination account
            accountTo.setBalance(accountTo.getBalance() + amount);

            // 4. Record transactions
            LocalDateTime transactionTime = LocalDateTime.now();

            // Transaction for the sender (WITHDRAW)
            Transaction transactionFrom = new Transaction(
                    UUID.randomUUID().toString(),
                    Type.WITHDRAW,
                    amount,
                    transactionTime,
                    "Transfer out to " + toAcc + ": " + note
            );
            Transactionrepo.add(transactionFrom, fromAcc);

            // Transaction for the receiver (DEPOSIT)
            Transaction transactionTo = new Transaction(
                    UUID.randomUUID().toString(), // Use a new ID for the deposit transaction
                    Type.DEPOSIT,
                    amount,
                    transactionTime,
                    "Transfer in from " + fromAcc + ": " + note
            );
            Transactionrepo.add(transactionTo, toAcc);

            System.out.println("Successfully transferred " + amount + " from " + fromAcc + " to " + toAcc);

        } catch (InsufficientBalanceAccount e) {
            // If withdrawal failed, deposit is not performed (atomicity)
            System.out.println("Transfer failed: " + e.getMessage());
        } catch (Exception e) {
            // Handle other potential errors (like null pointer if implementation is flawed)
            System.out.println("An unexpected error occurred during transfer: " + e.getMessage());
        }
    }



    private void withdrawAmount(Double amount, Account account) throws InsufficientBalanceAccount {
        if (account.getBalance() < amount) {
            throw new InsufficientBalanceAccount(amount);
        }
        account.setBalance(account.getBalance() - amount);
        System.out.println(amount + " Succesfully Withdrawn from your aaccout");
    }
}


//    public List<Account> ListAccounts() {
//        return accountRepository.findAll().stream()
//                .sorted(Comparator.comparing(Account::getAccountNo))
//                .toList();

//    }


