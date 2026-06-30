package org.kas.ws.be.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kas.ws.be.dto.request.SearchTransactionRequest;
import org.kas.ws.be.dto.request.TransactionRequest;
import org.kas.ws.be.dto.response.KasSummaryResponse;
import org.kas.ws.be.dto.response.PaginationResponse;
import org.kas.ws.be.dto.response.TransactionResponse;
import org.kas.ws.be.dto.response.WebResponse;
import org.kas.ws.be.service.TransactionService;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @Inject
    private TransactionService transactionService;

    @POST
    public Response create(TransactionRequest request) {
        // Memanggil service untuk memproses bisnis logis
        TransactionResponse responseData = transactionService.createKas(request);

        WebResponse response = new WebResponse();
        response.setStatus("Success");
        response.setMessage("Succesfully save kas");
        response.setData(responseData);
        // Mengembalikan HTTP 201 Created bersama dengan data response
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @POST
    @Path("/searchTransaction")
    public Response getPaginated(SearchTransactionRequest request) {
        PaginationResponse<TransactionResponse> responseData = transactionService.getTransactionsPaginated(request);

        WebResponse response = new WebResponse();
        response.setStatus("Success");
        response.setMessage("Successfully retrieve transactions");
        response.setData(responseData);

        return Response.ok(response).build();
    }

    @GET
    @Path("/summary")
    public Response getKasSummary() {
        KasSummaryResponse responseData = transactionService.getKasSummary();

        WebResponse response = new WebResponse();
        response.setStatus("Success");
        response.setMessage("Successfully retrieve kas summary");
        response.setData(responseData);

        return Response.ok(response).build();
    }
}
