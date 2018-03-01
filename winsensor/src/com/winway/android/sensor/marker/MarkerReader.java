package com.winway.android.sensor.marker;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.winway.android.util.LogUtil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.text.TextUtils;

/**
 * 标识器读取
 * @author mr-lao
 *
 */
public class MarkerReader implements Runnable {
	private String tag = "marker-reader";
	UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private String address;
	private ReaderListener listener;
	// private BufferedReader lineReader;
	private InputStream inputStream;

	public MarkerReader(String address, ReaderListener listener) {
		this.address = address;
		this.listener = listener;
	}

	private boolean isClose = false;

	public void stopRead() {
		/*if (null != lineReader) {
			try {
				lineReader.close();
				isClose = true;
				lineReader = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		isClose = true;
	}

	byte[] bufferCache = new byte[64];
	int cacheLength = 0;

	@Deprecated
	private String readLine2(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		if (cacheLength > 0) {
			sb.append(new String(bufferCache, 0, cacheLength));
		}
		cacheLength = 0;
		byte[] buffer = new byte[64];
		int len = -1;
		boolean bk = false;
		while (!isClose) {
			len = in.read(buffer);
			if (len > 0) {
				int count = 0;
				int cacheIndex = 0;
				for (int i = 0; i < len; i++) {
					char ch = (char) buffer[i];
					if (ch == '\n' || ch == '\r') {
						bk = true;
						count = i - 1;
					}
					if (bk) {
						bufferCache[cacheIndex] = buffer[i];
						cacheIndex++;
					}
				}
				cacheLength = len - count - 1;
				if (count > 0) {
					sb.append(new String(buffer, 0, count));
				}
			}
			if (bk) {
				break;
			}
		}
		if (TextUtils.isEmpty(sb.toString())) {
			return null;
		}
		return sb.toString();
	}

	private String readLine(InputStream in) throws IOException {
		byte buffer[] = new byte[2048];
		int va = in.available();
		int bufferLength = 0;
		int index = 0;
		while (!isClose) {
			byte bt = (byte) in.read();
			char ch = (char) bt;
			if (ch == '\n' || ch == '\r') {
				if (va == 1) {
					return null;
				}
				if (bufferLength > 0) {
					break;
				} else {
					continue;
				}
			}
			buffer[index] = bt;
			index++;
			bufferLength++;
		}
		if (bufferLength > 0) {
			return new String(buffer, 0, bufferLength);
		}
		return null;
	}

	@Override
	public void run() {
		try {
			int lineIndex = 1;
			BluetoothDevice remoteDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
			BluetoothSocket bluetoothSocket = remoteDevice.createRfcommSocketToServiceRecord(uuid);
			bluetoothSocket.connect();
			LogUtil.i(tag, "获取读取流");
			inputStream = bluetoothSocket.getInputStream();
			// lineReader = new BufferedReader(new
			// InputStreamReader(inputStream));
			String line = null;
			LogUtil.i(tag, "开始读取");
			while ((line = /*lineReader.*/readLine(inputStream)) != null) {
				if (lineIndex > 9) {
					lineIndex = 1;
				}
				if (!TextUtils.isEmpty(line)) {
					line = line.trim();
					if (line.contains("ID#") && line.length() > 10) {
						String mkstr = line.substring(line.length() - 10, line.length());
						try {
							Integer.parseInt(mkstr);
							lineIndex = 2;
						} catch (Exception e) {
						}
					}
					int type = lineIndex;
					if (null != listener) {
						listener.onDataReceiver(type, line);
					}
				}
				lineIndex++;
			}
			inputStream.close();
			if (!isClose) {
				// lineReader.close();
			}
			if (null != listener) {
				listener.onFinish();
			}
			LogUtil.i(tag, "读取结束");
		} catch (Exception e) {
			if (null != listener) {
				if (!isClose) {
					listener.onFailed(e);
				}
			}
			e.printStackTrace();
			LogUtil.i(tag, "发生异常，读取结束");
		}
	}

	public static final int TYPE_MARKER_ID = 2;
	public static final int TYPE_MODEL = 3;

	public interface ReaderListener {
		public void onDataReceiver(int type, String data);

		public void onFailed(Exception e);

		public void onFinish();
	}

	/**
	 * 提取出MarkerID
	 * @param data
	 */
	public static String extractMarkerID(String data) {
		// data = DL,01,ID# : 0001447403
		if (TextUtils.isEmpty(data) || data.length() < 10) {
			return null;
		}
		data = data.trim();
		CharSequence mk = data.subSequence(data.length() - 10, data.length());
		StringBuilder sb = new StringBuilder();
		sb.append(mk.subSequence(0, 3)).append("-");
		sb.append(mk.subSequence(3, 6)).append("-");
		sb.append(mk.subSequence(6, 10));
		return sb.toString();
	}
}
