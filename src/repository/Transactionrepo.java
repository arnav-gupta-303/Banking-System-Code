package repository;

import domain.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transactionrepo {
    private static final Map<String, List<Transaction>> txbyaccount=new HashMap<>();
    //String used here as a key in txbaccount is the ID initialized in Transaction class.
    public static void add(Transaction transaction,String AccountNo) {
        List<Transaction> transactions = txbyaccount.computeIfAbsent(AccountNo, k -> new ArrayList<>());
        transactions.add(transaction);

    }

    public static void showTransactions(String accountNo) {
        List<Transaction> tx=txbyaccount.get(accountNo);
        for(Transaction transaction:tx){
            System.out.println("ID: " + transaction.getID() +
                    " | Type: " + transaction.getType() +
                    " | Amount: " + transaction.getAmount() +
                    " | Time: " + transaction.getTime());
        }
//        for(Map.Entry<String, List<Transaction>> entries : txbyaccount.entrySet()){
//            System.out.println("Transaction ID : "+entries.getKey()+);
//        }
    }
}
