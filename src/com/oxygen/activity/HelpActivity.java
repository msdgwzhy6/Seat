package com.oxygen.activity;

import cn.waps.AppConnect;
import android.app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.Toast;

public class HelpActivity extends Activity {
	
	private LinearLayout item_book;
	private LinearLayout item_wifi;
	private LinearLayout item_search;
	private LinearLayout item_own;
	private LinearLayout item_email;
	private LinearLayout item_info;
	private LinearLayout item_quit;
	private LeftSliderLayout leftSliderLayout;
	
	private boolean isExit = false;
	
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_help);
		
		item_book = (LinearLayout) findViewById(R.id.item_book);
        item_wifi = (LinearLayout) findViewById(R.id.item_wifi);
        item_search = (LinearLayout) findViewById(R.id.item_search);
        item_own = (LinearLayout) findViewById(R.id.item_own);
        item_email = (LinearLayout) findViewById(R.id.item_email);
        item_info = (LinearLayout) findViewById(R.id.item_info);
        item_quit = (LinearLayout) findViewById(R.id.item_quit);
        leftSliderLayout = (LeftSliderLayout) findViewById(R.id.main_slider_layout);
	
        
     // 侧滑菜单：
     		//1.呼出订座登陆事件 
     		item_book.setOnClickListener(new OnClickListener() {

     			public void onClick(View v) {
     				Intent intent = new Intent(HelpActivity.this,LoginActivity.class);
     				HelpActivity.this.startActivity(intent);
     				HelpActivity.this.finish();
     			}
     		});
     		//2.已订座位事件 
     		item_own.setOnClickListener(new OnClickListener() {

     			public void onClick(View v) {
     				Intent intent = new Intent(HelpActivity.this,OwnActivity.class);
     				HelpActivity.this.startActivity(intent);
     				HelpActivity.this.finish();
     			}
     		});
     		//3.呼出wifi登陆事件 
     		item_wifi.setOnClickListener(new OnClickListener() {

     			public void onClick(View v) {
     				Intent intent = new Intent(HelpActivity.this,WifiActivity.class);
     				HelpActivity.this.startActivity(intent);
     				HelpActivity.this.finish();
     			}
     		});
     		//4.查看座位分布事件 
     		item_search.setOnClickListener(new OnClickListener() {

     			public void onClick(View v) {
     				Intent intent = new Intent(HelpActivity.this,CheckLibActivity.class);
     				HelpActivity.this.startActivity(intent);
     				HelpActivity.this.finish();
     			}
     		});
     		//5.帮助事件 
     		item_info.setOnClickListener(new OnClickListener() {

     			public void onClick(View v) {
     				if(leftSliderLayout.mIsOpen==true){
     					leftSliderLayout.close();
     				}
     			}
     		});
     		//6.反馈事件 
     		item_email.setOnClickListener(new OnClickListener() {

     			public void onClick(View v) {
     				Intent intent = new Intent(HelpActivity.this,EmailActivity.class);
     				HelpActivity.this.startActivity(intent);
     				HelpActivity.this.finish();
     			}
     		});

     		//8.退出事件 
     		item_quit.setOnClickListener(new OnClickListener() {

     			public void onClick(View v) {
     				//关闭万普广告
                	AppConnect.getInstance(HelpActivity.this).close();
     				Intent intent = new Intent(Intent.ACTION_MAIN);
                 	intent.addCategory(Intent.CATEGORY_HOME);
                 	startActivity(intent);
                 	System.exit(0);
     			}
     		});
	
	}
	
	//重写BACK键事件
		public boolean onKeyDown(int keyCode, KeyEvent event){
	        if(keyCode == KeyEvent.KEYCODE_BACK){
	        	leftSliderLayout.open();
	        	exit();
	            return false;
	        }
	        return super.onKeyDown(keyCode, event);
	    }

	    public void exit(){
	        if (!isExit) {
	            isExit = true;
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
	            mHandler.sendEmptyMessageDelayed(0, 2000);
	        } else {
	        	//关闭万普广告
            	AppConnect.getInstance(this).close();
	            Intent intent = new Intent(Intent.ACTION_MAIN);
	            intent.addCategory(Intent.CATEGORY_HOME);
	            startActivity(intent);
	            System.exit(0);
	        }

	    }
	    Handler mHandler = new Handler() {

	        public void handleMessage(Message msg) {
	            // TODO Auto-generated method stub
	            super.handleMessage(msg);
	            isExit = false;
	        }

	    };
	
	
	
}
