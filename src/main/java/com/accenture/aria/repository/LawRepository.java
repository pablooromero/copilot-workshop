package com.accenture.aria.repository;

import com.accenture.aria.model.Law;
import com.accenture.aria.model.LawCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawRepository extends JpaRepository<Law, Long> {
    List<Law> findByCategory(LawCategory category);
}
