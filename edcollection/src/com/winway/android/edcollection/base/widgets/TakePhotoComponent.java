package com.winway.android.edcollection.base.widgets;

import com.winway.android.edcollection.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 输入框组件
 * 
 * @author zgq
 *
 */
public class TakePhotoComponent extends RelativeLayout {

	private TextView tvTitle;
	private ImageView ivTakePic;
	private ImageView ivDel;
	private int pId;// 父容器id
	private LinearLayout llTakPhotosBox;

	public TakePhotoComponent(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		if (isInEditMode()) {
			return;
		}
		init(context, attrs);
	}

	public TakePhotoComponent(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		if (isInEditMode()) {
			return;
		}
		init(context, attrs);
	}

	@SuppressLint("Recycle")
	private void init(Context context, AttributeSet attrs) {
		// 初始化ui
		View view = View.inflate(getContext(), R.layout.take_photo, this);
		pId = view.getId();
		// 初始化控件
		tvTitle = (TextView) view.findViewById(R.id.tv_take_photo);
		ivTakePic = (ImageView) view.findViewById(R.id.iv_take_photo_takePic);
		ivDel = (ImageView) view.findViewById(R.id.iv_take_photo_delPic);
		llTakPhotosBox = (LinearLayout) findViewById(R.id.ll_take_photo_plays);

		// 获得自定义属性
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TakePhotoComponentAttr);
		int count = ta.getIndexCount();
		for (int i = 0; i < count; i++) {
			int itemId = ta.getIndex(i); // 获取某个属性的Id值
			switch (itemId) {
			case R.styleable.TakePhotoComponentAttr_takePhotoTitle: // 设置标题
			{
				tvTitle.setText(ta.getString(itemId));
				break;
			}

			default:
				break;
			}
		}

		// 回收资源
		ta.recycle();
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	/**
	 * 获取拍照view
	 * 
	 * @return
	 */
	public ImageView getTakePhotoView() {
		return ivTakePic;
	}

	/**
	 * 获取拍照view
	 * 
	 * @return
	 */
	public ImageView getDelPhotoView() {
		return ivDel;
	}

	/**
	 * 获取组件id
	 * 
	 * @return
	 */
	public int getPid() {
		return pId;
	}

	/**
	 * 获取装载缩略图的容器
	 * 
	 * @return
	 */
	public LinearLayout getLlImagesBox() {
		return this.llTakPhotosBox;
	}

}
