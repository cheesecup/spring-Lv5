package com.hh99.basket.response;

import lombok.Getter;
import lombok.Setter;

// 장바구니에 담긴 상품 정보
@Getter
@Setter
public class BasketItemResponseDTO {
    private Long basketItemId;
    private int basketItemStock;
    private Long itemId;
    private String itemName;
    private int itemPrice;
    private String itemDetail;

    public BasketItemResponseDTO() {}
}
