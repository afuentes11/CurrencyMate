package main.java.model;

import main.java.domain.Currency;
import main.java.exceptions.ApiRequestException;
import main.java.exceptions.DataReadException;

import java.util.ArrayList;

public interface DataAccess {

    boolean updateExchangeData(String resourceName) throws ApiRequestException;

    ArrayList<Currency> readData(String resourceNameCurrency, String resourceNameExchange) throws DataReadException;

}
