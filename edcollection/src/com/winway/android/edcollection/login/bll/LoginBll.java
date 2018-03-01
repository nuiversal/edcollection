package com.winway.android.edcollection.login.bll;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.pointGeometry;
import com.winway.android.edcollection.base.util.AnnotationUtil;
import com.winway.android.edcollection.datacenter.entity.edp.OrgResult1;
import com.winway.android.edcollection.login.entity.EdpOrgInfoEntity;
import com.winway.android.edcollection.login.entity.EdpOrgListResult;
import com.winway.android.edcollection.login.entity.EdpParamInfoEntity;
import com.winway.android.edcollection.login.entity.EdpUserInfoEntity;
import com.winway.android.edcollection.login.entity.LoginResult;
import com.winway.android.edcollection.login.utils.PasswordUtil;
import com.winway.android.edcollection.project.activity.ProjectingActivity;
import com.winway.android.edcollection.project.dto.EmProjectEntityDto;
import com.winway.android.edcollection.project.dto.GetProjectListResult;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EmProjectEntity;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.ewidgets.net.NetManager;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.network.HttpUtils;
import com.winway.android.network.HttpUtils.CallBack;
import com.winway.android.util.FileUtil;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.ProgressUtils;
import com.winway.android.util.SharedPreferencesUtils;
import com.winway.android.util.ToastUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

/**
 * 登录相关业务逻辑
 * 
 * @author zgq
 *
 */
public class LoginBll extends BaseBll<EdpUserInfoEntity> {

	public LoginBll(Context context, String dbUrl) {
		super(context, dbUrl);
	}

