package com.example.alwayson.service;

import com.example.alwayson.Constants;

import android.content.Intent;
import android.util.Log;

public class AlwaysOnService extends BaseService {
	private static String LOG_TAG = AlwaysOnService.class.getSimpleName();
	public static boolean isRunning = false;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (isRunning == false) {
			// run something
		}
		if (intent != null) {
			Log.v(LOG_TAG, intent.getStringExtra(Constants.STARTUP_ACTION_NAME));
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// stop running
		isRunning = false;
		super.onDestroy();
	}
}
