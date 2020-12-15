package com.cex.bot.fishing.model;

import lombok.Data;

@Data
public class Fishes {
    private int id;
    private String name;
    private String rarity;
    private int length;
    private int price;
    private boolean fishYn;
    private String remark;
}
