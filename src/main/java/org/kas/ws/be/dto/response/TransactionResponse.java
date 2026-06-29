package org.kas.ws.be.dto.response;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private Long categoriesId;
    private String categoriesName;
    private Integer income;
    private Integer expand;
    private Integer amount;
    private String description;
    private Timestamp transactionDate;
}
