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

    public List<Transactions> searchPaginatedNative(String search, int page, int size) {
        int offset = page * size;
        String sql = """
                SELECT t.* FROM tb_transaction t
                LEFT JOIN mst_categories c ON t.category_id = c.id
                WHERE ( :searchValue IS NULL OR :searchValue = '' )
                   OR ( UPPER(t.description) LIKE UPPER('%' || :searchValue || '%') )
                   OR ( UPPER(c.category_name) LIKE UPPER('%' || :searchValue || '%') )
                   OR ( UPPER(c.type) LIKE UPPER('%' || :searchValue || '%') )
                ORDER BY t.transaction_date DESC, t.id DESC
                LIMIT :size OFFSET :offset
                """;
        Query query = entityManager.createNativeQuery(sql, Transactions.class);
        query.setParameter("size", size);
        query.setParameter("offset", offset);
        query.setParameter("searchValue", search);
        return query.getResultList();
    }

    public long countSearchNative(String search) {
        String sql = """
                SELECT COUNT(*) FROM tb_transaction t
                LEFT JOIN mst_categories c ON t.category_id = c.id
                WHERE ( :searchValue IS NULL OR :searchValue = '' )
                   OR ( UPPER(t.description) LIKE UPPER('%' || :searchValue || '%') )
                   OR ( UPPER(c.category_name) LIKE UPPER('%' || :searchValue || '%') )
                   OR ( UPPER(c.type) LIKE UPPER('%' || :searchValue || '%') )
                """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("searchValue", search);
        return ((Number) query.getSingleResult()).longValue();
    }

    public Integer getTotalPemasukan() {
        String sql = "SELECT COALESCE(SUM(income), 0) FROM tb_transaction";
        Query query = entityManager.createNativeQuery(sql);
        return ((Number) query.getSingleResult()).intValue();
    }

    public Integer getTotalPengeluaran() {
        String sql = "SELECT COALESCE(SUM(expend), 0) FROM tb_transaction";
        Query query = entityManager.createNativeQuery(sql);
        return ((Number) query.getSingleResult()).intValue();
    }

    public Integer getSaldoTerakhir() {
        String sql = "SELECT amount FROM tb_transaction ORDER BY id DESC LIMIT 1";
        Query query = entityManager.createNativeQuery(sql);
        List<?> result = query.getResultList();
        if (result.isEmpty()) {
            return 0;
        }
        return ((Number) result.get(0)).intValue();
    }
}
