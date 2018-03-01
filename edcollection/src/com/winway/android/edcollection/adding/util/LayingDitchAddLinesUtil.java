package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.text.InputType;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.LayingDitchAddLinesAdapter;
import com.winway.android.edcollection.adding.adapter.LayingDitchAddLinesAdapter.ViewHolder;
import com.winway.android.edcollection.adding.bll.DataCacheBll;
import com.winway.android.edcollection.adding.entity.DitchCable;
import com.winway.android.edcollection.adding.entity.LineNodeSort;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.util.DeviceUtils;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

/**
 * 添加多条同沟线路工具
 * 
 * @author xs
 *
 */
public class LayingDitchAddLinesUtil {
	/**
	 * 单例
	 */
	private static LayingDitchAddLinesUtil instance;

	private LayingDitchAddLinesUtil() {

	}

	public static LayingDitchAddLinesUtil getInstance() {
		synchronized (LayingDitchAddLinesUtil.class) {
			if (instance == null) {
				instance = new LayingDitchAddLinesUtil();
			}
		}
		return instance;
	}

	private LayingDitchAddLinesListener ditchAddLinesListener;
	private ArrayList<DitchCable> ditchCables = null; // 同沟线路集合
	private List<String> selectedLinesNodeSort = null; // 用于记录选中的线路 -1表示未选中
	private List<String> selectedLineNodeOrders = null; // 选中的线路序号
	private List<LineNodeSort> lineNodeSort;
	private List<String> linesNodeSort; // 线路节点序号集合
	private DataCacheBll dataCacheBll;

