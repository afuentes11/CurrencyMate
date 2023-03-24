package main.java.controller;

import main.java.domain.Currency;
import main.java.exceptions.ApiRequestException;
import main.java.exceptions.DataReadException;
import main.java.model.DataAccess;
import main.java.model.DataAccessImpl;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CurrencyConverterImpl implements CurrencyConverter{
    private final DataAccess data;
    public CurrencyConverterImpl() {
        this.data = new DataAccessImpl();
    }


    @Override
    public ArrayList<String> listCurrencyName() {
        ArrayList<String> result = new ArrayList<>();
        try {
            ArrayList<Currency> currencies = this.data.readData(CURRENCIES_FILE, EXCHANGES_FILE);
            for (Currency currency : currencies){
                result.add(currency.getName());
            }

        } catch (DataReadException e) {
            System.out.println("Error: the file could not be accessed");
            e.printStackTrace();
        }
        return (ArrayList<String>) result.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateExchangeRates() {
        boolean result = false;
        try {
            this.data.updateExchangeData(EXCHANGES_FILE);
            result = true;
        } catch (ApiRequestException e) {
            System.out.println("Error: failed to call API");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getLastUpdateDate() {
        try {
            this.data.readData(CURRENCIES_FILE, EXCHANGES_FILE);
        } catch (DataReadException e) {
            System.out.println("Error: the file could not be accessed");
            e.printStackTrace();
        }
        return Currency.getLastUpdatedAt();
    }

    @Override
    public double converter(String firstCurrency, String secondCurrency, double value) {

        double result = 0;

        Currency currency1 = getCurrencyByNamePrivateMethod(firstCurrency, this.data);
        Currency currency2 = getCurrencyByNamePrivateMethod(secondCurrency, this.data);
        result = currency1.currencyExchange(currency2, value);

        return result;
    }

    @Override
    public Currency getCurrencyByName(String currencyName) {
        return getCurrencyByNamePrivateMethod(currencyName, this.data);
    }

    private static Currency getCurrencyByNamePrivateMethod(String currencyName, DataAccess data){

        Currency currency = null;
        try {
            ArrayList<Currency> currencies = data.readData(CURRENCIES_FILE, EXCHANGES_FILE);
            currency = currencies
                    .stream()
                    .filter((curr) -> curr.getName().equalsIgnoreCase(currencyName))
                    .findFirst()
                    .orElse(null);
        } catch (DataReadException e) {
            System.out.println("Error: the file could not be accessed");
            e.printStackTrace();
        }
        return currency;
    }

}
