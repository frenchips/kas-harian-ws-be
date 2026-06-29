package org.kas.ws.be.service;

import org.kas.ws.be.dto.request.CategoriesRequest;
import org.kas.ws.be.dto.request.SearchCategoriesRequest;
import org.kas.ws.be.dto.response.CategoriesResponse;
import org.kas.ws.be.dto.response.PaginationResponse;

public interface CategoriesService {
    CategoriesResponse createCategories(CategoriesRequest categoriesRequest);
    CategoriesResponse updateCategories(Long id, CategoriesRequest categoriesRequest);
    PaginationResponse<CategoriesResponse> getCategoriesPaginated(SearchCategoriesRequest searchCategoriesRequest);
}
