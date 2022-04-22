package com.rumesh.stockexchange.transaction;

import com.rumesh.stockexchange.company.Company;
import com.rumesh.stockexchange.company.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.Optional;


@Service
public class TradeService {

    @Autowired
    private CompanyRepo companyRepo;

    @Transactional
    public ResponseEntity<HttpStatus> trade(Trade item) {
        try {
            Optional<Company> companyOptional = companyRepo.findCompanyBySymbol(item.getCompanySymbol());
            if (companyOptional.isPresent()) {
                Company company = companyOptional.get();
                Float currentShares = company.getAvailShares();

                if ("BUY".equals(item.getType())) {
                    currentShares = currentShares - item.getNumShares();
                }
                else if ("SELL".equals(item.getType())) {
                    currentShares = currentShares + item.getNumShares();
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

               company.setAvailShares(currentShares);
                companyRepo.save(company);
                printTest(company);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private void printTest(Company company) {
        try {

            PrintWriter output = new PrintWriter("D:\\Projects\\company.txt");

            output.append(String.valueOf(company));

            output.close();
        }
        catch(Exception e) {
            e.getStackTrace();
        }
    }


}

