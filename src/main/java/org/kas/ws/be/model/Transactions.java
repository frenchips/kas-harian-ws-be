package org.kas.ws.be.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "tb_transaction")
@Getter
@Setter
public class Transactions extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Categories categoriesId;

    @Column(name = "income", nullable = false)
    private Integer income;

    @Column(name = "expend", nullable = true)
    private Integer expend;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "description", nullable = false, length = 255)
    private String description;


    @Column(name = "transaction_date")
    private Timestamp transactionDate;

}
