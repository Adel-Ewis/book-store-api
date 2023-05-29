package com.store.demoa.facade.impl;

import com.store.demoa.facade.CheckoutFacade;
import com.store.demoa.facade.PromotionFacade;
import com.store.demoa.facade.vm.BooksCheckoutRequestVM;
import com.store.demoa.facade.vm.CheckedOutBookItem;
import com.store.demoa.facade.vm.CheckoutResponseVM;
import com.store.demoa.service.BookService;
import com.store.demoa.service.dto.BookDTO;
import com.store.demoa.web.rest.exceptions.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CheckoutFacadeImpl implements CheckoutFacade {
    private final PromotionFacade promotionFacade;
    private final BookService bookService;


    public CheckoutFacadeImpl(PromotionFacade promotionFacade, BookService bookService) {
        this.promotionFacade = promotionFacade;
        this.bookService = bookService;
    }


    @Override
    public CheckoutResponseVM checkout(BooksCheckoutRequestVM booksCheckoutRequestVM) {
        CheckoutResponseVM checkoutResponseVM = new CheckoutResponseVM(0.0, 0.0);
        checkoutResponseVM.setCheckedOutBooks(new ArrayList<>());
        double totalInvoicePrice = 0.0;
        double totalInvoiceDiscount = 0.0;

        for (var bookItemVM : booksCheckoutRequestVM.getBooks()) {
            BookDTO bookDTO = bookService.findOne(bookItemVM.getId())
                    .orElseThrow(() -> new NotFoundException("Book not found, book ID : " + bookItemVM.getId()));
            double bookItemPrice = bookDTO.getPrice();
            CheckedOutBookItem checkedOutBookItem = bookDTOtoCheckedOutBookItem(bookDTO);
            checkedOutBookItem.setQuantity(bookItemVM.getQuantity());
            checkedOutBookItem.setPrice(bookDTO.getPrice() * bookItemVM.getQuantity());
            checkedOutBookItem.setDiscount(0.0);
            checkedOutBookItem.setSingleBookPrice(bookDTO.getPrice());
            if (StringUtils.isNoneBlank(booksCheckoutRequestVM.getPromotion())) {
                boolean promotionApplicable = promotionFacade.isPromotionApplicable(bookDTO.getType(),
                        booksCheckoutRequestVM.getPromotion());
                if (promotionApplicable) {
                    bookItemPrice = promotionFacade.applyDiscount(bookDTO, booksCheckoutRequestVM.getPromotion());
                    double totalDiscountPerItems = (bookDTO.getPrice() - bookItemPrice) * bookItemVM.getQuantity();
                    totalInvoiceDiscount += totalDiscountPerItems;
                    checkedOutBookItem.setPromotionApplied(true);
                    checkedOutBookItem.setDiscount(totalDiscountPerItems);
                }
            }
            double totalItemsPrice = bookItemPrice * bookItemVM.getQuantity();
            checkedOutBookItem.setTotalPrice(totalItemsPrice);
            totalInvoicePrice += checkedOutBookItem.getTotalPrice();

            checkoutResponseVM.getCheckedOutBooks().add(checkedOutBookItem);
        }

        if (StringUtils.isNoneBlank(booksCheckoutRequestVM.getPromotion())) {
            boolean isInvoicedPromotionApplicable = checkoutResponseVM.getCheckedOutBooks().stream()
                    .anyMatch(CheckedOutBookItem::isPromotionApplied);
            checkoutResponseVM.setPromotionApplicable(isInvoicedPromotionApplicable);
            checkoutResponseVM.setPromotion(booksCheckoutRequestVM.getPromotion());
        }
        checkoutResponseVM.setTotalPrice(totalInvoicePrice);
        checkoutResponseVM.setTotalDiscount(totalInvoiceDiscount);
        return checkoutResponseVM;

    }

    private CheckedOutBookItem bookDTOtoCheckedOutBookItem(BookDTO bookDTO) {
        CheckedOutBookItem checkedOutBookItem = new CheckedOutBookItem();
        checkedOutBookItem.setId(bookDTO.getId());
        checkedOutBookItem.setName(bookDTO.getName());
        checkedOutBookItem.setType(bookDTO.getType());
        return checkedOutBookItem;
    }

}
