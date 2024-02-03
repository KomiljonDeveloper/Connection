package com.example.usercard.model;


import com.example.usercard.dto.ResponseDto;

public interface CrUDSimple<K,V>{

    ResponseDto<K> create(K dto);
    ResponseDto<K> delete(V id);
    ResponseDto<K> update(K dto,V id);
    ResponseDto<K> get(V id);
}
