package com.richardrudolph.simplechatter;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatActivityFragment extends Fragment implements OnClickListener
{
    private EditText messageEntry;
    private SharedPreferences prefs;
    private DateFormat date = new SimpleDateFormat("d MM yyyy");
    private DateFormat time = new SimpleDateFormat("HH:mm:ss");
    private ChatMessageAdapter chatMessageAdapter;
    SimpleChatterDataWorker dataWorker;

    public ChatActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        dataWorker = new SimpleChatterDataWorker(getContext());

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);


        ListView messageListView = (ListView) rootView.findViewById(R.id.chat_messages);
        ArrayList<ChatMessage> messageList = new ArrayList<ChatMessage>();
        chatMessageAdapter = new ChatMessageAdapter(getActivity(), messageList, getContext());
        messageListView.setAdapter(chatMessageAdapter);
        ImageButton sendButton = (ImageButton) rootView.findViewById(R.id.message_send);
        sendButton.setOnClickListener(this);
        messageEntry = (EditText) rootView.findViewById(R.id.message_entry);

        messageListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        messageListView.setStackFromBottom(true);

        ArrayList<ChatMessage> dbMessageList = dataWorker.getMessageList(getActivity().getIntent
            ().getExtras().getString("chatTable"));
        for (ChatMessage item : dbMessageList)
        {
            chatMessageAdapter.add(item);
        }
        chatMessageAdapter.notifyDataSetChanged();

        return rootView;
    }

    public void sendMessage(View view)
    {
        String message = messageEntry.getEditableText().toString();
        if (!message.equalsIgnoreCase(""))
        {
            final ChatMessage chatMessage = new ChatMessage(message, 0, getActivity().getIntent()
                .getExtras().getInt("receiverId"), chatMessageAdapter.getCount(), date.format
                (Calendar.getInstance().getTime()), time.format(Calendar.getInstance().getTime()));
            messageEntry.setText("");
            chatMessageAdapter.add(chatMessage);
            chatMessageAdapter.notifyDataSetChanged();
            ContentValues messageValues = new ContentValues();
            messageValues.put(SimpleChatterContract.Chat.COLUMN_MESSAGE_TEXT, message);
            messageValues.put(SimpleChatterContract.Chat.COLUMN_SENDER_ID, 0);
            messageValues.put(SimpleChatterContract.Chat.COLUMN_RECEIVER_ID, getActivity()
                .getIntent().getExtras().getInt("receiverId"));
            messageValues.put(SimpleChatterContract.Chat.COLUMN_DATE, date.format(Calendar
                .getInstance().getTime()));
            messageValues.put(SimpleChatterContract.Chat.COLUMN_TIME, time.format(Calendar
                .getInstance().getTime()));
            Log.v("chat table on data put", "chat table: " + getActivity().getIntent().getExtras
                ().getString("chatTable"));
            dataWorker.writeData(getActivity().getIntent().getExtras().getString("chatTable"), messageValues);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.message_send:
                sendMessage(view);
        }
    }
}
