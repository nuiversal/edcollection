package com.winway.android.edcollection.setting.controll;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.HTTP;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.about.activity.AboutActivity;
import com.winway.android.edcollection.adding.controll.BasicInfoControll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.dbVersion.IDbVersion;
import com.winway.android.edcollection.base.dbVersion.impl.DbVersionImpl;
import com.winway.android.edcollection.base.dto.MessageBase;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseFragmentControll;
import com.winway.android.edcollection.login.bll.EdpBll;
import com.winway.android.edcollection.login.bll.LoginBll;
import com.winway.android.edcollection.login.entity.LoginResult;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.dto.SyncDataResult;
import com.winway.android.edcollection.project.entity.CDbVersionEntity;
import com.winway.android.edcollection.project.entity.EmCDataLogEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.rtk.activity.DiscoveryActivity;
import com.winway.android.edcollection.rtk.entity.BluetoothConnectResult;
import com.winway.android.edcollection.rtk.entity.RtkConstant;
import com.winway.android.edcollection.rtk.utils.BlueToothUtil;
import com.winway.android.edcollection.rtk.utils.RtkUtils;
import com.winway.android.edcollection.setting.adapter.SettingAdapter;
import com.winway.android.edcollection.setting.bll.SettingBll;
import com.winway.android.edcollection.setting.entity.OperateType;
import com.winway.android.edcollection.setting.entity.SettingListViewItem;
import com.winway.android.edcollection.setting.viewholder.SettingsFragmentViewHolder;
import com.winway.android.network.HttpUtils;
import com.winway.android.network.HttpUtils.CallBack;
import com.winway.android.util.BroadcastReceiverUtils;
import com.winway.android.util.DateUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.LogUtil;
import com.winway.android.util.NetWorkUtils;
import com.winway.android.util.ProgressBarUtil;
import com.winway.android.util.ProgressUtils;
import com.winway.android.util.SharedPreferencesUtils;
import com.winway.android.util.ToastUtil;

/**
 * 设置
 * 
 * @author ly
 *
 */
public class SettingsFragmentControll extends BaseFragmentControll<SettingsFragmentViewHolder> {
	private SettingBll cDataLogBll;// 数据更新日志业务逻辑
	private ComUploadBll comUploadBll;// 离线附件业务逻辑
	private final int FLAG_UPLOAD_SUCCESS = 0x001;// 同步数据上传成功标志
	private final int FLAG_UPLOAD_FAIL = 0x002;// 同步数据上传失败标志
	private final int FLAG_UPLOADIMG_SUCCESS = 0x005;// 上传附件成功标志
	private final int FLAG_UPLOADIMG_FAIL = 0x006;// 上传附件失败标志
	private List<OfflineAttach> entities;// 未上传照片
	private int uploadIndex = 0;// 当前上传的索引
	private long upImgSize = 0;// 未上传照片大小
	private long upedSize = 0;// 记录上传的大小
	private boolean initFlag = false;// 是否初始化
	private ArrayList<SettingListViewItem> lvItemList;// SettingListView数据源
	private SettingAdapter adapter;// SettingListView适配器
	private String proDbUrl;
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = { Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE };
	private int upImgSizeMB = 0; // 用于显示未上传照片大小.
	private int upedSizeMB = 0; // 用于显示已上传照片大小.

	private BluetoothAdapter mBluetoothAdapter = null;
	private final int requestCode_openBluetooth = 11;// 请求码（请求打开蓝牙）
	public static final String rtk = "rtk";
	private LoginBll loginBll = null;
	/** 没有文件路径附件 */
	private List<String> failPicPathList = new ArrayList<String>();
	
	/**项目对应db最后更新时间key*/
	private String mUpdateTimeKey=GlobalEntry.currentProject.getPrjNo()+"#updateTime#";

	@Override
	public void init() {
		loginBll = new LoginBll(mActivity, FileUtil.AppRootPath + "/db/common/global.db");
		proDbUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		cDataLogBll = new SettingBll(mActivity, proDbUrl);
		comUploadBll = new ComUploadBll(mActivity, proDbUrl);
		BroadcastReceiverUtils.getInstance().registReceiver(mActivity, rtk, BasicInfoControll.rtkBroadcastReceiver);
		initData();
		initAdapter();
		initEvent();
		initFlag = true;
	}

