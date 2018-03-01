package com.winway.android.edcollection.adding.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.LookVRImageActivity;
import com.winway.android.edcollection.adding.adapter.BracketAdapter;
import com.winway.android.edcollection.adding.customview.GJDrawView;
import com.winway.android.edcollection.adding.customview.Point;
import com.winway.android.edcollection.adding.entity.ChannelBracketEntity;
import com.winway.android.edcollection.adding.entity.channelplaning.JMEntity;
import com.winway.android.edcollection.adding.viewholder.DialogShowGridHelpViewHolder;
import com.winway.android.edcollection.adding.viewholder.ShowChannelPointToolsViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcChannelSectionEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.ewidgets.attachment.RicohUtil;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.DeviceUtils;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.FileUtil;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.ImageHelper;
import com.winway.android.util.ListUtil;
import com.winway.android.util.PhotoUtil;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.ToastUtils;

/**
 * @author lyh
 * @version 创建时间：2017年4月20日
 *
 */
public class ShowChannelPointToolsUtils {
	private Activity mActivity;
	private GJDrawView gjDrawView;
	private View compassContainer;
	private ShowChannelPointToolsViewHolder viewHolder;
	private DialogShowGridHelpViewHolder wgViewHolder;
	// 圆点（管线）
	private List<Point> pointList = new ArrayList<Point>();
	private View view;
	private AlertDialog dialog = null;
	private AlertDialog.Builder builder;
	private View wgView;
	boolean isMoreVisilibity = true;
	boolean isLpVisilibity = true;
	private boolean isUnknowLine = false;
	private BaseDBUtil dbUtil;
	private String rootPath;
	
	/**
	 * 是否折叠
	 */
	private boolean isFold = false;
	ArrayList<Point> pos;
	//public static boolean isTakePhoto = false;// 是否拍照
	// 工井支架信息
	private ArrayList<ChannelBracketEntity> GJZJList = new ArrayList<ChannelBracketEntity>();

	public ShowChannelPointToolsUtils(Activity mActivity, GJDrawView gjDrawView, View compassContainer,
			View menuContainer) {
		super();
		this.mActivity = mActivity;
		this.gjDrawView = gjDrawView;
		this.compassContainer = compassContainer;
		viewHolder = new ShowChannelPointToolsViewHolder();
		wgViewHolder = new DialogShowGridHelpViewHolder();
		// view = View.inflate(mActivity, R.layout.layout_menu, null);
		builder = new AlertDialog.Builder(mActivity);
		wgView = View.inflate(mActivity, R.layout.dialog_wg_setting, null);
		builder.setView(wgView);
		ViewUtils.inject(wgViewHolder, wgView);

		view = menuContainer;
		ViewUtils.inject(viewHolder, view);
		dbUtil = new BaseDBUtil(mActivity, GlobalEntry.prjDbUrl);
		ComUploadBll comUploadBll = new ComUploadBll(mActivity, GlobalEntry.prjDbUrl);
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		initDatas();
		initEvent();
	}

