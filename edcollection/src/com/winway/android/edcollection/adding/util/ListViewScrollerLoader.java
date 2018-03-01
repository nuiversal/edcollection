package com.winway.android.edcollection.adding.util;

import com.winway.android.util.LogUtil;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author mr-lao
 *
 */
@SuppressLint("HandlerLeak")
public class ListViewScrollerLoader {
	private ListView listView;
	private TextView moreTV;
	private LoadListener loaderListener;

	public ListViewScrollerLoader(ListView listView, TextView moreTV, LoadListener loaderListener) {
		this.listView = listView;
		this.moreTV = moreTV;
		this.loaderListener = loaderListener;
		init();
	}

	static final int BEGING = 0;
	static final int END = 1;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == BEGING) {
				moreTV.setText(msg.obj.toString());
			}
			if (msg.what == END) {
				moreTV.setVisibility(View.GONE);
				((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
				isLoading = false;
			}
		}
	};

	boolean loadFinish = false;
	boolean isLoading = false;
	public boolean needLoad = true;

	void init() {
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
					int totalItemCount) {
				if (firstVisibleItem + visibleItemCount < listView.getAdapter().getCount()
						|| !needLoad) {
					loadFinish = true;
					isLoading = false;
					return;
				}
				if (isLoading) {
					return;
				}
				loadFinish = false;
				isLoading = true;
				moreTV.setVisibility(View.VISIBLE);
				new Thread() {
					public void run() {
						int i = 0;
						while (!loadFinish) {
							Message msg = Message.obtain();
							msg.what = BEGING;
							i++;
							if (i % 3 == 0) {
								msg.obj = "正在加载...";
							}
							if (i % 3 == 1) {
								msg.obj = "正在加载.";
							}
							if (i % 3 == 2) {
								msg.obj = "正在加载..";
							}
							handler.sendMessage(msg);
							try {
								Thread.sleep(500);
							} catch (Exception e) {
							}
						}
						handler.sendEmptyMessage(END);
					}
				}.start();
				boolean loadSyn = loaderListener.loadSyn();
				if (!loadSyn) {
					loadFinish = true;
				}
			}
		});
	}

	public void loadFinish() {
		loadFinish = true;
		handler.sendEmptyMessage(END);
	}

	public interface LoadListener {
		/**
		 * 在主线程中加载数据返回false，在工作线程中加载数据返回true
		 * @return
		 */
		public boolean loadSyn();
	}
}
