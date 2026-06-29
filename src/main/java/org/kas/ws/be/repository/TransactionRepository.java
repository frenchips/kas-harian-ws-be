package org.kas.ws.be.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.kas.ws.be.model.Transactions;

import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transactions> {

    @Inject
    EntityManager entityManager;

    public List<Transactions> findPaginatedNative(int page, int size) {
        int offset = page * size;
        String sql = "SELECT * FROM tb_transaction ORDER BY id DESC LIMIT :size OFFSET :offset";
        Query query = entityManager.createNativeQuery(sql, Transactions.class);
        query.setParameter("size", size);
        query.setParameter("offset", offset);
        return query.getResultList();
    }

    public long countNative() {
        String sql = "SELECT COUNT(*) FROM tb_transaction";
        Query query = entityManager.createNativeQuery(sql);
        return ((Number) query.getSingleResult()).longValue();
    }
}
