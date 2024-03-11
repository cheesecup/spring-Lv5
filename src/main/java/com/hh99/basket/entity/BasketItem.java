package com.hh99.basket.entity;

import com.hh99.basket.request.BasketItemRequestDTO;
import com.hh99.global.entity.BaseTime;
import com.hh99.item.entity.Item;
import com.hh99.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "basket_item")
public class BasketItem extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_item_id")
    private Long basketItemId;

    private int basketItemStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected BasketItem() {}

    public BasketItem(BasketItemRequestDTO requestDTO, Member member, Item item) {
        this.basketItemStock = requestDTO.getBasketItemStock();
        this.member = member;
        this.item = item;
    }

    public void updateBasketItemStock(int basketItemStock){
        this.basketItemStock = basketItemStock;
    }
}
