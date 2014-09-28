package com.sendmedia.opiummks;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

@SuppressLint("NewApi")
public class FirstLoadPage extends Fragment {
	
	public FirstLoadPage(){}
	
	ProgressBar progress;
	PullToRefreshWebView mPullRefreshWebView;
	WebView myWebView;
	
	final static String home = "http://www.opium-makassar.com/progress/mobile";
	String myUrl;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.web_fragment,
	            container, false);
		
        mPullRefreshWebView = (PullToRefreshWebView) rootView.findViewById(R.id.pull_refresh_webview);
		
		myWebView = mPullRefreshWebView.getRefreshableView();
	    myWebView.getSettings().setJavaScriptEnabled(true);
	    myWebView.setWebViewClient(new MyWebViewClient());
	    

	    if (myUrl == null) {
	        myUrl = home;
	    }
	    
		progress = (ProgressBar) rootView.findViewById(R.id.progressBar);
		progress.setVisibility(View.GONE);
		
	    myWebView.loadUrl(myUrl);
	    
        myWebView.requestFocus(View.FOCUS_DOWN);
		
		//showing up keyboard on focus
	    myWebView.setOnTouchListener(new View.OnTouchListener() {
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
			myUrl = url;
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			progress.setVisibility(View.GONE);
			FirstLoadPage.this.progress.setProgress(100);
		    super.onPageFinished(view, url);
			
		}
		
		 @Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			progress.setVisibility(View.VISIBLE);
			FirstLoadPage.this.progress.setProgress(0);
			super.onPageStarted(view, url, favicon);
		}
	}
}
