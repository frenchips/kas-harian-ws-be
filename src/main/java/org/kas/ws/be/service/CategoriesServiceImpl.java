package org.kas.ws.be.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.kas.ws.be.dto.request.CategoriesRequest;
import org.kas.ws.be.dto.request.SearchCategoriesRequest;
import org.kas.ws.be.dto.response.CategoriesResponse;
import org.kas.ws.be.dto.response.PaginationResponse;
import org.kas.ws.be.model.Categories;
import org.kas.ws.be.repository.CategoriesRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoriesServiceImpl implements CategoriesService{

    @Inject
    CategoriesRepository categoriesRepository;

    @Override
    @Transactional
    public CategoriesResponse createCategories(CategoriesRequest categoriesRequest) {
        Categories saveData = saveCategories(categoriesRequest);

        CategoriesResponse response  = new CategoriesResponse();
        response.setId(saveData.getId());
        response.setCategoriesName(saveData.getCategoryName());
        response.setType(saveData.getType());
        return response;
    }

    private Categories saveCategories(CategoriesRequest categoriesRequest){

        Categories categories = new Categories();
        categories.setCategoryName(categoriesRequest.getCategoriesName());
        categories.setType(categoriesRequest.getType());
        categories.setCreateBy("Admin");
        categories.setCreateAt(new Timestamp(System.currentTimeMillis()));
        categories.setRecordFlag("N");

        categoriesRepository.persist(categories);
        return categories;
    }


    @Override
    @Transactional
    public CategoriesResponse updateCategories(Long id, CategoriesRequest categoriesRequest) {
        Categories categories = updateData(id, categoriesRequest);

        CategoriesResponse response = new CategoriesResponse();
        response.setId(categories.getId());
        response.setCategoriesName(categories.getCategoryName());
        response.setType(categories.getType());

        return response;
    }


    private Categories updateData(Long id, CategoriesRequest categoriesRequest){
        Categories categories = categoriesRepository.findById(id);
        if(categories != null){
            categories.setCategoryName(categoriesRequest.getCategoriesName());
            categories.setType(categoriesRequest.getType());
            categories.setUpdateBy("Admin");
            categories.setUdpateAt(new Timestamp(System.currentTimeMillis()));
            categories.setRecordFlag("U");
        }
        return  categories;
    }

    @Override
    public PaginationResponse<CategoriesResponse> getCategoriesPaginated(SearchCategoriesRequest searchCategoriesRequest) {
        List<Categories> categoriesList = categoriesRepository.findPaginatedNative(searchCategoriesRequest.getOffset(), searchCategoriesRequest.getSize(), searchCategoriesRequest.getSearch());

        List<CategoriesResponse> responseList = categoriesList.stream()
                .map(this::toCategoriesResponse)
                .collect(Collectors.toList());

        long totalElements;
        if (searchCategoriesRequest.getSearch() == null || searchCategoriesRequest.getSearch().isEmpty()) {
            totalElements = categoriesRepository.countNative();
        } else {
            totalElements = categoriesRepository.countSearchNative(searchCategoriesRequest.getSearch());
        }
        
        int totalPages = (int) Math.ceil((double) totalElements / searchCategoriesRequest.getSize());
        boolean isLast = (searchCategoriesRequest.getOffset() + 1) >= totalPages;

        return new PaginationResponse<>(responseList, searchCategoriesRequest.getOffset(), searchCategoriesRequest.getSize(), totalElements, totalPages, isLast);
    }

    private CategoriesResponse toCategoriesResponse(Categories categories) {
        CategoriesResponse response = new CategoriesResponse();
        response.setId(categories.getId());
        response.setCategoriesName(categories.getCategoryName());
        response.setType(categories.getType());
        return response;
    }

    @Override
    public List<CategoriesResponse> getAllCategories() {
        List<Categories> categoriesList = categoriesRepository.findAllNative();
        
        return categoriesList.stream()
                .map(this::toCategoriesResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCategories(Long id) {
        Categories categories = categoriesRepository.findById(id);
        if (categories != null) {
            categoriesRepository.delete(categories);
        }
    }
}
