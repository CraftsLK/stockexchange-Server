package com.rumesh.stockexchange.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepo
        extends JpaRepository<Company, Integer> {

    void deleteById(Integer id);
    Optional<Company> findCompanyById(Integer id);
    Optional<Company> findCompanyBySymbol(String symbol);
}
