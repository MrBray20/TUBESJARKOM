package com.clientjarkom.model;

public class GroupItem {
    private String groupName;
    private String idRoom;

    public GroupItem(String groupName,String idRoom) {
        this.groupName = groupName;
        this.idRoom = idRoom;
    }

    public String getidroom(){
        return this.idRoom;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}