package com.winway.android.basicbusiness.business;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.basicbusiness.entity.EmCDataLogEntity;
import com.winway.android.basicbusiness.entity.MessageBase;
import com.winway.android.basicbusiness.entity.OfflineAttach;
import com.winway.android.basicbusiness.entity.WhetherEnum;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.ewidgets.progress.AsynProgressUtils;
import com.winway.android.network.HttpUtils;
import com.winway.android.util.AsynToastUtil;
import com.winway.android.util.FileUtil;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.NetWorkUtils;
import com.winway.android.util.ThreadPoolUtil;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.os.Looper;

/**
 * 离线附件上传 预防组件出错，请在使用组件之前，执行初始化方法init()，初始化方法必须在主线程中调用
 * 
 * @author mr-lao
 *
 */
public class OfflineAttachUpload {
	private Activity activity;
	private BaseDBUtil dbUtil;
	private String serverUrl;
	private String credit;
	private long progress;

	/**
	 * 预防组件出错，请在使用组件之前，执行初始化方法，初始化方法必须在主线程中调用
	 * 
	 * @tip 如果AsynToastUtil.getInstance()和AsynProgressUtils.getInstance()这两个方法已经在主线程中被调用的话，则此初始化方法不执行也可
	 * @param app
	 */
	public static void init(Application app) {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new RuntimeException(" OfflineAttachUpload组件初始化方法必须在主线程中调用");
		}
		AsynToastUtil.getInstance(app);
		AsynProgressUtils.getInstance();
	}

	public OfflineAttachUpload(Activity activity, String dburl, String serverUrl) {
		this.activity = activity;
		this.serverUrl = serverUrl;
		dbUtil = new BaseDBUtil(activity, new File(dburl));
		try {
			dbUtil.createTableIfNotExist(OfflineAttach.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	public OfflineAttachUpload(Activity activity, BaseDBUtil dbUtil, String serverUrl) {
		this.activity = activity;
		this.dbUtil = dbUtil;
		this.serverUrl = serverUrl;
		try {
			dbUtil.createTableIfNotExist(OfflineAttach.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传离线附件
	 * 
	 * @param credit 登录凭据
	 * @param needCheckWifi 
	 */
	public void upload(String credit, boolean needCheckWifi) {
		this.progress = 0;
		this.credit = credit;
		needUploadList = null;
		if (needCheckWifi) {
			if (NetWorkUtils.isConnected(activity)
					&& NetWorkUtils.getNetworkTypeName(activity).equals(NetWorkUtils.NETWORK_TYPE_WIFI)) {
				// 上传附件
				upload(activity, dbUtil);
			} else if (!NetWorkUtils.getNetworkTypeName(activity).equals(NetWorkUtils.NETWORK_TYPE_WIFI)) {
				AsynToastUtil.getInstance(null).showToast("附件只能在wifi状态下上传", 200);
			} else {
				AsynToastUtil.getInstance(null).showToast("无网络连接");
			}
		} else {
			if (NetWorkUtils.isConnected(activity)) {
				upload(activity, dbUtil);
			} else {
				AsynToastUtil.getInstance(null).showToast("无网络连接");
			}
		}
	}

	/**
	 * 上传离线附件
	 * 
	 * @param credit
	 *            登陆凭据
	 */
	public void upload(String credit) {
		upload(credit, true);
	}

	private void upload(Activity activity, final BaseDBUtil dbUtil) {
		try {
			final List<OfflineAttach> attachList = getUploadAttachList(dbUtil);
			if (null == attachList || attachList.isEmpty()) {
				AsynToastUtil.getInstance(null).showToast("没有需要上传的附件");
				return;
			}
			final long upImgSize = getAttachFileSize(attachList);
			AsynToastUtil.getInstance(null).showToast("开始上传，附件总数量：" + attachList.size());
			AsynProgressUtils.getInstance().show("数据正在上传,请稍后......", false, activity, ProgressDialog.STYLE_HORIZONTAL);
			AsynProgressUtils.getInstance().setMax((int) upImgSize);
			final ArrayList<OfflineAttach> successList = new ArrayList<OfflineAttach>();
			final ArrayList<OfflineAttach> faildList = new ArrayList<OfflineAttach>();
			// 开始上传
			for (final OfflineAttach attach : attachList) {
				ThreadPoolUtil.excute(new Runnable() {
					@Override
					public void run() {
						try {
							uploadAttach(serverUrl, credit, attach, successList, faildList);
						} catch (Exception e) {
							e.printStackTrace();
						}
						// 更新进度条
						progress += FileUtil.getFileSize(attach.getFilePath());
						if (progress >= upImgSize) {
							uploadFinish(attachList, successList, faildList);
						} else {
							// 更新进度条
							AsynProgressUtils.getInstance().setProgress((int) progress);
						}
					}
				});
			}
		} catch (DbException e) {
			e.printStackTrace();
			AsynToastUtil.getInstance(null).showToast("上传失败");
		}
	}

	private void uploadFinish(List<OfflineAttach> attachList, ArrayList<OfflineAttach> successList,
			ArrayList<OfflineAttach> faildList) {
		if (null != uploadFinishListener) {
			uploadFinishListener.finish();
		}
		needUploadList = null;
		// 上传完成
		AsynToastUtil.getInstance(null).showToast(
				"上传完毕，上传附件数量：" + attachList.size() + "，成功上传数量：" + successList.size() + "，失败数据：" + faildList.size());
		AsynProgressUtils.getInstance().dismiss();
		if (successList.isEmpty()) {
			return;
		}
	}

	/**
	 * 单个附件上传
	 * 
	 * @throws Exception
	 */
	private void uploadAttach(String serverurl, String credit, OfflineAttach entity, List<OfflineAttach> successList,
			List<OfflineAttach> faildList) throws Exception {
		StringBuffer url = new StringBuffer(serverurl).append("?action=uploadfile&uploadid=")
				.append(entity.getComUploadId()).append("&workno=").append(entity.getWorkNo()).append("&filename=")
				.append(entity.getFileName()).append("&credit=").append(credit).append("&ownercode=")
				.append(entity.getOwnerCode());
		// 上传照片
		String uploadFile = HttpUtils.uploadFile(url.toString(), entity.getFilePath());
		MessageBase result = GsonUtils.build().fromJson(uploadFile, MessageBase.class);
		if (result.getCode() != 0) {
			// 同步失败
			faildList.add(entity);
			AsynToastUtil.getInstance(null).showToast("上传失败：" + entity.getFilePath());
		} else {
			successList.add(entity);
			// 更新数据库
			entity.setIsUploaded(WhetherEnum.YES.getValue());
			dbUtil.update(entity);
		}
	}

	private List<OfflineAttach> needUploadList;

	/** 获取未上的附件 */
	private List<OfflineAttach> getUploadAttachList(BaseDBUtil dbUtil) throws DbException {
		if (null != needUploadList) {
			return needUploadList;
		}
		dbUtil.EGNORE_VALUE = -1000;
		dbUtil.createTableIfNotExist(OfflineAttach.class);
		OfflineAttach queryEntity = new OfflineAttach();
		queryEntity.setIsUploaded(WhetherEnum.NO.getValue());
		List<OfflineAttach> excuteQuery = dbUtil.excuteQuery(OfflineAttach.class, queryEntity);
		if (null == excuteQuery || excuteQuery.isEmpty()) {
			return null;
		}
		needUploadList = new ArrayList<>();
		for (OfflineAttach offlineAttach : excuteQuery) {
			if (FileUtil.checkFilePathExists(offlineAttach.getFilePath())) {
				needUploadList.add(offlineAttach);
			}
		}
		return needUploadList;
	}

	/** 获取附件的大小 */
	private long getAttachFileSize(List<OfflineAttach> attachList) {
		long size = 0;
		if (attachList != null && !attachList.isEmpty()) {
			for (OfflineAttach attach : attachList) {
				size += FileUtil.getFileSize(attach.getFilePath());
			}
		}
		return size;
	}

	/**
	 * 获取未上传的附件的大小
	 * 
	 * @param dbUtil
	 * @return
	 */
	public String getNeedUploadAttachFileSize() {
		try {
			List<OfflineAttach> attachList = getUploadAttachList(dbUtil);
			double attachFileSize = getAttachFileSize(attachList);
			if (attachFileSize == 0) {
				return null;
			}
			double mb = (attachFileSize / 1024) / 1024;
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			return decimalFormat.format(mb) + "MB";
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	private UploadFinishListener uploadFinishListener;

	public void setUploadFinishListener(UploadFinishListener uploadFinishListener) {
		this.uploadFinishListener = uploadFinishListener;
	}

	public interface UploadFinishListener {
		void finish();
	}
}
