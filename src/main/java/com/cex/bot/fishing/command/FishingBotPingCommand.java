package com.cex.bot.fishing.command;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;


@Service
public class FishingBotPingCommand implements DiscordBaseCommand {
    @Override
    public void execute(MessageReceivedEvent event) {
        User user = event.getAuthor();
        TextChannel textChannel = event.getTextChannel();
        Message message = event.getMessage();

        textChannel.sendMessage("pong 이야 " + user.getAsMention()).queue();


    }
}
