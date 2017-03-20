package com.richardrudolph.simplechatter;

/**
 * Created by Richard on 11.03.2017.
 */

public class ChatMessage
{
    private String message;  //message text
    private int senderId;  //senderId account - mail
    private int receiverId;  //receiverId account - mail
    private int id;
    private String date;
    private String time;

    public ChatMessage(String message, int senderId, int receiverId, int id, String date, String
        time)
    {

        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.id = id;
        this.date = date;
        this.time = time;
    }

    /*
    public boolean isMine(int account)
    {
        if (this.senderId.equals(account))
        {
            return true;
        }
        return false;
    } */

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getSenderId()
    {
        return senderId;
    }

    public void setSenderId(int senderId)
    {
        this.senderId = senderId;
    }

    public int getReceiverId()
    {
        return receiverId;
    }

    public void setReceiverId(int receiverId)
    {
        this.receiverId = receiverId;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }
}
