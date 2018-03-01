package com.winway.android.edcollection.adding.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.customview.GJDrawView;
import com.winway.android.edcollection.adding.customview.Point;
import com.winway.android.edcollection.adding.entity.IsPluggingEnum;
import com.winway.android.edcollection.adding.entity.PhaseSeqEnum;
import com.winway.android.edcollection.adding.viewholder.DialogChanelPointViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.util.LineUtil;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.ListUtil;
import com.winway.android.util.ToastUtils;

public class DialogChannelPointSelectUtil {
	private DialogChanelPointViewHolder viewHolder;
	private Activity mActivity;
	private Point point;
	private View view;
	private GJDrawView gjDrawView;
	private BaseDBUtil baseDBUtil;
	/** 是否只是查看*/
	private boolean isJustLook = false;
	/** 是否未知线路*/
	private boolean isUnKnowLine;
	 private ColorPickerDialog colorDialog;

	public DialogChannelPointSelectUtil(Activity mActivity, GJDrawView gjDrawView, boolean isUnKnowLine) {
		this.mActivity = mActivity;
		this.gjDrawView = gjDrawView;
		this.isUnKnowLine = isUnKnowLine;
		viewHolder = new DialogChanelPointViewHolder();
		dialogUtil = new DialogUtil(mActivity);
		view = View.inflate(mActivity, R.layout.dialog_point_ref_line_selected, null);
		ViewUtils.inject(viewHolder, view);
		viewHolder.getIcFillColor().getEditTextView().setEnabled(false);
		initLines();
		initEvent();
	}

	private void initLines() {
		// 初始化选择的线路
		List<EcLineEntity> lines = LineUtil.getSelectedLineList();
		if (lines == null || lines.isEmpty()) {
			isJustLook = true;
			if (!isUnKnowLine) {
				ToastUtils.show(mActivity, "未选择采集线路只能查看");
			}
			return;
		}
		if (lines != null && !lines.isEmpty()) {
			LineUtil.addLine(lines);
			for (EcLineEntity lineEntity : lines) {
				listNameList.add(lineEntity.getName());
			}
		}
		listNameList.add("未知线路");
		listNameList.add("空管");
	}

	DialogUtil dialogUtil;

	public void showSelectingLineDialog(Point point, boolean isUnKnowLine) {
		this.point = point;
		this.isUnKnowLine = isUnKnowLine;
		initData();
		point.setLineName(viewHolder.getLineSelect().getEdtTextValue());
		viewHolder.getIcFillColor().setEdtText(point.getFillColor() == null ? "" : point.getFillColor()+"");
		initEvent();
		if (!isUnKnowLine) {
			dialogUtil.showAlertDialog(view, !isJustLook);
			dialogUtil.dialogOutsideTouchCancel(false);
		}
	}

	private ArrayList<String> listNameList = new ArrayList<>();

