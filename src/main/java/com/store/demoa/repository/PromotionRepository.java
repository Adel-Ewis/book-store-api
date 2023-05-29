package com.store.demoa.repository;

import com.store.demoa.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, UUID> {
    List<Promotion> findByCode(String code);
    Optional<Promotion> findByCodeAndBookTypeIgnoreCase(String code, String type);
}
