package com.example.chinanet;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;

public class SmsReciver extends BroadcastReceiver {
	public String password;
	 public void onReceive(Context context, Intent intent) {  
	        Bundle bundle = intent.getExtras();  
	        SmsMessage msg = null;  
	        if (null != bundle) {  
	            Object[] smsObj = (Object[]) bundle.get("pdus");  
	            for (Object object : smsObj) {  
	                msg = SmsMessage.createFromPdu((byte[]) object);  
	                Date date = new Date(msg.getTimestampMillis());//时间  
	                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	                String receiveTime = format.format(date);  
	                System.out.println("number:" + msg.getOriginatingAddress()  
	                + "   body:" + msg.getDisplayMessageBody() + "  time:"  
	                        + msg.getTimestampMillis());  
	                  
	                //在这里写自己的逻辑  
	                if (msg.getOriginatingAddress().equals("10001")) {  
	                    //TODO  
	                 	char[] sms= msg.getDisplayMessageBody().toCharArray();
	                	char[] smsb = new char[8];
	                	
	                	for(int i=17,k=0;i<25;i++,k++){
	                		smsb[k] = sms[i];
	                	}
	                 	password = String.valueOf(smsb);
	                	System.out.println(password);
	                	Toast.makeText(context, "密码已存入剪贴板", Toast.LENGTH_SHORT).show();
	                	Intent i = new Intent(MainActivity.Receiver.ACTION_SET_TO_CLIPBOARD_STRING);
	                	i.putExtra(MainActivity.Receiver.EXTRA_PASSWORD_STRING, password);
	                	context.sendBroadcast(i);
	                }  
	                  
	            }  
	        }  
	    }  

}
