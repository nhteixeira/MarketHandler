package nuno.santander.MarketPriceHandler.service;

import nuno.santander.MarketPriceHandler.model.Price;
import org.springframework.stereotype.Service;

@Service
public interface PriceService {

    Price getLatestPriceByInstrumentName(String instrumentName);

    void saveCsvPrice(String strPrice) throws Exception;
}
