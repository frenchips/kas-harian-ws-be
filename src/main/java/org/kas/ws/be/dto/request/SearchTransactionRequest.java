package org.kas.ws.be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchTransactionRequest {
    private String search;
    private Integer offset;
    private Integer size;
}
