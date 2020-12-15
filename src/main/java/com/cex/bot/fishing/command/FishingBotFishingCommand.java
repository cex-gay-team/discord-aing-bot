package com.cex.bot.fishing.command;

import com.cex.bot.fishing.bo.FishingAsyncExecutor;
import com.cex.bot.fishing.user.bo.FishingUserBo;
import com.cex.bot.fishing.user.model.FishingUser;
import com.cex.bot.fishing.user.model.UserStatus;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FishingBotFishingCommand implements DiscordBaseCommand {
    @Autowired
    private FishingAsyncExecutor fishingAsyncExecutor;
    @Autowired
    private FishingUserBo fishingUserBo;

    @Override
    public void execute(MessageReceivedEvent event) {
        FishingUser fishingUser = fishingUserBo.getFishingUserStatus(event.getAuthor());
        fishingUser.setUserStatus(UserStatus.FISHING);
        fishingUserBo.updateFishingUserStatus(fishingUser);

        fishingAsyncExecutor.executeFishing(event);

        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + "님. 낚시를 시작할게유~").queue();

    }
}
