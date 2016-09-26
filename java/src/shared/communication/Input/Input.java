package shared.communication;

public interface Input {
    public static class InvalidInputException extends Exception { 
        public InvalidInputException() {}
        public InvalidInputException(String msg) { super(msg); }
    }

    boolean valid(String input) throws InvalidInputException;
}
