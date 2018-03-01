package com.winway.android.edcollection.adding.controll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ocn.himap.datamanager.HiElement;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.DitchCableAdapter;
import com.winway.android.edcollection.adding.adapter.LineInputSelectAdapter;
import com.winway.android.edcollection.adding.bll.CollectCommonBll;
import com.winway.android.edcollection.adding.bll.DataCacheBll;
import com.winway.android.edcollection.adding.bll.SameGrooveInformationBll;
import com.winway.android.edcollection.adding.entity.DitchCable;
import com.winway.android.edcollection.adding.fragment.BasicInfoFragment;
import com.winway.android.edcollection.adding.util.FragmentEntry;
import com.winway.android.edcollection.adding.util.LayingDitchAddLinesUtil;
import com.winway.android.edcollection.adding.util.LayingDitchAddLinesUtil.LayingDitchAddLinesListener;
import com.winway.android.edcollection.adding.util.LengthTextWatcher;
import com.winway.android.edcollection.adding.util.SelectCollectObjPopWindow;
import com.winway.android.edcollection.adding.viewholder.LayingDitchViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.util.CoordsUtils;
import com.winway.android.edcollection.base.view.BaseFragmentControll;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.api.DataCenter.CallBack;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.entity.LineDevicesEntity;
import com.winway.android.edcollection.map.SpatialAnalysis;
import com.winway.android.edcollection.map.entity.SpatialAnalysisDataType;
import com.winway.android.edcollection.map.util.CombineSpatialDatasUtil;
import com.winway.android.edcollection.project.entity.EcLineDeviceEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.ewidgets.input.InputSelectComponent.DropDownSelectListener;
import com.winway.android.ewidgets.input.InputSelectComponent.SearchResultListener;
import com.winway.android.ewidgets.input.entity.DataItem;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

/**
 * 同沟信息
 * 
 * @author lyh
 * @version 创建时间：2016年12月21日 上午10:30:46
 * 
 */
