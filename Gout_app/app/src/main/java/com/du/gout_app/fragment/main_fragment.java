package com.du.gout_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import com.du.gout_app.R;
import com.du.gout_app.activity.Seting_Activity;
import com.du.gout_app.common.Constants;
import com.du.gout_app.common.HttpUtil;
import com.du.gout_app.common.Message;
import com.du.gout_app.common.News;
import com.du.gout_app.common.RecyclerViewAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class main_fragment extends Fragment {
	Button seting_button;
	List<News> newslist;
	Map<Integer,Bitmap> map;
	String result;
	int page=1;
	String tk;
	private RecyclerView recyclerView;
	private RecyclerViewAdapter adapter;
	MaterialRefreshLayout mrl;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v =inflater.inflate(R.layout.main_fragment_layout, parent, false);
		LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
		map = new HashMap<Integer, Bitmap>();
		recyclerView= (RecyclerView) v.findViewById(R.id.recyclerView);
		seting_button= (Button) v.findViewById(R.id.setting);
		seting_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(getActivity(), Seting_Activity.class);
				startActivityForResult(i, 100);
			}
		});
		//获取TK
		SharedPreferences preferences1 = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
		tk = preferences1.getString("token", "none");
		//获取第一页的内容
		try {
			result = HttpUtil.Get(Constants.url + "/app/news/gets/"+page, null);
			//Toast.makeText(getActivity(),result, Toast.LENGTH_SHORT).show();
			com.du.gout_app.common.Message<List<News>> ms = JSON.parseObject(result, new TypeReference<Message<List<News>>>() {
			});
			if (ms.getSuccess() == true){
				newslist = ms.getMessage();
				page++;
				//获取第一页的图片资源
				for(int i =0;i<newslist.size();i++){
					if(newslist.get(i).getImg()!=null){
						Bitmap bm = HttpUtil.getHttpBitmap(Constants.url+newslist.get(i).getImg());
						//Toast.makeText(getActivity(),bm.toString(), Toast.LENGTH_SHORT).show();
					    map.put(i,bm);
					}
				}
			} else
				Toast.makeText(getActivity(), "获取页面时发生错误", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}

		adapter=new RecyclerViewAdapter(newslist,map,getActivity());
		//设置上拉刷新
		mrl = (MaterialRefreshLayout) v.findViewById(R.id.refresh);
		mrl.setLoadMore(true);
		mrl.setMaterialRefreshListener(new MaterialRefreshListener() {
			@Override
			public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_LONG).show();
						mrl.finishRefresh();
					}
				},1000);
			}

			@Override
			public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout){
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						List<News> nls;
						try {
							result = HttpUtil.Get(Constants.url + "/app/news/gets/"+page, null);
							com.du.gout_app.common.Message<List<News>> ms = JSON.parseObject(result, new TypeReference<Message<List<News>>>() {
							});
							if (ms.getSuccess() == true){
								nls = ms.getMessage();
								if(nls.size()>0){
								  for(int i = 0;i<nls.size();i++) {
									  newslist.add(nls.get(i));
									  adapter.addBitmap(newslist.indexOf(nls.get(i)),HttpUtil.getHttpBitmap(Constants.url+nls.get(i).getImg()));
									  adapter.addData(adapter.getItemCount(),nls.get(i));
								  }
									page++;
									mrl.finishRefreshLoadMore();
								}
								else {
									Toast.makeText(getActivity(), "没有更多消息可以获取哦!", Toast.LENGTH_SHORT).show();
									mrl.finishRefreshLoadMore();
								}
							} else {
								Toast.makeText(getActivity(), "获取页面时发生错误", Toast.LENGTH_SHORT).show();
								mrl.finishRefreshLoadMore();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				},1000);
			}
		});

		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.addItemDecoration(new SpaceItemDecoration());

		return v;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode==100 && resultCode == 404){
			getActivity().finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			//不是第一个的格子都设一个左边和底部的间距
			outRect.left = 5;
			outRect.right = 5;
			outRect.top = 5;
			outRect.bottom = 5;

			}


		}
	}


