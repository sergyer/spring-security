package com.example.domain.repository;

import com.example.domain.model.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {

    Portfolio findByUsername(String username);
}
