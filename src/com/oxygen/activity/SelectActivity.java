package com.oxygen.activity;

import com.oxygen.seat.BookSeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SelectActivity extends Activity {
	
	private Button btn_go;
	private Button btn_random;
	private TextView title_lib;
	private TextView title_room;
	
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select);
		
		
		btn_go = (Button)findViewById(R.id.btn_go);
		btn_random = (Button)findViewById(R.id.btn_random);
		title_lib = (TextView)findViewById(R.id.title_lib);
		title_room = (TextView)findViewById(R.id.title_room);
		
		BookSeat.getLoction();
		title_lib.setText(BookSeat.tv_lib);
		title_room.setText(BookSeat.tv_room);
		
		
		btn_go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(BookSeat.roomId.equals("1202")){
					Intent goIntent = new Intent(SelectActivity.this,TableOld1202Activity.class);
					SelectActivity.this.startActivity(goIntent);
					SelectActivity.this.finish();
				}else if(BookSeat.roomId.equals("1203")){
					Intent goIntent = new Intent(SelectActivity.this,TableOld1203Activity.class);
					SelectActivity.this.startActivity(goIntent);
					SelectActivity.this.finish();
				}else if(BookSeat.roomId.equals("1303")){
					Intent goIntent = new Intent(SelectActivity.this,TableOld1303Activity.class);
					SelectActivity.this.startActivity(goIntent);
					SelectActivity.this.finish();
				}else if(BookSeat.roomId.equals("1401")){
					Intent goIntent = new Intent(SelectActivity.this,TableOld1401Activity.class);
					SelectActivity.this.startActivity(goIntent);
					SelectActivity.this.finish();
				}else if(BookSeat.roomId.equals("2107")){
					Intent goIntent = new Intent(SelectActivity.this,TableDig2107Activity.class);
					SelectActivity.this.startActivity(goIntent);
					SelectActivity.this.finish();
				}else if(BookSeat.roomId.equals("2207")){
					Intent goIntent = new Intent(SelectActivity.this,TableDig2207Activity.class);
					SelectActivity.this.startActivity(goIntent);
					SelectActivity.this.finish();
				}else if(BookSeat.roomId.equals("2326")){
					Intent goIntent = new Intent(SelectActivity.this,TableDig2326Activity.class);
					SelectActivity.this.startActivity(goIntent);
					SelectActivity.this.finish();
				}else if(BookSeat.roomId.equals("1302")){
					Intent goIntent = new Intent(SelectActivity.this,TableOld1302Activity.class);
					SelectActivity.this.startActivity(goIntent);
					SelectActivity.this.finish();
				}
			}
		});
		
		btn_random.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goIntent = new Intent(SelectActivity.this,RandomActivity.class);
				SelectActivity.this.startActivity(goIntent);
				SelectActivity.this.finish();
			}
		});
		
	}
	
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
  			backIntent = new Intent(SelectActivity.this, RoomOldActivity.class);
  			startActivity(backIntent);
  			SelectActivity.this.finish();
  		}else if(BookSeat.roomId.equals("1203")){
  			Intent backIntent = new Intent();
  			backIntent = new Intent(SelectActivity.this, RoomOldActivity.class);
  			startActivity(backIntent);
  			SelectActivity.this.finish();
  		}else if(BookSeat.roomId.equals("1303")){
  			Intent backIntent = new Intent();
  			backIntent = new Intent(SelectActivity.this, RoomOldActivity.class);
  			startActivity(backIntent);
  			SelectActivity.this.finish();
  		}else if(BookSeat.roomId.equals("1401")){
  			Intent backIntent = new Intent();
  			backIntent = new Intent(SelectActivity.this, RoomOldActivity.class);
  			startActivity(backIntent);
  			SelectActivity.this.finish();
  		}else if(BookSeat.roomId.equals("2107")){
  			Intent backIntent = new Intent();
//  	System.out.println("Back--------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>TableDig2107");
  			backIntent = new Intent(SelectActivity.this, RoomDigActivity.class);
  			startActivity(backIntent);
  			SelectActivity.this.finish();
  		}else if(BookSeat.roomId.equals("2207")){
  			Intent backIntent = new Intent();
  			backIntent = new Intent(SelectActivity.this, RoomDigActivity.class);
  			startActivity(backIntent);
  			SelectActivity.this.finish();
  		}else if(BookSeat.roomId.equals("2326")){
  			Intent backIntent = new Intent();
  			backIntent = new Intent(SelectActivity.this, RoomDigActivity.class);
  			startActivity(backIntent);
  			SelectActivity.this.finish();
  		}
  	  }    
  	    };
	
}
