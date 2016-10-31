package com.du.gout_app.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.du.gout_app.R;
import com.du.gout_app.common.Constants;
import com.du.gout_app.common.HttpUtil;
import com.du.gout_app.common.Unandpsw;
import com.du.gout_app.common.isNetworkavailable;
public class Log_inActivity extends AppCompatActivity implements OnClickListener {
   private EditText username;
   private EditText password;
   private Button login_button;
   private TextView regist_button;
   private Context context;
   private String result;
   private boolean flag;
   ProgressDialog myDialog;
   public static AppCompatActivity me;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                myDialog.dismiss();
                Intent i = new Intent(context,Index_Activity.class);
                startActivity(i);
                finish();
            }
            else{
                myDialog.dismiss();
                Toast.makeText(Log_inActivity.this,"用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);

        }
    };
    @Override
   protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   context = this;
   me = this;
   setContentView(R.layout.activity_login);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }


   Log_inActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
   initWidget();
   }

   private void initWidget()
   {
       username=(EditText)findViewById(R.id.username);
       password=(EditText)findViewById(R.id.password);
       password.setTransformationMethod(PasswordTransformationMethod.getInstance());
       login_button=(Button)findViewById(R.id.login_button);
       regist_button=(TextView)findViewById(R.id.regist_button);
       flag = false;
       login_button.setOnClickListener(this);
       regist_button.setOnClickListener(this);
   }
   public void onClick(View v) {
       // TODO Auto-generated method stub
       switch(v.getId())
       {
       case R.id.login_button:
           if(checkEdit())
           {
               if(isNetworkavailable.isnetWorkAvilable(getApplicationContext())==false){
                   Toast.makeText(Log_inActivity.this,"网络不可用", Toast.LENGTH_SHORT).show();
                   break;
               }
               login();
           }
           break;
       case R.id.regist_button:
           Intent intent2=new Intent(Log_inActivity.this,registerActivity.class);
           startActivity(intent2);
           break;
       }
   }
   private boolean checkEdit(){
       if(username.getText().toString().trim().length()<4){
           Toast.makeText(Log_inActivity.this, "用户名长度不可小于4位", Toast.LENGTH_SHORT).show();
       }else if(password.getText().toString().trim().length()<5){
           Toast.makeText(Log_inActivity.this, "密码长度不可小于5位", Toast.LENGTH_SHORT).show();
       }else{
           return true;
       }
       return false;
   }
   private void login(){

       Runnable mTasks = new Runnable() {
           public void run() {
               try{
                   Unandpsw unandpsw = new Unandpsw(username.getText().toString().trim(),password.getText().toString().trim());
                   String jsondata= JSON.toJSONString(unandpsw);
                   result = HttpUtil.Post(Constants.url + "/login", null, jsondata);
                   com.du.gout_app.common.Message<String> ms = JSON.parseObject(result, new TypeReference<com.du.gout_app.common.Message<String>>() {
                   });
                   if(ms.getSuccess() == true){
                       WriteSharedPreferences(ms.getMessage());
                        handler.sendEmptyMessage(1);
                   }
                   else{
                      handler.sendEmptyMessage(0);
                   }
               }
               catch (Exception e)
               {
                   e.printStackTrace();
               }
           }
       };
       myDialog = ProgressDialog.show(Log_inActivity.this, "正在登录..", "登录中,请稍后..", true, true);
       handler.postDelayed(mTasks, 1000);

   }

   void WriteSharedPreferences(String tk){
       SharedPreferences  uer = getSharedPreferences("token",Context.MODE_PRIVATE);
       Editor editor = uer.edit();
       editor.putString("token", tk);
       editor.commit();
      // Toast.makeText(this,tk+"已写入" , Toast.LENGTH_LONG).show();
   }
}

	
	
