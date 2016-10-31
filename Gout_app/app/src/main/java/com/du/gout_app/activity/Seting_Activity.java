package com.du.gout_app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.du.gout_app.R;

public class Seting_Activity extends Activity {
	Button logout_button;
	Button alter_button;
	LinearLayout eat_medicine;
	LinearLayout about_button;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_setting);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			//透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			//透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		logout_button = (Button) findViewById(R.id.log_out);
		logout_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logout();
			}
		});
		alter_button = (Button)findViewById(R.id.alterpwd);
		alter_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Seting_Activity.this,Alter_password_Activity.class);
				startActivityForResult(i,100);
			}
		});
		eat_medicine =(LinearLayout) findViewById(R.id.eat_madicine_setting);
        eat_medicine.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Seting_Activity.this, time_set_Activity.class);
				startActivity(i);
			}
		});
		about_button = (LinearLayout)findViewById(R.id.about);
		about_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Seting_Activity.this,About_activity.class);
				startActivity(i);
			}
		});


	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode==100 && resultCode == 404){
			setResult(404);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void logout(){
		SharedPreferences  uer = getSharedPreferences("token",Context.MODE_PRIVATE);
		Editor editor = uer.edit();
		editor.putString("token","none");
		editor.commit();
		Intent i = new Intent(this,Log_inActivity.class);
		startActivity(i);
		setResult(404);
		
		finish();
	}
}
