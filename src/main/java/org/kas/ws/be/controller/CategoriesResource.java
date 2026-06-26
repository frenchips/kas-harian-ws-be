package org.kas.ws.be.controller;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kas.ws.be.dto.request.CategoriesRequest;
import org.kas.ws.be.dto.response.CategoriesResponse;
import org.kas.ws.be.dto.response.WebResponse;
import org.kas.ws.be.service.CategoriesService;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriesResource {

    @Inject
    CategoriesService categoriesService;

    @POST
    public Response create(CategoriesRequest request) {
        // Memanggil service untuk memproses bisnis logis
        CategoriesResponse responseData = categoriesService.createCategories(request);

        WebResponse response = new WebResponse();
        response.setStatus("Success");
        response.setMessage("Succesfully create Categories");
        response.setData(responseData);
        // Mengembalikan HTTP 201 Created bersama dengan data response
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }


}
