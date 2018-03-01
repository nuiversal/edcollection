package com.winway.android.edcollection.adding.customview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 工井采集画板
 * 
 * @author mr-lao
 * 
 */

@SuppressLint("WrongCall")
public class GJDrawView extends View {
	private int view_width = 1400;
	private int view_height = 900;
	// 小圆
	public static float POINT_R_NORMAL = 15;
	// 中圆
	public static float POINT_R_MEDIUM = 20;
	// 大圆
	public static float POINT_R_LARGE = 25;
	// 支架线的长度
	private float LINE_LENGTH = 150;//
	private float defaultCircleR = POINT_R_MEDIUM;
	// 圆点（管线）
	private List<Point> pos = new ArrayList<Point>();
	/** 风格辅助是未知线路是画圆点 */
	private List<Point> unKnowLinePos = new ArrayList<Point>();
	/** 支架 */
	private List<Point> linesLocation = new ArrayList<Point>();
	public static boolean isTouch = false;

	public List<Point> getLinesLocation() {
		return linesLocation;
	}

	public void setLinesLocation(List<Point> linesLocation) {
		this.linesLocation = linesLocation;
		invalidate();
	}

	public void setLinesLocation(int lr) {
		setLinesLocation(lr, lr);
	}

	/**
	 * 支架
	 * 
	 * @param left
	 * @param right
	 */
	public void setLinesLocation(int left, int right) {
		if (null == linesLocation) {
			linesLocation = new ArrayList<Point>();
		}
		linesLocation.clear();
		float leftSpace = view_height / (float) (left + 1);
		float rightSpace = view_height / (float) (right + 1);
		for (int i = 1; i <= left; i++) {
			Point p = new Point(0, leftSpace * i);
			linesLocation.add(p);
		}
		for (int i = 1; i <= right; i++) {
			Point p = new Point(view_width - LINE_LENGTH, rightSpace * i);
			linesLocation.add(p);
		}
		invalidate();
	}
	
	// 增加一个x行，y列的圆点数据
	public void addZjPoints(int x, int y) {
		float width_a = 24f;
		float height_a = 58f;
		float lineBorder = 10f;
		//如果支架列表不为空
		if (null != linesLocation && !linesLocation.isEmpty()) {
			for (Point line : linesLocation) {
				//line.x - x轴坐标为0，说明是左边的支架
				if (line.x == 0) {
					// x和y的偏移量
					float mx = line.x+defaultCircleR+width_a;
					float my = line.y-height_a;
					// 创建圆组
					addBracketLeftPoint(mx, my);
				}else { //右边的支架
					// x和y的偏移量
					float mx = line.x+LINE_LENGTH-width_a+2-defaultCircleR;
					float my = line.y-height_a;
					// 创建圆组
					addBracketRightPoint(mx, my);
				}
			}
			//画板左边最下方增加三个圆
			float downLeftX = getLeft()+defaultCircleR+lineBorder;
			float downLeftY = getBottom() - defaultCircleR - lineBorder;
			addLeftBottonPoine(downLeftX, downLeftY);
			//画板右边最下方增加三个圆
			float downRightX = getBottom()-defaultCircleR-lineBorder;
			float downRightY = getBottom()-defaultCircleR-lineBorder;
			addRightBottomPoint(downRightX, downRightY);
		}
		invalidate();
	}

	/**
	 * 添加支架上左边的圆
	 * @param mx
	 * @param my
	 */
	private void addBracketLeftPoint(float mx, float my) {
		for (int j = 0; j < 1; j++) {
			for (int i = 0; i < 4; i++) {
				Point p = new Point(mx + i * defaultCircleR * 2 + i, my + j * defaultCircleR * 2 + j,
						defaultCircleR);
				pos.add(p);
			}
		}
	}
	/**
	 * 添加支架上右边的圆
	 * @param mx
	 * @param my
	 */
	private void addBracketRightPoint(float mx, float my) {
		for (int j = 0; j < 1; j++) {
			for (int i = 0; i < 4; i++) {
				Point p = new Point(mx - i * defaultCircleR * 2 + i, my - j * defaultCircleR * 2 + j,
						defaultCircleR);
				pos.add(p);
			}
		}
	}
	
