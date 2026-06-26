package org.kas.ws.be.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity extends PanacheEntityBase {

    @Column(name = "create_by", nullable = false, length = 64)
    private String createBy;

    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;

    @Column(name = "update_by", length = 64)
    private String updateBy;

    @Column(name = "update_at")
    private Timestamp udpateAt;

    @Column(name = "record_flag", nullable = false, length = 1)
    private String recordFlag;
}
