package com.example.domain.dto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class CryptoCurrencyDto {

	private final String symbol;
	private final String name;
	
}
