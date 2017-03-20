package com.richardrudolph.simplechatter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString("pref_theme", "0");
        if (theme.equals("0"))
        {
            setTheme(R.style.AppTheme_NoActionBar);
        }
        if (theme.equals("1"))
        {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderChatFragment extends Fragment
    {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public PlaceholderChatFragment()
        {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderChatFragment newInstance()
        {
            PlaceholderChatFragment fragment = new PlaceholderChatFragment();

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_chats, container, false);

            ListView chatsListView = (ListView) rootView.findViewById(R.id.list_chats);
            ArrayList<ChatsListItem> chatsList = new ArrayList<ChatsListItem>();
            final ChatsListAdapter chatsListAdapter = new ChatsListAdapter(getActivity(), chatsList);
            chatsListView.setAdapter(chatsListAdapter);

            chatsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    //TODO put chat id in bundle
                    Intent chat = new Intent(getActivity(), ChatActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("receiver", chatsListAdapter.getItem(position).getChatName());
                    chat.putExtras(bundle);
                    startActivity(chat);
                }
            });

            //TODO replace with load routine. check db table table_chats (chat_id:contact_id)
            // create new items with data in table table_contacts
            //for(entries in table table_chats)
            //chatsListAdapter.add(new ChatsListItem(table_contacts.contact_name(table_chats
            // .contact_id([chat_id])), table_contacts.contact_id.contact_thumb, chat_id));

            chatsListAdapter.add(new ChatsListItem("Test 1", null, 0));
            chatsListAdapter.add(new ChatsListItem("Test 2", null, 1));
            chatsListAdapter.add(new ChatsListItem("Test 3", null, 2));
            chatsListAdapter.notifyDataSetChanged();


            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderContactsFragment extends Fragment
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        //private SimpleChatterDataWorker dataWorker;

        public PlaceholderContactsFragment()
        {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderContactsFragment newInstance()
        {
            PlaceholderContactsFragment fragment = new PlaceholderContactsFragment();

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
                savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

            ListView contactsListView = (ListView) rootView.findViewById(R.id.list_contacts);
            ArrayList<ContactsListItem> contactsList = new ArrayList<ContactsListItem>();
            final ContactsListAdapter contactsListAdapter = new ContactsListAdapter(getActivity()
                    , contactsList);
            contactsListView.setAdapter(contactsListAdapter);

            final SimpleChatterDataWorker dataWorker = new SimpleChatterDataWorker(getContext());

            contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    //TODO check if entry exists in chats table with contact_id ==
                    // contactslistitem.id
                    //TODO if entry not exist create entry and create chat table

                    //TODO put table_chat in bundle
                    Intent contact = new Intent(getActivity(), ChatActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("chatTable", dataWorker.getChatTable(contactsListAdapter
                        .getItem(position).getId()));
                    bundle.putString("receiverName", contactsListAdapter.getItem(position)
                            .getContactName());
                    bundle.putInt("receiverId", contactsListAdapter.getItem(position).getId());
                    contact.putExtras(bundle);
                    startActivity(contact);
                }
            });

            //TODO load contacts from db
            //TODO add entry in db table_chats with contact_id from table_contacts if not exist


            //dataWorker.testData();

            ArrayList<ContactsListItem> dbContactsList = dataWorker.getContactList();
            for (ContactsListItem item : dbContactsList)
            {
                contactsListAdapter.add(item);
            }

            //contactsListAdapter.add(new ContactsListItem("Test 4", null, 0));
            //contactsListAdapter.add(new ContactsListItem("Test 5", null, 1));
            //contactsListAdapter.add(new ContactsListItem("Test 6", null, 2));
            contactsListAdapter.notifyDataSetChanged();

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position)
            {
                case 0:
                    return PlaceholderChatFragment.newInstance();
                case 1:
                    return PlaceholderContactsFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount()
        {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0:
                    return "CHATS";
                case 1:
                    return "CONTACTS";
            }
            return null;
        }
    }
}
