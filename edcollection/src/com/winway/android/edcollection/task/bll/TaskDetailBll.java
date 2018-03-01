package com.winway.android.edcollection.task.bll;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.dto.MessageBase;
import com.winway.android.edcollection.login.bll.LoginBll;
import com.winway.android.edcollection.login.entity.LoginResult;
import com.winway.android.edcollection.project.dto.SyncDataResult;
import com.winway.android.edcollection.project.entity.EmTaskItemEntity;
import com.winway.android.edcollection.project.vo.EmTaskItemEntityVo;
import com.winway.android.network.HttpUtils;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.NetWorkUtils;
import com.winway.android.util.ProgressUtils;
import com.winway.android.util.ToastUtil;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 任务业务处理
 * 
 * @author zgq
 *
 */
public class TaskDetailBll extends BaseBll<EmTaskItemEntity> {

	public TaskDetailBll(Context context, String dbUrl) {
		super(context, dbUrl);
	}

	public List<EmTaskItemEntity> getEmTaskItemEntityByTaskID(String taskID) {

		return null;
	}

	/**
	 * 获取详情任务列表
	 * 
	 * @param taskId
	 * @return
	 */
	public ArrayList<EmTaskItemEntityVo> getTaskDetailList(String taskId) {
		// TODO Auto-generated method stub
		ArrayList<EmTaskItemEntityVo> list = new ArrayList<EmTaskItemEntityVo>();
		for (int i = 0; i < 6; i++) {
			EmTaskItemEntityVo emTasksEntity = new EmTaskItemEntityVo();
			emTasksEntity.setDetailName("hahha" + i);
			emTasksEntity.setDeviceType(i);
			emTasksEntity.setEmTaskItemId(i + "aaaaa");
			emTasksEntity.setEmTasksId(i + "qwer");
			emTasksEntity.setObjId(i + "fdsfsfsd");
			list.add(emTasksEntity);
		}

		String exp = "";

		return list;
	}

	/**
	 * 上传任务项列表
	 * 
	 * @param context
	 * @return
	 */
	public void syncTaskitems(final Context context) {
		final int success_flag = 0;
		final int fail_flag = 1;
		if (NetWorkUtils.isConnected(context)==false) {
			ToastUtil.show(context, "网络异常", 200);
			return;
		}
		final Handler uiHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				String result = (String) msg.obj;
				switch (msg.what) {
				case fail_flag: {
					ToastUtil.show(context, result, 200);
					break;
				}
				case success_flag: {
					ToastUtil.show(context, result, 200);
					break;
				}

				default:
					break;
				}
				ProgressUtils.getInstance().dismiss();

			}
		};

		ProgressUtils.getInstance().show("上传中...", false, context);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				LoginBll loginBll = new LoginBll(context, GlobalEntry.globalDbUrl);
				LoginResult loginResult = loginBll.loginAndParse();
				if (loginResult == null) {
					message.what = fail_flag;
					message.obj = "在线登录失败！";
					uiHandler.sendMessage(message);
					return;
				}
				GlobalEntry.loginResult.setCredit(loginResult.getCredit());
				String url = GlobalEntry.dataServerUrl + "?action=em_synctaskitems&userid="
						+ GlobalEntry.loginResult.getUid() + "&credit=" + GlobalEntry.loginResult.getCredit();
				Map<String, String> params = new HashMap<>();
				List<EmTaskItemEntity> taskItems = getWaitingTaskItems();
				if (taskItems.size() < 1) {
					message.what = fail_flag;
					message.obj = "没有需要上传的数据！";
					uiHandler.sendMessage(message);
					return;
				}
				final Gson gson = GsonUtils.build();
				Type syscDataType = new TypeToken<List<EmTaskItemEntity>>() {
				}.getType();
				String data = gson.toJson(taskItems, syscDataType);
				params.put("data", data);
				String messageResult = HttpUtils.doPost(url, params);
				Type messageBaseType = new TypeToken<MessageBase>() {
				}.getType();
				MessageBase messageBase = gson.fromJson(messageResult, messageBaseType);
				if (messageBase != null && messageBase.getCode() == 0) {
					message.what = success_flag;
					message.obj = "上传成功！";
					uiHandler.sendMessage(message);
					return;
				} else {
					message.what = fail_flag;
					message.obj = "上传失败！";
					uiHandler.sendMessage(message);
					return;
				}

			}
		}).start();

	}

	/**
	 * 获取待上传的任务项
	 * 
	 * @return
	 */
	private List<EmTaskItemEntity> getWaitingTaskItems() {
		List<EmTaskItemEntity> datas = new ArrayList<>();
		String expr = "STATUS=1";
		List<EmTaskItemEntity> list = this.queryByExpr2(EmTaskItemEntity.class, expr);
		if (list != null && list.size() > 0) {
			datas.addAll(list);
		}
		return datas;
	}

}
