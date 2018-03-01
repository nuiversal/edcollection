package com.winway.android.edcollection.adding.controll;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.bll.BasicInfoBll;
import com.winway.android.edcollection.adding.bll.DataCacheBll;
import com.winway.android.edcollection.adding.controll.ChannelControll.ChannelDataEntity;
import com.winway.android.edcollection.adding.entity.DitchCable;
import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.adding.entity.UpMarkerCache;
import com.winway.android.edcollection.adding.fragment.BasicInfoFragment;
import com.winway.android.edcollection.adding.fragment.ChannelFragment;
import com.winway.android.edcollection.adding.fragment.ChannelNodeStatusFragment;
import com.winway.android.edcollection.adding.fragment.LayingDitchFragment;
import com.winway.android.edcollection.adding.fragment.ScenePhotoFragment;
import com.winway.android.edcollection.adding.util.FragmentEntry;
import com.winway.android.edcollection.adding.util.TreeRefleshUtil;
import com.winway.android.edcollection.adding.viewholder.BasicInfoViewHolder;
import com.winway.android.edcollection.adding.viewholder.MarkerCollectViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.FragmentViewPageAdapter;
import com.winway.android.edcollection.base.view.BaseFragmentsControll;
import com.winway.android.edcollection.login.controll.SettingServerAddressControll;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.ewidgets.attachment.AttachmentUtil;
import com.winway.android.ewidgets.attachment.AttachmentUtil.AddDeleteResult;
import com.winway.android.ewidgets.attachment.AttachmentView.AttachmentResult;
import com.winway.android.ewidgets.dialog.DialogView;
import com.winway.android.util.FileUtil;
import com.winway.android.util.SharedPreferencesUtils;
import com.winway.android.util.StringUtils;
import com.winway.android.util.ToastUtil;

/**
 * 标识器采集
 * 
 * @author lyh
 * @version 创建时间：2016年12月12日 上午11:10:42
 * 
 */
public class MarkerCollectControll extends BaseFragmentsControll<MarkerCollectViewHolder> {
	private BasicInfoFragment basicFragment;
	private ChannelFragment channelFragment;
	private ScenePhotoFragment scenePhotoFragment;
	private LayingDitchFragment layingDitchFragment;
	private DataCacheBll dataCacheBll = null;
	private BasicInfoBll basicInfoBll = null;
	private static MarkerCollectControll markerCollectControll;
	private long nowTime = 0;
	private ChannelNodeStatusFragment channelNodeStatusFragment;

