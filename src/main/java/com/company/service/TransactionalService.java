package com.company.service;

import com.company.dto.CardDTO;
import com.company.dto.ClientDTO;
import com.company.dto.TransactionHistoryDTO;
import com.company.dto.request.TransactionDTO;
import com.company.dto.request.TransactionFilterDTO;
import com.company.entity.TransactionHistoryEntity;
import com.company.enums.TransactionalStatus;
import com.company.exceptions.AppBadRequestException;
import com.company.mapper.TransactionInfoMapper;
import com.company.repository.CardRepository;
import com.company.repository.TransactionHistoryRepository;
import com.company.repository.custom.TransactionCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionalService {

    private final TransactionHistoryRepository transactionRepository;

    private final CardService cardService;

    private final CardRepository cardRepository;

    private final ClientService clientService;

    private final TransactionCustomRepository transactionCustomRepository;
    public TransactionHistoryDTO create(TransactionDTO dto) {

        if (dto.getFromCard().equals(dto.getToCard())) {
            throw new AppBadRequestException("from card equals to card");
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        dto.setProfileName(name);

        CardDTO toCard = cardService.getByCardNum(dto.getToCardNumber());

        CardDTO fromCard = cardService.getByCardNum(dto.getFromCardNumber());
        if (fromCard.getBalance() < dto.getAmount()) {
            throw new AppBadRequestException("balance not avaible!");
        }

        if (dto.getAmount() < 0) {
            throw new AppBadRequestException("amaunt!");
        }

        cardRepository.updateBalance(toCard.getBalance() + dto.getAmount(), dto.getToCardNumber());


        cardRepository.updateBalance(fromCard.getBalance() - dto.getAmount(), dto.getFromCardNumber());


        TransactionHistoryEntity entity = new TransactionHistoryEntity();
        entity.setAmount(dto.getAmount());
        entity.setFromCard(dto.getFromCardNumber());
        entity.setProfileName(dto.getProfileName());
        entity.setStatus(TransactionalStatus.SUCCESS);
        entity.setToCard(dto.getToCardNumber());

        transactionRepository.save(entity);

        return toDTO(entity);
    }

    public PageImpl<TransactionHistoryDTO> getByCardNumber(String cardNum, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);


        Page<TransactionHistoryEntity> toCard = transactionRepository.findByToCard(cardNum, pageable);

        Page<TransactionHistoryEntity> fromCard = transactionRepository.findByFromCard(cardNum, pageable);

        List<TransactionHistoryDTO> list = new java.util.ArrayList<>(toCard.stream().map(this::toDTO).toList());

        list.addAll(fromCard.stream().map(this::toDTO).toList());


        return new PageImpl<>(list, pageable, toCard.getTotalElements() + fromCard.getTotalElements());
    }

    public PageImpl<TransactionHistoryDTO> getByClientId(String clientId, int page, int size) {

        List<CardDTO> cardList = cardService.getByClientId(clientId);

        Pageable pageable = PageRequest.of(page, size);

        long totalElements = 0;

        List<TransactionHistoryDTO> list = new java.util.ArrayList<>();

        for (CardDTO dto : cardList) {
            Page<TransactionHistoryEntity> toCard = transactionRepository.findByToCard(dto.getNumber(), pageable);

            Page<TransactionHistoryEntity> fromCard = transactionRepository.findByFromCard(dto.getNumber(), pageable);

            list.addAll(toCard.stream().map(this::toDTO).toList());

            list.addAll(fromCard.stream().map(this::toDTO).toList());


            totalElements = toCard.getTotalElements() + fromCard.getTotalElements();
        }

        return new PageImpl<>(list, pageable, totalElements);
    }

    public PageImpl<TransactionHistoryDTO> getByPhone(String phoneNum, int page, int size) {

        List<CardDTO> cardList = cardService.getByPhone(phoneNum);

        Pageable pageable = PageRequest.of(page, size);

        long totalElements = 0;

        List<TransactionHistoryDTO> list = new java.util.ArrayList<>();

        for (CardDTO dto : cardList) {
            Page<TransactionHistoryEntity> toCard = transactionRepository.findByToCard(dto.getNumber(), pageable);

            Page<TransactionHistoryEntity> fromCard = transactionRepository.findByFromCard(dto.getNumber(), pageable);

            list.addAll(toCard.stream().map(this::toDTO).toList());

            list.addAll(fromCard.stream().map(this::toDTO).toList());


            totalElements = toCard.getTotalElements() + fromCard.getTotalElements();
        }

        return new PageImpl<>(list, pageable, totalElements);
    }
    public PageImpl<TransactionHistoryDTO> getByProfileName(String profileName, int page, int size) {



        Pageable pageable = PageRequest.of(page, size);




        Page<TransactionHistoryEntity> entityPage = transactionRepository.findByProfileName(profileName, pageable);

        List<TransactionHistoryDTO> list = entityPage.stream().map(this::toDTO).toList();

        return new PageImpl<>(list, pageable, entityPage.getTotalElements());
    }

    public List<TransactionDTO> filter(TransactionFilterDTO dto){

        return transactionCustomRepository.filter(dto).stream().map(this::toDTOMapper).toList();

    }




    public TransactionDTO toDTOMapper(TransactionInfoMapper mapper) {
        TransactionDTO dto = new TransactionDTO();

        dto.setId(mapper.getT_id());
        dto.setCash(mapper.getT_amount().toString());
        dto.setStatus(mapper.getT_status());
        dto.setCreatedDate(mapper.getT_created_date());

        dto.setFromCard(new CardDTO(mapper.getCf_id(), cardStars(mapper.getCf_number()),
                new ClientDTO(mapper.getClf_id(), mapper.getClf_name(), mapper.getClf_surname(), mapper.getClf_phone())));

        dto.setToCard(new CardDTO(mapper.getCt_id(), cardStars(mapper.getCt_number()),
                new ClientDTO(mapper.getClt_id(), mapper.getClt_name(), mapper.getClt_surname(), mapper.getClt_phone())));

        return dto;
    }


    public TransactionHistoryDTO toDTO(TransactionHistoryEntity entity) {
        TransactionHistoryDTO dto = new TransactionHistoryDTO();
        dto.setAmount(entity.getAmount());
        dto.setFromCard(cardStars(entity.getFromCard()));
        dto.setStatus(entity.getStatus());
        dto.setProfileName(entity.getProfileName());
        dto.setToCard(cardStars(entity.getToCard()));
        dto.setCreateDate(entity.getCreateDate());

        return dto;
    }


    public String cardStars(String cardNum) {
        return cardNum.substring(0, 4) +
                "********" +
                cardNum.substring(cardNum.length() - 4);
    }
}
