package com.winway.android.ewidgets.dialog.table;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

public abstract class AddRow {

	protected Context context;
	protected int color = Color.GREEN; // 分隔颜色
	protected int width = 1; // 设置分隔线的宽的，包括垂直和水平的分割线
	protected int height = TableRow.LayoutParams.WRAP_CONTENT;// 定义这个这一行的高度
	// 一个默认的tabRow的布局参数
	protected TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
			TableLayout.LayoutParams.WRAP_CONTENT);

	public AddRow(Context context) {
		this.context = context;
	}

	/**
	 * 添加一行内容
	 * 
	 * @return
	 */
	abstract public TableRow addTableRow();

	/*
	 * 设置分割线的颜色
	 */
	public void setDivisonColor(int color) {
		this.color = color;
	}

	/*
	 * 设置分割线的宽度
	 */
	public void setDivisonWidth(int width) {
		this.width = width;
	}

	/*
	 * 设置tabRow的布局参数 当然其宽度是无法设置的，这里只能设置高度
	 */
	public void setTabRowHeight(int height) {
		params = new TableLayout.LayoutParams(height, TableLayout.LayoutParams.WRAP_CONTENT);
	}
	
	
	/*
	 * 水平竖直分割线
	 */
	protected View addDivision() {
		View view = new View(context);
		TableRow.LayoutParams params = new TableRow.LayoutParams(width, TableRow.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);
		view.setBackgroundColor(color);
		return view;

	}

}
