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
import java.time.Instant;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project extends AuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("category")
    @Enumerated(EnumType.STRING)
    private ProjectCategory category;

    @Column("description")
    private String description;

    @Column("country")
    private String country;

    @Column("postcode")
    private Integer postcode;

    @Column("expected_no_of_hours")
    private Integer expectedNoOfHours;

    @Column("due_date_time")
    private Instant dueDateTime;

    @Column("summary")
    private String summary;

    @Column("budget")
    private Long budget;

    @Column("status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column("assigned_bid_id")
    private Long assignedBidId;


}
