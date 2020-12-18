package com.cex.bot.fishing.objectItem.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum FishRarity {
    SSS(1)
    , SS(4)
    , S(10)
    , A(20)
    , B(35)
    , C(65)
    , D(100);
    private static final List<FishRarity> sortedFishRarity;

    static {
        sortedFishRarity = Arrays.stream(FishRarity.values())
                                .sorted(Comparator.comparingInt(FishRarity::getProbability))
                                .collect(Collectors.toCollection(ArrayList::new));
    }
    @Getter
    int probability;
    FishRarity(int probability) {
        this.probability = probability;
    }

    public static FishRarity getRandomFishRarity(int randomProbability) {
        FishRarity rarity = FishRarity.D;
        for(FishRarity fishRarity : sortedFishRarity) {
            if(randomProbability < fishRarity.getProbability()) {
                rarity = fishRarity;
                break;
            }
        }

        return rarity;
    }

}
