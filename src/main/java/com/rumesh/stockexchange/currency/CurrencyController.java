package com.rumesh.stockexchange.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/currency") //port defined in application.properties(8081)
public class CurrencyController {
    @Autowired // not necessary to define
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
//get a list of all the currency
    @GetMapping("/get-all")
    public ResponseEntity<List<Currency>> getAllCurrency(){
        List<Currency> currencies = currencyService.findAll();
        return new ResponseEntity<>(currencies, HttpStatus.ACCEPTED);
    }
//get a particular currency details using company id
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Currency> getCurrencyById(@PathVariable("id") Integer id){
        Currency currency = (Currency) currencyService.findById(id);
        return new ResponseEntity<>(currency, HttpStatus.ACCEPTED);
    }

    //update details of an existing currency
    @PutMapping("/update/{id}")
    public ResponseEntity<Currency> updateCurrency(@RequestBody Currency currency) {
        Currency updateCurrency = currencyService.update(currency);
        return new ResponseEntity<>(updateCurrency, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency) {
        Currency updateCurrency;
        updateCurrency = currencyService.create(currency);
        return new ResponseEntity<>(updateCurrency, HttpStatus.OK);
    }
    //delete a currency by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
        currencyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //getting the currency by company symbol(for transaction table)
    @GetMapping("/get-by-symbol/{curCode}")
    public ResponseEntity<Currency> getByCode(@PathVariable String curCode) {
    return currencyService.findByCode(curCode);
    }
    //method to update currency rates

    @GetMapping("/get-rates")
    public ResponseEntity<HttpStatus> updateRates() {
        return currencyService.getRates();
    }
}