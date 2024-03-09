package com.hh99.basket.repository;

import com.hh99.basket.entity.BasketItem;
import com.hh99.item.entity.Item;
import com.hh99.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

    boolean existsByMemberAndItem(Member member, Item item);

    List<BasketItem> findAllByMember(Member member);

    Optional<BasketItem> findByItemAndMember(Item item, Member member);
}
