package com.du.gout_app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.du.gout_app.R;
import com.du.gout_app.common.Constants;

public class everyweek_fragment extends Fragment {
	 public WebView webview;
	 String tk;
	 Button add_button;
	MaterialRefreshLayout mrl;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstanceState){
		View v =inflater.inflate(R.layout.everyweek_fragment_layout,parent,false);
		SharedPreferences preferences=getActivity().getSharedPreferences("token",Context.MODE_PRIVATE);
		tk=preferences.getString("token", "none");
		
		mrl = (MaterialRefreshLayout) v.findViewById(R.id.week_swipe_container);
		mrl.setLoadMore(false);
		mrl.setMaterialRefreshListener(new MaterialRefreshListener() {
			@Override
			public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						webview.loadUrl(webview.getUrl());
						mrl.finishRefresh();
						Toast.makeText(getActivity(), "刷新成功!", Toast.LENGTH_LONG).show();
					}
				}, 1000);
			}
		});
		webview = (WebView) v.findViewById(R.id.everyweek_webview);
		webview.getSettings().setJavaScriptEnabled(true);  
		webview.loadUrl(Constants.url + "/html/week_news_list.html?token=" + tk);
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
		webview.requestFocus();
		add_button =(Button) v.findViewById(R.id.add_week);
			add_button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					webview.loadUrl(Constants.url+"/html/week_news.html?token="+tk);
				}
			});
		return v;
	
	}

  

}
