package com.winway.android.edcollection.adding.customview;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by mr-lao on 2015/9/15.
 */
public class CompassView extends ImageView implements SensorEventListener {
	private ImageView imageView;
	private float currentDegree = 0f;
	private String direction = "";
	private int change = 0;
	private OnDegreesChangeListener listener = null;

	public void setOnDreesChangeListener(OnDegreesChangeListener listener) {
		this.listener = listener;
	}

	public CompassView(Context context) {
		super(context);
		onCreate();
	}

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		onCreate();
	}

	public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		onCreate();
	}

	public float getCurrentDegree() {
		return -currentDegree;
	}

	public String getDirection() {
		return direction;
	}

	public void onCreate() {
		imageView = this;
		// 传感器管理器
		SensorManager sm = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
		// 注册传感器(Sensor.TYPE_ORIENTATION(方向传感器);SENSOR_DELAY_FASTEST(0毫秒延迟);
		// SENSOR_DELAY_GAME(20,000毫秒延迟)、SENSOR_DELAY_UI(60,000毫秒延迟))
		sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_FASTEST);
	}

	// 传感器报告新的值(方向改变)
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			float degree = event.values[0];
			/*
			 * RotateAnimation类：旋转变化动画类
			 * 
			 * 参数说明:
			 * 
			 * fromDegrees：旋转的开始角度。 toDegrees：旋转的结束角度。
			 * pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、
			 * RELATIVE_TO_PARENT。 pivotXValue：X坐标的伸缩值。
			 * pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、
			 * RELATIVE_TO_PARENT。 pivotYValue：Y坐标的伸缩值
			 */
			RotateAnimation ra = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			// 旋转过程持续时间
			ra.setDuration(50);
			// 罗盘图片使用旋转动画
			imageView.startAnimation(ra);
			currentDegree = -degree;
			direction = getDirection(degree);
			change++;
			if (change > 50) {
				change = 0;
				if (null != listener) {
					listener.degreesChange(-currentDegree, direction);
				}
			}
		}
	}

	// 传感器精度的改变
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public interface OnDegreesChangeListener {
		void degreesChange(float degrees, String direction);
	}

	/**
	 * 获取方向
	 * 
	 * @param degrees
	 * @return
	 */
	public static String getDirection(float degrees) {
		String result = "";
		if ((degrees >= 0 && degrees <= 5) || (degrees >= 355)) {
			result = "正北";
		} else if (degrees > 5 && degrees < 85) {
			result = "东北";
		} else if (degrees >= 85 && degrees <= 95) {
			result = "正东";
		} else if (degrees > 95 && degrees < 175) {
			result = "东南";
		} else if ((degrees >= 175 && degrees <= 185)) {
			result = "正南";
		} else if (degrees > 185 && degrees < 265) {
			result = "西南";
		} else if (degrees >= 265 && degrees <= 275) {
			result = "正西";
		} else if (degrees > 275 && degrees < 355) {
			result = "西北";
		}
		return result;
	}
}
