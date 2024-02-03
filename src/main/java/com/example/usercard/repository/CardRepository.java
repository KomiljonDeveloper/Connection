package com.example.usercard.repository;

import com.example.usercard.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    Optional<Card> findByCardIdAndDeletedAtIsNull(Integer id);

    @Query(
            value = "select * from card as c where c.user_id = :userId and c.deleted_at is null",
            nativeQuery = true
    )
    List<Card> findAllByUserIdAndDeletedAtIsNull(@Param(value = "userId") Integer userId);

    @Query(
            name = "existsByCardNumber"
    )
    boolean existsByCardNumber(@Param(value = "cardNumber") String cardNumber);

    Optional<Card> findByCardNumberAndDeletedAtIsNull(String cardNumber);

    @Query(
            value = "select * " +
                    "from card as c where c.card_id = coalesce(:id,c.card_id) and " +
                    "                     c.card_number = coalesce(:number,c.card_number) and " +
                    "                     c.card_name = coalesce(:name,c.card_name) and " +
                    "                     deleted_at is null"
            ,
            nativeQuery = true
    )
    Page<Card> searchByBasic(
            @Param(value = "id") Integer id,
            @Param(value = "number") String number,
            @Param(value = "name") String name,
            Pageable pageable);


    List<Card> findAllByDeletedAtIsNotNull();
}
