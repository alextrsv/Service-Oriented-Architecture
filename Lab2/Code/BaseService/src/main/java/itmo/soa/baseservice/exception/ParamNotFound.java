package itmo.soa.baseservice.exception;

public class ParamNotFound extends Exception {
    public ParamNotFound(String desc){
        super("required param isn't present: " + desc);
    }
}
