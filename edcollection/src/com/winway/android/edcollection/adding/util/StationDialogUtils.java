package com.winway.android.edcollection.adding.util;

import com.winway.android.edcollection.base.entity.lineStringGeometry;
import com.winway.android.util.DialogUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public class StationDialogUtils extends DialogUtil{
	
	private OnItemDialogListener listener;
	
	private Activity activity;

	public StationDialogUtils(Activity activity) {
		super(activity);
		this.activity = activity;
	}
	
	public void setOnItemDialogListenter(OnItemDialogListener listener) {
		this.listener = listener;
	}
	
	public void showAlertDialog(String title, String[] items) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		
		builder.setTitle(title);
		builder.setItems(items, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				listener.onItem(which);
			}
		} );
		builder.setNegativeButton("取消", null);
		builder.create().show();;
	}
	
	
	
	public interface OnItemDialogListener {
		void onItem(int item);
	}
}