	public void showAddLinesDialog(final Activity mActivity, final List<EcLineEntity> lineEntities, String cacheDbUrl,
			final String layType, final ArrayList<DitchCable> ditchCableList, final EcNodeEntity editNodeEntity) {
		dataCacheBll = new DataCacheBll(mActivity, cacheDbUrl);

		// 生成线路节点序号集合
		obtainLineNodeOrderList(lineEntities, mActivity);
		// 初始化线路集合
		selectedLinesNodeSort = new ArrayList<>();
		for (int i = 0; i < lineEntities.size(); i++) {
			selectedLinesNodeSort.add("-1");
		}
		// 初始化线路序号集合
		selectedLineNodeOrders = new ArrayList<>();
		// 初始化同沟线路集合
		ditchCables = new ArrayList<>();

		final DialogUtil dialog = new DialogUtil(mActivity);
		View addLinesView = View.inflate(mActivity, R.layout.laying_ditch_add_lines, null);
		ImageView ivAddLinesClose = (ImageView) addLinesView.findViewById(R.id.iv_laying_ditch_add_lines_close);
		TextView tvAddLinesAll = (TextView) addLinesView.findViewById(R.id.tv_laying_ditch_add_lines_all);
		TextView tvAddLinesInverse = (TextView) addLinesView.findViewById(R.id.tv_laying_ditch_add_lines_inverse);
		final ListView lvAddLines = (ListView) addLinesView.findViewById(R.id.lv_laying_ditch_add_lines);
		TextView tvAddLinesOK = (TextView) addLinesView.findViewById(R.id.tv_laying_ditch_add_lines_ok);
		TextView tvAddLineCancel = (TextView) addLinesView.findViewById(R.id.tv_laying_ditch_add_lines_cancel);
		final InputComponent inComAddLinesDistance = (InputComponent) addLinesView
				.findViewById(R.id.inCom_laying_ditch_add_lines_distance);
		inComAddLinesDistance.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		// 点击关闭按钮
		ivAddLinesClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismissDialog();
			}
		});
		// 点击取消按钮
		tvAddLineCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismissDialog();
			}
		});
		final LayingDitchAddLinesAdapter adapter = new LayingDitchAddLinesAdapter(mActivity,
				(ArrayList<EcLineEntity>) lineEntities);
		lvAddLines.setAdapter(adapter);
		// 点击线路列表项
		lvAddLines.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ViewHolder holder = (ViewHolder) view.getTag();
				holder.cbIsCheck.toggle();
				adapter.getSelectedPosition().put(position, holder.cbIsCheck.isChecked());
				if (holder.cbIsCheck.isChecked()) {
					selectedLinesNodeSort.set(position, linesNodeSort.get(position));
				} else {
					selectedLinesNodeSort.set(position, "-1");
				}
				adapter.notifyDataSetInvalidated();
			}
		});
		// 全选
		tvAddLinesAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < lvAddLines.getCount(); i++) {
					adapter.getSelectedPosition().put(i, true);
					selectedLinesNodeSort.set(i, linesNodeSort.get(i));
				}
				adapter.notifyDataSetInvalidated();
			}
		});
		// 反选
		tvAddLinesInverse.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < lvAddLines.getCount(); i++) {
					if (adapter.getSelectedMapItem(i) == true) {
						adapter.getSelectedPosition().put(i, false);
						selectedLinesNodeSort.set(i, "-1");
					} else {
						adapter.getSelectedPosition().put(i, true);
						selectedLinesNodeSort.set(i, linesNodeSort.get(i));
					}
				}
				adapter.notifyDataSetInvalidated();
			}
		});
		// 添加
		tvAddLinesOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				obtainSelectedLineNodeOrderList();

				List<EcLineEntity> lines = adapter.getSelectdValue();
				if (lines == null || lines.isEmpty() || lines.size() == 0) {
					ToastUtil.show(mActivity, "请选择线路", 200);
					return;
				}
				for (int i = 0; i < lines.size(); i++) {
					EcLineEntity line = lines.get(i);
					String OrderNo = selectedLineNodeOrders.get(i);
					String previousNodeDistance = inComAddLinesDistance.getEdtTextValue();
					if (ditchCableList != null && ditchCableList.size() > 0) {
						for (int j = 0; j < ditchCableList.size(); j++) {
							DitchCable ditchCable = ditchCableList.get(j);
							if (ditchCable.getLineName().equals(line.getName())) {
								ToastUtil.show(mActivity, line.getName() + "在同沟电缆列表中已经存在", 200);
								ditchCables.clear();
								return;
							}
						}
					}
					DitchCable ditchCable = new DitchCable();
					ditchCable.setLayType(layType);
					ditchCable.setId(UUIDGen.randomUUID());
					ditchCable.setLineId(line.getEcLineId());
					ditchCable.setLineNo(line.getLineNo());
					ditchCable.setLineName(line.getName());
					ditchCable.setLineNodeSort(Integer.valueOf(OrderNo));
					ditchCable.setPreviousNodeDistance(previousNodeDistance != null && !"".equals(previousNodeDistance)
							? Float.valueOf(previousNodeDistance)
							: null);
					ditchCable.setOid(editNodeEntity != null ? editNodeEntity.getOid() : null);
					// 如果是编辑状态下的添加
					if (GlobalEntry.editNode) {
						ditchCable.setEditAdd(true);
					}
					ditchCables.add(ditchCable);
				}
				ditchAddLinesListener.getDitchCables(ditchCables);
				ditchCables.clear();
				dialog.dismissDialog();
			}
		});
		int width = DeviceUtils.getScreenWidth(mActivity) * 4 / 5;
		dialog.showAlertDialog(addLinesView, width);
		dialog.dialogOutsideTouchCancel(false);
	}

	/**
	 * 生成选中的线路节点序号集合
	 */
	private void obtainSelectedLineNodeOrderList() {
		selectedLineNodeOrders.clear();
		for (String string : selectedLinesNodeSort) {
			if (!"-1".equals(string)) {
				selectedLineNodeOrders.add(string);
			}
		}
	}

	/**
	 * 生成线路节点序号
	 * 
	 * @param lineEntities
	 * @param mActivity
	 */
	private void obtainLineNodeOrderList(List<EcLineEntity> lineEntities, Activity mActivity) {
		BaseDBUtil dbUtil = new BaseDBUtil(mActivity, GlobalEntry.prjDbUrl);
		linesNodeSort = new ArrayList<>();
		if (lineEntities != null && lineEntities.size() > 0) {
			for (int i = 0; i < lineEntities.size(); i++) {
				lineNodeSort = dataCacheBll.findLineNodeSortById(lineEntities.get(i).getEcLineId());
				if (lineNodeSort != null && lineNodeSort.size() > 0) {
					if (lineNodeSort.size() == 1) {
						linesNodeSort.add((lineNodeSort.get(0).getSort() + GlobalEntry.node_variance) + "");
					} else {
						// 计算出前两个的差
						int variance = lineNodeSort.get(0).getSort() - lineNodeSort.get(1).getSort();
						linesNodeSort.add((lineNodeSort.get(0).getSort() + variance) + "");
					}
				} else {
					String sql = "SELECT * FROM EC_LINE_NODES WHERE EC_LINE_ID= '" + lineEntities.get(i).getEcLineId()
							+ "'ORDER BY N_INDEX DESC LIMIT 2";
					try {
						List<EcLineNodesEntity> list = dbUtil.excuteQuery(EcLineNodesEntity.class, sql);
						if (list == null || list.isEmpty() || list.size() < 1) {
							linesNodeSort.add("100");
							continue;
						} else if (list.size() == 1) {
							linesNodeSort.add((list.get(0).getNIndex() + GlobalEntry.node_variance) + "");
						} else {
							int previous = 0, next = 0;
							for (int j = 0; j < list.size(); j++) {
								EcLineNodesEntity ecLineNode = list.get(j);
								if (j == 0) {
									next = ecLineNode.getNIndex();
								} else {
									previous = ecLineNode.getNIndex();
								}
							}
							int cha = next - previous;
							if (cha < 100 && cha > 0) {
								cha = 100;
							}
							if (cha < -100) {
								cha = -100;
							}
							linesNodeSort.add((next + cha) + "");
						}
					} catch (DbException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void setDitchAddLinesListener(LayingDitchAddLinesListener ditchAddLinesListener) {
		this.ditchAddLinesListener = ditchAddLinesListener;
	}

	public interface LayingDitchAddLinesListener {

		void getDitchCables(ArrayList<DitchCable> ditchCables);

	}
}
