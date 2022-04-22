package com.rumesh.stockexchange.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepo
        extends JpaRepository<Currency, Integer> {

    void deleteById(Integer id);
    Optional<Currency> findCurrencyById(Integer id);
    Optional<Currency> findCurrencyByCode(String curCode);
}
