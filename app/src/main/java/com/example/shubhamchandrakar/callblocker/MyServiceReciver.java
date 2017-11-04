package com.example.shubhamchandrakar.callblocker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import java.lang.reflect.Method;
import database.BaseData;
public class MyServiceReciver extends BroadcastReceiver {
	SharedPreferences sp_pref;
	private ITelephony telephonyService;
	AudioManager m1;
	@Override
	public void onReceive(Context ctx, Intent revice_intent) {
		Bundle bb = revice_intent.getExtras();
		sp_pref = ctx.getSharedPreferences("r_m", ctx.MODE_PRIVATE);
		SharedPreferences.Editor p_id_editor= sp_pref.edit();
		m1 = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
		int sys_rm;
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(ctx.TELEPHONY_SERVICE);
		Log.d("onrecive", revice_intent.getAction());
//		Toast.makeText(ctx, "Service is called", Toast.LENGTH_SHORT).show();
		Log.d("onring", "recive");
		Log.d("onring", "state: "+revice_intent.getAction());
		if (revice_intent.getAction().equalsIgnoreCase("android.intent.action.PHONE_STATE")) {

			String state= revice_intent.getStringExtra(TelephonyManager.EXTRA_STATE);
			Log.d("onring", "state: "+ state);
			if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
				Log.d("AAA", "3 : "+TelephonyManager.EXTRA_STATE_OFFHOOK);
			}
			if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				sys_rm = m1.getRingerMode();
				Log.d("AA", "ringer mode : "+ sys_rm);
				p_id_editor.putInt("r_m", sys_rm);
				p_id_editor.commit();
				Log.d("onring", "call aaya");
				m1.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				String num = revice_intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
				num= num.substring(num.length()-10);
				boolean b1= BaseData.check_block_list(ctx, num);
				if(b1){
					blockCall(ctx, bb);
				}
				else {
					int r_m_old = sp_pref.getInt("r_m", 5);
					Log.d("AA", "r_mode : "+ r_m_old);
					m1.setRingerMode(r_m_old);
				}
//				Toast.makeText(ctx, " Incoming_No...:"+num+" : "+b1, Toast.LENGTH_SHORT).show();
			}
			if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
				int r_m_old = sp_pref.getInt("r_m", 5);
				Log.d("AA", "r_mode 2 : "+ r_m_old);
				m1.setRingerMode(r_m_old);
			}
			else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {


			}


		}





	}
	public void blockCall(Context c, Bundle b)
	{
		Log.d("AA", "block called");
		TelephonyManager telephony = (TelephonyManager)
				c.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Class cls = Class.forName(telephony.getClass().getName());
			Method m = cls.getDeclaredMethod("getITelephony");
			m.setAccessible(true);
			telephonyService = (ITelephony) m.invoke(telephony);
			//telephonyService.silenceRinger();
			telephonyService.endCall();
			Log.d("AA", "block end");
		} catch (Exception e) {
			Log.e("AA", " : "+e);
			e.printStackTrace();
		}

	}

}
