package com.smartbidderbatch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectBid extends AuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("bid_type")
    @Enumerated(EnumType.STRING)
    private BidType bidType;

    @Column("bid_amount")
    private Double bidAmount;

    @Column("comments")
    private String comments;

    @Column("bid_status")
    @Enumerated(EnumType.STRING)
    private BidStatus bidStatus;

    @Column("project_id")
    private Long projectId;

}
