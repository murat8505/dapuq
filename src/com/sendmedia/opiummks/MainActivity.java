package com.sendmedia.opiummks;

import java.util.ArrayList;
import java.util.List;

import com.sendmedia.opiummks.fragment.FirstLoadPage;
import com.sendmedia.opiummks.fragment.PageMain;
import com.sendmedia.opiummks.fragment.PageFacebook;
import com.sendmedia.opiummks.fragment.PageInstagram;
import com.sendmedia.opiummks.fragment.PageIssuu;
import com.sendmedia.opiummks.fragment.PageTwitter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class MainActivity extends ActionBarActivity {

	// deklarasi title, icon dan url menggunakan string array
	String[] menutitles;
	// TypedArray menuIcons;
	String[] pageUrl;

	String FirstPage;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView sliding_listview;

	private List<RowItem> rowItems;
	private CustomAdapter adapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// menampung string array ke variable
		menutitles = getResources().getStringArray(R.array.titles);
		// menuIcons = getResources().obtainTypedArray(R.array.icons);
		pageUrl = getResources().getStringArray(R.array.pageurl);

		// tampilan drawer
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		sliding_listview = (ListView) findViewById(R.id.drawer_list);

		rowItems = new ArrayList<RowItem>();
		// add menu menggunakan list view custom adapter
		for (int i = 0; i < menutitles.length; i++) {
			RowItem items = new RowItem(menutitles[i], pageUrl[i]);
			rowItems.add(items);
		}

		// menuIcons.recycle();

		adapter = new CustomAdapter(getApplicationContext(), rowItems);

		sliding_listview.setAdapter(adapter);

		sliding_listview.setOnItemClickListener(new SlideitemListener());
		sliding_listview.setSelector(R.drawable.list_view_selector);

		// Enabling Home button
		getSupportActionBar().setHomeButtonEnabled(true);

		// Enabling Up navigation
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		BitmapDrawable background = new BitmapDrawable(
				BitmapFactory.decodeResource(getResources(),
						R.drawable.action_bg));
		background.setTileModeX(android.graphics.Shader.TileMode.REPEAT);
		getSupportActionBar().setBackgroundDrawable(background);
		getSupportActionBar().setIcon(R.color.transparent);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.main_name, R.string.main_name) {

			public void onDrawerClosed(View view) {
				(new Handler()).postDelayed(new Runnable() {
					@Override
					public void run() {
						getSupportActionBar().setTitle(null);
						// calling onPrepareOptionsMenu() to show action bar
						// icons
						supportInvalidateOptionsMenu();
					}
				}, 200);
			}

			public void onDrawerOpened(View drawerView) {
				(new Handler()).postDelayed(new Runnable() {
					@Override
					public void run() {
						getSupportActionBar().setTitle(null);
					}
				}, 200);

			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			// updateDisplay(0);

			// initialisasi web view fragment
			android.support.v4.app.Fragment fragment1 = new FirstLoadPage();

			// menampilkan web view fragment ke activity_main.xml
			android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment1).commit();
			// setTitle(R.string.main_name);
			setTitle(null);

		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// cek jika drawer layout aktif dan tombol back di tekan maka drawer di
		// tutup
		if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		} else {
			super.onBackPressed();
		}
	}

	class SlideitemListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			updateDisplay(position);

			// grep selected menu from custom adapter
			((CustomAdapter) sliding_listview.getAdapter())
					.selectItem(position);
			((ListView) parent).invalidateViews();
		}

	}

	private void updateDisplay(int position) {

		// add url dari string array
		String url = rowItems.get(position).getPageUrl();

		// initialisasi web view fragment
		android.support.v4.app.Fragment fragment = new PageMain();

		// menambahkan string url
		Bundle bundle = new Bundle();
		bundle.putString("url", url);

		fragment.setArguments(bundle);

		// menampilkan web view fragment ke activity_main.xml
		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// set title action bar dari method set title

		// setTitle(menutitles[position]);
		setTitle(null);

		// close sliding menu setelah link list view di klik
		mDrawerLayout.closeDrawer(sliding_listview);

	}

	@Override
	public void setTitle(CharSequence title) {
		getSupportActionBar().setTitle(null);
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

		// facebook
		case R.id.facebook:
			Facebook();
			return true;

			// instagram
		case R.id.insta:
			Instagram();
			return true;

			// issu
		case R.id.issuu:
			Issuu();
			return true;

			// twitter
		case R.id.twitter:
			Twitter();
			return true;

		case R.id.about:
			about();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void Facebook() {

		// initialisasi web view fragment
		android.support.v4.app.Fragment facebook = new PageFacebook();

		// menampilkan web view fragment ke activity_main.xml
		android.support.v4.app.FragmentManager facebook_manager = getSupportFragmentManager();
		facebook_manager.beginTransaction()
				.replace(R.id.content_frame, facebook).commit();
		setTitle(null);
	}

	public void Instagram() {

		// initialisasi web view fragment
		android.support.v4.app.Fragment instagram = new PageInstagram();

		// menampilkan web view fragment ke activity_main.xml
		android.support.v4.app.FragmentManager instagram_manager = getSupportFragmentManager();
		instagram_manager.beginTransaction()
				.replace(R.id.content_frame, instagram).commit();
		setTitle(null);
	}

	public void Issuu() {
		// initialisasi web view fragment
		android.support.v4.app.Fragment issuu = new PageIssuu();

		// menampilkan web view fragment ke activity_main.xml
		android.support.v4.app.FragmentManager issuu_manager = getSupportFragmentManager();
		issuu_manager.beginTransaction().replace(R.id.content_frame, issuu)
				.commit();
		setTitle(null);
	}

	public void Twitter() {
		// initialisasi web view fragment
		android.support.v4.app.Fragment twitter = new PageTwitter();

		// menampilkan web view fragment ke activity_main.xml
		android.support.v4.app.FragmentManager twitter_manager = getSupportFragmentManager();
		twitter_manager.beginTransaction().replace(R.id.content_frame, twitter)
				.commit();
		setTitle(null);
	}

	// showing about on pop up notification

	public void about() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertadd = new AlertDialog.Builder(
		                MainActivity.this);
		alertadd.setTitle("About");

		LayoutInflater factory = LayoutInflater.from(MainActivity.this);
		final View view = factory.inflate(R.layout.about, null);

		ImageView image= (ImageView) view.findViewById(R.id.imageView);
		image.setImageResource(R.drawable.ic_about);
		TextView text= (TextView) view.findViewById(R.id.textView);
		text.setText("Contact Developer : \n"
					+"\t\t email: info@sendmedia.co.id\n "
					+"\t\t website: sendmedia.co.id\n"
					+ "Opium Makassar Mobile\n"
					+"Version 1.0");

		alertadd.setView(view);
		alertadd.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dlg, int sumthin) {

		                }
		            });

		alertadd.show();

	}

	/** Called whenever we call supportInvalidateOptionsMenu(); */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(sliding_listview);

		menu.findItem(R.id.about).setVisible(!drawerOpen);
		menu.findItem(R.id.facebook).setVisible(!drawerOpen);
		menu.findItem(R.id.insta).setVisible(!drawerOpen);
		menu.findItem(R.id.issuu).setVisible(!drawerOpen);
		menu.findItem(R.id.twitter).setVisible(!drawerOpen);
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
