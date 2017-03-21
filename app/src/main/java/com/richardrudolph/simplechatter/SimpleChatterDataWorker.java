package com.richardrudolph.simplechatter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Richard on 15.03.2017.
 */

public class SimpleChatterDataWorker
{
    private SQLiteDatabase database;
    private SimpleChatterDbHelper dbHelper;

    public SimpleChatterDataWorker(Context context)
    {
        dbHelper = new SimpleChatterDbHelper(context);
    }

    public void writeData(String table_name, ContentValues values)
    {
        database = dbHelper.getWritableDatabase();
        long newRowId = database.insert(table_name, null, values);
    }

    public void testData()
    {
        ContentValues test = new ContentValues();
        test.put(SimpleChatterContract.Contact.COLUMN_NAME, "Anonymous");
        test.put(SimpleChatterContract.Contact.COLUMN_THUMB, "");
        writeData(SimpleChatterContract.Contact.TABLE_NAME, test);

        ContentValues test2 = new ContentValues();
        test2.put(SimpleChatterContract.Contact.COLUMN_NAME, "Xeni");
        test2.put(SimpleChatterContract.Contact.COLUMN_THUMB, "");
        writeData(SimpleChatterContract.Contact.TABLE_NAME, test2);

        ContentValues test3 = new ContentValues();
        test3.put(SimpleChatterContract.Contact.COLUMN_NAME, "Bernd");
        test3.put(SimpleChatterContract.Contact.COLUMN_THUMB, "");
        writeData(SimpleChatterContract.Contact.TABLE_NAME, test3);
    }

    public ArrayList<ContactsListItem> getContactList()
    {
        database = dbHelper.getReadableDatabase();

        String[] projection = {SimpleChatterContract.Contact._ID, SimpleChatterContract.Contact
            .COLUMN_NAME, SimpleChatterContract.Contact.COLUMN_THUMB};

        Cursor cursor = database.query(SimpleChatterContract.Contact.TABLE_NAME, projection,
            null, null, null, null, null);

        ArrayList<ContactsListItem> contacts = new ArrayList<ContactsListItem>();
        while (cursor.moveToNext())
        {
            ContactsListItem item = new ContactsListItem(cursor.getString(cursor
                .getColumnIndexOrThrow(SimpleChatterContract.Contact.COLUMN_NAME)), null, cursor
                .getInt(cursor.getColumnIndexOrThrow(SimpleChatterContract.Contact._ID)));
            contacts.add(item);
        }
        cursor.close();

        return contacts;
    }


    public ArrayList<ChatsListItem> getChatsList()
    {
        database = dbHelper.getReadableDatabase();

        String[] projection = {SimpleChatterContract.Chats._ID, SimpleChatterContract.Chats
        .COLUMN_CONTACT,
            SimpleChatterContract.Chats.COLUMN_TABLE};

        Cursor cursor = database.query(SimpleChatterContract.Chats.TABLE_NAME, projection,
            null, null, null, null, null);

        ArrayList<ChatsListItem> chats = new ArrayList<ChatsListItem>();
        while (cursor.moveToNext())
        {
            int contact = cursor.getInt(cursor.getColumnIndexOrThrow(SimpleChatterContract.Chats
                .COLUMN_CONTACT));

            chats.add(getChat(contact));
        }
        cursor.close();

        return chats;
    }

    public ArrayList<ChatMessage> getMessageList(String tableName)
    {
        database = dbHelper.getReadableDatabase();

        String[] projection = {SimpleChatterContract.Chat._ID, SimpleChatterContract.Chat
            .COLUMN_MESSAGE_TEXT, SimpleChatterContract.Chat.COLUMN_SENDER_ID,
            SimpleChatterContract.Chat.COLUMN_RECEIVER_ID, SimpleChatterContract.Chat
            .COLUMN_DATE, SimpleChatterContract.Chat.COLUMN_TIME};

        Cursor cursor = database.query(tableName, projection, null, null, null, null, null);

        ArrayList<ChatMessage> messages = new ArrayList<ChatMessage>();
        while (cursor.moveToNext())
        {
            ChatMessage item = new ChatMessage(cursor.getString(cursor.getColumnIndexOrThrow
                (SimpleChatterContract.Chat.COLUMN_MESSAGE_TEXT)), cursor.getInt(cursor
                .getColumnIndexOrThrow(SimpleChatterContract.Chat.COLUMN_SENDER_ID)), cursor
                .getInt(cursor.getColumnIndexOrThrow(SimpleChatterContract.Chat
                    .COLUMN_RECEIVER_ID)), cursor.getInt(cursor.getColumnIndexOrThrow
                (SimpleChatterContract.Chat._ID)), cursor.getString(cursor.getColumnIndexOrThrow
                (SimpleChatterContract.Chat.COLUMN_DATE)), cursor.getString(cursor
                .getColumnIndexOrThrow(SimpleChatterContract.Chat.COLUMN_TIME)));
            messages.add(item);
        }
        cursor.close();

        return messages;
    }

    public void checkChatEntryExists(int contactId)
    {
        database = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        String sql = "SELECT table_chat FROM table_chats WHERE contact_id = " + contactId;
        cursor = database.rawQuery(sql, null);


        if (cursor.getCount() > 0)
        {
            //got an entry
        } else
        {
            int index = (int) (DatabaseUtils.queryNumEntries(database, "table_chats") + 1);
            ContentValues chatValues = new ContentValues();
            chatValues.put(SimpleChatterContract.Chats.COLUMN_CONTACT, contactId);
            chatValues.put(SimpleChatterContract.Chats.COLUMN_TABLE, "table_chat_" + index);
            writeData(SimpleChatterContract.Chats.TABLE_NAME, chatValues);

            createChatTable(index);
        }
        cursor.close();
    }

    public String getChatTable(int contactId)
    {
        database = dbHelper.getReadableDatabase();

        String[] projection = {SimpleChatterContract.Chats._ID, SimpleChatterContract.Chats
            .COLUMN_CONTACT, SimpleChatterContract.Chats.COLUMN_TABLE};

        String selection = SimpleChatterContract.Chats.COLUMN_CONTACT + " = ?";
        String[] selectionArgs = {Integer.toString(contactId)};

        Cursor cursor = database.query(SimpleChatterContract.Chats.TABLE_NAME, projection,
            selection, selectionArgs, null, null, null);
        Log.v("Cursor count", "Cursor count: " + cursor.getCount());

        cursor.moveToNext();
        String retVal = cursor.getString(cursor.getColumnIndexOrThrow(SimpleChatterContract.Chats
            .COLUMN_TABLE));
        Log.v("chat table", "chat table name got by contact id: " + retVal);
        cursor.close();

        return retVal;
    }

    public ChatsListItem getChat(int id)
    {
        ArrayList<ContactsListItem> contacts = getContactList();

        for (ContactsListItem item : contacts)
        {
            if (item.getId() == id)
            {
                return new ChatsListItem(item.getContactName(), item.getThumb(), item.getId());
            }
        }

        return new ChatsListItem("null", null, -1);
    }

    public void createChatTable(int chatID)
    {
        dbHelper.createChatTable(chatID);
    }

    public void resetDb(Context context)
    {
        dbHelper.resetDb(context);
    }
}
