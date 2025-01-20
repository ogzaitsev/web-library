package com.ogzaitsev.library.service;

import com.ogzaitsev.library.dto.CreateBookDto;
import com.ogzaitsev.library.dto.BookDto;
import com.ogzaitsev.library.entity.Book;
import com.ogzaitsev.library.entity.BorrowedBook;
import com.ogzaitsev.library.entity.Client;
import com.ogzaitsev.library.repository.BookRepository;
import com.ogzaitsev.library.repository.BorrowingsRepository;
import com.ogzaitsev.library.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> findAll(Long lastId, int limit) {
        List<Book> page = bookRepository.findAll(lastId, limit);
        List<BookDto> resultList = page.stream()
                .map(BookDto::of)
                .collect(Collectors.toList());
        return resultList;
    }

    public void create(CreateBookDto book) {
        Book entity = new Book();
        entity.setAuthor(book.getAuthor());
        entity.setIsbn(book.getIsbn());
        entity.setTitle(book.getTitle());
        bookRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Book> bookEntity = bookRepository.findById(id);
        bookEntity.ifPresent(bookRepository::delete);
    }

    public Optional<BookDto> findById(Long bookId) {
        return bookRepository.findById(bookId).map(BookDto::of);
    }

    @Transactional
    public Optional<BookDto> update(Long id, BookDto bookDto) {
        return bookRepository.findById(id)
                .map(entity -> {
                    entity.setTitle(bookDto.getTitle());
                    entity.setIsbn(bookDto.getIsbn());
                    entity.setAuthor(bookDto.getAuthor());
                    return entity;
                })
                .map(bookRepository::saveAndFlush)
                .map(BookDto::of);
    }

}
