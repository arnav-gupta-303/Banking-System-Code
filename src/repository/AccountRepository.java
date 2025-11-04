package repository;

import domain.Account;

import java.util.*;

public class AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();
    //   Here String is the AccountNo and Account is the class
    public void save(Account account){
        accounts.put(account.getAccountNo(),account);
    }


    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    public Optional<Account> findtheaccount(String accountNo) {
        return Optional.ofNullable(accounts.get(accountNo));
    }
    public Optional<Account> findtheaccountByName(String name) {
//        return Optional.ofNullable(accounts.containsValue());
        for (Account value : accounts.values()) {
            if(value.getName().equalsIgnoreCase(name)){
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}

