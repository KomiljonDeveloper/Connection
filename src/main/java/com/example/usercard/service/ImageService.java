package com.example.usercard.service;

import com.example.usercard.dto.ImageDto;
import com.example.usercard.dto.ResponseDto;
import com.example.usercard.model.CrUDImage;
import com.example.usercard.model.Image;
import com.example.usercard.repository.ImageRepository;
import com.example.usercard.repository.UserRepository;
import com.example.usercard.service.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService implements CrUDImage<ImageDto, MultipartFile, Integer> {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final UserRepository userRepository;

    @Override
    public ResponseDto<ImageDto> upload(MultipartFile image, Integer userId) {
        try {

            return this.userRepository.findByUserId(userId).map(user -> {

                String[] split = Objects.requireNonNull(image.getOriginalFilename()).split("\\.");
                Image image1 = new Image();
                image1.setCreatedAt(LocalDateTime.now());
                image1.setImageName(split[split.length - 2]);
                image1.setExt(split[split.length - 1]);
                image1.setPath(saveImage(image));
                image1.setUserId(user.getId());
                this.imageRepository.save(image1);
                return ResponseDto.<ImageDto>builder()
                        .success(true)
                        .message("OK")
                        .date(this.imageMapper.toDtoWithUser(image1))
                        .build();
            }).orElse(ResponseDto.<ImageDto>builder()
                    .code(-1)
                    .message("User not found!")
                    .build()); }

        catch (Exception e) {
            return ResponseDto.<ImageDto>builder()
                    .message(String.format("Error text : %s", e.getMessage()))
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<ImageDto> download(Integer imageId) {

        return this.imageRepository.findByImageIdAndDeletedAtIsNull(imageId).map(image -> {
            ImageDto dto = this.imageMapper.toDtoWithUser(image);
            try {
                dto.setData(Files.readAllBytes(Path.of(dto.getPath())));
                return ResponseDto.<ImageDto>builder()
                        .success(true)
                        .message("OK")
                        .date(dto)
                        .build();

            } catch (Exception e) {
                return ResponseDto.<ImageDto>builder()
                        .message(String.format("Error text : %s", e.getMessage()))
                        .code(-2)
                        .build();
            }


        }).orElse(ResponseDto.<ImageDto>builder()
                        .message("Image not found!")
                        .code(-1)
                .build());

    }

    @Override
    public ResponseDto<ImageDto> update(MultipartFile image, Integer imageId) {
         return this.imageRepository.findByImageIdAndDeletedAtIsNull(imageId).map(image1 -> {
            String[] split = Objects.requireNonNull(image.getOriginalFilename()).split("\\.");
            ImageDto build = ImageDto.<ImageDto>builder()
                    .imageName(split[split.length - 2])
                    .ext(split[split.length - 1])
                    .updatedAt(LocalDateTime.now())
                    .path(saveImage(image))
                    .build();
            this.imageMapper.UpdateToDtoFromEntity(image1,build);
            this.imageRepository.save(image1);
            return ResponseDto.<ImageDto>builder()
                    .success(true)
                    .message("OK")
                    .date(this.imageMapper.toDto(image1))
                    .build();
        }).orElse(ResponseDto.<ImageDto>builder()
                         .message("Image not found!")
                         .code(-1)
                 .build());

    }

    @Override
    public ResponseDto<ImageDto> delete(Integer imageId) {
      return   this.imageRepository.findByImageIdAndDeletedAtIsNull(imageId).map(image -> {
            image.setDeletedAt(LocalDateTime.now());
            this.imageRepository.save(image);
            return ResponseDto.<ImageDto>builder()
                    .success(true)
                    .message("Image Deleted!")
                    .build();
        }).orElse(ResponseDto.<ImageDto>builder()
                      .message("Image not found!")
                      .code(-1)
              .build());
    }

    public String saveImage(MultipartFile image) {
        try {
            String folderName = String.format("%s/%s", "upload", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            File file = new File(folderName);
            if (!file.exists()) {
                file.mkdirs();
            }
            String fileName = String.format("%s/%s", folderName, UUID.randomUUID());
            Files.copy(image.getInputStream(), Path.of(fileName));
            return fileName;
        } catch (Exception e) {
            return e.getMessage();
        }

    }

}
