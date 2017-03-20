package com.richardrudolph.simplechatter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Richard on 11.03.2017.
 */

public class ChatMessageAdapter extends BaseAdapter
{
    private static LayoutInflater inflater = null;
    private ArrayList<ChatMessage> messageList;
    private Context context;
    private SharedPreferences prefs;

    public ChatMessageAdapter(Activity activity, ArrayList<ChatMessage> list, Context context)
    {
        this.context = context;
        this.messageList = list;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public int getCount()
    {
        return messageList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ChatMessage message = (ChatMessage) messageList.get(position);
        View view = convertView;
        if (convertView == null)
        {
            view = inflater.inflate(R.layout.layout_chat_message, null);
        }

        TextView messageText = (TextView) view.findViewById(R.id.message_text);
        messageText.setText(message.getMessage());
        LinearLayout bubble_layout = (LinearLayout) view.findViewById(R.id.bubble_layout);
        LinearLayout bubble_layout_parent = (LinearLayout) view.findViewById(R.id.bubble_layout_parent);

        if (message.getSenderId() == 0)    //        if (message.isMine(prefs.getString
        // ("pref_account_name", "Anonymous")))
        {
            if (prefs.getString("pref_theme", "0").equals("0"))
            {
                bubble_layout.setBackgroundResource(R.drawable.bubble_left_light);
            } else if (prefs.getString("pref_theme", "0").equals("1"))
            {
                bubble_layout.setBackgroundResource(R.drawable.bubble_left_dark);
            }
            bubble_layout_parent.setGravity(Gravity.RIGHT);
        } else
        {
            if (prefs.getString("pref_theme", "0").equals("0"))
            {
                bubble_layout.setBackgroundResource(R.drawable.bubble_right_light);
            } else if (prefs.getString("pref_theme", "0").equals("1"))
            {
                bubble_layout.setBackgroundResource(R.drawable.bubble_right_dark);
            }
            bubble_layout_parent.setGravity(Gravity.LEFT);
        }

        return view;
    }

    public void add(ChatMessage object)
    {
        messageList.add(object);
    }
}
