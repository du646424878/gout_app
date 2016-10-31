package com.du.gout_app.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.du.gout_app.R;
import com.du.gout_app.common.Constants;
import com.du.gout_app.common.HttpUtil;
import com.du.gout_app.common.Message;
import com.du.gout_app.common.RemiderRecyclerAdapter;
import com.du.gout_app.common.Reminder;
import com.du.gout_app.common.User;
import com.du.gout_app.receiver.AlarmReceiver;
import com.du.gout_app.service.EverydayAlarmingService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class time_set_Activity extends AppCompatActivity {
    RecyclerView rv;
    RemiderRecyclerAdapter adapter;
    ImageView add_button;
    List<Reminder> ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_set);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rv = (RecyclerView)findViewById(R.id.reminder_list);
        //获取闹钟列表
        try {
            String r = HttpUtil.Get(Constants.url + "/admin/db/reminder/selects", Constants.getTk(this));
            ls = JSON.parseObject(r, new TypeReference<List<Reminder>>() {
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter=new RemiderRecyclerAdapter(ls,this);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
      //添加按钮
        add_button = (ImageView) findViewById(R.id.add_reminder);
        add_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(time_set_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        try {

                            Reminder r = new Reminder(Constants.getUserid(time_set_Activity.this), hourOfDay, minute,1);
                            String jsondata = JSON.toJSONString(r);
                            String re = HttpUtil.Post(Constants.url + "/admin/db/reminder/insert",Constants.getTk(time_set_Activity.this), jsondata);
                            List<Reminder> temp = JSON.parseObject(re, new TypeReference<List<Reminder>>() {
                            });
                            r.setReminderid(temp.get(0).getReminderid());
                            adapter.addData(adapter.getItemCount(),r);
                            adapter.reminderon(hourOfDay, minute, hourOfDay * 2 + minute * 3);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, hour, minute, true).show();
            }
        });
    }
}
