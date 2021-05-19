package nuno.santander.MarketPriceHandler.consumer;

import nuno.santander.MarketPriceHandler.model.Price;
import nuno.santander.MarketPriceHandler.repository.PriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceMessageConsumerImplTest {

    @Autowired
    private PriceMessageConsumer consumer;

    @Autowired
    private PriceRepository priceRepository;

    @Test
    public void shouldSavePrice() {

        String csvPrice = "1, EUR/USD, 1.1900,1.2450,01-06-2020 12:01:01:001";
        consumer.onMessage(csvPrice);
        List<Price> findAll = priceRepository.findAll();

        assertNotNull(findAll);
        assertThat(findAll.size(), is(1));

    }
}
