package com.example.domain.dto;

import com.example.domain.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TransactionDetailsDto {

    private String id;
    private String symbol;
    private String type;
    private String quantity;
    private String price;

    public TransactionDetailsDto(final Transaction transaction) {
        this.id = transaction.getId();
		this.symbol = transaction.getCryptoCurrency().getSymbol();
		this.type = transaction.getType().name();
		this.quantity = transaction.getQuantity().toString();
		this.price = transaction.getPrice().toString();
    }
}
