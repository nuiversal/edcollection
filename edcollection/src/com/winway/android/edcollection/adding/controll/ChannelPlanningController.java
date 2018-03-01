package com.winway.android.edcollection.adding.controll;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnLongClickListener;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.bll.CollectCommonBll;
import com.winway.android.edcollection.adding.customview.CompassView.OnDegreesChangeListener;
import com.winway.android.edcollection.adding.customview.GJDrawView;
import com.winway.android.edcollection.adding.customview.GJDrawView.OnDoubleClickPointListener;
import com.winway.android.edcollection.adding.customview.Point;
import com.winway.android.edcollection.adding.entity.ChannelSectionOperationEnum;
import com.winway.android.edcollection.adding.entity.channelplaning.ChannelPlaningEntity;
import com.winway.android.edcollection.adding.entity.channelplaning.JMEntity;
import com.winway.android.edcollection.adding.util.ChannelPlaningUtil;
import com.winway.android.edcollection.adding.util.DialogChannelPointSelectUtil;
import com.winway.android.edcollection.adding.util.DrawViewUtil;
import com.winway.android.edcollection.adding.util.ShowChannelPointToolsUtils;
import com.winway.android.edcollection.adding.util.TreeRefleshUtil;
import com.winway.android.edcollection.adding.viewholder.ChannelPlanningViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.base.util.ResourceDeviceUtil;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcChannelSectionEntity;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.ewidgets.input.InputSelectComponent.DropDownSelectListener;
import com.winway.android.util.DateUtils;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.ImageHelper;
import com.winway.android.util.StringUtils;
import com.winway.android.util.ToastUtils;
import com.winway.android.util.UUIDGen;

/**
 * 通道截面采集
 * 
 * @author mr-lao
 *
 */
public class ChannelPlanningController extends BaseControll<ChannelPlanningViewHolder> {
	private DialogChannelPointSelectUtil dialogChannelPointSelectUtil;
	private ShowChannelPointToolsUtils dialogToolsUtils = null;
	private String channelId, channelName, nodeId;
	private BaseDBUtil projectBDUtil = null;
	boolean isNeedSave = false;
	private CollectCommonBll collectCommonBll;
	private List<Point> pointList = new ArrayList<Point>();

	// 浮点数格式化
	private static DecimalFormat decimalFormat = new DecimalFormat("###.##");

	// 通道实体
	private ChannelPlaningEntity channelPlaningEntity;

	@Override
	public void init() {
		projectBDUtil = new BaseDBUtil(mActivity, new File(GlobalEntry.prjDbUrl));
		collectCommonBll = new CollectCommonBll(mActivity, GlobalEntry.prjDbUrl);
		EventBus.getDefault().register(this);
		initSetting();
		initDatas();
		initEvent();
	}

	void initSetting() {
		// 截面大小
		List<String> datas = new ArrayList<String>();
		datas.add(ChannelSectionOperationEnum.xy.getName());
		datas.add(ChannelSectionOperationEnum.zy.getName());
		datas.add(ChannelSectionOperationEnum.dy.getName());
		InputSelectAdapter adapter = new InputSelectAdapter(mActivity, datas);
		viewHolder.getInscSize().setAdapter(adapter);
		viewHolder.getInscSize().getEditTextView()
				.setText(ChannelSectionOperationEnum.zy.getName());
		viewHolder.getInscSize().getEditTextView().setFocusable(false);
//		viewHolder.getInconJmjd().getEditTextView().setFocusable(false);
		// viewHolder.getInconJmmc().getEditTextView().setFocusable(false);
		viewHolder.getInconSstd().getEditTextView().setEnabled(false);
		viewHolder.getInconSstd().setOnFocusChangeListener(null);
		viewHolder.getInconJmjd().getEditTextView().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
	}

	boolean shouldInitMiniDrawView = false;

