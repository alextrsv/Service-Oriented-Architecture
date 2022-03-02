package itmo.soa.baseservice.exception;

public class NoSuchPositionException extends CustomException {
    public NoSuchPositionException(){
        super("no such position " );
    }

}
