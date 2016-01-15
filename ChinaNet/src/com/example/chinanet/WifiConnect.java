package com.example.chinanet;

import java.util.List;  

import android.net.wifi.WifiConfiguration;  
import android.net.wifi.WifiManager;  
import android.util.Log;  
  
public class WifiConnect {  
  
    WifiManager wifiManager;  
      
//���弸�ּ��ܷ�ʽ��һ����WEP��һ����WPA������û����������  
	  public enum WifiCipherType  
	    {  
	      WIFICIPHER_WEP,WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID  
	    }     
//���캯��  
    public WifiConnect(WifiManager wifiManager)  
    {  
      this.wifiManager = wifiManager;  
    }  
      
//��wifi����  
     private boolean OpenWifi()  
     {  
         boolean bRet = true;  
         if (!wifiManager.isWifiEnabled())  
         {  
          bRet = wifiManager.setWifiEnabled(true);    
         }  
         return bRet;  
     }  
      
//�ṩһ���ⲿ�ӿڣ�����Ҫ���ӵ�������  
     public boolean Connect(String SSID, String Password,WifiCipherType Type)  
     {  
    	 System.out.println("connect");
//����wifi������Ҫһ��ʱ��(�����ֻ��ϲ���һ����Ҫ1-3������)������Ҫ�ȵ�wifi  
//״̬���WIFI_STATE_ENABLED��ʱ�����ִ����������  
        System.out.println("connect-1");
        System.out.println("connect-2");
    WifiConfiguration wifiConfig = this.CreateWifiInfo(SSID, Password, Type);  
    System.out.println("connect1");
        //  
        if(wifiConfig == null)  
        {  		
        	   System.out.println("null!");
               return false;  
        }  
        System.out.println("!=null");
        WifiConfiguration tempConfig = this.IsExsits(SSID);  
        System.out.println("hello!");
        if(tempConfig != null)  
        {  
            wifiManager.removeNetwork(tempConfig.networkId);  
            System.out.println("hi!");
        }            
        int netID = wifiManager.addNetwork(wifiConfig);  
        System.out.println("there!");
        boolean bRet = wifiManager.enableNetwork(netID, true);  
        boolean connected = wifiManager.reconnect();
        return bRet;  
     }  
       
    //�鿴��ǰ�Ƿ�Ҳ���ù��������  
     private WifiConfiguration IsExsits(String SSID)  
     {  
    	 System.out.println("is exists");
         List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();  
         System.out.println("get Lists");
            for (WifiConfiguration existingConfig : existingConfigs)   
            {  
            	System.out.println("go for");
              if (existingConfig.SSID.equals("\""+SSID+"\""))  
              {  
            	  System.out.println("return!");
                  return existingConfig;  
                  
              }  
              System.out.println("return!");
            }  
            System.out.println("rerurn null!");
         return null;   
         
     }  
       
     private WifiConfiguration CreateWifiInfo(String SSID, String Password, WifiCipherType Type)  
     {  
        WifiConfiguration config = new WifiConfiguration();    
         config.allowedAuthAlgorithms.clear();  
         config.allowedGroupCiphers.clear();  
         config.allowedKeyManagement.clear();  
         config.allowedPairwiseCiphers.clear();  
         config.allowedProtocols.clear();  
         config.SSID = "\"" + SSID + "\"";    
        if(Type == WifiCipherType.WIFICIPHER_NOPASS)  
        {   
        	
             config.wepKeys[0] = "";  
             config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);  
             config.wepTxKeyIndex = 0;  
             System.out.println("run there");
               
        }  
        if(Type == WifiCipherType.WIFICIPHER_WEP)  
        {  
            config.preSharedKey = "\""+Password+"\"";   
            config.hiddenSSID = true;    
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);  
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);  
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);  
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);  
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);  
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);  
            config.wepTxKeyIndex = 0;  
        }  
        if(Type == WifiCipherType.WIFICIPHER_WPA)  
        {  
        config.preSharedKey = "\""+Password+"\"";  
        config.hiddenSSID = true;    
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);    
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);                          
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);                          
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);                     
        config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);                       
        config.status = WifiConfiguration.Status.ENABLED;    
        }   
        System.out.println("connect success");
        return config;
     }  
       
}  
