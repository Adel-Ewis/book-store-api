package com.store.demoa.service.mapper;


import com.store.demoa.domain.Book;
import com.store.demoa.service.dto.BookDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookMapper extends EntityMapper<BookDTO, Book> {
}
