package com.serverjarkom;

import java.util.UUID;

public class randomManager {
    public static String codeRoom(){
        char[] ALPHANUMERIC  = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int length = 5;
        StringBuilder random = new StringBuilder();
        
        for(int i =0; i < length; i++) {
            int index = (int) (Math.random()*ALPHANUMERIC.length);
            random.append(ALPHANUMERIC[index]);
        }

        return random.toString();
    }

    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
