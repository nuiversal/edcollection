package com.winway.android.ewidgets.dialog.table;

import java.util.List;

import com.winway.android.ewidgets.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.Toast;

public class TableUtils {
	private static AlertDialog dlg;
	private Window window = null;
	private static TableUtils tableUtils;

	public static TableUtils getInstance() {
		if (tableUtils == null) {
			synchronized (TableUtils.class) {
				if (tableUtils == null) {
					tableUtils = new TableUtils();
				}
			}

		}
		return tableUtils;
	}

	public void test(Context context) {
		Toast.makeText(context, "测试", 200).show();
	}

	/**
	 * 
	 * @param context
	 * @param header
	 * @param list
	 */
	public void showDetailInfoTr(Context context, String[] header, List<Object[]> list) {
		if (dlg != null && dlg.isShowing()) {
			dlg.dismiss();
			dlg = null;
		}
		dlg = new AlertDialog.Builder(context).create();
		dlg.setCancelable(true);
		dlg.show();
		window = dlg.getWindow();
		KtableView kView = new KtableView(context, null);
		window.setContentView(kView);
		// 添加表头信息
		if (header != null && header.length > 0) {
			kView.setTableHeaders(header);// 表头
		}
		// 添加表内容
		if (list != null && list.size() > 0) {
			kView.addNewRows(list);
		}
	}

	/**
	 * 动态生成表格数据并弹出
	 */
	public void showDetailAndInfoTr(Context context, String[] header, List<Object[]> list) {
		if (dlg != null && dlg.isShowing()) {
			dlg.dismiss();
			dlg = null;
		}
		dlg = new AlertDialog.Builder(context).create();
		dlg.setCancelable(true);
		dlg.show();
		window = dlg.getWindow();

		window.setContentView(R.layout.popupwindow_layerinfo);
		TableLayout table = (TableLayout) window.findViewById(R.id.table_popupwindow_layerinfo);

		table.addView(addTableInfo(context, header, list));// 动态生成表格数据

		window.findViewById(R.id.btn_popupwindow_layerinfo_close).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.cancel();
			}
		});
	}

	/**
	 * 动态生成表格数据
	 * @param context
	 * @param header
	 * @param list
	 * @return
	 */
	public KtableView addTableInfo(Context context, String[] header, List<Object[]> list) {
		KtableView ktableView = new KtableView(context, null);
		// ktableView.setTableCellMaxEms(4);
		// 添加表头信息
		if (header != null && header.length > 0) {
			ktableView.setTableHeaders(header);// 表头
		}
		// 添加表内容
		if (list != null && list.size() > 0) {
			ktableView.addNewRows(list);
		}
		return ktableView;
	}

	/**
	 * 动态生成表格数据
	 * @param context
	 * @param header
	 * @param list
	 * @param cellTextSise   单元格字体大小
	 * @param cellTextColor  单元格字体颜色
	 * @return
	 */
	public KtableView addTableInfo(Context context, String[] header, List<Object[]> list, int cellTextSise,
			int cellTextColor) {
		KtableView ktableView = new KtableView(context, null);
		ktableView.setTableCellTextSize(cellTextSise);
		ktableView.setTableCellTextColor(cellTextColor);
		// ktableView.setTableCellMaxEms(4);
		// 添加表头信息
		if (header != null && header.length > 0) {
			ktableView.setTableHeaders(header);// 表头
		}
		// 添加表内容
		if (list != null && list.size() > 0) {
			ktableView.addNewRows(list);
		}
		return ktableView;
	}
}
