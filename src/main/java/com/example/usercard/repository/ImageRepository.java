package com.example.usercard.repository;

import com.example.usercard.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {
    Optional<Image> findByImageIdAndDeletedAtIsNull(Integer imageId);
    Optional<Image> findByUserIdAndDeletedAtIsNull(Integer userId);
    @Query(name = "deleting image")
    List<Image> deletingImage();
}
