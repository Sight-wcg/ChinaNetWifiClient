package com.example.chinanet;


import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView textConnected, textIp, textSsid, textBssid, textMac, textSpeed, textRssi,tv;
	public WifiInfo myWifiInfo;
	public Button b1,b2,b3;
	public NetworkInfo myNetworkInfo;
	public ConnectivityManager myConnManager;
	public WifiManager wifiManager;
	public static ClipboardManager cmb;
//	private NetworkConnectChangedReceiver networkConnectChangedReceiver;
	public static String ssid = "123";
//	WifiConnect wifiConnect = new WifiConnect(wifiManager);
//	WifiAutoConnectManager wifiAutoConnectManager = new WifiAutoConnectManager(wifiManager);
	public static Context context;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_main);
		wifiManager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
		cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		cmb.setText("1");
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		String tel = tm.getLine1Number();
		
		if (!wifiManager.isWifiEnabled()) {  
		         wifiManager.setWifiEnabled(true);    
		         }  
		
	    textConnected = (TextView)findViewById(R.id.Connected);
	    textSsid = (TextView)findViewById(R.id.Ssid);
	     //textBssid = (TextView)findViewById(R.id.Bssid);
	    textMac = (TextView)findViewById(R.id.Mac);
	    textSpeed = (TextView)findViewById(R.id.Speed);
	    textRssi = (TextView)findViewById(R.id.Rssi);
	    tv	= (TextView)findViewById(R.id.ipaddress);
		b1 = (Button)findViewById(R.id.button1);
		b2 = (Button)findViewById(R.id.button2);
		b3 = (Button)findViewById(R.id.button3);
//		int netWorkId = wifiInfo.getNetworkId();
//		wifiManager.removeNetwork(netWorkId); 
		 display();  //执行初始化操作！
		 System.out.println("run");
		 System.out.println(tel);
			b1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (myNetworkInfo.isConnected()) {
						int netWorkId = myWifiInfo.getNetworkId();
						wifiManager.removeNetwork(netWorkId);
						wifiManager.saveConfiguration();
						 textConnected.setText("DIS-CONNECTED!-未来开发者");
						   tv.setText("释放IP成功！");
						   System.out.println("wrong!");
						   textSsid.setText("N/A");
						   //textBssid.setText("N/A");
						   textMac.setText("N/A");
						   textSpeed.setText("N/A");
						   textRssi.setText("N/A");
						   startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
						   b1.setText(" 请连接热点！");
						   
					}else{
						ssid = "123";
						tv.setText(" 未连接到热点！");
						b1.setText(" 跳转设置界面！");
						startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
					}
				}
			});
			b2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (ssid.equals("\"ChinaNet\""))
					{
					 Uri uri = Uri.parse("https://wlan.ct10000.com/");  
					 Intent it  = new Intent(Intent.ACTION_VIEW,uri);  
					 startActivity(it);
					 }
					else{
						tv.setText("请连接ChinaNet后登录！");
					}
//					  WifiCipherType type = WifiCipherType.WIFICIPHER_WPA; 
//						 wifiConnect.Connect(ssid,Password,type);
				}
			});
			b3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					display();
				}
			});
//			IntentFilter intentFilter = new IntentFilter();
//			intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//			registerReceiver(networkConnectChangedReceiver, intentFilter);
			receiver.start();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		receiver.close();
	}
	
	public String intToIp(int i){
		return (i & 0xFF ) + "." +       
		        ((i >> 8 ) & 0xFF) + "." +       
		        ((i >> 16 ) & 0xFF) + "." +       
		        ( i >> 24 & 0xFF) ; 
	}

	public void display(){
		myConnManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		 myNetworkInfo = myConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (myNetworkInfo.isConnected()){
				b1.setText(" 释放IP地址！");
			   myWifiInfo = wifiManager.getConnectionInfo();
			   int ipAddress = myWifiInfo.getIpAddress();
			   String ip =intToIp(ipAddress);
			   tv.setText(ip);
			   textConnected.setText("CONNECTED - 未来开发者！");
			   textSsid.setText(myWifiInfo.getSSID());
			   ssid = myWifiInfo.getSSID();
			   System.out.println(ssid);
			   //textBssid.setText(myWifiInfo.getBSSID());
			   textMac.setText(myWifiInfo.getMacAddress());
			   textSpeed.setText(String.valueOf(myWifiInfo.getLinkSpeed()) + " " + WifiInfo.LINK_SPEED_UNITS);
			   textRssi.setText("RSSI:" + String.valueOf(myWifiInfo.getRssi()) + "dBm");
			  }
			  else{
				  tv.setText("N/A");
				   System.out.println("wrong!");
				   textSsid.setText("N/A");
				   ssid = "123";
				   //textBssid.setText("N/A");
				   textMac.setText("N/A");
				   textSpeed.setText("N/A");
				   textRssi.setText("N/A");
				   b1.setText(" 请连接热点！");
			  }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	 public static void setToClipboard(String s){
		if(cmb != null){
			cmb.setText(s);
		}
	}

	Receiver receiver = new Receiver();
	 public static class Receiver extends BroadcastReceiver {
		public static final String ACTION_SET_TO_CLIPBOARD_STRING = "set";
		public static final String EXTRA_PASSWORD_STRING = "password";
		public Receiver(){
			
		}
		public void start(){
			context.registerReceiver(this, new IntentFilter(ACTION_SET_TO_CLIPBOARD_STRING));
			System.out.println("inited");
		}
		public void close(){
			context.unregisterReceiver(this);
		}
		public void onReceive(Context context, Intent intent) {
			if(ACTION_SET_TO_CLIPBOARD_STRING.equals(intent.getAction())){
				String string = intent.getStringExtra(EXTRA_PASSWORD_STRING);
				if(!"".equals(string)){
				
					setToClipboard(string);	
				}else{
					System.out.println("password is null");
				}
			}
	    }
	}

}
