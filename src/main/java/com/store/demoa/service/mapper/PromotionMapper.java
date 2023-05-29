package com.store.demoa.service.mapper;

import com.store.demoa.domain.Promotion;
import com.store.demoa.service.dto.PromotionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Promotion} and its DTO {@link PromotionDTO}.
 */
@Mapper(componentModel = "spring")
public interface PromotionMapper extends EntityMapper<PromotionDTO, Promotion> {
}
