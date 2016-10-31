package com.du.gout_app.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.du.gout_app.R;
import com.du.gout_app.common.Constants;
import com.du.gout_app.common.HttpUtil;
import com.du.gout_app.common.Reminder;
import com.du.gout_app.fragment.detail_fragment;
import com.du.gout_app.fragment.everymonth_fragment;
import com.du.gout_app.fragment.everyweek_fragment;
import com.du.gout_app.fragment.main_fragment;
import com.du.gout_app.service.EverydayAlarmingService;
import com.du.gout_app.service.UpdateService;

import java.util.List;

public class Index_Activity extends AppCompatActivity implements OnClickListener{
	android.support.v4.app.Fragment main_fragment;
	android.support.v4.app.Fragment everyweek_fragment;
	android.support.v4.app.Fragment everymonth_fragment;
	android.support.v4.app.Fragment detail_fragment;
	RelativeLayout tab1;
	RelativeLayout tab2;
	RelativeLayout tab3;
	RelativeLayout tab4;
	ImageView icon1;
	ImageView icon2;
	ImageView icon3;
	ImageView icon4;
	FragmentManager fm;
	List<Reminder> ls;
	String tk;
	private PopupWindow mPopupWindow;
	// 屏幕的width
	private int mScreenWidth;
	// 屏幕的height
	private int mScreenHeight;
	// PopupWindow的width
	private int mPopupWindowWidth;
	// PopupWindow的height
	private int mPopupWindowHeight;
	Log_inActivity login = new Log_inActivity();
	registerActivity regi = new registerActivity();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if(login.me != null)
        login.me.finish();
		if(regi.me != null)
		regi.me.finish();
		//申请权限
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {
			//申请WRITE_EXTERNAL_STORAGE权限
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
		}
		//获取版本号
		try {
			int current_version = getVersionCode(this);
			String r = HttpUtil.Get(Constants.url+"/app/current/version",null);
			String s = JSON.parseObject(r).getString("message");
			int server_version = Integer.parseInt(s);
			System.out.println(server_version);
			if(current_version<server_version){
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("软件升级")
						.setMessage("发现新版本,建议立即更新使用.")
						.setPositiveButton("更新", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
//开启更新服务UpdateService
//这里为了把update更好模块化，可以传一些updateService依赖的值
//如布局ID，资源ID，动态获取的标题,这里以app_name为例
								Intent updateIntent =new Intent(Index_Activity.this, UpdateService.class);
								updateIntent.putExtra("titleId",R.string.app_name);
								startService(updateIntent);
							}
						})
						.setNegativeButton("取消",new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
				alert.create().show();
			}


		}
		catch (Exception e){
			e.printStackTrace();
		}
		//获取tk和闹钟设置
		SharedPreferences preferences = getSharedPreferences("token", Context.MODE_PRIVATE);
		tk = preferences.getString("token", "none");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String r = HttpUtil.Get(Constants.url + "/admin/db/reminder/selects", tk);
					ls = JSON.parseObject(r, new TypeReference<List<Reminder>>() {
					});
					for (int i = 0; i < ls.size(); i++) {
						if(ls.get(i).getEffective()==1){
							reminderon(ls.get(i).getHour(),ls.get(i).getMin(),ls.get(i).getHour()*2+ls.get(i).getMin()*3);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		//获取fragmentmanager
		fm =getSupportFragmentManager();
		//添加默认的fragment
		showFragment(1);
		//绑定控件
		tab1 = (RelativeLayout) findViewById(R.id.main_frag);
		tab1.setOnClickListener(this);
		tab2 = (RelativeLayout) findViewById(R.id.month_frag);
		tab2.setOnClickListener(this);
		tab3 = (RelativeLayout) findViewById(R.id.week_frag);
		tab3.setOnClickListener(this);
		tab4 = (RelativeLayout) findViewById(R.id.detail_frag);
		tab4.setOnClickListener(this);
		icon1 =(ImageView) findViewById(R.id.icon_main);
		icon1.setImageResource(R.mipmap.home_pressed);
		icon2 =(ImageView) findViewById(R.id.icon_month);
		icon3 =(ImageView) findViewById(R.id.icon_week);
		icon4 =(ImageView) findViewById(R.id.icon_profile);

	}
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("确认退出吗？")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作
						finish();
					}
				})
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“返回”后的操作,这里不设置没有任何操作
						return;
					}
				}).show();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = fm.beginTransaction();
		switch(v.getId()){
			case R.id.main_frag:
				icon1.setImageResource(R.mipmap.home_pressed);
				icon2.setImageResource(R.mipmap.upload_normal);
				icon3.setImageResource(R.mipmap.upload_normal);
				icon4.setImageResource(R.mipmap.profile_normal);
				showFragment(1);
				break;
			case R.id.month_frag:
				icon1.setImageResource(R.mipmap.home_normal);
				icon2.setImageResource(R.mipmap.upload_pressed);
				icon3.setImageResource(R.mipmap.upload_normal);
				icon4.setImageResource(R.mipmap.profile_normal);
				showFragment(2);
				break;
			case R.id.week_frag:
				icon1.setImageResource(R.mipmap.home_normal);
				icon2.setImageResource(R.mipmap.upload_normal);
				icon3.setImageResource(R.mipmap.upload_pressed);
				icon4.setImageResource(R.mipmap.profile_normal);
				showFragment(3);
				break;
			case R.id.detail_frag:
				icon1.setImageResource(R.mipmap.home_normal);
				icon2.setImageResource(R.mipmap.upload_normal);
				icon3.setImageResource(R.mipmap.upload_normal);
				icon4.setImageResource(R.mipmap.profile_pressed);
				showFragment(4);
				break;
		}

		transaction.commit();
	}


	private void hideFragments(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (main_fragment != null) {
			transaction.hide(main_fragment);
		}
		if (everyweek_fragment != null) {
			transaction.hide(everyweek_fragment);
		}
		if (everymonth_fragment != null) {
			transaction.hide(everymonth_fragment);
		}
		if (detail_fragment != null) {
			transaction.hide(detail_fragment);
		}
	}
	public void showFragment(int index){
		FragmentTransaction ft = fm.beginTransaction();
		// 想要显示一个fragment,先隐藏所有fragment，防止重叠
		hideFragments(ft);
		switch (index) {
			case 1:
				// 如果fragment1已经存在则将其显示出来
				if (main_fragment != null)
					ft.show(main_fragment);
					// 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
				else {
					main_fragment = new main_fragment();
					ft.add(R.id.container,main_fragment);
				}
				break;
			case 3:
				if (everyweek_fragment != null)
					ft.show(everyweek_fragment);
				else {
					everyweek_fragment = new everyweek_fragment();
					ft.add(R.id.container, everyweek_fragment);
				}
				break;
			case 2:
				if (everymonth_fragment != null)
					ft.show(everymonth_fragment);
				else {
					everymonth_fragment = new everymonth_fragment();
					ft.add(R.id.container, everymonth_fragment);
				}
				break;
			case 4:
				if (detail_fragment != null)
					ft.show(detail_fragment);
				else {
					detail_fragment = new detail_fragment();
					ft.add(R.id.container, detail_fragment);
				}
				break;
		}
		ft.commit();
	}
	private void reminderon(Integer hour,Integer min,int i){
		Intent intent1 = new Intent(Index_Activity.this,EverydayAlarmingService.class);
		intent1.putExtra("hourOfDay",hour);
		intent1.putExtra("min",min);
		intent1.putExtra("count", i);
		startService(intent1);
	}
	public static int getVersionCode(Context context)//获取版本号(内部识别号)
	{
		try {
			PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

}