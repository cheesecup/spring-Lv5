package com.hh99.item.entity;

import lombok.Getter;

@Getter
public enum Category {
    CLOTHES("CLOTHES"),
    CUP("CUP"),
    PHONE_CASE("PHONE_CASE"),
    CUSHION("CUSHION");

    private final String value;

    Category(String value) {
        this.value = value;
    }
}
