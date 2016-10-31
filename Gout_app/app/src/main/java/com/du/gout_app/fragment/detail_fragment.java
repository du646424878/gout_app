package com.du.gout_app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.du.gout_app.R;
import com.du.gout_app.common.Constants;

public class detail_fragment extends Fragment {
	private WebView webview;
	Button alter_button;
	String tk;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstanceState){
		View v =inflater.inflate(R.layout.detail_fragment_layout,parent,false);
		SharedPreferences preferences=getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
		tk=preferences.getString("token", "none");
		webview = (WebView) v.findViewById(R.id.user_info_webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.requestFocus();
		webview.loadUrl(Constants.url + "/html/userinfo.html?token="+tk);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});
		webview.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
						webview.goBack();
						return true;
					}
				}
				return false;
			}
		});
		webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

		alter_button =(Button) v.findViewById(R.id.add_month);
		alter_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				webview.loadUrl(Constants.url+"/html/change_userinfo.html?token="+tk);
			}
		});
		return v;
	}

}
