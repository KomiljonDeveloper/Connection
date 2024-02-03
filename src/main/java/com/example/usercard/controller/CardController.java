package com.example.usercard.controller;

import com.example.usercard.dto.CardDto;
import com.example.usercard.dto.ResponseDto;
import com.example.usercard.model.CrUDSimple;
import com.example.usercard.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("card")
public class CardController implements CrUDSimple<CardDto,Integer> {

    @Autowired
    private CardService cardService;

    @Override
    @Operation(
            tags = {"post"},
            summary = "This method is entries cards information!",
            description = "Bu methodda karta ma'lumotlarini saqlash uchun ishlatiladi.Ma'liumotlar berishda" +
                    " cardDate,cardNumber,cardName,cardPassword(Passwordning uzunligi 4 ga teng bo'lishi kerak) " +
                    " kabi ustunlar bo'sh bo'lmasligi kerak!"
    )
    @PostMapping("/create")
    public ResponseDto<CardDto> create(@RequestBody @Valid CardDto dto) {
        return this.cardService.create(dto);
    }

    @Override
    @Operation(
            summary = "This api is deleting in card information",
            description = "You only need to give an id to delete",
            tags = {"delete"}
    )
    @DeleteMapping("/delete")
    public ResponseDto<CardDto> delete(@RequestParam(value = "id") Integer id) {
        return this.cardService.delete(id);
    }

    @Override
    @Operation(
            tags = "put",
            summary = "This api is update card information!",
            description = "You only need to give an id to delete"
    )
    @PutMapping("/update")
    public ResponseDto<CardDto> update(@RequestBody CardDto dto, @RequestParam(value = "id") Integer id) {
        return this.cardService.update(dto,id);
    }

    @Operation(
            tags =("get"),
            summary = "This api is getting card information!",
            description = "You only need to give an id to get"
    )
    @Override
    @GetMapping("/get/{id}")
    public ResponseDto<CardDto> get(@PathVariable Integer id) {
        return this.cardService.get(id);
    }
    @Operation(
            tags = "get",
            summary = "This api is getting card information!",
            description = "Bu ma'lumotlarni basic ko'rinishdagi qidiruv bo'lib bu qidiruvni key value ko'rinishida beriladi " +
                    "hamda page ko'rinishida olib keladi " +
                    " Masalan : /search-by-basic?id=1&name=Humo&page=10&size=5 ."
    )
    @GetMapping("/search-by-basic")
    public ResponseDto<Page<CardDto>> searchByBasic(@RequestParam Map<String,String> params){
        return this.cardService.searchByBasic(params);
    }

    @GetMapping("/search-by-advanced")
    @Operation(
            tags = "get"
    )
    public ResponseDto<Page<CardDto>> searchByAdvanced(@RequestParam Map<String,String> params){
        return this.cardService.searchByAdvanced(params);
    }

}
