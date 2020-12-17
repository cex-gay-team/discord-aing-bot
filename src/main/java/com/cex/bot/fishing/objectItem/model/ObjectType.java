package com.cex.bot.fishing.objectItem.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum ObjectType {
    ROD("R"), FISH("F"), BAITS("B");
    @Getter
    String code;
    private static Map<String, ObjectType> OBJECT_TYPE_MAP_KEY_CODE = new HashMap<>();
    static {
        for(ObjectType objectType : ObjectType.values()) {
            OBJECT_TYPE_MAP_KEY_CODE.put(objectType.getCode(), objectType);
        }
    }
    ObjectType(String code) {
        this.code = code;
    }

    public static ObjectType getObjectTypeByCode(String code) {
        return OBJECT_TYPE_MAP_KEY_CODE.get(code);
    }
}
