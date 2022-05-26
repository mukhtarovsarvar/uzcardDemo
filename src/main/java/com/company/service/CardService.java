package com.company.service;

import com.company.entity.SalesCardNumber;
import com.company.repository.CardRepository;
import com.company.repository.SalesCardNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final SalesCardNumberRepository salesNumberRepository;


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
