package com.richardrudolph.simplechatter;


import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Richard on 11.03.2017.
 */

public class ChatsListItem
{
    private String chatName;
    private BitmapDrawable thumb;
    private int id;

    public ChatsListItem(String name, BitmapDrawable thumb, int id)
    {
        this.chatName = name;
        this.thumb = thumb;
        this.id = id;
    }

    public String getChatName()
    {
        return chatName;
    }

    public void setChatName(String chatName)
    {
        this.chatName = chatName;
    }

    public BitmapDrawable getThumb()
    {
        return thumb;
    }

    public void setThumb(BitmapDrawable thumb)
    {
        this.thumb = thumb;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
