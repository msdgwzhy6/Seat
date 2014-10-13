package com.oxygen.activity;



import cn.waps.AppConnect;

import com.oxygen.activity.R;
import com.oxygen.activity.R.id;
import com.oxygen.activity.R.layout;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class LoadingActivity extends Activity {

int MSG_INIT_OK = 1;
int MSG_INIT_INFO = 2;

 
boolean isTimeout = false;
 

 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        /*信鸽*/
	     // 开启logcat输出，方便debug
	     // 为保证数据安全性，发布时请删除本行或设置为false，否则一切风险自负。
	     XGPushConfig.enableDebug(this, false);
	     XGPushManager.registerPush(getApplicationContext());
	     
	     /*万普*/
	     AppConnect.getInstance(this);
	     
        setContentView(R.layout.loading);
        initSystem();
            
    }
    
    private void initSystem(){
//     Thread td = new Thread(timeOutTask);
//     try {
//		td.sleep(3000);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//     td.start();
     mHandler.postDelayed(timeOutTask,3000);//3秒后调用此Runnable对象
    }
    
    
    public Handler mHandler = new Handler(){     
     public void handleMessage(Message msg){
    	 if(msg.what == MSG_INIT_OK){
    		 startActivity(new Intent(getApplication(),LoginActivity.class));
    		 LoadingActivity.this.finish();
    	 }else {
    		 Toast.makeText(LoadingActivity.this, "timeout", Toast.LENGTH_LONG).show();
    		 LoadingActivity.this.finish();
    	 }
     }     
    };
    
    Runnable timeOutTask = new Runnable() {
    	  public void run() {
    		  
    		  Message msg2 = Message.obtain();
        	  msg2.what = MSG_INIT_OK;
        	  mHandler.sendMessage(msg2);
    	  }    
    	    };
    	    
    	    
    

}