package com.oxygen.activity;

import com.oxygen.seat.BookSeat;
import com.oxygen.seat.LoginCas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class SeatActivity extends Activity implements View.OnClickListener{

	private Button btn[] = new Button[6];
	
	private int MSG_BOOK_SUCCESS = 1;
	private ProgressDialog dialog;
	
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.seat);

		btn[0] = (Button)findViewById(R.id.seat_1);
		btn[0].setTag(1);
		btn[0].setOnClickListener(this);
		btn[1] = (Button)findViewById(R.id.seat_2);
		btn[1].setTag(2);
		btn[1].setOnClickListener(this);
		btn[2] = (Button)findViewById(R.id.seat_3);
		btn[2].setTag(3);
		btn[2].setOnClickListener(this);
		btn[3] = (Button)findViewById(R.id.seat_4);
		btn[3].setTag(4);
		btn[3].setOnClickListener(this);
		btn[4] = (Button)findViewById(R.id.seat_5);
		btn[4].setTag(5);
		btn[4].setOnClickListener(this);
		btn[5] = (Button)findViewById(R.id.seat_6);
		btn[5].setTag(6);
		btn[5].setOnClickListener(this);
		
		for(int i=0;i<6;i++){ 
			if(LoginCas.one[i]==1){
				btn[i].setTag(null);//该座位已被预订则不显示
				btn[i].setText("占");
				btn[i].setTextColor(Color.RED);
			}
		}
	}

	public void onClick(View v){
		if((!(BookSeat.libId.equals("0"))&&(!(v.getTag()==null)))){

			dialog = new ProgressDialog(SeatActivity.this);
			dialog.setMessage("预订中...");
			Window window = dialog.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.alpha = 0.7f;
			lp.dimAmount = 0.8f;
			window.setAttributes(lp); 
			dialog.show();
			
			BookSeat.seatId = String.valueOf(v.getTag());
			Thread td = new Thread(mThread);
			td.start();

		}
	}
	
	//Handler
	private Handler mHandler = new Handler(){
      	public void handleMessage(Message msg){
System.out.println("--------read book msg ");
				
      		if(msg.what==MSG_BOOK_SUCCESS){
      			//判断预订成功TODO
      			dialog.cancel();	
//				Toast.makeText(SeatActivity.this,"预订成功", Toast.LENGTH_SHORT).show();
				//跳转界面
				Intent intent = new Intent(SeatActivity.this,OverActivity.class);
				SeatActivity.this.startActivity(intent);
				//finish();
			}else{
				dialog.cancel();
				Toast.makeText(SeatActivity.this,"预订失败",Toast.LENGTH_LONG).show();
				Intent intent = new Intent(SeatActivity.this,LoginActivity.class);
				SeatActivity.this.startActivity(intent);
			}
      	}
    };
	
	//网络访问线程
	private Runnable mThread = new Runnable(){
    	@Override
    	public void run(){
			//连接服务器
System.out.println("book thread start--->>>>>>>>>");

		    try {
					BookSeat.book();
					Message m = Message.obtain();
					m.what = MSG_BOOK_SUCCESS;
					mHandler.sendMessage(m);
				} catch (Exception e) {
System.out.println("book Exception");
					e.printStackTrace();
				}	
    	}
    };
	

	//重写返回键，返回上一个TableXXXXXXXActivity

    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	Thread td =new Thread(bThread);
		td.start();
    	//super.onBackPressed();
    	return ;
    	}


//	public boolean onKeyDown(int keyCode, KeyEvent event){
//		if(keyCode == KeyEvent.KEYCODE_BACK){
//			Thread td =new Thread(bThread);
//			td.start();
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//
//    }  

	

	Runnable bThread = new Runnable() {
  	  public void run() {
//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~`");
//System.out.println(BookSeat.roomId);
  		if(BookSeat.roomId.equals("1202")){
  			Intent backIntent = new Intent();
  			backIntent = new Intent(SeatActivity.this, TableOld1202Activity.class);
  			startActivity(backIntent);
  			SeatActivity.this.finish();
  		}else if(BookSeat.roomId.equals("1203")){
  			Intent backIntent = new Intent();
  			backIntent = new Intent(SeatActivity.this, TableOld1203Activity.class);
  			startActivity(backIntent);
  			SeatActivity.this.finish();
  		}else if(BookSeat.roomId.equals("1303")){
  			Intent backIntent = new Intent();
  			backIntent = new Intent(SeatActivity.this, TableOld1303Activity.class);
  			startActivity(backIntent);
  			SeatActivity.this.finish();
  		}else if(BookSeat.roomId.equals("1401")){
  			Intent backIntent = new Intent();
  			backIntent = new Intent(SeatActivity.this, TableOld1401Activity.class);
  			startActivity(backIntent);
  			SeatActivity.this.finish();
  		}else if(BookSeat.roomId.equals("2107")){
  			Intent backIntent = new Intent();
//  	System.out.println("Back--------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>TableDig2107");
  			backIntent = new Intent(SeatActivity.this, TableDig2107Activity.class);
  			startActivity(backIntent);
  			SeatActivity.this.finish();
  		}else if(BookSeat.roomId.equals("2207")){
  			Intent backIntent = new Intent();
  			backIntent = new Intent(SeatActivity.this, TableDig2207Activity.class);
  			startActivity(backIntent);
  			SeatActivity.this.finish();
  		}else if(BookSeat.roomId.equals("2326")){
  			Intent backIntent = new Intent();
  			backIntent = new Intent(SeatActivity.this, TableDig2326Activity.class);
  			startActivity(backIntent);
  			SeatActivity.this.finish();
  		}
  	  }    
  	    };
	



}

	

