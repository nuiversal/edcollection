package com.winway.android.sensor.nfc;

import com.winway.android.sensor.R;
import com.winway.android.util.ThreadPoolUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class NFCReaderDialogActivity extends NFCActivity {
	public static final String RESULT_DATA_KEY = "RESULT_DATA_KEY";
	private ImageView imageFNCState;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setWindowBackground();
		setContentView(R.layout.dialog_nfc_reader);
		this.setOnNFCListener(onNFCListener);
		imageFNCState = (ImageView) findViewById(R.id.image_nfc_state);
		this.setFinishOnTouchOutside(false);
	}

	void setWindowBackground() {
		this.getWindow().setBackgroundDrawableResource(R.drawable.nfc_dialog_bg);
	}

	boolean stop = false;

	private ThreadPoolUtil.AsynCirculation asynCirculation = new ThreadPoolUtil.AsynCirculation() {
		int index = 0;

		@Override
		public boolean needStop() {
			return stop;
		}

		@Override
		public void runWorkThread() {
			index++;
		}

		@Override
		public void runMainThread() {
			if (index % 2 == 0) {
				imageFNCState.setImageResource(R.drawable.reading_t);
			} else {
				imageFNCState.setImageResource(R.drawable.reading_o);
			}
		}

	};

	@Override
	protected void onResume() {
		super.onResume();
		if (isNFCEnable) {
			if (!stop) {
				ThreadPoolUtil.excuteAsynCirculation(asynCirculation, 500);
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stop = true;
	}

	private OnNFCListener onNFCListener = new OnNFCListener() {
		@Override
		public void setReversed(long rev) {

		}

		@Override
		public void setId(byte[] id) {

		}

		@Override
		public void setHex(String hex) {
			Intent data = new Intent();
			data.putExtra(RESULT_DATA_KEY, hex);
			setResult(Activity.RESULT_OK, data);
			finish();
		}

		@Override
		public void setDec(long dec) {

		}
	};
}
