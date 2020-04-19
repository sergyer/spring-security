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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class PortfolioCommandServiceNoSql implements PortfolioCommandService {

	private final PortfolioRepository portfolioRepository;
	private final CryptoCurrencyRepository currencyRepository;

	@Override
	public void addTransactionToPortfolio(AddTransactionToPortfolioDto request,String username) {
		Portfolio portfolio = portfolioRepository.findByUsername(username).orElse(new Portfolio(username, new ArrayList<Transaction>()));
		Transaction transaction = createTransactionEntity(request);
		portfolio.addTransaction(transaction);
		portfolioRepository.save(portfolio);
	}

	@Override
	public void removeTransactionFromPortfolio(String transactionId,String username) {
		Portfolio portfolio = portfolioRepository.findByUsername(username).orElseThrow(RuntimeException::new);
		Transaction transacion = portfolio.getTransactionById(transactionId);
		portfolio.deleteTransaction(transacion);
		portfolioRepository.save(portfolio);
	}
	
	private Transaction createTransactionEntity(AddTransactionToPortfolioDto request) {
		CryptoCurrency crpytoCurrency = currencyRepository.findBySymbol(request.getCryptoSymbol());
		Type type = Type.valueOf(request.getType());
		BigDecimal quantity = new BigDecimal(request.getQuantity());
		BigDecimal price = new BigDecimal(request.getPrice());
		return new Transaction(crpytoCurrency, type, quantity, price,System.currentTimeMillis());
	}

}
