package nuno.santander.MarketPriceHandler.controller;

import nuno.santander.MarketPriceHandler.model.Price;
import nuno.santander.MarketPriceHandler.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping("/{instrumentName}")
    public Price getPriceByInstrumentName(@PathVariable String instrumentName) {
        return priceService.getLatestPriceByInstrumentName(instrumentName.replace("-", "/"));
    }
}
