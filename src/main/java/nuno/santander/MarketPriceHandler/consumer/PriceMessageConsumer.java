package nuno.santander.MarketPriceHandler.consumer;

public interface PriceMessageConsumer {
    void onMessage(String message);
}
