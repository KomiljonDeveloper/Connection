package com.example.usercard.service;

import com.example.usercard.dto.AuthDto;
import com.example.usercard.dto.ResponseDto;
import com.example.usercard.repository.AuthRepository;
import com.example.usercard.service.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServise {

    private final AuthRepository authRepository;
    private final AuthMapper authMapper;

    public ResponseDto<AuthDto> create(AuthDto dto) {
        try {
            dto.setCreatedAt(LocalDateTime.now());
            return ResponseDto.<AuthDto>builder()
                    .success(true)
                    .message("OK")
                    .date(this.authMapper.toDto(
                            this.authRepository.save(
                                    this.authMapper.toEntity(dto)
                            )
                    ))
                    .build();
        } catch (Exception e) {
               return ResponseDto.<AuthDto>builder()
                       .code(-1)
                       .message("Error : "+e.getMessage())
                       .build();
        }
    }
    public ResponseDto<AuthDto> get(Integer id) {
        return this.authRepository.findByAuthIdAndDeletedAtIsNull(id)
                .map(auth -> ResponseDto.<AuthDto>builder()
                        .success(true)
                        .message("OK")
                        .date(this.authMapper.toDto(auth))
                        .build())
                .orElse(ResponseDto.<AuthDto>builder()
                        .code(-1)
                        .message("Auth is not found!")
                        .build());
    }
}
