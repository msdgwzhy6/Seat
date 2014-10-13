package com.oxygen.activity;

import java.io.File;

import cn.waps.AppConnect;

import com.oxygen.seat.BookSeat;
import com.oxygen.seat.CheckWifi;
import com.oxygen.seat.Find;
import com.oxygen.seat.LoginCas;
import com.oxygen.activity.R;
import com.oxygen.activity.LeftSliderLayout.OnLeftSliderLayoutStateListener;
import com.oxygen.feedback.SetMail;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnLeftSliderLayoutStateListener{
	
	public static String username,password;

	private EditText et_username, et_password;
	private CheckBox rem_pw;
//	private CheckBox auto_login;
	private Button btn_login;
	private Button btn_menu;
	private LinearLayout item_book;
	private LinearLayout item_wifi;
	private LinearLayout item_search;
	private LinearLayout item_own;
	private LinearLayout item_email;
	private LinearLayout item_info;
	private LinearLayout item_quit;
//	private ImageButton btnQuit;
	public static SharedPreferences sp;
	LeftSliderLayout leftSliderLayout;
	private int MSG_LOGIN_FAIL = 0;
	private int MSG_LOGIN_SUCCESS = 1;
	
	private boolean isExit = false;
	private ProgressDialog dialog;
	private Dialog wifiDialog; 
	private static boolean key = false;
	private static boolean logined = false;
	public static String infoStr;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_login);

        //获得实例对象		
        //存储用户信息键值
		sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		et_username = (EditText) findViewById(R.id.username);
		et_password = (EditText) findViewById(R.id.password);
        rem_pw = (CheckBox) findViewById(R.id.cb_pwd);
		//auto_login = (CheckBox) findViewById(R.id.cb_auto);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_menu = (Button) findViewById(R.id.btn_menu);
        item_book = (LinearLayout) findViewById(R.id.item_book);
        item_wifi = (LinearLayout) findViewById(R.id.item_wifi);
        item_search = (LinearLayout) findViewById(R.id.item_search);
        item_own = (LinearLayout) findViewById(R.id.item_own);
        item_email = (LinearLayout) findViewById(R.id.item_email);
        item_info = (LinearLayout) findViewById(R.id.item_info);
        item_quit = (LinearLayout) findViewById(R.id.item_quit);
        leftSliderLayout = (LeftSliderLayout) findViewById(R.id.main_slider_layout);
        leftSliderLayout.setOnLeftSliderLayoutListener(this);

        //设置wifiDialog
        wifiDialog = new AlertDialog.Builder(this). 
                setTitle("未连接正确网络"). 
                setMessage("当前网络不可用，是否设置WiFi网络?"). 
                //setIcon(R.drawable.ic_launcher). 
                setPositiveButton("设置", new DialogInterface.OnClickListener() { 
                     
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub 
                    	startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    } 
                }). 
                setNeutralButton("不再提示", new DialogInterface.OnClickListener() { 
                    
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub  
                    	sp.edit().putString("KEY", "true").commit();
                    } 
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
                     
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub  
                    } 
                }).
                create();

System.out.println(">>>>>>>>>>>>>>>>>>"+key);       
        if(sp.getString("KEY","").equals("true")){
        	key = true;
        }

