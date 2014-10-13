package com.oxygen.activity;




import cn.waps.AppConnect;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

//查看座位布局
public class CheckLibActivity extends Activity {

	private Button btn_old;
	private Button btn_dig;
	
	private LinearLayout item_book;
	private LinearLayout item_wifi;
	private LinearLayout item_search;
	private LinearLayout item_own;
	private LinearLayout item_email;
	private LinearLayout item_info;
	private LinearLayout item_quit;
	
	LeftSliderLayout leftSliderLayout;
	
	private boolean isExit = false;

	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_lib);

		btn_old = (Button)findViewById(R.id.old_lib);
		btn_dig = (Button)findViewById(R.id.dig_lib);
		item_book = (LinearLayout) findViewById(R.id.item_book);
        item_wifi = (LinearLayout) findViewById(R.id.item_wifi);
        item_search = (LinearLayout) findViewById(R.id.item_search);
        item_own = (LinearLayout) findViewById(R.id.item_own);
        item_email = (LinearLayout) findViewById(R.id.item_email);
        item_info = (LinearLayout) findViewById(R.id.item_info);
        item_quit = (LinearLayout) findViewById(R.id.item_quit);
        leftSliderLayout = (LeftSliderLayout) findViewById(R.id.main_slider_layout);

		btn_old.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				BookSeat.libId = "0";
				//跳转界面
				Intent intent = new Intent(CheckLibActivity.this,RoomOldActivity.class);
				CheckLibActivity.this.startActivity(intent);
			}
		});
	
		btn_dig.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				BookSeat.libId = "0";
				//跳转界面
				Intent intent = new Intent(CheckLibActivity.this,RoomDigActivity.class);
				CheckLibActivity.this.startActivity(intent);
			}
		});
	
		
		// 侧滑菜单：
		//1.呼出订座登陆事件 
		item_book.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(CheckLibActivity.this,LoginActivity.class);
				CheckLibActivity.this.startActivity(intent);
				CheckLibActivity.this.finish();
			}
		});
		//2.已订座位事件 
		item_own.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(CheckLibActivity.this,OwnActivity.class);
				CheckLibActivity.this.startActivity(intent);
				CheckLibActivity.this.finish();
			}
		});
		//3.呼出wifi登陆事件 
		item_wifi.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(CheckLibActivity.this,WifiActivity.class);
				CheckLibActivity.this.startActivity(intent);
				CheckLibActivity.this.finish();
			}
		});
		//4.查看座位分布事件 
		item_search.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(leftSliderLayout.mIsOpen==true){
				leftSliderLayout.close();
			}
			}
		});
		//5.帮助事件 
		item_info.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(CheckLibActivity.this,HelpActivity.class);
				CheckLibActivity.this.startActivity(intent);
				CheckLibActivity.this.finish();
			}
		});
		//6.反馈事件 
		item_email.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(CheckLibActivity.this,EmailActivity.class);
				CheckLibActivity.this.startActivity(intent);
				CheckLibActivity.this.finish();
			}
		});

		//8.退出事件 
		item_quit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//关闭万普广告
            	AppConnect.getInstance(CheckLibActivity.this).close();
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
