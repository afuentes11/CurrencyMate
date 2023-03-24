package main.java.controller;

import main.java.domain.Currency;

import java.util.ArrayList;

public interface CurrencyConverter {

    String CURRENCIES_FILE = "src/main/resource/data/currencies.json";
    String EXCHANGES_FILE = "src/main/resource/data/exchange.json";

    ArrayList<String> listCurrencyName();

    boolean updateExchangeRates();

    String getLastUpdateDate();

    double converter(String firstCurrency, String secondCurrency, double value);

    Currency getCurrencyByName(String currencyName);

}
