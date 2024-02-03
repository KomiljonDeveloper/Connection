package com.example.usercard.model;


import com.example.usercard.dto.ResponseDto;

public interface CrUDImage<T,Y,U> {
    ResponseDto<T> upload(Y image, U userId);
    ResponseDto<T> download(U imageId);
    ResponseDto<T> update(Y image,U imageId);
    ResponseDto<T> delete(U imageId);
}
