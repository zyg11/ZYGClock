package com.example.zygclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("闹钟执行了");
		AlarmManager am=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.cancel(PendingIntent.getBroadcast(context,getResultCode(),new Intent(context,AlarmReceiver.class),0));
		Intent i=new Intent(context, PlayAlarmATY.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//启动新的任务
		context.startActivity(i);
	}

}
