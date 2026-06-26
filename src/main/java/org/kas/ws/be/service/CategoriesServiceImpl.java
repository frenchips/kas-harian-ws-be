package org.kas.ws.be.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.kas.ws.be.dto.request.CategoriesRequest;
import org.kas.ws.be.dto.response.CategoriesResponse;
import org.kas.ws.be.model.Categories;
import org.kas.ws.be.repository.CategoriesRepository;

import java.sql.Timestamp;

@ApplicationScoped
public class CategoriesServiceImpl implements CategoriesService{

    @Inject
    CategoriesRepository categoriesRepository;

    @Override
    @Transactional
    public CategoriesResponse createCategories(CategoriesRequest categoriesRequest) {
        Categories saveData = saveCategories(categoriesRequest);

        CategoriesResponse response  = new CategoriesResponse();
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
    public CategoriesResponse updateCategories(Long id, CategoriesRequest categoriesRequest) {
        Categories categories = updateData(id, categoriesRequest);

        CategoriesResponse response = new CategoriesResponse();
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


}
