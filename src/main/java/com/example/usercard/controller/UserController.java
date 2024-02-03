package com.example.usercard.controller;

import com.example.usercard.dto.ResponseDto;
import com.example.usercard.dto.UserDto;
import com.example.usercard.model.CrUDSimple;
import com.example.usercard.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "user")
@RequiredArgsConstructor
public class UserController implements CrUDSimple<UserDto,Integer> {

   private final UserService userService;
    @Override
    @Operation(
            tags =("post")
    )
    @PostMapping("/create")
    public ResponseDto<UserDto> create(@RequestBody @Valid UserDto dto){
        return this.userService.create(dto);
    }

    @Override
    @Operation(
            tags = "delete"
    )
    @DeleteMapping("/delete")
    public ResponseDto<UserDto> delete(@RequestParam(value ="id") Integer id) {
        return this.userService.delete(id);
    }

    @Override
    @Operation(
            tags = "put"
    )
    @PutMapping("/update/{id}")
    public ResponseDto<UserDto> update(@RequestBody UserDto dto, @PathVariable Integer id) {
        return this.userService.update(dto,id);
    }

    @Operation(
            tags =("get")
    )
    @Override
    @GetMapping("/get/{id}")
    public ResponseDto<UserDto> get(@PathVariable Integer id) {
        return this.userService.get(id);
    }
    @GetMapping("/get-all-page")
    @Operation(
            tags = "get"
    )
    public ResponseDto<Page<UserDto>> getAllByPage(@RequestParam Integer page,@RequestParam Integer size){
    return this.userService.getAllByPage(page,size);
    }
    @Operation(
            tags = "get"
    )
    @GetMapping("/get-all-page-value")
    public ResponseDto<Page<UserDto>> getAllByValue(@RequestParam Integer page,@RequestParam Integer size,@RequestParam String value){
        return this.userService.getAllByValue(page,size,value);

    }
    @Operation(
            tags = "get"
    )
    @GetMapping("/get-by-email")
    public ResponseDto<List<UserDto>> getAllByValue(@RequestParam String email){
        return this.userService.getAllByEmail(email);


    }
    @Operation(
            tags = "get"
    )
    @GetMapping("/search-by-basic")
    public ResponseDto<Page<UserDto>> searchByBasic(@RequestParam Map<String,String> params){
      return   this.userService.searchByBasic(params);
    }
    @Operation(
            tags = "get"
    )
    @GetMapping("/search-by-advanced")
    public ResponseDto<Page<UserDto>> searchByAdvanced(@RequestParam Map<String,String> params){
        return  this.userService.searchByAdvanced(params);

    }


}
