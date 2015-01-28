package google.codeforindia.haiyya;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(position==1){fragmentManager.beginTransaction()
                .replace(R.id.container, PoliceStationFragment.newInstance(position + 1))
                .commit();}
            else if(position==2){fragmentManager.beginTransaction()
                .replace(R.id.container, FavouritesFragment.newInstance(position + 1))
                .commit();}
            else {
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();}
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void btnHelp(View v)
    {
        /*TODO
                        Send a message to this phonenumber
                    * */
        Intent intent=new Intent(MainActivity.this,MapsActivity.class);
        startActivity(intent);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    public static class PoliceStationFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PoliceStationFragment newInstance(int sectionNumber) {
            PoliceStationFragment fragment = new PoliceStationFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PoliceStationFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_policestations, container, false);
            List<String> list1 = new ArrayList<>();
            for (int i = 0; i < SplashActivity.markerList.size(); i++) {
                list1.add(SplashActivity.markerList.get(i).name + ":" + SplashActivity.markerList.get(i).phonenumber);
                System.out.println(SplashActivity.markerList.get(i).name + ":" + SplashActivity.markerList.get(i).phonenumber);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(),
                    android.R.layout.simple_list_item_1, list1);
            ListView listView = (ListView) rootView.findViewById(R.id.listPoliceStations);
            listView.setAdapter(adapter);
            return rootView;
        }
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    public static class FavouritesFragment extends Fragment {
        ContactsDbHelper contactDb;
        LayoutInflater lay;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static FavouritesFragment newInstance(int sectionNumber) {
            FavouritesFragment fragment = new FavouritesFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public FavouritesFragment() {
        }
        View rView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
            contactDb=new ContactsDbHelper(rootView.getContext());
            lay=getLayoutInflater(savedInstanceState);
            rView=rootView;
            Retrieve();
            ImageButton imgAdd=(ImageButton)rootView.findViewById(R.id.imgAdd);
            imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    // Get the layout inflater
                    LayoutInflater inflater = lay;
                    //Create our dialog view
                    View dialogView = inflater.inflate(R.layout.addcontact, null);
                    final EditText txtName = (EditText) dialogView.findViewById(R.id.txtName);
                    final EditText txtNumber = (EditText) dialogView.findViewById(R.id.txtNumber);
                    builder.setView(dialogView)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (!(txtName.getText().toString().equals("") || txtNumber.getText().toString().equals(""))) {
                                        Contact ob = new Contact();
                                        ob.Name = txtName.getText().toString();
                                        ob.Number = txtNumber.getText().toString();
                                        contactDb.addContact(ob);
                                        System.out.println("Added");
                                        Retrieve();
                                        dialog.dismiss();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                }
            });
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
        public void Retrieve()
        {
            final List<Contact> list=contactDb.getAllTasks();
            System.out.println(list.size());
            TextView txt=(TextView)rView.findViewById(R.id.textView);
            if(list.size()==0){

                txt.setVisibility(View.VISIBLE);
            }
            else {
                txt.setVisibility(View.INVISIBLE);
                List<String> list1 = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    list1.add(list.get(i).Name + "->" + list.get(i).Number);
                    System.out.println(list.get(i).Name + ":" + list.get(i).Number);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(rView.getContext(),
                        android.R.layout.simple_list_item_1, list1);
                ListView listView = (ListView) rView.findViewById(R.id.listFavourites);
                listView.setAdapter(adapter);
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos=position;
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        // Get the layout inflater
                        LayoutInflater inflater = lay;
                        //Create our dialog view
                        View dialogView = inflater.inflate(android.R.layout.select_dialog_item, null);
                        builder.setView(dialogView)
                                .setTitle("Delete the Contact")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        contactDb.deleteTask(list.get(pos));
                                        dialog.dismiss();
                                        Retrieve();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder.show();
                        return false;
                    }
                });
            }
        }
    }
}
