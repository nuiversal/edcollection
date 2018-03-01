package com.winway.android.edcollection.adding.util;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.DeviceReadQueryActivity;
import com.winway.android.edcollection.adding.controll.NewFragmentControll;
import com.winway.android.edcollection.main.controll.MainControll;
import com.winway.android.sensor.marker.MarkerReadActivity;
import com.winway.android.util.AndroidBasicComponentUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class NewDeviceReadTool {
	private Activity mActivity;
	private static NewDeviceReadTool instance;
	private boolean isOpen = false;// 是否打开
	private PopupWindow popupWindowMarkWay = null;
	private View deviceReadView;// 标识器状态view
	private ImageButton DeviceReadToolEnter;// 标识器进入按钮

	private NewDeviceReadTool(Activity activity) {
		this.mActivity = activity;
	}

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static NewDeviceReadTool getInstance(Activity mActivity) {
		if (instance == null) {
			synchronized (NewDeviceReadTool.class) {
				if (instance == null) {
					instance = new NewDeviceReadTool(mActivity);
				}
			}
		}
		return instance;
	}

	/**
	 * 选择标识器类型
	 * 
	 * @param context
	 * @param imageButton
	 */
	public void markShowTool(Context context, ImageButton imageButton) {
		if (popupWindowMarkWay == null) {
			popupWindowMarkWay = new PopupWindow();
			View contentView = View.inflate(context, R.layout.device_read_tool, null);
			deviceReadView = contentView;
			ImageButton btnbsq = (ImageButton) contentView.findViewById(R.id.imgBtn_deviceRead__bsq);
			ImageButton btnbq = (ImageButton) contentView.findViewById(R.id.imgBtn_deviceRead_bq);
			btnbsq.setOnClickListener(orcl);
			btnbq.setOnClickListener(orcl);
			popupWindowMarkWay.setContentView(contentView);
			popupWindowMarkWay.setWidth(LayoutParams.WRAP_CONTENT);
			popupWindowMarkWay.setHeight(LayoutParams.WRAP_CONTENT);
			popupWindowMarkWay.setFocusable(false);
			popupWindowMarkWay.setOutsideTouchable(true);
			int width = imageButton.getMeasuredWidth();
			int height = imageButton.getMeasuredHeight();
			popupWindowMarkWay.showAsDropDown(imageButton, -(width + 5), -height);
		} else {
			closePopWid();
		}

	}

	// 点击事件
	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imgBtn_deviceRead__bsq:// 标识器
				closePopWid();
				// 业务逻辑代码
				AndroidBasicComponentUtils.launchActivityForResult(mActivity, MarkerReadActivity.class,
						NewFragmentControll.REQUEST_CODE_MARKER_READ);
				break;
			case R.id.imgBtn_deviceRead_bq:// 标签
				closePopWid();
				// 业务逻辑代码
				Intent intent = new Intent(mActivity, DeviceReadQueryActivity.class);
				mActivity.startActivityForResult(intent, MainControll.requestCode_devicequery);
				break;
			}
		}
	};

	// 关闭PopupWindow
	public void closePopWid() {
		if (popupWindowMarkWay != null) {
			popupWindowMarkWay.dismiss();
			popupWindowMarkWay = null;
		}
	}

	/**
	 * 是否显示了Popupwindow
	 * 
	 * @return
	 */
	public boolean isShowPopWid() {
		return popupWindowMarkWay.isShowing();
	}

}
