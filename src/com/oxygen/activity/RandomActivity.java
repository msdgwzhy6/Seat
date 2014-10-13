package com.oxygen.activity;

import java.util.Random;

import com.oxygen.seat.BookSeat;
import com.oxygen.seat.LoginCas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//随机预定
public class RandomActivity extends Activity implements SensorEventListener{
	
	private Button btn_random;
	private TextView tv_random;
	public int seatid;
	SensorManager sensorManager = null;//传感器管理
	Vibrator vibrator = null;//震动器
	private int MSG_SHAKE_OK = 1;
	private int MSG_SEAT_OVER = 0;
	private ProgressDialog dialog;
	private Thread td;
	
	private static long lastShakeTime;
	
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.random);
		
		 btn_random = (Button)findViewById(R.id.btn_random);
		 tv_random = (TextView)findViewById(R.id.tv_random);
		
		
		 
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		
		btn_random.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_random.setText("");
				dialog = new ProgressDialog(RandomActivity.this);
				dialog.setMessage("正在为您分配座位...");
				Window window = dialog.getWindow();
				WindowManager.LayoutParams lp = window.getAttributes();
				lp.alpha = 0.7f;
				lp.dimAmount = 0.8f;
				window.setAttributes(lp); 
				dialog.show();
				
				Thread btn_td = new Thread(mThread); 
				btn_td.start();
				
			}
		});
		
		
	}
	
	
	private Handler mHandler = new Handler(){
      	public void handleMessage(Message msg){
System.out.println("--------shake book  msg ");
				
      		if(msg.what==MSG_SHAKE_OK){
      			//判断摇一摇成功TODO
      			dialog.dismiss();
      			dialog.cancel();
				//Toast.makeText(RandomActivity.this,"分配成功", Toast.LENGTH_SHORT).show();
				//等待800毫秒
      			try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
      		//跳转界面
				Intent intent = new Intent(RandomActivity.this,OverActivity.class);
				RandomActivity.this.startActivity(intent);
				RandomActivity.this.finish();
			}else if(msg.what==MSG_SEAT_OVER){
				//等待800毫秒
      			try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tv_random.setText("无剩余座位");
				dialog.dismiss();
				dialog.cancel();
				
				Toast.makeText(RandomActivity.this,"很遗憾,今天的座位已被预订完了",Toast.LENGTH_LONG).show();
			}else{
				dialog.dismiss();
				dialog.cancel();
				Toast.makeText(RandomActivity.this,"读取随机数据失败\n再试一次",Toast.LENGTH_LONG).show();
//				Intent intent = new Intent(RandomActivity.this,LoginActivity.class);
//				RandomActivity.this.startActivity(intent);
			}
      	}
    };
	
	Runnable mThread = new Runnable() {
		public void run() {
			synchronized(this){//防止传感器和按钮争夺资源
				try {
					BookSeat.random();
	System.out.println("------------->>>>----BookSeat.random();已启动");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(LoginCas.randomID.size() != 0){
					int max = LoginCas.randomID.size();
					Random rand = new Random();
					seatid=(Integer)LoginCas.randomID.get(rand.nextInt(max));
					BookSeat.tableId=String.valueOf(seatid/10);
					BookSeat.seatId=String.valueOf(seatid%10);
	System.out.println("------------->>>>----random:"+seatid);
	System.out.println("------------->>>>----random-->>book:"+seatid);
					try {
						BookSeat.book();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
	System.out.println("-------->>>>----random book fail");
					}
					Message m = Message.obtain();
					m.what = MSG_SHAKE_OK;
					mHandler.sendMessage(m);
				}else{
					Message m = Message.obtain();
					m.what = MSG_SEAT_OVER;
					mHandler.sendMessage(m);
				}
			}
		
			
		}
	};

	
	@Override
	protected void onPause()
	{
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	// RandomActivity implement the inherited abstract method SensorEventListener.onSensorChanged(SensorEvent)
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		//当传感器精度改变时回调该方法，Do nothing.
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		int sensorType = event.sensor.getType();
		//values[0]:X轴，values[1]：Y轴，values[2]：Z轴
		float[] values = event.values;
		if (sensorType == Sensor.TYPE_ACCELEROMETER){
			if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math.abs(values[2]) > 17)){
//				Log.d("sensor x ", "============ values[0] = " + values[0]);
//				Log.d("sensor y ", "============ values[1] = " + values[1]);
//				Log.d("sensor z ", "============ values[2] = " + values[2]);
//				tv.setText("摇一摇成功!!!");
				
				//do something
					
//System.out.println(isFastDoubleClick());
					
					if(isFastDoubleClick()==false){
						tv_random.setText("");
						dialog = new ProgressDialog(RandomActivity.this);
						dialog.setMessage("正在为您分配座位...");
						Window window = dialog.getWindow();
						WindowManager.LayoutParams lp = window.getAttributes();
						lp.alpha = 0.7f;
						lp.dimAmount = 0.8f;
						window.setAttributes(lp); 
						dialog.show();
						System.out.println("----->>>>random thread start");
						td =new Thread(mThread);
						td.start();	
						//摇动手机后，再伴随震动提示~~
						vibrator.vibrate(500);	
					}


				}
					
					
				
				
			}

		}
		

	
	//重写返回键，返回上一个TableXXXXXXXActivity

    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	Thread td =new Thread(bThread);
		td.start();
    	//super.onBackPressed();
    	return ;
    	}
	
	Runnable bThread = new Runnable() {
	  	  public void run() {	  		  
	  		Intent backIntent = new Intent();
  			backIntent = new Intent(RandomActivity.this, SelectActivity.class);
  			startActivity(backIntent);
  			RandomActivity.this.finish();
	  	  }    
	  	    };
	
	  	    //防止防止控件被重复摇一摇或点击
	  	  public static boolean isFastDoubleClick() {
	          long time = System.currentTimeMillis();//获取自1970年以后的毫秒数。long类型
	          long timeD = time - lastShakeTime;
System.out.println("timeD--->>>>"+timeD);
System.out.println("timeD--->>>>"+lastShakeTime);
	          if ( 0 < timeD && timeD < 2000) {
	        	  lastShakeTime = time;
	        	  return true;
	          }
	          lastShakeTime = time;
	          return false;
	  	  }
	
}
