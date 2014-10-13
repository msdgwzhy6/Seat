package com.oxygen.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.oxygen.seat.BookSeat;
import com.oxygen.seat.LoginCas;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OverActivity extends Activity {

	private TextView tv_info;
	private Button btn_shake;
	private Button btn_again;
//	private Button btn_quit;
	private boolean isExit = false;
	private	String tv_lib,tv_room;
	
	public String BookInfo = "";
	public String ShareInfo = "";
	public String bookTime = "";
	
	private Dialog shakeDialog ;
	private Dialog againDialog ;
	
	Thread td;//账号退出

	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.over);

		tv_info = (TextView)findViewById(R.id.tv_info);
		btn_shake = (Button)findViewById(R.id.btn_shake);
		btn_again = (Button)findViewById(R.id.btn_again);
//		btn_quit = (Button)findViewById(R.id.btn_quit);
		
		td = new Thread(qThread);
		
		
		//判断图书馆
		if(BookSeat.libId.equals("1")){
			tv_lib = "老图书馆";
			if(BookSeat.roomId.equals("1202")){
				tv_room = "二楼社科借阅处";
			}else if(BookSeat.roomId.equals("1203")){
				tv_room = "二楼法学阅览室";
			}else if(BookSeat.roomId.equals("1303")){
				tv_room = "三楼外文借阅处";
			}else{
				tv_room = "四楼自习阅览室";
			}
		}else{
			tv_lib = "数字图书馆";
			if(BookSeat.roomId.equals("2107")){
				tv_room = "一楼社科阅览室";
			}else if(BookSeat.roomId.equals("2207")){
				tv_room = "二楼自习阅览室";
			}else {
				tv_room = "三楼自习借阅处";
			}
		}
		
		//预定信息

		//输出预定结果

		if(BookSeat.result.equals("预订成功")){
			for(int i =0;i<LoginCas.seat.length;i++){
				LoginCas.seat[i]=0;
			}
			for(int i =0;i<LoginCas.one.length;i++){
				LoginCas.one[i]=0;
			}
			getTime();
			BookInfo = "您已成功预订"+"\n"+bookTime+"\n"+tv_lib+"\n"+tv_room+"\n"+BookSeat.tableId+BookSeat.seatId;
			ShareInfo = "我成功预订到了"+bookTime+tv_lib+tv_room+BookSeat.tableId+BookSeat.seatId+"座位。求偶遇，求不挂科...";
			tv_info.setText("成功预订:\n"+tv_lib+"\n"+tv_room+"\n"+BookSeat.tableId+BookSeat.seatId);
			//String BookInfo = tv_info.getText().toString();
			//存入预定信息
			Editor editor = LoginActivity.sp.edit();
			editor.putString("BookInfo", BookInfo);
			editor.putString("ShareInfo", ShareInfo);
			editor.commit();
		}else{
			tv_info.setText("很遗憾，预订未成功！\n换个座位试试吧。");
		}
		
		
		againDialog = new AlertDialog.Builder(this). 
                setTitle("重新选择座位"). 
                setMessage("您确定要放弃已预定的座位吗？"). 
                //setIcon(R.drawable.icon_launcher). 
                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
                     
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub 
                    	//跳转界面
        				if(BookSeat.roomId.equals("1202")){
        		        		Intent backIntent = new Intent();
        		        		backIntent = new Intent(OverActivity.this, RoomOldActivity.class);
        		        		startActivity(backIntent);
        		        		OverActivity.this.finish();
        		        }else if(BookSeat.roomId.equals("1203")){
        		        		Intent backIntent = new Intent();
        		        		backIntent = new Intent(OverActivity.this, RoomOldActivity.class);
        		        		startActivity(backIntent);
        		        		OverActivity.this.finish();
        		        }else if(BookSeat.roomId.equals("1303")){
        		        		Intent backIntent = new Intent();
        		        		backIntent = new Intent(OverActivity.this, RoomOldActivity.class);
        		        		startActivity(backIntent);
        		        		OverActivity.this.finish();
        		        }else if(BookSeat.roomId.equals("1401")){
        		        		Intent backIntent = new Intent();
        		        		backIntent = new Intent(OverActivity.this, RoomOldActivity.class);
        		        		startActivity(backIntent);
        		        		OverActivity.this.finish();
        		        }else if(BookSeat.roomId.equals("2107")){
        		        		Intent backIntent = new Intent();
        		        		backIntent = new Intent(OverActivity.this, RoomDigActivity.class);
        		        		startActivity(backIntent);
        		        		OverActivity.this.finish();
        		        }else if(BookSeat.roomId.equals("2207")){
        		        		Intent backIntent = new Intent();
        		        		backIntent = new Intent(OverActivity.this, RoomDigActivity.class);
        		        		startActivity(backIntent);
        		        		OverActivity.this.finish();
        		        }else if(BookSeat.roomId.equals("2326")){
        		        		Intent backIntent = new Intent();
        		        		backIntent = new Intent(OverActivity.this, RoomDigActivity.class);
        		        		startActivity(backIntent);
        		        		OverActivity.this.finish();
        		        }
                    } 
                }). 
                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
                     
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub  
                    } 
                }).
                create();
		
		shakeDialog = new AlertDialog.Builder(this). 
                setTitle("摇一摇"). 
                setMessage("您确定要放弃已预订的座位吗？"). 
                //setIcon(R.drawable.ic_launcher). 
                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
                     
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub 
                    	Intent backIntent = new Intent();
		        		backIntent = new Intent(OverActivity.this, RandomActivity.class);
		        		startActivity(backIntent);
		        		OverActivity.this.finish();
                    } 
                }). 
                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
                     
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub  
                    } 
                }).
                create();
		
		
		//摇一摇按钮
		btn_shake.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(BookSeat.result.equals("预订成功")){
					shakeDialog.show(); 
				}else{
					Intent backIntent = new Intent();
	        		backIntent = new Intent(OverActivity.this, RandomActivity.class);
	        		startActivity(backIntent);
	        		OverActivity.this.finish();
				}
			}
		});

		//换个座位重新预订
		btn_again.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(BookSeat.result.equals("预订成功")){
					againDialog.show(); 
				}else{
					
					//跳转界面
					if(BookSeat.roomId.equals("1202")){
			        		Intent backIntent = new Intent();
			        		backIntent = new Intent(OverActivity.this, RoomOldActivity.class);
			        		startActivity(backIntent);
			        		OverActivity.this.finish();
			        }else if(BookSeat.roomId.equals("1203")){
			        		Intent backIntent = new Intent();
			        		backIntent = new Intent(OverActivity.this, RoomOldActivity.class);
			        		startActivity(backIntent);
			        		OverActivity.this.finish();
			        }else if(BookSeat.roomId.equals("1303")){
			        		Intent backIntent = new Intent();
			        		backIntent = new Intent(OverActivity.this, RoomOldActivity.class);
			        		startActivity(backIntent);
			        		OverActivity.this.finish();
			        }else if(BookSeat.roomId.equals("1401")){
			        		Intent backIntent = new Intent();
			        		backIntent = new Intent(OverActivity.this, RoomOldActivity.class);
			        		startActivity(backIntent);
			        		OverActivity.this.finish();
			        }else if(BookSeat.roomId.equals("2107")){
			        		Intent backIntent = new Intent();
			        		backIntent = new Intent(OverActivity.this, RoomDigActivity.class);
			        		startActivity(backIntent);
			        		OverActivity.this.finish();
			        }else if(BookSeat.roomId.equals("2207")){
			        		Intent backIntent = new Intent();
			        		backIntent = new Intent(OverActivity.this, RoomDigActivity.class);
			        		startActivity(backIntent);
			        		OverActivity.this.finish();
			        }else if(BookSeat.roomId.equals("2326")){
			        		Intent backIntent = new Intent();
			        		backIntent = new Intent(OverActivity.this, RoomDigActivity.class);
			        		startActivity(backIntent);
			        		OverActivity.this.finish();
			        }
				}
				
			}
		});

