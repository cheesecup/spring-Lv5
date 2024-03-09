package com.hh99.dto.request;

import com.hh99.entity.Category;
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
