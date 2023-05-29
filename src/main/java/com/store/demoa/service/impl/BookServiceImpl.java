package com.store.demoa.service.impl;

import com.store.demoa.domain.Book;
import com.store.demoa.repository.BookRepository;
import com.store.demoa.service.BookService;
import com.store.demoa.service.dto.BookDTO;
import com.store.demoa.service.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Book}
 */
@Service
public class BookServiceImpl implements BookService {

    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        log.debug("Save book : {}", bookDTO);
        Book book = bookMapper.toEntity(bookDTO);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDTO update(BookDTO bookDTO) {
        log.debug("Update book : {}", bookDTO);
        Book book = bookMapper.toEntity(bookDTO);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public Optional<BookDTO> partialUpdate(BookDTO bookDTO) {
        log.debug("partial update book : {}", bookDTO);
        return bookRepository.findById(bookDTO.getId())
                .map(persistedBook -> {
                    bookMapper.partialUpdate(persistedBook, bookDTO);
                    return persistedBook;
                })
                .map(bookRepository::save)
                .map(bookMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDTO> findOne(UUID id) {
        log.debug("find book by id : {}", id);
        return bookRepository.findById(id).map(bookMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findAll() {
        log.debug("find list of all books");
        return bookRepository.findAll().stream().map(bookMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void delete(UUID id) {
        log.debug("delete book by id : {}", id);
        bookRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return bookRepository.existsById(id);
    }
}
