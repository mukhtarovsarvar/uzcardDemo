package com.company.service;

import com.company.dto.CardDTO;
import com.company.dto.request.AssignPhoneDTO;
import com.company.dto.request.CardFilterDTO;
import com.company.dto.request.CardRequestDTO;
import com.company.entity.CardEntity;
import com.company.entity.SalesCardNumber;
import com.company.enums.EntityStatus;
import com.company.exceptions.AppBadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.CardRepository;
import com.company.repository.SalesCardNumberRepository;
import com.company.repository.custom.CardCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final SalesCardNumberRepository salesNumberRepository;
    private final ClientService clientService;

    private final CardCustomRepository cardCustomRepository;


    public CardDTO create(CardRequestDTO dto) {

        clientService.getById(dto.getClientId());

        CardEntity cardEntity = new CardEntity();
        cardEntity.setBalance(0L);
        cardEntity.setClientId(dto.getClientId());
        cardEntity.setExpDate(LocalDate.now().plusYears(4).toString());
        SalesCardNumber salesCardNumber = salesNumberRepository.findTop1By().get();
        cardEntity.setNumber(salesCardNumber.getCardNumber());
        cardEntity.setStatus(EntityStatus.ACTIVE);

        cardRepository.save(cardEntity);

        salesNumberRepository.deleteById(salesCardNumber.getUuid());

        return toDTO(cardEntity);
    }


    public Boolean changeStatus(String cardNumber, EntityStatus status) {
        Optional<CardEntity> cardEntity = cardRepository.findByNumber(cardNumber);
        if (cardEntity.isEmpty()) {
            throw new ItemNotFoundException("card not found!");
        }

        cardRepository.changeStatus(cardNumber, status);
        return true;
    }

    public static void check(String cardNum) {

        if (!cardNum.startsWith("8600") || cardNum.length() != 16) {
            throw new AppBadRequestException("card number not valid!");
        }

    }

    public Boolean assignPhone(AssignPhoneDTO dto) {
        check(dto.getCardNum());

        ClientService.check(dto.getPhone());

        Optional<CardEntity> cardEntity = cardRepository.findByNumber(dto.getCardNum());

        if (cardEntity.isEmpty()) throw new ItemNotFoundException("Card not found!");

        cardRepository.updatePhone(dto.getPhone(), dto.getCardNum());

        return true;
    }

    public List<CardDTO> getByPhone(String phone) {
        ClientService.check(phone);

        return cardRepository.findByPhoneNUmberAndStatus(phone,EntityStatus.ACTIVE).stream().map(this::toDTO).toList();
    }

    public List<CardDTO> getByClientId(String id) {
        clientService.getById(id);

        return cardRepository.findByClientId(id).stream().map(this::toDTO).toList();
    }


    public List<CardDTO> filter(CardFilterDTO dto){
        return cardCustomRepository.filter(dto).stream().map(this::toDTO).toList();
    }

    public CardDTO getByCardNum(String number) {

        CardEntity cardEntity = cardRepository.findByNumber(number).orElseThrow(() -> {
            throw new ItemNotFoundException("card not found!");
        });

        return toDTO(cardEntity);
    }

    public String getBalanceByCardNumber(String number) {
        String balance = getByCardNum(number).getBalance().toString();

        if (balance.equals("0")) {
            return "0 sum";
        }
        if (balance.length() < 2) {
            return "0,0" + balance + " sum";
        }
        if (balance.length() < 3) {
            return "0," + balance + " sum";
        }

        return balance.substring(0, balance.length() - 2) + " sum";
    }


    public  CardDTO  toDTO(CardEntity entity) {
        CardDTO dto = new CardDTO();
        dto.setBalance(entity.getBalance());
        dto.setClientId(entity.getClientId());
        dto.setNumber(entity.getNumber());
        dto.setExpDate(entity.getExpDate());
        return dto;
    }





    public List<SalesCardNumber> generatorSaveCardNumber() {
        long count = salesNumberRepository.count();

        int next = (int) (1000 - count);

        for (int j = 0; j < next; j++)
            if (!saveCardNumber()) {
                boolean check = true;
                while (check)
                    if (saveCardNumber())
                        check = false;
            }
        return salesNumberRepository.findAll();
    }

    public Boolean saveCardNumber() {
        StringBuilder cardNumber = new StringBuilder("8600");

        for (int i = 0; i < 3; i++) {
            int code = new Random().nextInt(1000, 9999);
            cardNumber.append(code);
        }
        var salesCard = salesNumberRepository.findByCardNumber(cardNumber.toString());

        var cardEntity = cardRepository.findByNumber(cardNumber.toString());

        if (cardEntity.isPresent() || salesCard.isPresent())
            return false;

        var salesCardNumber = new SalesCardNumber();
        salesCardNumber.setCardNumber(cardNumber.toString());
        salesNumberRepository.save(salesCardNumber);

        return true;
    }


}
