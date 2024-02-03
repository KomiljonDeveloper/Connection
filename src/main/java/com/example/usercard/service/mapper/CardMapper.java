package com.example.usercard.service.mapper;

import com.example.usercard.dto.CardDto;
import com.example.usercard.model.Card;
import com.example.usercard.repository.UserRepository;
import com.example.usercard.service.CardService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",imports = CardService.class)
public abstract class CardMapper {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Mapping(target = "cardId",ignore = true)
    @Mapping(target = "user",ignore = true)
    public abstract Card toEntity(CardDto date);
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    @Mapping(target = "deletedAt",ignore = true)
    @Mapping(target = "user",expression = "java(userMapper.toDto(userRepository.findByUserId(card.getUserId()).get()))")
    public abstract CardDto toDto(Card card);


    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    @Mapping(target = "deletedAt",ignore = true)
    @Mapping(target = "user",ignore = true)
    public abstract CardDto toDtoNotUser(Card card);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,resultType = Card.class )
    public abstract Card update(@MappingTarget Card card, CardDto dto);
}
