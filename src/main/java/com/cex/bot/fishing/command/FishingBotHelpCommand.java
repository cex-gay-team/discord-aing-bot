package com.cex.bot.fishing.command;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

@Service
public class FishingBotHelpCommand implements DiscordBaseCommand {
    @Override
    public void execute(MessageReceivedEvent event) {
        TextChannel channel = event.getTextChannel();
        StringBuilder builder = new StringBuilder("낚시봇 사용법 설명드려요.\n");
        builder.append("!fishing 낚시를 시작합니다.\n")
                .append("!bait [미끼번호] 미끼를 변경합니다. 번호를 입력안하면 가지고 있는 미끼 목록을 보여줍니다.\n")
                .append("!location [장소번호] 낚시 장소를 변경합니다. 번호를 입력안하면 낚시터 정보를 보여준다구!\n")
                .append("!buy [아이템번호] 아이템을 구매합니다. 번호를 입력안하면 아이템 구매가능 목록을 보여준다구!\n")
                .append("!sell [아이템번호] 아이템을 판매합니다. 번호를 입력안하면 판매 가능 목록을 보여준다구!\n")
                .append("!help 도움말을 보여줍니다.");
        channel.sendMessage(builder.toString()).queue();
    }
}
