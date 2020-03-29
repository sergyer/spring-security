package com.example.domain.service;

import com.example.domain.dto.CryptoCurrencyDto;
import com.example.domain.dto.PortfolioPositionsDto;
import com.example.domain.dto.PositionDto;
import com.example.domain.model.CryptoCurrency;
import com.example.domain.model.Portfolio;
import com.example.domain.model.Transaction;
import com.example.domain.repository.PortfolioRepository;
import com.example.domain.service.CurrencyQueryService;
import com.example.domain.service.PortfolioQueryServiceNoSql;
import com.example.domain.service.PricingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.domain.model.Type.BUY;
import static com.example.domain.model.Type.SELL;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PortfolioServiceTest {

    @Mock
    private PortfolioRepository portfolioRepostiory;
    @Mock
    private PricingService pricingService;
    @Mock
    private CurrencyQueryService currencyService;
    @InjectMocks
    private PortfolioQueryServiceNoSql portfolioService;
    Portfolio portfolio = null;
    private List<CryptoCurrencyDto> cryptos = new ArrayList<>();
    private CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin");
    private CryptoCurrency litecoin = new CryptoCurrency("LTC", "Litecoin");

    @Before
    public void setup() {
        cryptos.clear();
        cryptos.add(new CryptoCurrencyDto(bitcoin.getSymbol(), bitcoin.getName()));
        cryptos.add(new CryptoCurrencyDto(litecoin.getSymbol(), litecoin.getName()));
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(new Transaction(bitcoin, BUY, new BigDecimal("3.1"), new BigDecimal("15000.00"), System.currentTimeMillis()));
        transactions.add(new Transaction(litecoin, BUY, new BigDecimal("20.1"), new BigDecimal("200.00"), System.currentTimeMillis()));
        transactions.add(new Transaction(litecoin, BUY, new BigDecimal("200.1"), new BigDecimal("100.00"), System.currentTimeMillis()));
        transactions.add(new Transaction(litecoin, SELL, new BigDecimal("201.1"), new BigDecimal("150.00"), System.currentTimeMillis()));
        portfolio = new Portfolio("snakamoto", transactions);
    }

    @Test
    public void testGetPortfolioPositions() {
        when(currencyService.getSupportedCryptoCurrencies()).thenReturn(cryptos);
        when(portfolioRepostiory.findByUsername("snakamoto")).thenReturn(portfolio);
        when(pricingService.getCurrentPriceForCrypto(Mockito.anyString())).thenReturn(BigDecimal.TEN);
        PortfolioPositionsDto repsonse = portfolioService.getPortfolioPositions("snakamoto");

        PositionDto position = repsonse.getPositionForCrypto(new CryptoCurrencyDto(bitcoin.getSymbol(), bitcoin.getName()));
        assertEquals(0, BigDecimal.valueOf(3.1).compareTo(position.getQuantity()));
        assertEquals(0, BigDecimal.valueOf(31).compareTo(position.getValue()));
        position = repsonse.getPositionForCrypto(new CryptoCurrencyDto(litecoin.getSymbol(), litecoin.getName()));
        assertEquals(0, BigDecimal.valueOf(19.1).compareTo(position.getQuantity()));
        assertEquals(0, BigDecimal.valueOf(191).compareTo(position.getValue()));
    }

    @Test
    public void testAddAndDeleteTransaction() {
        Transaction transaction = new Transaction(bitcoin, BUY, new BigDecimal("3.6"), new BigDecimal("1500.00"), System.currentTimeMillis());
        assertFalse(this.portfolio.getTransactions().contains(transaction));
        this.portfolio.addTransaction(transaction);
        assertEquals(5, portfolio.getTransactions().size());
        assertTrue(this.portfolio.getTransactions().contains(transaction));
        this.portfolio.deleteTransaction(transaction);
        assertEquals(4, portfolio.getTransactions().size());
        assertFalse(this.portfolio.getTransactions().contains(transaction));
    }

}
