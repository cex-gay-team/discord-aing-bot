package com.cex.bot.fishing.command;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

@Service
public class FishingBotNotCommand implements DiscordBaseCommand {
    @Override
    public void execute(MessageReceivedEvent event) {
        TextChannel textChannel = event.getTextChannel();
        textChannel.sendMessage(event.getAuthor().getAsMention() + "님! 아직 구현안됬으니 기다리새우!").queue();
    }
}