	/**
	 * 添加画板底部上左边的三个圆
	 * @param downLeftX
	 * @param downLeftY
	 */
	private void addLeftBottonPoine(float downLeftX,float downLeftY) {
		for (int j = 0; j < 1; j++) { //行
			for (int i = 0; i < 2; i++) {  //列
				Point p = new Point(downLeftX + i * defaultCircleR * 2 + i, downLeftY - j * defaultCircleR * 2 + j,
						defaultCircleR);
				pos.add(p);
			}
			Point p = new Point(downLeftX + defaultCircleR, downLeftY -2*defaultCircleR+6,
					defaultCircleR);
			pos.add(p);	 
		}
	}
	/**
	 * 添加画板底部上右边的三个圆
	 * @param mx
	 * @param my
	 */
	private void addRightBottomPoint(float downX, float downY) {
		for (int j = 0; j < 1; j++) { //行
			for (int i = 0; i < 2; i++) {  //列
				Point p = new Point(downX - i * defaultCircleR * 2 + i, downY - j * defaultCircleR * 2 + j,
						defaultCircleR);
				pos.add(p);
			}
			Point p = new Point(downX - defaultCircleR, downY -2*defaultCircleR+6,
					defaultCircleR);
			pos.add(p);	 
		}
	}

	public float getPOINT_R_NORMAL() {
		return POINT_R_NORMAL;
	}

	public void setPOINT_R_NORMAL(float pOINT_R_NORMAL) {
		POINT_R_NORMAL = pOINT_R_NORMAL;
	}

	public float getPOINT_R_MEDIUM() {
		return POINT_R_MEDIUM;
	}

	public void setPOINT_R_MEDIUM(float pOINT_R_MEDIUM) {
		POINT_R_MEDIUM = pOINT_R_MEDIUM;
	}

	public float getPOINT_R_LARGE() {
		return POINT_R_LARGE;
	}

	public void setPOINT_R_LARGE(float pOINT_R_LARGE) {
		POINT_R_LARGE = pOINT_R_LARGE;
	}

	public void setPoints(List<Point> pos) {
		this.pos = pos;
		invalidate();
	}

	// 增加一个x行，y列的圆点数据
	public void addPoints(int x, int y) {
		float space = 24;
		// x和y的偏移量
		float mx = ((float) view_width - defaultCircleR * 2 * x - space * (x - 1)) / 2 + defaultCircleR;
		float my = ((float) view_height - defaultCircleR * 2 * y - space * (y - 1)) / 2 + defaultCircleR;
		// 创建圆组
		for (int j = 0; j < y; j++) {
			for (int i = 0; i < x; i++) {
				Point p = new Point(mx + i * defaultCircleR * 2 + i * space, my + j * defaultCircleR * 2 + j * space,
						defaultCircleR);
				// p.setNo("" + (j + i * y+1));
				pos.add(p);
			}
		}

		// for (int i = 0; i < x; i++) {
		// for (int j = 0; j < y; j++) {
		// Point p = new Point(mx + i * defaultCircleR * 2 + i * space, my + j *
		// defaultCircleR * 2 + j * space,
		// defaultCircleR);
		// pos.add(p);
		// }
		// }

		invalidate();
	}

	/**
	 * 网格辅助（未知线路）添加点时调用
	 * 
	 * @param x
	 * @param y
	 * @return 返回点集合
	 */
	public List<Point> addPointsRetList(int x, int y) {
		float space = 24;
		// x和y的偏移量
		float mx = ((float) view_width - defaultCircleR * 2 * x - space * (x - 1)) / 2 + defaultCircleR;
		float my = ((float) view_height - defaultCircleR * 2 * y - space * (y - 1)) / 2 + defaultCircleR;
		// 创建圆组
		for (int j = 0; j < y; j++) {
			for (int i = 0; i < x; i++) {
				Point p = new Point(mx + i * defaultCircleR * 2 + i * space, my + j * defaultCircleR * 2 + j * space,
						defaultCircleR);
				// p.setNo("" + (j + i * y+1));
				unKnowLinePos.add(p);
				pos.add(p);
			}
		}
		invalidate();
		return unKnowLinePos;
	}

