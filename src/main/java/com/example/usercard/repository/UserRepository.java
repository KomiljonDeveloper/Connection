package com.example.usercard.repository;

import com.example.usercard.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{

    @Query(
            value = "select * from users where email ilike concat('%',:email,'%') and deleted_at is null",
            nativeQuery = true
    )
    List<User> findByEmail(@Param(value = "email") String email);




    @Query(
            value = "select * from users where first_name ilike concat(:value,'%') and deleted_at is null",
            countQuery = "select count(*) from users",
            nativeQuery = true
    )
    Page<User> findByPageOnOrderBy(Pageable pageable,@Param(value = "value") String value);



    @Query(value = "select * from users where users.id = :id and users.deleted_at is null",
            nativeQuery = true)
    Optional<User> findByUserId(@Param(value = "id") Integer id);
    @Query(
            name = "existsByEmail"
     )
    boolean existsByEmail(@Param(value = "email") String email);

    Page<User> findAllByDeletedAtIsNull(Pageable pageable);
    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    @Query(
           value = "select  * from users where" +
                   " id = coalesce(:id,id) and" +
                   " first_name ilike coalesce(concat('%',:f,'%'),first_name) and" +
                   " last_name ilike coalesce(concat('%',:l,'%'),last_name) and" +
                   " birthday ilike coalesce(concat('%',:b,'%'),birthday) and" +
                   " email ilike coalesce(concat('%',:e,'%'),email) and deleted_at is null",
           nativeQuery = true
    )
    Page<User> searchByBasic(
            @Param(value = "id") Integer id,
            @Param(value = "f") String firstName,
            @Param(value = "l") String lastName,
            @Param(value = "b") String birthday,
            @Param(value = "e") String email,
            Pageable pageable);

    List<User> findAllByDeletedAtIsNotNull();
}
