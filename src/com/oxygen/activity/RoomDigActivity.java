package com.oxygen.activity;



import com.oxygen.seat.BookSeat;
import com.oxygen.seat.LoginCas;

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
import android.widget.Toast;

public class RoomDigActivity extends Activity {

	
	public String tableId;

	private Button btn_2326;
	private Button btn_2207;
	private Button btn_2107;
	
	private ProgressDialog dialog;
	
	public int MSG_EMPTY_INFO = 0;
	public int MSG_SEARCH = 1;
	public Thread td;

	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.room_dig);

		btn_2326 = (Button)findViewById(R.id.btn_2326);
		btn_2207 = (Button)findViewById(R.id.btn_2207);
		btn_2107 = (Button)findViewById(R.id.btn_2107);
		td = new Thread(mThread);


		btn_2326.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				BookSeat.roomId = "2326";
				
				dialog = new ProgressDialog(RoomDigActivity.this);
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

		btn_2207.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				BookSeat.roomId = "2207";
				
				dialog = new ProgressDialog(RoomDigActivity.this);
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

		btn_2107.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				BookSeat.roomId = "2107";
				
				dialog = new ProgressDialog(RoomDigActivity.this);
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
					if(BookSeat.roomId == "2107"){
						Intent intent = new Intent(RoomDigActivity.this,SelectActivity.class);
						RoomDigActivity.this.startActivity(intent);
						dialog.cancel();
						RoomDigActivity.this.finish();
					}else if(BookSeat.roomId == "2207"){
						Intent intent = new Intent(RoomDigActivity.this,SelectActivity.class);
						RoomDigActivity.this.startActivity(intent);
						dialog.cancel();
						RoomDigActivity.this.finish();
					}else if(BookSeat.roomId == "2326"){
						Intent intent = new Intent(RoomDigActivity.this,SelectActivity.class);
						RoomDigActivity.this.startActivity(intent);
						dialog.cancel();
						RoomDigActivity.this.finish();
					}
      		}else if(msg.what ==MSG_SEARCH){
      			if(BookSeat.roomId == "2107"){
					Intent intent = new Intent(RoomDigActivity.this,TableDig2107Activity.class);
					RoomDigActivity.this.startActivity(intent);
					dialog.cancel();
					RoomDigActivity.this.finish();
				}else if(BookSeat.roomId == "2207"){
					Intent intent = new Intent(RoomDigActivity.this,TableDig2207Activity.class);
					RoomDigActivity.this.startActivity(intent);
					dialog.cancel();
					RoomDigActivity.this.finish();
				}else if(BookSeat.roomId == "2326"){
					Intent intent = new Intent(RoomDigActivity.this,TableDig2326Activity.class);
					RoomDigActivity.this.startActivity(intent);
					dialog.cancel();
					RoomDigActivity.this.finish();
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
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
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
	
	
	
	
	//重写返回键，返回上一个Activity
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(!(BookSeat.libId.equals("0"))){
			if(keyCode == KeyEvent.KEYCODE_BACK){
            	Intent backIntent = new Intent();
            	backIntent = new Intent(RoomDigActivity.this, LibraryActivity.class);
            	startActivity(backIntent);
            	this.finish();
        	}
		}else{
			if(keyCode == KeyEvent.KEYCODE_BACK){
            	Intent backIntent = new Intent();
            	backIntent = new Intent(RoomDigActivity.this, CheckLibActivity.class);
            	startActivity(backIntent);
            	this.finish();
        	}
		}
		
		
        	return super.onKeyDown(keyCode, event);
	}

}