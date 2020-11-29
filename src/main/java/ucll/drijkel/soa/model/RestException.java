package ucll.drijkel.soa.model;

public class RestException extends RuntimeException {
    public RestException() {}
    public RestException(String message) {
        super(message);
    }
}
