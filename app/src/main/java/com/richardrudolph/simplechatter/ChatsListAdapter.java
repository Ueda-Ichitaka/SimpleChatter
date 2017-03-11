package com.richardrudolph.simplechatter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Richard on 11.03.2017.
 */

public class ChatsListAdapter extends BaseAdapter
{
    private static LayoutInflater inflater = null;
    ArrayList<String> chatsList;

    public ChatsListAdapter(Activity activity, ArrayList<String> list)
    {
        chatsList = list;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return chatsList.size();
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
        String chatTitle = (String) chatsList.get(position);
        View view = convertView;
        if (convertView == null)
        {
            view = inflater.inflate(R.layout.layout_chat_selector, null);
        }

        TextView title = (TextView) view.findViewById(R.id.chat_name);
        title.setText(chatTitle);

        return view;
    }

    public void add(String object)
    {
        chatsList.add(object);
    }
}
