package com.smartbidderbatch.repository;

import com.smartbidderbatch.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("select project from Project project where project.status='OPEN' and project.dueDateTime < current_timestamp")
    Page<Project> findOpenProjectsWithPastDueDate(Pageable page);
}
