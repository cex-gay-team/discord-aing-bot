package com.cex.bot.fishing.user.bo;

import com.cex.bot.fishing.user.mapper.FishingUserMapper;
import com.cex.bot.fishing.user.model.FishingUser;
import com.cex.bot.fishing.user.model.UserStatus;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FishingUserBoImpl implements FishingUserBo {
    @Autowired
    private FishingUserMapper fishingUserMapper;

    @Override
    public FishingUser getFishingUserStatus(User discordUser) {
        long discordUserId = Long.parseLong(discordUser.getId());
        FishingUser user = fishingUserMapper.selectUserStatus(discordUserId);

        if (Objects.isNull(user)) {
            user = new FishingUser();
            user.setDiscordId(discordUserId);
            user.setUserName(discordUser.getName());
            user.setUserStatus(UserStatus.SIGN_UP);

            fishingUserMapper.insertUser(user);
        } else {

        }
        
        return user;
    }

    @Override
    public void updateFishingUserStatus(FishingUser fishingUser) {
        fishingUserMapper.updateUserStatus(fishingUser);
    }
}