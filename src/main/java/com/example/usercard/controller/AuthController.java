package com.example.usercard.controller;

import com.example.usercard.dto.AuthDto;
import com.example.usercard.dto.ResponseDto;
import com.example.usercard.service.AuthServise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth/")
public class AuthController {

    private final AuthServise authServise;
    @PostMapping("create")
    public ResponseDto<AuthDto> create (@RequestBody AuthDto dto){
        return this.authServise.create(dto);
    }

    @GetMapping(value = "/{id}")
    public ResponseDto<AuthDto> get(@PathVariable Integer id){
        return this.authServise.get(id);
    }

}
