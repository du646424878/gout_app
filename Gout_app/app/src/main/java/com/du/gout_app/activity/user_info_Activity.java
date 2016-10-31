package com.du.gout_app.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.du.gout_app.R;
import com.du.gout_app.common.Constants;
import com.du.gout_app.common.HttpUtil;
import com.du.gout_app.common.Message;
import com.du.gout_app.common.Unandpsw;

import java.util.HashMap;
import java.util.Map;

public class user_info_Activity extends AppCompatActivity{
    EditText realname,age,weight,height,nation,nativeplace,job,email,idcardnumber,phonenumber,hypertensionmedicine,diabetesmedicine,heartdiseasemedicine,hlpmedicine,otherdiseasemedicine;
    RadioButton radioMale,radioFemale,isrelativegout_y,isrelativegout_n,havehypertension_y,havehypertension_n,havediabetes_y,havediabetes_n,haveheartdisease_y,haveheartdisease_n,havehlp_y,havehlp_n,haveotherdisease_y,haveotherdisease_n;
    Button finish_btn;
    Map<String,String> map;
    String tk;
    String jsondata;
    Handler handler ;
    ProgressDialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        init();
        new AlertDialog.Builder(user_info_Activity.this).setTitle("系统提示")//设置对话框标题

                .setMessage("亲爱的用户，由于您首次登录，请先完善一下个人信息")//设置显示的内容

                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        // TODO Auto-generated method stub
                    }

                }).show();//在按键响应事件中显示此对话框
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!check())
                   return;
                postdata();
            }
        });


    }
    private boolean check(){
        if(realname.length() == 0 ||phonenumber.length() == 0 || idcardnumber.length() == 0) {
            Toast.makeText(user_info_Activity.this,"有必填字段未填",Toast.LENGTH_LONG).show();
            return false;
        }
            else
            return true;

    }
    private void init(){
        map = new HashMap();
        handler = new Handler();
        finish_btn = (Button)findViewById(R.id.finish_regi);
        realname = (EditText)findViewById(R.id.realname);
        age = (EditText)findViewById(R.id.age);
        weight = (EditText)findViewById(R.id.weight);
        height = (EditText)findViewById(R.id.height);
        nation = (EditText)findViewById(R.id.nation);
        nativeplace = (EditText)findViewById(R.id.nativeplace);
        job = (EditText)findViewById(R.id.job);
        email = (EditText)findViewById(R.id.email);
        idcardnumber = (EditText)findViewById(R.id.idcardnumber);
        phonenumber = (EditText)findViewById(R.id.phonenumber);
        hypertensionmedicine = (EditText)findViewById(R.id.hypertensionmedicine);
        diabetesmedicine = (EditText)findViewById(R.id.diabetesmedicine);
        heartdiseasemedicine = (EditText)findViewById(R.id.heartdiseasemedicine);
        hlpmedicine = (EditText)findViewById(R.id.hlpmedicine);
        otherdiseasemedicine = (EditText)findViewById(R.id.otherdiseasemedicine);

        radioFemale = (RadioButton)findViewById(R.id.radioFemale);
        radioMale = (RadioButton)findViewById(R.id.radioMale);
        isrelativegout_y = (RadioButton)findViewById(R.id.isrelativegout_y);
        isrelativegout_n = (RadioButton)findViewById(R.id.isrelativegout_n);
        havehypertension_y = (RadioButton)findViewById(R.id.havehypertension_y);
        havehypertension_n = (RadioButton)findViewById(R.id.havehypertension_n);
        havediabetes_y = (RadioButton)findViewById(R.id.havediabetes_y);
        havediabetes_n = (RadioButton)findViewById(R.id.havediabetes_n);
        haveheartdisease_y = (RadioButton)findViewById(R.id.haveheartdisease_y);
        haveheartdisease_n = (RadioButton)findViewById(R.id.haveheartdisease_n);
        havehlp_y = (RadioButton)findViewById(R.id.havehlp_y);
        havehlp_n = (RadioButton)findViewById(R.id.havehlp_n);
        haveotherdisease_y = (RadioButton)findViewById(R.id.haveotherdisease_y);
        haveotherdisease_n = (RadioButton)findViewById(R.id.haveotherdisease_n);
    }
private  void postdata(){
      map.put("realname",realname.getText().toString());
      map.put("age",age.getText().toString());
      map.put("weight",weight.getText().toString());
      map.put("height",height.getText().toString());
      map.put("nation",nation.getText().toString());
      map.put("nativeplace",nativeplace.getText().toString());
      map.put("job",job.getText().toString());
      map.put("email",email.getText().toString());
      map.put("idcardnumber",idcardnumber.getText().toString());
      map.put("phonenumber",phonenumber.getText().toString());
      map.put("hypertensionmedicine",hypertensionmedicine.getText().toString());
      map.put("diabetesmedicine",diabetesmedicine.getText().toString());
      map.put("heartdiseasemedicine",heartdiseasemedicine.getText().toString());
      map.put("otherdiseasemedicine",otherdiseasemedicine.getText().toString());
      if(radioFemale.isChecked())
          map.put("gender","0");
      else
          map.put("gender","1");

    if(isrelativegout_n.isChecked())
        map.put("isrelativegout","0");
    else
        map.put("isrelativegout","1");
    if(havehypertension_n.isChecked())
        map.put("havehypertension","0");
    else
        map.put("havehypertension","1");

    if(havediabetes_n.isChecked())
        map.put("havediabetes","0");
    else
        map.put("havediabetes","1");
    if(haveheartdisease_n.isChecked())
        map.put("haveheartdisease","0");
    else
        map.put("haveheartdisease","1");
    if(havehlp_n.isChecked())
        map.put("havehlp","0");
    else
        map.put("havehlp","1");

    if(haveotherdisease_n.isChecked())
        map.put("haveotherdisease","0");
    else
        map.put("haveotherdisease","1");

     jsondata = JSON.toJSONString(map);
    Runnable mtask = new Runnable() {
        @Override
        public void run()  {
       try{
           login();
           SharedPreferences preferences1 = getSharedPreferences("token", Context.MODE_PRIVATE);
           tk = preferences1.getString("token", "none");
          String result =  HttpUtil.Post(Constants.url + "/admin/users/updatecurrentpatientdetail", tk, jsondata);
          if(JSON.parseObject(result).getBoolean("success")== true) {
              Intent i = new Intent(user_info_Activity.this,Index_Activity.class);
              startActivity(i);
              myDialog.dismiss();
              finish();
          }
       }catch (Exception e){
           e.printStackTrace();
       }
        }
    };
    myDialog = ProgressDialog.show(user_info_Activity.this, "正在提交..", "提交中,请稍后..", true, true);
    handler.postDelayed(mtask,1000);

   }

    private void login(){
        Unandpsw unandpsw = new Unandpsw(getIntent().getStringExtra("username"),getIntent().getStringExtra("password"));
        String jsondata= JSON.toJSONString(unandpsw);
        String result = null;
        try {
            result = HttpUtil.Post(Constants.url + "/login",null, jsondata);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Message<String> ms = JSON.parseObject(result, new TypeReference<Message<String>>() {
        });
        if(ms.getSuccess() == true){
            WriteSharedPreferences(ms.getMessage());
        }
        else
            Toast.makeText(user_info_Activity.this,"用户名或密码错误", Toast.LENGTH_SHORT).show();
    }

    void WriteSharedPreferences(String tk){
        SharedPreferences  uer = getSharedPreferences("token",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = uer.edit();
        editor.putString("token",tk);
        editor.commit();
    }
}
