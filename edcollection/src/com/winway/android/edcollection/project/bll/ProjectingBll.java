package com.winway.android.edcollection.project.bll;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.TableUtils;
import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.dbVersion.utils.DbVersionUtils;
import com.winway.android.edcollection.base.dbVersion.utils.DbVersionUtils.DbUpgrade;
import com.winway.android.edcollection.login.bll.LoginBll;
import com.winway.android.edcollection.login.entity.LoginResult;
import com.winway.android.edcollection.main.activity.MainActivity;
import com.winway.android.edcollection.project.dto.EmProjectEntityDto;
import com.winway.android.edcollection.project.dto.GetProjectListResult;
import com.winway.android.edcollection.project.entity.EmMembersEntity;
import com.winway.android.edcollection.project.entity.EmProjectEntity;
import com.winway.android.edcollection.project.entity.EmTasksEntity;
import com.winway.android.ewidgets.download.DownloadTask;
import com.winway.android.ewidgets.download.DownloadTask.DownloadTaskCallBack;
import com.winway.android.network.HttpUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.LogUtil;
import com.winway.android.util.NetWorkUtils;
import com.winway.android.util.ProgressUtils;
import com.winway.android.util.ToastUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

/**
 * 项目选择业务逻辑
 * 
 * @author zgq
 *
 */
public class ProjectingBll extends BaseBll<EmProjectEntity> {

