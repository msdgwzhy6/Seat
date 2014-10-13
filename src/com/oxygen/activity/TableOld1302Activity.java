package com.oxygen.activity;

import com.oxygen.seat.BookSeat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

//老图三楼教师
public class TableOld1302Activity extends Activity implements View.OnClickListener{

	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tableold1302);
	}
/*
		Button btn[] = new Button[24];

		btn[0] = (Button)findViewById(R.id.btn_101);
		btn[1] = (Button)findViewById(R.id.btn_102);
		btn[2] = (Button)findViewById(R.id.btn_103);
		btn[3] = (Button)findViewById(R.id.btn_104);
		btn[4] = (Button)findViewById(R.id.btn_105);
		btn[5] = (Button)findViewById(R.id.btn_106);
		btn[6] = (Button)findViewById(R.id.btn_107);
		btn[7] = (Button)findViewById(R.id.btn_201);
		btn[8] = (Button)findViewById(R.id.btn_202);
		btn[9] = (Button)findViewById(R.id.btn_203);
		btn[10] = (Button)findViewById(R.id.btn_204);
		btn[11] = (Button)findViewById(R.id.btn_205);
		btn[12] = (Button)findViewById(R.id.btn_206);
		btn[13] = (Button)findViewById(R.id.btn_207);
		btn[14] = (Button)findViewById(R.id.btn_301);
		btn[15] = (Button)findViewById(R.id.btn_302);
		btn[16] = (Button)findViewById(R.id.btn_303);
		btn[17] = (Button)findViewById(R.id.btn_304);
		btn[18] = (Button)findViewById(R.id.btn_305);
		btn[19] = (Button)findViewById(R.id.btn_306);
		btn[20] = (Button)findViewById(R.id.btn_307);
		btn[21] = (Button)findViewById(R.id.btn_401);
		btn[22] = (Button)findViewById(R.id.btn_402);
		btn[23] = (Button)findViewById(R.id.btn_403);
		

		for(int i=0;i<24;i++){
			int tag = Integer.parseInt((String)(btn[i].getText()));
			btn[i].setTag(tag);
			btn[i].setOnClickListener(this);
		}
	}

*/
	public void onClick(View v){
//		BookSeat.tableId = String.valueOf(v.getTag());
//		//跳转界面
//		Intent intent = new Intent(TableOld1302Activity.this,SeatActivity.class);
//		TableOld1302Activity.this.startActivity(intent);
	}

	//重写返回键，返回上一个LibraryActivity
	public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent backIntent = new Intent();
            backIntent = new Intent(TableOld1302Activity.this, RoomOldActivity.class);
            startActivity(backIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
