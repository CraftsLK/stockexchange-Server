package com.rumesh.stockexchange.company;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CompanyService {

    PrintWriter writerObj1 =null;

    @Autowired
    private final CompanyRepo companyRepo;

    public CompanyService(CompanyRepo companyRepo) {this.companyRepo = companyRepo;
    }
    public List<Company> findAll() {return companyRepo.findAll();
    }
    public Object findById(Integer id) {return companyRepo.findCompanyById(id)
            .orElseThrow(() -> new CompanyNotFoundException("Company by id" + id + " was not found"));
    }
    public Company update(Company company) {
        return companyRepo.save(company);
    }
    public Company create(Company company) {
        return companyRepo.save(company);
    }
    public void deleteById(Integer id) {
        companyRepo.deleteById(Math.toIntExact(id));
    }
    public ResponseEntity<Company> findBySymbol(String symbol) {
        Optional<Company> items = companyRepo.findCompanyBySymbol(symbol);
        if (items.isPresent()) {return new ResponseEntity<>(items.get(), HttpStatus.OK);
        } else                 {return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<HttpStatus> getPrice() {
        getPriceEvent();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public void getPriceEvent() {

        List<Company> companies = new ArrayList<>(companyRepo.findAll());
        for (Company company : companies) {
            String companySymbol = company.getSymbol();{
                try {
                    String urlString = quoteURL(companySymbol);
                    String results = httpRequest(urlString);

                    JSONObject alphaVantage = getAlphavantageData(results);
                    company.setPrice(Float.parseFloat(String.valueOf(alphaVantage.get("sharePrice"))));
                    company.setLastUpdated(String.valueOf(alphaVantage.get("date")));

                } catch (Exception err) {
                    System.out.println("Exception" + err);
                }
            }
            companyRepo.saveAll(companies);
            printJSON(companies);
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

    private void printJSON(List<Company> companies) {
        try {
            PrintWriter output = new PrintWriter("D:\\Projects\\company.json");
            output.printf(String.valueOf(companies));
            output.close();
        }
        catch(Exception e) {
            e.getStackTrace();
        }
    }
    private String quoteURL (String companySymbol){
        return "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + companySymbol +
                "&interval=5min&apikey=NRY4DZTJINT3LLUL"; //TDPJMOR8QZZJBTAY
    }

    private JSONObject getAlphavantageData(String response) throws ParseException {
        JSONParser alpha = new JSONParser();
        JSONObject json = (JSONObject) alpha.parse(response);
        String date = (String) ((JSONObject) json.get("Meta Data")).get("3. Last Refreshed");
        //String price = (String) ((JSONObject) json.get("Time Series (5min)")).get("4. close"); //old
        JSONObject sharePrice = (JSONObject) ((JSONObject) json.get("Time Series (5min)")).get(date);
        String price = String.valueOf(sharePrice.get("4. close")); //new
   // System.out.println(price); //old
        JSONObject data = new JSONObject();

        data.put("date", date);
        data.put("sharePrice", price);
       // data.put("sharePrice", sharePrice.get("4. close")); //new
        return data;

    }


}