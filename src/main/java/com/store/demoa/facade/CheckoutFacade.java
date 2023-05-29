package com.store.demoa.facade;

import com.store.demoa.facade.vm.BooksCheckoutRequestVM;
import com.store.demoa.facade.vm.CheckoutResponseVM;

public interface CheckoutFacade {

    /**
     * This method is used to checkout books and calculate the total price and total discount
     *
     * @param booksCheckoutRequestVM the books to checkout
     * @return the checkout response after calculating the total price and total discount
     */
    CheckoutResponseVM checkout(BooksCheckoutRequestVM booksCheckoutRequestVM);
}
