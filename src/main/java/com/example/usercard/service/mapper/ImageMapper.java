package com.example.usercard.service.mapper;

import com.example.usercard.dto.ImageDto;
import com.example.usercard.model.Image;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ImageMapper {
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    @Mapping(target = "deletedAt",ignore = true)
    @Mapping(target = "user",ignore = true)
    public abstract ImageDto toDto(Image image);
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    @Mapping(target = "deletedAt",ignore = true)
    public abstract ImageDto toDtoWithUser(Image image);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void UpdateToDtoFromEntity(@MappingTarget Image image, ImageDto dto);

}
