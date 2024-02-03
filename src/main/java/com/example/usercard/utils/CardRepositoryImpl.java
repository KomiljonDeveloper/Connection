package com.example.usercard.utils;

import com.example.usercard.model.Card;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@AllArgsConstructor
@Component
public class CardRepositoryImpl {

    private final EntityManager entityManager;

    public Page<Card> searchByAdvanced(Map<String, String> params){
        int page = 0,size = 10;
        if (params.containsKey("page")){
            page = Integer.parseInt(params.get("page"));
        }
        if (params.containsKey("size")){
            size = Integer.parseInt(params.get("size"));
        }

        String firstQuery = "select c from Card as c where true ";
        String secondQuery = "select count(c.id) from Card as c where true ";

        StringBuilder stringBuilder = setParameter(params);

        Query queryO = this.entityManager.createQuery(firstQuery + stringBuilder);
        Query queryT = this.entityManager.createQuery(secondQuery + stringBuilder);

        moreParameter(queryO,params);
        moreParameter(queryT,params);

        queryO.setFirstResult(size*page);
        queryO.setMaxResults(size);


        return new PageImpl<>(queryO.getResultList(), PageRequest.of(page,size),Long.parseLong(queryT.getSingleResult().toString()));

    }

    private void moreParameter(Query query, Map<String, String> params) {
        if(params.containsKey("id")){
            query.setParameter("id",params.get("id"));
        }
        if(params.containsKey("number")){
            query.setParameter("number",params.get("number"));
        }
        if(params.containsKey("name")){
            query.setParameter("name",params.get("name"));
        }

    }

    public StringBuilder setParameter(Map<String,String> params){
        StringBuilder stringBuilder = new StringBuilder();
        if(params.containsKey("id")){
            stringBuilder.append("AND c.cardId = :id ");
        }
        if(params.containsKey("name")){
            stringBuilder.append("AND c.cardName ilike concat('%',:name,'%') ");
        }
        if(params.containsKey("number")){
            stringBuilder.append("AND c.cardNumber ilike concat('%',:number,'%') ");
        }

        stringBuilder.append("And c.deletedAt is null");
        return stringBuilder;
    }
}
