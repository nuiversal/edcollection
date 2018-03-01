package com.winway.android.edcollection.setting.service;

import com.winway.android.edcollection.setting.service.AutoUploadService.AutoUpLoadBinder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 自动上传服务入口
 * 
 * @author xs
 *
 */
public class AutoUploadServiceEntrance {
	private Context context;
	private AutoUpLoadBinder binder;
	private String globalDbUrl;
	private String basicDbUrl;
	
	public AutoUploadServiceEntrance(Context context,String globalbUrl, String basicDbUrl){
		this.context = context;
		this.globalDbUrl = globalbUrl;
		this.basicDbUrl = basicDbUrl;
	}
	
	private ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (AutoUpLoadBinder) service;
			if (binder != null) {
				binder.getAutoUploadService().autoUploadStart(context, globalDbUrl, basicDbUrl);
				binder.getAutoUploadService().autoUploadAttachmentStart(context, globalDbUrl, basicDbUrl);
			}
		}
	};

	/**
	 * 打开服务
	 */
	public void open() {
		Intent intent = new Intent(context, AutoUploadService.class);
		context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}
	
	/**
	 * 关闭服务
	 */
	public void close(){
		if (binder != null) {
			binder.getAutoUploadService().autoUploadClose();
			binder.getAutoUploadService().autoUploadAttachClose();
		}
		context.unbindService(conn);
	}
}
