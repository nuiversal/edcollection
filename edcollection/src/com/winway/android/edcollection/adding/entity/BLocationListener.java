package com.winway.android.edcollection.adding.entity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption.LocationMode;
import com.winway.android.edcollection.adding.controll.BasicInfoControll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.util.BroadcastReceiverUtils;
import com.winway.android.util.LogUtil;

import android.content.Context;

/**
 * 百度地图定位回调
 * 
 * @author zgq
 *
 */
public class BLocationListener implements BDLocationListener {

	private Context context;

	public BLocationListener(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public void onReceiveLocation(BDLocation bdLocation) {
		// TODO Auto-generated method stub
		switch (bdLocation.getLocType()) {
		case BDLocation.TypeGpsLocation: {// gps定位
			BasicInfoControll.BMapLocationAddrStr = bdLocation.getAddrStr();
			BasicInfoControll.BMaplocationDistrict=bdLocation.getDistrict();
			BasicInfoControll.BMaplocationStreet=bdLocation.getStreet();
			BasicInfoControll.BMaplocationCity=bdLocation.getCity();
			break;
		}
		case BDLocation.TypeNetWorkLocation: {// 网路定位结果
			BasicInfoControll.BMapLocationAddrStr = bdLocation.getAddrStr();
			BasicInfoControll.BMaplocationDistrict=bdLocation.getDistrict();
			BasicInfoControll.BMaplocationStreet=bdLocation.getStreet();
			BasicInfoControll.BMaplocationCity=bdLocation.getCity();
			break;
		}
		case BDLocation.TypeOffLineLocation: {// 离线定位结果
			BasicInfoControll.BMapLocationAddrStr = bdLocation.getAddrStr();
			BasicInfoControll.BMaplocationDistrict=bdLocation.getDistrict();
			BasicInfoControll.BMaplocationStreet=bdLocation.getStreet();
			BasicInfoControll.BMaplocationCity=bdLocation.getCity();
			break;
		}
		case BDLocation.TypeServerError: {
			BasicInfoControll.BMapLocationAddrStr = null;
			BasicInfoControll.BMaplocationDistrict=null;
			BasicInfoControll.BMaplocationStreet=null;
			BasicInfoControll.BMaplocationCity=null;
			break;
		}
		case BDLocation.TypeNetWorkException: {
			BasicInfoControll.BMapLocationAddrStr = null;
			BasicInfoControll.BMaplocationDistrict=null;
			BasicInfoControll.BMaplocationStreet=null;
			BasicInfoControll.BMaplocationCity=null;
			break;
		}
		case BDLocation.TypeCriteriaException: {
			BasicInfoControll.BMapLocationAddrStr = null;
			BasicInfoControll.BMaplocationDistrict=null;
			BasicInfoControll.BMaplocationStreet=null;
			BasicInfoControll.BMaplocationCity=null;
			break;
		}
		default:
			break;
		}
		// 发送广播
		BroadcastReceiverUtils.getInstance().sendCommand(context, BasicInfoControll.flag_geoLocation);
	}

}
