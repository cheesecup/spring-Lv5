package com.hh99.basket.controller;

import com.hh99.basket.request.BasketItemRequestDTO;
import com.hh99.basket.request.BasketItemUpdateRequestDTO;
import com.hh99.basket.response.BasketItemListResponseDTO;
import com.hh99.basket.response.BasketItemResponseDTO;
import com.hh99.basket.service.BasketItemService;
import com.hh99.global.response.SuccessResponseDTO;
import com.hh99.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BasketItemController {

    private final BasketItemService basketItemService;

    @PostMapping("/basket")
    public ResponseEntity createBasketItem(@RequestBody BasketItemRequestDTO requestDTO,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BasketItemResponseDTO responseDTO = basketItemService.createBasketItem(requestDTO, userDetails);

        return ResponseEntity.ok(new SuccessResponseDTO<>("장바구니에 상품 추가 성공", responseDTO));
    }

    @GetMapping("/basket")
    public ResponseEntity getBasketItemList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        BasketItemListResponseDTO responseDTO = basketItemService.getBasketItemList(userDetails);

        return ResponseEntity.ok(new SuccessResponseDTO<>("장바구니 조회 성공", responseDTO));
    }

    @PutMapping("/basket")
    public ResponseEntity updateBasketItem(@RequestBody BasketItemUpdateRequestDTO requestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BasketItemResponseDTO responseDTO = basketItemService.updateBasketItem(requestDTO, userDetails);

        return ResponseEntity.ok(new SuccessResponseDTO<>("선택한 상품 수량 변경 성공", responseDTO));
    }

    @DeleteMapping("/basket/{basketItemId}")
    public ResponseEntity deleteBasketItem(@PathVariable Long basketItemId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long response = basketItemService.deleteBasketItem(basketItemId, userDetails);

        return ResponseEntity.ok(new SuccessResponseDTO<Long>("선택한 상품 삭제 성공", response));
    }


}
