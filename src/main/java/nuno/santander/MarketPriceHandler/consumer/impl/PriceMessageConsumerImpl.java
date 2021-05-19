package nuno.santander.MarketPriceHandler.consumer.impl;

import nuno.santander.MarketPriceHandler.consumer.PriceMessageConsumer;
import nuno.santander.MarketPriceHandler.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceMessageConsumerImpl implements PriceMessageConsumer {

    @Autowired
    private PriceService priceService;

    @Override
    public void onMessage(String price){
        try {
            priceService.saveCsvPrice(price);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
