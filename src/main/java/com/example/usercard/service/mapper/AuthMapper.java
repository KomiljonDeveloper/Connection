package com.example.usercard.service.mapper;

import com.example.usercard.dto.AuthDto;
import com.example.usercard.model.Auth;
import com.example.usercard.model.Authorities;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;

    public AuthDto toDto(Auth auth){
        return AuthDto.builder()
                .authId(auth.getAuthId())
                .username(auth.getUsername())
                .password(auth.getPassword())
                .enabled(auth.getEnabled())
                .createdAt(auth.getCreatedAt())
                .updatedAt(auth.getUpdatedAt())
                .deletedAt(auth.getDeletedAt())
                .build();
    }

    public Auth toEntity(AuthDto dto){
        return Auth.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .enabled(true)
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

}
