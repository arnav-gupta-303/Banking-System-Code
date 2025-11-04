package CustomException;

public class InsufficientBalanceAccount extends Exception{
    private double amount;
    public InsufficientBalanceAccount(double amount){
        super("You don't have "+amount+" rs in your account");
    }
}
