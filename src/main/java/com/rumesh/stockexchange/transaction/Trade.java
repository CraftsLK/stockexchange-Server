package com.rumesh.stockexchange.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

    private Float numShares;
    private String companySymbol;
  //  private String date;
   // private Integer userId;
    private Float price;
   // private Float priceInUsd;
    private String currencyCode;
    private String type;
}