	private void initData() {
		if (isJustLook) {
			viewHolder.getIsPlugging().getEditTextView().setEnabled(false);
			viewHolder.getIscPhaseSeq().getEditTextView().setEnabled(false);
			if (point.getIsPlugging() == null) {
				viewHolder.getIsPlugging().setEdtTextValue(IsPluggingEnum.T.getName());
			}else {
				if (point.getIsPlugging() == 0) {
					viewHolder.getIsPlugging().setEdtTextValue(IsPluggingEnum.T.getName());
				} else if(point.getIsPlugging() == 1){
					viewHolder.getIsPlugging().setEdtTextValue(IsPluggingEnum.BuT.getName());
				}else {
					viewHolder.getIsPlugging().setEdtTextValue(IsPluggingEnum.BanT.getName());
				}
			}
			if (point.getPhaseSeq() == null) {
				viewHolder.getIscPhaseSeq().setEdtTextValue(PhaseSeqEnum.W.getName());
			}else {
				viewHolder.getIscPhaseSeq().setEdtTextValue(point.getPhaseSeq());
			}
			viewHolder.getPointNo().setEdtText(point.getNo());
			viewHolder.getPointR().setEdtText(point.getR() + "");
			viewHolder.getPointR().getEditTextView().setEnabled(false);
			viewHolder.getPointNo().getEditTextView().setEnabled(false);
			viewHolder.getSureBT().setVisibility(View.GONE);
			viewHolder.getBtnChooseColor().setVisibility(View.GONE);
			viewHolder.getLineSelect().getEditTextView().setEnabled(false);
			EcLineEntity lineEntity = LineUtil.getByLineId(point.getLineId());
			if (isUnKnowLine) {
				String linename = "未知线路";
				if (listNameList == null || listNameList.isEmpty()) {
					listNameList.add(linename);
				}
				SetUnknowLineSelect();
				listNameList.remove(linename);
			} else if (lineEntity == null && null != point.getLineId()) {
				if (null == baseDBUtil) {
					baseDBUtil = new BaseDBUtil(mActivity, new File(GlobalEntry.prjDbUrl));
				}
				EcLineEntity queryEntity = new EcLineEntity();
				queryEntity.setEcLineId(point.getLineId());
				try {
					List<EcLineEntity> excuteQuery = baseDBUtil.excuteQuery(EcLineEntity.class, queryEntity);
					if (null != excuteQuery && !excuteQuery.isEmpty()) {
						lineEntity = excuteQuery.get(0);
					}
				} catch (DbException e) {
					e.printStackTrace();
				}
			}
			if (null != lineEntity) {
				viewHolder.getLineSelect().setEdtTextValue(lineEntity.getName());
			} else {
				if (null == point.getLineId()) {
					viewHolder.getLineSelect().setEdtTextValue("空管");
				} else {
					viewHolder.getLineSelect().setEdtTextValue("未知线路");
				}
			}

		} else {
			String[] isPlStrings = new String[] {IsPluggingEnum.T.getName(),IsPluggingEnum.BuT.getName(),IsPluggingEnum.BanT.getName()};
			viewHolder.getIsPlugging().setAdapter(new InputSelectAdapter(mActivity,isPlStrings));
			if (point.getIsPlugging() != null) {
				if (point.getIsPlugging() == 0) {
					viewHolder.getIsPlugging().setSelect(0);
				} else if(point.getIsPlugging() == 1){
					viewHolder.getIsPlugging().setSelect(1);
				}else {
					viewHolder.getIsPlugging().setSelect(2);
				}
			}
			List<String> phaseSeqList = new ArrayList<String>();
			phaseSeqList.add(PhaseSeqEnum.W.getName());
			phaseSeqList.add(PhaseSeqEnum.AX.getName());
			phaseSeqList.add(PhaseSeqEnum.BX.getName());
			phaseSeqList.add(PhaseSeqEnum.CX.getName());
			viewHolder.getIscPhaseSeq().setAdapter(new InputSelectAdapter(mActivity, phaseSeqList));
			if (point.getPhaseSeq() != null) {
				viewHolder.getIscPhaseSeq().setEdtTextValue(point.getPhaseSeq());
			}
			viewHolder.getIsPlugging().getEditTextView().setEnabled(false);
			viewHolder.getIscPhaseSeq().getEditTextView().setEnabled(false);
			viewHolder.getPointNo().setEdtText(point.getNo());
			viewHolder.getPointR().setEdtText(point.getR() + "");
			viewHolder.getPointNo().setEditTextInputType(InputType.TYPE_CLASS_NUMBER);
			viewHolder.getPointR().setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			viewHolder.getLineSelect().getEditTextView().setEnabled(false);
			if (isUnKnowLine) {
				SetUnknowLineSelect();
			} else {
				if (null != listNameList && !listNameList.isEmpty()) {
					// 未选择线路
					viewHolder.getLineSelect().setAdapter(new InputSelectAdapter(mActivity, listNameList));
				}
				if (null == point.getLineId()) {
					// 空管
					viewHolder.getLineSelect().setSelect(listNameList.size() - 1);
				} else {
					EcLineEntity lineEntity = LineUtil.getByLineId(point.getLineId());
					if (lineEntity == null) {
						point.setLineId(null);
					} else {
						boolean inList = ListUtil.isInList(lineEntity.getName(), listNameList);
						if (inList) {
							viewHolder.getLineSelect().setSelect(listNameList.indexOf(lineEntity.getName()));
						}
					}
				}
			}
		}
	}

	private void SetUnknowLineSelect() {
		if (null != listNameList && !listNameList.isEmpty()) {
			// 未选择线路
			viewHolder.getLineSelect().setAdapter(new InputSelectAdapter(mActivity, listNameList));
			viewHolder.getLineSelect().setEdtTextValue("未知线路");
			viewHolder.getLineSelect().setSelect(listNameList.indexOf("未知线路"));
		}
		EcLineEntity ecLineEntity = LineUtil.getByLinename(viewHolder.getLineSelect().getEdtTextValue());
		if (null == ecLineEntity) {
			point.setLineId(null);
		} else {
			point.setLineId(ecLineEntity.getEcLineId());
		}
		gjDrawView.invalidate();
	}

