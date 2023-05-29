package com.store.demoa.facade.impl;

import com.store.demoa.facade.PromotionFacade;
import com.store.demoa.service.PromotionService;
import com.store.demoa.service.dto.BookDTO;
import com.store.demoa.service.dto.PromotionDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PromotionFacadeImpl implements PromotionFacade {
    private final PromotionService promotionService;

    public PromotionFacadeImpl(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @Override
    public boolean isPromotionApplicable(String bookType, String promotionCode) {
        return promotionService.findByCodeAndBookType(promotionCode, bookType).isPresent();
    }


    @Override
    public double applyDiscount(BookDTO bookDTO, String promotionCode) {
        Optional<PromotionDTO> promotionDTO = promotionService.findByCodeAndBookType(promotionCode, bookDTO.getType());
        return promotionDTO.map(dto -> bookDTO.getPrice() - (bookDTO.getPrice() * dto.getPercentage())).orElse(0.0);
    }

}
