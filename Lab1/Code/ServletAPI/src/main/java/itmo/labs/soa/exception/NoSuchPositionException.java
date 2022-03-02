package itmo.labs.soa.exception;

public class NoSuchPositionException extends Exception {
    public NoSuchPositionException(){
        super("no such position " );
    }
}
