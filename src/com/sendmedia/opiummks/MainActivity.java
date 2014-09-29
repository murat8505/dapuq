package com.sendmedia.opiummks;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity  {
 
	//deklarasi title, icon dan url menggunakan string array
	String[] menutitles;
	//TypedArray menuIcons;
	String[] pageUrl;
	
	String FirstPage;
	
	
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView sliding_listview;
    

	private CharSequence mTitle;
	
	private List<RowItem> rowItems;
	private CustomAdapter adapter;
    
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        mTitle = getTitle();
        
        
        //menampung string array ke variable
        menutitles = getResources().getStringArray(R.array.titles);
		//menuIcons = getResources().obtainTypedArray(R.array.icons);
		pageUrl = getResources().getStringArray(R.array.pageurl);
        
        //tampilan drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        sliding_listview = (ListView) findViewById(R.id.drawer_list);
  
        
        rowItems = new ArrayList<RowItem>();
        //add menu menggunakan list view custom adapter
		for (int i = 0; i < menutitles.length; i++) {
			RowItem items = new RowItem(menutitles[i], pageUrl[i]);
			rowItems.add(items);
		}
		
		//menuIcons.recycle();
		
		adapter = new CustomAdapter(getApplicationContext(), rowItems);

		sliding_listview.setAdapter(adapter);

		sliding_listview.setOnItemClickListener(new SlideitemListener());
		sliding_listview.setSelector(R.drawable.list_view_selector);
		
		// Enabling Home button
        getSupportActionBar().setHomeButtonEnabled(true);

        // Enabling Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cc0000")));
        
        
        
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.main_name,
				R.string.main_name) {
        	
			public void onDrawerClosed(View view) {
				(new Handler()).postDelayed(new Runnable() {
		            @Override
		            public void run() {
					String mTitleFix = (String) mTitle;
					String judul = mTitleFix.replaceAll("\t","");
					//getSupportActionBar().setTitle(judul);
					
					getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + judul + "</font>")));
					// calling onPrepareOptionsMenu() to show action bar icons
					supportInvalidateOptionsMenu();
		            }
		        }, 200);
			}

			public void onDrawerOpened(View drawerView) {
				(new Handler()).postDelayed(new Runnable() {
		            @Override
		            public void run() {
		            	String mTitleFix = (String) mTitle;
						String judul = mTitleFix.replaceAll("\t","");
		                //getSupportActionBar().setTitle(judul);
		                getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + judul + "</font>")));
						// calling onPrepareOptionsMenu() to hide action bar icons
						supportInvalidateOptionsMenu();
		               // updateViews();
		            }
		        }, 200);
				
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			//updateDisplay(0);
			
			//initialisasi web view fragment
			android.support.v4.app.Fragment fragment1 = new FirstLoadPage();
			
			//menampilkan web view fragment ke activity_main.xml
			android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment1).commit();			
			setTitle(R.string.main_name);

		}
    }
    

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
    	//cek jika drawer layout aktif dan tombol back di tekan maka drawer di tutup
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else{
            super.onBackPressed();
        }
    }
    
    class SlideitemListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			updateDisplay(position);
			
			//grep selected menu from custom adapter
			((CustomAdapter) sliding_listview.getAdapter()).selectItem(position);
			((ListView) parent).invalidateViews();
		}

	}
    
	private void updateDisplay(int position) {

		//add url dari string array
		String url = rowItems.get(position).getPageUrl();

		
		//initialisasi web view fragment
		android.support.v4.app.Fragment fragment = new MyWebViewFragment();

		//menambahkan string url
		Bundle bundle = new Bundle();
		bundle.putString("url", url);

		fragment.setArguments(bundle);

		//menampilkan web view fragment ke activity_main.xml
		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		//set title action bar dari method set title

		setTitle(menutitles[position]);
		
		//close sliding menu setelah link list view di klik
		mDrawerLayout.closeDrawer(sliding_listview);

	}
 
	@Override
	public void setTitle(CharSequence title) {
		
		//mengirim value string mtitle ke update display
		mTitle = title;
		getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + mTitle + "</font>")));
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.about:
			about();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    }
    
    //showing about on pop up notification
    
	public void about (){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false)
		       .setTitle("About")
		       .setMessage(
		    		"Opium Makassar Mobile version 1.0\n" +
		       		"Development by : Send Media Indonesia\n\n\n\n\n\n\n" +
		    		"+--------------------------------+\n" +
		       		"   Contact Developer:\n" +
		       		"   sendmedia99@gmail.com\n" +
		       		"+--------------------------------+")
		       .setIcon(R.drawable.ic_about)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}

	/** Called whenever we call supportInvalidateOptionsMenu(); */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(sliding_listview);

        menu.findItem(R.id.about).setVisible(!drawerOpen);
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
