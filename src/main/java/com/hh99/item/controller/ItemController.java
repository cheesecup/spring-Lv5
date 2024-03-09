package com.hh99.item.controller;

import com.hh99.global.response.SuccessResponseDTO;
import com.hh99.item.request.ItemRequestDTO;
import com.hh99.item.response.ItemResponseDTO;
import com.hh99.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "ItemController.class")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    @Secured("ADMIN")
    @PostMapping("/item")
    public ResponseEntity<SuccessResponseDTO<ItemResponseDTO>> createItem(@RequestBody ItemRequestDTO requestDTO) {
        ItemResponseDTO responseDTO = itemService.createItem(requestDTO);

        return ResponseEntity.ok(new SuccessResponseDTO<>("상품 등록 성공", responseDTO));
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<SuccessResponseDTO<ItemResponseDTO>> getItem(@PathVariable Long id) {
        ItemResponseDTO responseDTO = itemService.getItem(id);

        return ResponseEntity.ok(new SuccessResponseDTO<>(id + "번 상품 조회 성공", responseDTO));
    }

    @GetMapping("/item/list")
    public ResponseEntity<SuccessResponseDTO<List<ItemResponseDTO>>> getItemList(@RequestParam(required = false, defaultValue = "itemName") String sort,
                                                                                 @RequestParam(required = false, defaultValue = "desc") String direction,
                                                                                 @RequestParam(required = false, defaultValue = "1") int pageNum) {
        List<ItemResponseDTO> responseDTO = itemService.getItemList(sort, direction, pageNum);

        return ResponseEntity.ok(new SuccessResponseDTO<>("상품 목록 조회 성공", responseDTO));
    }
}
