package nuno.santander.MarketPriceHandler.repository;

import nuno.santander.MarketPriceHandler.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    Optional<Price> findByInstrumentName(String instrumentName);

    Optional<Price> findTopByInstrumentName(String instrumentName);

}
