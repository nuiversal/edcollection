package com.winway.android.edcollection.adding.util;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * ���϶��ؼ��Ĺ�����
 *
 */
public class MoveViewUtils {
	/**
	 * ���ÿؼ�������Ļ�������϶�
	 * 
	 * @param view
	 *            Ҫ�ƶ��Ŀؼ�
	 * @param sp  
	 *            sharedPreference
	 * @param screenHeight
	 *            ��Ļ�ĸ߶�
	 * @param screenWidth
	 *            //��Ļ�Ŀ��
	 */
	protected static int screenWidth;
	protected static int screenHeight;
	protected static SharedPreferences sp;
	
	public static void setMoveView(Context context, final View view) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
	    screenHeight = wm.getDefaultDisplay().getHeight();
		sp = context.getSharedPreferences("config",  Context.MODE_PRIVATE);
		int lastx = sp.getInt("lastx", 0);
		int lasty = sp.getInt("lasty", 0);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view
				.getLayoutParams();
		params.leftMargin = lastx;
		params.topMargin = lasty;
		view.setLayoutParams(params);

		view.setOnTouchListener(new OnTouchListener() {
			int startX;
			int startY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// ��ָ��һ�δ�������Ļ
					this.startX = (int) event.getRawX();
					this.startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:// ��ָ�ƶ�
					int newX = (int) event.getRawX();
					int newY = (int) event.getRawY();

					int dx = newX - this.startX;
					int dy = newY - this.startY;

					// ��������ؼ�ԭ����λ��
					int l = view.getLeft();
					int r = view.getRight();
					int t = view.getTop();
					int b = view.getBottom();

					int newt = t + dy;
					int newb = b + dy;
					int newl = l + dx;
					int newr = r + dx;

					if ((newl < 0) || (newt < 0) || (newr > screenWidth)
							|| (newb > screenHeight)) {
						break;
					}

					// ���¿ؼ�����Ļ��λ��.
					view.layout(newl, newt, newr, newb);
					this.startX = (int) event.getRawX();
					this.startY = (int) event.getRawY();

					break;
				case MotionEvent.ACTION_UP: // ��ָ�뿪��Ļ��һ˲��
					int lastx = view.getLeft();
					int lasty = view.getTop();
					Editor editor = sp.edit();
					editor.putInt("lastx", lastx);
					editor.putInt("lasty", lasty);
					editor.commit();
					break;
				}
				return true;
			}
		});
	}
}
