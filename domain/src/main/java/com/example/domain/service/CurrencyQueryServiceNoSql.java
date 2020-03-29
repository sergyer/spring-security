package com.example.domain.service;

import com.example.domain.dto.CryptoCurrencyDto;
import com.example.domain.model.CryptoCurrency;
import com.example.domain.repository.CryptoCurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CurrencyQueryServiceNoSql implements CurrencyQueryService {

	private final CryptoCurrencyRepository cryptoCurrencyRepository;
	private Map<String, CryptoCurrencyDto> cryptoCurrencies = null;

	@Override
	public List<CryptoCurrencyDto> getSupportedCryptoCurrencies() {
		if(this.cryptoCurrencies == null) {
			this.cryptoCurrencies = new HashMap<>();
			for(CryptoCurrency currency : cryptoCurrencyRepository.findAll()) {
				this.cryptoCurrencies.put(currency.getSymbol(), new CryptoCurrencyDto(currency.getSymbol(), currency.getName()));
			}
		}
		return new ArrayList<>(cryptoCurrencies.values());
	}

	@Override
	public CryptoCurrencyDto getCryptoCurrency(String symbol) {
		return cryptoCurrencies.get(symbol);
	}
	
}
