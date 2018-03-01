package com.winway.android.sensor.rfid;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.winway.android.sensor.rfid.RFIDUtil.RFIDResultListener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 运行UDP服务器的Service（使用的时候，需要在项目工程的清单文件中注册Service）
 * ，此Service高度集成导游APP功能，不需要的时候不要使用（建议非导游APP不要使用）
 * 
 * @author mr-lao
 *
 */
public class RFIDService extends Service {
	// UDP服务器
	private DatagramSocket udpSocket = null;
	// UDP数据包
	private DatagramPacket pack = new DatagramPacket(new byte[1024], 1024);
	// 服务器运行状态
	private boolean needStop = false;
	private int defaultServerPort = 9997;
	private int defaultClientPort = 9998;
	// private boolean isFirst = true;

	// 线程池
	static ExecutorService excuter = null;
	public static String sendMessage = "";

	public static String rfid_nfc_Str = null;

	@Override
	public void onCreate() {
		super.onCreate();
		startUDP(defaultServerPort);
		Log.i("senserinfo", "蓝牙服务接收已经打开");
		if (null == excuter) {
			excuter = Executors.newFixedThreadPool(4);
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (null != UtilActivity.util) {
			UtilActivity.util.setRFIDResultListener(rfidListener);
		}
		if (intent != null) {
			String m = intent.getStringExtra("sendMessage");
			if (m != null) {
				sendMessage = m;
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public void onDestroy() {
		needStop = true;
	}

	// 启动UDP服务，监听
	void startUDP(final int port) {
		new Thread() {
			public void run() {
				needStop = false;
				try {
					udpSocket = new DatagramSocket(port);
					while (!needStop) {
						udpSocket.receive(pack);
						proccessDatagramPacket(pack);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void proccessScan() {
		excuter.execute(new Runnable() {
			@Override
			public void run() {
				rfid_nfc_Str = null;
				while (rfid_nfc_Str == null) {
					if (null != UtilActivity.util) {
						UtilActivity.util.scanfRFID();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {

					}
				}
				if (null != UtilActivity.util) {
					UtilActivity.util.stopScanf = true;
				}
				String string = AgreementUtil.getReplyString(rfid_nfc_Str);
				sendData(string);
				rfid_nfc_Str = null;
			}
		});
	}

	void proccessDatagramPacket(DatagramPacket pack) throws IOException {
		// TODO 处理接收到的UDP信息
		String data = new String(pack.getData(), 0, pack.getLength(), "UTF-8");
		Log.i("senserinfo", "command data = " + data);
		AgreementEntity agreement = AgreementUtil.parserAgreemen(data);
		if (agreement.type.equals(AgreementEntity.TYPE_SCAN_START)) {
			if (null != UtilActivity.util) {
				UtilActivity.util.scanfRFID();
			}
			// proccessScan();
		} else if (agreement.type.equals(AgreementEntity.TYPE_SYS_START)) {
			if (null != sendMessage) {
				sendData(sendMessage);
			}
		}
	}

	Object lock = "Lock";

	// RFID内容接收监听器
	private RFIDResultListener rfidListener = new RFIDResultListener() {
		@Override
		public void onResult(int what, String msg) {

			try {
				synchronized (lock) {
					String string = AgreementUtil.getReplyString(msg);
					sendData(string);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// rfid_nfc_Str = msg;
		}
	};

	// 返回消息给客户端
	void sendData(final String msg) {
		excuter.execute(new Runnable() {
			@Override
			public void run() {
				try {
					byte[] data = msg.getBytes("UTF-8");
					DatagramPacket pack = new DatagramPacket(data, data.length);
					pack.setPort(defaultClientPort);
					pack.setAddress(InetAddress.getByName("127.0.0.1"));
					udpSocket.send(pack);
					Log.i("senserinfo", "send:" + msg);
				} catch (IOException e) {
					e.printStackTrace();
					Log.i("senserinfo", "send error:" + msg);
				}
			}
		});
	}

	static void showToast(String msg, Context context) {
		Toast.makeText(context, msg, 0).show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
