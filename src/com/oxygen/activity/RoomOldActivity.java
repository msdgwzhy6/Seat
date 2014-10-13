package com.oxygen.activity;

import com.oxygen.seat.BookSeat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RoomOldActivity extends Activity {
	
	private Button btn_1401;
	private Button btn_1303;
	private Button btn_1302;
	private Button btn_1203;
	private Button btn_1202;
	
	
	private ProgressDialog dialog;
	public int MSG_EMPTY_INFO = 0;
	public int MSG_SEARCH = 1;
	public Thread td;

	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.room_old);

		btn_1401 = (Button)findViewById(R.id.btn_1401);
		btn_1303 = (Button)findViewById(R.id.btn_1303);
		btn_1302 = (Button)findViewById(R.id.btn_1302);
		btn_1203 = (Button)findViewById(R.id.btn_1203);
		btn_1202 = (Button)findViewById(R.id.btn_1202);
		td = new Thread(mThread);


		btn_1401.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				BookSeat.roomId = "1401";
				
				dialog = new ProgressDialog(RoomOldActivity.this);
				dialog.setMessage("加载中...");
				Window window = dialog.getWindow();
				WindowManager.LayoutParams lp = window.getAttributes();
				lp.alpha = 0.7f;
				lp.dimAmount = 0.8f;
				window.setAttributes(lp); 
				dialog.show();
				//查寻座位信息
				td.start();
			}
		});

		btn_1303.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				BookSeat.roomId = "1303";
				
				dialog = new ProgressDialog(RoomOldActivity.this);
				dialog.setMessage("加载中...");
				Window window = dialog.getWindow();
				WindowManager.LayoutParams lp = window.getAttributes();
				lp.alpha = 0.7f;
				lp.dimAmount = 0.8f;
				window.setAttributes(lp); 
				dialog.show();
				//查寻座位信息
				td.start();
			}
		});

		btn_1302.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				BookSeat.roomId = "1302";
				
				dialog = new ProgressDialog(RoomOldActivity.this);
				dialog.setMessage("加载中...");
				Window window = dialog.getWindow();
				WindowManager.LayoutParams lp = window.getAttributes();
				lp.alpha = 0.7f;
				lp.dimAmount = 0.8f;
				window.setAttributes(lp); 
				dialog.show();
				//查寻座位信息
				td.start();
			}
		});

		btn_1203.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				BookSeat.roomId = "1203";
				
				dialog = new ProgressDialog(RoomOldActivity.this);
				dialog.setMessage("加载中...");
				Window window = dialog.getWindow();
				WindowManager.LayoutParams lp = window.getAttributes();
				lp.alpha = 0.7f;
				lp.dimAmount = 0.8f;
				window.setAttributes(lp); 
				dialog.show();
				//查寻座位信息
				td.start();
			}
		});
		btn_1202.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				BookSeat.roomId = "1202";
				
				dialog = new ProgressDialog(RoomOldActivity.this);
				dialog.setMessage("加载中...");
				Window window = dialog.getWindow();
				WindowManager.LayoutParams lp = window.getAttributes();
				lp.alpha = 0.7f;
				lp.dimAmount = 0.8f;
				window.setAttributes(lp); 
				dialog.show();
				//查寻座位信息
				td.start();
			}
		});
    }

	//跳转界面
	public Handler mHandler = new Handler(){
      	public void handleMessage(Message msg){
System.out.println("--------read msg ");
				if(msg.what ==MSG_EMPTY_INFO){
					if(BookSeat.roomId == "1202"){
						Intent intent = new Intent(RoomOldActivity.this,SelectActivity.class);
						RoomOldActivity.this.startActivity(intent);
						dialog.cancel();
						RoomOldActivity.this.finish();
					}else if(BookSeat.roomId == "1203"){
						Intent intent = new Intent(RoomOldActivity.this,SelectActivity.class);
						RoomOldActivity.this.startActivity(intent);
						dialog.cancel();
						RoomOldActivity.this.finish();
					}else if(BookSeat.roomId == "1303"){
						Intent intent = new Intent(RoomOldActivity.this,SelectActivity.class);
						RoomOldActivity.this.startActivity(intent);
						dialog.cancel();
						RoomOldActivity.this.finish();
					}else if(BookSeat.roomId == "1401"){
						Intent intent = new Intent(RoomOldActivity.this,SelectActivity.class);
						RoomOldActivity.this.startActivity(intent);
						dialog.cancel();
						RoomOldActivity.this.finish();
					}else if(BookSeat.roomId == "1302"){
						Intent intent = new Intent(RoomOldActivity.this,TableOld1302Activity.class);
						RoomOldActivity.this.startActivity(intent);
						dialog.cancel();
						RoomOldActivity.this.finish();
					}
      		}else if(msg.what ==MSG_SEARCH){
      			if(BookSeat.roomId == "1202"){
					Intent intent = new Intent(RoomOldActivity.this,TableOld1202Activity.class);
					RoomOldActivity.this.startActivity(intent);
					dialog.cancel();
					RoomOldActivity.this.finish();
				}else if(BookSeat.roomId == "1203"){
					Intent intent = new Intent(RoomOldActivity.this,TableOld1203Activity.class);
					RoomOldActivity.this.startActivity(intent);
					dialog.cancel();
					RoomOldActivity.this.finish();
				}else if(BookSeat.roomId == "1303"){
					Intent intent = new Intent(RoomOldActivity.this,TableOld1303Activity.class);
					RoomOldActivity.this.startActivity(intent);
					dialog.cancel();
					RoomOldActivity.this.finish();
				}else if(BookSeat.roomId == "1401"){
					Intent intent = new Intent(RoomOldActivity.this,TableOld1401Activity.class);
					RoomOldActivity.this.startActivity(intent);
					dialog.cancel();
					RoomOldActivity.this.finish();
				}else if(BookSeat.roomId == "1302"){
					Intent intent = new Intent(RoomOldActivity.this,TableOld1302Activity.class);
					RoomOldActivity.this.startActivity(intent);
					dialog.cancel();
					RoomOldActivity.this.finish();
				}
      		}
				
      	}
	};
	
	//检测空位
	private Runnable mThread = new Runnable(){
    	@Override
    	public void run(){
    		if(BookSeat.libId != "0"){
				//连接服务器
	System.out.println("IsEmpty thread start---->>>>>>>>");	    
				try {
					BookSeat.emptyInfo();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						Message m = Message.obtain();
			    		m.what = MSG_EMPTY_INFO;
			    		mHandler.sendMessage(m);
	    	}else{
	    		Message m = Message.obtain();
	    		m.what = MSG_SEARCH;
	    		mHandler.sendMessage(m);
	    	}
    	}
    };
	
	
	//重写返回键，返回上一个LibraryActivity
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(!(BookSeat.libId.equals("0"))){
			if(keyCode == KeyEvent.KEYCODE_BACK){
            	Intent backIntent = new Intent();
            	backIntent = new Intent(RoomOldActivity.this, LibraryActivity.class);
            	startActivity(backIntent);
            	this.finish();
        	}
		}else{
			if(keyCode == KeyEvent.KEYCODE_BACK){
            	Intent backIntent = new Intent();
            	backIntent = new Intent(RoomOldActivity.this, CheckLibActivity.class);
            	startActivity(backIntent);
            	this.finish();
        	}
		}
		
		
        	return super.onKeyDown(keyCode, event);
	}

}