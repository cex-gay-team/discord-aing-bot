package com.cex.bot.fishing.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {
    private int inventoryId;
    private int fishUserId;
    private int objectId;
    private String objectName;
    private String objectType;
    private float objectPrice;
    private float objectLength;
    private int count;
    private int inventoryNo;
}
