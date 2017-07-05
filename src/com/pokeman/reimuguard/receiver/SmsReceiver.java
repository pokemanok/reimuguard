package com.pokeman.reimuguard.receiver;

import com.pokeman.reimuguard.service.LocationService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		// 2,获取短信内容,Protocol Data Unit
		Object[] objects = (Object[]) intent.getExtras().get("pdus");
		// 3,循环遍历短信过程
		for (Object object : objects) {
			// 4,获取短信对象
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
			// 5,获取短信对象的基本信息
			//获取短信发送者手机号
			String originatingAddress = sms.getOriginatingAddress(); 
			String messageBody = sms.getMessageBody();

			if (messageBody.contains("location")) {
				// 8,开启获取位置服务
				context.startService(new Intent(context, LocationService.class));
				System.out.println(originatingAddress);
			}

		}
	}
}
