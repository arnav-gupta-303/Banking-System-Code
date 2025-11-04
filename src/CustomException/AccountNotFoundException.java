package CustomException;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String accountNo) {
        super("Account not found with ID: " + accountNo);
    }
}
