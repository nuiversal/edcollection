package com.winway.android.ewidgets.dialog;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.ewidgets.R;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.ListUtil;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DialogView {
	private DialogUtil dialogUtil;
	private Activity activity;
	private DialogContentViewHolder dialogContentViewHolder;
	private DialogContentMessage dialogContentMessage;

	class DialogContentViewHolder {
		public View contentView;
		public TextView titleTV;
		public TextView messageTV;
		public ListView dataLV;
	}

	class DialogContentMessage {
		public String title;
		public String message;
		public List<String> datas;
		public OnItemClickListener oicl;
	}

	public DialogView(Activity activity) {
		this.activity = activity;
		dialogUtil = new DialogUtil(activity);
	}

	public DialogView(Activity activity, String title, String message, String[] datas,
			OnItemClickListener oicl) {
		this.activity = activity;
		dialogUtil = new DialogUtil(activity);
		initDialogContentMessage(title, message, datas, oicl);
	}

	public DialogView(Activity activity, String title, String message, List<String> datas,
			OnItemClickListener oicl) {
		this.activity = activity;
		dialogUtil = new DialogUtil(activity);
		initDialogContentMessage(title, message, datas, oicl);
	}

	public void showDialog(String title, String message, String[] datas, OnItemClickListener oicl) {
		initDialogContentMessage(title, message, datas, oicl);
		showDialog();
	}

	public void showDialog(String title, String message, List<String> datas,
			OnItemClickListener oicl) {
		initDialogContentMessage(title, message, datas, oicl);
		showDialog();
	}

	public void initDialogContentMessage(String title, String message, String[] datas,
			OnItemClickListener oicl) {
		ArrayList<String> listData = new ArrayList<>();
		ListUtil.copyList(listData, datas);
		initDialogContentMessage(title, message, listData, oicl);
	}

	public void initDialogContentMessage(String title, String message, List<String> datas,
			OnItemClickListener oicl) {
		dialogContentMessage = new DialogContentMessage();
		dialogContentMessage.title = title;
		dialogContentMessage.message = message;
		dialogContentMessage.oicl = oicl;
		dialogContentMessage.datas = datas;
	}

	public void showDialog() {
		if (null == dialogContentViewHolder) {
			dialogContentViewHolder = new DialogContentViewHolder();
			View view = View.inflate(activity, R.layout.dialog_content_view, null);
			dialogContentViewHolder.titleTV = (TextView) view
					.findViewById(R.id.dialog_title_textview);
			dialogContentViewHolder.messageTV = (TextView) view
					.findViewById(R.id.dialog_message_textview);
			dialogContentViewHolder.dataLV = (ListView) view
					.findViewById(R.id.dialog_datas_listview);
			dialogContentViewHolder.contentView = view;
		}
		if (null != dialogContentMessage) {
			if (null != dialogContentMessage.title) {
				dialogContentViewHolder.titleTV.setVisibility(View.VISIBLE);
				dialogContentViewHolder.titleTV.setText(dialogContentMessage.title);
			} else {
				dialogContentViewHolder.titleTV.setVisibility(View.GONE);
			}
			if (null != dialogContentMessage.message) {
				dialogContentViewHolder.messageTV.setVisibility(View.VISIBLE);
				dialogContentViewHolder.messageTV.setText(dialogContentMessage.message);
			} else {
				dialogContentViewHolder.messageTV.setVisibility(View.GONE);
			}
			if (null != dialogContentMessage.datas && !dialogContentMessage.datas.isEmpty()) {
				dialogContentViewHolder.dataLV.setVisibility(View.VISIBLE);
				dialogContentViewHolder.dataLV.setAdapter(new MAdapter());
				dialogContentViewHolder.dataLV.setOnItemClickListener(dialogContentMessage.oicl);
			} else {
				dialogContentViewHolder.dataLV.setVisibility(View.GONE);
			}
		}
		dialogUtil.showAlertDialog(dialogContentViewHolder.contentView, false);
	}

	public void dismisDialog() {
		dialogUtil.dismissDialog();
	}

	public void destroyDialog() {
		dialogUtil.dismissDialog();
		dialogUtil.destroy();
	}

	class MAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return dialogContentMessage.datas.size();
		}

		@Override
		public Object getItem(int position) {
			return dialogContentMessage.datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (null == convertView) {
				holder = new ViewHolder();
				convertView = View.inflate(activity, R.layout.dialog_listview_item, null);
				holder.dataTV = (TextView) convertView.findViewById(R.id.list_item_data_tv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.dataTV.setText(dialogContentMessage.datas.get(position));
			return convertView;
		}
	}

	class ViewHolder {
		public TextView dataTV;
	}
}
