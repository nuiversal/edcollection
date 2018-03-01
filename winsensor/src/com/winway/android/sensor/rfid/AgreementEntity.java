package com.winway.android.sensor.rfid;

/**
 * 
 * 
 * @author mr-lao
 *
 */
public class AgreementEntity {

	public static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	/**
	 * RFID扫描
	 */
	public static final String TYPE_SCAN_START = "SCAN_START";
	/**
	 * RFID扫描回复
	 */
	public static final String TYPE_SCAN_REPLY = "SCAN_REPLY";
	/**
	 * 消息推送
	 */
	public static final String TYPE_REAL_DATA_PUSH = "REAL_DATA_PUSH";
	/**
	 * 获取24小时趋势图
	 */
	public static final String TYPE_TREND24_GET = "TREND24_GET";
	/**
	 * 回复24小时趋势图
	 */
	public static final String TYPE_TREND24_REPLY = "TREND24_REPLY";
	/**
	 * 获取设备详细信息
	 */
	public static final String TYPE_DEVICE_START = "DEVICE_START";

	/**
	 * 回复设备详细信息
	 */
	public static final String TYPE_DEVICE_REPLY = "DEVICE_REPLY";
	/**
	 * 获取系统信息
	 */
	public static final String TYPE_SYS_START = "SYS_START";
	/**
	 * 系统信息回复
	 */
	public static final String TYPE_SYS_REPLY = "SYS_REPLY";
	/**
	 * 广播人员定位
	 */
	public static final String TYPE_NODIFY_POSITION = "NODIFY_POSITION";
	/**
	 * 协议类型
	 */
	public String type = null;
	/**
	 * 来源主机
	 */
	public String from = null;
	/**
	 * 目的主机
	 */
	public String to = null;
	/**
	 * 时间值
	 */
	public String cseq = null;
	/**
	 * RFID值（只用于RFID读取和请求设备信息）
	 */
	public String rfid = null;
	/**
	 * 数据包的数量，默认为1
	 */
	public int packetCount = 0;
	/**
	 * 表示第几个数据包，默认值为1
	 */
	public int currentPachetNo = 0;
	/**
	 * 数据内容格式，默认为XML
	 */
	public String contentType = "XML";
	/**
	 * 数据的长度
	 */
	public int contentLength = 0;
	/**
	 * 用于获取24小时趋势图
	 */
	public String trendType;
	/**
	 * 登录用户名
	 */
	public String user;
	/**
	 * x,y,z
	 */
	public String position;

	/**
	 * 消息内容
	 */
	public String contentMessage = null;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCseq() {
		return cseq;
	}

	public void setCseq(String cseq) {
		this.cseq = cseq;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public int getPacketCount() {
		return packetCount;
	}

	public void setPacketCount(int packetCount) {
		this.packetCount = packetCount;
	}

	public int getCurrentPachetNo() {
		return currentPachetNo;
	}

	public void setCurrentPachetNo(int currentPachetNo) {
		this.currentPachetNo = currentPachetNo;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public String getTrendType() {
		return trendType;
	}

	public void setTrendType(String trendType) {
		this.trendType = trendType;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getContentMessage() {
		return contentMessage;
	}

	public void setContentMessage(String contentMessage) {
		this.contentMessage = contentMessage;
	}

}
