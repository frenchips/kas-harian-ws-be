package org.kas.ws.be.dto.response;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public class TransactionResponse {
    private Long categoriesId;
    private Integer amount;
    private String description;
    private Timestamp transactionDate;
}
