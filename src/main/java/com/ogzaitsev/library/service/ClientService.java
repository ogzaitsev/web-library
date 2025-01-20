package com.ogzaitsev.library.service;

import com.ogzaitsev.library.dto.CreateClientDto;
import com.ogzaitsev.library.dto.ClientDto;
import com.ogzaitsev.library.entity.Client;
import com.ogzaitsev.library.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientDto> findAll(Long lastClientId, int pageSize) {
        List<Client> page = clientRepository.findAll(lastClientId, pageSize);
        List<ClientDto> resultList = page.stream()
                .map(ClientDto::of)
                .collect(Collectors.toList());
        return resultList;
    }

    @Transactional
    public void create(CreateClientDto client) {
        Client entity = new Client();
        entity.setName(client.getName());
        entity.setBirthDate(client.getBirthDate());
        clientRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Client> bookEntity = clientRepository.findById(id);
        bookEntity.ifPresent(clientRepository::delete);
    }

    public Optional<ClientDto> findById(Long id) {
        return clientRepository.findById(id).map(ClientDto::of);
    }

    @Transactional
    public Optional<ClientDto> update(Long id, ClientDto clientDto) {
        return clientRepository.findById(id)
                .map(entity -> {
                    entity.setName(clientDto.getName());
                    entity.setBirthDate(clientDto.getBirthDate());
                    return entity;
                })
                .map(clientRepository::saveAndFlush)
                .map(ClientDto::of);
    }

    public Optional<ClientDto> findByClientName(String clientName) {
        return clientRepository.findClientByName(clientName).map(ClientDto::of);
    }
}
