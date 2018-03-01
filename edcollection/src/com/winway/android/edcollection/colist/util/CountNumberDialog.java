package com.winway.android.edcollection.colist.util;

import com.winway.android.edcollection.R;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.util.DeviceUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
/**
 * 统计已采列表对话框
 * @author winway_cmx
 *
 */
public class CountNumberDialog extends Dialog{
	private Context context;
	private TextView tv_type;
	private TextView tv_number;
	private TextView tv_genre;
	private String type=null;
	private String number=null;
	private String mbCount=null;//标识球数量
	private String mnCount=null;//标识钉数量
	private String vpCount=null;//虚拟点数量
	private String towerCount=null;//杆塔数量
	private String ajhCount=null;//安键环数量
	private String dzbqCount=null;//电子标签数量
	private TextView tv_markBall;
	private TextView tv_markNail;
	private TextView tv_virtual_point;
	private TextView tv_tower;
	private TextView tv_ajh;
	private TextView tv_dzbq;
	private RelativeLayout rl_makeBall;
	private RelativeLayout rl_markNail;
	private RelativeLayout rl_virtyalPoint;
	private RelativeLayout rl_tower;
	private RelativeLayout rl_ajh;
	private RelativeLayout rl_dzbq;
	
	
	public CountNumberDialog(Context context, int theme) {
		super(context, theme);
		this.context=context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		window.setGravity(Gravity.CENTER);
		window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);// 设置为系统级别的dialog,可以再任意activity显示
		setContentView(R.layout.dialog_colist_count);
		WindowManager windowManager = ((Activity) context).getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = DeviceUtils.getScreenWidth(context)/2;
		//lp.width = display.getWidth() * 4 / 5; // 设置dialog宽度为屏幕的4/5
		int screenHeight = DeviceUtils.getScreenHeight(context);
		lp.height = LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(lp);
		this.setCancelable(true);
		initView();
		initData();
		initEvent();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 这个方法即是初始化方法，也是刷新数据方法
	 */
	public void initData() {
		// TODO Auto-generated method stub
		if(type!=null){
			tv_type.setText(type);
			tv_genre.setText(type);
		}
		if(number!=null){
			tv_number.setText(number);
		}
		if(mbCount==null){
			tv_markBall.setText(0+"");
		}else {
			tv_markBall.setText(mbCount);
		}
		if(mnCount==null){
			tv_markNail.setText(0+"");
		}else {
			tv_markNail.setText(mnCount);
		}
		if(vpCount==null){
			tv_virtual_point.setText(0+"");
		}else {
			tv_virtual_point.setText(vpCount);
		}
		if(towerCount==null){
			tv_tower.setText(0+"");
		}else {
			tv_tower.setText(towerCount);
		}
		if(ajhCount==null){
			tv_ajh.setText(0+"");
		}else {
			tv_ajh.setText(ajhCount);
		}
		if(dzbqCount==null){
			tv_dzbq.setText(0+"");
		}else {
			tv_dzbq.setText(dzbqCount);
		}
	}
	/**
	 * 重置对话框数据
	 */
	public void reset(){
		type=null;
		number=null;
		mbCount=null;
		mnCount=null;
		vpCount=null;
		towerCount=null;
		ajhCount=null;
		dzbqCount=null;
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		rl_makeBall=(RelativeLayout)findViewById(R.id.rl_makeBall);
		rl_markNail=(RelativeLayout)findViewById(R.id.rl_markNail);
		rl_virtyalPoint=(RelativeLayout)findViewById(R.id.rl_virtyalPoint);
		rl_tower=(RelativeLayout)findViewById(R.id.rl_tower);
		rl_ajh=(RelativeLayout)findViewById(R.id.rl_ajh);
		rl_dzbq=(RelativeLayout) findViewById(R.id.rl_dzbq);
		
		tv_type=(TextView)findViewById(R.id.tv_type);
		tv_number=(TextView)findViewById(R.id.tv_number);
		tv_genre=(TextView)findViewById(R.id.tv_genre);
		tv_markBall=(TextView)findViewById(R.id.tv_markBallNumber);
		tv_markNail=(TextView)findViewById(R.id.tv_markNailNumber);
		tv_virtual_point=(TextView)findViewById(R.id.tv_virtualPointNumber);
		tv_tower=(TextView)findViewById(R.id.tv_towerNumber);
		tv_ajh=(TextView)findViewById(R.id.tv_ajhNumber);
		tv_dzbq=(TextView) findViewById(R.id.tv_dzbqNumber);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMbCount() {
		return mbCount;
	}

	public void setMbCount(String mbCount) {
		this.mbCount = mbCount;
	}

	public String getMnCount() {
		return mnCount;
	}

	public void setMnCount(String mnCount) {
		this.mnCount = mnCount;
	}

	public String getVpCount() {
		return vpCount;
	}

	public void setVpCount(String vpCount) {
		this.vpCount = vpCount;
	}

	public String getTowerCount() {
		return towerCount;
	}

	public void setTowerCount(String towerCount) {
		this.towerCount = towerCount;
	}
	
	public String getAjhCount() {
		return ajhCount;
	}

	public void setAjhCount(String ajhCount) {
		this.ajhCount = ajhCount;
	}

	public String getDzbqCount() {
		return dzbqCount;
	}

	public void setDzbqCount(String dzbqCount) {
		this.dzbqCount = dzbqCount;
	}

	/**
	 * 显示路径点具体类型数量
	 */
	public void showPathpointType(){
		if(rl_makeBall.getVisibility()==View.GONE){
			rl_makeBall.setVisibility(View.VISIBLE);
		}
		if(rl_markNail.getVisibility()==View.GONE){
			rl_markNail.setVisibility(View.VISIBLE);
		}
		if(rl_virtyalPoint.getVisibility()==View.GONE){
			rl_virtyalPoint.setVisibility(View.VISIBLE);
		}
		if(rl_tower.getVisibility()==View.GONE){
			rl_tower.setVisibility(View.VISIBLE);
		}
		if(rl_ajh.getVisibility()==View.GONE){
			rl_ajh.setVisibility(View.VISIBLE);
		}
		if(rl_dzbq.getVisibility()==View.GONE){
			rl_dzbq.setVisibility(View.VISIBLE);
		}
	}		
	/**
	 * 隐藏路径点具体类型数量
	 */
	public void hidePathpointType(){
		
		rl_makeBall.setVisibility(View.GONE);
		rl_markNail.setVisibility(View.GONE);
		rl_virtyalPoint.setVisibility(View.GONE);
		rl_tower.setVisibility(View.GONE);
		rl_ajh.setVisibility(View.GONE);
		rl_dzbq.setVisibility(View.GONE);
	}
	
}
