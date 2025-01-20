package com.ogzaitsev.library.controller;

import com.ogzaitsev.library.dto.CreateBookDto;
import com.ogzaitsev.library.dto.BookDto;
import com.ogzaitsev.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public String showBooksPage(
            @RequestParam(required = false) Long lastBookId,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {
        List<BookDto> bookDtoList = bookService.findAll(lastBookId, pageSize);

        // for pagination
        Long nextBookId = bookDtoList.isEmpty() ? null : bookDtoList.get(bookDtoList.size() - 1).getId();

        model.addAttribute("booksList", bookDtoList);
        model.addAttribute("nextBookId", nextBookId); // for pagination
        return "books";
    }
    @GetMapping("add")
    public String showNewBookForm() {
        return "books_add";
    }

    @PostMapping("")
    public String addBook(@ModelAttribute("book") CreateBookDto book) {
        bookService.create(book);
        return "redirect:/books";
    }

    @PostMapping("{id}/delete")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("{id}")
    public String showBookDetails(@PathVariable("id") Long id, Model model) {
        return bookService.findById(id)
                .map(book -> {
                    model.addAttribute("book", book);
                    return "book_details";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/update")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute BookDto bookDto, Model model) {
        return bookService.update(id, bookDto)
                .map(book -> {
                    model.addAttribute("book", book);
                    return "redirect:/books/{id}";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
