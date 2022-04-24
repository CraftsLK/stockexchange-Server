package com.rumesh.stockexchange.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "trade")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "numShares", nullable = false)
    private Float numShares;

    @Column(name = "companySymbol", nullable = false)
    private String companySymbol;
    @Column(name = "price", nullable = false)
    private Float price;
    @Column(name = "currencyCode", nullable = false)
    private String currencyCode;
    @Column(name = "type", nullable = false)
    private String type;
}

