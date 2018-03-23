package com.example.zygclock;



import java.util.Calendar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

public class AlarmView extends LinearLayout {

//	public AlarmView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		// TODO Auto-generated constructor stub
//	}

	public AlarmView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init();
	}

	public AlarmView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init();
	}
	private void Init(){
		alarmManager=(AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);//痛闹钟服务来设置闹钟
	}
	
	@Override
		protected void onFinishInflate() {
			// TODO Auto-generated method stub
			super.onFinishInflate();
			btnAddalarm=(Button) findViewById(R.id.btnAddAlarm);
			alarmlistListView=(ListView) findViewById(R.id.lvAlarmList);
			adapter=new ArrayAdapter<AlarmView.AlarmData>(getContext(),android.R.layout.simple_list_item_1);
			alarmlistListView.setAdapter(adapter);
			ReadAlarmlist();
		//	adapter.add(new AlarmData(System.currentTimeMillis()));//问题在这;真实原因是导错了包
			btnAddalarm.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					addAlarm();
				}
			});
			alarmlistListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						final int position, long id) {
					// TODO Auto-generated method stub长按后删除 
					new AlertDialog.Builder(getContext()).setTitle("操作选项").
					setItems(new CharSequence[]{"删除"},new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							switch (which) {
							case 0:
								deleteAlarm(position);
								break;

							default:
								break;
							}
						}
					}).setNegativeButton("取消", null).show();
					return false;
				}
			});
		}
	private void deleteAlarm(int position){
		AlarmData ad=adapter.getItem(position);
		adapter.remove(ad);
		SaveAlarmList();
		alarmManager.cancel(PendingIntent.getBroadcast(getContext(),ad.getId(), new Intent(getContext(),AlarmReceiver.class),0));
	}
	
	private void addAlarm(){
		Calendar c=Calendar.getInstance();
		new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				Calendar calendar=Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND,0);
				Calendar currentTime=Calendar.getInstance();
				if (calendar.getTimeInMillis()<=currentTime.getTimeInMillis()) {
					calendar.setTimeInMillis(calendar.getTimeInMillis()+24*60*60*1000);
				}
				
				AlarmData ad=new AlarmData(calendar.getTimeInMillis());
				
				adapter.add(ad);
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
						ad.getTime(), 
						5*60*1000, 
						PendingIntent.getBroadcast(getContext(),ad.getId(), new Intent(getContext(),AlarmReceiver.class),0));
				SaveAlarmList();
			}
		}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
	}
	private void SaveAlarmList(){
		Editor editor=getContext().getSharedPreferences(AlarmView.class.getName(),Context.MODE_PRIVATE).edit();//存储 
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < adapter.getCount(); i++) {
			sb.append(adapter.getItem(i).getTime()).append(",");
			
		}
		if (sb.length()>1) {			
			String content=sb.toString().substring(0,sb.length()-1);
			editor.putString(Key_AlarmList,content);
			System.out.println(content);
		}else {
			editor.putString(Key_AlarmList, null);
		}
		
		editor.commit();
	}
	private void ReadAlarmlist(){
		SharedPreferences sp=getContext().getSharedPreferences(AlarmView.class.getName(),Context.MODE_PRIVATE);
		String content=sp.getString(Key_AlarmList, null);
	
		if (content!=null) {
			String[] timStrings=content.split(",");
			for (String string:timStrings) {//遍历
				adapter.add(new AlarmData(Long.parseLong(string)));//?不理解为啥
			}
		}
	}
	private Button btnAddalarm;
	private ListView alarmlistListView;
	private static final String Key_AlarmList="alarmlist";
	private ArrayAdapter<AlarmData> adapter;
	private AlarmManager alarmManager;
	public static class AlarmData{
		public AlarmData(long time){
			this.time=time;
			date=Calendar.getInstance();
			date.setTimeInMillis(time);
			timelabel=String.format("%d月%d日 %d:%d",
					date.get(Calendar.MONTH)+1, 
					date.get(Calendar.DAY_OF_MONTH),//这里没注意day_of_month
					date.get(Calendar.HOUR_OF_DAY),
					date.get(Calendar.MINUTE));
		}
		public long getTime(){
			return time;
		}
		public String getTimeLabel(){
			return timelabel;
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return getTimeLabel();
		}
		public int getId(){
			return (int) (getTime()/1000/60);
		}
		private String timelabel="";
		private long time=0;
		private Calendar date;
	}
}
