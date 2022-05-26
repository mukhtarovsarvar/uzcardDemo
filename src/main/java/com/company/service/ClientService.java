package com.company.service;


import com.company.dto.ClientDTO;
import com.company.dto.request.ClientRequestDTO;
import com.company.entity.ClientEntity;
import com.company.enums.EntityStatus;
import com.company.exceptions.AppBadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientDTO create(ClientRequestDTO dto) {

        check(dto.getPhone());

        Optional<ClientEntity> optional = clientRepository.findByPhone(dto.getPhone());
        if (optional.isPresent()) {
            throw new AppBadRequestException("item all ready exists!");
        }

        String profileName = SecurityContextHolder.getContext().getAuthentication().getName();

        ClientEntity entity = new ClientEntity();
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setStatus(EntityStatus.ACTIVE);
        entity.setSurname(dto.getSurname());
        entity.setProfileName(profileName);
        clientRepository.save(entity);

        return toDTO(entity);
    }

    public ClientDTO update(String phone, ClientRequestDTO dto) {

        check(phone);

        ClientEntity entity = clientRepository.findByPhone(phone).orElseThrow(() -> {
            throw new ItemNotFoundException("cleint not found!");
        });


        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        clientRepository.save(entity);

        return toDTO(entity);
    }

    public Boolean changeStatus(String phone, EntityStatus status) {

        check(phone);

        ClientEntity entity = clientRepository.findByPhone(phone).orElseThrow(() -> {
            throw new ItemNotFoundException("cleint not found!");
        });

        clientRepository.updateStatus(entity.getUuid(), status);

        return true;
    }

    public Boolean changePhone(String phone, String newPhone) {
        check(phone);

        check(newPhone);

        ClientEntity entity = clientRepository.findByPhone(phone).orElseThrow(() -> {
            throw new ItemNotFoundException("cleint not found!");
        });

        clientRepository.updatePhone(phone, newPhone);

        return true;
    }

    public PageImpl<ClientDTO> getList(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ClientEntity> entityPage = clientRepository.findAll(pageable);

        List<ClientDTO> clientDTOS = entityPage.stream().map(this::toDTO).toList();

        return new PageImpl<>(clientDTOS, pageable, entityPage.getTotalElements());
    }

    public ClientDTO getByPhoneNumber(String phoneNumber) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String profileName = authentication.getName();

        if (!profileName.equals("admin")) {
            return toDTO(clientRepository.findByPhoneAndProfileNameAndStatus(phoneNumber, profileName, EntityStatus.ACTIVE).orElseThrow(() -> {
                throw new ItemNotFoundException("client not found!");
            }));
        }

        return toDTO(clientRepository.findByPhone(phoneNumber).orElseThrow(() -> {
            throw new ItemNotFoundException("client not found!");
        }));
    }


    public ClientDTO getById(String id) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String profileName = authentication.getName();

        if (!profileName.equals("admin")) {
            return toDTO(clientRepository.findByUuidAndProfileNameAndStatus(id, profileName, EntityStatus.ACTIVE).orElseThrow(() -> {
                throw new ItemNotFoundException("Cleint not found!");
            }));

        }

        return toDTO(clientRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Cleint not found!");
        }));
    }

    public List<ClientDTO> getProfileClients() {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        return clientRepository.findByProfileNameAndStatus(name, EntityStatus.ACTIVE)
                .stream().map(this::toDTO).toList();
    }

    public void check(String phone) {
        String substring = phone.substring(4);

        if (!phone.startsWith("+998") || substring.length() != 9) {
            throw new AppBadRequestException("phone number not valid!");
        }
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
