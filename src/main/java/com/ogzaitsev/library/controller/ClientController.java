package com.ogzaitsev.library.controller;

import com.ogzaitsev.library.dto.CreateClientDto;
import com.ogzaitsev.library.dto.ClientDto;
import com.ogzaitsev.library.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("")
    public String showClientsPage(
            @RequestParam(required = false) Long lastClientId,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {

        List<ClientDto> clientsList = clientService.findAll(lastClientId, pageSize);

        // for pagination
        Long nextClientId = clientsList.isEmpty() ? null : clientsList.get(clientsList.size() - 1).getId();

        model.addAttribute("clientsList", clientsList);
        model.addAttribute("nextClientId", nextClientId); // for pagination
        model.addAttribute("pageSize", pageSize);
        return "clients";
    }

    @GetMapping("add")
    public String showNewClientForm() {
        return "clients_add";
    }

    @PostMapping("")
    public String addClient(@ModelAttribute("client") CreateClientDto client) {
        clientService.create(client);
        return "redirect:/clients";
    }

    @GetMapping("{id}")
    public String showClientDetails(@PathVariable("id") Long id, Model model) {
        return clientService.findById(id)
                .map(client -> {
                    model.addAttribute("client", client);
                    return "client_details";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/update")
    public String updateClient(@PathVariable("id") Long id, @ModelAttribute ClientDto clientDto, Model model) {
        return clientService.update(id, clientDto)
                .map(client -> {
                    model.addAttribute("client", client);
                    return "redirect:/clients/{id}";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
