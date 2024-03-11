package com.hh99.basket.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketItemRequestDTO {
    private Long itemId;
    private Integer basketItemStock;
}
