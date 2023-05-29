package com.store.demoa.facade.vm;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckoutResponseVM {
    private double totalPrice;
    private double totalDiscount;
    private String promotion;
    private Boolean promotionApplicable;
    private List<CheckedOutBookItem> checkedOutBooks;

    public double getTotalPrice() {
        return totalPrice;
    }

    public CheckoutResponseVM(double totalPrice, double totalDiscount) {
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public Boolean getPromotionApplicable() {
        return promotionApplicable;
    }

    public void setPromotionApplicable(Boolean promotionApplicable) {
        this.promotionApplicable = promotionApplicable;
    }

    public List<CheckedOutBookItem> getCheckedOutBooks() {
        return checkedOutBooks;
    }

    public void setCheckedOutBooks(List<CheckedOutBookItem> checkedOutBooks) {
        this.checkedOutBooks = checkedOutBooks;
    }
}
