package com.winway.android.edcollection.project.controll;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.dbVersion.utils.DbVersionUtils;
import com.winway.android.edcollection.base.dbVersion.utils.DbVersionUtils.DbUpgrade;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.login.bll.EdpBll;
import com.winway.android.edcollection.login.bll.LoginBll;
import com.winway.android.edcollection.login.entity.LoginResult;
import com.winway.android.edcollection.main.activity.MainActivity;
import com.winway.android.edcollection.project.adapter.ProjectingExpandableAdapter;
import com.winway.android.edcollection.project.bll.ProjectingBll;
import com.winway.android.edcollection.project.dto.EmProjectEntityDto;
import com.winway.android.edcollection.project.entity.EmMembersEntity;
import com.winway.android.edcollection.project.entity.EmProjectEntity;
import com.winway.android.edcollection.project.entity.EmTasksEntity;
import com.winway.android.edcollection.project.viewholder.ProjectingViewHolder;
import com.winway.android.util.FileUtil;
import com.winway.android.util.NetWorkUtils;
import com.winway.android.util.PreferencesUtils;
import com.winway.android.util.ProgressUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.ToastUtils;

/**选择项目业务处理类
 * @author lyh
 * @version 创建时间：2016年12月7日 上午10:19:38
 * 
 */
public class ProjectingControll extends BaseControll<ProjectingViewHolder> {
	private ArrayList<EmProjectEntityDto> prjList;
	private ProjectingExpandableAdapter adapter;
	private ProjectingBll prjBll;
	private int currentPosition = 0, currentPositionTask;
	private EmProjectEntityDto emProjectEntity;

	/**
	 * 初始化
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		prjBll = new ProjectingBll(mActivity, FileUtil.AppRootPath + "/db/common/global.db");
		PreferencesUtils.PREFERENCE_NAME = GlobalEntry.loginResult.getUid();
		initData();
		initEvent();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// TODO Auto-generated method stub
		// 拿到所有项目名称并遍历
		ArrayList<EmProjectEntityDto> list = prjBll.getProjectsByUid(mActivity, GlobalEntry.loginResult.getUid());
		if (list == null || list.isEmpty()) {
			// 请求网络项目任务列表
			refreshPrjList();
		} else {
			mRefreshProjectCallback.onSucceed(list);
		}
	}

	private RefreshProjectCallback mRefreshProjectCallback = new RefreshProjectCallback() {
		@Override
		public void onSucceed(ArrayList<EmProjectEntityDto> list) {
			if (list == null || list.isEmpty()) {
				ToastUtils.show(mActivity, "没有项目");
				return;
			}
			if (null != prjList) {
				prjList.clear();
				prjList = null;
			}
			prjList = new ArrayList<EmProjectEntityDto>();
			prjList.addAll(list);
			initAdapter();
			// 检查数据字典
			String dbUrl = FileUtil.AppRootPath + "/db/common/global.db";
			EdpBll edpBll = new EdpBll(mActivity, dbUrl);
			if (edpBll.getParamsCount() < 1) {
				edpBll.getParamList(mActivity);
			}
		}

		@Override
		public void onFailed() {
		}
	};

	/**
	 * 刷新工程回调
	 * 
	 * @author WINWAY
	 *
	 */
	private interface RefreshProjectCallback {
		void onSucceed(ArrayList<EmProjectEntityDto> list);

		void onFailed();
	}

