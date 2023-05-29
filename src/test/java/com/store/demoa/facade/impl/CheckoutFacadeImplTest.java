package com.store.demoa.facade.impl;

import com.store.demoa.facade.CheckoutFacade;
import com.store.demoa.facade.PromotionFacade;
import com.store.demoa.facade.vm.BookItemVM;
import com.store.demoa.facade.vm.BooksCheckoutRequestVM;
import com.store.demoa.facade.vm.CheckedOutBookItem;
import com.store.demoa.facade.vm.CheckoutResponseVM;
import com.store.demoa.service.BookService;
import com.store.demoa.service.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
 class CheckoutFacadeImplTest {
    @Mock
    private PromotionFacade promotionFacade;

    @Mock
    private BookService bookService;

    private CheckoutFacadeImpl checkoutFacade;

    @BeforeEach
    public void setUp() {
        checkoutFacade = new CheckoutFacadeImpl(promotionFacade, bookService);
    }

    @Test
    @DisplayName("Test checkout with no promotion")
    void testCheckout_NoPromotion() {
        BooksCheckoutRequestVM requestVM = new BooksCheckoutRequestVM();
        requestVM.setBooks(new ArrayList<>());

        BookItemVM bookItemVM = new BookItemVM();
        bookItemVM.setId(UUID.randomUUID());
        bookItemVM.setQuantity(2);

        requestVM.getBooks().add(bookItemVM);

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(UUID.randomUUID());
        bookDTO.setPrice(10.0);

        when(bookService.findOne(bookItemVM.getId())).thenReturn(Optional.of(bookDTO));
        when(promotionFacade.isPromotionApplicable(anyString(), anyString())).thenReturn(false);

        CheckoutResponseVM responseVM = checkoutFacade.checkout(requestVM);

        // Verify the result
        assertEquals(20.0, responseVM.getTotalPrice());
        assertEquals(0.0, responseVM.getTotalDiscount());
        assertEquals(1, responseVM.getCheckedOutBooks().size());

        CheckedOutBookItem checkedOutBookItem = responseVM.getCheckedOutBooks().get(0);
        assertEquals(2, checkedOutBookItem.getQuantity());
        assertEquals(20.0, checkedOutBookItem.getTotalPrice());
        assertEquals(0.0, checkedOutBookItem.getDiscount());
        assertFalse(checkedOutBookItem.isPromotionApplied());
    }

    @Test
    @DisplayName("Test checkout with valid promotion")
    void testCheckout_WithValidPromotion() {
        BooksCheckoutRequestVM requestVM = new BooksCheckoutRequestVM();
        List<BookItemVM> bookItems = new ArrayList<>();
        UUID book1Id = UUID.randomUUID();
        UUID book2Id = UUID.randomUUID();
        bookItems.add(new BookItemVM(book1Id, 2));
        bookItems.add(new BookItemVM(book2Id, 1));
        requestVM.setBooks(bookItems);
        requestVM.setPromotion("PROMO123");

        BookDTO book1 = new BookDTO();

        book1.setId(book1Id);
        book1.setName("Book 1");
        book1.setType("Type 1");
        book1.setPrice(10.0);

        BookDTO book2 = new BookDTO();

        book2.setId(book2Id);
        book2.setName("Book 2");
        book2.setType("Type 2");
        book2.setPrice(20.0);

        when(bookService.findOne(book1Id)).thenReturn(Optional.of(book1));
        when(bookService.findOne(book2Id)).thenReturn(Optional.of(book2));

        when(promotionFacade.isPromotionApplicable("Type 1", "PROMO123")).thenReturn(true);
        // discount of 20% for book 1
        when(promotionFacade.applyDiscount(book1, "PROMO123")).thenReturn(8.0);

        when(promotionFacade.isPromotionApplicable("Type 2", "PROMO123")).thenReturn(false);

        CheckoutResponseVM responseVM = checkoutFacade.checkout(requestVM);

        assertNotNull(responseVM);
        assertEquals(36.0, responseVM.getTotalPrice());
        assertEquals(4.0, responseVM.getTotalDiscount());
        assertTrue(responseVM.getPromotionApplicable());
        assertEquals("PROMO123", responseVM.getPromotion());
        assertNotNull(responseVM.getCheckedOutBooks());
        assertEquals(2, responseVM.getCheckedOutBooks().size());

        CheckedOutBookItem checkedOutBook1 = responseVM.getCheckedOutBooks().get(0);
        assertEquals(book1Id, checkedOutBook1.getId());
        assertEquals("Book 1", checkedOutBook1.getName());
        assertEquals("Type 1", checkedOutBook1.getType());
        assertEquals(20.0, checkedOutBook1.getPrice());
        assertEquals(16.0, checkedOutBook1.getTotalPrice());
        assertEquals(4.0, checkedOutBook1.getDiscount());
        assertTrue(checkedOutBook1.isPromotionApplied());

        CheckedOutBookItem checkedOutBook2 = responseVM.getCheckedOutBooks().get(1);
        assertEquals(book2Id, checkedOutBook2.getId());
        assertEquals("Book 2", checkedOutBook2.getName());
        assertEquals("Type 2", checkedOutBook2.getType());
        assertEquals(20.0, checkedOutBook2.getPrice());
        assertEquals(20.0, checkedOutBook2.getTotalPrice());
        assertEquals(0.0, checkedOutBook2.getDiscount());
        assertFalse(checkedOutBook2.isPromotionApplied());


        verify(bookService, times(2)).findOne(any());
        verify(promotionFacade, times(2)).isPromotionApplicable(any(), any());
        verify(promotionFacade, times(1)).applyDiscount(book1, "PROMO123");
    }
}
