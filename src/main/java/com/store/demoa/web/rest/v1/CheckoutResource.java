package com.store.demoa.web.rest.v1;

import com.store.demoa.facade.CheckoutFacade;
import com.store.demoa.facade.vm.BooksCheckoutRequestVM;
import com.store.demoa.facade.vm.CheckoutResponseVM;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CheckoutResource {
    private final Logger log = LoggerFactory.getLogger(CheckoutResource.class);
    private final CheckoutFacade checkoutFacade;

    public CheckoutResource(CheckoutFacade checkoutFacade) {
        this.checkoutFacade = checkoutFacade;
    }

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponseVM> checkout(@RequestBody @Valid BooksCheckoutRequestVM booksCheckoutRequestVM) {
        log.debug("REST request to checkout books : {}", booksCheckoutRequestVM);
        return ResponseEntity.ok(checkoutFacade.checkout(booksCheckoutRequestVM));
    }
}
