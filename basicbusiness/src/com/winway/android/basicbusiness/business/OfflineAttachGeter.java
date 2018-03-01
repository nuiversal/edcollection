package com.winway.android.basicbusiness.business;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.basicbusiness.entity.ComUploadEntity;
import com.winway.android.basicbusiness.entity.OfflineAttach;
import com.winway.android.basicbusiness.entity.SearchfilesResult;
import com.winway.android.basicbusiness.entity.WhetherEnum;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.BaseService.RequestCallBack;
import com.winway.android.ewidgets.net.service.impl.LineServiceImpl;
import com.winway.android.ewidgets.progress.AsynProgressUtils;
import com.winway.android.util.AsynToastUtil;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ListUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;

/**
 * 离线附件获取器
 * 
 * @author mr-lao
 *
 */
public class OfflineAttachGeter {
	private Activity activity;
	private BaseDBUtil dbUtil;
	private String serverUrl;
	private String credit;
	// 附件下载文件目录
	private String downLoadDir;
	// 网络下载器
	private BaseService net;

	private int downLoadCount = 0;
	private int downLoadProgress;

	public OfflineAttachGeter(Activity activity, BaseDBUtil dbUtil, String serverUrl, String credit,
			String downLoadDir) {
		super();
		this.activity = activity;
		this.dbUtil = dbUtil;
		this.serverUrl = serverUrl;
		this.credit = credit;
		this.downLoadDir = downLoadDir;
		net = new LineServiceImpl();
		init();
	}

	public OfflineAttachGeter(Activity activity, BaseDBUtil dbUtil, String serverUrl, String credit, String downLoadDir,
			BaseService net) {
		super();
		this.activity = activity;
		this.dbUtil = dbUtil;
		this.serverUrl = serverUrl;
		this.credit = credit;
		this.downLoadDir = downLoadDir;
		this.net = net;
		init();
	}

