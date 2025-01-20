package com.ogzaitsev.library.controller;

import com.ogzaitsev.library.dto.BookDto;
import com.ogzaitsev.library.entity.BorrowedBook;
import com.ogzaitsev.library.service.BookService;
import com.ogzaitsev.library.service.BorrowingsService;
import com.ogzaitsev.library.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class BorrowController {
    private final ClientService clientService;
    private final BookService bookService;
    private final BorrowingsService borrowingsService;

    @Autowired
    public BorrowController(ClientService clientService, BookService bookService, BorrowingsService borrowingsService) {
        this.clientService = clientService;
        this.bookService = bookService;
        this.borrowingsService = borrowingsService;
    }

    @GetMapping("")
    public String showIndexPage() {
        return "redirect:/borrow";
    }

    @GetMapping("borrow")
    public String showBorrowPage() {
        return "borrow";
    }

    @GetMapping("borrow/forName")
    public String searchClient(@RequestParam String name, RedirectAttributes redirectAttributes) {
        return clientService.findByClientName(name)
                .map(client -> "redirect:/borrow/" + client.getId())
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Клиент с указанным ФИО не найден");
                    return "redirect:/borrow";
                });
    }

    @GetMapping("borrow/{id}")
    public String showBookSelectionPage(@PathVariable Long id, Model model,
                                        @RequestParam(required = false) Long lastBookId,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        List<BookDto> booksList = bookService.findAll(lastBookId, pageSize);
        model.addAttribute("booksList", booksList);
        model.addAttribute("clientId", id);
        Long nextBookId = booksList.isEmpty() ? null : booksList.get(booksList.size() - 1).getId();
        model.addAttribute("nextBookId", nextBookId);
        return "borrow_select";
    }

    @PostMapping("/borrow/{clientId}/{bookId}")
    public String borrowBook(@PathVariable Long clientId, @PathVariable Long bookId, RedirectAttributes redirectAttributes) {
        BorrowedBook borrowed = null;
        try {
            borrowed = borrowingsService.borrowBook(clientId, bookId);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/borrow/{clientId}";
    }
}