	public List<Point> getPoints() {
		return pos;
	}

	public void clearPoints() {
		pos.clear();
		invalidate();
	}

	public GJDrawView(Context context) {
		super(context);
		init();
	}

	public GJDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public GJDrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GJDrawView(Context context, int width, int height, float scale) {
		super(context);
		view_width = width;
		view_height = height;
		this.onDoubleClickPointListener = null;
		this.touchEditor = null;
		this.drawMachine = new MiniDrawMachine().setScale(scale);
	}

	// 缩放画图器
	class MiniDrawMachine extends DrawMachine {
		// 圆画笔
		private Paint circelPaint;
		// 默认画笔
		private Paint defaultPaint;

		private float scale = 0.1f;// 比例

		// 支架画笔
		private Paint zjPaint;
		private float height_a = 24.8f;
		private float height_b = 40f;
		private float width_a = 20f;
		// 支架线的长度
		private float MINI_LINE_LENGTH = 150;//
		
		int i = 0xff111;

		public MiniDrawMachine setScale(float s) {
			scale = s;
			return this;
		}

		public MiniDrawMachine() {
			defaultPaint = new Paint();
			defaultPaint.setColor(Color.BLACK);
			defaultPaint.setStrokeWidth(5);
			defaultPaint.setStyle(Style.STROKE);

			circelPaint = new Paint();
			circelPaint.setColor(Color.RED);
			circelPaint.setStyle(Style.STROKE);
			circelPaint.setStrokeWidth(10);

			zjPaint = new Paint();
			zjPaint.setColor(Color.GRAY);
			zjPaint.setStyle(Style.STROKE);
			zjPaint.setStrokeWidth(4);

			MINI_LINE_LENGTH = DensityUtil.dip2px(getContext(), MINI_LINE_LENGTH);
			MINI_LINE_LENGTH = MINI_LINE_LENGTH - width_a;
		}

		@Override
		@SuppressLint("DrawAllocation")
		public void onDraw(Canvas canvas) {
			canvas.drawRect(0, 0, view_width, view_height, defaultPaint);
			for (int i = 0; i < pos.size(); i++) {
				Point p = pos.get(i);
				canvas.drawCircle(p.x * scale, p.y * scale, p.circleR * scale, circelPaint);
			}
			// 如果有支架数据，则画支架
			if (null != linesLocation && !linesLocation.isEmpty()) {
				for (Point line : linesLocation) {
					// 画一个三角形的支架
					Path path = new Path();
					if (line.x == 0) {
						path.moveTo(width_a * scale, line.y * scale);
						path.lineTo(MINI_LINE_LENGTH * scale, line.y * scale);
						path.lineTo(width_a * scale, (line.y + height_a) * scale);
						path.lineTo(width_a * scale, line.y * scale);
						path.addRect(0, (line.y - height_b) * scale, width_a * scale,
								(line.y + height_b + height_a) * scale, Direction.CW);
						path.close();
					} else {
						path.moveTo(line.x * scale, line.y * scale);
						path.lineTo(view_width - width_a * scale, line.y * scale);
						path.lineTo(view_width - width_a * scale, (line.y + height_a) * scale);
						path.lineTo(line.x * scale, line.y * scale);
						path.addRect(view_width - width_a * scale, (line.y - height_b) * scale, view_width,
								(line.y + height_b + height_a )* scale, Direction.CW);
						path.close();
					}
					canvas.drawPath(path, zjPaint);
				}
			}
		}
	}

	public void setCircleR(float r) {
		defaultCircleR = r;
	}

	private void resetConfig() {
		// 小圆
		POINT_R_NORMAL = 15;
		// 中圆
		POINT_R_MEDIUM = 20;
		// 大圆
		POINT_R_LARGE = 25;
	}

