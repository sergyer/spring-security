package com.example.domain.service;


import com.example.domain.dto.CryptoCurrencyDto;

import java.util.List;

public interface CurrencyQueryService {

	List<CryptoCurrencyDto> getSupportedCryptoCurrencies();

	CryptoCurrencyDto getCryptoCurrency(String symbol);
}
