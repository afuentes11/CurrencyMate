package main.java.model;

import main.java.domain.Currency;
import main.java.exceptions.ApiRequestException;
import main.java.exceptions.DataReadException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DataAccessImpl implements DataAccess{
    /*
        This is the api used for the application, when you register and get the API_KEY,
        assign it as an environment variable in your IDE, or paste it directly in the code
        (this last option is not recommended).
        https://app.currencyapi.com/
    */
    private static final String API_KEY = System.getenv("API_KEY");

    @Override
    public boolean updateExchangeData(String resourceName) throws ApiRequestException {

        try {
            URL url = new URL("https://api.currencyapi.com/v3/latest");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("apikey", API_KEY);
            connection.connect();

            StringBuilder response = new StringBuilder();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                connection.disconnect();
                throw new ApiRequestException("Exception when using API: " + responseCode);
            }else{
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
            connection.disconnect();

            JSONObject jsonResponse = new JSONObject(response.toString());

            FileWriter fileWriter = new FileWriter(resourceName);
            fileWriter.write(jsonResponse.toString(4));
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiRequestException("Exception when using API");
        }
        return false;
    }

    @Override
    public ArrayList<Currency> readData(String resourceNameCurrency, String resourceNameExchange) throws DataReadException {

        JSONObject dataCurrencies = null;
        JSONObject dataExchange = null;
        ArrayList<Currency> result = new ArrayList<>();
        try {
            String contentCurrencies = new String(Files.readAllBytes(Paths.get(resourceNameCurrency)), StandardCharsets.UTF_8);
            dataCurrencies = new JSONObject(contentCurrencies);

            String contentExchange = new String(Files.readAllBytes(Paths.get(resourceNameExchange)), StandardCharsets.UTF_8);
            dataExchange = new JSONObject(contentExchange);

            for (String code : dataCurrencies.getJSONObject("data").keySet()){
                JSONObject dataCurrency = dataCurrencies.getJSONObject("data").getJSONObject(code);
                Double value = dataExchange.getJSONObject("data").getJSONObject(code).getDouble("value");

                result.add(new Currency(dataCurrency.getString("name"),dataCurrency.getString("symbol_native"),code, value));
            }

            Currency.setLastUpdatedAt(dataExchange.getJSONObject("meta").getString("last_updated_at"));

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataReadException("Exception reading information");
        }
        return result;
    }
}
