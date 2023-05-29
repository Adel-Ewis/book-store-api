package com.store.demoa.facade.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckedOutBookItem {
    private UUID id;
    private String name;
    private String type;
    private int quantity;
    private double singleBookPrice;
    private double price;
    private double discount;
    private double totalPrice;
    private boolean isPromotionApplied;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSingleBookPrice() {
        return singleBookPrice;
    }

    public void setSingleBookPrice(double singleBookPrice) {
        this.singleBookPrice = singleBookPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPromotionApplied() {
        return isPromotionApplied;
    }

    public void setPromotionApplied(boolean promotionApplied) {
        isPromotionApplied = promotionApplied;
    }
}
