package com.richardrudolph.simplechatter;

import android.provider.BaseColumns;

/**
 * Created by Richard on 15.03.2017.
 */

public final class SimpleChatterContract
{
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private SimpleChatterContract()
    {
    }

    /* Inner class that defines contact table contents */
    public static class Contact implements BaseColumns
    {
        public static final String TABLE_NAME = "table_contacts";
        public static final String COLUMN_NAME = "contact_name";
        public static final String COLUMN_THUMB = "contact_thumb";
    }

    public static class Chats implements BaseColumns
    {
        public static final String TABLE_NAME = "table_chats";
        public static final String COLUMN_CONTACT = "contact_id";
        public static final String COLUMN_TABLE = "table_chat";
    }

    public static class Chat implements BaseColumns
    {
        public static final String TABLE_NAME = "table_chat_";
        public static final String COLUMN_MESSAGE_TEXT = "message_text";
        public static final String COLUMN_SENDER_ID = "sender_id";
        public static final String COLUMN_RECEIVER_ID = "receiver_id";
        public static final String COLUMN_DATE = "timestamp_date";
        public static final String COLUMN_TIME = "timestamp_time";
    }
}
