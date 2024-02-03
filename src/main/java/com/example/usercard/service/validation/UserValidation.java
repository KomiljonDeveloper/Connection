package com.example.usercard.service.validation;

import com.example.usercard.dto.ErrorDto;
import com.example.usercard.dto.UserDto;
import com.example.usercard.test.UserTest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidation {
  public List<ErrorDto> validation(UserDto dto){

      List<ErrorDto> errors = new ArrayList<>();

      if (dto.getEmail() != null && !UserTest.checkEmail(dto.getEmail())){
          errors.add(new ErrorDto(dto.getEmail(),String.format("This %s email is invalid!",dto.getEmail())));
      }

      if (dto.getPassword() != null && !UserTest.checkPassword(dto.getPassword())){
          errors.add(new ErrorDto(dto.getPassword(),String.format("This %s password is invalid! The password must contain 2 or more uppercase, lowercase letters and numbers.",dto.getPassword())));
      }
      return errors;
  }

}
