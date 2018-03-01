package com.winway.android.media.vr;

import static android.opengl.GLES20.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_NEAREST;
import static android.opengl.GLES20.GL_NO_ERROR;
import static android.opengl.GLES20.GL_REPEAT;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetError;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glTexParameterf;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.frustumM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.scaleM;
import static android.opengl.Matrix.translateM;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.winway.android.media.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 全景显示的View控件
 * 使用方法：直接new出控件即可
 * @author mr-lao
 *
 */
public class PanoramaView2 extends GLSurfaceView {
	private PanoramaRenderer panoramaRenderer = null;

	private PanoramaView2(Context context) {
		super(context);
		init(null, context);
	}

	/**
	 * 在XML布局中使用的PanoramaView系统会自动调用此构造方法
	 * 注意的是，必须在XML中设置PanoramaView的图片资源ID，否则程序会崩溃
	 * @param context
	 * @param drawableId
	 */
	public PanoramaView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, context);
	}

	/**
	 * 在Java代码中直接new出来
	 * @param context
	 * @param drawableId   全景图片资源ID
	 */
	public PanoramaView2(Context context, int drawableId) {
		super(context);
		setEGLContextClientVersion(2);
		setPanoramaImage(drawableId);
	}

	/**
	 * 在Java代码中直接new出来
	 * @param context
	 * @param bitmap   全景图片
	 */
	public PanoramaView2(Context context, Bitmap bitmap) {
		super(context);
		setEGLContextClientVersion(2);
		setPanoramaImage(bitmap);
	}

	/**
	 * 在Java代码中直接new出来
	 * @param context
	 * @param imageFilePath   全景图片文件的绝对路径
	 */
	public PanoramaView2(Context context, String imageFilePath) {
		super(context);
		setEGLContextClientVersion(2);
		setPanoramaImage(BitmapFactory.decodeFile(imageFilePath));
	}

	@SuppressLint("Recycle")
	void init(AttributeSet attrs, Context context) {
		setEGLContextClientVersion(2);
		if (null != attrs) {
			TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.panorama);
			int drawableId = ta.getResourceId(R.styleable.panorama_src, -1);
			if (drawableId > 0) {
				setPanoramaImage(drawableId);
			}
		}
	}

	private void setPanoramaImage(Bitmap bitmap) {
		panoramaRenderer = new PanoramaRenderer(getContext(), bitmap);
		setRenderer(panoramaRenderer);
	}

	private void setPanoramaImage(int drawableId) {
		setPanoramaImage(getBitmapFromDrawableId(getContext(), drawableId));
	}

	private float mPreviousY;
	private float mPreviousX;
	// up,down
	private static float[] ysection = new float[] { -80, 160 };
	// 目前的Y
	private float cy = 0;

	private boolean calculateSection(float dy) {
		float temp = cy + dy;
		if (temp > ysection[0] && temp < ysection[1]) {
			cy = temp;
			return true;
		}
		return false;
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent e) {
		float y = e.getY();
		float x = e.getX();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dy = y - mPreviousY;
			float dx = x - mPreviousX;
			panoramaRenderer.yAngle += dx * 0.3f;
			if (calculateSection(dy)) {
				panoramaRenderer.xAngle += dy * 0.3f;
			}
		}
		mPreviousY = y;
		mPreviousX = x;
		return true;
	}

	static Bitmap getBitmapFromDrawableId(Context context, int drawableId) {
		InputStream is = context.getResources().openRawResource(drawableId);
		Bitmap bitmapTmp;
		try {
			bitmapTmp = BitmapFactory.decodeStream(is);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmapTmp;
	}

	private static class PanoramaRenderer implements Renderer {
		Context mContext;
		private int mProgram;
		private int mAPositionHandler;
		private int mUProjectMatrixHandler;
		private int mATextureCoordHandler;
		private final float[] projectMatrix = new float[16];
		private int mSize;
		private FloatBuffer vertexBuff;

		private FloatBuffer textureBuff;
		private int textrueID;

		private Bitmap bitmap;

		public PanoramaRenderer(Context context, Bitmap bitmap) {
			this.bitmap = bitmap;
			mContext = context;
			init();
		}

		public void init() {
			int perVertex = 36;
			double perRadius = 2 * Math.PI / (float) perVertex;
			double perW = 1 / (float) perVertex;
			double perH = 1 / (float) (perVertex);

			ArrayList<Float> vetexList = new ArrayList<Float>();
			ArrayList<Float> textureList = new ArrayList<Float>();
			for (int a = 0; a < perVertex; a++) {
				for (int b = 0; b < perVertex; b++) {
					float w1 = (float) (a * perH);
					float h1 = (float) (b * perW);

					float w2 = (float) ((a + 1) * perH);
					float h2 = (float) (b * perW);

					float w3 = (float) ((a + 1) * perH);
					float h3 = (float) ((b + 1) * perW);

					float w4 = (float) (a * perH);
					float h4 = (float) ((b + 1) * perW);

					textureList.add(h1);
					textureList.add(w1);

					textureList.add(h2);
					textureList.add(w2);

					textureList.add(h3);
					textureList.add(w3);

					textureList.add(h3);
					textureList.add(w3);

					textureList.add(h4);
					textureList.add(w4);

					textureList.add(h1);
					textureList.add(w1);

					float x1 = (float) (Math.sin(a * perRadius / 2) * Math.cos(b * perRadius));
					float z1 = (float) (Math.sin(a * perRadius / 2) * Math.sin(b * perRadius));
					float y1 = (float) Math.cos(a * perRadius / 2);

					float x2 = (float) (Math.sin((a + 1) * perRadius / 2) * Math.cos(b * perRadius));
					float z2 = (float) (Math.sin((a + 1) * perRadius / 2) * Math.sin(b * perRadius));
					float y2 = (float) Math.cos((a + 1) * perRadius / 2);

					float x3 = (float) (Math.sin((a + 1) * perRadius / 2) * Math.cos((b + 1) * perRadius));
					float z3 = (float) (Math.sin((a + 1) * perRadius / 2) * Math.sin((b + 1) * perRadius));
					float y3 = (float) Math.cos((a + 1) * perRadius / 2);

					float x4 = (float) (Math.sin(a * perRadius / 2) * Math.cos((b + 1) * perRadius));
					float z4 = (float) (Math.sin(a * perRadius / 2) * Math.sin((b + 1) * perRadius));
					float y4 = (float) Math.cos(a * perRadius / 2);

					vetexList.add(x1);
					vetexList.add(y1);
					vetexList.add(z1);

					vetexList.add(x2);
					vetexList.add(y2);
					vetexList.add(z2);

					vetexList.add(x3);
					vetexList.add(y3);
					vetexList.add(z3);

					vetexList.add(x3);
					vetexList.add(y3);
					vetexList.add(z3);

					vetexList.add(x4);
					vetexList.add(y4);
					vetexList.add(z4);

					vetexList.add(x1);
					vetexList.add(y1);
					vetexList.add(z1);
				}
			}
			mSize = vetexList.size() / 3;
			float texture[] = new float[mSize * 2];
			for (int i = 0; i < texture.length; i++) {
				texture[i] = textureList.get(i);
			}
			textureBuff = ByteBuffer.allocateDirect(texture.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			textureBuff.put(texture);
			textureBuff.position(0);

			float vetex[] = new float[mSize * 3];
			for (int i = 0; i < vetex.length; i++) {
				vetex[i] = vetexList.get(i);
			}
			vertexBuff = ByteBuffer.allocateDirect(vetex.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			vertexBuff.put(vetex);
			vertexBuff.position(0);
		}

		@Override
		public void onDrawFrame(GL10 arg0) {
			rotateM(mCurrMatrix, 0, -xAngle, 1, 0, 0);
			rotateM(mCurrMatrix, 0, -yAngle, 0, 1, 0);
			rotateM(mCurrMatrix, 0, -zAngle, 0, 0, 1);

			glClearColor(1, 1, 1, 1);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			glActiveTexture(GLES20.GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, textrueID);
			glUniformMatrix4fv(mUProjectMatrixHandler, 1, false, getfinalMVPMatrix(), 0);
			glDrawArrays(GL_TRIANGLES, 0, mSize);
		}

		public float xAngle;
		public float yAngle;
		public float zAngle;

		final float mCurrMatrix[] = new float[16];

		final float mMVPMatrix[] = new float[16];

		public float[] getfinalMVPMatrix() {
			Matrix.multiplyMM(mMVPMatrix, 0, projectMatrix, 0, mCurrMatrix, 0);
			Matrix.setIdentityM(mCurrMatrix, 0);
			return mMVPMatrix;
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			glViewport(0, 0, width, height);
			GLES20.glEnable(GLES20.GL_CULL_FACE);
			float ratio = width / (float) height;
			frustumM(projectMatrix, 0, -ratio, ratio, -1, 1, 1, 20);

			Matrix.setIdentityM(mCurrMatrix, 0);
			Matrix.setIdentityM(mMVPMatrix, 0);

			translateM(projectMatrix, 0, 0, 0, -2);
			scaleM(projectMatrix, 0, 4, 4, 4);

			mProgram = getProgram(mContext);
			glUseProgram(mProgram);

			mAPositionHandler = glGetAttribLocation(mProgram, "aPosition");
			mUProjectMatrixHandler = glGetUniformLocation(mProgram, "uProjectMatrix");
			mATextureCoordHandler = glGetAttribLocation(mProgram, "aTextureCoord");
			textrueID = initTexture(mContext, bitmap);
			glVertexAttribPointer(mAPositionHandler, 3, GL_FLOAT, false, 0, vertexBuff);
			glVertexAttribPointer(mATextureCoordHandler, 2, GL_FLOAT, false, 0, textureBuff);
			glEnableVertexAttribArray(mAPositionHandler);
			glEnableVertexAttribArray(mATextureCoordHandler);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		}
	}

	public static int getProgram(Context context) {
		String vertexStr = "attribute vec4 aPosition;attribute vec2 aTextureCoord;uniform mat4 uProjectMatrix;varying vec2 vTextureCoord;void main(){gl_Position = uProjectMatrix * aPosition;vTextureCoord = aTextureCoord;}";
		String fragmentStr = "precision mediump float;varying vec2 vTextureCoord;uniform sampler2D uTexture;void main(){gl_FragColor = texture2D(uTexture, vTextureCoord); }";
		return getProgram(vertexStr, fragmentStr);
	}

	public static int getProgram(String vertexStr, String fragmentStr) {
		int program = glCreateProgram();
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, vertexStr);
		glShaderSource(fragmentShader, fragmentStr);
		glCompileShader(vertexShader);
		glCompileShader(fragmentShader);
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		glLinkProgram(program);
		return program;
	}

	protected static int bindTexture_(Context context, int drawable) {
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inScaled = false;
		return bindTexture(BitmapFactory.decodeResource(context.getResources(), drawable, option));
	}

	protected static int bindTexture(Bitmap bitmap) {
		int[] textures = new int[1];
		glGenTextures(1, textures, 0);
		glBindTexture(GL_TEXTURE_2D, textures[0]);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
		glGenerateMipmap(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);
		return textures[0];
	}

	public static void checkGlError(String op) {
		int error;
		while ((error = glGetError()) != GL_NO_ERROR) {
			throw new RuntimeException(op + ": glError " + error);
		}
	}

	public static int initTexture(Context context, Bitmap bitmapTmp) {
		int[] textures = new int[1];
		glGenTextures(1, textures, 0);
		int textureId = textures[0];
		glBindTexture(GL_TEXTURE_2D, textureId);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmapTmp, 0);
		bitmapTmp.recycle();

		return textureId;
	}

}
