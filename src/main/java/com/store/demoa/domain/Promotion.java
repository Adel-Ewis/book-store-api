package com.store.demoa.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A Promotion.
 */
@Entity
@Table(name = "promotion")
public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "code")
    private String code;

    @Column(name = "percentage")
    private Double percentage;
    @Column(name = "book_type")
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
        return "Promotion{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", percentage=" + percentage +
                ", bookType='" + bookType + '\'' +
                '}';
    }
}
