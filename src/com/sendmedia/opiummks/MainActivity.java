package com.sendmedia.opiummks;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.SlidingDrawer;
 
public class MainActivity extends ActionBarActivity  {
 
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView sliding_listview;
    private String mTitle;
    
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        mTitle = "Opium Makassar";
        
        //tampilan drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        sliding_listview = (ListView) findViewById(R.id.drawer_list);
  
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_navigation_drawer, R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when drawer is closed */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();

            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
            	getSupportActionBar().setTitle("Opium Makassar");
                supportInvalidateOptionsMenu();
            }

        };
     // Setting DrawerToggle on DrawerLayout
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //load string array
        String[] alamat = getResources().getStringArray(
                R.array.alamat);
        
        // Creating an ArrayAdapter to add items to the listview mDrawerList
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
                R.layout.list_item, R.id.list_menu , alamat);

        // Setting the adapter on mDrawerList
        sliding_listview.setAdapter(adapter);

        // Enabling Home button
        getSupportActionBar().setHomeButtonEnabled(true);

        // Enabling Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setting item click listener for the listview mDrawerList
        sliding_listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {

                // Getting an array of rivers
                String[] menuItems = getResources().getStringArray(R.array.alamat);

                // Currently selected river
                mTitle = menuItems[position];


                // Closing the drawer
                mDrawerLayout.closeDrawer(sliding_listview);

            }

        });
 
    }
 
    protected String getUrl(int position) {
        switch (position) {
        case 0:
            return "";
        case 1:
            return "";
        case 2:
            return "";
        case 3:
            return "";
        case 4:
            return "";
        case 5:
            return "";
        case 6:
            return "";
        case 7:
            return "";
        case 8:
            return "";
        case 9:
            return "";
        case 10:
            return "";
        case 11:
            return "";
        case 12:
            return "";
        case 13:
            return "";
        case 14:
            return "";
        case 15:
            return "";
        case 16:
            return "";
        case 17:
            return "";
        case 18:
            return "";
        case 19:
            return "";
        case 20:
            return "";
        case 21:
            return "";
        default:
            return "";
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called whenever we call supportInvalidateOptionsMenu(); */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(sliding_listview);

        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