	/**
	 * 设置适配器
	 */
	private void initAdapter() {
		ArrayList<List<EmTasksEntity>> childs = new ArrayList<>();
		for (int i = 0; i < prjList.size(); i++) {
			EmProjectEntityDto emProjectEntityDto = prjList.get(i);
			if (emProjectEntityDto == null) {
				continue;
			}
			List<EmTasksEntity> t = emProjectEntityDto.getTasks();
			childs.add(t != null ? t : new ArrayList<EmTasksEntity>());
		}
		if (adapter == null) {
			adapter = new ProjectingExpandableAdapter(mActivity);
			adapter.setParents(prjList);
			adapter.setChilds(childs);
			adapter.setExpandableListView(viewHolder.getProjectELV());
			viewHolder.getProjectELV().setAdapter(adapter);
			viewHolder.getProjectELV().setGroupIndicator(null);
		} else {
			adapter.setParents(prjList);
			adapter.setChilds(childs);
			adapter.refresh();
		}
		setUpSelectItem();
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		// TODO Auto-generated method stub
		viewHolder.getCancelTV().setOnClickListener(ocls);
		viewHolder.getConfirmTV().setOnClickListener(ocls);
		viewHolder.getBtnRefresh().setOnClickListener(ocls);
		viewHolder.getProjectELV().setOnGroupClickListener(onGroupClickListener);
		viewHolder.getProjectELV().setOnChildClickListener(onChildClickListener);
	}

	/**
	 * 获取上次选择的项目
	 */
	private void setUpSelectItem() {
		// 取出上次选择的项目
		int position = PreferencesUtils.getInt(mActivity, "projectSelectPosition", 0);
		int positionTask = PreferencesUtils.getInt(mActivity, "taskSelectPosition", -1);
		currentPosition = position;
		currentPositionTask = positionTask;
		adapter.setSelectedPosition(currentPosition);
		adapter.setSelectedChildPosition(currentPositionTask);
		viewHolder.getProjectELV().expandGroup(currentPosition);// 展开父节点
		adapter.notifyDataSetInvalidated();
		// 保存当前任务ID
		if (currentPositionTask >= 0) {
			if (adapter.getGroupCount() > currentPosition) {
				if (adapter.getChildrenCount(currentPosition) > currentPositionTask) {
					GlobalEntry.currentTask = adapter.getChild(currentPosition, currentPositionTask);
					return;
				} else {
					currentPositionTask = -1;
					adapter.setSelectedChildPosition(currentPositionTask);
					adapter.notifyDataSetInvalidated();
				}
			}
		}
		// 小于0则说明只选工程，不选任务
		GlobalEntry.currentTask = null;
	}

