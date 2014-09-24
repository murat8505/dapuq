package com.sendmedia.opiummks;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("NewApi")
public class FirstLoadPage extends Fragment {
	
	public FirstLoadPage(){}
	
	ProgressDialog mProgress;
	WebView myWebView;
	
	final static String home = "http://opium-makassar.com/progress/";
	String myUrl;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.web_fragment,
	            container, false);
		

	    myWebView = (WebView)rootView.findViewById(R.id.webview1);
	    myWebView.getSettings().setJavaScriptEnabled(true);
	    myWebView.setWebViewClient(new MyWebViewClient());
	    

	    if (myUrl == null) {
	        myUrl = home;
	    }
	    
	    mProgress = ProgressDialog.show(getActivity(), "Loading",
				"Please wait for a moment...");
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
			super.onPageFinished(view, url);
			if (mProgress.isShowing()) {
				mProgress.dismiss();
			}
		}
	}
}
