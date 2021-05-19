package nuno.santander.MarketPriceHandler.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Price implements Serializable {

    private static final long serialVersionUID = 3766471097161739599L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String instrumentName;
    private BigDecimal bid;
    private BigDecimal ask;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss:SSS", timezone="Europe/Lisbon")
    private Date timestamp;

    public Price() {
    }

    public Price(String instrumentName, BigDecimal bid, BigDecimal ask, Date timestamp) {
        this.instrumentName = instrumentName;
        this.bid = bid;
        this.ask = ask;
        this.timestamp = timestamp;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", instrumentName='" + instrumentName + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", timestamp=" + timestamp +
                '}';
    }
}
