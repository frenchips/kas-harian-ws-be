package org.kas.ws.be.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.kas.ws.be.model.Categories;

@ApplicationScoped
public class CategoriesRepository implements PanacheRepository<Categories> {

}
