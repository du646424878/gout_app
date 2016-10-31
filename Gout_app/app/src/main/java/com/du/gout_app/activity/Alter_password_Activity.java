package com.du.gout_app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.du.gout_app.R;
import com.du.gout_app.common.Constants;
import com.du.gout_app.common.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class Alter_password_Activity extends AppCompatActivity {
    Button alter_button;
    Button cancel_button;
    Map pwdmap;
    EditText old_pwd_edit;
    EditText new_pwd_edit;
    EditText check_pwd_edit;
    String tk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_password);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        SharedPreferences preferences = getSharedPreferences("token", Context.MODE_PRIVATE);
        tk = preferences.getString("token", "none");
        pwdmap = new HashMap();
        old_pwd_edit = (EditText)findViewById(R.id.old_pwd);
        new_pwd_edit = (EditText)findViewById(R.id.new_pwd);
        check_pwd_edit = (EditText)findViewById(R.id.check_new_pwd);
        alter_button = (Button)findViewById(R.id.alter_button);

        alter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isleagal()==false)

                       return;
                 else
                {
                            pwdmap.put("oldpassword", old_pwd_edit.getText().toString());
                            pwdmap.put("password", new_pwd_edit.getText().toString());
                            String jsondata = JSON.toJSONString(pwdmap);
                            try {
                                String r = HttpUtil.Post(Constants.url + "/admin/users/updatecurrentuserpassword", tk, jsondata);
                                if (JSON.parseObject(r).getBoolean("success") == true) {
                                    Toast.makeText(Alter_password_Activity.this, "修改成功!", Toast.LENGTH_LONG).show();
                                    logout();
                                }else
                                    Toast.makeText(Alter_password_Activity.this,"旧密码错误",Toast.LENGTH_LONG).show();

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
            }
        });
            cancel_button = (Button)findViewById(R.id.cancel_alter);
            cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

    }
    private boolean isleagal(){
        if(new_pwd_edit.getText().toString().equals(check_pwd_edit.getText().toString())){
            if(new_pwd_edit.getText().length()<6){
                Toast.makeText(Alter_password_Activity.this,"新密码长度不能少于6位!",Toast.LENGTH_LONG).show();
                return false;
            }
            else
                return true;

        }
        else {
            Toast.makeText(Alter_password_Activity.this, "两次输入的新密码不一致，请重新输入!", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private void logout(){
        SharedPreferences  uer = getSharedPreferences("token",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = uer.edit();
        editor.putString("token","none");
        editor.commit();
        Intent i = new Intent(this,Log_inActivity.class);
        startActivity(i);
        setResult(404);

        finish();
    }
}