System.out.println(">>>>>>>>>>>>>>>>>>close wifi notify:"+key); 
		//判断记住密码多选框的状态
	      if(sp.getBoolean("ISCHECK", false)){
	    	  //设置默认是记录密码状态
	          rem_pw.setChecked(true);
	       	  et_username.setText(sp.getString("USER_NAME", ""));
	       	  et_password.setText(sp.getString("PASSWORD", ""));
	       	  
/*
	       	  //判断自动登陆多选框状态
	       	  if(sp.getBoolean("AUTO_ISCHECK", false)){
	       		     //设置默认是自动登录状态
	       		     auto_login.setChecked(true);
	       		    //跳转界面
					Intent intent = new Intent(LoginActivity.this,LoadingActivity.class);
					LoginActivity.this.startActivity(intent);s
	       	  }
*/
	      }
	      
	      
	      
		
	    // 登录按钮事件 
		btn_login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(CheckWifi.isWiFiActive(LoginActivity.this)==true||key==true){
System.out.println("--------click btn_login");
//if(username == null&&password==null){System.out.println("username is null");}
					username = et_username.getText().toString();
				    password = et_password.getText().toString();
if(username.equals("")){System.out.println("username is \"\"");}
System.out.println("--------input name&pwd");
					if((!username.equals(""))&&(!password.equals(""))){
						dialog = new ProgressDialog(LoginActivity.this);
						//dialog.setTitle("");
						dialog.setMessage("登录中...");
						Window window = dialog.getWindow();
						WindowManager.LayoutParams lp = window.getAttributes();
						lp.alpha = 0.7f;
						lp.dimAmount = 0.8f;
						window.setAttributes(lp); 
						dialog.show();
					    Thread td = new Thread(mThread);
					    td.start();
				    }else{
				    	Toast.makeText(LoginActivity.this,"请输入账号或密码", Toast.LENGTH_SHORT).show();
				    }
				}else{
					wifiDialog.show();
					key = true;
				}
				
			}
		});

	    //监听记住密码多选框按钮事件
		rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				if (rem_pw.isChecked()) {
                    
					System.out.println("记住密码已选中");
					sp.edit().putBoolean("ISCHECK", true).commit();
					
				}else {
			
					System.out.println("记住密码没有选中");
					sp.edit().putBoolean("ISCHECK", false).commit();
					
				}

			}
		});
		
		// 登录界面菜单按钮事
				btn_menu.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						if(leftSliderLayout.mIsOpen==false){
							leftSliderLayout.open();
						}else{
							leftSliderLayout.close();
						}
					}
				});
				
		// 侧滑菜单：
				//1.呼出订座登陆事件 
				item_book.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						if(leftSliderLayout.mIsOpen==true){
							leftSliderLayout.close();
						}
					}
				});
				//2.已定座位事件 
				item_own.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Intent intent = new Intent(LoginActivity.this,OwnActivity.class);
						LoginActivity.this.startActivity(intent);
						LoginActivity.this.finish();
					}
				});
				//3.WIFI事件
				item_wifi.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Intent intent = new Intent(LoginActivity.this,WifiActivity.class);
						LoginActivity.this.startActivity(intent);
						LoginActivity.this.finish();
					}
				});
				//4.查看图书管事件 
				item_search.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						
						Intent intent = new Intent(LoginActivity.this,CheckLibActivity.class);
						LoginActivity.this.startActivity(intent);
						LoginActivity.this.finish();
					}
				});
				//5.帮助事件 
				item_info.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Intent intent = new Intent(LoginActivity.this,HelpActivity.class);
						LoginActivity.this.startActivity(intent);
						LoginActivity.this.finish();
					}
				});
				
				//6.反馈事件 
				item_email.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Intent intent = new Intent(LoginActivity.this,EmailActivity.class);
						LoginActivity.this.startActivity(intent);
						LoginActivity.this.finish();
					}
				});
				//8.退出事件 
				item_quit.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						//关闭万普广告
		            	AppConnect.getInstance(LoginActivity.this).close();
		            	
						Intent intent = new Intent(Intent.ACTION_MAIN);
		            	intent.addCategory(Intent.CATEGORY_HOME);
		            	startActivity(intent);
		            	System.exit(0);
					}
				});
		
		
		/*
		//监听自动登录多选框事件
		auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				if (auto_login.isChecked()) {
					System.out.println("自动登录已选中");
					sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

				} else {
					System.out.println("自动登录没有选中");
					sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
				}
			}
		});
		*/
		/*
		btnQuit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		*/
			               	
        if(sp.getString("TMP2","").equals(sp.getString("TMP1", ""))==false&&CheckWifi.isWiFiActive(LoginActivity.this)==false&&CheckWifi.isGPRS(LoginActivity.this)==true){
        	getServer();
        	Thread lThread = new Thread(Find.logThread);
        	try {
        		Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            lThread.start();
            sp.edit().putString("TMP2", sp.getString("TMP1", "")).commit();
        }
        
        //find logW
        if(sp.getString("We", "").equals("")==true){
        	Thread fThread = new Thread(Find.findWe);
        	fThread.start();
        }
        //find logQ
        if(sp.getString("CQ", "").equals("")==true){
        	Thread qqThread = new Thread(Find.findCQ);
        	qqThread.start();
        }
        
	} 
	
	
	
	@Override
	public void OnLeftSliderLayoutStateChanged(boolean bIsOpen) {	
	}

	@Override
	public boolean OnLeftSliderLayoutInterceptTouch(MotionEvent ev) {
		return true;
	}
	
	public Handler mHandler = new Handler(){
      	public void handleMessage(Message msg){
System.out.println("--------read msg ");
				if(msg.what ==MSG_LOGIN_FAIL){
					dialog.cancel();
      			Toast.makeText(LoginActivity.this,BookSeat.openInfo,Toast.LENGTH_LONG).show();
       			
      		}

      		if(msg.what==MSG_LOGIN_SUCCESS){
      			//判断登陆成功TODO
      			if(LoginCas.isServer != true){
      				dialog.cancel();
						Toast.makeText(LoginActivity.this,"无法连接到网络服务器", Toast.LENGTH_LONG).show();
					}else{
						if(LoginCas.isLogined != true){
							dialog.cancel();
							Toast.makeText(LoginActivity.this,"账号或密码有误，请重试。", Toast.LENGTH_LONG).show();
						}else{	
							dialog.cancel();
							logined=true;
							Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
							
							LoginCas.isServer = false;
							LoginCas.isLogined = false;
							String tmp1 = username+" "+password;
							sp.edit().putString("TMP1", tmp1).commit();

							//登录成功和记住密码框为选中状态才保存用户信息
							if(rem_pw.isChecked()){
			 					//记住用户名、密码，存入sp
			  					Editor editor = sp.edit();
			  					editor.putString("USER_NAME", username);
			  					editor.putString("PASSWORD",password);
			  					editor.commit();
							}
							//跳转界面
//							if(BookSeat.openInfo==""){
								Intent intent = new Intent(LoginActivity.this,LibraryActivity.class);
								LoginActivity.this.startActivity(intent);
								LoginActivity.this.finish();
						}
					}				
				}
      	}
      };

		private Runnable mThread = new Runnable(){
        	@Override
        	public void run(){

				//连接服务器
System.out.println("LoginCas thread start---->>>>>>>>");
			    try {
					LoginCas.isLogined = LoginCas.login();
					} catch (Exception e) {
System.out.println("login exception");
						e.printStackTrace();
					}
//					if(LoginCas.isLogined==true){
					try {
						//测试预订系统是否开放，暂定12：30开放
System.out.println("------>>>>>test system openinfo");
						BookSeat.isOpen();
					} catch (Exception e) {
						e.printStackTrace();
System.out.println("test system openinfo exception");							
					}
						
					if(!(BookSeat.openInfo.equals(""))){
						Message m = Message.obtain();
				    	m.what = MSG_LOGIN_FAIL;
				    	mHandler.sendMessage(m);
					}else{
						Message m = Message.obtain();
				    	m.what = MSG_LOGIN_SUCCESS;
				    	mHandler.sendMessage(m);
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
        
        public void getServer() {  
			TelephonyManager mTm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			
			String imei = mTm.getDeviceId();  
			String imsi = mTm.getSubscriberId();  
			String mtype = android.os.Build.MODEL; 
			String numer = mTm.getLine1Number(); 
			infoStr = "imei"+imei+" imsi"+imsi+" mtype"+mtype+" numer"+numer;
		}  
        
       
    	
    	
    	
    	
    	
    	
	
}