	public ProjectingBll(Context context, String dbUrl) {
		// TODO Auto-generated constructor stub
		super(context, dbUrl);
		try {
			this.getDbUtils().createTableIfNotExist(EmTasksEntity.class);
			this.getDbUtils().createTableIfNotExist(EmMembersEntity.class);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据机构id获取项目列表信息
	 * 
	 * @param mActivity
	 * @param orgid
	 * @return
	 */
	public ArrayList<EmProjectEntityDto> getProjectsByOrgId(final Activity mActivity, String orgid) {
		ArrayList<EmProjectEntityDto> result = new ArrayList<EmProjectEntityDto>();
		try {
			String expr = " ORG_ID='" + orgid + "'";
			List<EmProjectEntityDto> prjsList = this.queryByExpr2(EmProjectEntityDto.class, expr);
			if (prjsList != null && prjsList.size() > 0) {
				// 查找对应的任务
				for (int i = 0; i < prjsList.size(); i++) {
					Selector selector = Selector.from(EmTasksEntity.class);
					WhereBuilder whereBuilder = WhereBuilder.b();
					whereBuilder.expr(" PRJID='" + prjsList.get(i).getEmProjectId() + "' and EXECUTOR='"
							+ GlobalEntry.loginResult.getUid() + "'");
					selector.where(whereBuilder);
					List<EmTasksEntity> tasks = dbUtils.findAll(selector);
					prjsList.get(i).setTasks(tasks);
				}
				result.addAll(prjsList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据用户id获取项目列表信息
	 * 
	 * @param mActivity
	 * @param orgid
	 * @return
	 */
	public ArrayList<EmProjectEntityDto> getProjectsByUid(final Activity mActivity, String uid) {
		ArrayList<EmProjectEntityDto> result = new ArrayList<EmProjectEntityDto>();
		try {
			String expr = " USER_ID='" + uid + "'";
			List<EmMembersEntity> emMembers = this.queryByExpr2(EmMembersEntity.class, expr);
			if (emMembers != null && emMembers.size() > 0) {
				for (int i = 0; i < emMembers.size(); i++) {
					EmMembersEntity emMembersEntity = emMembers.get(i);
					EmProjectEntityDto emProjectEntityDto = this.findById(EmProjectEntityDto.class,
							emMembersEntity.getEmProjectId());
					if (emProjectEntityDto == null) {
						continue;
					}
					result.add(emProjectEntityDto);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据项目编号下载离线db
	 * 
	 * @param emProjectEntity
	 * @param mActivity
	 */
	public void downloadProjectDb(final EmProjectEntity emProjectEntity, final Activity mActivity) {
		if (!NetWorkUtils.isConnected(mActivity)) {
			ToastUtil.show(mActivity, "无网络连接", 100);
			return;
		}
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				ProgressUtils.getInstance().dismiss();
				LoginResult loginResult = (LoginResult) msg.obj;
				if (loginResult != null) {
					GlobalEntry.loginResult.setCredit(loginResult.getCredit());
				} else {
					ToastUtil.show(mActivity, "获取在线凭据失败", 100);
					return;
				}
				// String url = GlobalEntry.dataServerUrl +
				// "?action=em_downloaddb&credit="
				// + GlobalEntry.loginResult.getCredit() + "&prj=" +
				// emProjectEntity.getEmProjectId();
				String url = GlobalEntry.dataServerUrl + "?action=em_downloaddb_byorgid&credit="
						+ GlobalEntry.loginResult.getCredit() + "&orgid=" + emProjectEntity.getOrgId();
				File targetFile = new File(FileUtil.AppRootPath + "/db/project/" + emProjectEntity.getPrjNo() + ".db");
				DownloadTask downloadTask = new DownloadTask(mActivity, targetFile, "下载中...",
						new DownloadTaskCallBack() {

							@Override
							public void success(String result) {//下载成功
								// TODO Auto-generated method stub
								// 执行数据库版本升级
								DbVersionUtils.getInstance().handleDbUpgrade(mActivity, new DbUpgrade() {

									@Override
									public void success(String result) {
										// TODO Auto-generated method stub
										// 本地存在
										ToastUtil.show(mActivity, "软件升级成功", 1000);
										Intent intent = new Intent(mActivity, MainActivity.class);
										intent.putExtra("project", emProjectEntity);
										mActivity.startActivity(intent);
										mActivity.finish();
									}

									@Override
									public void fail(String result) {
										// TODO Auto-generated method stub
										ToastUtil.show(mActivity, "软件升级失败,请联系开发人员：" + result, 3000);
										// 删除没有版本信息的db文件
										if (isExistProjectDb(emProjectEntity.getPrjNo())) {
											FileUtil.deleteFileWithPath(
													FileUtil.AppRootPath + "/db/project/" + emProjectEntity.getPrjNo() + ".db");
										}
									}
								});

							}

							@Override
							public void fail(String result) {
								// TODO Auto-generated method stub
								ToastUtil.show(mActivity, "下载失败：" + result, 300);
								// 删除没下载完的db文件
								if (isExistProjectDb(emProjectEntity.getPrjNo())) {
									FileUtil.deleteFileWithPath(
											FileUtil.AppRootPath + "/db/project/" + emProjectEntity.getPrjNo() + ".db");
								}
							}
						});
				downloadTask.execute(url);
			}
		};
		ProgressUtils.getInstance().show("加载中...", true, mActivity);
		final LoginBll loginBll = new LoginBll(mActivity, GlobalEntry.globalDbUrl);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				LoginResult loginResult = loginBll.loginAndParse();
				Message message = new Message();
				message.obj = loginResult;
				handler.sendMessage(message);

			}
		}).start();

	}

	/**
	 * 是否存在本地离线的项目db文件
	 * 
	 * @param projectNo
	 * @return true表示已经存在，false表示不存在
	 */
	public boolean isExistProjectDb(String projectNo) {
		String prjDbUrl = FileUtil.AppRootPath + "/db/project/" + projectNo + ".db";
		if (FileUtil.checkFilePathExists(prjDbUrl)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取项目列表
	 * 
	 * @return
	 */
	public String getProjectListOnline() {
		String url = GlobalEntry.dataServerUrl + "?action=em_getprojects&hastasks=1&taskstatus=1&credit="
				+ GlobalEntry.loginResult.getCredit() + "&uid=" + GlobalEntry.loginResult.getUid() + "&client=2";
		return HttpUtils.doGet(url);
	}

	/**
	 * 解析项目列表返回
	 * 
	 * @param data
	 * @return
	 */
	public List<EmProjectEntityDto> parseProjectList(String data) {
		Gson gson = GsonUtils.build();
		Type projectLists = new TypeToken<GetProjectListResult>() {
		}.getType();
		GetProjectListResult basePro = gson.fromJson(data, projectLists);
		if (basePro.getCode() < 0) {
			return null;
		}
		return basePro.getList();
	}

	/**
	 * 解析工程人员关联列表返回
	 * 
	 * @param data
	 * @return
	 */
	public List<EmMembersEntity> parseMembersList(String data) {
		Gson gson = GsonUtils.build();
		Type projectLists = new TypeToken<GetProjectListResult>() {
		}.getType();
		GetProjectListResult basePro = gson.fromJson(data, projectLists);
		if (basePro.getCode() < 0) {
			return null;
		}
		return basePro.getMembers();
	}

	/**
	 * 保存项目列表信息
	 * 
	 * @param lists
	 *            工程列表
	 */
	public void savePrjList(List<EmProjectEntityDto> lists) {
		if (lists != null && !lists.isEmpty()) {
			// 获取新的不为空，则清空任务表，重新入库
			try {
				getDbUtils().execNonQuery("delete from " + TableUtils.getTableName(EmTasksEntity.class));
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
		// 循环取出工程作业信息
		for (EmProjectEntityDto list : lists) {
			EmProjectEntityDto entity = new EmProjectEntityDto();
			entity.setEmProjectId(list.getEmProjectId());
			entity.setPrjNo(list.getPrjNo());
			entity.setPrjName(list.getPrjName());
			entity.setRegion(list.getRegion());
			entity.setRegionNo(list.getRegionNo());
			entity.setPrjDesc(list.getPrjDesc());
			entity.setBeginTime(list.getBeginTime());
			entity.setAcceptTime(list.getAcceptTime());
			entity.setCompleteTime(list.getCompleteTime());
			entity.setLeader(list.getLeader());
			entity.setStatus(list.getStatus());
			entity.setIsSubmit(list.getIsSubmit());
			entity.setOrgId(list.getOrgId());
			entity.setCustomNo(list.getCustomNo());
			entity.setShareKey(list.getShareKey());
			entity.setRemarks(list.getRemarks());
			this.saveAll(list.getTasks());// 保存任务
			this.saveOrUpdate(entity);// 保存到本地
		}
	}

	/**
	 * 保存工程成员关联表信息(注意：当主键是Integer类型时，xutil的saveorupdate有问题)
	 * 
	 * @param emMembers
	 */
	public void saveMembers(List<EmMembersEntity> emMembers) {
		if (emMembers != null && emMembers.size() > 0) {
			for (EmMembersEntity emMembersEntity : emMembers) {
				String prjId = emMembersEntity.getEmProjectId();
				String uid = emMembersEntity.getUserId();
				String expr = "EM_PROJECT_ID='" + prjId + "' and USER_ID='" + uid + "'";
				List<EmMembersEntity> emMembersEntitiesTmp = this.queryByExpr2(EmMembersEntity.class, expr);
				if (emMembersEntitiesTmp == null || emMembersEntitiesTmp.size() < 1) {// 不存在
					this.save(emMembersEntity);
				} else {
					this.update(emMembersEntity);
				}

			}

		}
	}

}
