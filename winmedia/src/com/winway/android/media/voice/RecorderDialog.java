package com.winway.android.media.voice;

import java.io.File;

import com.example.lamemp3.MP3Recorder;
import com.example.lamemp3.RecorderAndPlayUtil;
import com.winway.android.media.R;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.ToastUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 录音对话框
 * @author mr-lao
 *
 */
public class RecorderDialog {
	private DialogUtil dialogUtil;
	private View view;
	Activity context;
	// 录音文件保存路径
	private String outpath;

	public RecorderDialog(Activity activity, String outpath) {
		this.context = activity;
		dialogUtil = new DialogUtil(activity);
		this.outpath = outpath;
		initView();
	}

	private LinearLayout mRecorderLayout = null;
	private TextView mShowTime = null;
	private boolean mIsRecording = false;// 是否正在录音
	private boolean mIsLittleTime = false;
	private boolean mIsSendVoice = false;
	private RecorderAndPlayUtil mRecorder = null;

	private boolean canRecorde = true;

	public void setOutPath(String outpath) {
		if (mIsRecording) {
			return;
		}
		this.outpath = outpath;
		mRecorder = new RecorderAndPlayUtil(outpath);
	}

	@SuppressLint("HandlerLeak")
	private Handler recorderHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MP3Recorder.MSG_REC_STARTED:
				// 开始录音
				break;
			case MP3Recorder.MSG_REC_STOPPED:
				// 停止录音
				if (mIsSendVoice) {// 是否发送录音
					mIsSendVoice = false;
					if (mIsLittleTime) {
						File file = new File(mRecorder.getRecorderPath());
						if (mRecorder.getRecorderPath() != null) {
							file.delete();
						}
						Toast.makeText(context, "录音时间太短", Toast.LENGTH_SHORT).show();
						stopRecording("录音时间太短");
						return;
					}
				}
				// stopRecording(null);
				break;
			case MP3Recorder.MSG_ERROR_GET_MIN_BUFFERSIZE:
				canRecorde = false;
				stopRecording("采样率手机不支持");
				Toast.makeText(context, "采样率手机不支持", Toast.LENGTH_SHORT).show();
				break;
			case MP3Recorder.MSG_ERROR_CREATE_FILE:
				stopRecording("创建音频文件出错");
				Toast.makeText(context, "创建音频文件出错", Toast.LENGTH_SHORT).show();
				break;
			case MP3Recorder.MSG_ERROR_REC_START:
				canRecorde = false;
				stopRecording("初始化录音器出错");
				Toast.makeText(context, "初始化录音器出错", Toast.LENGTH_SHORT).show();
				break;
			case MP3Recorder.MSG_ERROR_AUDIO_RECORD:
				stopRecording("录音的时候出错");
				Toast.makeText(context, "录音的时候出错", Toast.LENGTH_SHORT).show();
				break;
			case MP3Recorder.MSG_ERROR_AUDIO_ENCODE:
				stopRecording("编码出错");
				Toast.makeText(context, "编码出错", Toast.LENGTH_SHORT).show();
				break;
			case MP3Recorder.MSG_ERROR_WRITE_FILE:
				stopRecording("文件写入出错");
				Toast.makeText(context, "文件写入出错", Toast.LENGTH_SHORT).show();
				break;
			case MP3Recorder.MSG_ERROR_CLOSE_FILE:
				stopRecording("文件流关闭出错");
				Toast.makeText(context, "文件流关闭出错", Toast.LENGTH_SHORT).show();
				break;
			case 888888:
				// 改变时间值
				mShowTime.setText("" + recordedTime);
				if (recordedTime >= maxRecordingTime) {
					stopRecording("最大录音时间为10分种，录音停止");
				}
				break;
			}
		}
	};

	/**最大录音时间为10分钟*/
	int maxRecordingTime = 60 * 10;
	int recordedTime = 0;

	private void startRecording() {
		if (!canRecorde) {
			ToastUtils.show(context, "内存空间不足，或者是程序没有录音权限，请检查！");
			return;
		}
		if (mIsRecording) {
			return;
		}
		mRecorder.startRecording();
		mRecorderLayout.setVisibility(View.VISIBLE);
		mIsRecording = true;
		new Thread() {
			public void run() {
				while (recordedTime <= maxRecordingTime && mIsRecording) {
					recordedTime++;
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					recorderHandler.sendEmptyMessage(888888);
				}
				recordedTime = 0;
				recorderHandler.sendEmptyMessage(888888);
			};
		}.start();
	}

	@SuppressLint({ "HandlerLeak", "ClickableViewAccessibility" })
	private void initView() {
		view = View.inflate(context, R.layout.layout_voice_recorde, null);
		mRecorderLayout = (LinearLayout) view.findViewById(R.id.recorder_layout);
		mShowTime = (TextView) view.findViewById(R.id.show_time_tv);
		mShowTime.setText("0\"");
		mRecorder = new RecorderAndPlayUtil(outpath);
		mRecorder.getRecorder().setHandle(recorderHandler);
		final Button recorder = (Button) view.findViewById(R.id.recorder_bt);
		recorder.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					startRecording();
					recorder.setBackgroundColor(Color.parseColor("#0045D8"));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					stopRecording(null);
					dismiss();
					recorder.setBackgroundColor(Color.parseColor("#007EE5"));
				}
				return true;
			}
		});
	}

	private void stopRecording(String error) {
		mRecorder.stopRecording();
		mShowTime.setText("0\"");
		mIsRecording = false;
		File file = new File(outpath);
		if (!canRecorde) {
			if (file.exists()) {
				file.delete();
			}
			if (null != recordeResult) {
				recordeResult.onError("没有权限或者是内存空间不足");
			}
			return;
		}
		if (null != error) {
			if (file.exists()) {
				file.delete();
			}
			if (null != recordeResult) {
				recordeResult.onError(error);
			}
		} else {
			if (null != recordeResult && file.exists()) {
				recordeResult.onFinish(outpath);
			}
		}
	}

	public void show() {
		dialogUtil.showAlertDialog(view, false);
	}

	public void dismiss() {
		dialogUtil.dismissDialog();
	}

	private OnRecordeResultListener recordeResult;

	public void setRecordeResult(OnRecordeResultListener recordeResult) {
		this.recordeResult = recordeResult;
	}

	/**
	 * 录音结果监听器
	 * @author mr-lao
	 *
	 */
	public interface OnRecordeResultListener {
		public void onError(String error);

		public void onFinish(String filepath);
	}
}
