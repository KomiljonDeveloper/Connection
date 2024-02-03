package com.example.usercard.service;

import com.example.usercard.dto.CardDto;
import com.example.usercard.dto.ErrorDto;
import com.example.usercard.dto.ResponseDto;
import com.example.usercard.model.Card;
import com.example.usercard.model.CrUDSimple;
import com.example.usercard.model.User;
import com.example.usercard.repository.CardRepository;
import com.example.usercard.repository.UserRepository;
import com.example.usercard.service.mapper.CardMapper;
import com.example.usercard.service.validation.CardValidation;
import com.example.usercard.utils.CardRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService implements CrUDSimple<CardDto, Integer> {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper;
    private final CardValidation cardValidation;
    private final CardRepositoryImpl cardRepositoryImpl;


    @Override
    public ResponseDto<CardDto> create(CardDto dto) {
        try {
            dto.setCreatedAt(LocalDateTime.now());
            Optional<User> is = this.userRepository.findByUserId(dto.getUserId());
            List<ErrorDto> validation = cardValidation.validation(dto);
            if (is.isPresent()) {

                if (!validation.isEmpty()) {
                    return ResponseDto.<CardDto>builder()
                            .message("Validation error!")
                            .code(-4)
                            .errors(validation)
                            .build();
                }

                if (this.cardRepository.existsByCardNumber(dto.getCardNumber())) {
                    return ResponseDto.<CardDto>builder()
                            .message("This cardNumber is already exists!")
                            .code(-2)
                            .build();
                }
                this.cardRepository.save(this.cardMapper.toEntity(dto));
                return ResponseDto.<CardDto>builder()
                        .success(true)
                        .message("Created Card!!!")
                        .date(dto)
                        .build();
            } else {
                return ResponseDto.<CardDto>builder()
                        .message("User is not found!")
                        .code(-1)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDto.<CardDto>builder()
                    .code(-2)
                    .message(String.format("Error text : %s", e.getMessage()))
                    .build();
        }
    }

    @Override
    public ResponseDto<CardDto> delete(Integer id) {
        try {
            return this.cardRepository.findByCardIdAndDeletedAtIsNull(id).map(card -> {
                card.setDeletedAt(LocalDateTime.now());
                this.cardRepository.save(card);
                return ResponseDto.<CardDto>builder()
                        .success(true)
                        .message("Deleted Card!!!")
                        .build();
            }).orElse(ResponseDto.<CardDto>builder()
                    .message("Card not found!")
                    .code(-1)
                    .build());

        } catch (Exception e) {
            return ResponseDto.<CardDto>builder()
                    .code(-2)
                    .message(String.format("Error text : %s", e.getMessage()))
                    .build();
        }
    }

    @Override
    public ResponseDto<CardDto> update(CardDto dto, Integer id) {
        try {
            return this.cardRepository.findByCardIdAndDeletedAtIsNull(id).map(card -> {

                List<ErrorDto> validation = cardValidation.validation(dto);
                if (!validation.isEmpty()) {
                    return ResponseDto.<CardDto>builder()
                            .message("Validation error!")
                            .code(-4)
                            .errors(validation)
                            .build();
                }

                if (dto.getCardNumber() != null && !this.cardRepository.findByCardNumberAndDeletedAtIsNull(dto.getCardNumber()).get().getCardId().equals(id)) {
                    return ResponseDto.<CardDto>builder()
                            .message("This cardNumber is already exists!")
                            .code(-2)
                            .build();
                }

                card.setUpdatedAt(LocalDateTime.now());
                this.cardMapper.update(card, dto);
                this.cardRepository.save(card);
                return ResponseDto.<CardDto>builder()
                        .success(true)
                        .message("Update Card!!!")
                        .date(this.cardMapper.toDtoNotUser(card))
                        .build();
            }).orElse(ResponseDto.<CardDto>builder()
                    .message("Card not found!")
                    .code(-1)
                    .build());

        } catch (Exception e) {
            return ResponseDto.<CardDto>builder()
                    .code(-2)
                    .message(String.format("Error text : %s", e.getMessage()))
                    .build();
        }
    }

    @Override
    public ResponseDto<CardDto> get(Integer id) {
        try {
            return this.cardRepository.findByCardIdAndDeletedAtIsNull(id).map(card ->
                    ResponseDto.<CardDto>builder()
                            .success(true)
                            .message("Ok")
                            .date(cardMapper.toDto(card))
                            .build()
            ).orElse(ResponseDto.<CardDto>builder()
                    .message("Card not found!")
                    .code(-1)
                    .build());

        } catch (Exception e) {
            return ResponseDto.<CardDto>builder()
                    .code(-2)
                    .message(String.format("Error text : %s", e.getMessage()))
                    .build();
        }
    }

    public List<CardDto> cardListFromDto(Integer userId) {
        List<CardDto> cardsDto = new ArrayList<>();
        List<Card> all = this.cardRepository.findAllByUserIdAndDeletedAtIsNull(userId);
        for (Card card : all) {
            cardsDto.add(this.cardMapper.toDtoNotUser(card));
        }
        return cardsDto;

    }

    public ResponseDto<Page<CardDto>> searchByBasic(Map<String, String> params) {
        int page = 0,size = 10;
        if (params.containsKey("page")){
            page = Integer.parseInt(params.get("page"));
        }
        if (params.containsKey("size")){
            size = Integer.parseInt(params.get("size"));
        }
        Page<CardDto> map = this.cardRepository.searchByBasic(
                params.get("id") == null ? null : Integer.parseInt(params.get("id")),
                params.get("number"),
                params.get("name"),
                PageRequest.of(page, size)
        ).map(this.cardMapper::toDto);
        if (map.isEmpty()){
            return ResponseDto.<Page<CardDto>>builder()
                    .message("Cards are not found!")
                    .code(-1)
                    .build();
        }else {
            return ResponseDto.<Page<CardDto>>builder()
                    .success(true)
                    .message("Ok")
                    .date(map)
                    .build();
        }


    }

    public ResponseDto<Page<CardDto>> searchByAdvanced(Map<String, String> params) {
      return   Optional.of(this.cardRepositoryImpl.searchByAdvanced(params).map(this.cardMapper::toDto)).map(cards ->
                ResponseDto.<Page<CardDto>>builder()
                        .success(true)
                        .message("OK")
                        .date(cards)
                        .build()
                ).orElse(
                ResponseDto.<Page<CardDto>>builder()
                        .code(-1)
                        .message("Card is not found!")
                        .build()
        );
    }
}