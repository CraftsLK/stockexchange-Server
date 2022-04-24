package com.rumesh.stockexchange.currency;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CurrencyService {

    @Autowired
    private final CurrencyRepo currencyRepo;

    public CurrencyService(CurrencyRepo currencyRepo) {this.currencyRepo = currencyRepo;
    }
    public List<Currency> findAll() {return currencyRepo.findAll();
    }
    public Object findById(Integer id) {return currencyRepo.findCurrencyById(id)
            .orElseThrow(() -> new CurrencyNotFoundException("Currency by id" + id + " was not found"));
    }
    public Currency update(Currency currency) {
        return currencyRepo.save(currency);
    }
    public Currency create(Currency currency) {
        return currencyRepo.save(currency);
    }
    public void deleteById(Integer id) {
        currencyRepo.deleteById(Math.toIntExact(id));
    }
    public ResponseEntity<Currency> findByCode(String code) {
        Optional<Currency> items = currencyRepo.findCurrencyByCode(code);
        if (items.isPresent()) {return new ResponseEntity<>(items.get(), HttpStatus.OK);
        } else                 {return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
    public ResponseEntity<HttpStatus> getRates() {
        getRatesEvent();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Scheduled(cron= "0 0/10 * ? * *")
    private void getRatesEvent() {
        List<Currency> currencies = new ArrayList<>(currencyRepo.findAll());
        for (Currency currency : currencies) {
            String currencyCurrency = currency.getCode();{
                try {
                    String urlString = ratesURL(currencyCurrency);
                    String results = httpRequest(urlString);

                    JSONObject curConvRS = getCurConvData(results);
                    currency.setRate(Float.parseFloat(String.valueOf(curConvRS.get("rate"))));
                    currency.setLastUpdated(String.valueOf(curConvRS.get("date")));
                } catch (Exception err) {
                    System.out.println("Exception" + err);
                }
            }
            System.out.println("updating currency rates");
            currencyRepo.saveAll(currencies);
            printJSON(currencies);
        }

    }

    private String httpRequest(String urlString) throws IOException {
        URL getUrl = new URL(urlString);
        HttpURLConnection conURL = (HttpURLConnection) getUrl.openConnection();
        conURL.connect();

        BufferedReader input = new BufferedReader(new InputStreamReader(conURL.getInputStream()));
        String inputString;
        StringBuilder builder = new StringBuilder();
        while ((inputString = input.readLine()) != null) {
            builder.append(inputString);
        }
        input.close();
        conURL.disconnect();

        return builder.toString();
    }

    private void printJSON(List<Currency> currencies) {
        try {
            PrintWriter output = new PrintWriter("D:\\Projects\\currency.json");
            output.printf(String.valueOf(currencies));
            output.close();
        }
        catch(Exception e) {
            e.getStackTrace();
        }
    }

    @Contract(pure = true)
    private @NotNull String ratesURL(String code) {
      return "http://localhost:8080/CurConvRS/webresources/exchangeRate?fromCur=USD&toCur="+ code;
    }
    private @NotNull JSONObject getCurConvData(String response) throws ParseException {
        JSONParser curConv = new JSONParser();
        JSONObject json = (JSONObject) curConv.parse(response);
        String date = (String) json.get("date");
        Float rate = Float.parseFloat(String.valueOf(json.get("rate")));

        JSONObject data = new JSONObject();

        data.put("date", date);
        data.put("rate", rate);
        return data;

    }

}