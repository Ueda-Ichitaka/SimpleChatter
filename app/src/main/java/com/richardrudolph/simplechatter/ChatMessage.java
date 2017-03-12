package com.richardrudolph.simplechatter;

/**
 * Created by Richard on 11.03.2017.
 */

public class ChatMessage
{
    private String message;  //message text
    private String sender;  //sender account - mail
    private String receiver;  //receiver account - mail
    private int id;
    private String date;
    private String time;

    public ChatMessage(String message, String sender, String receiver, int id, String date, String time)
    {

        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public boolean isMine(String account)
    {
        if (this.sender.equals(account))
        {
            return true;
        }
        return false;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

    public String getReceiver()
    {
        return receiver;
    }

    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
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
