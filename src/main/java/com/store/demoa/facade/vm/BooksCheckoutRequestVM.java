package com.store.demoa.facade.vm;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;

public class BooksCheckoutRequestVM {
    @Valid
    @Size(min = 1, max = 100,message = "books must be between 1 and 100")
    private List<BookItemVM> books;
    private String promotion;


    public List<BookItemVM> getBooks() {
        return books;
    }

    public void setBooks(List<BookItemVM> books) {
        this.books = books;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return "BooksCheckoutRequestVM{" +
                "books=" + books +
                ", promotion='" + promotion + '\'' +
                '}';
    }
}
