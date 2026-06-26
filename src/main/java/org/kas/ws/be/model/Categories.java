package org.kas.ws.be.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "mst_categories")
@Getter
@Setter
public class Categories extends BaseEntity {


    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "category_name", nullable = false, length = 64)
    private String categoryName;

    @Column(name = "type")
    private String type;
}
