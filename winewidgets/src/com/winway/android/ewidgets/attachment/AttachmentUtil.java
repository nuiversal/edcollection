package com.winway.android.ewidgets.attachment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.winway.android.ewidgets.net.NetManager;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.BaseService.RequestCallBack;
import com.winway.android.util.ListUtil;

import android.text.TextUtils;

/**
 * 1、服务器操作（附件上传、下载、查询、删除），详情见接口文档
 * 2、找出附件列表中的增加或删除图片
 * @author mr-lao
 *
 */
public class AttachmentUtil {
	/**网络服务*/
	private static final BaseService netService = NetManager.getLineService();

	/**
	 * 上传附件（详情见接口文档）
	 * @param url  
	 * @param credit
	 * @param uploadid
	 * @param appcode
	 * @param ownercode
	 * @param workno
	 * @param filename
	 * @param files
	 * @param subdir
	 * @param uploadFile  要上传的文件
	 * @param cls
	 * @throws Exception
	 */
	public static <T> void uplaodAttachment(String url, String credit, String uploadid,
			String appcode, String ownercode, String workno, String filename, String files,
			String subdir, File uploadFile, Class<T> cls, RequestCallBack<T> call)
			throws Exception {
		if (TextUtils.isEmpty(credit)) {
			// call.error(-1);
			return;
		}
		HashMap<String, String> params = new HashMap<>();
		params.put("action", "uploadfile");
		params.put("credit", credit);
		if (!TextUtils.isEmpty(uploadid)) {
			params.put("uploadid", uploadid);
		}
		if (!TextUtils.isEmpty(appcode)) {
			params.put("appcode", appcode);
		}
		if (!TextUtils.isEmpty(ownercode)) {
			params.put("ownercode", ownercode);
		}
		if (!TextUtils.isEmpty(workno)) {
			params.put("workno", workno);
		}
		if (!TextUtils.isEmpty(filename)) {
			params.put("filename", filename);
		}
		// params.put("files", files);
		if (!TextUtils.isEmpty(subdir)) {
			params.put("subdir", subdir);
		}
		HashMap<String, File> fileMap = new HashMap<>();
		fileMap.put("files", uploadFile);
		netService.uploadFile(url, fileMap, params, null, cls, call);
	}

	/**
	 * 查询附件（详情见接口文档）
	 * @param url
	 * @param credit
	 * @param appcode
	 * @param ownercode
	 * @param workno
	 * @param filename
	 * @param cls
	 * @param call
	 * @throws Exception
	 */
	public static <T> void queryAttachment(String url, String credit, String appcode,
			String ownercode, String workno, String filename, Class<T> cls, RequestCallBack<T> call)
			throws Exception {
		HashMap<String, String> params = new HashMap<>();
		params.put("action", "searchfiles");
		params.put("credit", credit);
		params.put("appcode", appcode);
		params.put("ownercode", ownercode);
		params.put("workno", workno);
		params.put("filename", filename);
		netService.getRequest(url, params, null, cls, call);
	}

	/**
	 * 获取附件（详情见接口文档）
	 * @param url
	 * @param credit
	 * @param id
	 * @param size
	 * @param call
	 * @throws Exception
	 */
	public static void getAttachment(String url, String credit, String id, String size,
			RequestCallBack<File> call) throws Exception {
		HashMap<String, String> params = new HashMap<>();
		params.put("action", "getfile");
		params.put("credit", credit);
		params.put("id", id);
		params.put("size", size);
		netService.getRequestFile(url, params, null, call);
	}

	/**
	 * 删除附件（详情见接口文档）
	 * @param url
	 * @param credit
	 * @param id
	 * @param appcode
	 * @param ownercode
	 * @param workno
	 * @param filename
	 * @param cls
	 * @param call
	 * @throws Exception 
	 */
	public static <T> void deleteAttachment(String url, String credit, String id, String appcode,
			String ownercode, String workno, String filename, Class<T> cls, RequestCallBack<T> call)
			throws Exception {
		HashMap<String, String> params = new HashMap<>();
		params.put("action", "delfile");
		params.put("credit", credit);
		params.put("id", id);
		params.put("appcode", appcode);
		params.put("ownercode", ownercode);
		params.put("workno", workno);
		netService.getRequest(url, params, null, cls, call);
	}

	// 分组
	static void grouppinDivide(List<String> attachList, ArrayList<String> addList,
			ArrayList<String> deleteList,ArrayList<String> nochangeList) {
		if (null == attachList) {
			return;
		}
		for (String attach : attachList) {
			if (deleteList.contains(attach)) {
				deleteList.remove(attach);
				nochangeList.add(attach);
			} else {
				addList.add(attach);
			}
		}
	}

	public static AddDeleteResult findAddDeleteResult(AttachmentView.AttachmentResult result,
			List<String> allList) {
		final ArrayList<String> addList = new ArrayList<>();
		final ArrayList<String> deleteList = new ArrayList<>();
		final ArrayList<String> nochangeList=new ArrayList<>();
		if (null != allList) {
			ListUtil.copyList(deleteList, allList);
		}
		grouppinDivide(result.videoFilePathList, addList, deleteList, nochangeList);
		grouppinDivide(result.voiceFilePathList, addList, deleteList, nochangeList);
		grouppinDivide(result.textFilePathList, addList, deleteList, nochangeList);
		grouppinDivide(result.photoFilePathList, addList, deleteList, nochangeList);
		grouppinDivide(result.selectImgFilePathList, addList, deleteList, nochangeList);
		grouppinDivide(result.vrFilePathList, addList, deleteList, nochangeList);
		AddDeleteResult adrest = new AddDeleteResult();
		adrest.addList = addList;
		adrest.deleteList = deleteList;
		adrest.nochangeList=nochangeList;
		return adrest;
	}

	public static class AddDeleteResult {
		public ArrayList<String> addList;
		public ArrayList<String> deleteList;
		public ArrayList<String> nochangeList;
	}
}
 