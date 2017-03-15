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

public class ContactsListAdapter extends BaseAdapter
{
    private static LayoutInflater inflater = null;
    ArrayList<ContactsListItem> contactsList;

    public ContactsListAdapter(Activity activity, ArrayList<ContactsListItem> list)
    {
        this.contactsList = list;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return contactsList.size();
    }

    @Override
    public ContactsListItem getItem(int position)
    {
        return contactsList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ContactsListItem contact = (ContactsListItem) contactsList.get(position);
        View view = convertView;
        if (convertView == null)
        {
            view = inflater.inflate(R.layout.layout_contact_selector, null);
        }

        TextView title = (TextView) view.findViewById(R.id.contact_name);
        title.setText(contact.getContactName());
        ImageView thumb = (ImageView) view.findViewById(R.id.contact_image);
        if (contact.getThumb() != null)
        {
            thumb.setBackground((Drawable) contact.getThumb());
        }

        return view;
    }

    public void add(ContactsListItem object)
    {
        contactsList.add(object);
    }
}
