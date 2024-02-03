package com.example.usercard.config;

import com.example.usercard.model.Image;
import com.example.usercard.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class ImageScheduledConfiguration {
    private final ImageRepository imageRepository;
    @Scheduled(cron = "0 0 0 1 * *")
    private void clearDeletingImage(){
        List<Image> images =
                this.imageRepository.deletingImage();

         this.imageRepository.deleteAll(images);
    }

}
