package soa.labs.exception;

public class InvalidIDException extends Exception {
    String description = "";

    public InvalidIDException(String element) {
        super(element + " should be integer.");
        String desk = element + " should be integer.";
    }

}
