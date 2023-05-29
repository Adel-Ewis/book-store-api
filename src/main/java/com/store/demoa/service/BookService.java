package com.store.demoa.service;

import com.store.demoa.service.dto.BookDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for managing {@link com.store.demoa.domain.Book}
 */
public interface BookService {
    /**
     * Save book
     *
     * @param bookDTO the book to save
     * @return the saved book.
     */
    BookDTO save(BookDTO bookDTO);

    /**
     * Update book
     *
     * @param bookDTO the book to update
     * @return the updated book
     */
    BookDTO update(BookDTO bookDTO);

    /**
     * Partially updates a book
     *
     * @param bookDTO the book to update
     * @return the updated book.
     */
    Optional<BookDTO> partialUpdate(BookDTO bookDTO);

    /**
     * Find book by id
     *
     * @param id the id of the book.
     * @return the book.
     */
    Optional<BookDTO> findOne(UUID id);

    /**
     * Find all books
     *
     * @return the list of books
     */
    List<BookDTO> findAll();

    /**
     * Delete book by id
     *
     * @param id the id of the book.
     */
    void delete(UUID id);

    /**
     * Check whether book exists by id
     *
     * @param id the id of the book
     * @return true if book exists false otherwise
     */
    boolean existsById(UUID id);
}
