package com.richardrudolph.simplechatter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
        final SimpleChatterDataWorker dataWorker = new SimpleChatterDataWorker(this);

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
        if (id == R.id.action_add_contact)
        {
            //TODO implement
            return true;
        }
        if (id == R.id.action_clear_db)
        {
            //TODO drop all tables,
            dataWorker.resetDb(this);
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
            if (currentFragment instanceof ContactsFragment)
            {
                FragmentTransaction fragTransaction = getSupportFragmentManager()
                    .beginTransaction();
                fragTransaction.detach(currentFragment);
                fragTransaction.attach(currentFragment);
                fragTransaction.commit();
            }
            Fragment currentFragment2 = getSupportFragmentManager().findFragmentById(R.id
                .container);
            if (currentFragment2 instanceof ChatsFragment)
            {
                FragmentTransaction fragTransaction2 = getSupportFragmentManager()
                    .beginTransaction();
                fragTransaction2.detach(currentFragment2);
                fragTransaction2.attach(currentFragment2);
                fragTransaction2.commit();
            }
            return true;
        }
        if (id == R.id.action_clear_chats)
        {
            return true;
        }
        if (id == R.id.action_add_demo_contacts)
        {
            dataWorker.testData();
            //TODO refresh fragment
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
            if (currentFragment instanceof ContactsFragment)
            {
                FragmentTransaction fragTransaction = getSupportFragmentManager()
                    .beginTransaction();
                fragTransaction.detach(currentFragment);
                fragTransaction.attach(currentFragment);
                fragTransaction.commit();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ChatsFragment extends Fragment
    {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public ChatsFragment()
        {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ChatsFragment newInstance()
        {
            ChatsFragment fragment = new ChatsFragment();

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            final SimpleChatterDataWorker dataWorker = new SimpleChatterDataWorker(getContext());

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
                    Intent contact = new Intent(getActivity(), ChatActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("chatTable", dataWorker.getChatTable(chatsListAdapter
                        .getItem(position).getId()));
                    bundle.putString("receiverName", chatsListAdapter.getItem(position)
                        .getChatName());
                    bundle.putInt("receiverId", chatsListAdapter.getItem(position).getId());
                    contact.putExtras(bundle);
                    startActivity(contact);
                }
            });


            ArrayList<ChatsListItem> dbChatsList = dataWorker.getChatsList();
            for (ChatsListItem item : dbChatsList)
            {
                chatsListAdapter.add(item);
            }


            //chatsListAdapter.add(new ChatsListItem("Test 1", null, 0));
            //chatsListAdapter.add(new ChatsListItem("Test 2", null, 1));
            //chatsListAdapter.add(new ChatsListItem("Test 3", null, 2));
            chatsListAdapter.notifyDataSetChanged();


            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ContactsFragment extends Fragment
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        //private SimpleChatterDataWorker dataWorker;
        public ContactsFragment()
        {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ContactsFragment newInstance()
        {
            ContactsFragment contactFragment = new ContactsFragment();

            return contactFragment;
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
                    dataWorker.checkChatEntryExists(contactsListAdapter.getItem(position).getId());
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

            //dataWorker.testData();

            ArrayList<ContactsListItem> dbContactsList = dataWorker.getContactList();
            for (ContactsListItem item : dbContactsList)
            {
                contactsListAdapter.add(item);
            }

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
                    return ChatsFragment.newInstance();
                case 1:
                    return ContactsFragment.newInstance();
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
