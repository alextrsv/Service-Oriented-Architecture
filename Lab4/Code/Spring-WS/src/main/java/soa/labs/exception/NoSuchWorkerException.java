package soa.labs.exception;

public class NoSuchWorkerException extends Exception {
    String description;

    public NoSuchWorkerException(String id) {
        super("no user with id= " + id);
        String desk = "no user with id= " + id;
    }
}
