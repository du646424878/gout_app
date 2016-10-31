package com.du.gout_app.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import com.du.gout_app.receiver.AlarmReceiver;

import java.util.Calendar;
import java.util.TimeZone;

public class EverydayAlarmingService extends Service {
	AlarmManager manager;
	PendingIntent sender;
	public EverydayAlarmingService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	 public int onStartCommand(Intent intent, int flags, int startId) {
		int hour = intent.getIntExtra("hourOfDay",0);
		int min = intent.getIntExtra("min",0);
		int count = intent.getIntExtra("count",0);
		Intent intent1 = new Intent(EverydayAlarmingService.this, AlarmReceiver.class);
		sender = PendingIntent.getBroadcast(this,count, intent1, 0);
		long firstTime = SystemClock.elapsedRealtime();
		long systemTime = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.HOUR_OF_DAY,hour);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		long selectTime = calendar.getTimeInMillis();
		if(systemTime > selectTime) {
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		selectTime = calendar.getTimeInMillis();
		}
		long time = selectTime - systemTime;
		firstTime += time;
		manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
		                firstTime,24*60*60*1000, sender);
		return startId;
	 
	 } 
@Override
public void onDestroy() { 
 manager.cancel(sender);
}
}