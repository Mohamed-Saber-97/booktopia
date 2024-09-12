package error;

public class InsufficientStock extends Exception{
    public InsufficientStock() {
    }

    public InsufficientStock(String message) {
        super(message);
    }
}
