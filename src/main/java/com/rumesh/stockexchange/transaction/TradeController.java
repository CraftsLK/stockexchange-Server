package com.rumesh.stockexchange.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/trade")
public class TradeController {

    private TradeService service;

    public TradeController(TradeService _service) {
        this.service = _service;
    }

    // buy/sell shares
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> trade(@RequestBody Trade item) {
        return service.trade(item);
    }

    /*@RequestMapping(value = "/persistPerson", method = RequestMethod.POST)
    public ResponseEntity<String> persistPerson(@RequestBody PersonDTO person) {
        String s="shash";
        return ResponseEntity.;
    }

     */

}
