package org.kas.ws.be.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.kas.ws.be.model.Categories;

import java.util.List;

@ApplicationScoped
public class CategoriesRepository implements PanacheRepository<Categories> {

    @Inject
    EntityManager entityManager;

    public List<Categories> findPaginatedNative(int page, int size, String search) {
        int offset = page * size;
        String sql = """
                SELECT * FROM mst_categories
                WHERE ( :searchValue IS NULL OR :searchValue = '' )
                   OR ( UPPER(category_name) LIKE UPPER('%' || :searchValue || '%') )
                   OR ( UPPER(type) LIKE UPPER('%' || :searchValue || '%') )
                ORDER BY create_at DESC, id LIMIT :size OFFSET :offset
                """;
        Query query = entityManager.createNativeQuery(sql, Categories.class);
        query.setParameter("size", size);
        query.setParameter("offset", offset);
        query.setParameter("searchValue", search);
        return query.getResultList();
    }

    public long countNative() {
        String sql = "SELECT COUNT(*) FROM mst_categories";
        Query query = entityManager.createNativeQuery(sql);
        return ((Number) query.getSingleResult()).longValue();
    }
}
