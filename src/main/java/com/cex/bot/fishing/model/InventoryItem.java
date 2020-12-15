package com.cex.bot.fishing.model;

import lombok.Data;

@Data
public class InventoryItem {
    private int itemId;
    private String itemName;
    private int itemPrice;
    private String itemType;
}