	void init() {
		AsynProgressUtils.getInstance();
		try {
			dbUtil.createTableIfNotExist(OfflineAttach.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	public String getCredit() {
		return credit;
	}

	/**
	 * 设置登陆凭据
	 * 
	 * @param credit
	 */
	public void setCredit(String credit) {
		this.credit = credit;
	}

	/**
	 * 设置连接网络的服务对象
	 * 
	 * @param net
	 */
	public void setNetService(BaseService net) {
		this.net = net;
	}

	/**
	 * 通过主键
	 * 
	 * @param id
	 * @return
	 * @throws DbException
	 */
	public OfflineAttach getOfflineAttachFromDatabase(String id) throws DbException {
		return dbUtil.getById(OfflineAttach.class, id);
	}

	/**
	 * 通过查询实体获取离线附件
	 * 
	 * @param queryEntity
	 * @return
	 * @throws DbException
	 */
	public List<OfflineAttach> queryOfflineAttachFromDatabase(OfflineAttach queryEntity) throws DbException {
		return dbUtil.excuteQuery(OfflineAttach.class, queryEntity);
	}

	/**
	 * 通过业务表的主键获取离线附件
	 * 
	 * @param workno
	 * @return
	 * @throws DbException
	 */
	public List<OfflineAttach> queryOfflineAttachFromDatabase(String workno) throws DbException {
		OfflineAttach queryEntity = new OfflineAttach();
		queryEntity.setWorkNo(workno);
		return queryOfflineAttachFromDatabase(queryEntity);
	}

	static String createURL(String serverUrl, String action, String credit, String workno, String id) {
		String url = serverUrl + "?credit=" + credit + "&action=" + action;
		if (!TextUtils.isEmpty(id)) {
			url += "&id=" + id;
		}
		if (!TextUtils.isEmpty(workno)) {
			url += "&workno=" + workno;
		}
		return url;
	}

	static OfflineAttach createOfflineAttach(SearchfilesResult.Searchfile file) {
		OfflineAttach attach = new OfflineAttach();
		attach.setAppCode(file.getAppcode());
		attach.setComUploadId(file.getId());
		attach.setOwnerCode(file.getOwnercode());
		attach.setWorkNo(file.getWorkno());
		attach.setFileName(file.getFilename());
		attach.setIsUploaded(WhetherEnum.YES.getValue());
		return attach;
	}

	/**
	 * 通过业务编号（业务对象的主键）从网络上来获取附件以及附件的文件（显示Progress）
	 * 
	 * @param workno
	 * @param call
	 * @throws Exception
	 */
	public void queryOfflineAttachFromNetwork(String workno, final CallBack call) throws Exception {
		queryOfflineAttachFromNetwork(workno, true, call);
	}

	/**
	 * 通过业务编号（业务对象的主键）从网络上来获取附件以及附件的文件
	 * 
	 * @param workno
	 * @param hasProgress
	 * @param call
	 * @throws Exception
	 */
	public void queryOfflineAttachFromNetwork(String workno, final boolean hasProgress, final CallBack call)
			throws Exception {
		String url = createURL(serverUrl, "searchfiles", credit, workno, null);
		net.getRequest(url, null, null, SearchfilesResult.class, new BaseService.RequestCallBack<SearchfilesResult>() {
			@Override
			public void error(int code, BaseService.WayFrom from) {
				call.callError(CallBack.CODE_ERROR_NET_ERROR);
			}

			@Override
			public void callBack(SearchfilesResult result, BaseService.WayFrom from) {
				if (result.getCode() != 0 || result.getFiles() == null || result.getFiles().isEmpty()) {
					// 失败或者是无数据
					call.callError(CallBack.CODE_ERROR_NET_ERROR);
					return;
				}
				List<OfflineAttach> list = new ArrayList<>();
				for (SearchfilesResult.Searchfile file : result.getFiles()) {
					OfflineAttach attach = createOfflineAttach(file);
					list.add(attach);
					getOfflineAttachFileFromInternet(attach, true, hasProgress, call);
				}
				call.callAttachs(CallBack.CODE_FROM_INTERNET, list, false);
			}
		});
	}

	/**
	 * 通过离线附件的主键ID从网络上获取附件的文件（显示Progress）
	 * 
	 * @param attach
	 * @param hasProgress
	 * @param call
	 */
	public void getOfflineAttachFileFromInternet(OfflineAttach attach, CallBack call) {
		getOfflineAttachFileFromInternet(attach, true, call);
	}

	/**
	 * 通过离线附件的主键ID从网络上获取附件的文件
	 * 
	 * @param attach
	 * @param hasProgress
	 * @param call
	 */
	public void getOfflineAttachFileFromInternet(OfflineAttach attach, boolean hasProgress, CallBack call) {
		getOfflineAttachFileFromInternet(attach, false, hasProgress, call);
	}

	static String createAttachFilePath(String downLoadDir, OfflineAttach attach) {
		return downLoadDir + "/" + attach.getComUploadId() + "_" + attach.getFileName();
	}

	private void getOfflineAttachFileFromInternet(final OfflineAttach attach, final boolean create,
			final boolean hasProgress, final CallBack call) {
		getOfflineAttachFileFromInternet(attach, create, hasProgress, call, false, 0);
	}

	/**
	 * 通过离线附件的主键ID从网络上获取附件的文件
	 * 
	 * @param attach
	 * @param create
	 * @param hasProgress
	 * @param call
	 * @param isHorizontalProgress
	 *            值为true时，表示进度对话框显示条状进度条
	 * @param progressMax
	 *            进度条最大值
	 */
	private void getOfflineAttachFileFromInternet(final OfflineAttach attach, final boolean create,
			final boolean hasProgress, final CallBack call, final boolean isHorizontalProgress, int progressMax) {
		downLoadCount++;
		if (hasProgress) {
			if (isHorizontalProgress) {
				AsynProgressUtils.getInstance().show("正在下载附件", false, activity, ProgressDialog.STYLE_HORIZONTAL);
				AsynProgressUtils.getInstance().setMax(progressMax);
			} else {
				AsynProgressUtils.getInstance().show("正在下载附件", false, activity);
			}
		}
		// 下载离线附件
		String dowloadURL = createURL(serverUrl, "getfile", credit, null, attach.getComUploadId());
		// 文件路径
		String filepath = createAttachFilePath(downLoadDir, attach);
		try {
			net.downLoadFile(dowloadURL, null, null, filepath, new RequestCallBack<File>() {
				@Override
				public void error(int code, BaseService.WayFrom from) {
					downLoadProgress++;
					if (downLoadProgress >= downLoadCount) {
						if (hasProgress) {
							// 下载完成
							AsynProgressUtils.getInstance().dismiss();
						}
						downLoadCount = 0;
					}
					call.callError(CallBack.CODE_ERROR_NET_ERROR);
				}

				@Override
				public void callBack(File request, BaseService.WayFrom from) {
					downLoadProgress++;
					if (isHorizontalProgress && hasProgress) {
						AsynProgressUtils.getInstance().setProgress(downLoadProgress);
					}
					if (downLoadProgress >= downLoadCount) {
						if (hasProgress) {
							// 下载完成
							AsynProgressUtils.getInstance().dismiss();
						}
						downLoadCount = 0;
					}
					attach.setFilePath(request.getAbsolutePath());
					attach.setIsUploaded(WhetherEnum.YES.getValue());
					try {
						// 保存入库
						dbUtil.saveOrUpdate(attach);
					} catch (DbException e) {
						e.printStackTrace();
						call.callError(CallBack.CODE_ERROR_DBEXCEPTION);
					}
					call.callFile(attach);
				}
			});
		} catch (Exception e) {
			downLoadProgress++;
			if (downLoadProgress >= downLoadCount) {
				if (hasProgress) {
					// 下载完成（失败）
					AsynProgressUtils.getInstance().dismiss();
				}
				downLoadCount = 0;
			}
			call.callError(CallBack.CODE_ERROR_NET_ERROR);
			e.printStackTrace();
		}
	}

	public interface CallBack {
		/** 来自本地数据库 */
		public static final int CODE_FROM_DATABASE = 0;
		/** 来自网络 */
		public static final int CODE_FROM_INTERNET = 1;
		/** 下载失败 */
		public static final int CODE_ERROR_DOWN_FAILD = -1;
		/** 网络出错 */
		public static final int CODE_ERROR_NET_ERROR = -2;
		/** 参数错误，方法中传递的参数不正确 */
		public static final int CODE_ERROR_WRONG_PARAMS = -3;
		/** 数据库异常 */
		public static final int CODE_ERROR_DBEXCEPTION = -4;

		/**
		 * 获得离线附件回调
		 * 
		 * @param code
		 *            编码值，判断离线附件是从网络获取的，还是从本地数据库中获取的
		 * @param list
		 *            离线附件结果集
		 * @param hasfile
		 *            申明离线附件中是否包含文件路径，一般来说，从网络中获取的离线附件都不包含文件路径（由callFile()回调函数将文件路径返回）
		 */
		public void callAttachs(int code, List<OfflineAttach> list, boolean hasfile);

		/**
		 * 回调返回附件的文件路径
		 * 
		 * @param attach
		 */
		public void callFile(OfflineAttach attach);

		public void callError(int code);
	}

	/**
	 * 建议使用此方法获取离线附件 通过业务对象主键获取离线附件（显示Progress） 获取规则：
	 * 1、数据库中的离线附件，并且附件的文件存在，则将数据库中的离线附件返回，并请求服务器，看是否还有未下载的离线附件
	 * 2、数据库中存在离线附件，但附件的文件不存在，则将附件返回，并执行下载附件文件操作 3、数据库不存在离线附件，则从服务器请求离线附件
	 * 
	 * @param workno
	 *            业务对象编号
	 * @param call
	 *            回调（回调器会在主线程中被回调，所以可以放心地在回调器中执行UI方法）
	 * @throws Exception
	 */
	public void queryOfflieAttach(String workno, final CallBack call) throws Exception {
		queryOfflieAttach(workno, true, call);
	}

	/**
	 * 建议使用此方法获取离线附件 通过业务对象主键获取离线附件 获取规则：
	 * 1、数据库中的离线附件，并且附件的文件存在，则将数据库中的离线附件返回，并请求服务器，看是否还有未下载的离线附件
	 * 2、数据库中存在离线附件，但附件的文件不存在，则将附件返回，并执行下载附件文件操作 3、数据库不存在离线附件，则从服务器请求离线附件
	 * 
	 * @param workno
	 *            业务对象编号
	 * @param hasProgress
	 *            是否显示Progress
	 * @param call
	 *            回调（回调器会在主线程中被回调，所以可以放心地在回调器中执行UI方法）
	 * @throws Exception
	 */
	public void queryOfflieAttach(String workno, boolean hasProgress, final CallBack call) throws Exception {
		if (TextUtils.isEmpty(workno)) {
			throw new Exception("workno不能为空！");
		}

		// 首先去离线文件中查询
		if (queryInLocal(workno, call)) {
			return;
		}

		final List<OfflineAttach> queryList = queryOfflineAttachFromDatabase(workno);
		if (null != queryList && !queryList.isEmpty()) {
			ArrayList<OfflineAttach> hasFileList = new ArrayList<>();
			ArrayList<OfflineAttach> noFileList = new ArrayList<>();
			// 数据库中有离线附件
			for (OfflineAttach at : queryList) {
				if (!TextUtils.isEmpty(at.getFilePath()) && FileUtil.checkFilePathExists(at.getFilePath())) {
					hasFileList.add(at);
				} else {
					noFileList.add(at);
				}
			}
			if (!hasFileList.isEmpty()) {
				call.callAttachs(CallBack.CODE_FROM_DATABASE, hasFileList, true);
			}
			if (!noFileList.isEmpty()) {
				// 下载
				call.callAttachs(CallBack.CODE_FROM_DATABASE, noFileList, false);
				// 下载文件
				for (OfflineAttach attach : noFileList) {
					getOfflineAttachFileFromInternet(attach, hasProgress, call);
				}
			}
			// 请求网络，看是否有新的
			String url = createURL(serverUrl, "searchfiles", credit, workno, null);
			net.getRequest(url, null, null, SearchfilesResult.class,
					new BaseService.RequestCallBack<SearchfilesResult>() {
						@Override
						public void error(int code, BaseService.WayFrom from) {
							call.callError(CallBack.CODE_ERROR_NET_ERROR);
						}

						@Override
						public void callBack(SearchfilesResult result, BaseService.WayFrom from) {
							if (result.getCode() != 0 || result.getFiles() == null || result.getFiles().isEmpty()) {
								// 失败或者是无数据
								call.callError(CallBack.CODE_ERROR_NET_ERROR);
								return;
							}
							ArrayList<OfflineAttach> addList = new ArrayList<>();
							for (SearchfilesResult.Searchfile sf : result.getFiles()) {
								boolean add = true;
								for (OfflineAttach attach : queryList) {
									if (attach.getComUploadId().equals(sf.getId())) {
										add = false;
										break;
									}
								}
								if (add) {
									OfflineAttach addat = createOfflineAttach(sf);
									addList.add(addat);
								}
							}
							if (!addList.isEmpty()) {
								call.callAttachs(CallBack.CODE_FROM_INTERNET, addList, false);
								for (OfflineAttach at : addList) {
									getOfflineAttachFileFromInternet(at, true, call);
								}
							}
						}
					});

		} else {
			// 数据库中没有离线附件，去网上查找
			queryOfflineAttachFromNetwork(workno, hasProgress, call);
		}
	}

	boolean queryInLocal(String workno, final CallBack call) {
		try {
			ComUploadEntity queryEntity = new ComUploadEntity();
			queryEntity.setWorkNo(workno);
			List<ComUploadEntity> list = dbUtil.excuteQuery(queryEntity);
			if (list.isEmpty()) {
				return false;
			}
			ArrayList<OfflineAttach> attaList = new ArrayList<>();
			for (ComUploadEntity comUploadEntity : list) {
				String filepath = downLoadDir + "webimages" + File.separator + comUploadEntity.getSavePath();
				//将路径中的'\'改为系统默认的分隔符
				if (filepath.indexOf("\\") != -1) {
					String[] split = filepath.split("\\\\");
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < split.length; i++) {
						if (i != split.length - 1) {
							sb.append(split[i] + File.separator);
						} else {
							sb.append(split[i]);
						}
					}
					filepath = sb.toString();
				}
				File file = new File(filepath);
				if (null != file && file.exists()) {
					if (filepath.lastIndexOf(".vr") >= 0) {
						FileUtil.copy(new File(downLoadDir + "webimages/" + comUploadEntity.getComUploadId()), file);
					}
					OfflineAttach atta = new OfflineAttach();
					atta.setFilePath(filepath);
					atta.setFileName(comUploadEntity.getSavePath());
					atta.setComUploadId(comUploadEntity.getSavePath());
					attaList.add(atta);
				}
			}
			if (!attaList.isEmpty()) {
				call.callAttachs(0, attaList, true);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 通过附件ID获取附件（在线或者本地）（显示Progress）
	 * 
	 * @param id
	 *            离线附件ID
	 * @param sufx
	 *            文件后缀名
	 * @param call
	 *            回调
	 * @throws Exception
	 */
	public void getOfflieAttach(String id, String sufx, final CallBack call) throws Exception {
		getOfflieAttach(id, sufx, true, call);
	}

	/**
	 * 通过附件ID获取附件（在线或者本地）
	 * 
	 * @param id
	 *            离线附件ID
	 * @param sufx
	 *            文件后缀名
	 * @param hasProgress
	 *            是否显示Progress
	 * @param call
	 *            回调
	 * @throws Exception
	 */
	public void getOfflieAttach(String id, String sufx, boolean hasProgress, final CallBack call) throws Exception {
		OfflineAttach attach = getOfflineAttachFromDatabase(id);
		if (attach != null) {
			if (TextUtils.isEmpty(attach.getFilePath()) || !FileUtil.checkFilePathExists(attach.getFilePath())) {
				getOfflineAttachFileFromInternet(attach, hasProgress, call);
			} else {
				call.callFile(attach);
			}
		} else {
			attach = new OfflineAttach();
			attach.setComUploadId(id);
			attach.setFileName("_" + sufx);
			attach.setIsUploaded(WhetherEnum.YES.getValue());
			getOfflineAttachFileFromInternet(attach, true, call);
		}
	}

	/**
	 * 批量在线下载离线附件
	 * 
	 * @param idList
	 *            离线附件id集合
	 * @param sufxList
	 *            离线附件后缀名集合
	 * @param hasProgress
	 *            是否要显示进度条
	 * @param call
	 *            回调
	 */
	public void batchDownLoadOfflineAttach(final List<String> idList, List<String> sufxList, boolean hasProgress,
			CallBack call) {
		if (idList == null || idList.isEmpty() || sufxList == null || sufxList.isEmpty()
				|| idList.size() != sufxList.size()) {
			return;
		}
		CallBack mCall = call;
		if (mCall == null) {
			final List<String> faildidList = new ArrayList<String>();
			ListUtil.copyList(faildidList, idList);
			final HashMap<String, Integer> markmap = new HashMap<String, Integer>();
			final CallBack _call = new CallBack() {
				@Override
				public void callFile(OfflineAttach attach) {
					faildidList.remove(attach.getComUploadId());
					if (!markmap.containsKey("mark")) {
						markmap.put("mark", 1);
					} else {
						int count = markmap.get("mark") + 1;
						markmap.put("mark", count);
					}
					if (markmap.get("mark") >= idList.size()) {
						// 下载结束了
						if (faildidList.isEmpty()) {
							AsynToastUtil.getInstance().showToast("附件已经全部下载完毕");
						} else {
							AsynToastUtil.getInstance().showToast("抱歉，共有" + faildidList.size() + "个附件数据下载失败，请检查网络是否正常");
						}
					}
				}

				@Override
				public void callError(int code) {
					if (!markmap.containsKey("mark")) {
						markmap.put("mark", 1);
					} else {
						int count = markmap.get("mark") + 1;
						markmap.put("mark", count);
					}
					if (markmap.get("mark") >= idList.size()) {
						// 下载结束了
						if (faildidList.isEmpty()) {
							AsynToastUtil.getInstance().showToast("附件已经全部下载完毕");
						} else {
							AsynToastUtil.getInstance().showToast("抱歉，共有" + faildidList.size() + "个附件数据下载失败，请检查网络是否正常");
						}
					}
				}

				@Override
				public void callAttachs(int code, List<OfflineAttach> list, boolean hasfile) {

				}
			};
			mCall = _call;
		}
		for (int i = 0; i < idList.size(); i++) {
			OfflineAttach attach = new OfflineAttach();
			attach.setComUploadId(idList.get(i));
			attach.setFileName("_" + sufxList.get(i));
			attach.setIsUploaded(WhetherEnum.YES.getValue());
			getOfflineAttachFileFromInternet(attach, true, hasProgress, mCall, true, idList.size());
		}
	}

	/**
	 * 批量在线下载离线附件
	 * 
	 * @param idList
	 *            离线附件id集合
	 * @param sufx
	 *            离线附件后缀名
	 * @param hasProgress
	 *            是否要显示进度条
	 * @param call
	 *            回调
	 */
	public void batchDownLoadOfflineAttach(List<String> idList, String sufx, boolean hasProgress, CallBack call) {
		if (idList == null || idList.isEmpty() || TextUtils.isEmpty(sufx)) {
			return;
		}
		ArrayList<String> sufxList = new ArrayList<String>();
		for (int i = 0; i < idList.size(); i++) {
			sufxList.add(sufx);
		}
		batchDownLoadOfflineAttach(idList, sufxList, hasProgress, call);
	}

}
