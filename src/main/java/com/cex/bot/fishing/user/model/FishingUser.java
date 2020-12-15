package com.cex.bot.fishing.user.model;

import lombok.Data;

@Data
public class FishingUser {
    private int userId;
    private String userName;
    private int coin;
    private int rodId;
    private int batisId;
    private long discordId;
    private UserStatus userStatus;
}