public class LayingDitchControll extends BaseFragmentControll<LayingDitchViewHolder>
		implements LayingDitchAddLinesListener {

	private SameGrooveInformationBll sameGrooveInformationBll;
	private CollectCommonBll collectCommonBll;
	private DataCacheBll dataCacheBll;
	private CommonBll commonBll;

	private DitchCableAdapter ditchCableAdapter;
	public static ArrayList<DitchCable> ditchCableList = new ArrayList<DitchCable>();
	private List<EcLineEntity> lineEntities = null;// 线路列表
	private LineInputSelectAdapter lineInputSelectAdapter = null;// 线路列表适配器
	public static final int requestCode_layingditch = 2;
	private DitchCable ditchCable;
	private int lineInputSelectPositin = 0;// 记录选择线路的position
	private DataCenter dataCenter = null;
	private List<OfflineAttach> comUploadlist;
	private EcNodeEntity editNodeEntity;// 编辑
	private MarkerCollectControll markerCollectControll;

	@Override
	public void init() {
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		sameGrooveInformationBll = new SameGrooveInformationBll(mActivity, projectBDUrl);
		dataCacheBll = new DataCacheBll(mActivity, FileUtil.AppRootPath + "/db/project/data_cache.db");
		commonBll = new CommonBll(mActivity, projectBDUrl);
		dataCenter = new DataCenterImpl(mActivity, projectBDUrl);
		collectCommonBll = new CollectCommonBll(mActivity, projectBDUrl);
		markerCollectControll =MarkerCollectControll.getInstance();
		initCommonDatas();
		if (GlobalEntry.editNode == true) {
			initEditDatas();
		} else {
			initDatas();
		}
		initEvent();
	}
	
	/**
	 * 获取选择同沟电缆列表
	 * 
	 * @return
	 */
	public List<DitchCable> getDitchCableList() {
		return ditchCableList;
	}

	// 编辑中使用到的变量
	public List<EcLineEntity> edtLineList = null;

	/**
	 * 初始化编辑传递过来的数据
	 */
	private void initEditDatas() {
		Intent intent = getIntent();
		final EcNodeEntity ecNodeEntity = (EcNodeEntity) intent.getSerializableExtra("EcNodeEntity");
		editNodeEntity = ecNodeEntity;
		// 与上一路径点距离
		String oid = editNodeEntity.getOid();
		String expr = "OID ='" + oid + "'";
		List<EcLineNodesEntity> lineNodes2 = collectCommonBll.queryByExpr2(EcLineNodesEntity.class, expr);
		EcLineNodesEntity ecLineNodesEntity1 = lineNodes2.get(0);
		Float upSpace = ecLineNodesEntity1.getUpSpace();
		String deviceDesc = ecLineNodesEntity1.getDeviceDesc();
		if(deviceDesc!=null){
			viewHolder.getIcDeviceDescription().setEdtText(deviceDesc);
		}
		if (upSpace != null) {
			viewHolder.getIcDistance().setEdtText(upSpace + "");
		}
		// 同沟列表
		// 1、根据oid获取线路列表
		dataCenter.getAcrossLineInNode(ecNodeEntity.getOid(), null, new CallBack<List<EcLineEntity>>() {

			@Override
			public void call(List<EcLineEntity> data) {
				edtLineList = data;
			}
		});
		// 2、根据oid获取线路节点
		final Map<String, EcLineNodesEntity> mapLineId2LineNode = new HashMap<String, EcLineNodesEntity>();
		dataCenter.getLineNodeListByOid(ecNodeEntity.getOid(), null, new CallBack<List<EcLineNodesEntity>>() {

			@Override
			public void call(List<EcLineNodesEntity> data) {
				for (int i = 0; i < data.size(); i++) {
					EcLineNodesEntity ecLineNodesEntity = data.get(i);
					mapLineId2LineNode.put(ecLineNodesEntity.getEcLineId(), ecLineNodesEntity);
				}
			}
		});

		if (edtLineList != null && edtLineList.size() > 0) {
			for (int i = 0; i < edtLineList.size(); i++) {
				EcLineEntity ecLineEntity = edtLineList.get(i);
				final DitchCable ditchCable = new DitchCable();
				// 保存oid编辑线路附属设备中使用
				ditchCable.setOid(editNodeEntity.getOid());
				EcLineNodesEntity lineNodes = mapLineId2LineNode.get(ecLineEntity.getEcLineId());
				if (lineNodes != null) {
					ditchCable.setLineNodesId(lineNodes.getEcLineNodesId());
				}
				ditchCable.setId(UUIDGen.randomUUID());
				ditchCable.setLineId(ecLineEntity.getEcLineId());
				ditchCable.setLineName(ecLineEntity.getName());
				ditchCable.setLineNo(ecLineEntity.getLineNo());
				EcLineNodesEntity ecLineNodesEntity = mapLineId2LineNode.get(ecLineEntity.getEcLineId());
				ditchCable.setLineNodeSort(ecLineNodesEntity.getNIndex());
				ditchCable.setLayType(ecLineNodesEntity.getUpLay());
				ditchCable.setPreviousNodeDistance(ecLineNodesEntity.getUpSpace());
				ditchCable.setDeviceDescription(ecLineNodesEntity.getDeviceDesc());
				// 线路附属设备
				dataCenter.getLineDevicesList(ecLineEntity.getEcLineId(), null, new CallBack<LineDevicesEntity>() {

					@Override
					public void call(LineDevicesEntity data) {
						if (data != null) {
							// 环网柜
							List<DeviceEntity<EcHwgEntityVo>> hwgList = data.getHwgEntityVoList();
							if (hwgList != null && hwgList.size() > 0) {
								for (int i = 0; i < hwgList.size(); i++) {
									if (hwgList.get(i).getNode() != null) {
										if (hwgList.get(i).getNode().getOid().equals(ecNodeEntity.getOid())) {
											// 查找图片附件
											comUploadlist = dataCenter.getOfflineAttachListByWorkNo(
													hwgList.get(i).getData().getObjId(), null, null);
											hwgList.get(i).getData().setAttr(comUploadlist);
											ditchCable.setHwgEntityVo(hwgList.get(i).getData());
										}
									}
								}
							}
							// 电子标签
							List<DeviceEntity<EcLineLabelEntityVo>> lableList = data.getLineLabelEntityVoList();
							if (lableList != null && lableList.size() > 0) {
								for (int i = 0; i < lableList.size(); i++) {
									if (lableList.get(i).getNode() != null) {
										if (lableList.get(i).getNode().getOid().equals(ecNodeEntity.getOid())) {
											// 查找图片附件
											comUploadlist = dataCenter.getOfflineAttachListByWorkNo(
													lableList.get(i).getData().getObjId(), null, null);
											lableList.get(i).getData().setAttr(comUploadlist);
											ditchCable.setLineLabelEntityVo(lableList.get(i).getData());
										}
									}
								}
							}
							// 电缆分支箱
							List<DeviceEntity<EcDlfzxEntityVo>> dlfzxList = data.getDlfzxEntityVoList();
							if (dlfzxList != null && dlfzxList.size() > 0) {
								for (int i = 0; i < dlfzxList.size(); i++) {
									if (dlfzxList.get(i).getNode() != null) {
										if (dlfzxList.get(i).getNode().getOid().equals(ecNodeEntity.getOid())) {
											// 查找图片附件
											comUploadlist = dataCenter.getOfflineAttachListByWorkNo(
													dlfzxList.get(i).getData().getObjId(), null, null);
											dlfzxList.get(i).getData().setAttr(comUploadlist);
											ditchCable.setDlfzxEntityVo(dlfzxList.get(i).getData());
										}
									}
								}
							}
							// 低压电缆分支箱
							List<DeviceEntity<EcDydlfzxEntityVo>> dydlfzxList = data.getDydlfzxEntityVoList();
							if (dydlfzxList != null && dydlfzxList.size() > 0) {
								for (int i = 0; i < dydlfzxList.size(); i++) {
									if (dydlfzxList.get(i).getNode() != null) {
										if (dydlfzxList.get(i).getNode().getOid().equals(ecNodeEntity.getOid())) {
											// 查找图片附件
											comUploadlist = dataCenter.getOfflineAttachListByWorkNo(
													dydlfzxList.get(i).getData().getObjId(), null, null);
											dydlfzxList.get(i).getData().setAttr(comUploadlist);
											ditchCable.setDydlfzxEntityVo(dydlfzxList.get(i).getData());
										}
									}
								}
							}
							// 电缆中间接头
							List<DeviceEntity<EcMiddleJointEntityVo>> middleJointList = data
									.getMiddleJointEntityVoList();
							if (middleJointList != null && middleJointList.size() > 0) {
								for (int i = 0; i < middleJointList.size(); i++) {
									if (middleJointList.get(i).getData() != null) {
										// 根据中间接头id查找标签
										String sql = "DEV_OBJ_ID = '" + middleJointList.get(i).getData().getObjId()
												+ "'";
										ArrayList<EcLineLabelEntityVo> ecLineLabelEntityVos = (ArrayList<EcLineLabelEntityVo>) commonBll
												.queryByExpr2(EcLineLabelEntityVo.class, sql);
										if (ecLineLabelEntityVos != null && ecLineLabelEntityVos.size() > 0) {
											for (EcLineLabelEntityVo ecLineLabelEntityVo : ecLineLabelEntityVos) {
												// 获取所属工井标签的附件
												List<OfflineAttach> attachs = dataCenter.getOfflineAttachListByWorkNo(
														ecLineLabelEntityVo.getObjId(), null, null);
												ecLineLabelEntityVo.setAttr(attachs);
												middleJointList.get(i).getData()
														.setEcLineLabelEntityVo(ecLineLabelEntityVo);
											}
										}
									}
									if (middleJointList.get(i).getNode() != null) {
										if (middleJointList.get(i).getNode().getOid().equals(ecNodeEntity.getOid())) {
											// 查找图片附件
											comUploadlist = dataCenter.getOfflineAttachListByWorkNo(
													middleJointList.get(i).getData().getObjId(), null, null);
											middleJointList.get(i).getData().setAttr(comUploadlist);
											ditchCable.setMiddleJointEntityVo(middleJointList.get(i).getData());
										}
									}
								}
							}
						}
					}

				});
				ditchCableList.add(ditchCable);
			}
		}
//		recoverLineDeviceCheck();
		// 初始化同沟电缆列表
		ditchCableAdapter = new DitchCableAdapter(mActivity, ditchCableList, viewHolder.getIcLoopNum());
		viewHolder.getLvSwipeListView().setAdapter(ditchCableAdapter);
		viewHolder.getIcLoopNum().setEdtText(ecNodeEntity.getCableLoop() + "");
		viewHolder.getIcUnknownLoopNum().setEdtText(ecNodeEntity.getCableLoop() - ditchCableList.size() + "");
	}

	private void initCommonDatas() {
		// 初始化敷设方式下拉列表
		// final InputSelectAdapter layTypeAdapter = new InputSelectAdapter(mActivity,
		// collectCommonBll.getChannelTypeList());
		String[] stringArray = mActivity.getResources().getStringArray(R.array.stateGridType);
		List<String> layTypes = new ArrayList<String>(Arrays.asList(stringArray));
		final InputSelectAdapter layTypeAdapter = new InputSelectAdapter(mActivity, layTypes);
		viewHolder.getInsclayType().setAdapter(layTypeAdapter);
		// 初始化待采集下拉线路列表
		lineEntities = SelectCollectObjPopWindow.getIntance(mActivity).getWaitingLines();
		if (lineEntities != null && lineEntities.size() > 0) {
			lineInputSelectAdapter = new LineInputSelectAdapter(mActivity, lineEntities);
			viewHolder.getInscLineName().setAdapter(lineInputSelectAdapter);
		}
		viewHolder.getInscLineName().setDropDownSelectListener(new LineDropDownSelect());
		viewHolder.getInscLineName().setDataItemList(sameGrooveInformationBll.searchAllLines(mActivity));
		viewHolder.getInscLineName().setSearchResultListener(new SearchResultListener() {
			@Override
			public void searchResult(DataItem dataItem) {
				EcLineEntity lineEntity = (EcLineEntity) dataItem.getBusiObj();
				sameGrooveInformationBll.setLineNodeSortEdt(dataCacheBll, lineEntity.getEcLineId(),
						viewHolder.getIcLineNodeSort());
				
				boolean hasRepeat = false;
				if (null != lineEntities && !lineEntities.isEmpty()) {
					for (int i = 0; i < lineEntities.size(); i++) {
						EcLineEntity ecLineEntity = lineEntities.get(i);
						if (ecLineEntity.getName().equals(lineEntity.getName())) {
							hasRepeat = true;
						}
					}
				}
				if (!hasRepeat) {
					if (null == lineEntities || lineEntities.isEmpty()) {
						lineEntities = new ArrayList<EcLineEntity>();
					}
					lineEntities.add(lineEntity);
				}
			}
		});
		// 设置输入框的输入
		viewHolder.getIcLoopNum().setEditTextInputType(InputType.TYPE_CLASS_NUMBER);
		viewHolder.getIcLineNodeSort().setEditTextInputType(InputType.TYPE_CLASS_NUMBER);
		// 设置只读
		// viewHolder.getInscLineName().getEditTextView().setEnabled(false);
		// 设置与上一节点距离输入框输入类型
		viewHolder.getIcDistance()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

		// 禁止同沟回路数输入
		viewHolder.getIcLoopNum().getEditTextView().setEnabled(false);
		// 设置未知回路数的输入类型
		viewHolder.getIcUnknownLoopNum()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		// 禁止敷设方式人工输入
		viewHolder.getInsclayType().getEditTextView().setEnabled(false);
	}

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		// 初始化同沟电缆列表
		ditchCableAdapter = new DitchCableAdapter(mActivity, ditchCableList, viewHolder.getIcLoopNum());
		viewHolder.getLvSwipeListView().setAdapter(ditchCableAdapter);
		if (lineEntities != null && lineEntities.size() > 0) {
			String lineId = lineEntities.get(lineInputSelectPositin).getEcLineId();
			// 设置线路节点序号
			sameGrooveInformationBll.setLineNodeSortEdt(dataCacheBll, lineId, viewHolder.getIcLineNodeSort());
		}
		viewHolder.getIcLoopNum().setEdtText(String.valueOf(ditchCableList.size()));
	}

	// 线路下拉选择回调
	class LineDropDownSelect implements DropDownSelectListener {

		@Override
		public void handle(int index) {
			// 判断是否存在缓存的线路节点数据序号
			lineInputSelectPositin = index;
			EcLineEntity ecLineEntity = lineEntities.get(index);
			// 设置线路节点序号
			sameGrooveInformationBll.setLineNodeSortEdt(dataCacheBll, ecLineEntity.getEcLineId(),
					viewHolder.getIcLineNodeSort());
		}
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		viewHolder.getBtnAdd().setOnClickListener(ocls);
		viewHolder.getIcLoopNum().getEditTextView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

			}
		});
		// 监听EditText的输入状态
		viewHolder.getIcLoopNum().getEditTextView().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String loopNumStr = viewHolder.getIcLoopNum().getEdtTextValue().trim();
				if (loopNumStr.isEmpty()) {
					return;
				}
				if (Integer.valueOf(loopNumStr) < ditchCableList.size()) {
					ToastUtil.show(mActivity, "输入的值不能小于同沟电缆列表数", 100);
					viewHolder.getIcLoopNum().setEdtText(ditchCableList.size() + "");
					return;
				}
			}
		});
		viewHolder.getIcLineNodeSort().getEditTextView()
				.addTextChangedListener(new LengthTextWatcher(9, viewHolder.getIcLineNodeSort().getEditTextView()));
		viewHolder.getBtnLineNameAdds().setOnClickListener(ocls);
		LayingDitchAddLinesUtil.getInstance().setDitchAddLinesListener(LayingDitchControll.this);

		// 监听未知回路数的输入状态
		viewHolder.getIcUnknownLoopNum().getEditTextView().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String unknownLoopNumStr = s.toString();
				if (!"".equals(unknownLoopNumStr)) {
					int unknownLoopNum = Integer.parseInt(unknownLoopNumStr);
					viewHolder.getIcLoopNum().setEdtText(unknownLoopNum + ditchCableList.size() + "");
				} else {
					viewHolder.getIcLoopNum().setEdtText(ditchCableList.size() + "");
				}
			}
		});
	}

	private OnClickListener ocls = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_laying_ditch_add: // 添加
				addLine();
				break;
			case R.id.btn_laying_ditch_lineName_adds:// 添加多条线路
				if(lineEntities != null && !lineEntities.isEmpty()) {
					LayingDitchAddLinesUtil.getInstance().showAddLinesDialog(mActivity, lineEntities,
							FileUtil.AppRootPath + "/db/project/data_cache.db",
							viewHolder.getInsclayType().getEdtTextValue(), ditchCableList, editNodeEntity);
				}else {
					ToastUtil.showShort(mActivity, "请在主页面选择线路");
				}
				break;
			default:
				break;
			}
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == requestCode_layingditch) {
				ditchCable = (DitchCable) data.getSerializableExtra("ditchCable");
				FragmentEntry.getInstance().layingDitchVo.setLineNodeSort(ditchCable.getLineNodeSort());
				FragmentEntry.getInstance().layingDitchVo.setLayType(ditchCable.getLayType());
				FragmentEntry.getInstance().layingDitchVo.setPreviousNodeDistance(ditchCable.getPreviousNodeDistance());
				FragmentEntry.getInstance().layingDitchVo.setDeviceDescription(ditchCable.getDeviceDescription());
				// 修改同沟列表信息
				for (int i = 0; i < ditchCableList.size(); i++) {
					if (ditchCableList.get(i).getId().equals(ditchCable.getId())) {
						ditchCableList.remove(i);
						ditchCableList.add(i, ditchCable);
					}
				}
				ditchCableAdapter.notifyDataSetChanged();
			}
		}
	};

	/**
	 * 添加同构信息的线路缓存
	 */
	protected void addLineNodeSort() {
		// 在非编辑下才保存
		if (!GlobalEntry.editNode) {
			if (ditchCableList != null && ditchCableList.size() > 0) {
				for (int i = 0; i < ditchCableList.size(); i++) {
					int sort = ditchCableList.get(i).getLineNodeSort();
					dataCacheBll.saveLineNodeSort(ditchCableList.get(i).getLineId(), sort);
				}
			}
		}
	}

	/**
	 * 添加线路到同沟电缆列表
	 */
	protected void addLine() {
		String lineName = viewHolder.getInscLineName().getEdtTextValue();
		if (lineName.isEmpty()) {
			ToastUtil.show(mActivity, "线路名称不能为空", 200);
			return;
		}
		String sort = viewHolder.getIcLineNodeSort().getEdtTextValue();
		String regex = "\\d{1,9}|1\\d{9}|20\\d{8}|21[0-3]\\d{7}|214[0-6]\\d{6}|2147[0-3]\\d{5}|21474[0-7]\\d{4}|214748[0-2]\\d{3}|2147483[0-5]\\d{2}|21474836[0-3]\\d{1}|214748364[0-7]";
		if (TextUtils.isEmpty(sort)) {
			ToastUtil.show(mActivity, "序号不能为空", 200);
			return;
		}
		if (!sort.matches(regex)) {
			ToastUtil.show(mActivity, "序号已超过最大可接收范围", 200);
			return;
		}
		if (sort.isEmpty()) {
			ToastUtil.show(mActivity, "序号不能为空", 200);
			return;
		}
		EcLineEntity lineEntity = getLineEntity(lineName);
		if (lineEntity == null) {
			ToastUtil.show(mActivity, "没有此线路，请重新选择或输入", 200);
			return;
		}
		// 检查序号的合法性
		if (sameGrooveInformationBll.checkLineNodeSort(sort, lineEntity.getEcLineId())) {
			ToastUtil.show(mActivity, "线路节点序号不合法", 200);
			return;
		}
		String layType = viewHolder.getInsclayType().getEdtTextValue();
		if (ditchCableList != null && ditchCableList.size() > 0) {
			for (int i = 0; i < ditchCableList.size(); i++) {
				DitchCable ditchCable = ditchCableList.get(i);
				if (ditchCable.getLineName().equals(lineName)) {
					ToastUtil.show(mActivity, "同沟电缆列表中已经存在", 200);
					return;
				}
			}
		}
		String distance = viewHolder.getIcDistance().getEdtTextValue();

		DitchCable ditchCable = new DitchCable();
		ditchCable.setLayType(layType);
		if (!TextUtils.isEmpty(distance) /* && distance.matches("^[+-]?\\d*\\.\\d*$") */) {
			float distance2 = Float.parseFloat(distance);
			ditchCable.setPreviousNodeDistance(distance2);
		}
		ditchCable.setId(UUIDGen.randomUUID());
		ditchCable.setLineId(lineEntity.getEcLineId());
		ditchCable.setLineNo(lineEntity.getLineNo());
		ditchCable.setLineName(lineName);
		ditchCable.setLineNodeSort(Integer.valueOf(sort));
		ditchCable.setDeviceDescription(viewHolder.getIcDeviceDescription().getEdtTextValue());
		ditchCable.setOid(editNodeEntity != null ? editNodeEntity.getOid() : null);
		
		if (ditchCableList == null && ditchCableList.size() == 0) {
			ditchCableList = new ArrayList<DitchCable>();
		}
		// 如果是编辑状态下的添加
		if (GlobalEntry.editNode) {
			ditchCable.setEditAdd(true);
		}
		ditchCableList.add(ditchCable);
		viewHolder.getIcLoopNum().setEdtText(String.valueOf(ditchCableList.size()));
		ditchCableAdapter.notifyDataSetChanged();
	}

	// 根据线路名称获取线路对象
	private EcLineEntity getLineEntity(String lineName) {
		if (lineEntities != null) {
			for (int i = 0; i < lineEntities.size(); i++) {
				EcLineEntity ecLineEntity = lineEntities.get(i);
				if (ecLineEntity.getName().equals(lineName)) {
					return ecLineEntity;
				}
			}
		}
		return null;
	}

	/**
	 * 保存同沟信息
	 * 
	 * @param oid
	 */
	public void saveLayingDitchInfo(EcNodeEntity ecNodeEntity) {
		EcLineNodesEntity lineNodes = new EcLineNodesEntity();
		EcLineDeviceEntity lineDevice = null;
		for (DitchCable ditchCable : ditchCableList) {
			if (!GlobalEntry.editNode) {// 新增
				// 1、保存线路节点关联表
				saveLineNodesData(ditchCable, lineNodes, ecNodeEntity);
				// 2、线路附属具体设备和线路设备关联表
				saveLineDevice(ditchCable, lineNodes, lineDevice, ecNodeEntity);
			} else {// 编辑
				// 判断编辑下是否在同沟添加了线路
				if (ditchCable.isEditAdd()) {
					// 1、保存线路节点关联表
					saveLineNodesData(ditchCable, lineNodes, editNodeEntity);
					// 2、线路附属具体设备和线路设备关联表
					saveLineDevice(ditchCable, lineNodes, lineDevice, editNodeEntity);
				} else {
					// 线路节点关联表
					EcLineNodesEntity ecLineNodesEntity = dataCenter.getLineNode(ecNodeEntity.getOid(),
							ditchCable.getLineId(), null, null);
					saveLineNodesData(ditchCable, ecLineNodesEntity, editNodeEntity);
					// 保存电缆电子标签
					EcLineLabelEntityVo lineLabel = ditchCable.getLineLabelEntityVo();
					if (lineLabel != null) {
						// 如果是自动添加的标签，标签名称设为BQ+标识器ID
						if (lineLabel.getGxsj() == null) {
							BasicInfoFragment basicInfoFragment = markerCollectControll.getBasicInfoFragment();
							if (!lineLabel.getDevName()
								.equals("BQ"+basicInfoFragment.getAction().getViewHolder().getIcMarkerId().getEdtTextValue())) {
								lineLabel.setDevName("BQ" + basicInfoFragment.getAction().getViewHolder().getIcMarkerId().getEdtTextValue());
							}
						}			
						if (lineLabel.getOperateMark() == 1 || lineLabel.getOperateMark() == 3) {// 编辑中新增、设备关联
							// 保存线路设备关联表
							saveLineDeviceLink(ditchCable.getOid(), ditchCable, ResourceEnum.DZBQ,
									lineLabel.getObjId());
						}
						// 保存电缆电子标签
						savelineLabelData(ditchCable, lineLabel, ecNodeEntity);
					}
					EcMiddleJointEntityVo middleJoint = ditchCable.getMiddleJointEntityVo();
					if (middleJoint != null) {
						if (middleJoint.getOperateMark() == 1 || middleJoint.getOperateMark() == 3) {// 编辑中新增、设备关联
							// 保存线路设备关联表
							saveLineDeviceLink(ditchCable.getOid(), ditchCable, ResourceEnum.ZJJT,
									middleJoint.getObjId());
						}
						// 保存电缆中间接头
						saveMiddleJointData(ditchCable, middleJoint, ecNodeEntity);
					}

					EcHwgEntityVo hwg = ditchCable.getHwgEntityVo();
					if (hwg != null) {
						if (hwg.getOperateMark() == 1 || hwg.getOperateMark() == 3) {// 编辑中新增、设备关联
							// 保存线路设备关联表
							saveLineDeviceLink(ditchCable.getOid(), ditchCable, ResourceEnum.HWG, hwg.getObjId());
						}
						// 保存环网柜
						saveHwgData(ditchCable, hwg);
					}

					EcDlfzxEntityVo dlfzx = ditchCable.getDlfzxEntityVo();
					if (dlfzx != null) {
						if (dlfzx.getOperateMark() == 1 || dlfzx.getOperateMark() == 3) {// 编辑中新增、设备关联
							// 保存线路设备关联表
							saveLineDeviceLink(ditchCable.getOid(), ditchCable, ResourceEnum.DLFZX, dlfzx.getObjId());
						}
						// 保存电缆分支箱
						saveDlfzxData(ditchCable, dlfzx);
					}

					EcDydlfzxEntityVo dydlfzx = ditchCable.getDydlfzxEntityVo();
					if (dydlfzx != null) {
						if (dydlfzx.getOperateMark() == 1 || dydlfzx.getOperateMark() == 3) {// 编辑中新增、设备关联
							// 保存线路设备关联表
							saveLineDeviceLink(ditchCable.getOid(), ditchCable, ResourceEnum.DYDLFZX,
									dydlfzx.getObjId());
						}
						// 保存低压电缆分支箱
						saveDydlfzxData(ditchCable, dydlfzx);
					}
				}
			}
		}

		addLineNodeSort();
		ditchCableList.clear();

	}

	/**
	 * 保存低压电缆分支箱
	 * 
	 * @param ditchCable
	 * @param dydlfzx
	 */
	private void saveDydlfzxData(DitchCable ditchCable, EcDydlfzxEntityVo dydlfzx) {

		// 1、保存电缆分支箱
		sameGrooveInformationBll.saveOrUpdate(dydlfzx);
		// 2、保存日志
		if (dydlfzx.getOperateMark() == 0 || dydlfzx.getOperateMark() == 1) {// 新增、编辑下新增
			commonBll.handleDataLog(dydlfzx.getObjId(), TableNameEnum.DYDLFZX.getTableName(), DataLogOperatorType.Add,
					WhetherEnum.NO, "新增低压电缆分支箱", true);
		} else if (dydlfzx.getOperateMark() == 2 || dydlfzx.getOperateMark() == 3) {// 编辑下编辑、设备关联
			commonBll.handleDataLog(dydlfzx.getObjId(), TableNameEnum.DYDLFZX.getTableName(),
					DataLogOperatorType.update, WhetherEnum.NO, "编辑低压电缆分支箱", false);
		}
		// 3、附件保存
		List<OfflineAttach> comUploadList = dydlfzx.getAttr();
		saveOfflineAttach(comUploadList);

	}

	/**
	 * 电缆分支箱
	 * 
	 * @param ditchCable
	 * @param dlfzx
	 */
	private void saveDlfzxData(DitchCable ditchCable, EcDlfzxEntityVo dlfzx) {
		// 1、保存电缆分支箱
		sameGrooveInformationBll.saveOrUpdate(dlfzx);
		// 2、保存日志
		if (dlfzx.getOperateMark() == 0 || dlfzx.getOperateMark() == 1) {// 新增、编辑下新增
			commonBll.handleDataLog(dlfzx.getObjId(), TableNameEnum.DLFZX.getTableName(), DataLogOperatorType.Add,
					WhetherEnum.NO, "新增电缆分支箱", true);
		} else if (dlfzx.getOperateMark() == 2 || dlfzx.getOperateMark() == 3) {// 编辑下编辑、设备关联
			commonBll.handleDataLog(dlfzx.getObjId(), TableNameEnum.DLFZX.getTableName(), DataLogOperatorType.update,
					WhetherEnum.NO, "编辑电缆分支箱", false);
		}
		// 3、附件保存
		List<OfflineAttach> comUploadList = dlfzx.getAttr();
		saveOfflineAttach(comUploadList);
	}

	/**
	 * 保存环网柜
	 * 
	 * @param ditchCable
	 * @param hwg
	 */
	private void saveHwgData(DitchCable ditchCable, EcHwgEntityVo hwg) {
		// 1、保存环网柜
		sameGrooveInformationBll.saveOrUpdate(hwg);
		// 2、保存日志
		if (hwg.getOperateMark() == 0 || hwg.getOperateMark() == 1) {// 新增、编辑下新增
			commonBll.handleDataLog(hwg.getObjId(), TableNameEnum.HWG.getTableName(), DataLogOperatorType.Add,
					WhetherEnum.NO, "新增环网柜", true);
		} else if (hwg.getOperateMark() == 2 || hwg.getOperateMark() == 3) {// 编辑下编辑、设备关联
			commonBll.handleDataLog(hwg.getObjId(), TableNameEnum.HWG.getTableName(), DataLogOperatorType.update,
					WhetherEnum.NO, "编辑环网柜", false);
		}
		// 3、附件保存
		List<OfflineAttach> comUploadList = hwg.getAttr();
		saveOfflineAttach(comUploadList);

	}

	/**
	 * 保存线路设备关联表和关联表的操作日志
	 * 
	 * @param objId
	 */
	private void saveLineDeviceLink(String oid, DitchCable ditchCable, ResourceEnum type, String objId) {
		EcLineDeviceEntity lineDevice = new EcLineDeviceEntity();
		lineDevice.setEcLineDeviceId(UUIDGen.randomUUID());// 线路附属设备ID
		lineDevice.setOid(oid);// OID
		lineDevice.setEcLineNodesId(ditchCable.getLineNodesId());// 线路节点ID
		lineDevice.setLineNo(ditchCable.getLineNo());// 线路编号
		lineDevice.setDeviceType(type.getValue() + "");// 设备类型
		lineDevice.setDevObjId(objId);// 对象ID
		lineDevice.setPrjid(GlobalEntry.currentProject.getEmProjectId());// 项目ID
		lineDevice.setOrgid(GlobalEntry.currentProject.getOrgId());// ORGID
		// 保存线路设备关联表
		sameGrooveInformationBll.saveOrUpdate(lineDevice);
		// 保存日志
		commonBll.handleDataLog(lineDevice.getEcLineDeviceId(), TableNameEnum.XLFSSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增线路设备关联:" + type.getName(), true);
	}

	/**
	 * 线路附属具体设备(电缆电子标签、分接箱、电缆中间接头)和线路设备关联表
	 * 
	 * @param lineNodes
	 * @param lineDevice
	 * @param entity
	 * @param oid
	 */
	private void saveLineDevice(DitchCable ditchCable, EcLineNodesEntity lineNodes, EcLineDeviceEntity lineDevice,
			EcNodeEntity ecNodeEntity) {
		EcLineLabelEntityVo lineLabel = ditchCable.getLineLabelEntityVo();
		if (lineLabel != null) {
			// 如果是自动添加的标签，标签名称设为BQ+标识器ID
			if (lineLabel.getGxsj() == null) {
				BasicInfoFragment basicInfoFragment = markerCollectControll.getBasicInfoFragment();
				if (!lineLabel.getDevName()
						.equals("BQ"+basicInfoFragment.getAction().getViewHolder().getIcMarkerId().getEdtTextValue())) {
					lineLabel.setDevName("BQ" + basicInfoFragment.getAction().getViewHolder().getIcMarkerId().getEdtTextValue());
				}
			}			
			// 保存线路设备关联表
			if (lineLabel.getOperateMark() == 0 || lineLabel.getOperateMark() == 1 || lineLabel.getOperateMark() == 3) {// 新增、编辑下新增、关联
				saveLineDeviceLink(ecNodeEntity.getOid(), ditchCable, ResourceEnum.DZBQ, lineLabel.getObjId());
			}
			// 保存电缆电子标签
			savelineLabelData(ditchCable, lineLabel, ecNodeEntity);
			// 添加元素到数据集中
			double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
			HiElement hiElement = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, lineLabel,
					lineLabel.getObjId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElement,
					lineLabel);
		}

		EcMiddleJointEntityVo middleJoint = ditchCable.getMiddleJointEntityVo();
		if (middleJoint != null) {
			// 保存线路设备关联表
			if (middleJoint.getOperateMark() == 0 || middleJoint.getOperateMark() == 1
					|| middleJoint.getOperateMark() == 3) {// 新增、编辑下新增、设备关联
				saveLineDeviceLink(ecNodeEntity.getOid(), ditchCable, ResourceEnum.ZJJT, middleJoint.getObjId());
			}
			// 保存电缆中间接头
			saveMiddleJointData(ditchCable, middleJoint, ecNodeEntity);
			// 添加元素到数据集中
			double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
			HiElement hiElement = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, middleJoint,
					middleJoint.getObjId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElement,
					middleJoint);
		}

		EcHwgEntityVo hwg = ditchCable.getHwgEntityVo();
		if (hwg != null) {
			// 保存线路设备关联表
			if (hwg.getOperateMark() == 0 || hwg.getOperateMark() == 1 || hwg.getOperateMark() == 3) {// 新增、编辑下新增、设备关联
				saveLineDeviceLink(ecNodeEntity.getOid(), ditchCable, ResourceEnum.HWG, hwg.getObjId());
			}
			// 保存环网柜
			saveHwgData(ditchCable, hwg);
			// 添加元素到数据集中
			double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
			HiElement hiElement = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, hwg, hwg.getObjId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElement, hwg);
		}

		EcDlfzxEntityVo dlfzx = ditchCable.getDlfzxEntityVo();
		if (dlfzx != null) {
			// 保存线路设备关联表
			if (dlfzx.getOperateMark() == 0 || dlfzx.getOperateMark() == 1 || dlfzx.getOperateMark() == 3) {// 新增、编辑下新增、设备关联
				saveLineDeviceLink(ecNodeEntity.getOid(), ditchCable, ResourceEnum.DLFZX, dlfzx.getObjId());
			}
			// 保存电缆分支箱
			saveDlfzxData(ditchCable, dlfzx);
			// 添加元素到数据集中
			double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
			HiElement hiElement = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, dlfzx, dlfzx.getObjId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElement, dlfzx);
		}

		EcDydlfzxEntityVo dydlfzx = ditchCable.getDydlfzxEntityVo();
		if (dydlfzx != null) {
			// 保存线路设备关联表
			if (dydlfzx.getOperateMark() == 0 || dydlfzx.getOperateMark() == 1 || dydlfzx.getOperateMark() == 3) {// 新增、编辑下新增、设备关联
				saveLineDeviceLink(ecNodeEntity.getOid(), ditchCable, ResourceEnum.DYDLFZX, dydlfzx.getObjId());
			}
			// 保存低压电缆分支箱
			saveDydlfzxData(ditchCable, dydlfzx);
			// 添加元素到数据集中
			double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
			HiElement hiElement = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, dydlfzx,
					dydlfzx.getObjId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElement,
					dydlfzx);
		}
	}

	/**
	 * 保存线路节点关联表信息
	 * 
	 * @param lineNodes
	 * @param oid
	 */
	private void saveLineNodesData(DitchCable ditchCable, EcLineNodesEntity lineNodesEntity,
			EcNodeEntity ecNodeEntity) {
		if (!GlobalEntry.editNode || ditchCable.isEditAdd()) {
			lineNodesEntity.setEcLineNodesId(UUIDGen.randomUUID());// 线路节点ID
		}
		lineNodesEntity.setEcLineId(ditchCable.getLineId());// 电缆ID
		lineNodesEntity.setOid(ecNodeEntity.getOid());// OID
		// LogUtil.i(ecNodeEntity.getOid()+"节点oid");
		// lineNodes.setTgxx(tgxx);//TGXX
		lineNodesEntity.setNIndex(Integer.valueOf(ditchCable.getLineNodeSort()));// 顺序
		lineNodesEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());// 项目ID
		lineNodesEntity.setOrgid(GlobalEntry.currentProject.getOrgId());// ORGID
		// lineNodes.setUpSpace(upSpace);//上一节点距离
		lineNodesEntity.setUpLay(ditchCable.getLayType());// 上一节点敷设方式
		lineNodesEntity.setUpSpace(ditchCable.getPreviousNodeDistance());
		// lineNodes.setCableDeepth(cableDeepth);//深度|距电缆沟、槽、顶管底深度
		lineNodesEntity.setDeviceDesc(ditchCable.getDeviceDescription());//设备描述
		// lineNodes.setRemark(remark);//备注
		ditchCable.setLineNodesId(lineNodesEntity.getEcLineNodesId());
		sameGrooveInformationBll.saveOrUpdate(lineNodesEntity);
		//线路节点日志
		if (GlobalEntry.editNode) {//编辑
			if (ditchCable.isEditAdd()) {//编辑下新增
				commonBll.handleDataLog(lineNodesEntity.getEcLineNodesId(), TableNameEnum.XLJDGL.getTableName(),
						DataLogOperatorType.Add, WhetherEnum.NO, "编辑中新增线路节点:" + lineNodesEntity.getEcLineNodesId(), true);
			}else {//编辑下编辑
				commonBll.handleDataLog(lineNodesEntity.getEcLineNodesId(), TableNameEnum.XLJDGL.getTableName(),
						DataLogOperatorType.update, WhetherEnum.NO, "编辑中编辑线路节点:" + lineNodesEntity.getEcLineNodesId(), false);
			}
		}else {//新增
			commonBll.handleDataLog(lineNodesEntity.getEcLineNodesId(), TableNameEnum.XLJDGL.getTableName(),
					DataLogOperatorType.Add, WhetherEnum.NO, "新增线路节点:" + lineNodesEntity.getEcLineNodesId(), true);
		}
	}

	/**
	 * 保存电缆电子标签
	 * 
	 * @param lineLabel
	 * @param entity
	 */
	private void savelineLabelData(DitchCable ditchCable, EcLineLabelEntityVo lineLabel, EcNodeEntity ecNodeEntity) {
		// 1、保存电子标签
		lineLabel.setOid(ecNodeEntity.getOid());
		sameGrooveInformationBll.saveOrUpdate(lineLabel);
		// 2、保存日志
		if (lineLabel.getOperateMark() == 0 || lineLabel.getOperateMark() == 1) {// 新增、编辑下新增
			commonBll.handleDataLog(lineLabel.getObjId(), TableNameEnum.DLDZBQ.getTableName(), DataLogOperatorType.Add,
					WhetherEnum.NO, "新增电缆电子标签", true);
		} else if (lineLabel.getOperateMark() == 2 || lineLabel.getOperateMark() == 3) {// 编辑下编辑、设备关联
			commonBll.handleDataLog(lineLabel.getObjId(), TableNameEnum.DLDZBQ.getTableName(),
					DataLogOperatorType.update, WhetherEnum.NO, "编辑电缆电子标签", false);
		}
		// 3、附件保存
		List<OfflineAttach> comUploadList = lineLabel.getAttr();
		saveOfflineAttach(comUploadList);

	}

	/**
	 * 保存电缆中间接头
	 * 
	 * @param middleJoint
	 * @param entity
	 */
	private void saveMiddleJointData(DitchCable ditchCable, EcMiddleJointEntityVo middleJoint,
			EcNodeEntity ecNodeEntity) {
		// 1、保存电缆中间接头
		sameGrooveInformationBll.saveOrUpdate(middleJoint);
		// 2、日志
		if (middleJoint.getOperateMark() == 0 || middleJoint.getOperateMark() == 1) {// 新增
																						// 、编辑下新增
			commonBll.handleDataLog(middleJoint.getObjId(), TableNameEnum.DLZJJT.getTableName(),
					DataLogOperatorType.Add, WhetherEnum.NO, "新增电缆中间接头", true);
		} else if (middleJoint.getOperateMark() == 2 || middleJoint.getOperateMark() == 3) {// 编辑下编辑、
																							// 设备关联
			commonBll.handleDataLog(middleJoint.getObjId(), TableNameEnum.DLZJJT.getTableName(),
					DataLogOperatorType.update, WhetherEnum.NO, "编辑电缆中间接头", false);
		}
		// 保存接头标签数据
		EcLineLabelEntityVo ecLineLabelEntityVo = middleJoint.getEcLineLabelEntityVo();
		if (ecLineLabelEntityVo != null) {
			// 初始化日志
			commonBll.initIsLocal(ecLineLabelEntityVo.getObjId(), TableNameEnum.DLDZBQ.getTableName(), "OBJ_ID");
			// 保存
			ecLineLabelEntityVo.setOid(ecNodeEntity.getOid());
			commonBll.saveOrUpdate(ecLineLabelEntityVo);
			// 保存日志
			commonBll.handleDataLog(ecLineLabelEntityVo.getObjId(), TableNameEnum.DLDZBQ.getTableName(),
					DataLogOperatorType.Add, WhetherEnum.NO, "中间接头标签操作");
			// 保存工井标签附件
			List<OfflineAttach> comUploadEntityZjjtLabelList = ecLineLabelEntityVo.getAttr();
			if (comUploadEntityZjjtLabelList != null && comUploadEntityZjjtLabelList.size() > 0) {
				for (OfflineAttach offlineAttach : comUploadEntityZjjtLabelList) {
					// 保存附件
					sameGrooveInformationBll.saveOrUpdate(offlineAttach);
				}
			}
		}
		// 3、保存离线附件表
		List<OfflineAttach> comUploadList = middleJoint.getAttr();
		saveOfflineAttach(comUploadList);

	}

	/**
	 * 保存离线附件表
	 * 
	 * @param comUploadList
	 */
	private void saveOfflineAttach(List<OfflineAttach> comUploadList) {
		if (comUploadList != null && comUploadList.size() > 0) {
			for (OfflineAttach comUpload : comUploadList) {
				sameGrooveInformationBll.saveOrUpdate(comUpload);
			}
		}
	}

	/**
	 * 多选线路回调
	 */
	@Override
	public void getDitchCables(ArrayList<DitchCable> ditchCables) {
		ditchCableList.addAll(ditchCables);
		viewHolder.getIcLoopNum().setEdtText(String.valueOf(ditchCableList.size()));
		ditchCableAdapter.notifyDataSetChanged();
	}

}
