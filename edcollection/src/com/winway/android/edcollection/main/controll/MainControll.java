package com.winway.android.edcollection.main.controll;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.MarkerCollectActivity;
import com.winway.android.edcollection.adding.activity.SubstationActivity;
import com.winway.android.edcollection.adding.controll.NewFragmentControll;
import com.winway.android.edcollection.adding.controll.SubstationControll;
import com.winway.android.edcollection.adding.fragment.NewFragment;
import com.winway.android.edcollection.adding.util.NewMarkWayTool;
import com.winway.android.edcollection.adding.util.SelectCollectObjPopWindow;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.ToastHandler;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.util.CoordsUtils;
import com.winway.android.edcollection.base.view.BaseFragmentsControll;
import com.winway.android.edcollection.colist.fragment.CollectedFragment;
import com.winway.android.edcollection.damage.entity.DamageFlag;
import com.winway.android.edcollection.damage.fragment.DamageFragment;
import com.winway.android.edcollection.main.bll.MainBll;
import com.winway.android.edcollection.main.viewholder.MainViewHolder;
import com.winway.android.edcollection.map.SpatialAnalysis;
import com.winway.android.edcollection.map.entity.MapOperatorFlag;
import com.winway.android.edcollection.map.entity.SpatialAnalysisDataType;
import com.winway.android.edcollection.map.util.CombineSpatialDatasUtil;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.setting.fragment.SettingsFragment;
import com.winway.android.edcollection.setting.service.AutoUploadServiceEntrance;
import com.winway.android.edcollection.task.fragment.TaskListFragment;
import com.winway.android.map.entity.MapCache;
import com.winway.android.map.util.MapUtils;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.BroadcastReceiverUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ProgressUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import ocn.himap.datamanager.HiElement;

/**
 * 主界面业务处理
 * 
 * @author zgq
 *
 */
public class MainControll extends BaseFragmentsControll<MainViewHolder> {
	private boolean isOpen = false;// PopupWindow 是否打开
	public static int flag_marker_collect = 1;// 标识标识器采集
	public static int requestCode_addtempline = 2;// 新增临时线路
	public static int flag_marker_edit = 3;// 标识标识器编辑
	public static int flag_search_line = 4;// 搜索线路
	private NewFragment newFragment;
	public static EcSubstationEntityVo ecSubstationEntityVo;// 变电站
	public static final int requestCode_subtation = 5; // 新增变电站
	public static final int mapSelectCoord_requestCode = 6; // 地图选点
	public static final int requestCode_devicequery=7;
	//	private AutoUploadServiceEntrance autoUploadServiceEntrance;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		setFragmentSelect(0);
		initEvent();

		// 添加数据集到数据管理器中
		SpatialAnalysis.getInstance().loadDataset2DataManager();
		// 构建空间查询数据
		ProgressUtils.getInstance().show("加载中...", false, mActivity);
		final int combineSpatialData = 111;
		final Handler uiHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case combineSpatialData: {
					ProgressUtils.getInstance().dismiss();
					break;
				}

				default:
					break;
				}

			}
		};

		final ToastHandler toastHandler = new ToastHandler(mActivity);

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = combineSpatialData;
				CombineSpatialDatasUtil.getInstane().setToastHandler(toastHandler);
				CombineSpatialDatasUtil.getInstane().loadSpatialDatas(mActivity);
				uiHandler.sendMessage(message);
			}
		}).start();

		BroadcastReceiverUtils.getInstance().registReceiver(mActivity, "JUST_LOOK_MAP", bReceiver);
		// 打开自动上传服务
