package com.example.domain.service;

import com.example.domain.dto.AddTransactionToPortfolioDto;

public interface PortfolioCommandService {

	void addTransactionToPortfolio(AddTransactionToPortfolioDto request, String username);

	void removeTransactionFromPortfolio(String transactionId, String username);
	
}
