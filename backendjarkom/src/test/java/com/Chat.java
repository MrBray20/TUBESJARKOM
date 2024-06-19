
package com;
public class Chat {
    private String user_id ;
    private String room_id;
    private String content;
    private String timestamp;
    public char[] id;
    
    Chat(String id,String room,String content,String time){
        this.user_id = id;
        this.room_id = room;
        this.content = content;
        this.timestamp = time;
    }

    public String getuserId(){
        return this.user_id;
    }
}