	/**
	 * 执行登陆
	 * 
	 * @param loginEntity
	 * @param mActivity
	 */
	@SuppressLint("HandlerLeak")
	public void login(final EdpUserInfoEntity userInfoEntity, final Activity mActivity) {
		ProgressUtils.getInstance().show("登录中...", false, mActivity);
		String loginUrl = GlobalEntry.loginServerUrl + "login?logicSysNo=WCLOUD_MANAGER&loginName="
				+ userInfoEntity.getUserNo() + "&pwd=" + userInfoEntity.getUserPwd() + "&client=2";
		final int flagSuccess = 1;// 成功标识
		final int flagFail = 2;// 失败标识
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case flagSuccess: {
					// 登录成功
					LoginResult loginResult = (LoginResult) msg.obj;
					loginResult.setPassWord(userInfoEntity.getUserPwd());
					GlobalEntry.loginResult = loginResult;// 保存登录结果
					ToastUtil.show(mActivity, "登录成功", 200);
					ProgressUtils.getInstance().dismiss();
					getGlobalDataOnLine(mActivity, loginResult);
					// 保存用户登账号与密码
					SharedPreferencesUtils.put(mActivity, "#loginname#", userInfoEntity.getUserNo());
					// SharedPreferencesUtils.put(mActivity, "#password#",
					// userInfoEntity.getUserPwd());
					break;
				}
				case flagFail: {
					ProgressUtils.getInstance().dismiss();
					ToastUtil.show(mActivity, "登录失败", 200);
					break;
				}

				default:
					break;
				}
			}
		};
		// 执行登录
		HttpUtils.doGet(loginUrl, new CallBack() {

			public void onRequestComplete(String result) {
				// TODO Auto-generated method stub
				ProgressUtils.getInstance().dismiss();
				Gson gson = GsonUtils.build();
				Type listType = new TypeToken<LoginResult>() {
				}.getType();
				LoginResult loginResult = gson.fromJson(result, listType);
				Message message = new Message();
				if (loginResult.getCode() >= 0) {
					message.what = flagSuccess;
					message.obj = loginResult;
					handler.sendMessage(message);
				} else {
					message.what = flagFail;
					handler.sendMessage(message);
				}
			}

			@Override
			public void onError(Exception exception) {
				Message message = new Message();
				message.what = flagFail;
				handler.sendMessage(message);
			}

		});
	}

	/**
	 * 离线登录
	 */
	public void offlineLogin(EdpUserInfoEntity entity, final Activity mActivity) {
		// 获取用户名
		String loginName = entity.getUserNo();
		// 获取密码（加密后）
		String userPwd = PasswordUtil.getEDPHashedPasswordBase64(entity.getUserPwd());
		try {
			// 根据条件去数据库查找所有符合此条件的数据
			String expr = "user_no='" + loginName + "'and user_pwd='" + userPwd + "' ";
			List<EdpUserInfoEntity> userList = this.queryByExpr(EdpUserInfoEntity.class, expr);
			if (userList != null && userList.size() > 0) {
				// 保存登录信息
				EdpUserInfoEntity userInfoEntity = userList.get(0);
				LoginResult loginResult = new LoginResult();
				loginResult.setLoginname(userInfoEntity.getUserNo());
				loginResult.setUsername(userInfoEntity.getUserName());
				loginResult.setPassWord(entity.getUserPwd());// 明文密码
				loginResult.setUid(userInfoEntity.getUserId());// 用户id
				// 根据用户表中的机构标识获取机构信息
				String dbUrl = FileUtil.AppRootPath + "/db/common/global.db";
				EdpBll edpBll = new EdpBll(mActivity, dbUrl);
				EdpOrgInfoEntity edpOrgInfoEntity = edpBll.getOrgInfoByOrgNo(userInfoEntity.getOrgNo());
				loginResult.setOrgid(userInfoEntity.getOrgNo());
				loginResult.setOrgname(edpOrgInfoEntity.getOrgName());
				loginResult.setOrgid(edpOrgInfoEntity.getOrgId());
				loginResult.setLogicSysNo(edpOrgInfoEntity.getLogicSysNo());
				// 获取部门信息
				EdpParamInfoEntity edpParamInfoEntity = edpBll.getParamInfoByParamId(userInfoEntity.getDeptNo());
				loginResult.setDeptid(userInfoEntity.getDeptNo());
				if (edpParamInfoEntity != null) {
					loginResult.setDeptname(edpParamInfoEntity.getParamName());
				} else {
					loginResult.setDeptname(null);
				}
				// 保存到内存
				GlobalEntry.loginResult = loginResult;
				// 保存用户登账号与密码
				SharedPreferencesUtils.put(mActivity, "#loginname#", userInfoEntity.getUserNo());
				// SharedPreferencesUtils.put(mActivity, "#password#",
				// userInfoEntity.getUserPwd());
				// 跳转到选择项目界面
				mActivity.startActivity(new Intent(mActivity, ProjectingActivity.class));
				mActivity.finish();
				ToastUtil.show(mActivity, "登录成功", 200);
			} else {
				ToastUtil.show(mActivity, "登录失败", 200);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ToastUtil.show(mActivity, "登录异常", 200);
			e.printStackTrace();
		}
	}

	/**
	 * 检查是否存在本地用户
	 * 
	 * @param loginName
	 * @param passWord
	 * @return true表示本地存在，false表示本地不存在
	 */
	public boolean checkIsExistOfflineUser(String loginName, String passWord) {
		String pwd = PasswordUtil.getEDPHashedPasswordBase64(passWord);
		String expr = "user_no='" + loginName + "' and user_pwd='" + pwd + "' ";
		List<EdpUserInfoEntity> userList = this.queryByExpr(EdpUserInfoEntity.class, expr);
		if (userList != null && userList.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取全局数据
	 * 
	 * @param mActivity
	 * @param loginResult
	 */
	public void getGlobalDataOnLine(Activity mActivity, LoginResult loginResult) {
		getOrgUsersDataOnline(loginResult, mActivity);
		//getProjectDataOnline(mActivity);
		mActivity.startActivity(new Intent(mActivity, ProjectingActivity.class));
		ProgressUtils.getInstance().dismiss();
		mActivity.finish();
	}

	/**
	 * 获取机构内用户
	 */
	public void getOrgUsersDataOnline(LoginResult loginResult, Activity mActivity) {
		if (loginResult != null) {
			// 1机构信息
			getOrgListDataAndSave(loginResult, mActivity);
		}
	}

	/**
	 * 保存登录用户到本地库
	 * 
	 * @param loginResult
	 */
	private void saveUserInfo(LoginResult loginResult, Map<String, String> orgId2orgNo) {
		// 2用户信息
		EdpUserInfoEntity edpUserInfoEntity = new EdpUserInfoEntity();
		edpUserInfoEntity.setUserId(loginResult.getUid());
		edpUserInfoEntity.setUserName(loginResult.getUsername());
		edpUserInfoEntity.setUserNo(loginResult.getLoginname());
		String pwd = PasswordUtil.getEDPHashedPasswordBase64(loginResult.getPassWord());
		edpUserInfoEntity.setUserPwd(pwd);
		if (orgId2orgNo != null) {
			String orgNo=orgId2orgNo.get(loginResult.getOrgid());
			if (orgNo!=null) {
				edpUserInfoEntity.setOrgNo(orgNo);
			}
		}
		edpUserInfoEntity.setLogicSysNo(loginResult.getLogicSysNo());
		edpUserInfoEntity.setDeptNo(loginResult.getDeptid());// 把no列当id列使用
		saveOrUpdate(edpUserInfoEntity);
	}

	/**
	 * 获取机构列表数据
	 * 
	 * @param loginResult
	 * @param mActivity
	 */
	private void getOrgListDataAndSave(final LoginResult loginResult, final Activity mActivity) {
		final int flagSuccess = 1;// 成功标识
		final int flagFail = 2;// 失败标识
		final Handler uiHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case flagSuccess: {
					EdpOrgListResult edpOrgListResult = (EdpOrgListResult) msg.obj;
					Map<String, String> orgId2orgNo = new HashMap<String, String>();// 机构id->机构标识
					for (OrgResult1 org : edpOrgListResult.getRows()) {
						orgId2orgNo.put(org.getOrgid(), org.getOrgno());// 保存映射
						EdpOrgInfoEntity edpOrgInfoEntity = new EdpOrgInfoEntity();
						edpOrgInfoEntity.setOrgId(org.getOrgid());
						edpOrgInfoEntity.setOrgName(org.getOrgname());
						edpOrgInfoEntity.setCustomNo(org.getCustomno());
						edpOrgInfoEntity.setLogicSysNo(loginResult.getLogicSysNo());
						edpOrgInfoEntity.setMapCenter(org.getMapCenter());
						edpOrgInfoEntity.setOrgNo(org.getOrgno());
						edpOrgInfoEntity.setUpNo(org.getParentOrgNo());
						saveOrUpdate(edpOrgInfoEntity);
					}
					// 保存用户信息
					saveUserInfo(loginResult, orgId2orgNo);
					break;
				}
				case flagFail: {
					ToastUtil.show(mActivity, "获取机构列表失败", 100);
					break;
				}
				default:
					break;
				}

			};
		};
		String url = GlobalEntry.loginServerUrl + "edp?action=getorgtree&credit=" + loginResult.getCredit() + "&orgid="
				+ loginResult.getOrgid() + "&logic_sys_no=" + loginResult.getLogicSysNo() + "&style=1";
		HttpUtils.doGet(url, new CallBack() {

			@Override
			public void onRequestComplete(String result) {
				// TODO Auto-generated method stub
				Message message = new Message();
				Gson gson = GsonUtils.build();
				Type typeOfT = new TypeToken<EdpOrgListResult>() {
				}.getType();
				EdpOrgListResult edpOrgListResult = gson.fromJson(result, typeOfT);
				if (edpOrgListResult.getCode() >= 0) {
					uiHandler.sendMessage(message);
					message.what = flagSuccess;
					message.obj = edpOrgListResult;
				} else {
					message.what = flagFail;
					uiHandler.sendMessage(message);
				}
			}

			@Override
			public void onError(Exception exception) {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = flagFail;
				uiHandler.sendMessage(message);
			}
		});

	}

	/**
	 * 获取工程作业信息
	 */
	public void getProjectDataOnline(final Activity mActivity) {

		final int flagSuccess = 1;// 成功标识
		final int flagFail = 2;// 失败标识
		final Handler uiHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case flagSuccess: {
					mActivity.startActivity(new Intent(mActivity, ProjectingActivity.class));
					ProgressUtils.getInstance().dismiss();
					mActivity.finish();
					break;
				}
				case flagFail: {
					ToastUtil.show(mActivity, msg.obj.toString(), 200);
					break;
				}

				default:
					break;
				}
			}
		};

		String url = GlobalEntry.dataServerUrl + "?action=em_getprojects&hastasks=1&taskstatus=1&credit="
				+ GlobalEntry.loginResult.getCredit()+"&uid="+GlobalEntry.loginResult.getUid();
		HttpUtils.doGet(url, new CallBack() {

			@Override
			public void onRequestComplete(String result) {
				// TODO Auto-generated method stub
				Gson gson = GsonUtils.build();
				Type projectLists = new TypeToken<GetProjectListResult>() {
				}.getType();
				GetProjectListResult basePro = gson.fromJson(result, projectLists);
				if (basePro.getCode() < 0) {
					return;
				}
				EmProjectEntity entity;
				List<EmProjectEntityDto> lists = basePro.getList();
				// 循环取出工程作业信息
				for (EmProjectEntity list : lists) {
					entity = new EmProjectEntity();
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
					saveOrUpdate(entity);// 保存到本地
				}
				Message msg = new Message();
				msg.what = flagSuccess;
				uiHandler.sendMessage(msg);
			}

			@Override
			public void onError(Exception exception) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = flagFail;
				msg.obj = exception.getMessage();
				uiHandler.sendMessage(msg);
			}
		});
	}

	/**
	 * 登录
	 * 
	 * @return
	 */
	public String login() {
		// 执行登录
		String loginUrl = GlobalEntry.loginServerUrl + "login?logicSysNo=WCLOUD_MANAGER&loginName="
				+ GlobalEntry.loginResult.getLoginname() + "&pwd=" + GlobalEntry.loginResult.getPassWord()
				+ "&client=2";
		return HttpUtils.doGet(loginUrl);
	}

	/**
	 * 解析登录
	 * 
	 * @param data
	 * @return
	 */
	public LoginResult parseLogin(String data) {
		Gson gson = GsonUtils.build();
		Type listType = new TypeToken<LoginResult>() {
		}.getType();
		LoginResult loginResult = gson.fromJson(data, listType);
		return loginResult;
	}

	/**
	 * 在线登录并解析数据
	 * 
	 * @return LoginResult或者null
	 */
	public LoginResult loginAndParse() {
		String loginResultStr = this.login();
		if (loginResultStr == null) {
			return null;
		}
		return this.parseLogin(loginResultStr);
	}

	/**
	 * 获取机构列表
	 * 
	 * @param loginResult
	 * @return
	 */
	public EdpOrgListResult getOrgListDatas(LoginResult loginResult) {
		try {
			String url = GlobalEntry.loginServerUrl + "edp?action=getorgtree&credit=" + loginResult.getCredit()
					+ "&orgid=" + loginResult.getOrgid() + "&logic_sys_no=" + loginResult.getLogicSysNo() + "&style=1";
			String jsonData = HttpUtils.doGet(url);
			if (jsonData != null) {
				Gson gson = GsonUtils.build();
				Type typeOfT = new TypeToken<EdpOrgListResult>() {
				}.getType();
				EdpOrgListResult edpOrgListResult = gson.fromJson(jsonData, typeOfT);
				return edpOrgListResult;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 更新机构用户信息
	 * 
	 * @param loginResult
	 */
	public void updateOrgUserInfo(LoginResult loginResult) {
		EdpOrgListResult edpOrgListResult = getOrgListDatas(loginResult);
		Map<String, String> orgId2orgNo = new HashMap<String, String>();// 机构id->机构标识
		if (edpOrgListResult == null || edpOrgListResult.getRows() == null) {
			return;
		}
		for (OrgResult1 org : edpOrgListResult.getRows()) {
			orgId2orgNo.put(org.getOrgid(), org.getOrgno());// 保存映射
			EdpOrgInfoEntity edpOrgInfoEntity = new EdpOrgInfoEntity();
			edpOrgInfoEntity.setOrgId(org.getOrgid());
			edpOrgInfoEntity.setOrgName(org.getOrgname());
			edpOrgInfoEntity.setCustomNo(org.getCustomno());
			edpOrgInfoEntity.setLogicSysNo(loginResult.getLogicSysNo());
			edpOrgInfoEntity.setMapCenter(org.getMapCenter());
			edpOrgInfoEntity.setOrgNo(org.getOrgno());
			edpOrgInfoEntity.setUpNo(org.getParentOrgNo());
			saveOrUpdate(edpOrgInfoEntity);
		}
		// 保存用户信息
		saveUserInfo(loginResult, orgId2orgNo);
	}
}
