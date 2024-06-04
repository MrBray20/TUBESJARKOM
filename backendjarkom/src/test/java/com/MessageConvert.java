package com;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.*;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.google.gson.Gson;
import com.serverjarkom.DBhandler;

public class MessageConvert {

    @Test
    public void testJson() {
        Text texTest = new Text("dssadf", "fsadfsa", "asdasd", "sddas");
        Gson gson = new Gson();

        System.out.println(gson.toJson(texTest));
    }

    @Test
    public void UUID() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }

    @Test
    public void dbaa() throws SQLException {
        DBhandler dbHandler = new DBhandler();
        ResultSet res = dbHandler.getRoom();
        while (res.next()) {
            System.out.println(res.getInt("id_room"));
            System.out.println(res.getString("name_room"));
            System.out.println(res.getTimestamp("created_at"));
            System.out.println("im here");
        }
        ;

        dbHandler.close();

    }

    @Test
    public void SHA256() {
        String shaString = DigestUtils.sha256Hex("oasdkjfpsadjfps");
        System.out.println(shaString);
    }
}
