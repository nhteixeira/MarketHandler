package nuno.santander.MarketPriceHandler.service.impl;

import nuno.santander.MarketPriceHandler.exception.CustomServiceException;
import nuno.santander.MarketPriceHandler.model.Price;
import nuno.santander.MarketPriceHandler.repository.PriceRepository;
import nuno.santander.MarketPriceHandler.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    private final static int SCALE = 4;

    @Override
    public Price getLatestPriceByInstrumentName(String instrumentName) {

        Optional<Price> opPrice = this.priceRepository.findTopByInstrumentName(instrumentName);
        return opPrice.orElse(new Price());
    }

    public void saveCsvPrice(String strPrice) {

        String[] priceArray = extractPricesFromCsv(strPrice);
        try {
            Price price = new Price();
            price.setInstrumentName(priceArray[1]);
            price.setBid(new BigDecimal(priceArray[2]).setScale(SCALE, RoundingMode.HALF_DOWN));
            price.setAsk(new BigDecimal(priceArray[3]).setScale(SCALE, RoundingMode.HALF_DOWN));
            price.setTimestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS").parse(priceArray[4]));

            Optional<Price> oldPrice = this.priceRepository.findTopByInstrumentName(price.getInstrumentName());

            if (oldPrice.isEmpty() || oldPrice.get().getTimestamp().before(price.getTimestamp())) {
                this.priceRepository.save(calculateAdjustedPrice(price));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String[] extractPricesFromCsv(String priceString) {

        List<String> csvList = Arrays.asList(priceString.split(","));
        if (csvList.size() != 5 || csvList.stream().anyMatch(String::isBlank)) {
            throw new CustomServiceException();
        }
        return csvList.stream().map(String::trim).toArray(String[]::new);
    }

    private Price calculateAdjustedPrice(Price price) {

        BigDecimal spread = new BigDecimal("0.1");
        BigDecimal bidSpread = BigDecimal.ONE.subtract(spread);
        BigDecimal askSpread = BigDecimal.ONE.add(spread);
        price.setBid(price.getBid().multiply(bidSpread).setScale(SCALE, RoundingMode.HALF_DOWN));
        price.setAsk(price.getAsk().multiply(askSpread).setScale(SCALE, RoundingMode.HALF_DOWN));
        return price;
    }
}
