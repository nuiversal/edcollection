package com.winway.android.edcollection.adding.util;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.rtk.entity.RtkLocationInfo;
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
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;

public class RtkInfoDialog extends Dialog {
	private Context context;
	private InputComponent icLongitude;// 经度
	private InputComponent iclatitude; // 纬度
	private InputComponent icAltitude; // 高程
	private InputComponent icGpsState; // 定位状态
	private InputComponent icSatellites;// 卫星数量
	private InputComponent icHdop; // 水平精度因子
	private Button btnOk;
	private String longitude = null;
	private String latitude = null;
	private String altitude = null;
	private String gpsStatr = null;
	private String satellitse = null;
	private String hdop = null;
	private String[] gpsStatteArr;
	private ConfirmCallBack confirmCallBack = null;

	public RtkInfoDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		window.setGravity(Gravity.CENTER);
		window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);// 设置为系统级别的dialog,可以再任意activity显示
		setContentView(R.layout.dialog_rtk_info);
		WindowManager windowManager = ((Activity) context).getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		// lp.width = DeviceUtils.getScreenWidth(context)/2;
		lp.width = display.getWidth() * 4 / 5; // 设置dialog宽度为屏幕的4/5
		int screenHeight = DeviceUtils.getScreenHeight(context);
		lp.height = LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(lp);
		this.setCancelable(true);
		// 0初始化， 1单点定位， 2码差分， 3无效PPS， 4固定解， 5浮点解， 6正在估算 7，人工输入固定值， 8模拟模式，
		// 9WAAS差分
		gpsStatteArr = new String[] { "初始化", "单点定位", "码差分", "无效PPS", "固定解", "浮点解", "正在估算", "人工输入固定值", "模拟模式",
				"WAAS差分 " };
		initView();
		setData();
		initEvent();
	}

	private void initEvent() {
		btnOk.setOnClickListener(ocl);
	}

	public void setData() {
		if (GlobalEntry.rtkLocationInfo.getLon() != null) {
			icLongitude.setEdtText(GlobalEntry.rtkLocationInfo.getLon() + "");
		}
		if (GlobalEntry.rtkLocationInfo.getLat() != null) {
			iclatitude.setEdtText(GlobalEntry.rtkLocationInfo.getLat() + "");
		}
		if (GlobalEntry.rtkLocationInfo.getAltitude() != null) {
			icAltitude.setEdtText(GlobalEntry.rtkLocationInfo.getAltitude() + "");
		}
		if (GlobalEntry.rtkLocationInfo.getGpsState() != null) {
			String gpsStatr = gpsStatteArr[GlobalEntry.rtkLocationInfo.getGpsState()];
			icGpsState.setEdtText(gpsStatr);
		}
		if (GlobalEntry.rtkLocationInfo.getSatellites() != null) {
			icSatellites.setEdtText(GlobalEntry.rtkLocationInfo.getSatellites() + "");
		}
		if (GlobalEntry.rtkLocationInfo.getHdop() != null) {
			icHdop.setEdtText(GlobalEntry.rtkLocationInfo.getHdop() + "");
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		icLongitude = (InputComponent) findViewById(R.id.inCon_rtk_longitude);
		iclatitude = (InputComponent) findViewById(R.id.inCon_rtk_latitude);
		icAltitude = (InputComponent) findViewById(R.id.inCon_rtk_altitude);
		icGpsState = (InputComponent) findViewById(R.id.inCon_rtk_gpsState);
		icSatellites = (InputComponent) findViewById(R.id.inCon_rtk_satellites);
		icHdop = (InputComponent) findViewById(R.id.inCon_rtk_hdop);
		btnOk = (Button) findViewById(R.id.btn_rkt_ok);
		icLongitude.getEditTextView().setEnabled(false);
		iclatitude.getEditTextView().setEnabled(false);
		icAltitude.getEditTextView().setEnabled(false);
		icGpsState.getEditTextView().setEnabled(false);
		icSatellites.getEditTextView().setEnabled(false);
		icHdop.getEditTextView().setEnabled(false);
	}

	private View.OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			confirmCallBack.onClick();
		}
	};

	/**
	 * 确定按钮回调
	 * 
	 * @author administrator
	 *
	 */
	public interface ConfirmCallBack {
		void onClick();
	}

	public ConfirmCallBack getConfirmCallBack() {
		return confirmCallBack;
	}

	public void setConfirmCallBack(ConfirmCallBack confirmCallBack) {
		this.confirmCallBack = confirmCallBack;
	}

}
