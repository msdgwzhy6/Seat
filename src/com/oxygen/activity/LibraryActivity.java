package com.oxygen.activity;

import com.oxygen.seat.BookSeat;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LibraryActivity extends Activity {

	private Button btn_old;
	private Button btn_dig;
	Thread td;

	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.library);

		btn_old = (Button)findViewById(R.id.old_lib);
		btn_dig = (Button)findViewById(R.id.dig_lib);
		td = new Thread(mThread);

		btn_old.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
System.out.println("----------------->>>>监听到relativelayout--------");
				BookSeat.libId = "1";
				//跳转界面
				Intent intent = new Intent(LibraryActivity.this,RoomOldActivity.class);
				LibraryActivity.this.startActivity(intent);
				LibraryActivity.this.finish();
			}
		});
	
		btn_dig.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				BookSeat.libId = "2";
				//跳转界面
				Intent intent = new Intent(LibraryActivity.this,RoomDigActivity.class);
				LibraryActivity.this.startActivity(intent);
				LibraryActivity.this.finish();
			}
		});
	
	}
	
		//账号退出线程
		private Runnable mThread = new Runnable(){
	    	@Override
	    	public void run(){
				//连接服务器
	System.out.println("quit thread start--->>>>>>>>>");

			    try {
						BookSeat.quit();
//						Message m = Message.obtain();
//						m.what = MSG_QUIT_SUCCESS;
//						mHandler.sendMessage(m);
					} catch (Exception e) {
	System.out.println("quit Exception");
						e.printStackTrace();
					}	
	    	}
	    };
	

	//重写返回键，返回上一个LoginActivity
	public boolean onKeyDown(int keyCode, KeyEvent event){
    	if(keyCode == KeyEvent.KEYCODE_BACK){
    		td.start();
        	Intent intent = new Intent();
        	intent = new Intent(LibraryActivity.this, LoginActivity.class);
        	startActivity(intent);
        	this.finish();
    	}
    	return super.onKeyDown(keyCode, event);
	}

	
}
