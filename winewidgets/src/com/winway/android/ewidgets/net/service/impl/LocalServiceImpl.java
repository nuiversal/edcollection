package com.winway.android.ewidgets.net.service.impl;

import java.io.File;
import java.util.Map;

import com.winway.android.ewidgets.net.service.LocalService;

import android.content.Context;

public class LocalServiceImpl implements LocalService {

	public LocalServiceImpl(Context context) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getRequestString(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postRequestString(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestString(String url, Map<String, String> params, RequestMethod method,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestFile(String url, Map<String, String> params, RequestMethod method,
			Map<String, String> headers, RequestCallBack<File> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void getRequest(String url, Map<String, String> params, Map<String, String> headers,
			Class<T> cls, RequestCallBack<T> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void postRequest(String url, Map<String, String> params, Map<String, String> headers,
			Class<T> cls, RequestCallBack<T> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void request(String url, Map<String, String> params, Map<String, String> headers,
			Class<T> cls, RequestMethod method, RequestCallBack<T> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadFile(String url, File file, String fileparam, Map<String, String> headers,
			RequestCallBack<String> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadFile(String url, Map<String, File> files, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void uploadFile(String url, Map<String, File> files, Map<String, String> params,
			Map<String, String> headers, Class<T> cls, RequestCallBack<T> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downLoadFile(String url, Map<String, String> params, Map<String, String> headers,
			String filepath, RequestCallBack<File> call) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveString(String url, Map<String, String> params, Map<String, String> headers,
			String result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void saveT(String url, Map<String, String> params, Map<String, String> headers,
			T result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveFile(String url, Map<String, String> params, Map<String, String> headers,
			File result) {
		// TODO Auto-generated method stub
		
	}

	
}