package com.example.domain.service;

import com.example.domain.dto.*;
import com.example.domain.model.Portfolio;
import com.example.domain.model.Transaction;
import com.example.domain.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PortfolioQueryServiceNoSql implements PortfolioQueryService {

    private final CurrencyQueryService currencyService;
    private final PortfolioRepository portfolioRepository;
    private final PricingService pricingService;

    @Override
    public PortfolioPositionsDto getPortfolioPositions(final String username) {
        List<CryptoCurrencyDto> cryptos = currencyService.getSupportedCryptoCurrencies();
        Portfolio portfolio = portfolioRepository.findByUsername(username);
        List<PositionDto> positions = calculatePositions(cryptos, portfolio);
        Map<String, String> cryptoMap = convertCryptoListToMap(cryptos);
        return new PortfolioPositionsDto("", "", positions, cryptoMap);
    }

    @Override
    public ListTransactionsDto getPortfolioTransactions(final String username) {
        Portfolio portfolio = this.portfolioRepository.findByUsername(username);
        List<Transaction> transactions = portfolio.getTransactions();
        return createListTransactionsResponse(username, transactions);
    }

    private List<PositionDto> calculatePositions(List<CryptoCurrencyDto> cryptos, Portfolio portfolio) {
        List<PositionDto> positions = new ArrayList<>();
        for (CryptoCurrencyDto crypto : cryptos) {
            List<Transaction> cryptoTransactions = portfolio.getTransactionsForCoin(crypto.getSymbol());
            BigDecimal quantity = calculatePositionQuantity(cryptoTransactions);
            BigDecimal currentPrice = getCurrentPriceForCrypto(crypto);
            BigDecimal positionValue = calculatePositionValue(quantity, currentPrice);
            PositionDto position = new PositionDto(crypto, quantity, positionValue);
            positions.add(position);
        }
        return positions;
    }

    private BigDecimal getCurrentPriceForCrypto(CryptoCurrencyDto crypto) {
        return pricingService.getCurrentPriceForCrypto(crypto.getSymbol());
    }

    private BigDecimal calculatePositionValue(BigDecimal quantity, BigDecimal currentPrice) {
        return currentPrice.multiply(quantity);
    }

    private Map<String, String> convertCryptoListToMap(List<CryptoCurrencyDto> cryptos) {
        Map<String, String> cryptoMap = new HashMap<>();
        for (CryptoCurrencyDto crypto : cryptos) {
            cryptoMap.put(crypto.getSymbol(), crypto.getName());
        }
        return cryptoMap;
    }

    private BigDecimal calculatePositionQuantity(List<Transaction> cryptoTransactions) {
        BigDecimal quantity = BigDecimal.ZERO;
        for (Transaction transaction : cryptoTransactions) {
            switch (transaction.getType()) {
                case BUY:
                    quantity = quantity.add(transaction.getQuantity());
                    break;
                case SELL:
                    quantity = quantity.subtract(transaction.getQuantity());
                    break;
                default:
                    break;
            }
        }
        return quantity;
    }

    private ListTransactionsDto createListTransactionsResponse(final String username, final List<Transaction> transactions) {
        final List<TransactionDetailsDto> transationDetails = transactions.stream().map(TransactionDetailsDto::new).collect(Collectors.toList());
        return new ListTransactionsDto(username, transationDetails);
    }

}
