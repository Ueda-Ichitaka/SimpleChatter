package com.richardrudolph.simplechatter;

import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Richard on 14.03.2017.
 */

public class ContactsListItem
{
    private String contactName;
    private BitmapDrawable thumb;
    private int id;

    public ContactsListItem(String name, BitmapDrawable thumb, int id)
    {
        this.contactName = name;
        this.thumb = thumb;
        this.id = id;
    }

    public String getContactName()
    {
        return contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
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