	private void initAdapter() {
		adapter = new SettingAdapter(mActivity, lvItemList);
		viewHolder.getLvSettingList().setAdapter(adapter);
	}

	// 初始化数据
	private void initData() {
		lvItemList = new ArrayList<>();
		for (OperateType o : OperateType.values()) {
			SettingListViewItem item = new SettingListViewItem(o.getValue(), null);
			lvItemList.add(item);
		}
		// 获取上传照片的大小
		getPicSize();

		// 显示头部出现分割线
		viewHolder.getLvSettingList().setHeaderDividersEnabled(true);
		// 禁止底部出现分割线
		viewHolder.getLvSettingList().setFooterDividersEnabled(false);

		// 本地蓝牙适配器
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

	}

	public void refreshItems() {
		if (initFlag) {
			upImgSize = 0;
			getPicSize();
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 获取上传照片的大小
	 */
	private void getPicSize() {
		failPicPathList.clear();
		entities = comUploadBll.findNotUploadPictures();
		if (entities != null && entities.size() > 0) {
			// Iterator<OfflineAttach> it = entities.iterator();
			// while(it.hasNext()){
			// OfflineAttach offlineAttach = it.next();
			// //文件路径不存在时，将跳过此记录并从上传集合中删除
			// if (!FileUtil.checkFilePathExists(offlineAttach.getFilePath())) {
			// failPicPathList.add(offlineAttach.getFilePath());
			// it.remove();
			// continue;
			// }
			// upImgSize += FileUtil.getFileSize(offlineAttach.getFilePath());
			// }
			Iterator<OfflineAttach> iterator = entities.iterator();
			while (iterator.hasNext()) {
				OfflineAttach entity = iterator.next();
				if (!FileUtil.checkFilePathExists(entity.getFilePath())) {
					failPicPathList.add(entity.getFilePath());
					iterator.remove();
				} else {
					upImgSize += FileUtil.getFileSize(entity.getFilePath());
				}
			}
		}

		for (int i = 0; i < lvItemList.size(); i++) {
			SettingListViewItem item = lvItemList.get(i);
			if (item.getSettingItem().equals("上传附件")) {
				item.setSettingState(FileUtil.formatFileSize(upImgSize));
				lvItemList.set(i, item);
				break;
			}
		}
	}

	private void initEvent() {
		viewHolder.getLvSettingList().setOnItemClickListener(oicl);
	}

	private OnItemClickListener oicl = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			switch (position) {
			case 0: {// 同步数据
				getCreditAndOperate(OperateType.TBSJ);
				break;
			}
			case 1: {// 上传附件
				// if (NetWorkUtils.isConnected(mActivity)
				// &&
				// NetWorkUtils.getNetworkTypeName(mActivity).equals(NetWorkUtils.NETWORK_TYPE_WIFI))
				// {
				// getCreditAndOperate(OperateType.SCFJ);
				// } else if
				// (!NetWorkUtils.getNetworkTypeName(mActivity).equals(NetWorkUtils.NETWORK_TYPE_WIFI))
				// {
				// ToastUtil.show(mActivity, "附件只能在wifi状态下上传", 200);
				// } else {
				// showToast("无网络连接");
				// }
				if (NetWorkUtils.isConnected(mActivity)) {
					getCreditAndOperate(OperateType.SCFJ);
				} else {
					showToast("无网络连接");
				}
				break;
			}
			case 2:// 更新数据字典
			{
				getCreditAndOperate(OperateType.GXSJZD);
				break;
			}
			case 3:// 蓝牙
			{

				if (BlueToothUtil.isOpenBluetooth()) {
					// 关闭蓝牙socket连接
					BlueToothUtil.closeConnect();
					// 关闭蓝牙
					BlueToothUtil.closeBluetooth(mBluetoothAdapter);
					// 发送广播，停止闪烁
					BroadcastReceiverUtils.getInstance().sendCommand(mActivity, RtkConstant.flag_rtk_off);
					ToastUtil.show(mActivity, "蓝牙已关闭", 100);
				} else {
					BlueToothUtil.openBluetooth(mBluetoothAdapter, mActivity, requestCode_openBluetooth);
				}
				adapter.notifyDataSetChanged();
				break;
			}
			case 4:// RTK
			{
				if (BlueToothUtil.isConnectedRtk()) {
					// 关闭蓝牙socket连接
					BlueToothUtil.closeConnect();
					// 发送广播，停止闪烁
					BroadcastReceiverUtils.getInstance().sendCommand(mActivity, RtkConstant.flag_rtk_off);
					ToastUtil.show(mActivity, "RTK连接已关闭", 100);
				} else {
					if (!BlueToothUtil.isOpenBluetooth()) {
						BlueToothUtil.openBluetooth(mBluetoothAdapter, mActivity, requestCode_openBluetooth);
					}
					Intent intent = new Intent(mActivity, DiscoveryActivity.class);
					mActivity.startActivityForResult(intent, RtkConstant.requestCode_discoveryDevice);
				}
				adapter.notifyDataSetChanged();
				break;
			}
			case 5:// 备份数据
			{
				copyDBToSDcrad();
				break;
			}
			case 6:// 关于
			{
				Intent intent = new Intent(mActivity, AboutActivity.class);
				mActivity.startActivity(intent);
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * 获取tf卡路径
	 * 
	 * @param context
	 * @return
	 */
	public String getTFCardPath(Context context) {
		String sdPathIn = FileUtil.getSDCardPath();// 系统使用的路径
		List<String> sdPaths = FileUtil.getTFCardPaths();// 全部卡的路径
		if (sdPaths != null) {
			if (sdPaths.isEmpty()) {
				// 没有找到tf卡
				return null;
			} else {
				// 有tf卡
				String path = sdPaths.get(sdPaths.size() - 1);// tf卡路径为最后一个
				if (sdPathIn.equals(path)) {
					// tf卡与系统使用的卡路径一样
				} else {
					// tf卡与系统使用的卡路径不一样
				}
				return path;
			}
		}
		return null;
	}

	/** 备份db到外置sd卡中 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void copyDBToSDcrad() {
		ProgressUtils.getInstance().show("请稍后", false, mActivity);
		new Thread(new Runnable() {
			@Override
			public void run() {
				String path = getTFCardPath(mActivity);
				String fileName = GlobalEntry.currentProject.getPrjNo() + ".db";
				if (TextUtils.isEmpty(path)) {// 判断tf卡是否存在
					Looper.prepare();
					ToastUtil.show(mActivity, "外置SD卡不存在，无法备份数据！", 200);
					ProgressUtils.getInstance().dismiss();
					Looper.loop();
				} else {
					// 获取所有缓存目录
					File[] fs = mActivity.getExternalCacheDirs();
					// 匹配sd卡目录
					for (int i = 0; i < fs.length; i++) {
						String str = fs[i].getAbsolutePath();
						if (str.contains(path)) {
							path = str;// 找到路径了
							break;
						}
					}
					path = path + "/" + fileName;
					// 已存在备份, 删除文件
					if (FileUtil.checkFilePathExists(path)) {
						FileUtil.deleteFile(path);
					}
					String fromDir = FileUtil.AppRootPath + "/db/project/" + fileName;
					FileUtil.copy(fromDir, path);
					Looper.prepare();
					ToastUtil.show(mActivity, "备份成功", 100);
					ProgressUtils.getInstance().dismiss();
					Looper.loop();
				}
			}
		}).start();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == requestCode_openBluetooth) {// 蓝牙成功打开回调
				adapter.notifyDataSetChanged();
			} else if (requestCode == RtkConstant.requestCode_discoveryDevice) {// 处理rtk
				BluetoothDevice mBluetoothDevice = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				BluetoothConnectResult bluetoothConnectResult = BlueToothUtil.connect(mBluetoothDevice);
				if (bluetoothConnectResult.isConnect() == false) {// 连接失败
					ToastUtil.show(mActivity, "RTK连接失败", 100);
				} else {
					ToastUtil.show(mActivity, "RTK连接成功", 100);
					// 刷新状态
					adapter.notifyDataSetChanged();
					// 发送广播，通知闪烁
					BroadcastReceiverUtils.getInstance().sendCommand(mActivity, RtkConstant.flag_rtk_on);
					// 接收数据
					BlueToothUtil.dataReceiver(bluetoothConnectResult, uiHandler);
				}
			}
		}
	};

	// =====RTK开始=====
	/** GGA字符串 **/
	private StringBuffer sBufferGGA = new StringBuffer();
	private String currentGPGGA = null;
	private String GPGGA = "$GPGGA";
	private Handler uiHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case RtkConstant.get_coords:
				try {
					String str = (String) msg.obj;
					sBufferGGA.append(str);
					if (RtkUtils.appearNumber(sBufferGGA.toString(), GPGGA) == 2) {// 有2个$GPGGA字符才去取值
						String tmp = sBufferGGA.toString();
						tmp = tmp.substring(tmp.indexOf(GPGGA));
						tmp = tmp.substring(0, tmp.lastIndexOf(GPGGA));
						currentGPGGA = tmp;// 赋值
						sBufferGGA.delete(0, sBufferGGA.toString().length());// 清空
						// 解析数据、更新ui显示
						String[] arr = currentGPGGA.split(",");
						RtkUtils.parseRtkData(arr);
						if (BasicInfoControll.rtkInfoDialog != null) {
							BroadcastReceiverUtils.getInstance().sendCommand(mActivity, rtk);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case RtkConstant.LINK_BREAK_OFF:
				ToastUtil.show(mActivity, "RTK连接断开", 100);
				// 刷新状态
				adapter.notifyDataSetChanged();
				// 发送广播，通知闪烁
				BroadcastReceiverUtils.getInstance().sendCommand(mActivity, RtkConstant.flag_rtk_off);
				// 清掉最后一次数据
				GlobalEntry.rtkLocationInfo = null;
				if (BasicInfoControll.rtkInfoDialog != null) {
					BasicInfoControll.rtkInfoDialog.dismiss();
					BasicInfoControll.rtkInfoDialog = null;
				}
				if (BasicInfoControll.rtkBroadcastReceiver != null) {
					try {
						mActivity.unregisterReceiver(BasicInfoControll.rtkBroadcastReceiver);
						BasicInfoControll.rtkBroadcastReceiver = null;
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				break;
			default:
				break;
			}
		};

	};
	// =====RTK结束=====

	final Handler unloadHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case FLAG_UPLOADIMG_SUCCESS: {// 上传附件成功标志
				upedSize += msg.getData().getLong("size");
				if (upedSize >= (1024 * 1024)) {
					upedSizeMB = (int) upedSize / (1024 * 1024);
				} else {
					upedSizeMB = (int) upedSize;
				}
				ProgressBarUtil.mProgressDialog.setProgress(upedSizeMB);
				if (upedSize == upImgSize) {
					resetUplaodAttachStatus();
				} else {
					uploadIndex++;
					uploadCurrIndex();
				}
				break;
			}
			case FLAG_UPLOADIMG_FAIL: {// 上传附件失败标志
				String path = msg.getData().getString("imgpath");
				// getPicSize();
				// adapter.notifyDataSetChanged();
				showToast("上传附件失败,附件路径：" + path);
				ProgressBarUtil.getInstance().dismiss();
				break;
			}
			default:
				break;
			}
			return false;
		}
	});

	/**
	 * 重置上传附件状态
	 */
	private void resetUplaodAttachStatus() {
		ProgressBarUtil.getInstance().dismiss();
		upImgSize = 0;
		uploadIndex = 0;
		for (int i = 0; i < lvItemList.size(); i++) {
			if (OperateType.SCFJ.getIndex() == i) {
				SettingListViewItem item = lvItemList.get(i);
				item.setSettingState("0.00B");
				lvItemList.set(i, item);
				adapter.notifyDataSetChanged();
				break;
			}
		}
		showToast("上传附件成功");
		showUploadFailPathAndSaveDialog();
	}

	/**
	 * 显示上传失败附件路径弹窗和保存失败路径到本地
	 */
	private void showUploadFailPathAndSaveDialog() {
		// 将文件路径不存在的附件以弹窗形式显示提示
		if (null != failPicPathList && !failPicPathList.isEmpty()) {
			String dir = FileUtil.AppRootPath + "log/";
			String fileName = "上传失败的附件" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".txt";
			String[] paths = new String[failPicPathList.size()];
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < failPicPathList.size(); i++) {
				paths[i] = new String(failPicPathList.get(i));
				builder.append(failPicPathList.get(i).toString());
				builder.append("\r\n");
			}
			FileUtil.save(dir, fileName, builder.toString());
			AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
			dialog.setTitle("上传失败文件路径(可在本地log文件夹下查看)");
			dialog.setItems(paths, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			dialog.create();
			dialog.show();
		}
	}

	private void uploadCurrIndex() {
		if (uploadIndex >= entities.size()) {
			resetUplaodAttachStatus();
			return;
		}
		final OfflineAttach entity = entities.get(uploadIndex);
		StringBuffer url = new StringBuffer(GlobalEntry.fileServerUrl).append("?action=uploadfile&uploadid=")
				.append(entity.getComUploadId()).append("&workno=").append(entity.getWorkNo()).append("&filename=")
				.append(entity.getFileName()).append("&credit=").append(GlobalEntry.loginResult.getCredit())
				.append("&ownercode=").append(entity.getOwnerCode());
		LogUtil.i(url + "");
		// 上传照片
		HttpUtils.uploadFile(url.toString(), entity.getFilePath(), new CallBack() {
			@Override
			public void onRequestComplete(String result) {
				Gson gson = GsonUtils.build();
				Type listType = new TypeToken<MessageBase>() {
				}.getType();
				MessageBase messageBase = gson.fromJson(result, listType);
				Message msg = new Message();
				if (messageBase.getCode() >= 0) {
					long value = FileUtil.getFileSize(entity.getFilePath());
					msg.what = FLAG_UPLOADIMG_SUCCESS;
					// 修改附件的状态
					comUploadBll.updateUpload(entity, WhetherEnum.YES.getValue());
					msg.getData().putLong("size", value);
					unloadHandler.sendMessage(msg);
				} else {
					msg.what = FLAG_UPLOADIMG_FAIL;
					msg.getData().putString("imgpath", entity.getFilePath());
					unloadHandler.sendMessage(msg);
				}

			}

			@Override
			public void onError(Exception exception) {
				Message msg = new Message();
				msg.what = FLAG_UPLOADIMG_FAIL;
				msg.getData().putString("imgpath", entity.getFilePath());
				unloadHandler.sendMessage(msg);
			}
		});
	}

	/**
	 * 上传附件
	 */
	protected void uploadPic() {
		if (upImgSize == 0) {
			showToast("没有可上传的附件");
			return;
		}
		AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity, R.style.dialog_app_style);
		dialog.setTitle("温馨提示");
		dialog.setMessage("确认上传附件?");
		dialog.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ProgressBarUtil.getInstance().showBar(mActivity, "数据正在上传,请稍后......");
				if (upImgSize >= (1024 * 1024)) {
					upImgSizeMB = (int) (upImgSize / (1024 * 1024));
				} else {
					upImgSizeMB = (int) upImgSize;
				}
				ProgressBarUtil.mProgressDialog.setMax(upImgSizeMB);
				uploadCurrIndex();
			}
		});
		dialog.show();
	}

	/**
	 * 同步数据
	 */
	protected void synData() {
		ProgressUtils.getInstance().show("同步中...", false, mActivity);
		final Handler handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case FLAG_UPLOAD_SUCCESS: {// 上传成功
					showToast("数据同步成功");
					ProgressUtils.getInstance().dismiss();
					break;
				}
				case FLAG_UPLOAD_FAIL: {// 上传失败
					ProgressUtils.getInstance().dismiss();
					String err = "数据同步失败";
					if (msg.obj != null) {
						err += "：" + msg.obj;
					}
					showToast(err);
					break;
				}
				default:
					break;
				}
				return false;
			}
		});
		// 执行数据查找、数据上传、数据更新的耗时操作
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				// 判断db版本和服务器版本是否一致
				CDbVersionEntity componentVersion = cDataLogBll.getServerComVersion();
				if (componentVersion == null) {
					msg.what = FLAG_UPLOAD_FAIL;
					msg.obj = "获取服务端版本信息异常，请联系开发人员";
					handler.sendMessage(msg);
					return;
				}
				IDbVersion dbVersion = new DbVersionImpl();
				CDbVersionEntity cDbVersionEntity = dbVersion.getDbVersion(mActivity, GlobalEntry.prjDbUrl,
						"ED_DATAPLUG");
				if (cDbVersionEntity == null) {
					msg.what = FLAG_UPLOAD_FAIL;
					msg.obj = "无本地数据库版本信息，请联系开发人员";
					handler.sendMessage(msg);
					return;
				}
				Integer serverVersion1 = componentVersion.getVersion1();
				Integer localDbVersion1 = cDbVersionEntity.getVersion1();
				Integer serverVersion2 = componentVersion.getVersion2();
				Integer localDbVersion2 = cDbVersionEntity.getVersion2();
				if (!serverVersion1.equals(localDbVersion1)) {// 本地db版本和服务端版本不一致
					msg.what = FLAG_UPLOAD_FAIL;
					msg.obj = "本地db版本和服务端版本不一致，请联系开发人员";
					handler.sendMessage(msg);
					return;
				}
				if (!serverVersion2.equals(localDbVersion2)) {
					msg.what = FLAG_UPLOAD_FAIL;
					msg.obj = "本地db版本和服务端版本不一致，请联系开发人员";
					handler.sendMessage(msg);
					return;
				}
				// 获取上次更新的时间
				String lasttime = "";
				if (SharedPreferencesUtils.contains(mActivity, mUpdateTimeKey)) {
					lasttime = (String) SharedPreferencesUtils.get(mActivity, mUpdateTimeKey, "");
				}
				// 对于首次时间为空，取日志表最新一条已经上传的记录
				if (lasttime == null || lasttime.equals("")) {
					Date d = cDataLogBll.findLastlogtime();
					if (d != null) {
						lasttime = DateUtils.date2Str(d, DateUtils.datetimeMSFormat);
					}
				}
				SyncDataResult syncData = cDataLogBll.findNotUploadByPrj(GlobalEntry.currentProject.getEmProjectId());
				// 将对象转换成json
				final Gson gson = GsonUtils.build();
				Type syscDataType = new TypeToken<SyncDataResult>() {
				}.getType();
				String data = gson.toJson(syncData, syscDataType);
				try {
					// 上传数据请求
					// String urlStr = GlobalEntry.dataServerUrl +
					// "?action=em_syncdates&credit="
					// + GlobalEntry.loginResult.getCredit() + "&prj="
					// + GlobalEntry.currentProject.getEmProjectId() +
					// "&lasttime="
					// + URLEncoder.encode(lasttime, HTTP.UTF_8);
					String urlStr = GlobalEntry.dataServerUrl + "?action=em_syncdates_byorgid&credit="
							+ GlobalEntry.loginResult.getCredit() + "&prj="
							+ GlobalEntry.currentProject.getEmProjectId() + "&orgid="
							+ GlobalEntry.currentProject.getOrgId() + "&lasttime="
							+ URLEncoder.encode(lasttime, HTTP.UTF_8);
					Map<String, String> params = new HashMap<String, String>();
					params.put("data", data);
					String syncDataResultJsonStr = HttpUtils.doPost(urlStr, params);
					if (TextUtils.isEmpty(syncDataResultJsonStr)) {
						msg.what = FLAG_UPLOAD_FAIL;
						handler.sendMessage(msg);
						return;
					}
					SyncDataResult syncDataResult = gson.fromJson(syncDataResultJsonStr, syscDataType);
					if (syncDataResult.getCode() == 0) {
						// 修改状态
						updateUpoladData();
						cDataLogBll.saveUpdateData(syncDataResult);
						cDataLogBll.delLocalDatasByLog(mActivity);
						// 保存更新的时间
						if (syncDataResult.getTime() != null && !syncDataResult.getTime().equals("")) {
							SharedPreferencesUtils.put(mActivity, mUpdateTimeKey, syncDataResult.getTime());
						}
						msg.what = FLAG_UPLOAD_SUCCESS;
						msg.obj = syncDataResult;
						handler.sendMessage(msg);
					} else {
						msg.what = FLAG_UPLOAD_FAIL;
						msg.obj = syncDataResult.getMsg();
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = FLAG_UPLOAD_FAIL;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	/**
	 * 修改同步后的状态
	 */
	protected void updateUpoladData() {
		ArrayList<EmCDataLogEntity> upoladDataList = cDataLogBll.getUpoladDataList();
		if (upoladDataList != null && upoladDataList.size() > 0) {
			for (EmCDataLogEntity entity : upoladDataList) {
				// 修改操作状态
				// entity.setOptType(DataLogOperatorType.update.getValue());
				cDataLogBll.updateDataState(entity, WhetherEnum.YES.getValue());
			}
		}
	}

	private final int synDataFlagSuccess = 0x007;// 同步数据操作
	private final int uploadAttFlagSuccess = 0x008;// 上传附件操作
	private final int getParamFlagSuccess = 0x009;// 更新数据字典操作
	private final int prjFlagFail = 0x010;// 获取凭据失败

	/**
	 * 获取凭据并且进行不同的操作
	 * 
	 * @param operateType
	 */
	private void getCreditAndOperate(final OperateType operateType) {
		// TODO Auto-generated method stub
		if (!NetWorkUtils.isConnected(mActivity)) {
			ToastUtil.show(mActivity, "网络异常", 100);
			return;
		}
		ProgressUtils.getInstance().show("加载中...", false, mActivity);

		new Thread(new Runnable() {
			@Override
			public void run() {
				Message message = new Message();
				try {
					String loginData = loginBll.login();
					LoginResult loginResult = loginBll.parseLogin(loginData);
					GlobalEntry.loginResult.setCredit(loginResult.getCredit());// 保存登录凭据到内存
					// 根据枚举判断是哪个操作
					if (OperateType.TBSJ.getIndex().equals(operateType.getIndex())) {// 同步数据操作
						message.what = synDataFlagSuccess;
					} else if (OperateType.SCFJ.getIndex().equals(operateType.getIndex())) {// 上传附件操作
						message.what = uploadAttFlagSuccess;
					} else if (OperateType.GXSJZD.getIndex().equals(operateType.getIndex())) {// 更新数据字典操作
						message.what = getParamFlagSuccess;
					}
					operateHandler.sendMessage(message);
				} catch (Exception e) {
					// TODO: handle exception
					message.what = prjFlagFail;
					message.obj = e.getMessage();
					operateHandler.sendMessage(message);
				}

			}
		}).start();
	}

	Handler operateHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			ProgressUtils.getInstance().dismiss();
			switch (msg.what) {
			case synDataFlagSuccess: {// 同步数据操作
				// 更新机构用户信息
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						loginBll.updateOrgUserInfo(GlobalEntry.loginResult);
					}
				}).start();
				synData();
				break;
			}
			case uploadAttFlagSuccess: {// 上传附件操作
				uploadPic();
				break;
			}
			case getParamFlagSuccess: {// 更新数据字典操作
				String dbUrl = FileUtil.AppRootPath + "/db/common/global.db";
				EdpBll edpBll = new EdpBll(mActivity, dbUrl);
				edpBll.getParamList(mActivity);
				break;
			}
			case prjFlagFail: {
				ToastUtil.show(mActivity, "加载失败:" + msg.obj.toString(), 100);
				break;
			}
			default:
				break;
			}
			return false;
		}
	});

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ProgressUtils.getInstance().dismiss();
		}
		return false;

	};
}
