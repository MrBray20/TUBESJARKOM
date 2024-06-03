package com;

public class Text {
    private String user_id ;
    private String room_id;
    private String content;
    private String timestamp;
    
    Text(String id,String room,String content,String time){
        this.user_id = id;
        this.room_id = room;
        this.content = content;
        this.timestamp = time;
    }
}
