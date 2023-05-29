package com.store.demoa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.demoa.service.BookService;
import com.store.demoa.service.dto.BookDTO;
import com.store.demoa.web.rest.v1.BookResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class, MockitoExtension.class})
 class BookResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @InjectMocks
    private BookResource bookResource;

    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("should return all books")
    void testGetAllBooks() throws Exception {

        List<BookDTO> mockBooks = new ArrayList<>();
        mockBooks.add(new BookDTO(UUID.randomUUID(), "Book 1", "Description 1", "Author 1", "Type 1", 10.0, "ISBN 1"));
        mockBooks.add(new BookDTO(UUID.randomUUID(), "Book 2", "Description 2", "Author 2", "Type 2", 20.0, "ISBN 2"));


        when(bookService.findAll()).thenReturn(mockBooks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.books").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.length()").value(mockBooks.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[0].id").value(mockBooks.get(0).getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[0].name").value(mockBooks.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[0].description").value(mockBooks.get(0).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[0].author").value(mockBooks.get(0).getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[0].type").value(mockBooks.get(0).getType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[0].price").value(mockBooks.get(0).getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[0].isbn").value(mockBooks.get(0).getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[1].id").value(mockBooks.get(1).getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[1].name").value(mockBooks.get(1).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[1].description").value(mockBooks.get(1).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[1].author").value(mockBooks.get(1).getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[1].type").value(mockBooks.get(1).getType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[1].price").value(mockBooks.get(1).getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[1].isbn").value(mockBooks.get(1).getIsbn()));

        verify(bookService, times(1)).findAll();
    }


    @Test
    @DisplayName("should create a new book")
    void testCreateBook() throws Exception {

        BookDTO bookDTO = new BookDTO(null, "New Book", "Description", "Author", "Type", 15.0, "ISBN");


        BookDTO savedBookDTO = new BookDTO(UUID.randomUUID(), bookDTO.getName(), bookDTO.getDescription(), bookDTO.getAuthor(), bookDTO.getType(), bookDTO.getPrice(), bookDTO.getIsbn());
        when(bookService.save(any(BookDTO.class))).thenReturn(savedBookDTO);
        String bookJsonString = mapper.writeValueAsString(bookDTO);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJsonString))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/v1/books/" + savedBookDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(bookDTO.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(bookDTO.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(bookDTO.getType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(bookDTO.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookDTO.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());


        verify(bookService, times(1)).save(any(BookDTO.class));
    }

    @Test
    @DisplayName("should raise bad request when creating a new book with id")
    void testCreateBookWithId() throws Exception {

        BookDTO bookDTO = new BookDTO(UUID.randomUUID(), "New Book", "Description", "Author", "Type", 15.0, "ISBN");
        String bookJsonString = mapper.writeValueAsString(bookDTO);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJsonString))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


        verify(bookService, never()).save(any(BookDTO.class));
    }

    @Test
    @DisplayName("should raise bad request when creating a new book with id")
    void testGetBook() throws Exception {

        UUID bookId = UUID.randomUUID();
        BookDTO bookDTO = new BookDTO(bookId, "Test Book", "Description", "Author", "Type", 19.99, "ISBN");


        when(bookService.findOne(bookId)).thenReturn(Optional.of(bookDTO));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/{id}", bookId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(bookDTO.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(bookDTO.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(bookDTO.getType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(bookDTO.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookDTO.getIsbn()));


        verify(bookService, times(1)).findOne(bookId);
    }

    @Test
    @DisplayName("should raise not found when getting a non-existing book")
    void testGetNonExistingBook() throws Exception {

        UUID bookId = UUID.randomUUID();

        when(bookService.findOne(bookId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/{id}", bookId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(bookService, times(1)).findOne(bookId);
    }

    @Test
    @DisplayName("should get no content when deleting a book")
    void testDeleteBook() throws Exception {

        UUID bookId = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/{id}", bookId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(bookService, times(1)).delete(bookId);
    }

    @Test
    @DisplayName("should update a book")
    void testUpdateBook() throws Exception {

        UUID bookId = UUID.randomUUID();
        BookDTO bookDTO = new BookDTO(bookId, "Updated Book", "Description", "Author", "Type", 29.99, "ISBN");
        String bookJsonString = mapper.writeValueAsString(bookDTO);
        when(bookService.existsById(bookId)).thenReturn(true);
        when(bookService.save(any(BookDTO.class))).thenReturn(bookDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJsonString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(bookDTO.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(bookDTO.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(bookDTO.getType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(bookDTO.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookDTO.getIsbn()));


        verify(bookService, times(1)).save(any(BookDTO.class));
    }

    @Test
    @DisplayName("should partially update a book")
    void testPartiallyUpdateBook() throws Exception {
        UUID bookId = UUID.randomUUID();
        BookDTO bookDTO = new BookDTO(bookId, "Updated Book", "Description", "Author", "Type", 29.99, "ISBN");

        when(bookService.partialUpdate(any(BookDTO.class))).thenReturn(Optional.of(bookDTO));
        when(bookService.existsById(bookId)).thenReturn(true);

        String bookJsonString = mapper.writeValueAsString(bookDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJsonString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDTO.getName()));

        verify(bookService, times(1)).partialUpdate(any(BookDTO.class));
    }

    @Test
    @DisplayName("should raise not found when updating a non-existing book")
    void testUpdateNonExistingBook() throws Exception {

        UUID bookId = UUID.randomUUID();
        BookDTO bookDTO = new BookDTO(bookId, "Updated Book", "Description", "Author", "Type", 29.99, "ISBN");
        String bookJsonString = mapper.writeValueAsString(bookDTO);
        when(bookService.existsById(bookId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJsonString))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
