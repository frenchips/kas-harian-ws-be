package org.kas.ws.be.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private Long categoriesId;
    private Integer income;
    private Integer expend;
    private String description;
}
