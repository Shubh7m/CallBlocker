package com.example.shubhamchandrakar.callblocker;



import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	MyServiceReciver r1 = new MyServiceReciver();

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		Log.d("aa","a1");
		
		Toast.makeText(this, "Service is start", Toast.LENGTH_SHORT).show();
		Log.d("asdq", "oncreate");
		IntentFilter filter = new IntentFilter(Intent.EXTRA_PHONE_NUMBER);
		filter.addAction("android.intent.action.PHONE_STATE");
		registerReceiver(r1, filter);

		super.onCreate();
	}
	public void onDestroy() {
		unregisterReceiver(r1);
		super.onDestroy();
	}

}
