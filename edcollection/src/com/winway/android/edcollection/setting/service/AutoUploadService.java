package com.winway.android.edcollection.setting.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.dto.MessageBase;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.login.bll.LoginBll;
import com.winway.android.edcollection.login.entity.LoginResult;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.dto.SyncDataResult;
import com.winway.android.edcollection.project.entity.EmCDataLogEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.setting.bll.SettingBll;
import com.winway.android.network.HttpUtils;
import com.winway.android.network.HttpUtils.CallBack;
import com.winway.android.util.DateUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.LogUtil;
import com.winway.android.util.NetWorkUtils;
import com.winway.android.util.SharedPreferencesUtils;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**
 * 自动上传服务
 * 
 * @author xs
 *
 */
public class AutoUploadService extends Service {
	private AutoUpLoadBinder autoUpLoadBinder = new AutoUpLoadBinder();
	// 上传间隔时间
	private final long UPLOAD_INTERVAL_TIME = 10 * 60 * 1000;
	private boolean uploadThreadMark = false;// 上传线程标记
	private boolean uploadAttachThreadMark = false;// 上传附件线程标记
	private Context context;
	private LoginBll loginBll = null;
	private SettingBll cDataLogBll;// 数据更新日志业务逻辑
	private ComUploadBll comUploadBll;// 离线附件业务逻辑
	private List<OfflineAttach> entities;// 未上传附件集合
	private long upImgSize = 0;// 未上传照片大小
	private long upedSize = 0;// 记录上传的大小
	private int uploadIndex = 0;// 当前上传的索引

