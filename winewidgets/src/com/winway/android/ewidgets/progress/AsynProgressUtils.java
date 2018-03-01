package com.winway.android.ewidgets.progress;

import java.util.HashMap;

import com.winway.android.ewidgets.R;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 异步自定义进度条（此类可以在子线程中调用）
 * 作用：在子线程中，可以随意调用show()、dismiss()、cancel()方法进行进度条对话框的显示、消失与取消操三种对话框UI操作，而
 * 不会报非主线程程不能操作UI异常。 注意：第一次调用AsynProgressUtils.getInstance()获取单例的时候，必须在主线程中调用，
 * 否则会出现RuntimeException异常。 只有在单例被实例化之后，才能在子线程中调用。
 * 
 * @author lhw
 * 
 */
@SuppressLint("HandlerLeak")
public class AsynProgressUtils {

	private static ProgressDialog dialog;// 进度对话框
	private static AsynProgressUtils instance;
	private String LOCK = "LOCK";

	static boolean isInMainThread() {
		return Looper.myLooper() == Looper.getMainLooper();
	}

	private AsynProgressUtils() {
	}

	public static AsynProgressUtils getInstance() {
		if (instance == null) {
			synchronized (AsynProgressUtils.class) {
				if (instance == null) {
					if (!isInMainThread()) {
						// 如果非主线程中不能生成实例
						throw new RuntimeException(
								"instance单例实例化之前，不能在非主线程中调用AsynProgressUtils.getInstance()，必须等到instance单例实例化之后才能在子线程中才能调用AsynProgressUtils.getInstance()");
					}
					instance = new AsynProgressUtils();
				}
			}
		}
		return instance;
	}

	static final int WHAT_SHOW_PROGRESS_BAR = 1000;
	static final int WHAT_DISMISS_PROGRESS_BAR = 1001;
	static final int WHAT_CANCEL_PROGRESS_BAR = 1002;
	static final int WHAT_SET_PROGRESS_BAR_MAX = 1003;
	static final int WHAT_SET_PROGRESS_BAR_PROGRESS = 1004;

	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			if (msg.what == WHAT_SHOW_PROGRESS_BAR) {
				// 显示
				HashMap<String, Object> map = (HashMap<String, Object>) msg.obj;
				mainshow((String) map.get("txt"), (Boolean) map.get("cancelabled"), (Context) map.get("context"),
						(Integer) map.get("style"));
			} else if (msg.what == WHAT_DISMISS_PROGRESS_BAR) {
				// 隐藏
				mainDismiss();
			} else if (msg.what == WHAT_CANCEL_PROGRESS_BAR) {
				// 取消
				maincancel();
			} else if (msg.what == WHAT_SET_PROGRESS_BAR_MAX) {
				// 设置最大值
				int max = (int) msg.obj;
				mainsetMax(max);
			} else if (msg.what == WHAT_SET_PROGRESS_BAR_PROGRESS) {
				// 设置进度
				int progress = (int) msg.obj;
				mainsetProgress(progress);
			}
		}
	};

	/**
	 * 出现的是带文字的圆形转圈进度条
	 * 
	 * @param txt
	 * @param context
	 */
	public void show(String txt, Context context) {
		show(txt, false, context);
	}

	/**
	 * 出现的是带文字的圆形转圈进度条
	 * 
	 * @param txt
	 * @param context
	 * @param cancelabled
	 */
	public void show(String txt, boolean cancelabled, Context context) {
		show(txt, cancelabled, context, ProgressDialog.STYLE_SPINNER);
	}

	/**
	 * 根据style的值显示条形或者圆形的进度框
	 * 
	 * @param txt
	 * @param cancelabled
	 * @param context
	 * @param style
	 *            值为ProgressDialog.STYLE_SPINNER 或
	 *            ProgressDialog.STYLE_HORIZONTAL
	 */
	public void show(String txt, boolean cancelabled, Context context, int style) {
		if (isInMainThread()) {
			mainshow(txt, cancelabled, context, style);
		} else {
			HashMap<String, Object> map = new HashMap<>();
			map.put("txt", txt);
			map.put("cancelabled", cancelabled);
			map.put("context", context);
			map.put("style", style);
			Message msg = Message.obtain();
			msg.what = WHAT_SHOW_PROGRESS_BAR;
			msg.obj = map;
			handler.sendMessage(msg);
		}
	}

	static enum DialogState {
		show, cancle, dismiss
	}

	private DialogState dialogstate;

	/**
	 * 显示等候框
	 * 
	 * @param txt
	 *            提示文本
	 * @param cancelabled
	 *            是否可取消
	 * @param context
	 */

	public void mainshow(String txt, boolean cancelabled, Context context, int style) {
		if (DialogState.show.equals(dialogstate)) {
			return;
		}
		dialogstate = DialogState.show;
		cancel();
		dismiss();
		View view = null;
		if (style == ProgressDialog.STYLE_HORIZONTAL) {
			dialog = new ProgressDialog(context);
			// 进度条
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setTitle(txt);

		} else {
			dialog = new ProgressDialog(context, R.style.progress_dialog);
			// 圆形进度条
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.progress_dialog, null);
			ImageView img = (ImageView) view.findViewById(R.id.progress_dialog_img);
			TextView text = (TextView) view.findViewById(R.id.progress_dialog_txt);
			text.setText(txt);
			// 添加动画
			Animation anim = AnimationUtils.loadAnimation(context, R.anim.loading_dialog_progressbar);
			img.setAnimation(anim);
		}
		dialog.setCancelable(cancelabled);
		dialog.show();
		if (view != null) {
			dialog.setContentView(view);// 设置布局
		}
		synchronized (LOCK) {
			LOCK.notifyAll();
		}
	}

	public void cancel() {
		if (isInMainThread()) {
			maincancel();
		} else {
			handler.sendEmptyMessage(WHAT_CANCEL_PROGRESS_BAR);
		}
	}

	/**
	 * 取消
	 */
	public void maincancel() {
		dialogstate = DialogState.cancle;
		if (dialog != null && dialog.isShowing()) {
			dialog.cancel();
		}
		dialog = null;
	}

	public void dismiss() {
		if (isInMainThread()) {
			mainDismiss();
		} else {
			handler.sendEmptyMessage(WHAT_DISMISS_PROGRESS_BAR);
		}
	}

	/**
	 * 销毁等候框
	 */
	public void mainDismiss() {
		dialogstate = DialogState.dismiss;
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		dialog = null;
	}

	public interface onProgressDialogCancelListener {
		public void onCancle();
	}

	private void mainsetMax(int max) {
		if (null == dialog) {
			synchronized (LOCK) {
				try {
					LOCK.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setMax(max);
	}

	public void setMax(int max) {
		if (isInMainThread()) {
			mainsetMax(max);
		} else {
			Message msg = Message.obtain();
			msg.what = WHAT_SET_PROGRESS_BAR_MAX;
			msg.obj = max;
			handler.sendMessage(msg);
		}
	}

	private void mainsetProgress(int progress) {
		if (null == dialog) {
			synchronized (LOCK) {
				try {
					LOCK.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		dialog.setProgress(progress);
	}

	public void setProgress(int progress) {
		if (isInMainThread()) {
			mainsetProgress(progress);
		} else {
			Message msg = Message.obtain();
			msg.what = WHAT_SET_PROGRESS_BAR_PROGRESS;
			msg.obj = progress;
			handler.sendMessage(msg);
		}
	}
}
