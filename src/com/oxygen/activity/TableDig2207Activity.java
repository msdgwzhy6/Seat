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
import android.widget.Toast;

//数图二楼

/*
101-115  -103 -112

201-215  -203 -212

301-315

401-415

501-515


13+13+15*3=71

71*6=426
*/
public class TableDig2207Activity extends Activity implements View.OnClickListener{

	private Button btn[];
	private byte flag = 0;
	private int click ;
	private int MSG_SEACH_SUCCESS = 1;
	private ProgressDialog dialog;
	private Thread td;
	
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tabledig2207);

		btn = new Button[71];
		td = new Thread(mThread);

		btn[0] = (Button)findViewById(R.id.btn_101);
		btn[1] = (Button)findViewById(R.id.btn_102);

		btn[2] = (Button)findViewById(R.id.btn_104);
		btn[3] = (Button)findViewById(R.id.btn_105);
		btn[4] = (Button)findViewById(R.id.btn_106);
		btn[5] = (Button)findViewById(R.id.btn_107);
		btn[6] = (Button)findViewById(R.id.btn_108);
		btn[7] = (Button)findViewById(R.id.btn_109);
		btn[8] = (Button)findViewById(R.id.btn_110);
		btn[9] = (Button)findViewById(R.id.btn_111);

		btn[10] = (Button)findViewById(R.id.btn_113);
		btn[11] = (Button)findViewById(R.id.btn_114);
		btn[12] = (Button)findViewById(R.id.btn_115);

		btn[13] = (Button)findViewById(R.id.btn_201);
		btn[14] = (Button)findViewById(R.id.btn_202);

		btn[15] = (Button)findViewById(R.id.btn_204);
		btn[16] = (Button)findViewById(R.id.btn_205);
		btn[17] = (Button)findViewById(R.id.btn_206);
		btn[18] = (Button)findViewById(R.id.btn_207);
		btn[19] = (Button)findViewById(R.id.btn_208);
		btn[20] = (Button)findViewById(R.id.btn_209);
		btn[21] = (Button)findViewById(R.id.btn_210);
		btn[22] = (Button)findViewById(R.id.btn_211);

		btn[23] = (Button)findViewById(R.id.btn_213);
		btn[24] = (Button)findViewById(R.id.btn_214);
		btn[25] = (Button)findViewById(R.id.btn_215);

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
		btn[39] = (Button)findViewById(R.id.btn_314);
		btn[40] = (Button)findViewById(R.id.btn_315);

		btn[41] = (Button)findViewById(R.id.btn_401);
		btn[42] = (Button)findViewById(R.id.btn_402);
		btn[43] = (Button)findViewById(R.id.btn_403);
		btn[44] = (Button)findViewById(R.id.btn_404);
		btn[45] = (Button)findViewById(R.id.btn_405);
		btn[46] = (Button)findViewById(R.id.btn_406);
		btn[47] = (Button)findViewById(R.id.btn_407);
		btn[48] = (Button)findViewById(R.id.btn_408);
		btn[49] = (Button)findViewById(R.id.btn_409);
		btn[50] = (Button)findViewById(R.id.btn_410);
		btn[51] = (Button)findViewById(R.id.btn_411);
		btn[52] = (Button)findViewById(R.id.btn_412);
		btn[53] = (Button)findViewById(R.id.btn_413);
		btn[54] = (Button)findViewById(R.id.btn_414);
		btn[55] = (Button)findViewById(R.id.btn_415);

		btn[56] = (Button)findViewById(R.id.btn_501);
		btn[57] = (Button)findViewById(R.id.btn_502);
		btn[58] = (Button)findViewById(R.id.btn_503);
		btn[59] = (Button)findViewById(R.id.btn_504);
		btn[60] = (Button)findViewById(R.id.btn_505);
		btn[61] = (Button)findViewById(R.id.btn_506);
		btn[62] = (Button)findViewById(R.id.btn_507);
		btn[63] = (Button)findViewById(R.id.btn_508);
		btn[64] = (Button)findViewById(R.id.btn_509);
		btn[65] = (Button)findViewById(R.id.btn_510);
		btn[66] = (Button)findViewById(R.id.btn_511);
		btn[67] = (Button)findViewById(R.id.btn_512);
		btn[68] = (Button)findViewById(R.id.btn_513);
		btn[69] = (Button)findViewById(R.id.btn_514);
		btn[70] = (Button)findViewById(R.id.btn_515);

		for(int i=0;i<71;i++){
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
		dialog = new ProgressDialog(TableDig2207Activity.this);
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
      			Intent intent = new Intent(TableDig2207Activity.this,SeatActivity.class);
      			TableDig2207Activity.this.startActivity(intent);
      			TableDig2207Activity.this.finish();
			}
      	}
    };
	
	//one座位线程
	private Runnable mThread = new Runnable(){
    	@Override
    	public void run(){
    		if(BookSeat.libId != "0"){
System.out.println("seach ONE thread start--->>>>>>>>>");
				for(int i=0;i<71;i++){
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
            backIntent = new Intent(TableDig2207Activity.this, RoomDigActivity.class);
            startActivity(backIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
