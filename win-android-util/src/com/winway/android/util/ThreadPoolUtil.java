package com.winway.android.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 线程工具类
 * @author mr-lao
 *
 */
public class ThreadPoolUtil {
	// 线程初始数据
	final static int nThreads = 10;
	final static ExecutorService threadPool;

	static {
		threadPool = Executors.newFixedThreadPool(nThreads);
	}

	/**
	 * 执行
	 * @param run
	 */
	public static void excute(Runnable run) {
		threadPool.execute(run);
	}

	private static Handler handler;

	/**
	 * 初始化线程池工具类
	 * @note 需要使用到异步、或者在主线程中进行方法回调的功能的时候，必须先执行初始化方法，以防万一
	 */
	public static void init() {
		if (null == handler) {
			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (null != msg.obj) {
						if (msg.obj instanceof EndListener) {
							((EndListener) msg.obj).threadEnd();
						} else if (msg.obj instanceof MainRunableWrapter) {
							MainRunableWrapter wrapter = (MainRunableWrapter) msg.obj;
							wrapter.run.run(wrapter.params);
						} else if (msg.obj instanceof AsynCirculation) {
							((AsynCirculation) msg.obj).runMainThread();
						}
					}
				}
			};
		}
	}

	static void sendExcuteAsynMessage(Object taskObj) {
		Message msg = Message.obtain();
		msg.obj = taskObj;
		handler.sendMessage(msg);
	}

	/**
	 * 异步执行线程，并在主线程中进行回调
	 * @param runnable
	 * @param endListener
	 */
	public static void excuteAsyn(final Runnable runnable, final EndListener endListener) {
		init();
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				runnable.run();
				if (null != endListener) {
					sendExcuteAsynMessage(endListener);
				}
			}
		});
	}

	public interface EndListener {
		public void threadEnd();
	}

	/**
	 * 在主线程中执行任务
	 * @param runnable
	 * @param params
	 */
	public static void excuteInMainThread(MainRunnable runnable, Object... params) {
		if (Looper.myLooper() == Looper.getMainLooper()) {
			// 主线程
			runnable.run(params);
		} else {
			// 工作线程
			sendExcuteAsynMessage(new MainRunableWrapter(runnable, params));
		}
	}

	static class MainRunableWrapter {
		public MainRunnable run;
		public Object[] params;

		public MainRunableWrapter(MainRunnable run, Object[] params) {
			super();
			this.run = run;
			this.params = params;
		}
	}

	public interface MainRunnable {
		public void run(Object[] params);
	}

	/**
	 * 循环任务：在工作线程中执行完之非UI任务之后，等待特定时间，然后在主线程中执行UI任务。这方法通常用于动态更改UI场景中，比如，第隔1秒动态更改背景图片。
	 * @param asynCirculation
	 * @param runSpaceTime    循环等待的时间，如果runSpaceTime <= 0，则等待无效
	 */
	public static void excuteAsynCirculation(final AsynCirculation asynCirculation, final long runSpaceTime) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				while (!asynCirculation.needStop()) {
					asynCirculation.runWorkThread();
					if (runSpaceTime > 0) {
						try {
							Thread.sleep(runSpaceTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					sendExcuteAsynMessage(asynCirculation);
				}
			}
		});
	}

	/**
	 * 循环任务
	 * @author mr-lao
	 *
	 */
	public interface AsynCirculation {
		/**
		 * 用于控制循环停止，返回值为true，循环停止，返回值为false，循环继续
		 * @return
		 */
		public boolean needStop();

		/**
		 * 运行在工作线程中的方法，可以在此方法中调用耗时操作的代码，不能在此方法中进行UI操作
		 */
		public void runWorkThread();

		/**
		 * 运行在主线程中的方法，可以在此方法中进行UI操作
		 */
		public void runMainThread();
	}
}
