package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.JMsWindowAdapter;
import com.winway.android.edcollection.adding.entity.channelplaning.JMEntity;
import com.winway.android.util.DialogUtil;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 历史截面展示工具
 * 
 * @author xs
 *
 */
public class JMsWindowDisplayUtil {
	private static JMsWindowDisplayUtil instance;

	private JMsWindowDisplayUtil() {
	}

	public static JMsWindowDisplayUtil getInstance() {
		synchronized (JMsWindowDisplayUtil.class) {
			if (instance == null) {
				instance = new JMsWindowDisplayUtil();
			}
		}
		return instance;
	}

	public void showJMsWindow(Activity mActivity, final List<JMEntity> jmEntities) {
		final DialogUtil dialog = new DialogUtil(mActivity);
		View miniJMsView = View.inflate(mActivity, R.layout.layout_window_show_jms, null);
		miniJMsView.findViewById(R.id.iv_jms_dialog_close).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismissDialog();
			}
		});
		GridView gvMiniJMs = (GridView) miniJMsView.findViewById(R.id.gv_jms_dialog);
		JMsWindowAdapter adapter = new JMsWindowAdapter(mActivity, (ArrayList<JMEntity>) jmEntities, miniJMsView,
				dialog);
		gvMiniJMs.setAdapter(adapter);
		dialog.showAlertDialog(miniJMsView);
	}

	public void recycle() {
		if (instance != null) {
			instance = null;
		}
	}
}
