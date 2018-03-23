package com.example.zygclock;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TabHost;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost=(TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tabTime").setIndicator("时钟").setContent(R.id.tabTime));
		tabHost.addTab(tabHost.newTabSpec("tabAlarm").setIndicator("闹钟").setContent(R.id.tabAlarm));
		tabHost.addTab(tabHost.newTabSpec("tabtimer").setIndicator("计时器").setContent(R.id.tabtimer));
		tabHost.addTab(tabHost.newTabSpec("tabStopWatch").setIndicator("秒表").setContent(R.id.tabStopWatch));
		stopWatcherView=(StopWatcherView) findViewById(R.id.tabStopWatch);
	}
	@Override
	protected void onDestroy() {
		stopWatcherView.onDestroy();
		super.onDestroy();
	}
	private StopWatcherView stopWatcherView;
    private TabHost tabHost;

}
