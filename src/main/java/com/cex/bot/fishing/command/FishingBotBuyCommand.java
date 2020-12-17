package com.cex.bot.fishing.command;

import com.cex.bot.fishing.objectItem.bo.ObjectItemBo;
import com.cex.bot.fishing.user.bo.FishingUserBo;
import com.cex.bot.fishing.user.model.FishingUser;
import com.cex.common.util.DiscordSendUtil;
import com.cex.common.util.DiscordUtil;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FishingBotBuyCommand implements DiscordBaseCommand {
    private static final String[] BAIT_ARRAYS = {"미끼", "B", "BAIT"};
    private static final String[] RODS_ARRAYS = {"낚시대", "R", "RODS"};

    @Autowired
    private DiscordSendUtil discordSendUtil;
    @Autowired
    private ObjectItemBo objectItemBo;
    @Autowired
    private FishingUserBo fishingUserBo;

    @Override
    public void execute(MessageReceivedEvent event) {
        DiscordUtil discordUtil = DiscordUtil.getInstance();
        String message = event.getMessage().getContentRaw();
        String[] parameters = discordUtil.parsingParam(message, 3);

        String itemType = Objects.isNull(parameters[0]) ? "" : parameters[0];
        String itemNo = Objects.isNull(parameters[1]) ? "" : parameters[1];

        if (ArrayUtils.contains(BAIT_ARRAYS, itemType.toUpperCase())) {
            FishingUser fishingUser = fishingUserBo.getFishingUserByDiscordId(event.getAuthor().getIdLong());
            message = objectItemBo.processBuyBaitsItem(itemNo, fishingUser);
        } else if (ArrayUtils.contains(RODS_ARRAYS, itemType.toUpperCase())) {
            FishingUser fishingUser = fishingUserBo.getFishingUserByDiscordId(event.getAuthor().getIdLong());
            message = objectItemBo.processBuyRodsItem(itemNo, fishingUser);
        } else {
            message = "잘못된 상점 요청입니다. !buy [타입] [아이템번호]로 아이템을 구입할수 있고, !buy [타입] 의 경우 구입할수 있는 아이템 목록을 볼수 있습니다.";
        }
        discordSendUtil.sendMessage(message, event.getTextChannel().getIdLong());
    }
}
