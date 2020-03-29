package com.example.domain.service;

import java.math.BigDecimal;

public interface PricingService {

	BigDecimal getCurrentPriceForCrypto(String symbol);
	
}
