package com.example.usercard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Integer id;
   // @NotBlank(message = "This column cannot be empty")
    private String firstName;
   // @NotBlank(message = "This column cannot be empty")
    private String lastName;
   // @NotBlank(message = "This column cannot be empty")
    private String email;
    @NotBlank(message = "This column cannot be empty")
   // @Size(max = 8,min = 8,message = "This column length equal to eight!")
    private String password;
   // @NotBlank(message = "This column cannot be empty")
    private String birthday;
    private List<CardDto> cards;
    private ImageDto image;
    private String username;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;
}
