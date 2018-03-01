package com.winway.android.edcollection.task.controll;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.adding.util.TreeMapUtil;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.base.view.BaseFragmentControll;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.login.entity.LoginResult;
import com.winway.android.edcollection.project.entity.EmTaskItemEntity;
import com.winway.android.edcollection.project.entity.EmTasksEntity;
import com.winway.android.edcollection.task.activity.EmTaskDetailActivity;
import com.winway.android.edcollection.task.activity.TaskDetailActivity;
import com.winway.android.edcollection.task.adapter.TaskListAdapter;
import com.winway.android.edcollection.task.entity.TaskDetailItemData;
import com.winway.android.edcollection.task.entity.TaskDetailResult;
import com.winway.android.edcollection.task.entity.TaskResult;
import com.winway.android.edcollection.task.viewholder.TaskListViewHolder;
import com.winway.android.ewidgets.net.NetManager;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.map.entity.MapCache;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.BroadcastReceiverUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ListUtil;
import com.winway.android.util.ProgressUtils;
import com.winway.android.util.SharedPreferencesUtils;
import com.winway.android.util.ToastUtils;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * 任务列表
 * 
 * @author zgq
 *
 */
public class TaskListControll extends BaseFragmentControll<TaskListViewHolder> {
	private BaseDBUtil dbUtil;
	private BaseService netService;
	static final String LAST_GET_TASK_TIME = "LAST_GET_TASK_TIME";
	private ArrayList<EmTasksEntity> taskList;
	private TaskListAdapter taskListAdapter;

	@Override
	public void init() {
		netService = NetManager.getLineService();
		String dbUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		dbUtil = new BaseDBUtil(mActivity, new File(dbUrl));
		initDatas();
		initEvents();
	}

