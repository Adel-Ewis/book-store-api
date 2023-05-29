package com.store.demoa.service;

import com.store.demoa.service.dto.PromotionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link com.store.demoa.domain.Promotion}
 */
public interface PromotionService {
    /**
     * Get list of all promotion by code
     * @param code promotion code
     * @return promotion List
     */
    List<PromotionDTO> findByCode(String code);

    /**
     * Find book code & book type
     * @param code code
     * @param type book type
     * @return Promotion
     */
    Optional<PromotionDTO> findByCodeAndBookType(String code, String type);
}
