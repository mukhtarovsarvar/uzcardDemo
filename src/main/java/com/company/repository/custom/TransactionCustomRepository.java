package com.company.repository.custom;

import com.company.dto.TransactionHistoryDTO;
import com.company.dto.request.TransactionFilterDTO;
import com.company.entity.TransactionHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionCustomRepository {


    private final EntityManager entityManager;


    public List<TransactionHistoryDTO> filter(TransactionFilterDTO filter) {


        StringBuilder sql = new StringBuilder("select t from TransactionHistoryEntity as t");

        if (filter != null) {
            sql.append(" WHERE c.status = '").append(filter.getStatus().name()).append("'");
        } else {
            sql.append(" WHERE c.status = 'ACTIVE'");
        }

        assert filter != null;

        if (filter.getCardId() != null) {
            sql.append(" AND  c.uuid = '").append(filter.getCardId()).append("'");
        }
        if (filter.getCardNumber() != null) {
            sql.append(" AND  c.number = ").append(filter.getCardId());
        }

        if (filter.getFromAmount() != null && filter.getToAmount() != null) {
            sql.append(" AND  c.balance between ").append(filter.getFromAmount()).append(" AND ").append(filter.getToAmount());
        } else if (filter.getFromAmount() != null) {
            sql.append(" AND  c.balance > ").append(filter.getFromAmount());
        } else if (filter.getToAmount() != null) {
            sql.append(" AND  c.balance < ").append(filter.getToAmount());
        }

        if (filter.getProfile_name() != null) {
            sql.append(" AND  c.profile_name = '").append(filter.getProfile_name()).append("'");
        }

        Query query = entityManager.createQuery(sql.toString(), TransactionHistoryEntity.class);
        List<TransactionHistoryEntity> cardEntityList = query.getResultList();


        return cardEntityList.stream().map(this::toDTO).toList();
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
