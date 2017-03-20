package com.richardrudolph.simplechatter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Richard on 15.03.2017.
 */

public class SimpleChatterDbHelper extends SQLiteOpenHelper
{

    private static final String SQL_CREATE_CONTACT_TABLE = "CREATE TABLE " +
        SimpleChatterContract.Contact.TABLE_NAME + " (" + SimpleChatterContract.Contact._ID + " "
        + "INTEGER NOT NULL PRIMARY KEY UNIQUE," + "" + SimpleChatterContract.Contact.COLUMN_NAME
        + " TEXT NOT NULL," + SimpleChatterContract.Contact.COLUMN_THUMB + " " + "TEXT)";

    private static final String SQL_CREATE_CHATS_TABLE = "CREATE TABLE " + SimpleChatterContract
        .Chats.TABLE_NAME + "" + " (" + SimpleChatterContract.Chats._ID + " INTEGER NOT NULL " +
        "" + "PRIMARY KEY " + "UNIQUE," + SimpleChatterContract.Chats.COLUMN_CONTACT + " " +
        "INTEGER " + "NOT NULL UNIQUE," + SimpleChatterContract.Chats.COLUMN_TABLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
        SimpleChatterContract.Contact.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SimpleChatter.db";

    public SimpleChatterDbHelper(Context context)
    {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase("simpleChatter.db");
        //context.deleteDatabase("SimpleChatter.db");
        Log.v(SimpleChatterDbHelper.class.getSimpleName(), "db " + getDatabaseName() + " erzeugt");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_CONTACT_TABLE);
        db.execSQL(SQL_CREATE_CHATS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void createChatTable(int id)
    {
        final String SQL_CREATE_CHAT_TABLE = "CREATE TABLE IF NOT EXIST " + SimpleChatterContract
            .Chat.TABLE_NAME + Integer.toString(id) + " (" + SimpleChatterContract.Chat._ID + " "
            + "INTEGER NOT" + " NULL PRIMARY KEY " + "UNIQUE," + SimpleChatterContract.Chat
            .COLUMN_MESSAGE_TEXT + " TEXT NOT NULL," + SimpleChatterContract.Chat
            .COLUMN_SENDER_ID + " INTEGER NOT" + " NULL," + SimpleChatterContract.Chat
            .COLUMN_RECEIVER_ID + " INTEGER " + "NOT " + "NULL," + SimpleChatterContract.Chat
            .COLUMN_DATE + " TEXT NOT NULL," + SimpleChatterContract.Chat.COLUMN_TIME + " " +
            "TEXT NOT NULL" + " UNIQUE)";

        getWritableDatabase().execSQL(SQL_CREATE_CHAT_TABLE);
    }


}
