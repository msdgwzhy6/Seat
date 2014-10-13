package com.oxygen.activity;

import com.oxygen.seat.BookSeat;
import com.oxygen.seat.LoginCas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

//老图四楼1401阅览
/*
101-113

201-213

301-313

401-413


13*4=52

52*6=312
*/
public class TableOld1401Activity extends Activity implements View.OnClickListener{

	private Button btn[];
	private byte flag = 0;
	private int click ;
	private int MSG_SEACH_SUCCESS = 1;
	private ProgressDialog dialog;
	private Thread td;
	
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tableold1401);

		btn = new Button[52];
		td = new Thread(mThread);

		btn[0] = (Button)findViewById(R.id.btn_101);
		btn[1] = (Button)findViewById(R.id.btn_102);
		btn[2] = (Button)findViewById(R.id.btn_103);
		btn[3] = (Button)findViewById(R.id.btn_104);
		btn[4] = (Button)findViewById(R.id.btn_105);
		btn[5] = (Button)findViewById(R.id.btn_106);
		btn[6] = (Button)findViewById(R.id.btn_107);
		btn[7] = (Button)findViewById(R.id.btn_108);
		btn[8] = (Button)findViewById(R.id.btn_109);
		btn[9] = (Button)findViewById(R.id.btn_110);
		btn[10] = (Button)findViewById(R.id.btn_111);
		btn[11] = (Button)findViewById(R.id.btn_112);
		btn[12] = (Button)findViewById(R.id.btn_113);
		btn[13] = (Button)findViewById(R.id.btn_201);
		btn[14] = (Button)findViewById(R.id.btn_202);
		btn[15] = (Button)findViewById(R.id.btn_203);
		btn[16] = (Button)findViewById(R.id.btn_204);
		btn[17] = (Button)findViewById(R.id.btn_205);
		btn[18] = (Button)findViewById(R.id.btn_206);
		btn[19] = (Button)findViewById(R.id.btn_207);
		btn[20] = (Button)findViewById(R.id.btn_208);
		btn[21] = (Button)findViewById(R.id.btn_209);
		btn[22] = (Button)findViewById(R.id.btn_210);
		btn[23] = (Button)findViewById(R.id.btn_211);
		btn[24] = (Button)findViewById(R.id.btn_212);
		btn[25] = (Button)findViewById(R.id.btn_213);
		btn[26] = (Button)findViewById(R.id.btn_301);
		btn[27] = (Button)findViewById(R.id.btn_302);
		btn[28] = (Button)findViewById(R.id.btn_303);
		btn[29] = (Button)findViewById(R.id.btn_304);
		btn[30] = (Button)findViewById(R.id.btn_305);
		btn[31] = (Button)findViewById(R.id.btn_306);
		btn[32] = (Button)findViewById(R.id.btn_307);
		btn[33] = (Button)findViewById(R.id.btn_308);
		btn[34] = (Button)findViewById(R.id.btn_309);
		btn[35] = (Button)findViewById(R.id.btn_310);
		btn[36] = (Button)findViewById(R.id.btn_311);
		btn[37] = (Button)findViewById(R.id.btn_312);
		btn[38] = (Button)findViewById(R.id.btn_313);
		btn[39] = (Button)findViewById(R.id.btn_401);
		btn[40] = (Button)findViewById(R.id.btn_402);
		btn[41] = (Button)findViewById(R.id.btn_403);
		btn[42] = (Button)findViewById(R.id.btn_404);
		btn[43] = (Button)findViewById(R.id.btn_405);
		btn[44] = (Button)findViewById(R.id.btn_406);
		btn[45] = (Button)findViewById(R.id.btn_407);
		btn[46] = (Button)findViewById(R.id.btn_408);
		btn[47] = (Button)findViewById(R.id.btn_409);
		btn[48] = (Button)findViewById(R.id.btn_410);
		btn[49] = (Button)findViewById(R.id.btn_411);
		btn[50] = (Button)findViewById(R.id.btn_412);
		btn[51] = (Button)findViewById(R.id.btn_413);

		for(int i=0;i<52;i++){
			int tag = Integer.parseInt((String)(btn[i].getText()));
			btn[i].setTag(tag);
			if(BookSeat.libId.equals("0")==false){
				for(int j=0;j<6;j++){
					if(LoginCas.seat[i*6+j]==1){
						flag++;
					}
				}		
				if(flag==BookSeat.FULL){
					btn[i].setText(btn[i].getText()+"满");//桌子部位空就标记
					btn[i].setTextColor(Color.RED);
					btn[i].setTextSize(25);
				}else if(flag>=1&&flag<=5){
					btn[i].setText(btn[i].getText()+"-"+flag);//桌子部位空就标记
					btn[i].setTextColor(Color.rgb(84,139,84));
					btn[i].setTextSize(25);
				}
				flag=0;
			}
			btn[i].setOnClickListener(this);
		}
	}


	public void onClick(View v){
		click = (Integer)v.getTag();
		BookSeat.tableId = String.valueOf(v.getTag());
		dialog = new ProgressDialog(TableOld1401Activity.this);
		dialog.setMessage("加载中...");
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.7f;
		lp.dimAmount = 0.8f;
		window.setAttributes(lp); 
		dialog.show();
		
		td.start();
		
	}

	//跳转Handler
	private Handler mHandler = new Handler(){
      	public void handleMessage(Message msg){	
      		if(msg.what==MSG_SEACH_SUCCESS){
      			//跳转界面
      			dialog.cancel();	
      			Intent intent = new Intent(TableOld1401Activity.this,SeatActivity.class);
      			TableOld1401Activity.this.startActivity(intent);
      			TableOld1401Activity.this.finish();
			}
      	}
    };
	
	//one座位线程
	private Runnable mThread = new Runnable(){
    	@Override
    	public void run(){
    		if(BookSeat.libId != "0"){
	System.out.println("seach ONE thread start--->>>>>>>>>");
				for(int i=0;i<52;i++){
					if(click==(Integer)btn[i].getTag()){
						for(int j=0;j<6;j++){
							LoginCas.one[j]=LoginCas.seat[i*6+j];
						}
						break;
					}
				}
				Message m = Message.obtain();
				m.what = MSG_SEACH_SUCCESS;
				mHandler.sendMessage(m);
    		}else{
    			Message m = Message.obtain();
				m.what = MSG_SEACH_SUCCESS;
				mHandler.sendMessage(m);
    		}
    	}
    };

	//重写返回键，返回上一个LibraryActivity
	public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent backIntent = new Intent();
            backIntent = new Intent(TableOld1401Activity.this, RoomOldActivity.class);
            startActivity(backIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
