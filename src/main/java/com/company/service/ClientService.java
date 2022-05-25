package com.company.service;


import com.company.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

}
