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
import android.widget.TextView;
import android.widget.Toast;

public class OwnActivity extends Activity {

	
	private TextView tv;
	private RelativeLayout share;
	private String ownInfo;
	private String shareInfo;
	
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
		setContentView(R.layout.menu_own);

		tv = (TextView)findViewById(R.id.tv);
		share = (RelativeLayout)findViewById(R.id.share);

		item_book = (LinearLayout) findViewById(R.id.item_book);
        item_wifi = (LinearLayout) findViewById(R.id.item_wifi);
        item_search = (LinearLayout) findViewById(R.id.item_search);
        item_own = (LinearLayout) findViewById(R.id.item_own);
        item_email = (LinearLayout) findViewById(R.id.item_email);
        item_info = (LinearLayout) findViewById(R.id.item_info);

        item_quit = (LinearLayout) findViewById(R.id.item_quit);
        leftSliderLayout = (LeftSliderLayout) findViewById(R.id.main_slider_layout);
        
        //载入万普广告
        LinearLayout adlayout =(LinearLayout)findViewById(R.id.AdLinearLayout);
        AppConnect.getInstance(this).showBannerAd(this, adlayout);


        tv.setText(LoginActivity.sp.getString("BookInfo", ""));
	
        

        share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Intent.ACTION_SEND);  
				intent.setType("text/plain");  //分享的数据类型
				intent.putExtra(Intent.EXTRA_SUBJECT, "重邮订座");  //主题
				intent.putExtra(Intent.EXTRA_TEXT,  LoginActivity.sp.getString("ShareInfo", ""));  //内容
				startActivity(Intent.createChooser(intent, "来自重邮订座APP的分享"));  //目标应用选择对话框的标题
			}
		});
        
		// 侧滑菜单：
		//1.呼出订座登陆事件 
		item_book.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(OwnActivity.this,LoginActivity.class);
				OwnActivity.this.startActivity(intent);
				OwnActivity.this.finish();
			}
		});
		//2.已订座位事件 
		item_own.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(leftSliderLayout.mIsOpen==true){
					leftSliderLayout.close();
				}
			}
		});
		//3.呼出wifi登陆事件 
		item_wifi.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(OwnActivity.this,WifiActivity.class);
				OwnActivity.this.startActivity(intent);
				OwnActivity.this.finish();
			}
		});
		//4.查看座位分布事件 
		item_search.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(OwnActivity.this,CheckLibActivity.class);
				OwnActivity.this.startActivity(intent);
				OwnActivity.this.finish();
			}
		});
		//5.帮助事件 
		item_info.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(OwnActivity.this,HelpActivity.class);
				OwnActivity.this.startActivity(intent);
				OwnActivity.this.finish();
			}
		});
		//6.反馈事件 
		item_email.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(OwnActivity.this,EmailActivity.class);
				OwnActivity.this.startActivity(intent);
				OwnActivity.this.finish();
			}
		});
		
		//8.退出事件 
		item_quit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//关闭万普广告
            	AppConnect.getInstance(OwnActivity.this).close();
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
