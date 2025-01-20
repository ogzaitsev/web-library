package com.ogzaitsev.library.service;

import com.ogzaitsev.library.dto.ApiResponse;
import com.ogzaitsev.library.dto.ClientReadingDto;
import com.ogzaitsev.library.entity.Book;
import com.ogzaitsev.library.entity.BorrowedBook;
import com.ogzaitsev.library.entity.Client;
import com.ogzaitsev.library.repository.BookRepository;
import com.ogzaitsev.library.repository.BorrowingsRepository;
import com.ogzaitsev.library.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowingsService {
    private final BorrowingsRepository borrowingsRepository;
    private final ClientRepository clientRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BorrowingsService(BorrowingsRepository borrowingsRepository, ClientRepository clientRepository, BookRepository bookRepository) {
        this.borrowingsRepository = borrowingsRepository;
        this.clientRepository = clientRepository;
        this.bookRepository = bookRepository;
    }


    @Transactional
    public BorrowedBook borrowBook(Long clientId, Long bookId) {
        Optional<BorrowedBook> borrowedBook = borrowingsRepository.findByClientIdAndBookId(clientId, bookId);
        if (borrowedBook.isPresent()) {
            throw new IllegalArgumentException("Данная книга уже взята этим читателем");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Клиент не найден"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));

        BorrowedBook entity = new BorrowedBook();
        entity.setClient(client);
        entity.setBook(book);
        entity.setBorrowedAt(LocalDateTime.now());
        return borrowingsRepository.save(entity);
    }


    public List<ClientReadingDto> getReadingClients(Long lastId, int pageSize) {
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        List<BorrowedBook> borrowedBooks = borrowingsRepository.findReadingClients(lastId, pageRequest);

        // BorrowedBook into ClientReadingDto
        return borrowedBooks.stream()
                .map(bb -> new ClientReadingDto(
                        bb.getId(),
                        bb.getClient().getName(),
                        bb.getClient().getBirthDate(),
                        bb.getBook().getTitle(),
                        bb.getBook().getAuthor(),
                        bb.getBook().getIsbn(),
                        bb.getBorrowedAt()
                ))
                .collect(Collectors.toList());
    }

}