//		autoUploadServiceEntrance = new AutoUploadServiceEntrance(mActivity, GlobalEntry.globalDbUrl,
//				GlobalEntry.prjDbUrl);
//		autoUploadServiceEntrance.open();
	}

	private void initEvent() {
		viewHolder.getRlNew().setOnClickListener(orcl);
		viewHolder.getLlLink().setOnClickListener(orcl);
		viewHolder.getLlCollected().setOnClickListener(orcl);
		viewHolder.getLlSetting().setOnClickListener(orcl);
		viewHolder.getIvSelectCollectObjNew().setOnClickListener(orcl);
		viewHolder.getLlTasklist().setOnClickListener(orcl);
	}

	/**
	 * 将 tabs重置
	 */
	private void resetTabs() {
		SelectCollectObjPopWindow.getIntance(mActivity).closeNewPopWid();
		NewMarkWayTool.getInstance().closePopWid();
		// 重置tab 图片
		viewHolder.getImgBtnNew().setImageResource(R.drawable.tab_new);
		viewHolder.getIvSelectCollectObjNew().setImageResource(R.drawable.list);
		viewHolder.getTvNew().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab));

		viewHolder.getImgBtnLink().setImageResource(R.drawable.tab_edit);
		viewHolder.getTvLink().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab));

		viewHolder.getImgBtnCollected().setImageResource(R.drawable.tab_already);
		viewHolder.getTvCollected().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab));

		viewHolder.getImgBtnSetting().setImageResource(R.drawable.tab_setting);
		viewHolder.getTvSetting().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab));

		viewHolder.getImgBtnTasklist().setImageResource(R.drawable.tab_task);
		viewHolder.getTvTasklist().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab));

	}

	/**
	 * 实现选中后的 tabs的状态
	 * 
	 * @param i
	 */
	private void setTabsSelected(int i) {

		switch (i) {
		case 0: {
			viewHolder.getImgBtnNew().setImageResource(R.drawable.tab_new_s);
			viewHolder.getIvSelectCollectObjNew().setImageResource(R.drawable.list_s);
			viewHolder.getTvNew().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab_f));
			// 新增
			GlobalEntry.editNode = false;
			break;
		}
		case 1: {
			viewHolder.getImgBtnLink().setImageResource(R.drawable.tab_edit_s_s);
			viewHolder.getTvLink().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab_f));
			// 编辑
			GlobalEntry.editNode = true;
			// 注册广播
			BroadcastReceiverUtils.getInstance().registReceiver(mActivity, MapOperatorFlag.flag_edit_show, bReceiver);
			BroadcastReceiverUtils.getInstance().registReceiver(mActivity, MapOperatorFlag.flag_edit_show_cancel,
					bReceiver);
			break;
		}
		case 2: {
			viewHolder.getImgBtnCollected().setImageResource(R.drawable.tab_already_s);
			viewHolder.getTvCollected().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab_f));
			break;
		}
		case 3: {
			viewHolder.getImgBtnTasklist().setImageResource(R.drawable.tab_task_f);
			viewHolder.getTvTasklist().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab_f));
			break;
		}

		case 4: {
			viewHolder.getImgBtnSetting().setImageResource(R.drawable.tab_setting_s);
			viewHolder.getTvSetting().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab_f));
			break;
		}
		default:
			break;
		}

	}

	// 广播接收者
	private BroadcastReceiver bReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(MapOperatorFlag.flag_edit_show)) {
				NewFragmentControll newFragmentControll = newFragment.getAction();
				if (newFragmentControll != null) {
					newFragmentControll.getViewHolder().getIncludeMainItemTransfer().setVisibility(View.VISIBLE);
				}

			} else if (intent.getAction().equals(MapOperatorFlag.flag_edit_show_cancel)) {
				NewFragmentControll newFragmentControll = newFragment.getAction();
				if (newFragmentControll != null) {
					newFragmentControll.getViewHolder().getIncludeMainItemTransfer().setVisibility(View.GONE);
				}
				GlobalEntry.currentEditNode = null;
			} else if (intent.getAction().equals(DamageFlag.flag_add_damage_success)) {
				// 切换到主界面
				viewHolder.getLlLink().performClick();
			} else if (intent.getAction().equals("JUST_LOOK_MAP")) {
				resetTabs();
				setTabsSelected(1);
				if (selectedfragment == 0) {
					return;
				}
				MainControll.this.setFragmentSelect(0);
			}

		}
	};

	/**
	 * 监听器
	 */
	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.rl_bottom_new: {// 新增
				resetTabs();
				setTabsSelected(0);
				if (selectedfragment != 0) {
					MainControll.this.setFragmentSelect(0);
				}
				// 判断是否有选择采集的线路
				List<EcLineEntity> lineList = SelectCollectObjPopWindow.getIntance(mActivity).getWaitingLines();
				if (lineList == null || lineList.size() < 1) {
					ToastUtil.show(mActivity, "请先选择采集线路", 200);
					return;
				}
				Intent intent = new Intent(mActivity, MarkerCollectActivity.class);
				intent.putExtra("coord", MapUtils.getInstance().getMapCenter());
				// 打开标识器采集界面
				AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, flag_marker_collect);
				break;
			}
			case R.id.ll_bottom_link: {// 编辑
				resetTabs();
				setTabsSelected(1);
				if (selectedfragment == 0) {
					return;
				}
				MainControll.this.setFragmentSelect(0);
				if (MapCache.map.getMapsLevel() < 14) {
					MapCache.map.setMapsLevel(14);
				}
				break;
			}
			case R.id.ll_bottom_collected: {// 已采
				resetTabs();
				setTabsSelected(2);
				MainControll.this.setFragmentSelect(1);
				CollectedFragment cf = (CollectedFragment) MainControll.this.fragments.get(1);
				if (cf.getAction() != null) {
					cf.getAction().refreshItems();
				}
				break;
			}
			case R.id.ll_bottom_tasklist: {// 任务列表
				resetTabs();
				setTabsSelected(3);
				MainControll.this.setFragmentSelect(2);
				break;
			}
			case R.id.ll_bottom_setting: {// 设置
				resetTabs();
				setTabsSelected(4);
				MainControll.this.setFragmentSelect(4);
				SettingsFragment cf = (SettingsFragment) MainControll.this.fragments.get(4);
				if (cf.getAction() != null) {
					cf.getAction().refreshItems();
				}
				break;
			}
			case R.id.iv_bottom_selectCollectObj: {// 选择采集对象
				resetTabs();
				setTabsSelected(0);
				if (isOpen) {
					isOpen = false;
					SelectCollectObjPopWindow.getIntance(mActivity).closeNewPopWid();
				} else {
					TextView titleLine = newFragment.getAction().getViewHolder().getTvTitleLine();
					SelectCollectObjPopWindow.getIntance(mActivity).showNewPopWid(viewHolder.getViewLine(), titleLine);
					isOpen = true;
				}
				break;
			}
			default:
				break;
			}
		}
	};

	@Override
	public List<Fragment> getFragments() {
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		newFragment = new NewFragment();
		fragments.add(newFragment);
		CollectedFragment collectedFragment = new CollectedFragment();
		fragments.add(collectedFragment);
		TaskListFragment taskListFragment = new TaskListFragment();
		fragments.add(taskListFragment);
		DamageFragment damageFragment = new DamageFragment();
		fragments.add(damageFragment);
		SettingsFragment settingsFragment = new SettingsFragment();
		fragments.add(settingsFragment);
		return fragments;
	}

	@Override
	public int content_id() {
		return R.id.rl_main_content;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == requestCode_addtempline) {
				SelectCollectObjPopWindow.getIntance(mActivity).updateLineList();
			} else if (requestCode == flag_search_line) {
				SelectCollectObjPopWindow.getIntance(mActivity).updateLineList();
			} else if (requestCode == MainControll.flag_marker_edit) {
				resetTabs();
				setTabsSelected(1);
				MainControll.this.setFragmentSelect(0);
			}
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SelectCollectObjPopWindow.getIntance(mActivity).closeNewPopWid();
		// 关闭自动上传服务
//		autoUploadServiceEntrance.close();
	}

	private long nowTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - nowTime) < 2000) {
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(10);
			} else {
				nowTime = System.currentTimeMillis();
				ToastUtil.show(mActivity, "再按一次返回键退出", 300);
			}
		}

		return false;

	}

}
