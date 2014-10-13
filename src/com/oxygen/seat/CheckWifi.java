package com.oxygen.seat;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckWifi {
	//检测wifi是否打开
	public static boolean isWiFiActive(Context inContext) {    
        Context context = inContext.getApplicationContext();    
        ConnectivityManager connectivity = (ConnectivityManager) context    
                .getSystemService(Context.CONNECTIVITY_SERVICE);    
        if (connectivity != null) {    
            NetworkInfo[] info = connectivity.getAllNetworkInfo();    
            if (info != null) {    
                for (int i = 0; i < info.length; i++) {    
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {    
                        return true;    
                    }    
                }    
            }    
        }    
        return false;    
    }
	

	public static boolean isGPRS(Context inContext) {    
        Context context = inContext.getApplicationContext();    
        ConnectivityManager connectivity = (ConnectivityManager) context    
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        
        NetworkInfo mMobile = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        
        if (connectivity != null) {    
            NetworkInfo[] info = connectivity.getAllNetworkInfo();    
            if (mMobile.isAvailable()) {    
                   
                    if (mMobile.isConnected()) {    
                        return true;    
                    }    
                    
            }    
        }    
        return false;    
    }
	
}
