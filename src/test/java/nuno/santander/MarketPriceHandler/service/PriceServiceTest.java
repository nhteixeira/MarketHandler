package nuno.santander.MarketPriceHandler.service;

import nuno.santander.MarketPriceHandler.consumer.PriceMessageConsumer;
import nuno.santander.MarketPriceHandler.model.Price;
import nuno.santander.MarketPriceHandler.repository.PriceRepository;
import nuno.santander.MarketPriceHandler.service.impl.PriceServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceServiceTest {

    @Mock
    PriceRepository priceRepository;

    @InjectMocks
    PriceServiceImpl priceService;

    @Autowired
    private PriceMessageConsumer consumer;

    Price mockedPrice;

    List<Price> mockedList;


    @Before
    public void setup() throws ParseException {

        mockedPrice = new Price("EUR/USD", new BigDecimal("1.1000"), new BigDecimal("1.2000"),
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS").parse("01-06-2020 12:01:01:001"));
        Price mockedPrice2 = new Price("EUR/USD", new BigDecimal("1.1000"), new BigDecimal("1.2000"),
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS").parse("01-06-2020 12:01:01:001"));
        Price mockedPrice3 = new Price("EUR/JPY", new BigDecimal("119.60"), new BigDecimal("119.90"),
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS").parse("01-06-2020 12:01:02:001"));
        Price mockedPrice4 = new Price("GBP/USD", new BigDecimal("1.2500"), new BigDecimal("1.2560"),
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS").parse("01-06-2020 12:01:02:001"));
        Price mockedPrice5 = new Price("GBP/USD", new BigDecimal("1.2500"), new BigDecimal("1.2560"),
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS").parse("01-06-2020 12:01:02:100"));

        String csvPrice = "1, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001";
        String csvPrice2 = "2, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:001";
        String csvPrice3 = "3, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:001";
        String csvPrice4 = "5, GBP/USD, 1.3500,1.3560,01-06-2020 12:02:02:100";
        String csvPrice5 = "4, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:100";

        consumer.onMessage(csvPrice);
        consumer.onMessage(csvPrice2);
        consumer.onMessage(csvPrice3);
        consumer.onMessage(csvPrice4);
        consumer.onMessage(csvPrice5);
        mockedList = new ArrayList<>();
        Collections.addAll(mockedList, mockedPrice2, mockedPrice3, mockedPrice4, mockedPrice5);
        priceRepository.saveAll(mockedList);

    }

    @Test
    public void shouldReturnLastPriceOfInstrument() {
        String mockedInstrumentNameGBPUSD = "GBP/USD";
        Mockito.when(priceRepository.findByInstrumentName(mockedInstrumentNameGBPUSD)).thenReturn(Optional.ofNullable(mockedList.get(3)));
        Optional<Price> result = priceRepository.findByInstrumentName(mockedInstrumentNameGBPUSD);
        Assert.assertEquals(result, Optional.ofNullable(mockedList.get(3)));
        System.out.println(result);
    }
}
