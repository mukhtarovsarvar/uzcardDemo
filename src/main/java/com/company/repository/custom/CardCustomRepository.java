package com.company.repository.custom;


import com.company.dto.request.CardFilterDTO;
import com.company.entity.CardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CardCustomRepository {

    private final EntityManager entityManager;

    @Value("${message.bank.key.word}")
    private String keyWord;


    public List<CardEntity> filter(CardFilterDTO dto) {
        StringBuilder sql = new StringBuilder(
                "select c from CardEntity c " +
                        "left join c.client cl " +
                        "where c.visible = true " +
                        "and cast(c.status as string) <> :keyWord ");

        Map<String, Object> params = new HashMap<>();
        params.put("keyWord", keyWord);



        if (Optional.ofNullable(dto.getCardNumber()).isPresent()) {
            sql.append(" and c.cardNumber = :cardNumber ");
            params.put("cardNumber", dto.getCardNumber());
        }

        if (Optional.ofNullable(dto.getAmountFrom()).isPresent() &&
                Optional.ofNullable(dto.getToAmount()).isPresent()) {
            sql.append(" and c.balance between :fromBalance and :toBalance ");
            params.put("fromBalance", Long.parseLong(dto.getAmountFrom() + "00"));
            params.put("toBalance",  Long.parseLong(dto.getToAmount() + "00"));
        } else if (Optional.ofNullable(dto.getAmountFrom()).isPresent()) {
            sql.append(" and c.balance > :fromBalance ");
            params.put("fromBalance", Long.parseLong(dto.getAmountFrom() + "00"));
        } else if (Optional.ofNullable(dto.getToAmount()).isPresent()) {
            sql.append(" and c.balance < :toBalance ");
            params.put("toBalance", Long.parseLong(dto.getToAmount() + "00"));
        }

        if (Optional.ofNullable(dto.getProfileName()).isPresent()) {
            sql.append(" and cl.profileName = :profileName ");
            params.put("profileName", dto.getProfileName());
        }

        if (Optional.ofNullable(dto.getStatus()).isPresent()) {
            sql.append(" and c.status = :status ");
            params.put("status", dto.getStatus());
        }

        if (Optional.ofNullable(dto.getClientId()).isPresent()) {
            sql.append(" and cl.id = :id ");
            params.put("id", dto.getClientId());
        }

        Query query = entityManager.createQuery(sql.toString(), CardEntity.class);

        params.forEach(query::setParameter);

        return query.getResultList();
    }
}
