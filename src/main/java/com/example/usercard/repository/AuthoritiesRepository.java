package com.example.usercard.repository;

import com.example.usercard.model.Auth;
import com.example.usercard.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities,Integer> {

}
