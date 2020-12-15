package com.cex.bot.fishing.user.bo;

import com.cex.bot.fishing.user.model.FishingUser;
import net.dv8tion.jda.api.entities.User;

public interface FishingUserBo {
    FishingUser getFishingUserStatus(User discordUser);
    void updateFishingUserStatus(FishingUser fishingUser);
}
