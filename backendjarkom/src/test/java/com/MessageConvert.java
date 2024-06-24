package com;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.serverjarkom.controller.DBhandler;
import com.serverjarkom.util.randomManager;

public class MessageConvert {

    @Test
    public void testJson() {
        Chat texTest = new Chat("dssadf", "fsadfsa", "asdasd", "sddas");
        Gson gson = new Gson();

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        System.out.println(timeStamp);
        String a = gson.toJson(texTest);
        Chat tes = gson.fromJson(a, Chat.class);
        System.out.println(tes.getuserId());
        System.out.println(gson.toJson(texTest));
    }

    @Test
    public void UUID() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
    }

    // @Test
    // public void dbaa() throws SQLException {
    // dbHandler dbHandler = new dbHandler();
    // ResultSet res = dbHandler.getRoom();
    // while (res.next()) {
    // System.out.println(res.getInt("id_room"));
    // System.out.println(res.getString("name_room"));
    // System.out.println(res.getTimestamp("created_at"));
    // }
    // ;

    // dbHandler.close();

    // }

    @Test
    public void SHA256() {
        String shaString = DigestUtils.sha256Hex("hallo");
        System.out.println(shaString);
    }

    @Test
    public void randomCode() {
        System.out.println(randomManager.codeRoom());
    }

    @Test
    public void messageTest() {
        Gson gson = new Gson();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        ChatMessage chat = new ChatMessage();
        chat.setMessage("ahay", "hello", "agus", timeStamp);

        CommadMessage command = new CommadMessage();
        command.setCommad(timeStamp);
        command.setMessage("test");

        System.out.println(command.getCommand());
        System.out.println(command.getMessage());

        System.out.println(gson.toJson(chat));
        String json = gson.toJson(chat);
        
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String classname = jsonObject.get("Type").getAsString();
        System.out.println(classname); 
        CommadMessage inchat = gson.fromJson(json,CommadMessage.class);
        System.out.println(inchat.getType());
    }

}
