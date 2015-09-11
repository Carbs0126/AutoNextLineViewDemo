package com.carbs.view;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

	private AutoNextLineViewGroup mAutoGroup;

	private ArrayList<View> mViews;
	private String[] mStrings;

	private Button mResizeMarginButton;
	private Button mRequestLayoutButton;
	private static final int HANDLER_UI_REQUEST_LAYOUT = 1;
	private static final int VIEW_HEIGHT_PX = 80;

	private int mCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mAutoGroup = (AutoNextLineViewGroup) findViewById(R.id.auto_group);

		mResizeMarginButton = (Button) findViewById(R.id.button1);
		mResizeMarginButton.setOnClickListener(this);

		mRequestLayoutButton = (Button) findViewById(R.id.button2);
		mRequestLayoutButton.setOnClickListener(this);
		
		mStrings = this.getResources().getStringArray(R.array.poem);

		mViews = getViewList();

		//open next codes to test oncreate() adds view function
//		for (View v : mViews) {
//			mAutoGroup.addView(v);
//		}

	}

	public ArrayList<View> getViewList() {

		ArrayList<View> list = new ArrayList();

		int size = mStrings.length;
		for (int i = 0; i < size; i++) {
			TextView view = new TextView(MainActivity.this);

			view.setTextColor(Color.WHITE);
			view.setPadding(20, 5, 20, 5);
			view.setBackgroundColor(0x55555555);
			view.setText(mStrings[i]);
			view.setHeight(VIEW_HEIGHT_PX);
			view.setGravity(Gravity.CENTER);
			
			list.add(view);

		}

		return list;
	}

	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			
			case HANDLER_UI_REQUEST_LAYOUT:
				mAutoGroup.requestLayout();
				break;
			}
		}
	};
	
	ImageButton ib;
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			//test resize function 
//			mAutoGroup.setMarginHorizontal(2);
//			mAutoGroup.setMarginVertical(4);
			
//			TextView view = new TextView(MainActivity.this);
//			view.setTextColor(Color.WHITE);
//			view.setPadding(20, 5, 20, 5);
//			view.setBackgroundColor(0x55555555);
//			view.setText("NewView");
//			view.setHeight(80);
//			view.setGravity(Gravity.CENTER);
//			mAutoGroup.addView(view, mAutoGroup.getChildCount()-1);

			
//			ImageButton imageButton1=new ImageButton(MainActivity.this);
//			ViewGroup.LayoutParams layoutParamsButton = new ViewGroup.LayoutParams(160, 80);
//			imageButton1.setLayoutParams(layoutParamsButton);
//			imageButton1.setBackgroundResource(R.drawable.button_gray_hollow);
//			imageButton1.setImageResource(R.drawable.widget_add_gray_icon);
//			imageButton1.setScaleType(ScaleType.FIT_CENTER);
//			imageButton1.setAdjustViewBounds(true);
//			imageButton1.setMinimumHeight(VIEW_HEIGHT_PX);
//			imageButton1.setMaxHeight(VIEW_HEIGHT_PX);
//			mAutoGroup.addView(imageButton1);
			if(mCount < mViews.size()){
				mAutoGroup.addView(mViews.get(mCount));
				mCount++;
			}
			mHandler.sendEmptyMessage(HANDLER_UI_REQUEST_LAYOUT);
			break;
			
		case R.id.button2:
			
			ImageButton imageButton1=new ImageButton(MainActivity.this);
			ViewGroup.LayoutParams layoutParamsButton = new ViewGroup.LayoutParams(160, 80);
			imageButton1.setLayoutParams(layoutParamsButton);
			imageButton1.setBackgroundResource(R.drawable.button_gray_hollow);
			imageButton1.setImageResource(R.drawable.widget_add_gray_icon);
			imageButton1.setScaleType(ScaleType.FIT_CENTER);
			imageButton1.setAdjustViewBounds(true);
			imageButton1.setMinimumHeight(VIEW_HEIGHT_PX);
			imageButton1.setMaxHeight(VIEW_HEIGHT_PX);
			imageButton1.setTag(new Object());
			imageButton1.setOnClickListener(this);
			mAutoGroup.addView(imageButton1);
			mHandler.sendEmptyMessage(HANDLER_UI_REQUEST_LAYOUT);
			break;
		default:
			if(v.getTag() != null){
				Toast.makeText(MainActivity.this, "imagebutton onclick", Toast.LENGTH_LONG).show();
			}
			break;
		}
	}
}