package main.java.domain;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Currency {
    private String name;
    private String symbol_native;
    private String code;
    private double value;
    private static String lastUpdatedAt = null;

    public Currency(String name, String symbol_native, String code, double value) {
        this.name = name;
        this.symbol_native = symbol_native;
        this.code = code;
        this.value = value;
    }

    public static String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public static void setLastUpdatedAt(String lastUpdatedAt) {
        String isoDate = lastUpdatedAt;

        Instant instant = Instant.parse(isoDate);

        ZonedDateTime utcDateTime = instant.atZone(ZoneId.of("UTC"));

        ZonedDateTime colombiaDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("America/Bogota"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String colombiaDateStr = colombiaDateTime.format(formatter);

        Currency.lastUpdatedAt = colombiaDateStr;
    }

    public String getName() {
        return name;
    }

    public String getSymbol_native() {
        return symbol_native;
    }

    public String getCode() {
        return code;
    }

    public double getValue() {
        return value;
    }

    public double currencyExchange(Currency currency, double value){
        return (value * currency.getValue())/this.value;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", symbol_native='" + symbol_native + '\'' +
                ", code='" + code + '\'' +
                ", value=" + value;
    }

}
