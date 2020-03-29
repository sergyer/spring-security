package com.example.domain.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ListTransactionsDto {

	private final String username;
	private final List<TransactionDetailsDto> transactions;
	
}
