package com.example.usercard.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDto {
    private Integer userId;
    private Integer cardId;
    @NotBlank(message = "This column cannot be empty")
    private String cardName;
    @NotBlank(message = "This column cannot be empty")
    @Size(max = 16,min = 16,message ="This column length equal to sixteen" )
    private String cardNumber;
    @NotBlank(message = "This column cannot be empty")
    private String cardDate;
    @NotBlank(message
            = "This column cannot be empty")
    @Size(min =4,max = 4,message = "This column length equal to four")
    private String cardPassword;
    private UserDto user;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;
}
