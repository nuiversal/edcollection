package com.winway.android.edcollection.adding.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.winway.android.edcollection.adding.bll.BasicInfoBll;
import com.winway.android.edcollection.adding.controll.BasicInfoControll;
import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.ToastUtil;

/**
 * Created by mr-lao on 2015/10/12. 标识器ID的文本监听
 */
public class MarkerIDWatcher implements TextWatcher {
	private EditText IDET;
	public static final int BeforeTextChanged = 0;
	public static final int OnTextChanged = 1;
	public static final int AfterTextChanged = 2;
	private MarketIDWatcherListener listener = null;
	private Activity mActivity;
	private BasicInfoBll basicInfoBll;
	public static String editMarkerId;

	public void setEditMarkerId(String editMarkerId) {
		MarkerIDWatcher.editMarkerId = editMarkerId;
	}

	public static String getEditMarkerId() {
		return editMarkerId;
	}

	public MarkerIDWatcher(EditText editText, Activity mActivity, BasicInfoBll basicInfoBll) {
		this.IDET = editText;
		editText.addTextChangedListener(this);
		this.mActivity = mActivity;
		this.basicInfoBll = basicInfoBll;
		setEditMarkerId(editText.getText().toString());
	}

	public void setListener(MarketIDWatcherListener listener) {
		this.listener = listener;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		if (null != listener) {
			listener.textChange(BeforeTextChanged, s);
		}
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (null != listener) {
			listener.textChange(OnTextChanged, s);
		}
		String str = s.toString();
		if (count == 0) {
			// 说明这是删除
			return;
		}
		// 当标识类型是虚拟点或者安键环时，不加“_”
		if (BasicInfoControll.markerType == NodeMarkerType.AJH.getValue()) {
			return;
		}
		if (str.length() == 3 || str.length() == 7) {
			str += "-";
			IDET.setText(str);
			IDET.setSelection(str.length());
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		if (null != listener) {
			listener.textChange(AfterTextChanged, s);
		}
	}

	public interface MarketIDWatcherListener {
		void textChange(int type, CharSequence text);
	}

	public DefaulltMarketIDWatcherListener defaulltMarketIDWatcherListener = new DefaulltMarketIDWatcherListener();

	public class DefaulltMarketIDWatcherListener implements MarketIDWatcherListener {
		@Override
		public void textChange(int type, CharSequence text) {
			if (type == OnTextChanged) {
				if (BasicInfoControll.markerType == NodeMarkerType.AJH.getValue()) {
					checkMarkerNoOrChangeHistoryNode(text, 12);
				} else {
					checkMarkerNoOrChangeHistoryNode(text, 12);
				}
			}
		}
	}

	/**
	 * 显示历史数据编辑dialog
	 * 
	 * @param historyNode
	 * @param text
	 * 
	 * @author xs
	 */
	private void showAlertDialog(final EcNodeEntity historyNode, final CharSequence text) {
		final DialogUtil dialogU = new DialogUtil(mActivity);
		dialogU.showAlertDialog("是否编辑历史数据", new String[] { "确认", "取消" }, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					Intent data = new Intent();
					data.putExtra("historyNode", historyNode);
					mActivity.setResult(Activity.RESULT_OK, data);
					mActivity.finish();
					dialog.dismiss();
				} else {
					checkMarkerNo(text);
					dialog.dismiss();
				}
			}
		}, true);
		dialogU.dialogOutsideTouchCancel(false);
	}

	/**
	 * 检查标识器编号or修改历史节点信息
	 * 
	 * @param text
	 * @param count
	 * 
	 * @author xs
	 */
	private void checkMarkerNoOrChangeHistoryNode(CharSequence text, int count) {
		if (text.length() >= count) {
			// 进行数据库查找
			String markerId = IDET.getText().toString();
			if (GlobalEntry.editNode == true) {
				if (markerId.equals(BasicInfoControll.initEditMarkerNo)) {
					return;
				}
			}
			boolean isExist = basicInfoBll.checkMarkerId(markerId);
			final EcNodeEntity historyNode = basicInfoBll.getExistNode(markerId);
			if (isExist || null != historyNode) {
				if (!GlobalEntry.editNode) {
					showAlertDialog(historyNode, text);
				} else {
					checkMarkerNo(text);
				}
			} else {
				BasicInfoControll.markerNo = text.toString();
				BasicInfoControll.isWrittenMarkerNo = true;
			}
		} else {
			BasicInfoControll.markerNo = null;
			BasicInfoControll.isWrittenMarkerNo = false;
		}
	}

	/**
	 * 检查标识器编号
	 * 
	 * @param text
	 * 
	 * @author xs
	 */
	private void checkMarkerNo(CharSequence text) {
		ToastUtil.show(mActivity, "标识器id不可用", 200);
		IDET.setText(text.subSequence(0, text.length() - 1));
		IDET.setSelection(IDET.getText().length());
		BasicInfoControll.markerNo = null;
	}

}
