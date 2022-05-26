package com.company.service;

import com.company.dto.CardDTO;
import com.company.dto.request.CardRequestDTO;
import com.company.entity.CardEntity;
import com.company.entity.SalesCardNumber;
import com.company.repository.CardRepository;
import com.company.repository.SalesCardNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final SalesCardNumberRepository salesNumberRepository;

    private final ClientService clientService;


    public CardDTO create(CardRequestDTO dto) {

        clientService.getById(dto.getClientId());

        CardEntity cardEntity = new CardEntity();
        cardEntity.setBalance(0L);
        cardEntity.setClientId(dto.getClientId());
        cardEntity.setExpDate(LocalDate.now().plusYears(4).toString());
        SalesCardNumber salesCardNumber = salesNumberRepository.findTop1By().get();
        cardEntity.setNumber(salesCardNumber.getCardNumber());

        cardRepository.save(cardEntity);

        salesNumberRepository.deleteById(salesCardNumber.getUuid());

        return toDTO(cardEntity);
    }



    public CardDTO toDTO(CardEntity entity) {
        CardDTO dto = new CardDTO();
        dto.setBalance(entity.getBalance());
        dto.setClientId(entity.getClientId());
        dto.setNumber(cardStars(entity.getNumber()));
        dto.setExpDate(entity.getExpDate());
        return dto;
    }

    public String cardStars(String cardNum) {
        return cardNum.substring(0, 4) +
                "********" +
                cardNum.substring(cardNum.length() - 4);
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
