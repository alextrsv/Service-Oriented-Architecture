package itmo.labs.soa.exception;

public class NegativeValueException extends Exception {
    public NegativeValueException(String desc){
        super(desc);
    }
}
