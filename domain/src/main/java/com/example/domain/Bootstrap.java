package com.example.domain;

import com.example.domain.model.CryptoCurrency;
import com.example.domain.model.Portfolio;
import com.example.domain.repository.CryptoCurrencyRepository;
import com.example.domain.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;

@SpringBootConfiguration
@EnableAutoConfiguration
@RequiredArgsConstructor
public class Bootstrap {
    private final PortfolioRepository portfolioRepository;

    private final CryptoCurrencyRepository cryptoRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        portfolioRepository.deleteAll();
        cryptoRepository.deleteAll();
        final CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin");
        final CryptoCurrency litecoin = new CryptoCurrency("LTC", "Litecoin");
        cryptoRepository.save(bitcoin);
        cryptoRepository.save(litecoin);
        portfolioRepository.save(new Portfolio("user", new ArrayList<>()));
    }
}
