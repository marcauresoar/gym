package com.gymproject.app.classes;

public class ItemData {

    String text;
    Integer imageId;
    String id;
    public ItemData(String text, Integer imageId){
        this.text=text;
        this.imageId=imageId;
    }
    public ItemData(String text, Integer imageId, String id){
        this.text=text;
        this.imageId=imageId;
        this.id = id;
    }

    public String getText(){
        return text;
    }

    public Integer getImageId(){
        return imageId;
    }

    public String getId() { return id; };
}