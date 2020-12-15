package com.cex.discord;

import com.cex.bot.fishing.user.bo.FishingUserBo;
import com.cex.bot.fishing.command.DiscordBaseCommand;
import com.cex.bot.fishing.user.model.FishingUser;
import com.cex.bot.fishing.user.model.UserStatus;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DiscordBotListener extends ListenerAdapter {
    private static final String COMMAND_PREFIX = "fishingBot";
    private static final String COMMAND_POSTFIX = "Command";
    @Autowired
    private Map<String, DiscordBaseCommand> commandMap;

    @Autowired
    private DiscordBaseCommand fishingBotNotCommand;

    @Autowired
    private DiscordBaseCommand fishingBotHelpCommand;

    @Autowired
    private FishingUserBo fishingUserBo;



    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        DiscordUtil discordUtil = DiscordUtil.getInstance();
        if (discordUtil.isCommand(event)) {
            DiscordBaseCommand command;
            FishingUser fishingUser = fishingUserBo.getFishingUserStatus(event.getAuthor());

            if(fishingUser.getUserStatus() == UserStatus.WAIT) {
                String commandName = discordUtil.getCommand(event.getMessage().getContentRaw());
                command = commandMap.getOrDefault(COMMAND_PREFIX + commandName + COMMAND_POSTFIX, fishingBotNotCommand);
                command.execute(event);
            } else if (fishingUser.getUserStatus() == UserStatus.SIGN_UP) {
                event.getTextChannel().sendMessage("환영합니다. " + event.getAuthor().getAsMention() + "님.").queue();
                fishingBotHelpCommand.execute(event);

                fishingUser.setUserStatus(UserStatus.WAIT);
                fishingUserBo.updateFishingUserStatus(fishingUser);
            } else {
                event.getTextChannel().sendMessage(event.getAuthor().getAsMention() +  "님. 현재 낚시중 상태이십니다. 낚시 완료 후 요청해주세요.").queue();
            }

        }
    }
}
