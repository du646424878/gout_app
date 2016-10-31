package com.du.gout_app.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.du.gout_app.R;
import com.du.gout_app.common.Constants;
import com.du.gout_app.common.HttpUtil;
import com.du.gout_app.common.Message;
import com.du.gout_app.common.User;
import com.du.gout_app.common.isNetworkavailable;

public class registerActivity extends AppCompatActivity implements OnClickListener{
	 private ProgressDialog dialog; 
	private EditText username;
	private EditText password;
	private EditText checkpwd;
	private Button register_button;
	private Button cancel_button;
	private Boolean ifuser =false;
	Handler handler ;
	public static AppCompatActivity me;
	ProgressDialog myDialog;
@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) { 

	super.onCreate(savedInstanceState); 
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	StrictMode.setThreadPolicy(policy);
	setContentView(R.layout.activity_register);
	if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		//透明状态栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		//透明导航栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
	}
	me = this;
	registerActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    init();
	} 
  
private void init(){
	handler = new Handler();
	username =(EditText) findViewById(R.id.register_username);
	password =(EditText) findViewById(R.id.register_password);
	checkpwd =(EditText) findViewById(R.id.check_password);
	password.setTransformationMethod(PasswordTransformationMethod.getInstance());
	checkpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
	register_button=(Button) findViewById(R.id.register_button);
	cancel_button =(Button) findViewById(R.id.cancel_button);
	register_button.setOnClickListener(this);
	cancel_button.setOnClickListener(this);
  }

@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	switch(arg0.getId())
	{
	case R.id.cancel_button:
		Intent i =new Intent(registerActivity.this,Log_inActivity.class);
		startActivity(i);
	     finish();
		break;
	case R.id.register_button:
	  if(!checkEdit())
		  return;
	  if(!isNetworkavailable.isnetWorkAvilable(getApplicationContext())){
		  Toast.makeText(registerActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
		  return;
	  }
	   Thread t = new Thread(hasuser);
	   t.start();
		try {
			  t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	  if(ifuser != true){
	   
	   register();
	  }
	  else{
		  Toast.makeText(registerActivity.this, "该用户已经存在", Toast.LENGTH_SHORT).show();
		  ifuser = false;
	  }
		break;
	}
  }
void register(){
    Runnable mtask = new Runnable() {
		@Override
		public void run() {
			String jsondata =String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username.getText().toString(),password.getText().toString());
			String result= null;
			try {
				result = HttpUtil.Post(Constants.url + "/signin", null, jsondata);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Message<User> ms = JSON.parseObject(result, new TypeReference<Message<User>>() {
			});
			System.out.println(ms.getMessage());
			if(ms.getSuccess() ==true){
				Intent i =new Intent(registerActivity.this,user_info_Activity.class);
				i.putExtra("username",username.getText().toString().trim());
				i.putExtra("password",password.getText().toString().trim());
				myDialog.dismiss();
				startActivity(i);
			}
		}
	};
	myDialog = ProgressDialog.show(registerActivity.this, "正在注册..", "注册中,请稍后..", true, true);
	handler.postDelayed(mtask,1000);
}
private boolean checkEdit(){
	if(username.getText().toString().trim().length()<4){
		Toast.makeText(registerActivity.this, "用户名长度不可小于4位", Toast.LENGTH_SHORT).show();
	}
	else if(password.getText().toString().trim().length()<5){
		Toast.makeText(registerActivity.this, "密码长度不可小于5位", Toast.LENGTH_SHORT).show();
	}
	else if(!password.getText().toString().equals(checkpwd.getText().toString())){
		Toast.makeText(registerActivity.this, "密码不可为空", Toast.LENGTH_SHORT).show();
	}
	 else
		return true;
	return false;
  }
Runnable hasuser = new Runnable() {
	@Override
	public void run() {
		String un =String.format("{\"username\":\"%s\"}", username.getText().toString().trim());
		String result =null;
		// TODO Auto-generated method stub

		try {
			result = HttpUtil.Post(Constants.url+"/hasuser","none",un);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Message<Boolean> ms = JSON.parseObject(result, new TypeReference<Message<Boolean>>() {
			});
		    System.out.println(ms.getMessage());
            if(ms.getSuccess()==true && ms.getMessage() ==true)
            	ifuser =true;
		}
		
};
}