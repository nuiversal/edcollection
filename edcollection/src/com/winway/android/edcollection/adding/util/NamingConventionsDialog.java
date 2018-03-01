package com.winway.android.edcollection.adding.util;

import com.winway.android.edcollection.R;
import com.winway.android.ewidgets.input.InputComponentMultiline;
import com.winway.android.util.DeviceUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class NamingConventionsDialog extends Dialog{
	private Context context;
	private TextView tv_nameMethod;
	private TextView tv_namePrinciple;
	private TextView tv_nameConventions;
	private String nameMethod=null;
	private String namePrinciple=null;
	private String nameConventionsTitle=null;
	public NamingConventionsDialog(Context context, int theme) {
		super(context, theme);
		this.context=context;
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
		//window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);// 设置为系统级别的dialog,可以再任意activity显示
		setContentView(R.layout.layout_naming_conventions);
		WindowManager windowManager = ((Activity) context).getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = display.getWidth() * 4 / 5; // 设置dialog宽度为屏幕的4/5
		int screenHeight = DeviceUtils.getScreenHeight(context);
		lp.height = LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(lp);
		this.setCancelable(true);//设置点击dialog以外的区域  dialog消失
		initView();// 初始化控件
		initData();// 初始化数据
		initEvent();// 初始化事件
	}
	private void initEvent() {
		// TODO Auto-generated method stub
		
	}
	private void initData() {
		// TODO Auto-generated method stub
		if(nameMethod!=null){//命名方式
			tv_nameMethod.setText(nameMethod);
		}
		if(namePrinciple!=null){//命名原则
			tv_namePrinciple.setText(namePrinciple);
		}
		if(nameConventionsTitle!=null){//标题
			tv_nameConventions.setText(nameConventionsTitle);
		}
	}
	private void initView() {
		// TODO Auto-generated method stub
		tv_nameConventions=(TextView) findViewById(R.id.tv_nameConventions);
		tv_nameMethod=(TextView) findViewById(R.id.tv_nameMethod);
		tv_namePrinciple=(TextView)findViewById(R.id.tv_namePrinciple);
	}
	public String getNameMethod() {
		return nameMethod;
	}
	public void setNameMethod(String nameMethod) {
		this.nameMethod = nameMethod;
	}
	public String getNamePrinciple() {
		return namePrinciple;
	}
	public void setNamePrinciple(String namePrinciple) {
		this.namePrinciple = namePrinciple;
	}
	public String getNameConventionsTitle() {
		return nameConventionsTitle;
	}
	public void setNameConventionsTitle(String nameConventionsTitle) {
		this.nameConventionsTitle = nameConventionsTitle;
	}
	
	

}