	private void initEvent() {
		viewHolder.getSureBT().setOnClickListener(ocl);
		viewHolder.getBtnChooseColor().setOnClickListener(ocl);
		viewHolder.getIvClose().setOnClickListener(ocl);
		viewHolder.getLineSelect().getEditTextView().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//监听关联线路文本变化，当关联线路为空管或者未知线路时，填充颜色为空，否则为默认颜色“#ffdadada”
				if(!viewHolder.getLineSelect().getEdtTextValue().equals("空管") && 
						!viewHolder.getLineSelect().getEdtTextValue().equals("未知线路")) {
					viewHolder.getIcFillColor().setEdtText("#ffdadada");
				}else {
					viewHolder.getIcFillColor().setEdtText("");
				}
			}
		});
	}
	private OnClickListener ocl = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.dialog_channel_point_sure: { //确定按钮

				if (TextUtils.isEmpty(viewHolder.getPointNo().getEdtTextValue())) {
					ToastUtils.show(mActivity, "管孔序号不能为空！");
					return;
				}
				if (TextUtils.isEmpty(viewHolder.getPointR().getEdtTextValue())) {
					ToastUtils.show(mActivity, "管孔直径不能为空！");
					return;
				}
				if (!viewHolder.getLineSelect().getEdtTextValue().equals("空管") && !viewHolder.getLineSelect().getEdtTextValue().equals("未知线路")) {
					if (TextUtils.isEmpty(viewHolder.getIcFillColor().getEdtTextValue())) {
						ToastUtils.show(mActivity, "线路填充颜色不能为空！");
						return;
					}
				}
				//判断线路是否关联过管孔
				EcLineEntity lineEntity = LineUtil.getByLinename(viewHolder.getLineSelect().getEdtTextValue());
				if(lineEntity!=null && isLinkLine(lineEntity)){
					ToastUtils.show(mActivity, "该线路已经被其他管孔关联过");
					return ;
				}
				point.setR(Float.parseFloat(viewHolder.getPointR().getEdtTextValue()));
				point.setNo(viewHolder.getPointNo().getEdtTextValue());
				if (viewHolder.getIsPlugging().getEdtTextValue().equals(IsPluggingEnum.T.getName())) {
					point.setIsPlugging(0);
				} else if(viewHolder.getIsPlugging().getEdtTextValue().equals(IsPluggingEnum.BuT.getName())){
					point.setIsPlugging(1);
				}else {
					point.setIsPlugging(2);
				}
				if ("空管".equals(viewHolder.getLineSelect().getEdtTextValue())) {
					point.setLineId(null);
				} else {
					EcLineEntity ecLineEntity = LineUtil.getByLinename(viewHolder.getLineSelect().getEdtTextValue());
					if (null == ecLineEntity) {
						point.setLineId(null);
					} else {
						point.setLineId(ecLineEntity.getEcLineId());
					}
					// 移除线路
					// listNameList.remove(viewHolder.getLineSelect().getEdtTextValue());
				}
				point.setPhaseSeq(viewHolder.getIscPhaseSeq().getEdtTextValue().trim());
				point.setLineName(viewHolder.getLineSelect().getEdtTextValue());
				if (!viewHolder.getIcFillColor().getEdtTextValue().equals("")) {
					point.setFillColor(viewHolder.getIcFillColor().getEdtTextValue());
				}
				if (viewHolder.getLineSelect().getEdtTextValue().equals("未知线路")) {
					point.setFillColor("#ff808080");
				}
				dialogUtil.dismissDialog();
				// dialogUtil.destroy();
				if (point.getFillColor() != null) {
					gjDrawView.setFillColor(point,Color.parseColor(point.getFillColor()));
				}
				gjDrawView.invalidate();
			
			}
			break;
			case R.id.dialog_channel_point_choose_color: { //填充颜色
				if (viewHolder.getLineSelect().getEdtTextValue().equals("空管") || viewHolder.getLineSelect().getEdtTextValue().equals("未知线路")) {
					ToastUtils.show(mActivity, "线路为空管或未知线路时不可选择填充颜色！");
					return;
				}
				 //第二个参数要把圆的颜色赋值过来
				colorDialog = new ColorPickerDialog(mActivity, point.getFillColor()==null ? Color.parseColor("#ff000000") : Color.parseColor(point.getFillColor()),   
                        "你要选择的颜色是？",   
                        new ColorPickerDialog.OnColorChangedListener() {  
                      
                    @Override  
                    public void colorChanged(int color) { 
                    	String fillColor = Integer.toHexString(color);
                    	viewHolder.getIcFillColor().setEdtText("#"+fillColor);
                    }  
                });  
				colorDialog.show();  
			}	
				break;
			case R.id.iv_dialog_point_ref_line_selected_close: { //关闭图标
				dialogUtil.dismissDialog();
				break;
			}
			default:
				break;
			}
		}
	};
	
	/**
	 * 过滤掉关联的线路
	 * 
	 * @param lineEntity
	 * @return true表示已经关联过
	 */
	private boolean isLinkLine(EcLineEntity lineEntity) {
		List<Point> points = gjDrawView.getPoints();
		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			String lineId = point.getLineId();// 管孔关联的线路Id
			String lineName = point.getLineName();
			if (TextUtils.isEmpty(lineId)) {
				continue;
			}
			if(lineName.equals("未知线路")){
				return false;
			}
			if (lineId.equals(lineEntity.getEcLineId())) {
				return true;
			}

		}
		return false;

	}
}
