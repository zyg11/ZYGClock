	package com.example.zygclock;
	
	import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

	public class PlayAlarmATY extends Activity{
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.alarm_player_aty);
			mp=MediaPlayer.create(this,R.raw.canongetup);
			mp.start();
		}
		private MediaPlayer mp;
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			finish();
		}
		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			mp.stop();
			mp.release();
		}
	}