	private void initDatas() {
		dialogToolsUtils = new ShowChannelPointToolsUtils(mActivity, viewHolder.getDrawView(),
				viewHolder.getCompassContainer(), viewHolder.getMenuContainer());

		Intent intent = getIntent();
		channelId = intent.getStringExtra("channel_id");
		channelName = intent.getStringExtra("channel_name");
		nodeId = intent.getStringExtra("node_id");
		isNeedSave = intent.getStringExtra("isNeedSave").equals("1") ? true : false;
		viewHolder.getInconSstd().setEdtText(channelName);
		channelPlaningEntity = ChannelPlaningUtil.getChannelPlanning(channelId, nodeId,
				new BaseDBUtil(mActivity, GlobalEntry.prjDbUrl));
		if (null == channelPlaningEntity) {
			channelPlaningEntity = new ChannelPlaningEntity();
			channelPlaningEntity.setChannelId(channelId);
			channelPlaningEntity.setJmList(new ArrayList<JMEntity>());
		} else {
			shouldInitMiniDrawView = true;
			/*
			 * JMEntity jmEntity = channelPlaningEntity.getJmList().get(0);
			 * setChannelPlaningJM(jmEntity.getName(), jmEntity.getDegress() +
			 * "", jmEntity.getPointList(), jmEntity.getZjList(),
			 * jmEntity.getBackgroundImage());
			 */
			dialogToolsUtils.JM_VR_BG_IMG_PATH = channelPlaningEntity.getBackgroundVrImage();
			dialogToolsUtils.JM_VR_PS_IMG_PATH = channelPlaningEntity.getPositionVrImage();
		}
	}

	// private Handler h = new Handler();

	/**
	 * 初始化
	 */
	void initMiniDrawView() {
		for (JMEntity jm : channelPlaningEntity.getJmList()) {
			addMiniDrawViewInContainer(jm);
		}
	}

	/**
	 * 设置通道截面
	 * 
	 * @param jmName
	 *            截面名称
	 * @param degress
	 *            截面角度
	 * @param points
	 *            截面管径
	 * @param zjpoints
	 *            截面支架
	 */
	private void setChannelPlaningJM(String jmName, String degress, List<Point> points,
			List<Point> zjpoints, String jmBgImg) {
		viewHolder.getDrawView().setPoints(points);
		viewHolder.getInconJmmc().getEditTextView().setText(StringUtils.nullStrToEmpty(jmName));
		viewHolder.getInconJmjd().setEdtText(StringUtils.nullStrToEmpty(degress));
		viewHolder.getDrawView().setLinesLocation(zjpoints);
		if (TextUtils.isEmpty(jmBgImg)) {
			viewHolder.getDrawView().setBackgroundColor(Color.WHITE);
		} else {
			@SuppressWarnings("deprecation")
			BitmapDrawable drawable = new BitmapDrawable(
					ImageHelper.decodeSampledBitmapFromImagePath(jmBgImg, 540, 960));
			viewHolder.getDrawView().setBackground(drawable);
			viewHolder.getDrawView().setTag(jmBgImg);
		}
	}

	private boolean isFirstLayoutChange = true;

	private String direction = "";

