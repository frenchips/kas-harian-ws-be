package org.kas.ws.be.service;


import org.kas.ws.be.dto.request.SearchTransactionRequest;
import org.kas.ws.be.dto.request.TransactionRequest;
import org.kas.ws.be.dto.response.KasSummaryResponse;
import org.kas.ws.be.dto.response.PaginationResponse;
import org.kas.ws.be.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse createKas(TransactionRequest transactionRequest);
    PaginationResponse<TransactionResponse> getTransactionsPaginated(SearchTransactionRequest searchTransactionRequest);
    KasSummaryResponse getKasSummary();
}
