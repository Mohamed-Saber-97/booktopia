package error;

public class InsufficientFunds extends Exception{
    public InsufficientFunds() {
    }

    public InsufficientFunds(String message) {
        super(message);
    }
}
