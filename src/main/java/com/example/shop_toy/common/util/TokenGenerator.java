package com.example.shop_toy.common.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.util.Strings;

public class TokenGenerator {
    private static final int TOKEN_LENGTH = 20;
    private static final String TYPE = "Bearer";

    public static String randomCharacter(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String randomCharacterWithPrefix(String prefix) {
        return prefix + randomCharacter(TOKEN_LENGTH - prefix.length());
    }

    public static String getToken(String authorization) {
        if (authorization.toLowerCase().startsWith(TYPE.toLowerCase())) {
            return authorization.substring(TYPE.length()).trim();
        }
        return Strings.EMPTY;
    }
}