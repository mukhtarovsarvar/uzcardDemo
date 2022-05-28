package com.company.repository.custom;

import com.company.dto.CardDTO;
import com.company.dto.request.CardFilterDTO;
import com.company.entity.CardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardCustomRepository {

    private final EntityManager entityManager;

    public List<CardDTO> filter(CardFilterDTO filter) {
      /*  String sql = "SELECT  c FROM  CardEntity as c";
        Query query = entityManager.createQuery(sql, CardEntity.class);
        List<CardEntity> cardEntityList = query.getResultList();*/

        StringBuilder sql = new StringBuilder("SELECT  c FROM  CardEntity as c ");
        if (filter != null) {
            sql.append(" WHERE c.status = '").append(filter.getStatus().name()).append("'");
        } else {
            sql.append(" WHERE c.status = 'ACTIVE'");
        }

        if (filter.getCardId() != null) {
            sql.append(" AND  c.uuid = '").append(filter.getCardId()).append("'");
        }
        if (filter.getCardNumber() != null) {
            sql.append(" AND  c.number = ").append(filter.getCardId());
        }

        if (filter.getAmountFrom() != null && filter.getToAmount() != null) {
            sql.append(" AND  c.balance between ").append(filter.getAmountFrom()).append(" AND ").append(filter.getToAmount());
        } else if (filter.getAmountFrom() != null) {
            sql.append(" AND  c.balance > ").append(filter.getAmountFrom());
        } else if (filter.getToAmount() != null) {
            sql.append(" AND  c.balance < ").append(filter.getToAmount());
        }

        if (filter.getProfileName() != null) {
            sql.append(" AND  c.profile_name = '").append(filter.getProfileName()).append("'");
        }

        Query query = entityManager.createQuery(sql.toString(), CardEntity.class);
        List<CardEntity> cardEntityList = query.getResultList();


        return cardEntityList.stream().map(this::toDTO).toList();
    }

    public  CardDTO  toDTO(CardEntity entity) {
        CardDTO dto = new CardDTO();
        dto.setBalance(entity.getBalance());
        dto.setClientId(entity.getClientId());
        dto.setNumber(entity.getNumber());
        dto.setExpDate(entity.getExpDate());
        return dto;
    }

}
