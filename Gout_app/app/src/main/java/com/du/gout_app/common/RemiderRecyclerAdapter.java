package com.du.gout_app.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alibaba.fastjson.JSON;
import com.du.gout_app.R;
import com.du.gout_app.activity.News_Activity;
import com.du.gout_app.activity.time_set_Activity;
import com.du.gout_app.receiver.AlarmReceiver;
import com.du.gout_app.service.EverydayAlarmingService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 杜哲凯 on 2016/9/20.
 */
public class RemiderRecyclerAdapter  extends RecyclerView.Adapter<RemiderRecyclerAdapter.MyViewHolder>{
    private List<Reminder> re;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private String tk;
    public RemiderRecyclerAdapter(List<Reminder> rms,Context context) {
        this.re = rms;
        this.context= context;
        this.mLayoutInflater =LayoutInflater.from(context);
        SharedPreferences preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        this.tk = preferences.getString("token", "none");
    }


    //自定义ViewHolder类
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView reminder_time;
        TextView reminder_state;
        TextView reminder_button;
        TextView delete_reminder;
        TextView alter_reminder;
        MyViewHolder(final View itemView) {
            super(itemView);
            reminder_time= (TextView) itemView.findViewById(R.id.reminder_time);
            reminder_state= (TextView) itemView.findViewById(R.id.reminder_state);
            reminder_button= (TextView) itemView.findViewById(R.id.reminder_button);
            delete_reminder= (TextView) itemView.findViewById(R.id.delete_reminder);
            alter_reminder= (TextView) itemView.findViewById(R.id.alter_reminder);

        }


    }

    //定义结束

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            return new MyViewHolder(mLayoutInflater.inflate(R.layout.reminder_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder mv, int i) {
        final int j=i;
       mv.reminder_time.setText(re.get(i).getHour()+"时"+re.get(i).getMin()+"分");
       mv.reminder_state.setText(re.get(i).getEffective()==1?"已开启":"已关闭");
       mv.reminder_button.setText(re.get(i).getEffective()==1?"关闭":"开启");
       mv.delete_reminder.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int i = re.get(j).getReminderid();
               try {
                   HttpUtil.Get(Constants.url + "/admin/db/reminder/delete/" + i, tk);
                   reminderoff(re.get(j).getHour() * 2 + re.get(j).getMin()*3);
                   re.remove(j);
                   notifyItemRemoved(j);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       });
       mv.alter_reminder.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final Calendar c = Calendar.getInstance();
               c.setTimeInMillis(System.currentTimeMillis());
               int hour = re.get(j).getHour();
               int minute = re.get(j).getMin();
               reminderoff(hour*2+minute*3);
               new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       c.setTimeInMillis(System.currentTimeMillis());
                       c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                       c.set(Calendar.MINUTE, minute);
                       c.set(Calendar.SECOND, 0);
                       c.set(Calendar.MILLISECOND, 0);
                       System.out.println(c.getTime());
                       try {
                           String jsondata = JSON.toJSONString(new Reminder(Constants.getUserid(context), hourOfDay, minute));
                           int i = re.get(j).getReminderid();
                           HttpUtil.Post(Constants.url + "/admin/db/reminder/update/" + i, tk, jsondata);
                           re.get(j).setHour(hourOfDay);
                           re.get(j).setMin(minute);
                           mv.reminder_time.setText(re.get(j).getHour()+"时"+re.get(j).getMin()+"分");
                           notifyItemChanged(j);
                           reminderon(hourOfDay,minute,hourOfDay*2+minute*3);
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
               }, hour, minute, true).show();
           }
       });
      mv.reminder_button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(re.get(j).getEffective()==1){
                  try {
                      String jsondata = JSON.toJSONString(new Reminder(0));
                      int i = re.get(j).getReminderid();
                      HttpUtil.Post(Constants.url + "/admin/db/reminder/update/" + i, tk, jsondata);
                      mv.reminder_state.setText("已关闭");
                      re.get(j).setEffective(0);
                      notifyItemChanged(j);
                      reminderoff(re.get(j).getHour()*2+re.get(j).getMin()*3);
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
              else {
                  try {
                      String jsondata = JSON.toJSONString(new Reminder(1));
                      int i = re.get(j).getReminderid();
                      HttpUtil.Post(Constants.url + "/admin/db/reminder/update/" + i, tk, jsondata);
                      mv.reminder_state.setText("已开启");
                      re.get(j).setEffective(1);
                      notifyItemChanged(j);
                      reminderon(re.get(j).getHour(),re.get(j).getMin(),re.get(j).getHour()*2+re.get(j).getMin()*3);
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
          }
      });
    }

    //  添加数据
    public void addData(int position,Reminder rm) {
//      在list中添加数据，并通知条目加入一条
        re.add(rm);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return re.size();
    }
    //闹钟启动
    public void reminderon(Integer hour,Integer min,int i){
        Intent intent1 = new Intent(context,EverydayAlarmingService.class);
        intent1.putExtra("hourOfDay",hour);
        intent1.putExtra("min",min);
        intent1.putExtra("count",i);
        context.startService(intent1);
    }
    //闹钟关闭
    public void reminderoff(int l){
        Intent intent = new Intent(context,
                AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                context,l, intent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        manager.cancel(sender);
    }
}
