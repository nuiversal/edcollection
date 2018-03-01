package com.winway.android.util;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 验证码生成器
 * 
 * @author zgq
 * 
 */
public class ValidateCode {
	private final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
			'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'J', 'K', 'L', 'M', 'N', 'P', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private final int DEFAULT_CODE_LENGTH = 4;
	private final int DEFAULT_FONT_SIZE = 20;
	private final int DEFAULT_LINE_NUMBER = 0;
	private final int BASE_PADDING_LEFT = 5;
	private final int RANGE_PADDING_LEFT = 5;
	private final int BASE_PADDING_TOP = 15;
	private final int RANGE_PADDING_TOP = 5;
	private final int DEFAULT_WIDTH = 70;
	private final int DEFAULT_HEIGHT = 30;

	private String code;
	private int padding_left, padding_top;
	private Random random = new Random();

	public ValidateCode() {
	}

	/**
	 * 创建位图
	 * 
	 * @return
	 */
	public Bitmap createBitmap() {
		padding_left = 0;
		padding_top = 0;
		Bitmap bp = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Config.ARGB_8888);
		Canvas c = new Canvas(bp);
		code = createCode();
		c.drawColor(getRandColor(200, 250));
		Paint paint = new Paint();
		paint.setTextSize(DEFAULT_FONT_SIZE);
		for (int i = 0; i < code.length(); i++) {
			randomTextStyle(paint);
			randomPadding();
			c.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
		}
		for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
			drawLine(c, paint);
		}
		c.save(Canvas.ALL_SAVE_FLAG);
		c.restore();
		return bp;
	}

	/**
	 * 随机验证码
	 * 
	 * @return
	 */
	private String createCode() {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < DEFAULT_CODE_LENGTH; i++) {
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}

	/**
	 * 获取验证码
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 画干扰线
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawLine(Canvas canvas, Paint paint) {
		int color = randomColor();
		int startX = random.nextInt(DEFAULT_WIDTH);
		int startY = random.nextInt(DEFAULT_HEIGHT);
		int stopX = random.nextInt(DEFAULT_WIDTH);
		int stopY = random.nextInt(DEFAULT_HEIGHT);
		paint.setStrokeWidth(0.5f);
		paint.setColor(color);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}

	/**
	 * 生成在指定范围内的颜色
	 * 
	 * @param fc
	 *            范围fc color值 小于255
	 * @param bc
	 *            范围bc color值 小于255
	 * @return Color
	 */
	private int getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc < 0)
			fc = 0;
		if (bc < 0)
			bc = 1;
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		if (bc == fc)
			bc += 10;
		int temp = 0;
		if (bc < fc) {
			temp = bc;
			bc = fc;
			fc = temp;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return Color.rgb(r, g, b);
	}

	/**
	 * 随机颜色
	 * 
	 * @return
	 */
	private int randomColor() {
		return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	}

	/**
	 * 随机字体样式
	 * 
	 * @param paint
	 */
	private void randomTextStyle(Paint paint) {
		paint.setColor(randomColor());
		paint.setFakeBoldText(true);
	}

	/**
	 * 随机字体位置
	 */
	private void randomPadding() {
		padding_left += BASE_PADDING_LEFT + 5 + random.nextInt(RANGE_PADDING_LEFT);
		padding_top = BASE_PADDING_TOP + 5 + random.nextInt(RANGE_PADDING_TOP);
	}
}
