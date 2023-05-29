package com.store.demoa.service.dto;

import java.io.Serializable;
import java.util.UUID;



public class PromotionDTO implements Serializable {

    private UUID id;

    private String code;

    private Double percentage;

    private String bookType;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    @Override
    public String toString() {
        return "PromotionDTO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", percentage=" + percentage +
                ", bookType='" + bookType + '\'' +
                '}';
    }
}
