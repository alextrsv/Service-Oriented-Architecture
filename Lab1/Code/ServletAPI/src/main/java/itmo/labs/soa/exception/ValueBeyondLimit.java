package itmo.labs.soa.exception;

public class ValueBeyondLimit extends Exception{
    public ValueBeyondLimit(String desc){
        super("BeyondLimit: " + desc);
    }
}
