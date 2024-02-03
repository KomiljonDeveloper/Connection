package com.example.usercard.controller;

import com.example.usercard.dto.ImageDto;
import com.example.usercard.dto.ResponseDto;
import com.example.usercard.model.CrUDImage;
import com.example.usercard.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController implements CrUDImage<ImageDto, MultipartFile, Integer> {

    private final ImageService imageService;

    @Override
    @Operation(
            tags = "post"
    )
    @PostMapping("/upload/{userId}")
    public ResponseDto<ImageDto> upload(@RequestBody MultipartFile image,@PathVariable Integer userId) {
        return this.imageService.upload(image,userId);
    }

    @Operation(
            tags = "get"
    )
    @GetMapping("/download/{fileId}")
    public ResponseDto<ImageDto> download(@PathVariable Integer fileId){
        return this.imageService.download(fileId);
    }

    @Operation(
            tags = "put"
    )
    @PutMapping("/update/{fileId}")
    public ResponseDto<ImageDto> update(@RequestBody MultipartFile file,@PathVariable Integer fileId){
        return this.imageService.update(file,fileId);
    }

    @Operation(
            tags = "delete"
    )
    @DeleteMapping("/delete")
    public ResponseDto<ImageDto> delete(@RequestParam(name = "fileId") Integer fileId){
        return this.imageService.delete(fileId);
    }
}
