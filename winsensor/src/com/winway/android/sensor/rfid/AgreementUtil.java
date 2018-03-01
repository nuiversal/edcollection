package com.winway.android.sensor.rfid;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.text.TextUtils;

/**
 * 协议工具类
 * 
 * @author mr-lao
 *
 */
public class AgreementUtil {
	public static String getSendString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SCAN_START").append("\n");
		builder.append("From:127.0.0.1").append("\n");
		builder.append("To:127.0.0.1").append("\n");
		builder.append("CSeq:").append(System.currentTimeMillis()).append("\n");
		builder.append("\n");
		builder.append("\n");
		return builder.toString();
	}

	public static String getReplyString(String rfid) {
		StringBuilder builder = new StringBuilder();
		builder.append("SCAN_REPLY").append("\n");
		builder.append("From:127.0.0.1").append("\n");
		builder.append("To:127.0.0.1").append("\n");
		builder.append("CSeq:").append(System.currentTimeMillis()).append("\n");
		builder.append("Rfid:").append(rfid).append("\n");
		builder.append("\n");
		builder.append("\n");
		return builder.toString();
	}

	static Pattern p = Pattern.compile("\\s*|\t|\r|\n");

	static String deelString(String str) {
		Matcher matcher = p.matcher(str);
		return matcher.replaceAll("");
	}

	/**
	 * 将字符串数据解决成协议实体（单包情况下使用）
	 * 
	 * @param str
	 * @return
	 */
	public static AgreementEntity parserAgreemen(String str) {
		AgreementEntity entity = new AgreementEntity();
		int flag = str.indexOf("Content-Length");
		if (flag > 0) {
			// 说明有消息体
			flag = str.indexOf("\n", flag) + 1;
		}
		// 协议头
		String headerStr = flag > 0 ? str.substring(0, flag) : str;
		String[] headerLines = headerStr.split("\n");
		// 协议类型
		entity.type = deelString(headerLines[0]);
		String line = "";
		for (int i = 1; i < headerLines.length; i++) {
			line = deelString(headerLines[i]);
			if (TextUtils.isEmpty(line)) {
				continue;
			}
			String[] split = line.split(":");
			if (null == split || split.length < 2) {
				continue;
			}
			String name = split[0];
			String value = split[1];
			if ("From".equals(name)) {
				entity.from = value;
			} else if ("To".equals(name)) {
				entity.to = value;
			} else if ("CSeq".equals(name)) {
				entity.cseq = value;
			} else if ("PacketCount".equals(name)) {
				entity.packetCount = Integer.parseInt(value);
			} else if ("CurrentPacketNo".equals(name)) {
				entity.currentPachetNo = Integer.parseInt(value);
			} else if ("Content-type".equals(name)) {
				entity.contentType = value;
			} else if ("Content-Length".equals(name)) {
				entity.contentLength = Integer.parseInt(value);
			} else if ("Trend-type".equals(name)) {
				entity.trendType = value;
			} else if ("Rfid".equals(name)) {
				entity.rfid = value;
			} else if ("User".equals(name)) {
				entity.user = value;
			} else if ("Position".equals(name)) {
				entity.position = value;
			}
		}
		entity.contentMessage = flag > 0 ? str.substring(flag) : null;
		return entity;
	}

	/**
	 * 将数据包字符串解释成协议实体（支持分包）
	 * 
	 * @param array
	 *            必须是同一条消息的数据包，否则会出现无法预料的错误
	 * @return
	 */
	@SuppressLint("UseSparseArrays")
	public static AgreementEntity parserAgreemen(String[] array) {
		// 解释协议头
		AgreementEntity entity = null;
		HashMap<Integer, String> contentMap = new HashMap<Integer, String>();
		for (String str : array) {
			entity = parserAgreemen(str);
			contentMap.put(entity.getCurrentPachetNo(), entity.contentMessage);
		}
		String contentMessage = "";
		for (int i = 1; i <= entity.packetCount; i++) {
			contentMessage += contentMap.get(i);
		}
		entity.contentMessage = contentMessage;
		return entity;
	}

	/**
	 * 将多个协议实体合并成一个协议实体（分包的情况下此方法才有意义，而且，数据包数量必须完整才行，否则方法会抛出异常）
	 * 
	 * @param list
	 * @return
	 */
	@SuppressLint("UseSparseArrays")
	public static AgreementEntity merge(List<AgreementEntity> list) {
		AgreementEntity agrement = list.get(0);
		String contentMessage = "";
		// 进行排序
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (AgreementEntity entity : list) {
			map.put(entity.currentPachetNo, entity.getContentMessage());
		}
		for (int i = 1; i <= agrement.packetCount; i++) {
			contentMessage += map.get(i);
		}
		agrement.contentMessage = contentMessage;
		return agrement;
	}

	/**
	 * 解释协议实体，但只生成协议头数据
	 * 
	 * @return
	 */
	public static String formatAgreemenInHead(AgreementEntity entity) {
		StringBuilder builder = new StringBuilder();
		builder.append(entity.type).append("\n");
		if (null != entity.from) {
			builder.append("From:").append(entity.from).append("\n");
		}
		if (null != entity.to) {
			builder.append("To:").append(entity.to).append("\n");
		}
		if (null != entity.cseq) {
			builder.append("CSeq:").append(entity.cseq).append("\n");
		}
		if (entity.packetCount > 0) {
			builder.append("PacketCount:").append(entity.packetCount).append("\n");
		}
		if (entity.currentPachetNo > 0) {
			builder.append("CurrentPacketNo:").append(entity.currentPachetNo).append("\n");
		}
		if (null != entity.contentType) {
			builder.append("Content-type:").append(entity.contentType).append("\n");
		}
		if (null != entity.getTrendType()) {
			builder.append("Trend-type:").append(entity.trendType).append("\n");
		}
		if (null != entity.rfid) {
			builder.append("Rfid:").append(entity.rfid).append("\n");
		}
		if (null != entity.user) {
			builder.append("User:").append(entity.user).append("\n");
		}
		if (null != entity.position) {
			builder.append("Position:").append(entity.position).append("\n");
		}
		if (entity.contentLength > 0) {
			builder.append("Content-Length:").append(entity.contentLength).append("\n");
		}
		return builder.toString();
	}

	/**
	 * 解释协议实体成字符串（单包情况下使用）
	 * 
	 * @param entity
	 * @return
	 */
	public static String formatAgreemen(AgreementEntity entity) {
		String str = formatAgreemenInHead(entity);
		if (null != entity.contentMessage) {
			str = str + entity.contentMessage + "\n";
		}
		return str;
	}

	/**
	 * 解释协议实体成字符串（支持分包）
	 * 
	 * @param entity
	 * @return
	 */
	public static String[] formatAgreemenBySubpackage(AgreementEntity entity) {
		// 大于500个字符就要进行分包
		String string = formatAgreemen(entity);
		if (string.length() > 500) {
			// 进行分包
			String head = formatAgreemenInHead(entity);
			int lenth = 500 - head.length();
			double d = entity.contentMessage.length() / (double) lenth;
			int size = (int) d;
			if (d > size) {
				size++;
			}
			// 包数量
			entity.setPacketCount(size);
			String array[] = new String[size];
			String content = "";
			for (int i = 0; i < size; i++) {
				try {
					content = entity.contentMessage.substring(i * lenth, (i + 1) * lenth);
				} catch (Exception e) {
					content = entity.contentMessage.substring(i * lenth);
				}
				entity.setContentLength(content.length());
				entity.setCurrentPachetNo(i + 1);
				head = formatAgreemenInHead(entity);
				array[i] = head + content;
			}
			return array;
		} else {
			// 不需要分包
			String array[] = new String[1];
			array[0] = string;
			return array;
		}
	}

}
