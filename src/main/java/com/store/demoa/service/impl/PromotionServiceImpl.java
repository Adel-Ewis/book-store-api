package com.store.demoa.service.impl;

import com.store.demoa.repository.PromotionRepository;
import com.store.demoa.service.PromotionService;
import com.store.demoa.service.dto.PromotionDTO;
import com.store.demoa.service.mapper.PromotionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    public PromotionServiceImpl(PromotionRepository promotionRepository, PromotionMapper promotionMapper) {
        this.promotionRepository = promotionRepository;
        this.promotionMapper = promotionMapper;
    }

    @Override
    public List<PromotionDTO> findByCode(String code) {
        return promotionMapper.toDto(promotionRepository.findByCode(code));
    }

    @Override
    public Optional<PromotionDTO> findByCodeAndBookType(String code, String type) {
        return promotionRepository.findByCodeAndBookTypeIgnoreCase(code, type).map(promotionMapper::toDto);
    }
}