//		btn_quit.setOnClickListener(new OnClickListener(){
//			public void onClick(View v){
///*				
//				boolean flag = false;
//				while(falg){
//				Toast.makeText(LoginActivity.this,"",Toast.LENGTH_LONG).show();						
//				}
//*/				
//				//跳转界面
//				Intent intent = new Intent(Intent.ACTION_MAIN);
//            	intent.addCategory(Intent.CATEGORY_HOME);
//            	startActivity(intent);
//            	System.exit(0);
//			}
//		});

    }


	//重写返回键，按两次返回键退出
/*
*	当按下BACK键时，会被onKeyDown捕获，判断是BACK键，则执行exit方法。
*	在exit()中，首先判断isExit的值，若为false，则置isExit为true，同时弹出提示，并在2秒后发出消息，在Handler中将isExit还原成false
*	如果在发送消息间隔的2秒内，再次按了BACK键，则再次执行exit()，此时isExit的值已为true，则执行退出的方法。
*/
	public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
        	
        	Thread qtd = new Thread(qThread);
        	qtd.start();
        	Intent backIntent = new Intent();
    		backIntent = new Intent(OverActivity.this, LoginActivity.class);
    		startActivity(backIntent);
    		OverActivity.this.finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }

    };
    
    //获取时间
    public void getTime(){
    	
    	DateFormat dateFormat=null;
    	dateFormat=new SimpleDateFormat("MM月dd日");
    	Date nextDate =new Date();
    	long time=(nextDate.getTime()/1000)+60*60*24;
    	nextDate.setTime(time*1000);
    	bookTime=dateFormat.format(nextDate);    	   
	
    }
    
    
  //账号退出线程
  		private Runnable qThread = new Runnable(){
  	    	@Override
  	    	public void run(){
  				//连接服务器
  	System.out.println("quit thread start--->>>>>>>>>");

  			    try {
  						BookSeat.quit();
//  						Message m = Message.obtain();
//  						m.what = MSG_QUIT_SUCCESS;
//  						mHandler.sendMessage(m);
  					} catch (Exception e) {
  	System.out.println("quit Exception");
  						e.printStackTrace();
  					}	
  	    	}
  	    };

}