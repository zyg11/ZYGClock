package com.example.zygclock;

import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TimerView extends LinearLayout {


	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TimerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		btnPause=(Button) findViewById(R.id.btnPause);
		btnPause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StopTimer();
				btnPause.setVisibility(View.GONE);
				btnResume.setVisibility(View.VISIBLE);
			}
		});
		btnRest=(Button) findViewById(R.id.btnReset);
		btnRest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StopTimer();
				etHour.setText("0");
				etMin.setText("0");
				etSec.setText("0");
				
				btnRest.setVisibility(View.GONE);
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.GONE);
				btnStart.setVisibility(View.VISIBLE);
				
			}
		});
		btnResume=(Button) findViewById(R.id.btnResume);
		btnResume.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTimer();
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
			}
		});
		btnStart=(Button) findViewById(R.id.btnStart);
		btnStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startTimer();
				
				btnStart.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
				btnRest.setVisibility(View.VISIBLE);
			}
		});
		
		etHour=(EditText) findViewById(R.id.etHour);
		etMin=(EditText) findViewById(R.id.etMin);
		etSec=(EditText) findViewById(R.id.etSec);
		etHour.setText("00");
		etHour.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!TextUtils.isEmpty(s)) {
					int Value=Integer.parseInt(s.toString());
					if (Value>59) {
						etHour.setText("59");
					}else if (Value<0) {
						etHour.setText("0");
					}
				}
				
				checkToInableBtnStart();
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		etMin.setText("00");
		etMin.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!TextUtils.isEmpty(s)){
					int Value=Integer.parseInt(s.toString());
					if (Value>59) {
						etMin.setText("59");
					}else if (Value<0) {
						etMin.setText("0");
					}
				}
				
				checkToInableBtnStart();
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		etSec.setText("00");
		etSec.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			    if (!TextUtils.isEmpty(s)) {
			    	int Value=Integer.parseInt(s.toString());
					if (Value>59) {
						etSec.setText("59");
					}else if (Value<0) {
						etSec.setText("0");
					}
				}
				
				checkToInableBtnStart();
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		btnStart.setVisibility(View.VISIBLE);
		btnStart.setEnabled(false);
		btnPause.setVisibility(View.GONE);
		btnRest.setVisibility(View.GONE);
		btnResume.setVisibility(View.GONE);
	}
	private void checkToInableBtnStart(){
		btnStart.setEnabled((!TextUtils.isEmpty(etHour.getText())&&Integer.parseInt(etHour.getText().toString())>0)||
				(!TextUtils.isEmpty(etMin.getText())&&Integer.parseInt(etMin.getText().toString())>0)||
						(!TextUtils.isEmpty(etSec.getText())&&Integer.parseInt(etSec.getText().toString())>0));
	}
	private void startTimer(){
		if (timerTask==null) {
			allTimerCount=Integer.parseInt(etHour.getText().toString())*60*60
					+Integer.parseInt(etMin.getText().toString())*60+
					Integer.parseInt(etSec.getText().toString());
			timerTask=new TimerTask() {
				
				@Override
				public void run() {
					allTimerCount--;
					
					handler.sendEmptyMessage(MSG_WHAT_TIME_IS_TIC);
					if (allTimerCount<=0) {
						handler.sendEmptyMessage(MSG_WHAT_TIME_IS_UP);
						StopTimer();
					}
					
				}
			};
			
			timer.schedule(timerTask, 1000,1000);
			
			
		}
	}
	private void StopTimer(){
		if (timerTask!=null) {
			timerTask.cancel();
			timerTask=null;
		}
	}
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_WHAT_TIME_IS_UP:
				new AlertDialog.Builder(getContext()).setTitle("Time is up")
				.setMessage("Time is up").setNegativeButton("Cancel", null).show();
				btnRest.setVisibility(View.GONE);
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.GONE);
				btnStart.setVisibility(View.VISIBLE);
				break;
			case MSG_WHAT_TIME_IS_TIC:
				int hour=allTimerCount/60/60;
				int min=(allTimerCount/60)%60;
				int sec=allTimerCount%60;
				etHour.setText(hour+"");
				etMin.setText(min+"");
				etSec.setText(sec+"");
				break;
			default:
				break;
			}
		};
	};
	
	private static final int MSG_WHAT_TIME_IS_UP=1; 
	private static final int MSG_WHAT_TIME_IS_TIC=2; 
	private int allTimerCount=0;
	private Timer timer=new Timer();
	private TimerTask timerTask=null;
	
	private Button btnStart,btnPause,btnResume,btnRest;
	private EditText etHour,etMin,etSec;
}
