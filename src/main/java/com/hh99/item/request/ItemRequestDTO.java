package com.hh99.item.request;

import com.hh99.item.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDTO {
    private String itemName;
    private Integer price;
    private Integer itemStock;
    private String itemDetail;
    private Category category;
}
