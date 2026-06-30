package org.kas.ws.be.service;

import org.kas.ws.be.dto.request.CategoriesRequest;
import org.kas.ws.be.dto.request.SearchCategoriesRequest;
import org.kas.ws.be.dto.response.CategoriesResponse;
import org.kas.ws.be.dto.response.PaginationResponse;

import java.util.List;

public interface CategoriesService {
    CategoriesResponse createCategories(CategoriesRequest categoriesRequest);
    CategoriesResponse updateCategories(Long id, CategoriesRequest categoriesRequest);
    void deleteCategories(Long id);
    PaginationResponse<CategoriesResponse> getCategoriesPaginated(SearchCategoriesRequest searchCategoriesRequest);
    List<CategoriesResponse> getAllCategories();
}
