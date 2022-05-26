package com.company.service;


import com.company.dto.ClientDTO;
import com.company.dto.reponse.ClientResponseDTO;
import com.company.entity.ClientEntity;
import com.company.enums.EntityStatus;
import com.company.exceptions.AppBadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientDTO create(ClientResponseDTO dto) {
        String substring = dto.getPhone().substring(4);
        if (!dto.getPhone().startsWith("+998") || substring.length() != 9) {
            throw new AppBadRequestException("phone number not valid!");
        }

        Optional<ClientEntity> optional = clientRepository.findByPhone(dto.getPhone());
        if (optional.isPresent()) {
            throw new AppBadRequestException("item all ready exists!");
        }

        ClientEntity entity = new ClientEntity();
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setStatus(EntityStatus.ACTIVE);
        entity.setSurname(dto.getSurname());
        clientRepository.save(entity);

        return toDTO(entity);
    }

    public ClientDTO update(String phone, ClientResponseDTO dto) {

        String substring = phone.substring(4);

        if (!phone.startsWith("+998") || substring.length() != 9) {
            throw new AppBadRequestException("phone number not valid!");
        }

        ClientEntity entity = clientRepository.findByPhone(phone).orElseThrow(() -> {
            throw new ItemNotFoundException("cleint not found!");
        });


        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        clientRepository.save(entity);

        return toDTO(entity);
    }

    public Boolean changeStatus(String phone, EntityStatus status) {
        String substring = phone.substring(4);

        if (!phone.startsWith("+998") || substring.length() != 9) {
            throw new AppBadRequestException("phone number not valid!");
        }

        ClientEntity entity = clientRepository.findByPhone(phone).orElseThrow(() -> {
            throw new ItemNotFoundException("cleint not found!");
        });

        clientRepository.updateStatus(entity.getUuid(), status);

        return true;
    }

    public ClientDTO toDTO(ClientEntity entity) {
        ClientDTO dto = new ClientDTO();
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus());
        dto.setUuid(entity.getUuid());
        dto.setSurname(entity.getSurname());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }

}
