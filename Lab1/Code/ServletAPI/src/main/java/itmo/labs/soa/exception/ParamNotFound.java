package itmo.labs.soa.exception;

public class ParamNotFound extends Exception {
    public ParamNotFound(String desc){
        super("required param isn't present: " + desc);
    }
}
