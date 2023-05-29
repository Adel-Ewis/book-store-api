package com.store.demoa.web.rest.v1;

import com.store.demoa.service.BookService;
import com.store.demoa.service.dto.BookDTO;
import com.store.demoa.web.rest.exceptions.BadRequestException;
import com.store.demoa.web.rest.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class BookResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);
    private final BookService bookService;

    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<Map<String, List<BookDTO>>> getAllBooks() {
        log.debug("request to get all books");
        List<BookDTO> books = bookService.findAll();
        Map<String, List<BookDTO>> result = new HashMap<>();
        result.put("books", books);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/books")
    public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookDTO book) throws URISyntaxException {
        log.debug("request to save book : {}", book);
        if (Objects.nonNull(book.getId())) {
            throw new BadRequestException("ID_EXISTS", "A new book must not have an ID");
        }
        BookDTO savedBook = bookService.save(book);
        return ResponseEntity.created(new URI("/api/v1/books/" + savedBook.getId()))
                .body(savedBook);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable UUID id) {
        log.debug("request to get book : {}", id);
        BookDTO bookDTO = bookService.findOne(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
        return ResponseEntity.ok(bookDTO);
    }

    @DeleteMapping("books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        log.debug("request to delete book : {}", id);
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable(value = "id", required = false) UUID id,
                                              @RequestBody @Valid BookDTO bookDTO) {
        log.debug("request to update book {}", bookDTO);
        validateRequestForUpdate(id, bookDTO);
        return ResponseEntity.ok(bookService.save(bookDTO));
    }

    @PatchMapping("/books/{id}")
    public ResponseEntity<BookDTO> partiallyUpdateBook(@PathVariable(value = "id", required = false) UUID id,
                                                       @RequestBody BookDTO bookDTO) {
        log.debug("request to partially update book {}", bookDTO);
        validateRequestForUpdate(id, bookDTO);
        BookDTO updatedBook = bookService.partialUpdate(bookDTO)
                .orElseThrow(() -> new NotFoundException(
                        "The specified book id for update was not found"));
        return ResponseEntity.ok(updatedBook);

    }

    private void validateRequestForUpdate(UUID id, BookDTO bookDTO) {
        if (id == null || Objects.isNull(bookDTO.getId())) {
            throw new BadRequestException("ID_MISSING", "Book id must be provided for update");
        }
        if (!Objects.equals(id, bookDTO.getId())) {
            throw new BadRequestException("INVALID_ID", "invalid id");
        }
        if (!bookService.existsById(bookDTO.getId())) {
            throw new NotFoundException("The specified book id for update was not found");
        }
    }


}
