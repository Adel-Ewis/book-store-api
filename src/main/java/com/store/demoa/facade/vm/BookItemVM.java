package com.store.demoa.facade.vm;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class BookItemVM {

    @NotNull(message = "id is required")
    private UUID id;
    @Min(value = 1, message = "quantity must be greater than 0")
    private int quantity;
    private String name;

    public BookItemVM() {
    }

    public BookItemVM(UUID id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BookItemVM{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                '}';
    }
}
