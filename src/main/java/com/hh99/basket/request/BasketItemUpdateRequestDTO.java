package com.hh99.basket.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketItemUpdateRequestDTO {
    private Long basketItemId;
    private Integer basketItemStock;
}