	private void initDatas() {
		/*
		 * WindowManager wm = (WindowManager)
		 * mActivity.getSystemService(Context.WINDOW_SERVICE); int width =
		 * wm.getDefaultDisplay().getWidth() / 2; FrameLayout.LayoutParams params = new
		 * FrameLayout.LayoutParams(width, FrameLayout.LayoutParams.MATCH_PARENT); //
		 * 设置左边布局 params.gravity = Gravity.LEFT; mActivity.addContentView(view, params);
		 */

		wgViewHolder.getInconX().getEditTextView().setText("3");
		wgViewHolder.getInconY().getEditTextView().setText("3");
		wgViewHolder.getInconX().getEditTextView().setInputType(InputType.TYPE_CLASS_NUMBER);
		wgViewHolder.getInconY().getEditTextView().setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	private void initEvent() {
		viewHolder.getIvClose().setOnClickListener(ocl);
		viewHolder.getIvFold().setOnClickListener(ocl);
		viewHolder.getTvWgfz().setOnClickListener(ocl);
		viewHolder.getTvZjzj().setOnClickListener(ocl);
		viewHolder.getTvQxzj().setOnClickListener(ocl);
		viewHolder.getTvUnknowLine().setOnClickListener(ocl);
		viewHolder.getOpenLp().setOnClickListener(ocl);
		viewHolder.getTvHistroyImg().setOnClickListener(ocl);
		wgViewHolder.getBtnGridSure().setOnClickListener(ocl);
		wgViewHolder.getBtnNoGrid().setOnClickListener(ocl);
		viewHolder.getRgOperation().setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int id = group.getCheckedRadioButtonId();
				switch (id) {
				case R.id.rb_activity_channel_planing_read: // 查看
				{
					gjDrawView.setTouchEditor(gjDrawView.getTouchEditor(GJDrawView.TOUCH_EDITOE_LOOK));
				}
					break;
				case R.id.rb_activity_channel_planing_edit: // 编辑
				{
					gjDrawView.setTouchEditor(gjDrawView.getTouchEditor(GJDrawView.TOUCH_EDITOE_DRAG));
				}
					break;
				case R.id.rb_activity_channel_planing_del: // 删除
				{
					gjDrawView.setTouchEditor(gjDrawView.getTouchEditor(GJDrawView.TOUCH_EDITOE_DELETE));
				}
					break;

				default:
					break;
				}
				dismissMenu();
			}
		});
		viewHolder.getJmBgImg().setOnClickListener(ocl);
		viewHolder.getVrBgImg().setOnClickListener(ocl);
		viewHolder.getVrPsImg().setOnClickListener(ocl);
		viewHolder.getLookVrBgImg().setOnClickListener(ocl);
		viewHolder.getLookVrPsImg().setOnClickListener(ocl);
	}

	public void showMenu() {
		view.setVisibility(View.VISIBLE);
	}

	public void dismissMenu() {
		view.setVisibility(View.GONE);
	}

	private OnClickListener ocl = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_layout_menu_close: {// 关闭
				if (isMoreVisilibity) {
					view.setVisibility(View.GONE);
				} else {
					view.setVisibility(View.VISIBLE);
				}

				break;
			}
			case R.id.iv_layout_menu_fold: {// 折叠
				if (isFold) {
					isFold = false;
					((ImageView) view.findViewById(R.id.iv_layout_menu_fold)).setImageResource(R.drawable.ic_up);
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
							RelativeLayout.LayoutParams.MATCH_PARENT, DeviceUtils.getScreenHeight(mActivity));
					view.setLayoutParams(params);
				} else {
					isFold = true;
					((ImageView) view.findViewById(R.id.iv_layout_menu_fold)).setImageResource(R.drawable.ic_down);
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
							RelativeLayout.LayoutParams.MATCH_PARENT, 120);
					view.setLayoutParams(params);
				}
				break;
			}

			case R.id.tv_layout_menu_wgfz: // 网格辅助-空管
			{
				isUnknowLine = false;
				showWGDialog();
				break;
			}
			case R.id.tv_layout_menu_wgfz_unknow_line: // 网格辅助-未知线路
			{
				isUnknowLine = true;
				showWGDialog();
				break;
			}

			case R.id.btn_gj_dl_not_grid: // 无网格
			{
				gjDrawView.setDrawMachine(gjDrawView.getDrawMachine(GJDrawView.DRAWMACHINE_DEFAULT));
				dialog.dismiss();
				break;
			}

			case R.id.tv_layout_menu_lp: // 打开罗盘
			{
				if (isLpVisilibity) {
					compassContainer.setVisibility(View.VISIBLE);
					isLpVisilibity = false;
				} else {
					compassContainer.setVisibility(View.GONE);
					isLpVisilibity = true;
				}
				break;
			}

			case R.id.btn_gj_dl_grid_sure:// 增加网格
			{
				String valueX = wgViewHolder.getInconX().getEdtTextValue().trim();
				String valueY = wgViewHolder.getInconY().getEdtTextValue().trim();
				if (valueX.isEmpty()) {
					ToastUtils.show(mActivity, "请输入行数");
					return;
				}
				if (valueY.isEmpty()) {
					ToastUtils.show(mActivity, "请输入列数");
					return;
				}
				int x = Integer.parseInt(valueX);
				int y = Integer.parseInt(valueY);
				if (isUnknowLine) {
					pointList = gjDrawView.addPointsRetList(x, y);
					for (Point point : pointList) {
						new DialogChannelPointSelectUtil(mActivity, gjDrawView, isUnknowLine)
								.showSelectingLineDialog(point, isUnknowLine);
						;
					}
					isUnknowLine = false;
				} else {
					gjDrawView.addPoints(x, y);
				}
				dialog.dismiss();
				break;
			}

			case R.id.tv_layout_menu_zjzj: // 增加支架
			{
				addBracket();
				break;
			}

			case R.id.tv_layout_menu_qxzj: // 取消支架
			{
				gjDrawView.setLinesLocation(0, 0);
				break;
			}

			case R.id.tv_layout_menu_jm_bg_image:// 截面背景图
			{
				// 背景图
				String filename = System.currentTimeMillis() + "_JMBG.jpg";
				JM_BG_IMG_PATH = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() +
						File.separator + "截面背景" + File.separator + filename;
				PhotoUtil.takePhoto(mActivity, rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() +
						File.separator + "截面背景" + File.separator, filename, 9999);
				break;
			}
			case R.id.tv_layout_menu_vr_bg_image:// 截面VR背景图
			{
				// 背景图
				if (!RicohUtil.openRicohAPP(mActivity)) {
					break;
				}
				String filename = System.currentTimeMillis() + "_JMVRBG.jpg";
				JM_VR_BG_IMG_PATH = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() +
						File.separator + "截面背景" + File.separator +  filename;
				openRicohTime = System.currentTimeMillis();
				which_vr_img = vr_bg;
				break;
			}
			case R.id.tv_layout_menu_vr_ps_image:// 截面VR前景图
			{
				// 背景图
				if (!RicohUtil.openRicohAPP(mActivity)) {
					break;
				}
				String filename = System.currentTimeMillis() + "_JMVRPS.jpg";
				JM_VR_PS_IMG_PATH = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() +
						File.separator + "截面背景" + File.separator +  filename;
				openRicohTime = System.currentTimeMillis();
				which_vr_img = vr_ps;
				break;
			}
			case R.id.tv_layout_menu_look_vr_bg_image: {
				if (TextUtils.isEmpty(JM_VR_BG_IMG_PATH)) {
					ToastUtils.show(mActivity, "请拍VR背景图");
					return;
				}
				AndroidBasicComponentUtils.launchActivity(mActivity, LookVRImageActivity.class, "vr-image-path",
						JM_VR_BG_IMG_PATH);
				break;
			}
			case R.id.tv_layout_menu_look_vr_ps_image: {
				if (TextUtils.isEmpty(JM_VR_PS_IMG_PATH)) {
					ToastUtils.show(mActivity, "请拍VR前景图");
					return;
				}
				AndroidBasicComponentUtils.launchActivity(mActivity, LookVRImageActivity.class, "vr-image-path",
						JM_VR_PS_IMG_PATH);
				break;
			}
			case R.id.tv_layout_menu_jm_history_image: {
				List<JMEntity> jmEntities = new ArrayList<>();
				try {
					String sql = "SELECT * FROM ec_channel_section ORDER BY GXSJ DESC LIMIT 9";
					List<EcChannelSectionEntity> list = dbUtil.excuteQuery(EcChannelSectionEntity.class, sql);
					for (EcChannelSectionEntity ecChannelSectionEntity : list) {
						JMEntity jmEntity = GsonUtils.build().fromJson(ecChannelSectionEntity.getSectionDesc(),
								JMEntity.class);
						// 查找相片
						OfflineAttach queryAttach = new OfflineAttach();
						queryAttach.setWorkNo(ecChannelSectionEntity.getEcChannelSectionId());
						List<OfflineAttach> attachList = dbUtil.excuteQuery(OfflineAttach.class, queryAttach);
						if (null != attachList && !attachList.isEmpty()) {
							for (OfflineAttach offlineAttach : attachList) {
								if (offlineAttach.getFileName().contains("_JMBG")) {
									// 截面背景图
									jmEntity.setBackgroundImage(offlineAttach.getFilePath());
								}
							}
						}
						jmEntities.add(jmEntity);
					}
					if (jmEntities == null || jmEntities.isEmpty()) {
						ToastUtil.showShort(mActivity, "没有历史截面信息！");
						return;
					}
				} catch (Exception e) {
					if (jmEntities.size() < 1) {
						ToastUtil.showShort(mActivity, "历史数据异常,无法显示");
					}
				} finally {
					if (jmEntities.size() < 1) {
						return;
					}
					JMsWindowDisplayUtil.getInstance().showJMsWindow(mActivity, jmEntities);
				}
				view.setVisibility(View.GONE);
				break;
			}
			default:
				break;
			}
			// dismissMenu();
		}
	};

	private long openRicohTime = -1;
	private int which_vr_img = -1;
	final static int vr_bg = 0;
	final static int vr_ps = 1;

	public String JM_BG_IMG_PATH;// 截面背景
	public String JM_VR_BG_IMG_PATH;// 截面VR背景图
	public String JM_VR_PS_IMG_PATH;// 截面VR全景图
	boolean isCheck = false;

	/**
	 * 增加支架
	 */
	private void addBracket() {
		final DialogUtil dialogUtil = new DialogUtil(mActivity);
		View layout = View.inflate(mActivity, R.layout.dialog_choose_bracket, null);
		ListView listView = (ListView) layout.findViewById(R.id.lv_dialog_choose_bracket);
		final CheckBox checkBox = (CheckBox) layout.findViewById(R.id.cb_dialog_choose_bracket);
		ArrayList<String> list = new ArrayList<String>();
		list.add("一层");
		list.add("二层");
		list.add("三层");
		list.add("四层");
		list.add("五层");
		list.add("六层");
		BracketAdapter adapter = new BracketAdapter(mActivity, list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				addZJ(position + 1, position + 1);
				if (isCheck) {
					gjDrawView.addZjPoints(position + 1, position + 1);
					isCheck = false;
				}
				dialogUtil.dismissDialog();
			}
		});
		layout.findViewById(R.id.iv_dialog_choose_bracket_close).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogUtil.dismissDialog();
				dialogUtil.destroy();
			}
		});
		checkBox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isCheck = isChecked;
			}
		});
		dialogUtil.showAlertDialog(layout);
	}

	// 当前选中的
	private int selectGJ = -1;

	private void addZJ(int left, int right) {
		int index = -1000;
		if (selectGJ > 0) {
			// 说明是选中状态
			index = selectGJ;
		} else {
			// 说明是新增加状态
			if (gjDrawView.getLinesLocation() != null && !gjDrawView.getLinesLocation().isEmpty()) {
				// 说明已经增加了一次
				index = GJZJList.size() - 1;
			}
		}
		gjDrawView.setLinesLocation(left, right);
		ChannelBracketEntity zj = new ChannelBracketEntity();
		pos = new ArrayList<Point>();
		ListUtil.copyList(pos, gjDrawView.getLinesLocation());
		zj.setPos(pos);
		if (index >= 0) {
			GJZJList.set(index, zj);
		} else {
			GJZJList.add(zj);
		}
	}

	/**
	 * 辅助网格对话框
	 */
	@SuppressLint("InflateParams")
	private void showWGDialog() {
		if (null == dialog) {
			wgViewHolder.getInconX().setOnClickListener(ocl);
			wgViewHolder.getInconY().setOnClickListener(ocl);
			dialog = builder.create();
		}
		dialog.show();
		dialog.setView(view, 0, 0, 0, 0);
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.width = 800;
		params.height = 800;
		dialog.getWindow().setAttributes(params);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 9999) {
				// 截面背景图
				@SuppressWarnings("deprecation")
				BitmapDrawable drawable = new BitmapDrawable(
						ImageHelper.decodeSampledBitmapFromImagePath(JM_BG_IMG_PATH, 540, 960));
				this.gjDrawView.setBackground(drawable);
				this.gjDrawView.setTag(JM_BG_IMG_PATH);
			}
		}
	}

	public void onResume() {
		if (openRicohTime > 0) {
			if (which_vr_img == vr_bg) {
				// VR背景图
				try {
					RicohUtil.copyPicture(JM_VR_BG_IMG_PATH, openRicohTime);
					if (!FileUtil.checkFilePathExists(JM_VR_BG_IMG_PATH)) {
						JM_VR_BG_IMG_PATH = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
					JM_VR_BG_IMG_PATH = null;
				}
			}
			if (which_vr_img == vr_ps) {
				// VR前景图
				try {
					RicohUtil.copyPicture(JM_VR_PS_IMG_PATH, openRicohTime);
					if (!FileUtil.checkFilePathExists(JM_VR_PS_IMG_PATH)) {
						JM_VR_PS_IMG_PATH = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
					JM_VR_PS_IMG_PATH = null;
				}
			}
		}
		openRicohTime = -1;
		which_vr_img = -1;
	}
}
