package com.du.gout_app.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class Constants {
	public static String url ="https://gout.suntao.science";
	private static int userid;
	private static String tk;
	public static int getUserid(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
		String tk = preferences.getString("token", "none");
		try {
			String result = HttpUtil.Get(Constants.url + "/admin/users/currentuser", tk);
			Message<User> ms = JSON.parseObject(result, new TypeReference<Message<User>>() {
			});
			userid = ms.getMessage().getUserid();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userid;
	}
	public static String getTk(Context context){
		SharedPreferences preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
		String tk = preferences.getString("token", "none");
		return tk;
	}
}
