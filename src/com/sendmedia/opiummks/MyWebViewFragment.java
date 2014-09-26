package com.sendmedia.opiummks;


import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.handmark.pulltorefresh.library.PullToRefreshWebView;

public class MyWebViewFragment extends Fragment {

	public MyWebViewFragment(){}
	
	ProgressBar progress;
	PullToRefreshWebView mPullRefreshWebView;
	WebView webview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.web_fragment, container,
				false);

		Bundle bundle = getArguments();

		String url = bundle.getString("url");

		//pull down to refresh using Android-PullToRefresh Library
		mPullRefreshWebView = (PullToRefreshWebView) rootView.findViewById(R.id.pull_refresh_webview);
		
		webview = mPullRefreshWebView.getRefreshableView();
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new MyWebViewClient());

		//show progress bar when web loading
		progress = (ProgressBar) rootView.findViewById(R.id.progressBar);
		progress.setVisibility(View.GONE);
		
		webview.loadUrl(url);

		
		webview.requestFocus(View.FOCUS_DOWN);
		
		//showing up keyboard on focus
	    webview.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            switch (event.getAction()) {
	                case MotionEvent.ACTION_DOWN:
	                case MotionEvent.ACTION_UP:
	                    if (!v.hasFocus()) {
	                        v.requestFocus();
	                    }
	                    break;
	            }
	            return false;
	        }
	    });

		return rootView;
	}

	private class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url); 
	        return true;
	    }
	    
		@Override
		public void onPageFinished(WebView view, String url) {
			progress.setVisibility(View.GONE);
			MyWebViewFragment.this.progress.setProgress(100);
		    super.onPageFinished(view, url);
			
		}
		
		 @Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			progress.setVisibility(View.VISIBLE);
			MyWebViewFragment.this.progress.setProgress(0);
			super.onPageStarted(view, url, favicon);
		}
	}
}