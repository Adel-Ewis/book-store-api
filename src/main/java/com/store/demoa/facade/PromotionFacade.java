package com.store.demoa.facade;

import com.store.demoa.service.dto.BookDTO;

public interface PromotionFacade {
    /**
     * Check whether promotion is applicable for the book
     * @param bookType the book type
     * @param promotionCode the promotion code
     * @return true if promotion is applicable for the book
     */
    boolean isPromotionApplicable(String bookType, String promotionCode);

    /**
     * Get book price after applying promotion
     * @param bookDTO the book to apply promotion
     * @param promotionCode the promotion code
     * @return the book price after applying promotion
     */
    double applyDiscount(BookDTO bookDTO, String promotionCode);
}