	private void initEvent() {
		/** 保存好的截面数据添加到迷你画板 */
		viewHolder.getDrawView().addOnLayoutChangeListener(new OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom,
					int oldLeft, int oldTop, int oldRight, int oldBottom) {
				if (isFirstLayoutChange) {
					if (shouldInitMiniDrawView) {
						initMiniDrawView();
					}
				}
				isFirstLayoutChange = false;
			}
		});
		// 画笔大小切换
		viewHolder.getInscSize().setDropDownSelectListener(new DropDownSelectListener() {
			@Override
			public void handle(int index) {
				if (index == ChannelSectionOperationEnum.xy.getValue()) {
					viewHolder.getDrawView().setCircleR(GJDrawView.POINT_R_NORMAL);

				} else if (index == ChannelSectionOperationEnum.zy.getValue()) {
					viewHolder.getDrawView().setCircleR(GJDrawView.POINT_R_MEDIUM);

				} else if (index == ChannelSectionOperationEnum.dy.getValue()) {
					viewHolder.getDrawView().setCircleR(GJDrawView.POINT_R_LARGE);
				}
			}
		});
		// 画板双击事件
		viewHolder.getDrawView().setOnDoubleClickPointListener(new OnDoubleClickPointListener() {
			@Override
			// TODO Auto-generated method stub
			public boolean clickResult(Point p) {
				if (null == dialogChannelPointSelectUtil) {
					dialogChannelPointSelectUtil = new DialogChannelPointSelectUtil(mActivity,
							viewHolder.getDrawView(), false);
				}
				dialogChannelPointSelectUtil.showSelectingLineDialog(p, false);
				return true;
			}
		});
		/**
		 * 进入侧滑栏
		 */
		viewHolder.getIvDown().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogToolsUtils.showMenu();
			}
		});
		// 罗盘
		viewHolder.getCompassView().setOnDreesChangeListener(new OnDegreesChangeListener() {
			@Override
			public void degreesChange(float degrees, String direction) {
				viewHolder.getCompassDgressTV().setText(decimalFormat.format(degrees));
				ChannelPlanningController.this.direction = direction;
			}
		});
		viewHolder.getCompassSureBT().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewHolder.getInconJmjd().setEdtText(
						viewHolder.getCompassDgressTV().getText().toString());
				viewHolder.getCompassContainer().setVisibility(View.GONE);
				viewHolder.getInconJmmc().setEdtText(direction + "截面");
			}
		});
		// 添加截面
		viewHolder.getAddJmBT().setOnClickListener(orcl);
		// 保存数据
		viewHolder.getSaveDataBT().setOnClickListener(orcl);

	}

	/**
	 * 共用的点击事件监听器
	 */
	private OnClickListener orcl = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.activity_channel_planing_save_data :// 采集完成
					// saveData();
					saveData_old();
					break;
				case R.id.activity_channel_planing_add_jm :// 保存截面
					addJM();
					break;
			}
		}
	};

	/**
	 * 保存数据
	 */
	private void saveData() {
		// 检查数据
		if (TextUtils.isEmpty(viewHolder.getInconJmmc().getEdtTextValue())) {
			ToastUtils.show(mActivity, "截面名称不能为空");
			return;
		}
		if (TextUtils.isEmpty(viewHolder.getInconJmjd().getEdtTextValue())) {
			ToastUtils.show(mActivity, "截面角度不能为空");
			return;
		}
		if (null == viewHolder.getDrawView().getPoints()
				|| viewHolder.getDrawView().getPoints().isEmpty()) {
			ToastUtils.show(mActivity, "截面应该有管孔");
			return;
		}
		// 创建截面实体
		JMEntity jmEntity = new JMEntity();
		jmEntity.setPointList(copyDrawViewPoints(viewHolder.getDrawView()));
		jmEntity.setDrawViewHeight(viewHolder.getDrawView().getHeight());
		jmEntity.setDwawViewWidth(viewHolder.getDrawView().getWidth());
		jmEntity.setName(viewHolder.getInconJmmc().getEdtTextValue());
		jmEntity.setZjList(viewHolder.getDrawView().getLinesLocation());
		jmEntity.setUuid(UUIDGen.randomUUID());
		jmEntity.setDegress(Float.parseFloat(viewHolder.getInconJmjd().getEdtTextValue()));
		jmEntity.setBackgroundImage((String) viewHolder.getDrawView().getTag());
		ArrayList<JMEntity> jmList = new ArrayList<>();
		jmList.add(jmEntity);
		channelPlaningEntity.setJmList(jmList);
		// 保存VR照片
		channelPlaningEntity.setBackgroundVrImage(dialogToolsUtils.JM_VR_BG_IMG_PATH);
		channelPlaningEntity.setPositionVrImage(dialogToolsUtils.JM_VR_PS_IMG_PATH);
		ChannelPlaningUtil.putChannelPlaningEntity(channelPlaningEntity);
		mActivity.finish();
	}

	/** 保存数据 */
	private void saveData_old() {
		if (null == miniDrawViewList || miniDrawViewList.isEmpty()) {
			if (viewHolder.getDrawView().getPoints() == null
					|| viewHolder.getDrawView().getPoints().isEmpty()) {
				// 删除
				ChannelPlaningUtil.removeChannelPlanningEntity(channelId);
				mActivity.setResult(Activity.RESULT_OK);
				mActivity.finish();
			} else {
				ToastUtils.show(mActivity, "目前没有截面数据，请先保存当前截面数据");
			}
			// 刷新树
			// TreeRefleshUtil.getSingleton().refleshWholeTree();
			return;
		}
		ArrayList<JMEntity> jmList = new ArrayList<>();
		for (GJDrawView drawView : miniDrawViewList) {
			jmList.add((JMEntity) drawView.getTag());
		}
		channelPlaningEntity.setJmList(jmList);
		// 保存VR照片
		channelPlaningEntity.setBackgroundVrImage(dialogToolsUtils.JM_VR_BG_IMG_PATH);
		channelPlaningEntity.setPositionVrImage(dialogToolsUtils.JM_VR_PS_IMG_PATH);
		ChannelPlaningUtil.putChannelPlaningEntity(channelPlaningEntity);
		if (isNeedSave) {
			// 截面实体（一个截面对应一个截面实体）
			ArrayList<EcChannelSectionEntity> sectionList = new ArrayList<>();
			if (jmList != null && !jmList.isEmpty()) {
				for (JMEntity jm : jmList) {
					// 通道截面实体
					EcChannelSectionEntity ecChannelSectionEntity = ChannelPlaningUtil
							.getEcChannelSection(jm.getUuid());
					if (null == ecChannelSectionEntity) {
						ecChannelSectionEntity = new EcChannelSectionEntity();
						ecChannelSectionEntity.setEcChannelSectionId(UUIDGen.randomUUID());
						ecChannelSectionEntity.setEcChannelId(channelId);
						ecChannelSectionEntity.setEcNodeId(nodeId);
						ecChannelSectionEntity
								.setPrjid(GlobalEntry.currentProject.getEmProjectId());
						ecChannelSectionEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
						ecChannelSectionEntity.setCjsj(DateUtils.getDate());
					}
					if (BasicInfoControll.ecWorkWellEntityVo != null) {
						// 关联工井
						ecChannelSectionEntity.setResType(ResourceDeviceUtil
								.getDeviceCode(EcWorkWellEntityVo.class));
						ecChannelSectionEntity.setObjid(BasicInfoControll.ecWorkWellEntityVo
								.getObjId());
					}
					ecChannelSectionEntity.setRotate((double) jm.getDegress());
					// 将截面的UUID设置成截面实体的主键
					jm.setUuid(ecChannelSectionEntity.getEcChannelSectionId());
					ecChannelSectionEntity.setSectionDesc(GsonUtils.build().toJson(jm));
					ecChannelSectionEntity.setGxsj(DateUtils.getDate());
					sectionList.add(ecChannelSectionEntity);
				}
			}
			try {
				projectBDUtil.saveOrUpdate(sectionList);
				// 保存日志信息
				if (null != sectionList && !sectionList.isEmpty()) {
					for (EcChannelSectionEntity ecChannelSectionEntity : sectionList) {
						collectCommonBll.handleDataLog(
								ecChannelSectionEntity.getEcChannelSectionId(),
								TableNameEnum.EC_CHANNEL_SECTION.getTableName(),
								DataLogOperatorType.Add, WhetherEnum.NO, "插入通道截面数据"
										+ ecChannelSectionEntity.getEcChannelSectionId(), true);
					}
				}
			} catch (DbException e) {
				e.printStackTrace();
			}
			// 刷新台帐树
			TreeRefleshUtil util = TreeRefleshUtil.getSingleton();
			if (util != null) {
				util.refleshWholeTree();
			}
		}
		mActivity.setResult(Activity.RESULT_OK);
		mActivity.finish();
	}

	/** 添加截面 */
	private void addJM() {
		// 检查数据
		if (TextUtils.isEmpty(viewHolder.getInconJmmc().getEdtTextValue())) {
			ToastUtils.show(mActivity, "截面名称不能为空");
			return;
		}
		if (TextUtils.isEmpty(viewHolder.getInconJmjd().getEdtTextValue())) {
			ToastUtils.show(mActivity, "截面角度不能为空");
			return;
		}
		if (null == viewHolder.getDrawView().getPoints()
				|| viewHolder.getDrawView().getPoints().isEmpty()) {
			ToastUtils.show(mActivity, "截面应该有管孔");
			return;
		}

		// 获取选中的迷你画板
		GJDrawView selectedDrawView = DrawViewUtil.getSelectedDrawView(miniDrawViewList);
		if (null == selectedDrawView) {
			// 创建截面实体
			JMEntity jmEntity = new JMEntity();
			jmEntity.setPointList(copyDrawViewPoints(viewHolder.getDrawView()));
			jmEntity.setDrawViewHeight(viewHolder.getDrawView().getHeight());
			jmEntity.setDwawViewWidth(viewHolder.getDrawView().getWidth());
			jmEntity.setName(viewHolder.getInconJmmc().getEdtTextValue());
			jmEntity.setZjList(viewHolder.getDrawView().getLinesLocation());
			jmEntity.setUuid(UUIDGen.randomUUID());
			jmEntity.setDegress(Float.parseFloat(viewHolder.getInconJmjd().getEdtTextValue()));
			jmEntity.setBackgroundImage((String)viewHolder.getDrawView().getTag());
			// 添加迷你画板
			addMiniDrawViewInContainer(jmEntity);
		} else {
			JMEntity jmEntity = (JMEntity) selectedDrawView.getTag();
			jmEntity.setPointList(copyDrawViewPoints(viewHolder.getDrawView()));
			jmEntity.setDrawViewHeight(viewHolder.getDrawView().getHeight());
			jmEntity.setDwawViewWidth(viewHolder.getDrawView().getWidth());
			jmEntity.setName(viewHolder.getInconJmmc().getEdtTextValue());
			jmEntity.setZjList(viewHolder.getDrawView().getLinesLocation());
			jmEntity.setDegress(Float.parseFloat(viewHolder.getInconJmjd().getEdtTextValue()));
			jmEntity.setBackgroundImage((String)viewHolder.getDrawView().getTag());
			// 当小面板中管孔数据不等于保存截面前画板中的管孔数据或者画板拖动了编辑器
			if (pointList.size() != jmEntity.getPointList().size() || selectedDrawView.isTouch) {
				// 刷新小面板
				refreshMiniDrawView(selectedDrawView, jmEntity);
				selectedDrawView.isTouch = false;
			}
		}

		DrawViewUtil.selectNull(miniDrawViewList);
		// 清空当前采集截面数据
		setChannelPlaningJM(null, null, new ArrayList<Point>(), null, null);
	}

	/**
	 * 刷新迷你面板
	 */
	private void refreshMiniDrawView(GJDrawView selectedDrawView, JMEntity jmEntity) {
		viewHolder.getMiniDrawViewContainer().removeView(selectedDrawView);
		miniDrawViewList.remove(selectedDrawView);
		GJDrawView miniDrawView = DrawViewUtil.createMiniDrawView(mActivity,
				viewHolder.getDrawView(), jmEntity.getPointList(), jmEntity.getZjList());
		miniDrawView.setTag(jmEntity);
		viewHolder.getMiniDrawViewContainer().addView(miniDrawView);
		// 设置迷你画板点击事件
		miniDrawView.setOnClickListener(miniDrawViewClickListener);
		miniDrawView.setOnLongClickListener(miniDrawViewLongClickListener);
		// 添加到迷你画板集合中
		miniDrawViewList.add(miniDrawView);
	}

	/**
	 * 把截面数据添加进迷你画板，顺便保存截面数据
	 * 
	 * @param jmEntity
	 */
	void addMiniDrawViewInContainer(JMEntity jmEntity) {
		GJDrawView miniDrawView = DrawViewUtil.createMiniDrawView(mActivity,
				viewHolder.getDrawView(), jmEntity.getPointList(), jmEntity.getZjList());
		// 把截面数据存放到迷你画板中
		miniDrawView.setTag(jmEntity);
		viewHolder.getMiniDrawViewContainer().addView(miniDrawView);
		// 设置迷你画板点击事件
		miniDrawView.setOnClickListener(miniDrawViewClickListener);
		miniDrawView.setOnLongClickListener(miniDrawViewLongClickListener);
		// 添加到迷你画板集合中
		miniDrawViewList.add(miniDrawView);
	}

	static ArrayList<Point> copyDrawViewPoints(GJDrawView drawView) {
		ArrayList<Point> list = new ArrayList<>();
		for (Point point : drawView.getPoints()) {
			Point p = new Point(point.getX(), point.getY(), point.getCircleR());
			p.setLineId(point.getLineId());
			p.setIsPlugging(point.getIsPlugging());
			p.setR(point.getR());
			p.setLineName(point.getLineName() == null ? "空管" : point.getLineName());
			p.setFillColor(point.getFillColor());
			p.setPhaseSeq(point.getPhaseSeq() == null ? "无" :point.getPhaseSeq());
			p.setNo(point.getNo());
			list.add(p);
		}
		return list;
	}

	// 迷你画板集合
	private ArrayList<GJDrawView> miniDrawViewList = new ArrayList<>();

	// 迷你画板点击事件
	private OnClickListener miniDrawViewClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 选择迷你视图
			DrawViewUtil.selectMiniDrawView((GJDrawView) v, miniDrawViewList);
			// 切换选中的内容
			JMEntity jm = (JMEntity) v.getTag();
			setChannelPlaningJM(jm.getName(), jm.getDegress() + "", jm.getPointList(),
					jm.getZjList(), jm.getBackgroundImage());
			if (pointList != null) {
				pointList.clear();
			}
			pointList.addAll(jm.getPointList());
		}
	};

	DialogUtil deleteDialogUtil;
	// 被删除的迷你画板
	private GJDrawView deleteMiniDrawView = null;

	// 迷你画板长按删除
	private OnLongClickListener miniDrawViewLongClickListener = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			deleteMiniDrawView = (GJDrawView) v;
			// 选择迷你视图
			DrawViewUtil.selectMiniDrawView((GJDrawView) v, miniDrawViewList);
			// 切换选中的内容
			JMEntity jm = (JMEntity) v.getTag();
			setChannelPlaningJM(jm.getName(), jm.getDegress() + "", jm.getPointList(), null,
					jm.getBackgroundImage());
			// 删除对话框
			if (null == deleteDialogUtil) {
				deleteDialogUtil = new DialogUtil(mActivity);
			}
			deleteDialogUtil.showAlertDialog("确定要删除此选中的截面吗", new String[]{"取消", "删除"},
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (which == 1) {
								if (null != deleteMiniDrawView) {
									// 删除背景图
									JMEntity jmEntity = (JMEntity) deleteMiniDrawView.getTag();
									if (!TextUtils.isEmpty(jmEntity.getBackgroundImage())) {
										File file = new File(jmEntity.getBackgroundImage());
										file.delete();
									}
									viewHolder.getMiniDrawViewContainer().removeView(
											deleteMiniDrawView);
									miniDrawViewList.remove(deleteMiniDrawView);
									// 清空画板
									setChannelPlaningJM(null, null, new ArrayList<Point>(), null,
											null);
									// EcChannelSectionEntity
									// ecChannelSectionEntity =
									// ChannelPlaningUtil.getEcChannelSection(jmEntity.getUuid());
									try {
										projectBDUtil.deleteById(EcChannelSectionEntity.class,
												jmEntity.getUuid());
									} catch (DbException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							deleteMiniDrawView = null;
							deleteDialogUtil.dismissDialog();
						}
					}, false);
			return false;
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		dialogToolsUtils.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (null != dialogToolsUtils) {
			dialogToolsUtils.onResume();
		}
	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void addHistoryJMsInfo(JMEntity jmEntity) {
		if (jmEntity == null) {
			return;
		}
		viewHolder.getDrawView().setPoints(jmEntity.getPointList());
		viewHolder.getDrawView().setLinesLocation(jmEntity.getZjList());
	}

}
