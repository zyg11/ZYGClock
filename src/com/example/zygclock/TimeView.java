package com.example.zygclock;

import java.util.Calendar;

import android.R.anim;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeView extends LinearLayout {

//	public TimeView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		// TODO Auto-generated constructor stub
//	}



	public TimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TimeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		tvTime=(TextView) findViewById(R.id.tvTime);
		tvTime.setText("Hello");
		timHandler.sendEmptyMessage(0);
	}
	@Override
		protected void onVisibilityChanged(View changedView, int visibility) {
			// TODO Auto-generated method stub
			super.onVisibilityChanged(changedView, visibility);
			if (visibility==View.VISIBLE) {
				timHandler.sendEmptyMessage(0);
			}else {
				timHandler.removeMessages(0);
			}
		}
	private void refreshTime(){
		Calendar c=Calendar.getInstance();
		tvTime.setText(String.format("%d:%d:%d", c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND)));
	}
	private Handler timHandler=new Handler(){
		public void handleMessage(android.os.Message msg){
			refreshTime();
			if (getVisibility()==View.VISIBLE) {
				timHandler.sendEmptyMessageDelayed(0,1000);
			}
			
		};
	};
	private TextView tvTime;
}
