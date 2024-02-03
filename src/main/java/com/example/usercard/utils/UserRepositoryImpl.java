package com.example.usercard.utils;

import com.example.usercard.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class UserRepositoryImpl {

    private final EntityManager entityManager;

    public Page<User> searchByAdvanced(Map<String, String> params){
        int page = 0,size = 10;
        if (params.containsKey("page")){
            page = Integer.parseInt(params.get("page"));
        }
        if (params.containsKey("size")){
            size = Integer.parseInt(params.get("size"));
        }

        String firstQuery = "select u from User as u where true ";
        String secondQuery = "select count(u.id) from User as u where true ";

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
        if(params.containsKey("firstname")){
            query.setParameter("name",params.get("firstname"));
        }
        if(params.containsKey("lastName")){
            query.setParameter("lname",params.get("lastname"));
        }
        if(params.containsKey("email")){
            query.setParameter("email",params.get("email"));
        }
        if(params.containsKey("username")){
            query.setParameter("uname",params.get("username"));
        }
        if(params.containsKey("birthday")){
            query.setParameter("birthday",params.get("birthday"));
        }

    }

    public StringBuilder setParameter(Map<String,String> params){
        StringBuilder stringBuilder = new StringBuilder();
        if(params.containsKey("id")){
            stringBuilder.append("AND u.id = :id ");
        }
        if(params.containsKey("firstname")){
            stringBuilder.append("AND u.firstName ilike concat('%',:name,'%') ");
        }
        if(params.containsKey("lastName")){
            stringBuilder.append("AND u.lastName ilike concat('%',:lname,'%') ");
        }
        if(params.containsKey("email")){
            stringBuilder.append("AND u.email ilike concat('%',:email,'%') ");
        }
        if(params.containsKey("username")){
            stringBuilder.append("AND u.username ilike concat('%',:uname,'%') ");
        }
        if(params.containsKey("birthday")){
            stringBuilder.append("AND u.birthday ilike concat('%',:birthday,'%') ");
        }
        stringBuilder.append("And u.deletedAt is null");
        return stringBuilder;
    }
}
