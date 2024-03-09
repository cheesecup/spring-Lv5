package com.hh99.item.entity;

import com.hh99.item.request.ItemRequestDTO;
import com.hh99.global.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Item extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String itemName;

    private int price;

    private int itemStock;

    @Column(columnDefinition = "text")
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private Category category;

    protected Item() {}

    public Item(ItemRequestDTO requestDTO) {
        this.itemName = requestDTO.getItemName();
        this.price = requestDTO.getPrice();
        this.itemStock = requestDTO.getItemStock();
        this.itemDetail = requestDTO.getItemDetail();
        this.category = requestDTO.getCategory();
    }
}
