package com.winway.android.sensor.rfid;

import android.util.Log;

public class DataProcessUtil {
	private static byte[] dataReceive = new byte[1024];
	private static int dataReceiveEnd = 0;

	private static String dataProcess(byte[] readBuf, int nBytes) {// 处理接收数据
		String returnData = "";
		System.arraycopy(readBuf, 0, dataReceive, dataReceiveEnd, nBytes);
		dataReceiveEnd += nBytes;
		int dataEnd = -1;
		for (int i = 1; i < dataReceiveEnd; i++) {
			if (dataReceive[i] == (byte) 0x55 && dataReceive[i - 1] != (byte) 0xFF) {
				dataEnd = i;
				break;
			}
		}

		if (dataEnd > 0) {
			dataEnd++;
			int dataStrat = -1;
			for (int i = 0; i < dataReceiveEnd; i++) {
				if (dataReceive[i] == (byte) 0xAA && i == 0) {
					dataStrat = i;
					break;
				} else if (dataReceive[i] == (byte) 0xAA && dataReceive[i - 1] == (byte) 0xFF) {
					dataStrat = i;
					break;
				}
			}

			if (dataStrat >= 0) {
				byte[] bufTemp = subBytes(dataReceive, dataStrat, dataEnd - dataStrat);
				if (bufTemp[1] == (byte) (dataEnd - dataStrat - 2)) {
					if (bufTemp[2] == (byte) 0x20) {
						if (bufTemp[3] == (byte) 0x00) {
							int readDataLen = Integer.valueOf("06", 16) * 2;
							byte[] readData = subBytes(bufTemp, 4, readDataLen);
							returnData = byteToHexString(readData);
							byte[] EPCData = subBytes(bufTemp, readDataLen + 4, bufTemp.length - readDataLen - 5);

							// TODO 这是要拿取到的数据
							String targetData = returnData;
							Log.i("info", "目标数据：" + targetData);
						} else {
							returnData = "读取失败！";
						}
					}
				} else {
					returnData = "操作失败！" + byteToHexString(subBytes(dataReceive, 0, dataReceiveEnd));
				}
			}
			System.arraycopy(dataReceive, dataReceiveEnd, dataReceive, 0, dataReceiveEnd - dataEnd);
			dataReceiveEnd = dataReceiveEnd - dataEnd;
		}
		Log.i("info", "returnData:" + returnData);
		return returnData;
	}

	public static byte[] subBytes(byte[] src, int begin, int count) {
		byte[] bs = new byte[count];
		for (int i = begin; i < begin + count; i++)
			bs[i - begin] = src[i];
		return bs;
	}

	public static String byteToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	// TODO 检查接收到的数据
	private static byte[] checkRecievedData(byte[] readBuf, int nBytes) {
		byte[] returnBuf = new byte[1024];
		int l = 0;
		for (int i = 0; i < nBytes; i++) {
			if (readBuf[i] == 0xFF && i + 1 < nBytes) {
				if (readBuf[i + 1] == 0xAA || readBuf[i + 1] == 0xFF || readBuf[i + 1] == 0x55) {
					returnBuf[l] = readBuf[i + 1];
					i++;
				}
			} else {
				returnBuf[l] = readBuf[i];
			}
			l++;
		}
		return subBytes(returnBuf, 0, l);
	}

	public static String processData(byte[] readBuf, int nBytes) {
		String returnData = dataProcess(readBuf, nBytes);

		if (returnData != null && !returnData.trim().equals("") && !returnData.contains("失败")
				&& returnData.length() >= 24) {
			// 这是成功的了
			return returnData;
		}

		try {
			// 这是失败的，开始截取EPC ID数据
			return returnData.substring(returnData.length() - 26, returnData.length() - 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