	/**
	 * 二级列表父级单击事件
	 */
	private OnGroupClickListener onGroupClickListener = new OnGroupClickListener() {
		@Override
		public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
			// 记录本次选择的项目
			PreferencesUtils.putInt(mActivity, "projectSelectPosition", groupPosition);
			int childPosition = -1;// 点击的是父级，没有选中任务
			PreferencesUtils.putInt(mActivity, "taskSelectPosition", childPosition);
			currentPosition = groupPosition;
			currentPositionTask = childPosition;
			adapter.setSelectedPosition(currentPosition);
			adapter.setSelectedChildPosition(currentPositionTask);
			adapter.notifyDataSetInvalidated();
			setUpSelectItem();
			return true;
		}
	};

	/**
	 * 二级列表子列表单击事件
	 */
	private OnChildClickListener onChildClickListener = new OnChildClickListener() {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
			// 记录本次选择的项目
			PreferencesUtils.putInt(mActivity, "projectSelectPosition", groupPosition);
			PreferencesUtils.putInt(mActivity, "taskSelectPosition", childPosition);
			currentPosition = groupPosition;
			currentPositionTask = childPosition;
			adapter.setSelectedPosition(currentPosition);
			adapter.setSelectedChildPosition(currentPositionTask);
			adapter.notifyDataSetInvalidated();
			setUpSelectItem();
			return true;
		}
	};

	/**
	 * 单击列表item执行的操作
	 * 
	 * @param arg0
	 * @param arg1
	 * @param position
	 * @param arg3
	 */
	@OnItemClick(R.id.lv_projecting_project)
	private void itemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// 记录本次选择的项目
		PreferencesUtils.putInt(mActivity, "projectSelectPosition", position);
		currentPosition = position;
		adapter.setSelectedPosition(position);
		adapter.notifyDataSetInvalidated();
	}

	private OnClickListener ocls = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_projecting_title_cancel: {
				mActivity.finish();
				break;
			}
			case R.id.tv_projecting_title_confirm: {
				if (prjList == null || prjList.isEmpty()) {
					ToastUtil.show(mActivity, "请先选择项目！", 200);
					return;
				}
				emProjectEntity = prjList.get(currentPosition);
				if (emProjectEntity.getPrjNo() == null) {
					ToastUtil.show(mActivity, "项目编号不能为空！", 200);
					return;
				}
				submitSure(emProjectEntity);
				break;
			}
			case R.id.btn_projecting_project: {// 刷新项目列表
				refreshPrjList();
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * 确定项目选择
	 */
	private void submitSure(final EmProjectEntity emProjectEntity) {
		GlobalEntry.currentProject = emProjectEntity;// 保存当前选择的项目到内存
		GlobalEntry.prjDbUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		if (prjBll.isExistProjectDb(emProjectEntity.getPrjNo())) {
			// 执行数据库版本升级
			DbVersionUtils.getInstance().handleDbUpgrade(mActivity, new DbUpgrade() {

				@Override
				public void success(String result) {
					// TODO Auto-generated method stub
					ToastUtil.show(mActivity, "软件升级成功", 1000);
					// 本地存在
					Intent intent = new Intent(mActivity, MainActivity.class);
					intent.putExtra("project", emProjectEntity);
					startActivity(intent);
					mActivity.finish();
				}

				@Override
				public void fail(String result) {
					// TODO Auto-generated method stub
					ToastUtil.show(mActivity, "软件升级失败,请联系开发人员：" + result, 3000);
					return;
				}
			});
		} else {
			// 本地不存在,走下载db逻辑
			prjBll.downloadProjectDb(emProjectEntity, mActivity);
		}
		// 创建项目中存放照片的文件夹
		FileUtil.createPath(FileUtil.AppRootPath + "/picture/" + emProjectEntity.getPrjNo());
	}

	private final int prjFlagSuccess = 1;
	private final int prjFlagFail = 2;
	@SuppressLint("HandlerLeak")
	Handler refreshHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case prjFlagSuccess: {
				// 刷新列表
				ArrayList<EmProjectEntityDto> list = prjBll.getProjectsByUid(mActivity, GlobalEntry.loginResult.getUid());
				if (list == null || list.isEmpty()) {
					ProgressUtils.getInstance().dismiss();
					return;
				}
				ToastUtil.show(mActivity, "加载成功", 100);
				ProgressUtils.getInstance().dismiss();
				mRefreshProjectCallback.onSucceed(list);
				break;
			}
			case prjFlagFail: {
				ToastUtil.show(mActivity, "加载失败:" + msg.obj.toString(), 100);
				ProgressUtils.getInstance().dismiss();
				mRefreshProjectCallback.onFailed();
				break;
			}

			default:
				break;
			}
		}
	};

	/**
	 * 刷新项目列表
	 */
	private void refreshPrjList() {
		// TODO Auto-generated method stub
		if (!NetWorkUtils.isConnected(mActivity)) {
			ToastUtil.show(mActivity, "网络异常", 100);
			return;
		}
		final LoginBll loginBll = new LoginBll(mActivity, FileUtil.AppRootPath + "/db/common/global.db");
		ProgressUtils.getInstance().show("加载中...", false, mActivity);
		// 执行登录、获取项目列表
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				try {
					String loginData = loginBll.login();
					LoginResult loginResult = loginBll.parseLogin(loginData);
					GlobalEntry.loginResult.setCredit(loginResult.getCredit());// 保存登录凭据到内存
					// 获取工程、任务信息
					String jsonData = prjBll.getProjectListOnline();
					List<EmProjectEntityDto> projectList = prjBll.parseProjectList(jsonData);
					prjBll.savePrjList(projectList);// 入库
					List<EmMembersEntity> emMembers = prjBll.parseMembersList(jsonData);
					prjBll.saveMembers(emMembers);// 入库
					message.what = prjFlagSuccess;
					refreshHandler.sendMessage(message);
				} catch (Exception e) {
					// TODO: handle exception
					message.what = prjFlagFail;
					message.obj = e.getMessage();
					refreshHandler.sendMessage(message);
				}

			}
		}).start();

	}

}
