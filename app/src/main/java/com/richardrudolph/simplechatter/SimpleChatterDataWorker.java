package com.richardrudolph.simplechatter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public void clearDB()
    {
        database = dbHelper.getWritableDatabase();
        database.execSQL("delete * from " + SimpleChatterContract.Contact.TABLE_NAME);
    }

    public void createChatTable(int chatID)
    {
        dbHelper.createChatTable(chatID);
    }
}
