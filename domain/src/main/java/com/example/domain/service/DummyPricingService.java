package com.example.domain.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class DummyPricingService implements PricingService {

	@Override
	public BigDecimal getCurrentPriceForCrypto(String symbol) {
		return new BigDecimal("12000.00");
	}

}