	@Override
	public IBinder onBind(Intent intent) {
		return autoUpLoadBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	class AutoUpLoadBinder extends Binder {
		public AutoUploadService getAutoUploadService() {
			return AutoUploadService.this;
		}
	}

	/**
	 * 关闭自动上传
	 */
	public void autoUploadClose() {
		uploadThreadMark = false;
	}

	/**
	 * 关闭自动上传附件
	 */
	public void autoUploadAttachClose() {
		uploadAttachThreadMark = false;
	}

	private final int UP_LOAD_DATA = 0x11;// 上传数据
	private final int UP_LOAD_ATTACH_DATA = 0x12;// 上传附件

	/**
	 * 上传数据Handler
	 */
	Handler uploadHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case UP_LOAD_DATA:
				// 更新机构用户信息
				new Thread(new Runnable() {
					@Override
					public void run() {
						loginBll.updateOrgUserInfo(GlobalEntry.loginResult);
					}
				}).start();
				uploadData(context);
				break;
			case UP_LOAD_ATTACH_DATA:
				// 更新机构用户信息
				new Thread(new Runnable() {
					public void run() {
						loginBll.updateOrgUserInfo(GlobalEntry.loginResult);
					}
				}).start();
				uploadAttachData();
				break;
			default:
				break;
			}
			return false;
		}
	});

	/**
	 * 开始自动上传
	 * 
	 * @param context
	 *            上下文
	 * @param globalDbUrl
	 *            有机构和用户信息的DB路径
	 * @param basicDbUrl
	 *            有基本业务信息的DB路径
	 */
	public void autoUploadStart(final Context context, String globalDbUrl, String basicDbUrl) {
		if (loginBll == null) {
			loginBll = new LoginBll(context, globalDbUrl);
		}
		cDataLogBll = new SettingBll(context, basicDbUrl);
		this.context = context;
		uploadThreadMark = true;
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (uploadThreadMark) {
					try {
						if (!NetWorkUtils.isConnected(context)) {
							Thread.sleep(UPLOAD_INTERVAL_TIME);
							continue;
						}
						if (GlobalEntry.loginResult.getCredit() == null
								|| GlobalEntry.loginResult.getCredit().equals("")) {
							String loginData = loginBll.login();
							LoginResult loginResult = loginBll.parseLogin(loginData);
							GlobalEntry.loginResult.setCredit(loginResult.getCredit());// 保存登录凭据到内存
						}
						uploadHandler.sendEmptyMessage(UP_LOAD_DATA);
						Thread.sleep(UPLOAD_INTERVAL_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private final int FLAG_UPLOAD_SUCCESS = 0x111;// 同步数据成功标识
	private final int FLAG_UPLOAD_FAIL = 0x112;// 同步数据失败标识

	/**
	 * 上传数据
	 * 
	 * @param context
	 */
	private void uploadData(final Context context) {
		// 获取上次更新的时间
		String lasttime = "";
		if (SharedPreferencesUtils.contains(context, "#lasttime#")) {
			lasttime = (String) SharedPreferencesUtils.get(context, "#lasttime#", "");
		}
		// 对于首次时间为空，取日志表最新一条已经上传的记录
		if (lasttime == null || lasttime.equals("")) {
			Date d = cDataLogBll.findLastlogtime();
			if (d != null) {
				lasttime = DateUtils.date2Str(d, DateUtils.datetimeMSFormat);
			}
		}

		final Handler handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case FLAG_UPLOAD_SUCCESS: // 上传成功
					LogUtil.d("ins", "上传成功");
					break;
				case FLAG_UPLOAD_FAIL: // 上传失败
					LogUtil.e("ins", "上传失败");
					break;
				default:
					break;
				}
				return false;
			}
		});

		SyncDataResult syncData = cDataLogBll.findNotUploadByPrj(GlobalEntry.currentProject.getEmProjectId());
		// 将对象转换成json
		final Gson gson = GsonUtils.build();
		Type syscDataType = new TypeToken<SyncDataResult>() {
		}.getType();
		String data = gson.toJson(syncData, syscDataType);
		// 上传数据请求
		String urlStr = GlobalEntry.dataServerUrl + "?action=em_syncdates&credit=" + GlobalEntry.loginResult.getCredit()
				+ "&prj=" + GlobalEntry.currentProject.getEmProjectId();
		Map<String, String> params = new HashMap<String, String>();
		params.put("lasttime", lasttime);
		params.put("data", data);
		try {

			HttpUtils.doPost(urlStr, params, new CallBack() {
				@Override
				public void onRequestComplete(String result) {
					Gson gson = GsonUtils.build();
					Type listType = new TypeToken<SyncDataResult>() {
					}.getType();
					SyncDataResult syncDataResult = gson.fromJson(result, listType);
					if (syncDataResult.getCode() >= 0) {
						// 修改状态
						updateUpoladData();
						cDataLogBll.saveUpdateData(syncDataResult);
						// 保存更新的时间
						if (syncDataResult.getTime() != null && !syncDataResult.getTime().equals("")) {
							SharedPreferencesUtils.put(context, "#lasttime#", syncDataResult.getTime());
						}
						handler.sendEmptyMessage(FLAG_UPLOAD_SUCCESS);
					} else {
						handler.sendEmptyMessage(FLAG_UPLOAD_FAIL);
					}
				}

				@Override
				public void onError(Exception exception) {
					handler.sendEmptyMessage(FLAG_UPLOAD_FAIL);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			handler.sendEmptyMessage(FLAG_UPLOAD_FAIL);
		}
	}

	/**
	 * 修改同步后的状态
	 */
	private void updateUpoladData() {
		ArrayList<EmCDataLogEntity> upoladDataList = cDataLogBll.getUpoladDataList();
		if (upoladDataList != null && upoladDataList.size() > 0) {
			for (EmCDataLogEntity entity : upoladDataList) {
				// 修改操作状态
				entity.setOptType(DataLogOperatorType.update.getValue());
				cDataLogBll.updateDataState(entity, WhetherEnum.YES.getValue());
			}
		}
	}

	/**
	 * 开启自动上传附件
	 * 
	 * @param context
	 *            上下文
	 * @param globalDbUrl
	 *            有机构和用户信息的DB路径
	 * @param basicDbUrl
	 *            有基本业务信息的DB路径
	 */
	public void autoUploadAttachmentStart(final Context context, String globalDbUrl, String basicDbUrl) {
		if (loginBll == null) {
			loginBll = new LoginBll(context, globalDbUrl);
		}
		comUploadBll = new ComUploadBll(context, basicDbUrl);
		uploadAttachThreadMark = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (uploadAttachThreadMark) {
					try {
						if (!NetWorkUtils.isConnected(context)
								|| !(NetWorkUtils.getNetworkTypeName(context).equals(NetWorkUtils.NETWORK_TYPE_WIFI))) {
							Thread.sleep(UPLOAD_INTERVAL_TIME);
							continue;
						}
						if (GlobalEntry.loginResult.getCredit() == null
								|| GlobalEntry.loginResult.getCredit().equals("")) {
							String loginData = loginBll.login();
							LoginResult loginResult = loginBll.parseLogin(loginData);
							GlobalEntry.loginResult.setCredit(loginResult.getCredit());// 保存登录凭据到内存
						}
						uploadHandler.sendEmptyMessage(UP_LOAD_ATTACH_DATA);
						Thread.sleep(UPLOAD_INTERVAL_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * 获取上传附件的大小
	 */
	private void getPicSize() {
		entities = comUploadBll.findNotUploadPictures();
		if (entities != null && entities.size() > 0) {
			for (OfflineAttach entity : entities) {
				upImgSize += FileUtil.getFileSize(entity.getFilePath());
			}
		}
	}

	/**
	 * 上传附件
	 */
	private void uploadAttachData() {
		getPicSize();
		if (upImgSize == 0) {
			return;
		}
		uploadCurrIndex();
	}

	private final int FLAG_UPLOAD_ATTACH_SUCCESS = 0x113;// 上传附件成功标识
	private final int FLAG_UPLOAD_ATTACH_FAIL = 0x114;// 上传附件失败标识

	/**
	 * 上传当前附件
	 */
	private void uploadCurrIndex() {
		if (uploadIndex >= entities.size()) {
			return;
		}
		final OfflineAttach entity = entities.get(uploadIndex);
		StringBuffer url = new StringBuffer(GlobalEntry.fileServerUrl).append("?action=uploadfile&uploadid=")
				.append(entity.getComUploadId()).append("&workno=").append(entity.getWorkNo()).append("&filename=")
				.append(entity.getFileName()).append("&credit=").append(GlobalEntry.loginResult.getCredit())
				.append("&ownercode=").append(entity.getOwnerCode());
		// 上传照片
		HttpUtils.uploadFile(url.toString(), entity.getFilePath(), new CallBack() {
			@Override
			public void onRequestComplete(String result) {
				Gson gson = GsonUtils.build();
				Type listType = new TypeToken<MessageBase>() {
				}.getType();
				MessageBase messageBase = gson.fromJson(result, listType);
				Message message = Message.obtain();
				if (messageBase.getCode() >= 0) {
					long currentAttachSize = FileUtil.getFileSize(entity.getFilePath());
					message.what = FLAG_UPLOAD_ATTACH_SUCCESS;
					message.getData().putLong("currentAttachSize", currentAttachSize);
					// 修改附件的状态
					comUploadBll.updateUpload(entity, WhetherEnum.YES.getValue());
					uploadAttachHandler.sendEmptyMessage(FLAG_UPLOAD_ATTACH_SUCCESS);
				} else {
					uploadAttachHandler.sendMessage(message);
				}
			}

			@Override
			public void onError(Exception exception) {
				uploadAttachHandler.sendEmptyMessage(FLAG_UPLOAD_ATTACH_FAIL);
			}
		});
	}

	/**
	 * 上传附件Handler
	 */
	final Handler uploadAttachHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case FLAG_UPLOAD_ATTACH_SUCCESS:// 上传附件成功标志
				upedSize += msg.getData().getLong("currentAttachSize");
				if (upImgSize == upedSize) {
					upImgSize = 0;
					LogUtil.d("ins", "上传附件成功");
				} else {
					uploadIndex++;
					uploadCurrIndex();
				}
				break;
			case FLAG_UPLOAD_ATTACH_FAIL:// 上传附件失败标志
				LogUtil.e("ins", "上传附件失败");
				break;
			default:
				break;
			}
			return false;
		}
	});

}
