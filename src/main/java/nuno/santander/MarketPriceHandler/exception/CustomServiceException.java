package nuno.santander.MarketPriceHandler.exception;

public class CustomServiceException extends RuntimeException {

    private static final long serialVersionUID = -5701375666458851268L;

    public CustomServiceException() {
        super("Service error");
        printStackTrace();
    }
}
