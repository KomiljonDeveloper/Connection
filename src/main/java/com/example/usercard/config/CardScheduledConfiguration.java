package com.example.usercard.config;

import com.example.usercard.model.Card;
import com.example.usercard.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class CardScheduledConfiguration {
   private final CardRepository cardRepository;
    @Scheduled(
            cron = "0 0 0 1 * *"
    )

    private void clearDeletingCard(){
        List<Card> allByDeletedAtIsNotNull =
                this.cardRepository.findAllByDeletedAtIsNotNull();
         this.cardRepository.deleteAll(allByDeletedAtIsNotNull);
    }

}
