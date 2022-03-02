package itmo.soa.baseservice.exception;


public class NoSuchStatusException extends CustomException {
    public NoSuchStatusException(){
        super("no such status " );
    }
}

