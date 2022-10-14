package com.smartbidderbatch.repository;

import com.smartbidderbatch.domain.ProjectBid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectBidRepository extends JpaRepository<ProjectBid,Long> {
    List<ProjectBid> findProjectBidByProjectId(Long projectId);
}
