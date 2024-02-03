package com.example.usercard.repository;

import com.example.usercard.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth,Integer> {
    Optional<Auth> findByAuthIdAndDeletedAtIsNull(Integer authId);
}
