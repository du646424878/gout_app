package com.du.gout_app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.du.gout_app.R;
import com.du.gout_app.common.Constants;
import com.du.gout_app.common.HttpUtil;
import com.du.gout_app.common.Message;
import com.du.gout_app.common.isNetworkavailable;
/**
 * Created by 杜哲凯 on 2016/7/8.
 */
public class welcome_activity extends Activity{
    private Intent intent;
    String tk;
    Boolean flag;
    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.welcome_activity_layout);
        welcome_activity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        flag= isNetworkavailable.isnetWorkAvilable(getApplication());
        if(flag==false){
            Toast.makeText(this, "网络不可用", Toast.LENGTH_LONG).show();
            SharedPreferences preferences=getSharedPreferences("token", Context.MODE_PRIVATE);
            tk=preferences.getString("token", "none");
            if(tk.compareTo("none")==0){
                startLoginAvtivity();
            }
            else{
                Intent i =new Intent(welcome_activity.this,Index_Activity.class);
                startActivity(i);
                finish();
            }

        }
        else{
            SharedPreferences preferences=getSharedPreferences("token",Context.MODE_PRIVATE);
            tk=preferences.getString("token", "none");
            if(tk.compareTo("none")==0){
                startLoginAvtivity();
            }
            else{
                autologin();
            }
        }
    }
    private void startLoginAvtivity() {
        new Handler().postDelayed(new Runnable() {
            public void run() {

                intent=new Intent(welcome_activity.this,Log_inActivity.class);
                startActivity(intent);
                welcome_activity.this.finish();
            }
        }, 2000);
    }
    private void autologin() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                String result = null;
                try {
                    result = HttpUtil.Get(Constants.url + "/admin/users/currentuser", tk);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Toast.makeText(welcome_activity.this, result, Toast.LENGTH_SHORT).show();
                Message<String> ms = JSON.parseObject(result, new TypeReference<Message<String>>() {
                });
                if(ms.getSuccess()==true){
                    Intent i =new Intent(welcome_activity.this,Index_Activity.class);
                    startActivity(i);
                    finish();

                }
                else
                    startLoginAvtivity();
            }
        }, 2000);
    }

}
