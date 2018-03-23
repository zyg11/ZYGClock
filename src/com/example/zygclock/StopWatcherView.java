package com.example.zygclock;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StopWatcherView extends LinearLayout {

	public StopWatcherView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public StopWatcherView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		tvHour=(TextView) findViewById(R.id.timerHour);
		tvHour.setText("0");
		tvMin=(TextView) findViewById(R.id.timeMin);
		tvMin.setText("0");
		tvSec=(TextView) findViewById(R.id.timeSec);
		tvSec.setText("0");
		tvMsec=(TextView) findViewById(R.id.timeMSec);
		tvMsec.setText("0");
		btnStart=(Button) findViewById(R.id.btnSWStart);
		btnStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTimer();
				btnStart.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
				btnLap.setVisibility(View.VISIBLE);
			}
		});
		btnPause=(Button) findViewById(R.id.btnSWPause);
		btnPause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stoptimer();
				btnPause.setVisibility(View.GONE);
				btnResume.setVisibility(View.VISIBLE);
				btnLap.setVisibility(View.GONE);
				btnReset.setVisibility(View.VISIBLE);
			}
		});
		btnResume=(Button) findViewById(R.id.btnSWResume);
		btnResume.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTimer();
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
				btnReset.setVisibility(View.GONE);
				btnLap.setVisibility(View.VISIBLE);
			}
		});
		btnReset=(Button) findViewById(R.id.btnSWReset);
		btnReset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stoptimer();
				tenMsec=0;
				adapter.clear();//重置清理列表
			    btnStart.setVisibility(View.VISIBLE);
				btnPause.setVisibility(View.GONE);
				btnResume.setVisibility(View.GONE);
				btnReset.setVisibility(View.GONE);
				btnLap.setVisibility(View.GONE);
			}
		});
		btnLap=(Button) findViewById(R.id.btnSWLap);
		btnLap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				adapter.insert(String.format("%d:%d:%d.%d",tenMsec/100/60/60
						,tenMsec/100/60%60,tenMsec/100%60,tenMsec%100), 0);
				
			}
		});
		lvTimelist=(ListView) findViewById(R.id.lvWatchTimelist);
		
		btnPause.setVisibility(View.GONE);
		btnResume.setVisibility(View.GONE);
		btnReset.setVisibility(View.GONE);
		btnLap.setVisibility(View.GONE);
		
		adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
		lvTimelist.setAdapter(adapter);
		showTimerTask=new TimerTask() {
			
			@Override
			public void run() {
				handler.sendEmptyMessage(MSG_WHAT_SHOW_TIME);//不断执行刷新操作
				
			}
		};
		timer.schedule(showTimerTask,200,200);
	}
	private void startTimer(){
		if (timerTask==null) {
			timerTask=new TimerTask() {
				
				@Override
				public void run() {
					tenMsec++;
					
				}
			};
			timer.schedule(timerTask, 10,10);
		}
	}
	private void stoptimer(){
		if (timerTask!=null) {
			timerTask.cancel();
			timerTask=null;
		}
	}
	private int tenMsec=0;
	private Timer timer=new Timer();
	private TimerTask timerTask=null;
	private TimerTask showTimerTask=null;
	private TextView tvHour,tvMin,tvSec,tvMsec;
	private Button btnStart,btnResume,btnReset,btnPause,btnLap;
	private ListView lvTimelist;
	private ArrayAdapter<String> adapter;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_WHAT_SHOW_TIME:
				tvHour.setText(tenMsec/100/60/60+"");
				tvMin.setText(tenMsec/100/60%60+"");
				tvSec.setText(tenMsec/100%60+"");
				tvMsec.setText(tenMsec%100+"");
				break;

			default:
				break;
			}
		};
	};
	private static final int MSG_WHAT_SHOW_TIME=1;
	public void onDestroy() {
		timer.cancel();
		
	}
}
