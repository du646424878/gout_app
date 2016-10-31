package com.du.gout_app.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.du.gout_app.R;


public class AlarmReceiver extends BroadcastReceiver {

	private Notification note;
	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		//设置通知内容并在onReceive()这个函数执行时开启
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification.Builder builder = new Notification.Builder(context);
		builder.setContentText("今天你吃药了吗?");
		builder.setContentTitle("该吃药了");
		builder.setSmallIcon(R.mipmap.logo_nobc);
		builder.setWhen(System.currentTimeMillis());
		Notification no = builder.build();
		manager.notify(1,no);
	}
}