	private void init() {
		resetConfig();
		POINT_R_LARGE = DensityUtil.dip2px(getContext(), POINT_R_LARGE);
		POINT_R_MEDIUM = DensityUtil.dip2px(getContext(), POINT_R_MEDIUM);
		POINT_R_NORMAL = DensityUtil.dip2px(getContext(), POINT_R_NORMAL);
		LINE_LENGTH = DensityUtil.dip2px(getContext(), LINE_LENGTH);
		defaultCircleR = POINT_R_MEDIUM;
		touchEditor = new DragTouchEditor();
		drawMachine = new DrawMachine();
	}

	// 随机生成圆点，大小为默认值
	public void randomPoint(int count) {
		for (int i = 1; i <= count; i++) {
			float x = 0, y = 0;
			int k = i % 6;
			x = defaultCircleR * 2 * k + 40;
			y = defaultCircleR * 2 * ((int) (i / 8 + 1)) + 40;
			Point p = new Point(x, y, defaultCircleR);
			pos.add(p);
		}
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		if (widthMode == MeasureSpec.EXACTLY) {
			// 精确或指定大小
			view_width = widthSize;
		}
		if (widthMode == MeasureSpec.AT_MOST) {
			// view_width = widthSize;
		}
		if (heightMode == MeasureSpec.EXACTLY) {
			// Parent has told us how big to be. So be it.
			view_height = heightSize;
		}
		if (heightMode == MeasureSpec.AT_MOST) {
			heightSize = view_width;
			view_height = heightSize;
		}
		setMeasuredDimension(view_width, view_height);
	}

	/**
	 * 重置点坐标，因为不同屏幕大小手机采集出来的截面坐标是不一样的，从而造成显示效果不一样，在小屏手机采集出来的截面，在大屏手机
	 * 上显示会有一大片空白，而在大屏手机上采集的截面
	 * 
	 * @param width
	 * @param heidht
	 */
	public void resetPointsSize(int width, int heidht) {
		if (null == pos || pos.isEmpty()) {
			return;
		}
		if (view_width <= 0 || view_height <= 0) {
			return;
		}
		float scalew = (float) width / (float) view_width;
		float scaleh = (float) heidht / (float) view_height;
		if (scalew != scaleh) {
			return;
		}
		for (Point p : pos) {
			p.x = p.x * scalew;
			p.y = p.y * scalew;
			p.circleR = p.circleR * scalew;
		}
		invalidate();
	}

	// 获取画板的宽度
	public int getViewWidth() {
		return view_width;
	}

	// 获取画板的高度
	public int getViewHeight() {
		return view_height;
	}

