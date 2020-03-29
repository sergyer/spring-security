package com.example.domain.service;

import com.example.domain.dto.ListTransactionsDto;
import com.example.domain.dto.PortfolioPositionsDto;

public interface PortfolioQueryService {

    PortfolioPositionsDto getPortfolioPositions(String username);

    ListTransactionsDto getPortfolioTransactions(String username);

}
