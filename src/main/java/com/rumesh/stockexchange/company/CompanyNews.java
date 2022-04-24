package com.rumesh.stockexchange.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyNews {
    private String title;
    private String content;
    private String date;
}
