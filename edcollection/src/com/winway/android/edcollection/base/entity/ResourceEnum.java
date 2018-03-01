package com.winway.android.edcollection.base.entity;

import com.winway.android.sensor.R.string;

/**
 * 统一资源分类枚举
 * 
 * @author Administrator
 *
 */
public enum ResourceEnum {
	XL("90101", "线路"), // 线路
	BDZ("10101", "变电站"), // 变电站
	KGZ("10102", "开关站"), // 开关站
	PDS("10103", "配电室"), // 配电室
	HWG("10104", "环网柜"), // 环网柜
	XSBDZ("10105", "箱式变电站"), // 箱式变电站
	DLFZX("10106", "电缆分支箱"), // 电缆分支箱
	DYDLFZX("10107", "低压电缆分支箱"), // 低压电缆分支箱
	DYPDX("10108", "低压配电箱"), // 低压配电箱
	BYQ("10109", "变压器"), // 变压器
	PG("20101", "排管"), // 排管
	MG("20101", "埋管"), // 埋管
	GD("20102", "沟道"), // 沟道
	SD("20103", "隧道"), // 隧道
	QJ("20104", "桥架"), // 桥架
	ZM("20105", "直埋"), // 直埋
	JKXL("20110", "架空"), // 架空线路
	TLG("20106", "拖拉管"), // 拖拉管
	DG("20106", "顶管"), // 顶管
	DLC("20112", "电缆槽"), // 电缆槽
	DLJ("20107", "电缆井"), // 电缆井（工井）
	JG("20108", "井盖"), // 井盖
	GT("20109", "杆塔"), // 杆塔
	ZJJT("30101", "中间接头"), // 中间接头
	DZBZQ("40101", "电子标识器"), // 电子标识器
	DZBQ("40102", "电子标签"), // 电子标签
	AJH("40103", "安健环"), // 安健环
	TDBZ("40104", "通道标志"), // 通道标志
	BZP("40105", "标识牌"), // 标识牌
	TD("20100", "通道"), // 通道
	TDJM("20111", "通道截面"), // 通道截面
	EM_TASKS("99010", "任务"), // 任务
	EM_TASK_ITEM("99011", "任务条目");// 任务条目

	private final String value;
	private final String name;

	ResourceEnum(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	/**
	 * 根据名称获取值
	 * 
	 * @param name
	 * @return
	 */
	public static String getValueByName(String name) {
		String result = null;
		for (ResourceEnum resourceEnum : ResourceEnum.values()) {
			if (resourceEnum.getName().equals(name)) {
				result = resourceEnum.getValue();
				return result;
			}
		}
		return result;
	}
}
