package nuno.santander.MarketPriceHandler.controller;

import nuno.santander.MarketPriceHandler.consumer.impl.PriceMessageConsumerImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    private PriceController priceController;

    @Autowired
    private PriceMessageConsumerImpl consumer;

    @Before
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();

    }

    @Test
    public void shouldGetPriceByInstrumentName() throws Exception {



        mockList().forEach(csv -> consumer.onMessage(csv));

        mockMvc.perform(get("/price/GBP-USD").contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instrumentName", is("GBP/USD")))
                .andExpect(jsonPath("$.externalId", is(5)))
                .andExpect(jsonPath("$.bid", is(1.2150)))
                .andExpect(jsonPath("$.ask", is(1.4916)))
                .andExpect(jsonPath("$.date", is("01-06-2020 12:02:02:100")));

    }

    protected List<String> mockList() {
        return asList("1, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001",
                "2, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:001",
                "3, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:001",
                "5, GBP/USD, 1.3500,1.3560,01-06-2020 12:02:05:100",
                "4, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:03:100");
    }

}
