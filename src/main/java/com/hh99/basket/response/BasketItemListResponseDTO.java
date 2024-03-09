package com.hh99.basket.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasketItemListResponseDTO {
    private long sumPrice; // 장바구니 상품 총 합계
    private List<BasketItemResponseDTO> basketItems;

    public BasketItemListResponseDTO(long sumPrice, List<BasketItemResponseDTO> basketItems) {
        this.sumPrice = sumPrice;
        this.basketItems = basketItems;
    }
}
