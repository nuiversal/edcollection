package com.winway.android.edcollection.adding.util;

import com.winway.android.edcollection.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.PopupWindow;

/**
 * NewFragment
 * 
 * @author ly
 *
 */
public class NewMarkWayTool {
	private static NewMarkWayTool instance;
	private boolean isOpenMarkWay = false;// 是否打开
	private PopupWindow popupWindowMarkWay = null;// 标识器选择PopupWindow
	private View markWayView;// 标识器状态view
	private ImageButton markToolEnter;// 标识器进入按钮

	private NewMarkWayTool() {
	}

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static NewMarkWayTool getInstance() {
		if (instance == null) {
			synchronized (NewMarkWayTool.class) {
				if (instance == null) {
					instance = new NewMarkWayTool();
				}
			}
		}
		return instance;
	}

	/**
	 * 选择标识器类型
	 * 
	 * @param context
	 * @param imageButton
	 */
	public void markShowTool(Context context, ImageButton imageButton) {
		this.markToolEnter = imageButton;
		if (isOpenMarkWay) {
			if (popupWindowMarkWay != null) {
				restMarkWay();
				closePopWid();
				imageButton.setImageResource(R.drawable.ico_bsq_n);
			}
		} else {
			isOpenMarkWay = true;
			if (popupWindowMarkWay == null) {
				popupWindowMarkWay = new PopupWindow();
				View contentView = View.inflate(context, R.layout.mark_way_tool, null);
				markWayView = contentView;
				ImageButton btnbsq = (ImageButton) contentView
						.findViewById(R.id.imgBtn_mark_way_bsq);
				ImageButton btnid = (ImageButton) contentView.findViewById(R.id.imgBtn_mark_way_id);
				ImageButton btnxh = (ImageButton) contentView.findViewById(R.id.imgBtn_mark_way_xh);
				btnbsq.setOnClickListener(orcl);
				btnid.setOnClickListener(orcl);
				btnxh.setOnClickListener(orcl);
				popupWindowMarkWay.setContentView(contentView);
				popupWindowMarkWay.setWidth(LayoutParams.WRAP_CONTENT);
				popupWindowMarkWay.setHeight(LayoutParams.WRAP_CONTENT);
				popupWindowMarkWay.setFocusable(false);
				popupWindowMarkWay.setOutsideTouchable(true);
			}
			int width = imageButton.getMeasuredWidth();
			int height = imageButton.getMeasuredHeight();
			popupWindowMarkWay.showAsDropDown(imageButton, -(width + 5), -height);
		}
	}

	public static final int BSQ_IMG = 1;
	public static final int MARKER_ORDER = 2;
	public static final int MARKER_NO = 3;
	public int WHAT = BSQ_IMG;
	public TreeMapUtil treeMapUtil = null;

	// 点击事件
	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imgBtn_mark_way_bsq:// 标识器
				restMarkWay();
				((ImageButton) v).setImageResource(R.drawable.ico_bsq_s);
				markToolEnter.setImageResource(R.drawable.ico_bsq_s);
				closePopWid();
				WHAT = BSQ_IMG;
				if (null != treeMapUtil) {
					treeMapUtil.drawTextLayer();
				}
				break;
			case R.id.imgBtn_mark_way_id:// id
				restMarkWay();
				((ImageButton) v).setImageResource(R.drawable.ico_id_s);
				markToolEnter.setImageResource(R.drawable.ico_id_s);
				closePopWid();
				WHAT = MARKER_NO;
				if (null != treeMapUtil) {
					treeMapUtil.drawTextLayer();
				}
				break;
			case R.id.imgBtn_mark_way_xh://
				restMarkWay();
				((ImageButton) v).setImageResource(R.drawable.ico_xh_s);
				markToolEnter.setImageResource(R.drawable.ico_xh_s);
				closePopWid();
				WHAT = MARKER_ORDER;
				if (null != treeMapUtil) {
					treeMapUtil.drawTextLayer();
				}
				break;
			default:
				break;
			}
		}
	};

	// 重置图标F
	private void restMarkWay() {
		if (markWayView != null) {
			ImageButton btnbsq = (ImageButton) markWayView.findViewById(R.id.imgBtn_mark_way_bsq);
			ImageButton btnid = (ImageButton) markWayView.findViewById(R.id.imgBtn_mark_way_id);
			ImageButton btnxh = (ImageButton) markWayView.findViewById(R.id.imgBtn_mark_way_xh);
			btnbsq.setImageResource(R.drawable.ico_bsq_n);
			btnid.setImageResource(R.drawable.ico_id_n);
			btnxh.setImageResource(R.drawable.ico_xh_n);
		}
	}

	// 关闭PopupWindow
	public void closePopWid() {
		if (popupWindowMarkWay != null) {
			popupWindowMarkWay.dismiss();
			// popupWindowMarkWay = null;
			isOpenMarkWay = false;
		}
	}
}
