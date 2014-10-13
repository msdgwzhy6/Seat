package com.oxygen.activity;

import cn.waps.AppConnect;

import com.oxygen.feedback.SetMail;
import com.oxygen.seat.CheckWifi;
import com.oxygen.seat.LoginWifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.Toast;

public class EmailActivity extends Activity {
	private Button submit;
	private EditText et_back;
	private EditText et_email;
	private String ownInfo;
	
	private LinearLayout item_book;
	private LinearLayout item_wifi;
	private LinearLayout item_search;
	private LinearLayout item_own;
	private LinearLayout item_email;
	private LinearLayout item_info;
	private LinearLayout item_other;
	private LinearLayout item_quit;
	private LeftSliderLayout leftSliderLayout;
	
	private boolean isExit = false;
	
	private int MSG_ET_BACK_NULL = -1; 
	private int MSG_ET_EMAIL_NULL = 0;
	private int MSG_OK = 1;

	ProgressDialog dialog;
	private Dialog wifiDialog;
	boolean key = false; 
	
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_email);

		et_back = (EditText)findViewById(R.id.et_back);
		et_email = (EditText)findViewById(R.id.et_email);
		submit = (Button)findViewById(R.id.submit);

		item_book = (LinearLayout) findViewById(R.id.item_book);
        item_wifi = (LinearLayout) findViewById(R.id.item_wifi);
        item_search = (LinearLayout) findViewById(R.id.item_search);
        item_own = (LinearLayout) findViewById(R.id.item_own);
        item_email = (LinearLayout) findViewById(R.id.item_email);
        item_info = (LinearLayout) findViewById(R.id.item_info);
        item_quit = (LinearLayout) findViewById(R.id.item_quit);
        leftSliderLayout = (LeftSliderLayout) findViewById(R.id.main_slider_layout);



//检测WIFI
        
        
        wifiDialog = new AlertDialog.Builder(this). 
                setTitle("网络WIFI-CQUPT不可用 "). 
                setMessage("您是否要关闭 WIFI-CQUPT，切换其他网络？"). 
                //setIcon(R.drawable.ic_launcher). 
                setPositiveButton("确认", new DialogInterface.OnClickListener() { 
                     
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub 
                    	startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    } 
                }). 
                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
                     
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub  
                    } 
                }).
                create();
        
      
		
     // 登录监听事件 
     		submit.setOnClickListener(new OnClickListener() {

     			public void onClick(View v) {
     				if(CheckWifi.isWiFiActive(EmailActivity.this)==false||key==true){
     					if(et_back.getText().toString().equals("")==true){
     						Toast.makeText(getApplicationContext(), "请您填写反馈信息", Toast.LENGTH_SHORT).show();
     					}else if(et_email.getText().toString().equals("")==true){
     						Toast.makeText(getApplicationContext(), "请您填写联系方式", Toast.LENGTH_SHORT).show();
     					}else{
		     				dialog = new ProgressDialog(EmailActivity.this);
		     				//dialog.setTitle("");
		     				dialog.setMessage("请稍后...");
		     				Window window = dialog.getWindow();
		     				WindowManager.LayoutParams lp = window.getAttributes();
		     				lp.alpha = 0.7f;
		     				lp.dimAmount = 0.8f;
		     				window.setAttributes(lp); 
		     				dialog.show();
		     				Thread td = new Thread(mThread);
		        		    td.start();
     					}
     				}else{
     					wifiDialog.show();
     					key=true;
     				}
     			}
     		});
     		
     		
		// 侧滑菜单：
		//1.呼出订座登陆事件 
		item_book.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(EmailActivity.this,LoginActivity.class);
				EmailActivity.this.startActivity(intent);
				EmailActivity.this.finish();
			}
		});
		//2.已订座位事件 
		item_own.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(EmailActivity.this,OwnActivity.class);
				EmailActivity.this.startActivity(intent);
				EmailActivity.this.finish();
			}
		});
		//3.呼出wifi登陆事件 
		item_wifi.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(EmailActivity.this,WifiActivity.class);
				EmailActivity.this.startActivity(intent);
				EmailActivity.this.finish();
			}
		});
		//4.查看座位分布事件 
		item_search.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(EmailActivity.this,CheckLibActivity.class);
				EmailActivity.this.startActivity(intent);
				EmailActivity.this.finish();
			}
		});
		//5.帮助事件 
		item_info.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(EmailActivity.this,HelpActivity.class);
				EmailActivity.this.startActivity(intent);
				EmailActivity.this.finish();
			}
		});
		//6.反馈事件 
		item_email.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(leftSliderLayout.mIsOpen==true){
					leftSliderLayout.close();
				}
			}
		});

		//8.退出事件 
		item_quit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AppConnect.getInstance(EmailActivity.this).close();
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
    
    
    //发送邮件
    public Handler sendHandler = new Handler(){
      	public void handleMessage(Message msg){
//System.out.println("--------read msg ");
			if(msg.what ==MSG_ET_BACK_NULL){
				Toast.makeText(EmailActivity.this,"请输入反馈内容",Toast.LENGTH_SHORT).show();       			
			}else{
				if(msg.what==MSG_ET_EMAIL_NULL){
					Toast.makeText(EmailActivity.this,"请输入您的联系邮箱或QQ",Toast.LENGTH_SHORT).show();
				}else{
					dialog.cancel();
					Toast.makeText(EmailActivity.this,"感谢您对本应用的支持，我们会及时处理您的反馈信息并给予回复。",Toast.LENGTH_SHORT).show();
					et_back.setText("");
					et_email.setText("");
				}
			}
      		
      	}
      };

		private Runnable mThread = new Runnable(){
        	@Override
        	public void run(){
				//连接服务器
        		if(et_back.getText().toString().equals("")){
        			Message m = Message.obtain();
		    		m.what = MSG_ET_BACK_NULL;
		    		sendHandler.sendMessage(m);
        		}else{
        			if(et_email.getText().toString().equals("")){
        				Message m = Message.obtain();
    		    		m.what = MSG_ET_EMAIL_NULL;
    		    		sendHandler.sendMessage(m);
        			}else{
        				SetMail.content = et_back.getText().toString()+"from:"+et_email.getText().toString();
//System.out.println("print content-------@@@@@@");        				
//System.out.println(SetMail.content);        				
        				SetMail.setMail();
        				Message m = Message.obtain();
    		    		m.what = MSG_OK;
    		    		sendHandler.sendMessage(m);
        			}
        		}
			    	
        	}
        };
}
