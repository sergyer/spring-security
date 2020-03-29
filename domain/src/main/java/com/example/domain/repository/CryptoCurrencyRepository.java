package com.example.domain.repository;

import com.example.domain.model.CryptoCurrency;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CryptoCurrencyRepository extends MongoRepository<CryptoCurrency, String>{

    CryptoCurrency findBySymbol(String symbol);
}
