package com.winway.android.media.photo.photoselector.ui;

/**
 * 
 * @author Aizaz AZ
 *
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.winway.android.media.R;
import com.winway.android.media.photo.photoselector.model.PhotoModel;
import com.winway.android.media.photo.photoselector.util.AnimationUtil;

import java.util.List;

public class BasePhotoPreviewActivity extends Activity implements OnPageChangeListener, OnClickListener {

	private ViewPager mViewPager;
	private RelativeLayout layoutTop;
	private ImageButton btnBack;
	private TextView tvPercent;
	protected List<PhotoModel> photos;
	protected int current;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ?????????
		setContentView(R.layout.activity_photopreview);
		layoutTop = (RelativeLayout) findViewById(R.id.layout_top_app);
		btnBack = (ImageButton) findViewById(R.id.btn_back_app);
		tvPercent = (TextView) findViewById(R.id.tv_percent_app);
		mViewPager = (ViewPager) findViewById(R.id.vp_base_app);

		btnBack.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(this);

		overridePendingTransition(R.anim.activity_alpha_action_in, 0); // ????Ч??

	}

	/** ?????????????? */
	protected void bindData() {
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setCurrentItem(current);
	}

	/**
	 * ??????????????????
	 */
	private PagerAdapter mPagerAdapter = new PagerAdapter() {

		@Override
		public int getCount() {
			if (photos == null) {
				return 0;
			} else {
				return photos.size();
			}
		}

		@Override
		public View instantiateItem(final ViewGroup container, final int position) {
			PhotoPreview photoPreview = new PhotoPreview(getApplicationContext());
			((ViewPager) container).addView(photoPreview);
			photoPreview.loadImage(photos.get(position));
			photoPreview.setOnClickListener(photoItemClickListener);
			return photoPreview;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	};
	protected boolean isUp;

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_back_app)
			finish();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		current = arg0;
		updatePercent();
	}

	protected void updatePercent() {
		tvPercent.setText((current + 1) + "/" + photos.size());
	}

	/** ??????????? */
	private OnClickListener photoItemClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!isUp) {
				new AnimationUtil(getApplicationContext(), R.anim.translate_up)
						.setInterpolator(new LinearInterpolator()).setFillAfter(true).startAnimation(layoutTop);
				isUp = true;
			} else {
				new AnimationUtil(getApplicationContext(), R.anim.translate_down_current)
						.setInterpolator(new LinearInterpolator()).setFillAfter(true).startAnimation(layoutTop);
				isUp = false;
			}
		}
	};
}
