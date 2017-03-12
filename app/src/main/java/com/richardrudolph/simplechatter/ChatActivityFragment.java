package com.richardrudolph.simplechatter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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

    public ChatActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
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

        return rootView;
    }

    public void sendMessage(View view)
    {
        String message = messageEntry.getEditableText().toString();
        if (!message.equalsIgnoreCase(""))
        {
            final ChatMessage chatMessage = new ChatMessage(message, prefs.getString("pref_account_name", "Anonymous"), getActivity().getIntent().getExtras().getString("receiver"), chatMessageAdapter.getCount(), date.format(Calendar.getInstance().getTime()), time.format(Calendar.getInstance().getTime()));
            messageEntry.setText("");
            chatMessageAdapter.add(chatMessage);
            chatMessageAdapter.notifyDataSetChanged();
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
