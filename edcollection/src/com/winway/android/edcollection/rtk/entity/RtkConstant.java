package com.winway.android.edcollection.rtk.entity;

/**
 * rtk操作相关的常量
 * 
 * @author zgq
 *
 */
public class RtkConstant {
	/**
	 * 设备查找请求码
	 */
	public static final int requestCode_discoveryDevice = 100;
	/** 关闭com1所有数据 */
	public static final String closeCom1 = "02 00 64 0D 00 00 00 03 00 01 00 07 04 FF 00 00 00 7F 03";
	/** 请求GGA数据 */
	public static final String gga = "02 00 64 0D 00 00 00 03 00 01 00 07 04 06 00 03 00 89 03";
	public static final int get_coords = 1;// 获取坐标

	/**
	 * rtk打开
	 */
	public static final String flag_rtk_on = "flag_rtk_on";

	/**
	 * rtk关闭
	 */
	public static final String flag_rtk_off = "flag_rtk_off";
	
	public static final int LINK_BREAK_OFF = -1;
}
