package com.oxygen.activity;




import cn.waps.AppConnect;

import com.oxygen.seat.CheckWifi;
import com.oxygen.seat.LoginWifi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class WifiActivity extends Activity {
	
	public static String username,password;

	private TextView tv,tv_care;
	private EditText et_username, et_password;
//	private CheckBox rem_pw;
	private Button btn_login;
	private Button btn_quit;
	private Button btn_seat;
	private CheckBox rem_pw;
	//private SharedPreferences sp;
	private int MSG_LOGIN_FAIL = 0;
	private int MSG_LOGIN_SUCCESS = 1;
	private int MSG_QUIT = -1;
	
	private static int ISVISIBLE = 1;
	
	private LinearLayout item_book;
	private LinearLayout item_wifi;
	private LinearLayout item_search;
	private LinearLayout item_own;
	private LinearLayout item_email;
	private LinearLayout item_info;
	private LinearLayout item_quit;
	private LeftSliderLayout leftSliderLayout;
	
	private boolean isExit = false;
	
	private ProgressDialog dialog;
	private Dialog wifiDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_wifi);
		
		tv = (TextView) findViewById(R.id.tv);
		tv_care = (TextView) findViewById(R.id.tv_care);
		et_username = (EditText) findViewById(R.id.username);
		et_password = (EditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_quit = (Button) findViewById(R.id.btn_quit);
        btn_seat = (Button) findViewById(R.id.btn_seat);
        rem_pw = (CheckBox) findViewById(R.id.cb_pwd);
        item_book = (LinearLayout) findViewById(R.id.item_book);
        item_wifi = (LinearLayout) findViewById(R.id.item_wifi);
        item_search = (LinearLayout) findViewById(R.id.item_search);
        item_own = (LinearLayout) findViewById(R.id.item_own);
        item_email = (LinearLayout) findViewById(R.id.item_email);
        item_info = (LinearLayout) findViewById(R.id.item_info);
        item_quit = (LinearLayout) findViewById(R.id.item_quit);
        leftSliderLayout = (LeftSliderLayout) findViewById(R.id.main_slider_layout);
        
      //判断记住密码多选框的状态,如果WIFIISCHECK的值是false不执行下列语句
        if(LoginActivity.sp.getBoolean("WIFIISCHECK", false)){
	    	  //设置默认是记录密码状态
	          rem_pw.setChecked(true);
	       	  et_username.setText(LoginActivity.sp.getString("WIFIUSER_NAME", ""));
	       	  et_password.setText(LoginActivity.sp.getString("WIFIPASSWORD", ""));
        }
        
/*
setVisibility(View.VISIBLE);   //正常显示
etVisibility(View.INVISIBLE); //隐藏参与布局（还占着地方）
setVisibility(View.GONE);      //隐藏不参与布局（不占地方）
*/
        //判断显示退出按钮
        if(ISVISIBLE == 1){
        	btn_quit.setVisibility(View.GONE);
        	btn_seat.setVisibility(View.GONE);
        }else if(ISVISIBLE == 0){
        	btn_quit.setVisibility(View.VISIBLE);//显示退出按钮
        	btn_seat.setVisibility(View.VISIBLE);//显示去订座按钮
        	
        	et_username.setVisibility(View.INVISIBLE);
        	et_password.setVisibility(View.INVISIBLE);
        	btn_login.setVisibility(View.GONE);
        	rem_pw.setVisibility(View.INVISIBLE);
        	tv_care.setVisibility(View.INVISIBLE);
        	tv.setText("WIFI-CQUPT认证成功");
        }
        
        //检测WIFI
        wifiDialog = new AlertDialog.Builder(this). 
                setTitle("没有可用WiFi网络"). 
                setMessage("当前WiFi网络不可用，是否设置WiFi网络?"). 
                //setIcon(R.drawable.ic_launcher). 
                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
                     
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
        
        if(CheckWifi.isWiFiActive(WifiActivity.this)==false){
        	wifiDialog.show();
		}
        
        
      //监听记住密码多选框按钮事件
      		rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
      				if (rem_pw.isChecked()) {
                          
      					System.out.println("WIFI记住密码已选中");
      					LoginActivity.sp.edit().putBoolean("WIFIISCHECK", true).commit();
      					
      				}else {
      			
      					System.out.println("WIFI记住密码没有选中");
      					LoginActivity.sp.edit().putBoolean("WIFIISCHECK", false).commit();
      					
      				}

      			}
      		});
        
        //登陆WIFI按钮
        btn_login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(et_username.getText().toString().equals("")){
					Toast.makeText(WifiActivity.this,"请输入账号", Toast.LENGTH_SHORT).show();
				}else if(et_password.getText().toString().equals("")){
					Toast.makeText(WifiActivity.this,"请输入密码", Toast.LENGTH_SHORT).show();
				}else{
					dialog = new ProgressDialog(WifiActivity.this);
					//dialog.setTitle("");
					dialog.setMessage("登录中...");
					Window window = dialog.getWindow();
					WindowManager.LayoutParams lp = window.getAttributes();
					lp.alpha = 0.7f;
					lp.dimAmount = 0.8f;
					window.setAttributes(lp); 
					dialog.show();
					
	System.out.println("-------->>>>--click wifi btn_login");
					username = et_username.getText().toString();
				    password = et_password.getText().toString();
	System.out.println("-------->>>>--input wifi name&pwd");
				    Thread td = new Thread(mThread);
				    td.start();
				}
			}
		});
        
    	btn_quit.setOnClickListener(new OnClickListener(){
    		public void onClick(View v){
    			Thread td2 = new Thread(qThread);
    		    td2.start();
    		    tv.setTextColor(Color.rgb(255,165,0));
    		    tv.setText("重邮WIFI-CQUPT认证");
    		}
    	});
    	
    	btn_seat.setOnClickListener(new OnClickListener(){
    		public void onClick(View v){
    			Intent backIntent = new Intent();
      			backIntent = new Intent(WifiActivity.this, LoginActivity.class);
      			startActivity(backIntent);
      			WifiActivity.this.finish();
    		}
    	});
        
    	// 侧滑菜单：
    			//1.呼出订座登陆事件 
    			item_book.setOnClickListener(new OnClickListener() {

    				public void onClick(View v) {
    					Intent intent = new Intent(WifiActivity.this,LoginActivity.class);
    					WifiActivity.this.startActivity(intent);
    					//WifiActivity.this.finish();
    				}
    			});
    			//2.已订座位事件 
    			item_own.setOnClickListener(new OnClickListener() {

    				public void onClick(View v) {
    					Intent intent = new Intent(WifiActivity.this,OwnActivity.class);
    					WifiActivity.this.startActivity(intent);
    				}
    			});
    			//3.呼出wifi登陆事件 
    			item_wifi.setOnClickListener(new OnClickListener() {

    				public void onClick(View v) {
    					if(leftSliderLayout.mIsOpen==true){
    						leftSliderLayout.close();
    					}
    				}
    			});
    			//4.查看座位分布事件 
    			item_search.setOnClickListener(new OnClickListener() {

    				public void onClick(View v) {
    					Intent intent = new Intent(WifiActivity.this,CheckLibActivity.class);
    					WifiActivity.this.startActivity(intent);
    					//WifiActivity.this.finish();
    				}
    			});
    			//5.帮助事件 
    			item_info.setOnClickListener(new OnClickListener() {

    				public void onClick(View v) {
    					Intent intent = new Intent(WifiActivity.this,HelpActivity.class);
    					WifiActivity.this.startActivity(intent);
    					//WifiActivity.this.finish();
    				}
    			});
    			//6.反馈事件 
    			item_email.setOnClickListener(new OnClickListener() {

    				public void onClick(View v) {
    					Intent intent = new Intent(WifiActivity.this,EmailActivity.class);
    					WifiActivity.this.startActivity(intent);
    					//WifiActivity.this.finish();
    				}
    			});
    			//8.退出事件 
    			item_quit.setOnClickListener(new OnClickListener() {

    				public void onClick(View v) {
    					ISVISIBLE=1;
    					//关闭万普广告
    	            	AppConnect.getInstance(WifiActivity.this).close();
    					Intent intent = new Intent(Intent.ACTION_MAIN);
    	            	intent.addCategory(Intent.CATEGORY_HOME);
    	            	startActivity(intent);
    	            	System.exit(0);
    				}
    			});

	}
	

	
	public Handler mHandler = new Handler(){
      	public void handleMessage(Message msg){
System.out.println("--------read wifilogin msg ");
//				if(msg.what ==MSG_LOGIN_FAIL){
//					dialog.cancel();
//      			Toast.makeText(WifiActivity.this,"服务器异常，请重试或使用浏览器登录",Toast.LENGTH_SHORT).show();
//      			// LoginActivity.this.finish();        			
//      		}

      		if(msg.what==MSG_LOGIN_SUCCESS){
      			//判断登陆成功TODO
      			if(LoginWifi.isServer != true){
      				dialog.cancel();
						Toast.makeText(WifiActivity.this,"服务器异常，请稍后重试或尝试浏览器登录。", Toast.LENGTH_SHORT).show();
					}else{
						if(LoginWifi.isLogined != true){
							dialog.cancel();
							Toast.makeText(WifiActivity.this,"账号或密码有误，请重试。", Toast.LENGTH_SHORT).show();
						}else{
							if(LoginWifi.info.equals("无线上网认证成功")==true){
								dialog.cancel();
								LoginWifi.info = "";
								Toast.makeText(WifiActivity.this,"WIFI-CQUPT登录成功", Toast.LENGTH_SHORT).show();
								
								ISVISIBLE=0;
								btn_quit.setVisibility(View.VISIBLE);//显示退出按钮
								btn_seat.setVisibility(View.VISIBLE);//显示去订座按钮
					        	et_username.setVisibility(View.INVISIBLE);
					        	et_password.setVisibility(View.INVISIBLE);
					        	btn_login.setVisibility(View.GONE);
					        	rem_pw.setVisibility(View.INVISIBLE);
					        	tv_care.setVisibility(View.INVISIBLE);
					        	
								LoginWifi.isServer = false;
								LoginWifi.isLogined = false;
								
								//登录成功和记住密码框为选中状态才保存用户信息
								if(rem_pw.isChecked()){
				 					//记住用户名、密码，存入sp
				  					Editor editor = LoginActivity.sp.edit();
				  					editor.putString("WIFIUSER_NAME", username);
				  					editor.putString("WIFIPASSWORD",password);
				  					editor.commit();
								}
								
								tv.setTextColor(Color.rgb(255,165,0));
								tv.setText("WIFI-CQUPT认证成功");
							}
							
						}
					}				
				}else if(msg.what==MSG_QUIT){
					ISVISIBLE=1;
					btn_quit.setVisibility(View.GONE);//只显示退出按钮
					btn_seat.setVisibility(View.GONE);
					
		        	et_username.setVisibility(View.VISIBLE);
		        	et_password.setVisibility(View.VISIBLE);
		        	btn_login.setVisibility(View.VISIBLE);
		        	rem_pw.setVisibility(View.VISIBLE);
		        	tv_care.setVisibility(View.VISIBLE);
		        	tv.setVisibility(View.VISIBLE);
					Toast.makeText(WifiActivity.this,"账号已退出", Toast.LENGTH_SHORT).show();
				}else {
					dialog.cancel();
					Toast.makeText(WifiActivity.this,"服务器异常，请稍后重试",Toast.LENGTH_SHORT).show();	
				}
      	}
      };

      //登录线程
		private Runnable mThread = new Runnable(){
        	@Override
        	public void run(){
				//连接服务器
System.out.println("网络线程已启动");
			    try {
			    	LoginWifi.isLogined = LoginWifi.login(LoginWifi.loginUrl);
					} catch (Exception e) {
System.out.println("login exception");
						e.printStackTrace();
					}
					Message m = Message.obtain();
			    	m.what = MSG_LOGIN_SUCCESS;
			    	mHandler.sendMessage(m);
        	}
        };
        
        
        //退出
        private Runnable qThread = new Runnable(){
        	@Override
        	public void run(){
    			//连接服务器
    System.out.println("网络线程已启动");

    		    try {
    		    	LoginWifi.quit(LoginWifi.quitUrl);
    					Message m = Message.obtain();
    					m.what = MSG_QUIT;
    					mHandler.sendMessage(m);
    				} catch (Exception e) {
    System.out.println("book Exception");
    					e.printStackTrace();
    				}	
        	}
        };
        
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
                   qHandler.sendEmptyMessageDelayed(0, 2000);
               } else {
               	//关闭万普广告
               	AppConnect.getInstance(this).close();
               	
                   Intent intent = new Intent(Intent.ACTION_MAIN);
                   intent.addCategory(Intent.CATEGORY_HOME);
                   startActivity(intent);
                   System.exit(0);
               }

           }
           Handler qHandler = new Handler() {

               public void handleMessage(Message msg) {
                   // TODO Auto-generated method stub
                   super.handleMessage(msg);
                   isExit = false;
               }

           };


}
