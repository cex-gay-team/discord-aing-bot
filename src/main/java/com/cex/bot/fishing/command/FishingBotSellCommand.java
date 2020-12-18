package com.cex.bot.fishing.command;

import com.cex.bot.fishing.objectItem.bo.ObjectItemBo;
import com.cex.bot.fishing.user.bo.FishingUserBo;
import com.cex.bot.fishing.user.model.FishingUser;
import com.cex.bot.fishing.user.model.InventoryItem;
import com.cex.common.util.DiscordSendUtil;
import com.cex.common.util.DiscordUtil;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FishingBotSellCommand implements DiscordBaseCommand {
    private static final int MOUNTING_INVENTORY_MAX_NO = 2;
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
        String[] parameters = discordUtil.parsingParam(message, 2);
        FishingUser fishingUser = fishingUserBo.getFishingUserByDiscordId(event.getAuthor().getIdLong());
        String inventoryNo = Objects.isNull(parameters[0]) ? "" : parameters[0];
        List<InventoryItem> myInventoryList = objectItemBo.getMyItems(fishingUser.getUserId()).stream()
                .filter(inventoryItem -> inventoryItem.getInventoryNo() > MOUNTING_INVENTORY_MAX_NO).collect(Collectors.toList());

        if (discordUtil.isNumeric(inventoryNo) && Integer.parseInt(inventoryNo) > MOUNTING_INVENTORY_MAX_NO) {
            Optional<InventoryItem> sellItem = myInventoryList.stream().filter(inventoryItem -> inventoryItem.getInventoryNo() == Integer.parseInt(inventoryNo)).findFirst();
            if (sellItem.isPresent()) {
                InventoryItem item = sellItem.get();
                int sellCoin = objectItemBo.sellItem(fishingUser, item);

                message = fishingUser.getUserName() + "님!. " + item.getObjectName() + "을 " + item.getSellPrice() + "에 팔았어요!\n"
                        + "현재 보유하신 코인은 " + fishingUser.getCoin() + sellCoin + " 입니다.\n";
            } else {
                message = "해당 인벤토리 번호의 아이템이 없어요. \n 현재 판매가능 아이템은 아래와 같아요.\n" +
                        getPossibleSellListString(myInventoryList);
            }
        } else {
            message = "판매는 !sell [인벤토리번호] 로 판매할 수 있어요. \n 현재 판매가능 아이템은 아래와 같아요.\n"
                    + "No | 아이템명 | 가격 | 개수 \n"
                    + getPossibleSellListString(myInventoryList);
        }

        discordSendUtil.sendMessage(message, event.getTextChannel().getIdLong());
    }

    private String getPossibleSellListString(List<InventoryItem> myInventoryItemList) {
        StringBuilder message = new StringBuilder();
        if (myInventoryItemList.size() > 0) {
            for (InventoryItem inventoryItem : myInventoryItemList) {
                message.append(inventoryItem.getInventoryNo() + " | " + inventoryItem.getObjectName() + " | " + inventoryItem.getSellPrice() + " | "+ inventoryItem.getCount() + "\n");
            }
        } else {
            message.append("판매가능한 아이템이 없어요.\n");
        }


        return message.toString();
    }
}
