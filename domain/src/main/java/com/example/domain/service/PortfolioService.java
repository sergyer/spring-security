package com.example.domain.service;

import com.example.domain.dto.AddTransactionToPortfolioDto;
import com.example.domain.dto.ListTransactionsDto;
import com.example.domain.dto.PortfolioPositionsDto;
import com.example.domain.model.CryptoCurrency;
import com.example.domain.model.Portfolio;

import java.util.List;

public interface PortfolioService {

	List<CryptoCurrency> getSupportedCryptoCurrencies();

	Portfolio getPortfolioForUsername(String username);

	PortfolioPositionsDto getPortfolioPositions(String username);

	void addTransactionToPortfolio(AddTransactionToPortfolioDto request);

	ListTransactionsDto getPortfolioTransactions(String username);

	void removeTransactionFromPortfolio(String username, String transactionId);
	
}
