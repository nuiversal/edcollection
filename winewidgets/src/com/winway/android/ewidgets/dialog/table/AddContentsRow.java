package com.winway.android.ewidgets.dialog.table;

import java.math.BigDecimal;

import com.winway.android.ewidgets.R;
import com.winway.android.util.ImageHandle;
import com.winway.android.util.PhotoUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

/* 
 * 添加内容 
 */
public class AddContentsRow extends AddRow {

	private Object[] objects; // 表格填充内容
	private int resId;
	private int backcolor = Color.WHITE; // 默认背景为白色

	private boolean isBackground = false;

	private int textMaxlength = 10;// 如果有文本信息，则文本信息的长度
	private int textColor = Color.parseColor("#666666");
	private int textSize = 18; // 文字大小 如果是文本的信息
	private int cellTopBottomPading = 10;

	public AddContentsRow(Context context, Object[] objects) {
		super(context);
		// TODO Auto-generated constructor stub
		this.objects = objects;
	}

	@Override
	public TableRow addTableRow() {
		TableRow tableRow = new TableRow(context);
		// tableRow.addView(addDivision());
		tableRow.setLayoutParams(params);

		for (int i = 0; i < objects.length; i++) {
			String value = null;
			if (objects[i] instanceof BigDecimal) {
				value = String.valueOf(((BigDecimal) objects[i]).doubleValue());
			} else if (objects[i] instanceof Double) {
				value = String.valueOf(((Double) objects[i]));
			} else if ((objects[i] instanceof Float)) {
				value = String.valueOf(((Float) objects[i]));
			} else if (objects[i] instanceof Integer) {
				value = String.valueOf(((Integer) objects[i]));
			} else {
				value = (String) objects[i];
			}

			TableRow.LayoutParams param = null;
			if ((i + 1) == objects.length) {// 判断是否是最后一个
				param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
						TableRow.LayoutParams.MATCH_PARENT, 1);// 如果是将空白的列补充
			} else {
				param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
						TableRow.LayoutParams.MATCH_PARENT);
			}
			//// 验证是否是图片路径 已"$"开始和"$"结束 形如“$____$ ”
			if (value.startsWith("$") && value.endsWith("$")) {
				// 照片路径
				value = value.substring(value.indexOf("$") + 1, value.lastIndexOf("$"));
				final String imgPath = value;
				LinearLayout linearLayout = new LinearLayout(context);
				linearLayout.setLayoutParams(param);
				LinearLayout.LayoutParams iParams = new LinearLayout.LayoutParams(300, 100);
				iParams.setMargins(0, 5, 0, 5);
				linearLayout.setGravity(Gravity.CENTER);
				ImageView imageView = new ImageView(context);
				imageView.setLayoutParams(iParams);
				// Bitmap bm = BitmapFactory.decodeFile(value);
				Bitmap bm = ImageHandle.getInstance().decodeSampledBitmapFromResource(imgPath, (Activity) context);
				imageView.setImageBitmap(bm);
				linearLayout.setBackgroundResource(R.drawable.border);
				imageView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						PhotoUtil.openPhoto((Activity) context, imgPath);
					}
				});
				linearLayout.addView(imageView);
				tableRow.addView(linearLayout);
			} else {
				TextView textView = new TextView(context);
				textView.setClickable(true);
				textView.setGravity(Gravity.CENTER | Gravity.CENTER);
				if (isBackground) {
					textView.setBackgroundResource(resId);
				} else {
					textView.setBackgroundColor(backcolor);
				}
				textView.setLayoutParams(param);
				textView.setBackgroundColor(Color.WHITE);
				textView.setGravity(Gravity.CENTER | Gravity.CENTER); // 文本居中显示
				textView.setMaxEms(textMaxlength);// 定于每一行最多显示几个字
				// textView.setTextSize(textSize); //float型
				textView.setBackgroundColor(backcolor);
				textView.setPadding(10, cellTopBottomPading, 10, cellTopBottomPading);
				textView.setBackgroundResource(R.drawable.border);
				textView.setText(String.valueOf(value));
				textView.setTextColor(textColor);
				textView.setTextSize(textSize);

				tableRow.addView(textView);
			}
			// 添加分割线
			// tableRow.addView(addDivision());
		}
		return tableRow;
	}

	/*
	 * 设置背景的颜色
	 */
	public void setBackground(int resId) {
		this.resId = resId;
		isBackground = true;
	}

	public void setBackColor(int color) {
		this.backcolor = color;
		isBackground = false;
	}

	public void setTextMaxEms(int size) {
		this.textMaxlength = size;
	}

	public void setTextSize(int size) {
		this.textSize = size;
	}

	public void setTextColor(int color) {
		this.textColor = color;
	}

	public void setCellTopBottomPading(int cellTopBottomPading) {
		this.cellTopBottomPading = cellTopBottomPading;
	}

}
