package com.example.domain.repository;

import com.example.domain.model.SupportQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SupportQueryRepository extends MongoRepository<SupportQuery, String> {

    List<SupportQuery> findByUsername(String username);

}