	// 获取任务详情列表并保存到数据库中
	void getDetailFromNetWork(String taskID) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", "em_gettaskitems");
		params.put("credit", GlobalEntry.loginResult.getCredit());
		params.put("taskid", taskID);
		BaseService netService = NetManager.getLineService();
		try {
			netService.getRequest(GlobalEntry.dataServerUrl, params, null, TaskDetailResult.class,
					new BaseService.RequestCallBack<TaskDetailResult>() {
						@Override
						public void error(int code,BaseService.WayFrom from) {
							// ToastUtils.show(mActivity, "未知错误");
						}

						@Override
						public void callBack(TaskDetailResult request,BaseService.WayFrom from) {
							if (null == request || request.getList() == null || request.getList().isEmpty()) {
								return;
							}
							ArrayList<EmTaskItemEntity> list = new ArrayList<EmTaskItemEntity>();
							for (TaskDetailItemData data : request.getList()) {
								if (null == data.getItem()) {
									continue;
								}
								list.add(data.getItem());
							}
							try {
								dbUtil.saveOrUpdate(list);
							} catch (DbException e) {
								e.printStackTrace();
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 更新任务数据
	private void updateTaskData(String lastTime) {
		String url = GlobalEntry.dataServerUrl + "";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", "em_gettasks");
		params.put("credit", GlobalEntry.loginResult.getCredit());
		params.put("prj", GlobalEntry.currentProject.getEmProjectId());
		params.put("createtime", lastTime);
		params.put("executor", GlobalEntry.loginResult.getUid());
		try {
			netService.getRequest(url, params, null, TaskResult.class, new BaseService.RequestCallBack<TaskResult>() {
				@Override
				public void error(int code,BaseService.WayFrom from) {
					ToastUtils.show(mActivity, "获取最新数据失败，未知原因");
					ProgressUtils.getInstance().dismiss();
				}

				@Override
				public void callBack(TaskResult request,BaseService.WayFrom from) {
					List<EmTasksEntity> list = request.getList();
					if (null == list || list.isEmpty()) {
						ProgressUtils.getInstance().dismiss();
						return;
					}
					// 保存进入数据库
					try {
						dbUtil.saveOrUpdate(list);
						/*SharedPreferencesUtils.put(mActivity, GlobalEntry.currentProject.getEmProjectId() + LAST_GET_TASK_TIME,
								DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));*/
						// 更新列表
						taskList.clear();
						ListUtil.copyList(taskList, list);
						taskListAdapter.notifyDataSetChanged();
						ProgressUtils.getInstance().dismiss();
						// 获取任务详情
						for (EmTasksEntity task : list) {
							getDetailFromNetWork(task.getEmTasksId());
						}
					} catch (DbException e) {
						e.printStackTrace();
						ProgressUtils.getInstance().dismiss();
					}
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtils.show(mActivity, "获取最新数据失败，请检查网络");
			ProgressUtils.getInstance().dismiss();
		}
		ProgressUtils.getInstance().show("正在获取任务列表", false, mActivity);
	}

	private void initEvents() {
		// 点击进入任务列表详情
		viewHolder.getLvTasklist().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AndroidBasicComponentUtils.launchActivity(mActivity, TaskDetailActivity.class, "taskId",
						taskList.get(position).getEmTasksId());
			}
		});
		// 长按任务详情
		viewHolder.getLvTasklist().setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				AndroidBasicComponentUtils.launchActivity(mActivity, EmTaskDetailActivity.class, "EM_TASK_ENTITY",
						taskList.get(position));
				return true;
			}
		});
		viewHolder.getIvTaskReflesh().setOnClickListener(orcl);
	}

	private OnClickListener orcl = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!TextUtils.isEmpty(GlobalEntry.loginResult.getCredit())) {
				// 已经在线登陆过啦
				updateTaskData((String) SharedPreferencesUtils.get(mActivity,
						GlobalEntry.currentProject.getEmProjectId() + LAST_GET_TASK_TIME, "1900-12-20 13:30:40"));
			} else {
				// 登陆
				login();
			}
		}
	};

	private void login() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("logicSysNo", GlobalEntry.DEFAULT_LOGIC_SYS_NO);
		params.put("loginName", GlobalEntry.loginResult.getLoginname());
		params.put("pwd", GlobalEntry.loginResult.getPassWord());
		params.put("client", "2");
		String url = GlobalEntry.loginServerUrl + "login";
		try {
			netService.getRequest(url, params, null, LoginResult.class, new BaseService.RequestCallBack<LoginResult>() {
				@Override
				public void error(int code,BaseService.WayFrom from) {
					ToastUtils.show(mActivity, "在线登陆失败");
					ProgressUtils.getInstance().dismiss();
				}

				@Override
				public void callBack(LoginResult request,BaseService.WayFrom from) {
					if (request.getCode() != GlobalEntry.RESULT_SUCCESS) {
						ToastUtils.show(mActivity, request.getMsg() + "");
						ProgressUtils.getInstance().dismiss();
						return;
					}
					ToastUtils.show(mActivity, "在线登陆成功");
					GlobalEntry.loginResult.setCredit(request.getCredit());
					ProgressUtils.getInstance().dismiss();
					updateTaskData((String) SharedPreferencesUtils.get(mActivity, LAST_GET_TASK_TIME, "1900-12-20 13:30:40"));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			ProgressUtils.getInstance().dismiss();
		}
		ProgressUtils.getInstance().show("正在在线登陆", false, mActivity);
	}

	private void initDatas() {
		// 初始化
		try {
			EmTasksEntity queryEntity = new EmTasksEntity();
			queryEntity.setExecutor(GlobalEntry.loginResult.getUid());
			// taskList = (ArrayList<EmTasksEntity>)
			// dbUtil.getAll(EmTasksEntity.class);
			taskList = (ArrayList<EmTasksEntity>) dbUtil.excuteQuery(EmTasksEntity.class, queryEntity);
		} catch (DbException e) {
			e.printStackTrace();
		}
		if (null == taskList) {
			taskList = new ArrayList<EmTasksEntity>();
		}
		taskListAdapter = new TaskListAdapter(mActivity, taskList);
		viewHolder.getLvTasklist().setAdapter(taskListAdapter);
	}

	// 切换到
	void selectMapFragment() {
		BroadcastReceiverUtils.getInstance().sendCommand(mActivity, "JUST_LOOK_MAP");
	}

	public static final Location location = new Location();

	private TreeMapUtil treeMapUtil = null;

	@Override
	public void onResume() {
		if (location.location) {
			selectMapFragment();
			if (null == treeMapUtil) {
				treeMapUtil = new TreeMapUtil(mActivity);
			}
			treeMapUtil.drawDevice(location.device);
			MapCache.map.smoothMove(location.longitude, location.latitude);
			MapCache.map.setMapsLevel(17);
		}
		location.location = false;
	}

	public static class Location {
		public boolean location = false;
		public double longitude;
		public double latitude;
		public DeviceEntity<?> device;
	}
}
