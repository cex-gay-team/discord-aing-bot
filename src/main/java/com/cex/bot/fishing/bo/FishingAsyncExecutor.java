package com.cex.bot.fishing.bo;

import com.cex.bot.fishing.user.model.FishingUser;
import com.cex.bot.fishing.user.model.UserStatus;
import com.cex.bot.fishing.user.bo.FishingUserBo;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Component
@Slf4j
public class FishingAsyncExecutor {
    @Autowired
    private FishingUserBo fishingUserBo;

    @Async("fishingExecutor")
    @Transactional
    public void executeFishing(MessageReceivedEvent event) {
        try {
            Thread.sleep(10000);
            Random random = new Random();

            if(random.nextInt(10) > 5 ) {
                event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + "님; 낚시 결과 알려드려유; 낚았어요!").queue();
            } else {
                event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + "님; 낚시 결과 알려드려유; 실패했어요!!").queue();
            }
        } catch (InterruptedException exception) {
            log.error("executeFishing aysnc task error param : {0} ", event, exception);
        } finally {
            FishingUser fishingUser = fishingUserBo.getFishingUserStatus(event.getAuthor());
            fishingUser.setUserStatus(UserStatus.WAIT);
            fishingUserBo.updateFishingUserStatus(fishingUser);
        }
    }
}
