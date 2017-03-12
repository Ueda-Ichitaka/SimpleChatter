package com.richardrudolph.simplechatter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Richard on 11.03.2017.
 */

public class ChatsListAdapter extends BaseAdapter
{
    private static LayoutInflater inflater = null;
    ArrayList<ChatsListItem> chatsList;

    public ChatsListAdapter(Activity activity, ArrayList<ChatsListItem> list)
    {
        this.chatsList = list;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return chatsList.size();
    }

    @Override
    public ChatsListItem getItem(int position)
    {
        return chatsList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ChatsListItem chat = (ChatsListItem) chatsList.get(position);
        View view = convertView;
        if (convertView == null)
        {
            view = inflater.inflate(R.layout.layout_chat_selector, null);
        }

        TextView title = (TextView) view.findViewById(R.id.chat_name);
        title.setText(chat.getChatName());
        ImageView thumb = (ImageView) view.findViewById(R.id.chat_image);
        if (chat.getThumb() != null)
        {
            thumb.setBackground((Drawable) chat.getThumb());
        }

        return view;
    }

    public void add(ChatsListItem object)
    {
        chatsList.add(object);
    }
}
