package com.hh99.dto.response;

import com.hh99.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponseDTO {
    private Long itemId;
    private String itemName;
    private int price;
    private int stock;
    private String itemDetail;
    private Category category;

    public ItemResponseDTO() {}
}