	@Override
	public void init() {
		basicInfoBll = new BasicInfoBll(mActivity,
				FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db");
		dataCacheBll = new DataCacheBll(mActivity, FileUtil.AppRootPath + "/db/project/data_cache.db");
		initFragment();
		initEvent();
		markerCollectControll = this;
		viewHolder.getVpmarkMainVp().setOffscreenPageLimit(4);
		initAtta();
	}

	/**
	 * 获取对象实例
	 * 
	 * @return
	 */
	public static MarkerCollectControll getInstance() {
		return markerCollectControll;
	}

	private void initAtta() {
		GlobalEntry.useTfcard = Boolean
				.parseBoolean(SharedPreferencesUtils.get(mActivity, GlobalEntry.IS_USE_TFCARD, false).toString());
		if (GlobalEntry.useTfcard) {
			String path = SettingServerAddressControll.getTFCardPath(mActivity);
			if (TextUtils.isEmpty(path)) {// 判断tf卡是否存在
				ToastUtil.show(mActivity, "未找到TF卡,附件数据将保存到内置存储卡中", 200);
			}
			long sdFreeSize = FileUtil.getSDFreeSize(Environment.getExternalStorageDirectory().getPath());
			if (sdFreeSize <= 5120) { // 当内置存储容量小于5m时
				ToastUtil.show(mActivity, "储存空间不足" + FileUtil.formatFileSize(sdFreeSize) + "M,请注意添加的附件大小！", 200);
			}
		} else {
			long sdFreeSize = FileUtil.getFreeDiskSpace();
			if (sdFreeSize <= 5120) { // 当内置存储容量小于5m时
				ToastUtil.show(mActivity, "储存空间不足" + FileUtil.formatFileSize(sdFreeSize) + ",请注意添加的附件大小！", 200);
			}
		}
	}

	private void initEvent() {
		viewHolder.getLlBasicInfo().setOnClickListener(ocl);
		viewHolder.getLlChannelInfo().setOnClickListener(ocl);
		viewHolder.getLlDeviceInfo().setOnClickListener(ocl);
		viewHolder.getLlScenePhoto().setOnClickListener(ocl);
		viewHolder.getLlChannelnodeStatus().setOnClickListener(ocl);
		viewHolder.getBtnSameInfo().setOnClickListener(ocl);
		viewHolder.getBtnSave().setOnClickListener(ocl);
		viewHolder.getHcHead().getReturnView().setOnClickListener(ocl);
		// 设置ViewPage 切换效果
		viewHolder.getVpmarkMainVp().setOnPageChangeListener(new vpOnChangeListener());
		if (!GlobalEntry.editNode) {
			viewHolder.getVpmarkMainVp().setScanScroll(false);
		}
	}

	/**
	 * 初始化fragment 数据
	 */
	private void initFragment() {
		FragmentViewPageAdapter adapter = new FragmentViewPageAdapter(fragmentManager, fragments);
		viewHolder.getVpmarkMainVp().setAdapter(adapter);
	}

	DialogView dialogView = null;

	void showChannelEnSureDialog() {
		if (null == dialogView) {
			dialogView = new DialogView(mActivity, "通道保存提示", "您有未保存的通道数据，是否要放弃未保存的通道数据？", new String[] { "放弃", "去保存" },
					new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int which, long id) {
							if (which == 0) {
								saveData();
								dialogView.destroyDialog();
								dialogView = null;
							} else {
								resetTabs();
								setTabsSelected(1);
								dialogView.dismisDialog();
							}
						}
					});
		}
		dialogView.showDialog();
	}

	/**
	 * 监听器
	 */
	private OnClickListener ocl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ll_mark_tab_basic_info: // 基本信息
				resetTabs();
				setTabsSelected(0);
				break;
			case R.id.ll_mark_tab_channel_info: // 通道信息
				selectPlusModule(1);
				break;
			case R.id.ll_mark_tab_device_info: // 同沟信息
				selectPlusModule(2);
				break;
			case R.id.ll_mark_tab_scene_photo: // 现场照片
				selectPlusModule(3);
				break;
			case R.id.ll_mark_tab_channelnodeStatus: // 通道现状
				selectPlusModule(4);
				break;
			case R.id.btn_marker_collect_sameinfo: // 同上一标识信息
				sameUpMarker();
				break;
			case R.id.btn_marker_collect_save: // 保存
				if (channelFragment.getAction().needSave()) {
					showChannelEnSureDialog();
					return;
				}
				saveData();
				break;
			case R.id.tv_head_item_return: // 返回
				if ((System.currentTimeMillis() - nowTime) < 2000) {
					// saveData();
					delAttaPath();
					mActivity.finish();
				} else {
					nowTime = System.currentTimeMillis();
					ToastUtil.show(mActivity, "再按一次返回键退出", 300);
				}
				break;
			default:
				break;
			}
		}

	};

	/**
	 * 选择更多组件
	 * 
	 * @param moduleId
	 *            组件id(num)
	 */
	private void selectPlusModule(int moduleId) {
		if (!GlobalEntry.editNode) {
			if (BasicInfoControll.isWrittenMarkerNo) {
				resetTabs();
				setTabsSelected(moduleId);
				viewHolder.getVpmarkMainVp().setScanScroll(true);
			} else {
				viewHolder.getVpmarkMainVp().setScanScroll(false);
				showToast("请输入完整的标识器ID号");
			}
		} else {
			resetTabs();
			setTabsSelected(moduleId);
		}
	}

	/**
	 * 操作附件
	 */
	private void delAttaPath() {
		EcNodeEntity ecNodeEntity = (EcNodeEntity) getIntent().getSerializableExtra("EcNodeEntity");
		ScenePhotoControll scenePhotoControll = scenePhotoFragment.getAction();
		if (null != ecNodeEntity) {// 编辑路径点时
			if (scenePhotoControll != null) {
				// 查找出附件组件中存在的附件路径
				AttachmentResult result = scenePhotoControll.getViewHolder().getSceneAttachment().getResult();
				// 查找出添加、删除、未改变的附件
				AddDeleteResult addDeleteResult = AttachmentUtil.findAddDeleteResult(result,
						scenePhotoControll.getAttachFiles());
				// 添加的路径或者是重命名的附件
				ArrayList<String> addList = addDeleteResult.addList;
				if (null != addList && !addList.isEmpty()) {
					for (String filePath : addList) {
						// 如果数据库中不存在此附件
						if (null == basicInfoBll.getAttaByPath(filePath)) {
							// 删除此附件路径
							FileUtil.deleteFileWithPath(filePath);
						}
					}
				}
				String expr = "WORK_NO='" + ecNodeEntity.getOid() + "'";
				List<OfflineAttach> attaList = basicInfoBll.queryByExpr2(OfflineAttach.class, expr);
				if (null == attaList || attaList.isEmpty()) {
					// 删除附件目录
					FileUtil.deleteDirectory(scenePhotoControll.fileDir);
				}
			}
		} else {// 新增路径点时
			if (scenePhotoControll != null) {
				// 删除目录
				FileUtil.deleteDirectory(scenePhotoControll.fileDir);
			}
		}
	}

	@Override
	public void onDestroy() {
		// 清空数据
		if (FragmentEntry.isSelectMap) {
			FragmentEntry.isSelectMap = false;
		} else {
			FragmentEntry.getInstance().resetAllData();
		}
		BasicInfoControll.isWrittenMarkerNo = false;
	};

	/**
	 * 将 tabs重置
	 */
	private void resetTabs() {
		viewHolder.getTvBasicInfo().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab));
		viewHolder.getLlBasicInfo().setBackgroundResource(0);

		viewHolder.getTvChannelInfo().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab));
		viewHolder.getLlChannelInfo().setBackgroundResource(0);

		viewHolder.getTvDeviceInfo().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab));
		viewHolder.getLlDeviceInfo().setBackgroundResource(0);

		viewHolder.getTvScenePhoto().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab));
		viewHolder.getLlScenePhoto().setBackgroundResource(0);

		viewHolder.getTvChannelnodeStatus()
				.setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab));
		viewHolder.getLlChannelnodeStatus().setBackgroundResource(0);
	}

	/**
	 * 处理同上一标识器
	 */
	protected void sameUpMarker() {
		UpMarkerCache upMarkerCache = dataCacheBll.getUpMarkerCache();
		if (upMarkerCache == null) {
			ToastUtil.show(mActivity, "没有数据", 200);
			return;
		}
		BasicInfoViewHolder basicInfoViewHolder = basicFragment.getAction().getViewHolder();
		Integer markType = upMarkerCache.getMarkerType();
		// TODO
		if (markType.equals(NodeMarkerType.BSQ.getValue())) {
			basicInfoViewHolder.getRgMarkerType().check(basicInfoViewHolder.getRgMarkerType().getChildAt(0).getId());
		} else if (markType.equals(NodeMarkerType.BSD.getValue())) {
			basicInfoViewHolder.getRgMarkerType().check(basicInfoViewHolder.getRgMarkerType().getChildAt(1).getId());
		} else if (markType.equals(NodeMarkerType.XND.getValue())) {
			basicInfoViewHolder.getRgMarkerType().check(basicInfoViewHolder.getRgMarkerType().getChildAt(2).getId());
		} else if (markType.equals(NodeMarkerType.GT.getValue())) {
			basicInfoViewHolder.getRgMarkerType().check(basicInfoViewHolder.getRgMarkerType().getChildAt(3).getId());
		} else if (markType.equals(NodeMarkerType.AJH.getValue())) {
			basicInfoViewHolder.getRgMarkerType().check(basicInfoViewHolder.getRgMarkerType().getChildAt(4).getId());
		}
		basicInfoViewHolder.getIscInstallPath()//安装位置
				.setEdtTextValue(StringUtils.nullStrToEmpty(upMarkerCache.getInstallPosition()));
		basicInfoViewHolder.getIcFlootHeight()//沟底到地面高度
				.setEdtText(StringUtils.nullStrToEmpty(upMarkerCache.getCableDeepth()) + "");
		//管块尺寸
		basicInfoViewHolder.getIcWidth().setEdtText(StringUtils.nullStrToEmpty(upMarkerCache.getCableWidth()));
		//设备描述
		basicInfoViewHolder.getIscDescribe().setEdtTextValue(StringUtils.nullStrToEmpty(upMarkerCache.getDeviceDesc()));
		//其他
		basicInfoViewHolder.getIscOther().setEdtTextValue(StringUtils.nullStrToEmpty(upMarkerCache.getRemark()));
		//地理位置
		basicInfoViewHolder.getIcGeographyPos()
				.setEdtText(StringUtils.nullStrToEmpty(upMarkerCache.getGeographicalPosition()));
		// basicInfoViewHolder.getIscLayType().setEdtTextValue(StringUtils.nullStrToEmpty(upMarkerCache.getLayType()));//
		// 敷设类型
	}

	/**
	 * 保存
	 */
	private void saveData() {
		BasicInfoControll basicInfoControll = basicFragment.getAction();
		if (basicInfoControll == null) {
			return;
		}
		// 检查必填基本信息
		if (basicInfoControll.getViewHolder().getIcMarkerId().getEdtTextValue().isEmpty()) {
			ToastUtil.show(mActivity, "标识器ID号不能为空！", 200);
			viewHolder.getLlBasicInfo().performClick();
			basicInfoControll.getViewHolder().getIcMarkerId().getEditTextView().setFocusable(true);
			basicInfoControll.getViewHolder().getIcMarkerId().getEditTextView().requestFocus();
			return;
		}
		if (basicInfoControll.getViewHolder().getIcLongitude().getEdtTextValue().isEmpty()) {
			ToastUtil.show(mActivity, "经度不能为空！", 200);
			viewHolder.getLlBasicInfo().performClick();
			basicInfoControll.getViewHolder().getIcLongitude().getEditTextView().setFocusable(true);
			basicInfoControll.getViewHolder().getIcLongitude().getEditTextView().requestFocus();
			return;
		}
		if (basicInfoControll.getViewHolder().getIcLatitude().getEdtTextValue().isEmpty()) {
			ToastUtil.show(mActivity, "纬度不能为空！", 200);
			viewHolder.getLlBasicInfo().performClick();
			basicInfoControll.getViewHolder().getIcLatitude().getEditTextView().setFocusable(true);
			basicInfoControll.getViewHolder().getIcLatitude().getEditTextView().requestFocus();
			return;
		}
		// 检查标识器ID是否可用(新增情况)
		if (basicInfoBll.checkMarkerId(basicInfoControll.getViewHolder().getIcMarkerId().getEdtTextValue())
				&& GlobalEntry.editNode == false) {
			ToastUtil.show(mActivity, "该标识器ID号不能用！", 200);
			viewHolder.getLlBasicInfo().performClick();
			basicInfoControll.getViewHolder().getIcMarkerId().getEditTextView().setFocusable(true);
			basicInfoControll.getViewHolder().getIcMarkerId().getEditTextView().requestFocus();
			return;
		}

		// 如果路径点类型为标识球和标识钉才进行命名格式检查
		int markTypeId = basicInfoControll.getViewHolder().getRgMarkerType().getCheckedRadioButtonId();
		// TODO
		if (markTypeId == basicInfoControll.getViewHolder().getRgMarkerType().getChildAt(0).getId()
				|| markTypeId == basicInfoControll.getViewHolder().getRgMarkerType().getChildAt(1).getId()) {// 球
			// 检查标识器ID是否符合xxx-xxx-xxxx的格式
			String markeridRegex = "\\d{3}-\\d{3}-\\d{4}";
			if (!basicInfoControll.getViewHolder().getIcMarkerId().getEdtTextValue().matches(markeridRegex)) {
				ToastUtil.show(mActivity, "标识器id格式必须为〇〇〇-〇〇〇-〇〇〇〇", 200);
				return;
			}
		}

		ChannelControll channelControll = channelFragment.getAction();
		ArrayList<ChannelDataEntity> channelList = channelControll.getChannelList();
		if (null == channelList || channelList.size() < 1) {
			showToast("至少添加一条通道！");
			viewHolder.getLlChannelInfo().performClick();
			return;
		}
		// 检查同沟信息
		LayingDitchControll layingDitchControll = layingDitchFragment.getAction();
		List<DitchCable> ditchCableList = layingDitchControll.getDitchCableList();
		if (ditchCableList == null || ditchCableList.size() < 1) {
			ToastUtil.show(mActivity, "同沟电缆至少包含一条！", 200);
			viewHolder.getLlDeviceInfo().performClick();
			return;
		}
		if (layingDitchControll != null) {
			String cableLoop = layingDitchControll.getViewHolder().getIcLoopNum().getEdtTextValue();
			if (cableLoop.isEmpty()) {
				ToastUtil.show(mActivity, "同沟回路数不能为空！", 200);
				viewHolder.getLlDeviceInfo().performClick();
				return;
			}
		}

		// 1、保存基本信息
		EcNodeEntity ecNodeEntity = basicInfoControll.saveBasicInfo();
		if (ecNodeEntity == null) {
			return;
		}
		// 刷新树
		TreeRefleshUtil treeReflesh = TreeRefleshUtil.getSingleton();
		if (treeReflesh != null) {
			TreeRefleshUtil.getSingleton().refleshEcNode(ecNodeEntity);
		}

		// 保存通道
		channelFragment.getAction().saveChannel(ecNodeEntity, LayingDitchControll.ditchCableList,
				layingDitchControll.edtLineList);
		// 2、保存同沟信息
		if (layingDitchControll != null && ecNodeEntity != null) {
			layingDitchFragment.getAction().saveLayingDitchInfo(ecNodeEntity);
		}
		// 3、保存路径点对应的附件
		ScenePhotoControll scenePhotoControll = scenePhotoFragment.getAction();
		if (scenePhotoControll != null) {
			scenePhotoFragment.getAction().saveScenePhotoInfo(ecNodeEntity.getOid());
		}

		// 4、保存通道隐患信息
		ChannelNodeStatusControll channelNodeStatusControll = channelNodeStatusFragment.getAction();
		if (channelNodeStatusControll != null) {
			channelNodeStatusControll.saveChannelDanger(ecNodeEntity);
		}

		// 更新数节点
		if (GlobalEntry.editNode == true) {

		}

		// 重置静态对象
		BasicInfoControll.ecWorkWellEntityVo = null;

		// 保存成功
		ToastUtil.show(mActivity, "保存成功", 200);
		mActivity.finish();

		// 刷新台账树
		TreeRefleshUtil singleton = TreeRefleshUtil.getSingleton();
		if (null != singleton) {
			singleton.refleshWholeTree();
		}
	}

	/**
	 * 获取基本信息片段对象
	 * 
	 * @return
	 */
	public BasicInfoFragment getBasicInfoFragment() {
		return basicFragment;
	}

	/**
	 * 获取同沟信息片段
	 * 
	 * @return
	 */
	public LayingDitchFragment getLayingDitchFragment() {
		return layingDitchFragment;
	}

	/**
	 * 获取现场照片片段
	 * 
	 * @return
	 */
	public ScenePhotoFragment getScenePhotoFragment() {
		return scenePhotoFragment;
	}

	/**
	 * 实现选中后的 tabs的状态
	 * 
	 * @param i
	 */
	private void setTabsSelected(int i) {
		switch (i) {
		case 0: {// 基本信息
			viewHolder.getTvBasicInfo().setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab_f));
			viewHolder.getLlBasicInfo()
					.setBackground(((Context) mActivity).getResources().getDrawable(R.drawable.underline_tab));
			break;
		}
		case 1: {// 通道信息
			viewHolder.getTvChannelInfo()
					.setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab_f));
			viewHolder.getLlChannelInfo()
					.setBackground(((Context) mActivity).getResources().getDrawable(R.drawable.underline_tab));
			break;
		}
		case 2: {// 同沟信息
			viewHolder.getTvDeviceInfo()
					.setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab_f));
			viewHolder.getLlDeviceInfo()
					.setBackground(((Context) mActivity).getResources().getDrawable(R.drawable.underline_tab));
			break;
		}
		case 3: {// 现场照片
			viewHolder.getTvScenePhoto()
					.setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab_f));
			viewHolder.getLlScenePhoto()
					.setBackground(((Context) mActivity).getResources().getDrawable(R.drawable.underline_tab));
			break;
		}
		case 4: {// 通道节点状态
			viewHolder.getTvChannelnodeStatus()
					.setTextColor(((Context) mActivity).getResources().getColor(R.color.main_tab_f));
			viewHolder.getLlChannelnodeStatus()
					.setBackground(((Context) mActivity).getResources().getDrawable(R.drawable.underline_tab));
			break;
		}
		default:
			break;
		}
		// 切换 viewpage item
		viewHolder.getVpmarkMainVp().setCurrentItem(i);
		selectedfragment = i;
	}

	/**
	 * 实现滑动 切换 tabs
	 * 
	 * @author yuan
	 */
	class vpOnChangeListener extends SimpleOnPageChangeListener {
		@Override
		public void onPageSelected(int position) {
			// 设置 tab 背景
			resetTabs();
			setTabsSelected(position);
		}
	}

	@Override
	public List<Fragment> getFragments() {
		ArrayList<Fragment> f = new ArrayList<Fragment>();
		// 基本信息页
		basicFragment = new BasicInfoFragment();
		f.add(basicFragment);
		// 通道
		channelFragment = new ChannelFragment();
		f.add(channelFragment);
		// 同沟
		layingDitchFragment = new LayingDitchFragment();
		f.add(layingDitchFragment);
		// 现场照片
		scenePhotoFragment = new ScenePhotoFragment();
		f.add(scenePhotoFragment);
		// 通道节点现状
		channelNodeStatusFragment = new ChannelNodeStatusFragment();
		f.add(channelNodeStatusFragment);
		return f;
	}

	@Override
	public int content_id() {
		return R.id.vp_marker_collect_viewpage;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			viewHolder.getHcHead().getReturnView().performClick();
		}
		return false;
	}
}