	// 画图器
	private DrawMachine drawMachine = null;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawMachine.onDraw(canvas);
	}

	public void setDrawMachine(DrawMachine machine) {
		if (null == machine) {
			return;
		}
		this.drawMachine = machine;
		invalidate();
	}

	// 默认画图器
	public static final int DRAWMACHINE_DEFAULT = 0;
	// 网格画图器
	public static final int DRAWMACHINE_RECT = 1;
	public static final int DRAWMACHINE_MINI = 2;

	public DrawMachine getDrawMachine(int machine) {
		if (machine == DRAWMACHINE_RECT) {
			return new RectDrawMachine();
		}
		if (machine == DRAWMACHINE_MINI) {
			return new MiniDrawMachine();
		}
		return new DrawMachine();
	}

	// 获取迷你缩放画图器
	public DrawMachine getMiniDrawMachine(float scale) {
		MiniDrawMachine mini = new MiniDrawMachine();
		mini.setScale(scale);
		return mini;
	}

	// 生成网格画图器
	public DrawMachine getWGDrawMachine(int x, int y) {
		RectDrawMachine action = new RectDrawMachine();
		action.setMx(x);
		action.setMy(y);
		return action;
	}

	// 画笔填充颜色
	private static final ArrayList<Integer> fillColors = new ArrayList<Integer>();

	static {
		// "#f00","#ff0","#0f0","#0ff","#00f","#f0f","#00a650","#2f3192","#00aeef","#f7941d","#588528"]
		// fillColors.add(Color.WHITE);

		fillColors.add(Color.parseColor("#ff0000"));
		fillColors.add(Color.parseColor("#ffff00"));
		fillColors.add(Color.parseColor("#00ff00"));
		fillColors.add(Color.parseColor("#00ffff"));
		fillColors.add(Color.parseColor("#0000ff"));
		fillColors.add(Color.parseColor("#ff00ff"));
		fillColors.add(Color.parseColor("#00a650"));
		fillColors.add(Color.parseColor("#2f3192"));
		fillColors.add(Color.parseColor("#00aeef"));
		fillColors.add(Color.parseColor("#f7941d"));
		fillColors.add(Color.parseColor("#588528"));
	}

	private HashMap<String, Integer> colorMap = null;
	private HashMap<String, Integer> copyColorMap = new HashMap<String, Integer>();

	private Integer fillColor;

	public void setFillColor(Point point, int fillColor) {
		this.fillColor = fillColor;
		colorMap = new HashMap<String, Integer>();
		colorMap.put(point.getLineName(), this.fillColor);
		Integer integer = colorMap.get(point.getLineName());
		if (integer != null) {
			copyColorMap.put(point.getLineName(), integer);
		}
		copyColorMap.put(point.getLineName(), this.fillColor);
		copyColorMap.put("UNKNOW-LINE", Color.GRAY);
		invalidate();
	}

	public void setLineNOs(List<String> lineNOs) {
		if (lineNOs == null || lineNOs.isEmpty()) {
			return;
		}
		colorMap = new HashMap<String, Integer>();

		int end = lineNOs.size() < fillColors.size() ? lineNOs.size() : fillColors.size();
		for (int i = 0; i < end; i++) {
			colorMap.put(lineNOs.get(i), fillColors.get(i));
		}
		// 未知线路设置黑色填充
		colorMap.put("UNKNOW-LINE", Color.GRAY);
		invalidate();
	}

	// 默认画图器
	class DrawMachine {
		// 填充画笔
		private Paint fillPaint;
		// 圆画笔
		private Paint circelPaint;
		// 默认画笔
		private Paint defaultPaint;
		// 字体画笔
		private Paint textPaint;
		// 字体大小
		private int textSize = 32;
		// 支架画笔
		private Paint zjPaint;
		private float height_a = 24.8f;
		private float height_b = 40f;
		private float width_a = 20f;

		public DrawMachine() {
			defaultPaint = new Paint();
			defaultPaint.setColor(Color.BLACK);
			defaultPaint.setStrokeWidth(10);
			defaultPaint.setStyle(Style.STROKE);

			circelPaint = new Paint();
			circelPaint.setColor(Color.RED);
			circelPaint.setStyle(Style.STROKE);
			circelPaint.setStrokeWidth(10);

			textPaint = new Paint();
			textPaint.setStrokeWidth(8);
			textPaint.setTextSize(textSize);
			textPaint.setColor(Color.BLACK);

			fillPaint = new Paint();
			fillPaint.setStyle(Style.FILL);
			fillPaint.setColor(Color.GRAY);

			zjPaint = new Paint();
			zjPaint.setColor(Color.GRAY);
			zjPaint.setStyle(Style.STROKE);
			zjPaint.setStrokeWidth(4);

			LINE_LENGTH = LINE_LENGTH - width_a;
		}

		@SuppressLint("DrawAllocation")
		public void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			// 画背景框
			canvas.drawRect(0, 0, view_width, view_height, defaultPaint);
			// 画管线
			for (int i = 0; i < pos.size(); i++) {
				Point p = pos.get(i);
				if (p.getNo() == null) {
					p.setNo((i + 1) + "");
				}
				canvas.drawCircle(p.x, p.y, p.circleR, circelPaint);
				if (!TextUtils.isEmpty(p.getLineId())) {
					if (null != copyColorMap) {
						Integer color = copyColorMap.get(p.getLineName());
						if (p.getLineId().equals("UNKNOW-LINE")) {
							color = Color.GRAY;
						}
						if (color == null) {
							color = Color.WHITE;
						}
						if (p.getFillColor() != null) {
							fillPaint.setColor(Color.parseColor(p.getFillColor()));
						} else {
							fillPaint.setColor(color);
						}
					}
					canvas.drawCircle(p.x, p.y, p.circleR - 5, fillPaint);
				}
				canvas.drawText(p.getNo(), p.x - textSize / 4, p.y + textSize / 2, textPaint);
			}
			// 如果有支架数据，则画支架
			if (null != linesLocation && !linesLocation.isEmpty()) {
				for (Point line : linesLocation) {
					// 画一个三角形的支架
					Path path = new Path();
					if (line.x == 0) {
						path.moveTo(width_a, line.y);
						path.lineTo(LINE_LENGTH, line.y);
						path.lineTo(width_a, line.y + height_a);
						path.lineTo(width_a, line.y);
						path.addRect(0, line.y - height_b, width_a, line.y + height_b + height_a, Direction.CW);
						path.close();
					} else {
						path.moveTo(line.x, line.y);
						path.lineTo(view_width - width_a, line.y);
						path.lineTo(view_width - width_a, line.y + height_a);
						path.lineTo(line.x, line.y);
						path.addRect(view_width - width_a, line.y - height_b, view_width, line.y + height_b + height_a,
								Direction.CW);
						path.close();
					}
					canvas.drawPath(path, zjPaint);
				}
			}
		}
	}

	// 辅助网格画图器
	class RectDrawMachine extends DrawMachine {
		private int mx = 8;// 长多少个格子
		private int my = 7;// 宽多少个格子
		private float mxs;
		private float mys;
		private Paint rectPaint;

		public RectDrawMachine() {
			rectPaint = new Paint();
			rectPaint.setStrokeWidth(5);
			rectPaint.setColor(Color.BLUE);
			mxs = view_width / mx;
			mys = view_height / my;
		}

		public int getMx() {
			return mx;
		}

		public void setMx(int mx) {
			this.mx = mx;
			mys = view_width / mx;
		}

		public int getMy() {
			return my;
		}

		public void setMy(int my) {
			this.my = my;
			mxs = view_height / my;
		}

		public void onDraw(Canvas canvas) {
			for (int i = 1; i < mx; i++) {
				// 画行，x坐标不变，y坐标变
				canvas.drawLine(0, mys * i, view_width, mys * i, rectPaint);
			}

			for (int i = 1; i < my; i++) {
				// 画列，x坐标变，y坐标不变
				canvas.drawLine(mxs * i, 0, mxs * i, view_height, rectPaint);
			}
			super.onDraw(canvas);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (null != touchEditor) {
			touchEditor.onTouch(pos, event);
			if (touchEditor.reflesh()) {
				invalidate();
			}
		}
		super.onTouchEvent(event);
		return true;
	}

	public void clean() {
		pos.clear();
		invalidate();
	}

	// 触摸画板监听器
	private TouchEditor touchEditor;// = new DefaultTouchEditor();

	public void setTouchEditor(TouchEditor t) {
		touchEditor = t;
	}

	public TouchEditor getTouchEditor() {
		return touchEditor;
	}

	// 触摸画板监听器
	public interface TouchEditor {
		// 触摸事件
		boolean onTouch(List<Point> pos, MotionEvent event);

		// 是否需要刷新
		boolean reflesh();

		// 销毁监听器，如果参数b为true，则销毁对象，如果为false，则只是销毁对象使用资源
		boolean destroy(boolean b);
	}

	// 点双击事件监听器
	private OnDoubleClickPointListener onDoubleClickPointListener = null;

	public void setOnDoubleClickPointListener(OnDoubleClickPointListener listener) {
		onDoubleClickPointListener = listener;
	}

	public interface OnDoubleClickPointListener {
		// 如果需要刷新数据，则返回true，否则返回false
		boolean clickResult(Point p);
	}

	// 默认触摸编辑器，增加点、修改点数据、删除点
	class DefaultTouchEditor implements TouchEditor {
		long startTime = 0;
		long endTime = 0;

		// 超过两秒的时间为长按事件
		boolean longClick(MotionEvent e) {
			switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 开始记录时间
				startTime = System.currentTimeMillis();
				break;
			case MotionEvent.ACTION_UP:
				// 关闭时间
				endTime = System.currentTimeMillis();
				break;
			case MotionEvent.ACTION_OUTSIDE:
				// 滑出控件
				destroy(true);
				return false;
			}
			return endTime - startTime > 2000;
		}

		Timer mTimer = new Timer();
		private boolean timeOut = false;
		private int click = 0;

		// 是否双击事件
		boolean doubleClick() {
			click++;
			if (!timeOut) {
				// 启动计时器
				mTimer.schedule(new DoubleClickTask(), 500);
				timeOut = false;
			}
			if (click >= 2) {
				return true;
			}
			return false;
		}

		class DoubleClickTask extends TimerTask {
			@Override
			public void run() {
				timeOut = false;
				click = 0;
			}
		}

		@Override
		public boolean onTouch(final List<Point> pots, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 编辑点数据
				Point point = new Point(event.getX(), event.getY(), defaultCircleR);
				int containsPoint = isContainsPoint(pots, point);
				if (containsPoint >= 0) {
					if (doubleClick()) {
						final Point p = pots.get(containsPoint);
						if (onDoubleClickPointListener != null) {
							needReflesh = onDoubleClickPointListener.clickResult(p);
						} else {
							Toast.makeText(getContext(), "需要的话，请设置双击点事件监听器", Toast.LENGTH_SHORT).show();
						}
						return true;
					}
				}
				if (containsPoint < 0) {
					// 判断是否越界
					if (point.x - point.circleR < 0 || point.x + point.circleR > view_width
							|| point.y - point.circleR < 0 || point.y + point.circleR > view_height) {
						needReflesh = false;
						return false;
					} else {// 增加点
						pots.add(point);
						needReflesh = true;
						return true;
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
			return false;
		}

		private boolean needReflesh = true;

		@Override
		public boolean reflesh() {
			return needReflesh;
		}

		@Override
		public boolean destroy(boolean b) {
			startTime = 0;
			endTime = 0;
			return true;
		}
	}

	// 默认触摸编辑器，增加点、修改点数据、删除点
	class LookTouchEditor implements TouchEditor {
		long startTime = 0;
		long endTime = 0;

		// 超过两秒的时间为长按事件
		boolean longClick(MotionEvent e) {
			switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 开始记录时间
				startTime = System.currentTimeMillis();
				break;
			case MotionEvent.ACTION_UP:
				// 关闭时间
				endTime = System.currentTimeMillis();
				break;
			case MotionEvent.ACTION_OUTSIDE:
				// 滑出控件
				destroy(true);
				return false;
			}
			return endTime - startTime > 2000;
		}

		Timer mTimer = new Timer();
		private boolean timeOut = false;
		private int click = 0;

		// 是否双击事件
		boolean doubleClick() {
			click++;
			if (!timeOut) {
				// 启动计时器
				mTimer.schedule(new DoubleClickTask(), 500);
				timeOut = false;
			}
			if (click >= 2) {
				return true;
			}
			return false;
		}

		class DoubleClickTask extends TimerTask {
			@Override
			public void run() {
				timeOut = false;
				click = 0;
			}
		}

		@Override
		public boolean onTouch(final List<Point> pots, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 编辑点数据
				Point point = new Point(event.getX(), event.getY(), defaultCircleR);
				int containsPoint = isContainsPoint(pots, point);
				if (containsPoint >= 0) {
					if (doubleClick()) {
						final Point p = pots.get(containsPoint);
						if (onDoubleClickPointListener != null) {
							needReflesh = onDoubleClickPointListener.clickResult(p);
						} else {
							Toast.makeText(getContext(), "需要的话，请设置双击点事件监听器", Toast.LENGTH_SHORT).show();
						}
						return true;
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
			return false;
		}

		private boolean needReflesh = true;

		@Override
		public boolean reflesh() {
			return needReflesh;
		}

		@Override
		public boolean destroy(boolean b) {
			startTime = 0;
			endTime = 0;
			return true;
		}
	}

	// 拖动编辑器
	class DragTouchEditor implements TouchEditor {
		private TouchEditor defaultEditor = new DefaultTouchEditor();
		private Point point = null;
		private float px;
		private float py;

		@Override
		public boolean onTouch(List<Point> pos, MotionEvent event) {
			boolean onTouch = defaultEditor.onTouch(pos, event);
			isTouch = true;
			needReflesh = defaultEditor.reflesh();
			if (onTouch) {
				// 说明触摸事件已经被默认触摸处理器处理掉了
				point = null;
				return true;
			}
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				int containsPoint = isContainsPoint(pos, new Point(event.getX(), event.getY()));
				if (containsPoint < 0) {
					// 点击了空白处
					break;
				}
				point = pos.get(containsPoint);
				px = point.x - event.getX();
				py = point.y - event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				if (null == point) {
					break;
				}
				// 保存现场
				float x = point.x;
				float y = point.y;
				// 改动现场
				point.x = event.getX() + px;
				point.y = event.getY() + py;
				// 判断是否越界
				if (point.x - point.circleR < 0 || point.x + point.circleR > view_width || point.y - point.circleR < 0
						|| point.y + point.circleR > view_height) {
					// 恢复现场
					point.x = x;
					point.y = y;
				}
				needReflesh = true;
				break;
			case MotionEvent.ACTION_OUTSIDE:
				// Toast.makeText(getContext(), "OUT", 0).show();
				defaultEditor.destroy(true);
				destroy(true);
				break;
			case MotionEvent.ACTION_UP:
				defaultEditor.destroy(true);
				destroy(true);
				break;
			}
			return true;
		}

		private boolean needReflesh = true;

		@Override
		public boolean reflesh() {
			return needReflesh;
		}

		@Override
		public boolean destroy(boolean b) {
			// TODO Auto-generated method stub
			point = null;
			px = 0;
			py = 0;
			return true;
		}

	}

	// 删除编辑器
	class DeleteTouchEditor implements TouchEditor {
		@Override
		public boolean onTouch(List<Point> pos, MotionEvent event) {
			// TODO Auto-generated method stub
			int containsPoint = isContainsPoint(pos, new Point(event.getX(), event.getY()));
			if (containsPoint >= 0) {
				// 点击了圆
				pos.remove(containsPoint);
				invalidate();
			}
			return true;
		}

		@Override
		public boolean reflesh() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean destroy(boolean b) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	public static final int TOUCH_EDITOE_DEFAULT = 0;
	public static final int TOUCH_EDITOE_DRAG = 1;
	public static final int TOUCH_EDITOE_DELETE = 2;
	public static final int TOUCH_EDITOE_LOOK = 3;

	private DefaultTouchEditor defaultTouchEditor = new DefaultTouchEditor();
	private DeleteTouchEditor deleteTouchEditor = new DeleteTouchEditor();
	private DragTouchEditor dragTouchEditor = new DragTouchEditor();
	private LookTouchEditor lookTouchEditor = new LookTouchEditor();

	public TouchEditor getTouchEditor(int editor) {
		switch (editor) {
		case TOUCH_EDITOE_DEFAULT:
			return defaultTouchEditor;
		case TOUCH_EDITOE_DELETE:
			return deleteTouchEditor;
		case TOUCH_EDITOE_DRAG:
			return dragTouchEditor;
		case TOUCH_EDITOE_LOOK:
			return lookTouchEditor;
		}
		return new DefaultTouchEditor();
	}

	// 判断点是否在圆内，-1表示不在圆内，正数表示在圆内
	public static int isContainsPoint(List<Point> pots, Point p) {
		for (int i = 0; i < pots.size(); i++) {
			if (touchInCircle(pots.get(i), p)) {
				return i;
			}
		}
		return -1;
	}

	// 判断点x2是否在点x1以r为半径的圆内
	public static boolean touchInCircle(Point x1, Point x2) {
		float r1 = x1.circleR, r2 = x2.circleR;
		return (x1.x - x2.x) * (x1.x - x2.x) + (x1.y - x2.y) * (x1.y - x2.y) - (r1 + r2) * (r1 + r2) < 0;
	}

}
