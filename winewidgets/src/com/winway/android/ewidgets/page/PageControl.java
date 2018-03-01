package com.winway.android.ewidgets.page;

import com.winway.android.ewidgets.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 分页组件
 * 
 * @author zgq
 *
 */
public class PageControl extends LinearLayout {
	private ImageView firstImg;// 第一页
	private ImageView preImg;// 上一页
	private ImageView nextImg;// 下一页
	private ImageView endImg;// 最后一页
	private TextView totalPageText;// 总页数
	private TextView curPageText;// 当前页
	private int numPerPage = 10;// 每页的大小
	private int curPage = 1;// 当前页
	private int count = 0;// 总记录数
	private OnPageChangeListener pageChangeListener;

	// 全局属性设置
	private String bgColorStr = "#aabbcc";

	public PageControl(Context context) {
		super(context);
		initPageComposite(context);
	}

	public PageControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPageComposite(context);
	}

	@SuppressLint("NewApi")
	public PageControl(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPageComposite(context);
	}

	// 初始化组件
	private void initPageComposite(Context context) {
		View view = LayoutInflater.from(context).inflate(com.winway.android.ewidgets.R.layout.paging, this);
		firstImg = (ImageView) view.findViewById(com.winway.android.ewidgets.R.id.iv_paging_firstpage);
		firstImg.setOnClickListener(orcl);
		preImg = (ImageView) view.findViewById(com.winway.android.ewidgets.R.id.iv_paging_prepage);
		preImg.setOnClickListener(orcl);
		nextImg = (ImageView) view.findViewById(com.winway.android.ewidgets.R.id.iv_paging_nextpage);
		nextImg.setOnClickListener(orcl);
		endImg = (ImageView) view.findViewById(com.winway.android.ewidgets.R.id.iv_paging_lastpage);
		endImg.setOnClickListener(orcl);
		totalPageText = (TextView) view.findViewById(com.winway.android.ewidgets.R.id.tv_paging_totalPage);
		curPageText = (TextView) view.findViewById(com.winway.android.ewidgets.R.id.tv_paging_curPage);
	}

	/**
	 * 初始化分页组件的显示状态
	 * 
	 * @param newCount
	 */
	public void initPageShow(int newCount) {
		count = newCount;
		int totalPage = count % numPerPage == 0 ? count / numPerPage : count / numPerPage + 1;// 总页数
		curPage = 1;
		firstImg.setEnabled(false);
		preImg.setEnabled(false);
		if (totalPage <= 1) {
			endImg.setEnabled(false);
			nextImg.setEnabled(false);
		} else {
			endImg.setEnabled(true);
			nextImg.setEnabled(true);
		}
		totalPageText.setText("总页数: " + totalPage);
		curPageText.setText("当前页 :" + curPage);
	}

	/**
	 * 分页按钮被点击时更新状态,该方法要在initPageShow后调用
	 */
	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			if (pageChangeListener == null) {
				return;
			}
			int totalPage = count % numPerPage == 0 ? count / numPerPage : count / numPerPage + 1;
			Integer id = view.getId();
			if (R.id.iv_paging_firstpage == id) {// 第一页
				curPage = 1;
				firstImg.setEnabled(false);
				preImg.setEnabled(false);
				if (totalPage > 1) {
					nextImg.setEnabled(true);
					endImg.setEnabled(true);
				}
			} else if (R.id.iv_paging_prepage == id) {// 上一页
				curPage--;
				if (curPage == 1) {
					firstImg.setEnabled(false);
					preImg.setEnabled(false);
				}
				if (totalPage > 1) {
					nextImg.setEnabled(true);
					endImg.setEnabled(true);
				}
			} else if (R.id.iv_paging_nextpage == id) {// 下一页
				curPage++;
				if (curPage == totalPage) {
					nextImg.setEnabled(false);
					endImg.setEnabled(false);
				}
				firstImg.setEnabled(true);
				preImg.setEnabled(true);
			} else if (R.id.iv_paging_lastpage == id) {// 最后页
				curPage = totalPage;
				nextImg.setEnabled(false);
				endImg.setEnabled(false);
				firstImg.setEnabled(true);
				preImg.setEnabled(true);
			}

			totalPageText.setText("总页数: " + totalPage);
			curPageText.setText("当前页 :" + curPage);
			pageChangeListener.pageChanged(curPage, numPerPage);

		}
	};

	public OnPageChangeListener getPageChangeListener() {
		return pageChangeListener;
	}

	/**
	 * 设置分页监听事件
	 * 
	 * @param pageChangeListener
	 */
	public void setPageChangeListener(OnPageChangeListener pageChangeListener) {
		this.pageChangeListener = pageChangeListener;
	}
}