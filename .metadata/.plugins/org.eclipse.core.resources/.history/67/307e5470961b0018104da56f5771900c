package com.winway.android.edcollection.base.service;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.login.entity.LoginResult;
import com.winway.android.network.HttpUtils;
import com.winway.android.network.HttpUtils.CallBack;
import com.winway.android.util.FileUtil;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.LogUtil;
import com.winway.android.util.ProgressUtils;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class UploadCrashFileService extends Service {
	
	private UploadCrashBinder uploadCrashBinder = new UploadCrashBinder();
	private   List<File> files = new ArrayList<File>() ;
	private   List<File> filesCopy = new ArrayList<File>() ;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		uploadCrashFile();
		return uploadCrashBinder;
	}

	
	
	private void uploadCrashFile() {
		
		
		// TODO Auto-generated method stub
		String filePath = FileUtil.AppRootPath + "/log/";	//异常文件的目录
	    //如果filePath不以文件分隔符结尾，自动添加文件分隔符  
	    File dirFile = new File(filePath);  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	      return;
	    } 
	    files = Arrays.asList(dirFile.listFiles()); 
	    filesCopy.addAll(files);
	    //遍历删除文件夹下的所有文件(包括子目录)  
	    for (int i = 0; i < files.size(); i++) {  
	        if (files.get(i).isFile()) {  
	        		StringBuffer url = new StringBuffer(GlobalEntry.fileServerUrl).append("?action=uploadlog")
	        					.append("&credit=").append(GlobalEntry.loginResult.getCredit())
	        					.append("&filename=").append(files.get(i).getName());
	        		LogUtil.i(url + "");
	        		Toast.makeText(getApplication(),"path:" + files.get(i).getAbsolutePath() , Toast.LENGTH_SHORT).show();
				HttpUtils.uploadFile(url.toString(),files.get(i).getAbsolutePath(),new CallBack() {
					@Override
					public void onRequestComplete(String result) {
						Log.e("TAG","result:"+result);
						if(TextUtils.isEmpty(result)) {
							try {
								JSONObject  json = new JSONObject(result);
								/*
								 * {"uploaded":"","code":0,"msg":"","result":"aa.txt","attributes":null}
								 */
								if(0== json.optInt("code")) {
									String fileName = json.optString("result");
									File file = indextFiles(files,fileName);
									if(deleteFile(file.getAbsolutePath())){
										filesCopy.remove(file);
										if(filesCopy.size()==0) {
											stopSelf();
										}
									}	
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
					@Override
					public void onError(Exception exception) {
						stopSelf();
					}
				});
	        } 
	    } 
	}

	protected File indextFiles(List<File> files2,String fileName) {
		// TODO Auto-generated method stub
		for(int i =0;i<files2.size();i++) {
			String name =files2.get(i).getName();
			if(name.equals(fileName)) {
				return files2.get(i);
			}
		}
		return null;
	}
	public class UploadCrashBinder extends Binder{
		public void stopSelf() {
			UploadCrashFileService.this.stopSelf();
		}
	}
}
