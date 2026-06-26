package org.kas.ws.be.service;

import org.kas.ws.be.dto.request.CategoriesRequest;
import org.kas.ws.be.dto.response.CategoriesResponse;

public interface CategoriesService {
    CategoriesResponse createCategories(CategoriesRequest categoriesRequest);
}
