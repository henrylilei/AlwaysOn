package com.example.alwayson.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.alwayson.Constants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class AlwaysOnService extends BaseService {
	private static String LOG_TAG = AlwaysOnService.class.getSimpleName();
	public static boolean isRunning = false;
	private ScheduledExecutorService backgroundService;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (isRunning == false) {
			// run something
			backgroundService = Executors.newSingleThreadScheduledExecutor();
			backgroundService.scheduleAtFixedRate(new TimerIncreasedRunnable(
					this), 0, 1000, TimeUnit.MILLISECONDS);
			isRunning = true;
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// stop running
		isRunning = false;
		backgroundService.shutdownNow();
		super.onDestroy();
	}

	public class TimerIncreasedRunnable implements Runnable {
		private SharedPreferences currentSharedPreferences;

		public TimerIncreasedRunnable(Context context) {
			this.currentSharedPreferences = context.getSharedPreferences(
					Constants.SHAREDPREF_APP_STRING, MODE_PRIVATE);
		}

		@Override
		public void run() {
			int timeCount = this.readTimeCount() + 1;
			this.writeTimeCount(timeCount);
			int currentEpochTimeInSeconds = (int) (System.currentTimeMillis() / 1000L);
			Log.v(LOG_TAG, "Count:" + timeCount + " at time:"
					+ currentEpochTimeInSeconds);
		}

		private int readTimeCount() {
			return this.currentSharedPreferences.getInt(
					Constants.SHAREDPREF_RUNNINGTIMECOUNT_STRING, 0);
		}

		private void writeTimeCount(int timeCount) {
			this.currentSharedPreferences.edit().putInt(
					Constants.SHAREDPREF_RUNNINGTIMECOUNT_STRING,
					timeCount).commit();
		}
	}
}
