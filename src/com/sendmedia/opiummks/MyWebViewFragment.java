package com.sendmedia.opiummks;


import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewFragment extends Fragment {

	public MyWebViewFragment(){}
	
	ProgressDialog mProgress;
	WebView webview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.web_fragment, container,
				false);

		Bundle bundle = getArguments();

		String url = bundle.getString("url");

		webview = (WebView) rootView.findViewById(R.id.webview1);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new MyWebViewClient());

		mProgress = ProgressDialog.show(getActivity(), "Loading",
				"Please wait for a moment...");
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
			super.onPageFinished(view, url);
			if (mProgress.isShowing()) {
				mProgress.dismiss();
			}
		}
	}
}