package com.example.domain.service;

import com.example.domain.dto.AddTransactionToPortfolioDto;
import com.example.domain.model.CryptoCurrency;
import com.example.domain.model.Portfolio;
import com.example.domain.model.Transaction;
import com.example.domain.model.Type;
import com.example.domain.repository.CryptoCurrencyRepository;
import com.example.domain.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class PortfolioCommandServiceNoSql implements PortfolioCommandService {

	private final PortfolioRepository portfolioRepository;
	private final CryptoCurrencyRepository currencyRepository;
	
	@Override
	public void addTransactionToPortfolio(AddTransactionToPortfolioDto request) {
		Portfolio portfolio = portfolioRepository.findByUsername(getUsername());
		Transaction transaction = createTransactionEntity(request);
		portfolio.addTransaction(transaction);
		portfolioRepository.save(portfolio);
	}
	
	@Override
	public void removeTransactionFromPortfolio(String transactionId) {
		Portfolio portfolio = portfolioRepository.findByUsername(getUsername());
		Transaction transacion = portfolio.getTransactionById(transactionId);
		portfolio.deleteTransaction(transacion);
		portfolioRepository.save(portfolio);
	}
	
	private String getUsername() {
//		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		return ((User)principle).getUsername();
		return null;
	}
	
	private Transaction createTransactionEntity(AddTransactionToPortfolioDto request) {
		CryptoCurrency crpytoCurrency = currencyRepository.findBySymbol(request.getCryptoSymbol());
		Type type = Type.valueOf(request.getType());
		BigDecimal quantity = new BigDecimal(request.getQuantity());
		BigDecimal price = new BigDecimal(request.getPrice());
		Transaction transaction = new Transaction(crpytoCurrency, type, quantity, price,System.currentTimeMillis());
		return transaction;
	}

}
