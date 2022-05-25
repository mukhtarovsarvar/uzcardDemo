package com.company.service;

import com.company.entity.CardEntity;
import com.company.entity.SalesCardNumber;
import com.company.repository.CardRepository;
import com.company.repository.SalesCardNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final SalesCardNumberRepository salesNumberRepository;


    public  void generatorSaveCardNumber() {
        long count = salesNumberRepository.count();

        int next = (int) (100-count);

        for (int j = 0; j < next; j++) {
            String cardNumber = generatorCardNumber();

            Optional<CardEntity> cardEntity = cardRepository.findByNumber(cardNumber);

            if (cardEntity.isEmpty()) {
                SalesCardNumber salesCardNumber = new SalesCardNumber();
                salesCardNumber.setCardNumber(cardNumber.toString());
                salesNumberRepository.save(salesCardNumber);
            }

        }
    }

    public  String generatorCardNumber() {
        StringBuilder cardNumber = new StringBuilder("8600");

        for (int i = 0; i < 3; i++) {
            int code = new Random().nextInt(1000, 9999);
            cardNumber.append(code);
        }


        return cardNumber.toString();
    }


}
