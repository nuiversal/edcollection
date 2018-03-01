package com.winway.android.ewidgets.attachment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.provider.MediaStore.Images;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lamemp3.RecorderAndPlayUtil;
import com.example.lamemp3.RecorderAndPlayUtil.onPlayerCompletionListener;
import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.attachment.entity.Panoramic;
import com.winway.android.media.photo.photoselector.model.PhotoModel;
import com.winway.android.media.photo.photoselector.ui.PhotoSelectorActivity;
import com.winway.android.media.photo.photoselector.util.PhotoUtils;
import com.winway.android.media.video.VideoUtil;
import com.winway.android.media.voice.RecorderDialog;
import com.winway.android.media.voice.RecorderDialog.OnRecordeResultListener;
import com.winway.android.media.vr.LookVRImageActivity;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.DateUtils;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.FileUtil;
import com.winway.android.util.GpsUtil;
import com.winway.android.util.ImageHelper;
import com.winway.android.util.PhotoUtil;
import com.winway.android.util.ToastUtils;
import com.winway.android.util.UUIDGen;

/**
 * 附件采集组件 功能：文件附件采集、图片附件采集、视频附件采集、音频附件采集
 * 注意：使用图片和视频采集功能的时候，调用者一定要在使用AttachmentView的Activity中执行AttachmentView
 * .onActivityResult() 方法，否则图片和视频数据无法返回给调用者 使用方法： 1、在布局中加入，或直接new出实例
 * <com.winway.android.ewidgets.attachment.AttachmentView android:id=
 * "@+id/attachment_view" android:layout_width="wrap_content"
 * android:layout_height="wrap_content" android:background="#FFFFFF" /> 2、
 * 设置Activity attach.setActivity(activity); 3、初始化
 * attach.setAttachmentAttrs(attrs);
 * 
 * @author mr-lao
 *
 */
@SuppressLint({ "CutPasteId", "HandlerLeak" })
public class AttachmentView extends RelativeLayout {
	private Button btnVideo;// 录像
	private Button btnRecorde;// 录音
	private Button btnPhoto;// 拍照
	private Button btnText;// 文本
	private Button btnFinish;// 结束
	private Button btnTakeVr;// vr
	private Button btnImages;// 图库
	private TextView tvTitleName;// 左侧标题
	private LinearLayout attachmentContainer;

	private Activity activity;
	/** 录像请求码 */
	final int REQUEST_CODE_VEDIO = 0x00000001;
	/** 拍照请求码 */
	final int REQUEST_CODE_PHOTO = 0x00000003;
	/** 录音请求码 */
	final int REQUEST_CODE_RECORDE = 0x00000002;
	/** 文本请求码 */
	final int REQUEST_CODE_TEXT = 0x00000004;
	/** VR请求码 */
	public final int REQUEST_CODE_VR = 0x00000005;
	/** 图库选择请求码 */
	public final int REQUEST_CODE_SELECT_IMAGES = 0x00000006;

	// 记录当前正在保存正在采集的数据的文件的路径
	private String nowFilePath = null;

	private AttachmentAttrs attrs;

	private RecorderDialog recorderDialog;

	private OnAttachmentResultListener resultListener;
	private AttachmentResult result = new AttachmentResult();

	private AttachOptionListener attachOptListener;

	public AttachOptionListener getAttachOptListener() {
		return attachOptListener;
	}

	/**
	 * 设置附件操作事件监听器 详情见<code>AttachOptionListener</code>
	 * 
	 * @param attachOptListener
	 */
	public void setAttachOptListener(AttachOptionListener attachOptListener) {
		this.attachOptListener = attachOptListener;
	}

	public AttachmentResult getResult() {
		return result;
	}

	public void setOnResultListener(OnAttachmentResultListener resultListener) {
		this.resultListener = resultListener;
	}

	public void setAttachmentAttrs(AttachmentAttrs attrs) {
		this.attrs = attrs;
		if (attrs.hasPhoto) {
			btnPhoto.setVisibility(View.VISIBLE);
			btnImages.setVisibility(View.VISIBLE);
		} else {
			btnPhoto.setVisibility(View.GONE);
			btnImages.setVisibility(View.GONE);
		}
		if (attrs.hasVideo) {
			btnVideo.setVisibility(View.VISIBLE);
		} else {
			btnVideo.setVisibility(View.GONE);
		}
		if (attrs.hasVoice) {
			btnRecorde.setVisibility(View.VISIBLE);
		} else {
			btnRecorde.setVisibility(View.GONE);
		}
		if (attrs.hasText) {
			btnText.setVisibility(View.VISIBLE);
		} else {
			btnText.setVisibility(View.GONE);
		}
		if (attrs.hasVr) {// vr显示控制
			btnTakeVr.setVisibility(View.VISIBLE);
		} else {
			btnTakeVr.setVisibility(View.GONE);
		}
		if (attrs.hasFinish) {
			btnFinish.setVisibility(View.VISIBLE);
		} else {
			btnFinish.setVisibility(View.GONE);
		}
		if (null != attrs.activity) {
			this.activity = attrs.activity;
		}
		if (attrs.isJustShow) {
			findViewById(R.id.toolbar).setVisibility(View.GONE);
			invalidate();
		}
		if (attrs.titleName != null) {
			tvTitleName.setText(attrs.titleName);
		}
		initAttrsFileDir();
	}

	/** 初始化存储目录 */
	private void initAttrsFileDir() {
		if (null != attrs.photoDir) {
			File f = new File(attrs.photoDir, ".dir");
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			if (f.exists()) {
				f.delete();
			}
		}
		if (null != attrs.videoDir) {
			File f = new File(attrs.videoDir, ".dir");
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			if (f.exists()) {
				f.delete();
			}
		}
		if (null != attrs.voiceDir) {
			File f = new File(attrs.voiceDir, ".dir");
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			if (f.exists()) {
				f.delete();
			}
		}
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public AttachmentView(Context context) {
		super(context);
		this.init(context, null, 0);
	}

	public AttachmentView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.init(context, attrs, defStyle);
	}

	public AttachmentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context, attrs, 0);
	}

	void init(Context context, AttributeSet attrs, int defStyle) {
		LayoutInflater.from(context).inflate(R.layout.layout_offline_attachment, this);
		btnPhoto = (Button) this.findViewById(R.id.btn_take_photo);
		btnText = (Button) this.findViewById(R.id.btn_record_text);
		btnVideo = (Button) this.findViewById(R.id.btn_make_video);
		btnRecorde = (Button) this.findViewById(R.id.btn_recording);
		btnFinish = (Button) this.findViewById(R.id.btn_finish);
		btnTakeVr = (Button) this.findViewById(R.id.btn_take_vr);
		btnImages = (Button) this.findViewById(R.id.btn_select_photo);
		tvTitleName = (TextView) this.findViewById(R.id.tv_title_name);
		attachmentContainer = (LinearLayout) this.findViewById(R.id.attachments_container);
		// 设置监听器
		btnPhoto.setOnClickListener(orcl);
		btnText.setOnClickListener(orcl);
		btnRecorde.setOnClickListener(orcl);
		btnVideo.setOnClickListener(orcl);
		btnFinish.setOnClickListener(orcl);
		btnTakeVr.setOnClickListener(orcl);
		btnImages.setOnClickListener(orcl);
	}

	private void inputText(final String filepath, final boolean update, final TextView textTV) {
		final DialogUtil dialogUtil = new DialogUtil(activity);
		View view = View.inflate(activity, R.layout.dialog_text_input, null);
		final EditText textET = (EditText) view.findViewById(R.id.et_text);
		if (update) {
			textET.setText(FileUtil.readUTF8(filepath));
		}
		view.findViewById(R.id.btn_cancle).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogUtil.dismissDialog();
				dialogUtil.destroy();
			}
		});
		view.findViewById(R.id.btn_sure).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String text = textET.getText().toString();
				if (TextUtils.isEmpty(text)) {
					ToastUtils.show(activity, "请输入文本内容");
					return;
				}
				FileUtil.saveUTF8(filepath, text);
				dialogUtil.dismissDialog();
				dialogUtil.destroy();
				if (!update) {
					addAttachmentInContainer(filepath, REQUEST_CODE_TEXT);
				} else {
					if (null != textTV) {
						textTV.setText(text);
					}
				}
			}
		});

		if (attrs.isJustShow) {
			view.findViewById(R.id.btn_sure).setVisibility(View.GONE);
			textET.setEnabled(false);
			((TextView) view.findViewById(R.id.tv_title)).setText("文本详情");
		}
		dialogUtil.showAlertDialog(view);
	}

	// 判断是否过量
	private boolean isOverDose(int id) {
		if (result == null) {
			return true;
		}
		if (id == R.id.btn_take_photo) {
			// 拍照
			if (result.photoFilePathList != null && result.photoFilePathList.size() >= attrs.maxPhoto) {
				ToastUtils.show(activity, "照片数量已经到达最大限制啦！");
				return true;
			}
		} else if (id == R.id.btn_record_text) {
			// 文本
			if (result.textFilePathList != null && result.textFilePathList.size() >= attrs.maxText) {
				ToastUtils.show(activity, "文本数量已经到达最大限制啦！");
				return true;
			}
		} else if (id == R.id.btn_recording) {
			// 录音
			if (result.voiceFilePathList != null && result.voiceFilePathList.size() >= attrs.maxVoice) {
				ToastUtils.show(activity, "录音数量已经到达最大限制啦！");
				return true;
			}
		} else if (id == R.id.btn_make_video) {
			// 视频
			if (result.videoFilePathList != null && result.videoFilePathList.size() >= attrs.maxVideo) {
				ToastUtils.show(activity, "视频数量已经到达最大限制啦！");
				return true;
			}
		} else if (id == R.id.btn_take_vr) {
			// VR
			if (result.vrFilePathList != null && result.vrFilePathList.size() >= attrs.maxVr) {
				ToastUtils.show(activity, "vr数量已经到达最大限制啦！");
				return true;
			}
		}
		return false;
	}

	private OnClickListener orcl = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mAttachmentViewClickListener!=null) {
				mAttachmentViewClickListener.onClick(v);
			}
			if (null == attrs) {
				ToastUtils.show(getContext(), "请初始化附件采集组件");
				return;
			}
			if (null == activity) {
				ToastUtils.show(getContext(), "请设置Activity");
				return;
			}
			if (isOverDose(v.getId())) {
				return;
			}
			final String filename = null == attrs.prefxName ? "" : attrs.prefxName;
			if (v.getId() == R.id.btn_take_photo) {
				// 拍照
				String tmpFileDir=attrs.photoDir;
				String tmpFileName=filename + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".jpg";
				PhotoUtils.takePicture(tmpFileDir,
						tmpFileName, activity,
						REQUEST_CODE_PHOTO);
				nowFilePath = tmpFileDir + "/" + tmpFileName;
			} else if (v.getId() == R.id.btn_record_text) {
				// 文本
				String filepath = attrs.textDir + "/" + filename + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss")
						+ ".txt";
				inputText(filepath, false, null);
				nowFilePath = filepath;
			} else if (v.getId() == R.id.btn_recording) {
				// 录音
				nowFilePath = attrs.voiceDir + "/" + filename + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss")
						+ ".mp3";
				if (null == recorderDialog) {
					recorderDialog = new RecorderDialog(activity, nowFilePath);
					recorderDialog.setRecordeResult(new OnRecordeResultListener() {
						@Override
						public void onFinish(String filepath) {
							addAttachmentInContainer(filepath, REQUEST_CODE_RECORDE);
						}

						@Override
						public void onError(String error) {
							nowFilePath = null;
						}
					});
				} else {
					recorderDialog.setOutPath(nowFilePath);
				}
				recorderDialog.show();
			} else if (v.getId() == R.id.btn_make_video) {
				// 视频
				File file = new File(attrs.videoDir,
						filename + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".mp4");
				VideoUtil.makeVideo(activity, REQUEST_CODE_VEDIO, file);
				nowFilePath = file.getAbsolutePath();
			} else if (v.getId() == R.id.btn_take_vr) {// vr
				String tmpFileName = System.currentTimeMillis() + "_VR.vr";
				String vrPath = attrs.vrDir + "/" + tmpFileName;
				if (!RicohUtil.openRicohAPP(activity)) {
					return;
				}
				long openRicohTime = System.currentTimeMillis();// 打开全景的时间
				Panoramic panoramic = new Panoramic();
				panoramic.setFilePath(vrPath);
				panoramic.setOpenRicohTime(openRicohTime);
				attrs.panoramic = panoramic;// 保存当前拍照的全景对象
			} else if (v.getId() == R.id.btn_finish) {
				if (null != resultListener) {
					resultListener.onAttachmentResult(result);
				}
			} else if (v.getId() == R.id.btn_select_photo) {
				// 选择图库
				Intent intent = new Intent(activity, PhotoSelectorActivity.class);
				intent.putExtra(PhotoSelectorActivity.KEY_MAX, attrs.maxPhoto);
				activity.startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGES);
			}
		}
	};

	private OnAttachmentViewClickListener mAttachmentViewClickListener;

	public void setOnAttachmentViewClickListener(OnAttachmentViewClickListener onAttachmentViewClickListener) {
		this.mAttachmentViewClickListener = onAttachmentViewClickListener;
	}

	/**
	 * 附件组件按钮点击监听器
	 * 
	 * @author zgq
	 *
	 */
	public interface OnAttachmentViewClickListener {
		void onClick(View v);
	}

	/**
	 * 因为使用的是系统拍照和录像API，需要乃至Activity返回结果回调
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			// 如果结果码不是RESULT_OK，则方法返回，不再向下执行后面的代码
			if (requestCode == REQUEST_CODE_PHOTO) {
				ToastUtils.show(activity, "拍照取消");
			}
			if (requestCode == REQUEST_CODE_VEDIO) {
				ToastUtils.show(activity, "录像取消");
			}
			return;
		}
		if (requestCode == REQUEST_CODE_SELECT_IMAGES) {
			@SuppressWarnings("unchecked")
			ArrayList<PhotoModel> photomodelList = (ArrayList<PhotoModel>) data.getSerializableExtra("photos");
			if (null == photomodelList || photomodelList.isEmpty()) {
				return;
			}
			// 取第一张图片放入容器中
			for (PhotoModel modle : photomodelList) {
				String originalPath = modle.getOriginalPath();
				String tagerFilePath = attrs.photoDir + "/selectImg_" + UUIDGen.randomUUID()
						+ originalPath.substring(originalPath.length() - 4, originalPath.length());
				FileUtil.copy(originalPath, tagerFilePath);
				addAttachmentInContainer(tagerFilePath, REQUEST_CODE_SELECT_IMAGES);
			}
		} else {
			if (null == nowFilePath) {
				return;
			}
			if (requestCode == REQUEST_CODE_PHOTO || requestCode == REQUEST_CODE_VEDIO) {
				File file = new File(nowFilePath);
				if (!file.exists()) {
					ToastUtils.show(activity, "文件已经被删除了");
					return;
				}
			}
			if (requestCode == REQUEST_CODE_PHOTO) {
				// 拍照
				addAttachmentInContainer(nowFilePath, REQUEST_CODE_PHOTO);
				// 写入GPS坐标到图片中
				try {
					Location location = GpsUtil.getLocation(activity);
					if (null != location) {
						writeLocationInImage(nowFilePath, location.getLongitude(), location.getLatitude());
						// ToastUtils.show(activity, "写入位置信息成功");
					} else {
						// ToastUtils.show(activity, "GPS异常");
					}
				} catch (Exception e) {
					// ToastUtils.show(activity, "为了使照片记录位置信息，请允许程序使用GPS服务");
					e.printStackTrace();
				}
			} else if (requestCode == REQUEST_CODE_VEDIO) {
				// 录像
				addAttachmentInContainer(nowFilePath, REQUEST_CODE_VEDIO);
			}
		}
	}

	/**
	 * 把位置信息写入到图片中
	 * 
	 * @param imagePath
	 * @param longtitude
	 * @param latitude
	 */
	private void writeLocationInImage(String imagePath, double longtitude, double latitude) {
		try {
			// 获取图片前缀
			ExifInterface exif = new ExifInterface(imagePath);
			// 写入经度信息
			exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, longtitude + "");
			exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, longtitude > 0 ? "E" : "W");
			// 写入纬度信息
			exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, latitude + "");
			exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, latitude > 0 ? "N" : "S");
			// 执行保存
			exif.saveAttributes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private RecorderAndPlayUtil voicePlayUtil;
	private VoicePlayAnimation playAnimation;

	// 附件容器中的附件被点击事件
	OnClickListener attachListener = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			String filepath = ((FilePath) v.getTag()).filePath;
			if (filepath == null) {
				return;
			}
			if (filepath.lastIndexOf(".mp3") >= 0) {
				if (playAnimation != null && !playAnimation.stop) {
					return;
				}
				if (null == voicePlayUtil) {
					voicePlayUtil = new RecorderAndPlayUtil(null);
				}
				playAnimation = new VoicePlayAnimation((ImageView) v);
				voicePlayUtil.setOnPlayerCompletionListener(new onPlayerCompletionListener() {
					@Override
					public void onPlayerCompletion() {
						playAnimation.stop();
					}
				});
				voicePlayUtil.startPlaying(filepath);
				playAnimation.start();
			} else if (filepath.lastIndexOf(".mp4") >= 0) {
				VideoUtil.openVideo(activity, new File(filepath));
			} else if (filepath.lastIndexOf(".jpg") >= 0) {
				PhotoUtil.openPhoto(activity, filepath);
			} else if (filepath.lastIndexOf(".JPG") >= 0) {
				PhotoUtil.openPhoto(activity, filepath);
			} else if (filepath.lastIndexOf(".png") >= 0) {
				PhotoUtil.openPhoto(activity, filepath);
			} else if (filepath.lastIndexOf(".txt") >= 0) {
				// 修改文本内容
				inputText(filepath, true, (TextView) v);
			} else if (filepath.lastIndexOf(".vr") >= 0) {// vr
				// 预览vr
				AndroidBasicComponentUtils.launchActivity(activity, LookVRImageActivity.class, "vr-image-path",
						filepath);
			}
		}
	};

	static class VoicePlayAnimation {
		ImageView imageView;
		int index = 0;
		boolean stop = false;
		Handler h = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (!stop) {
					if (index == 0) {
						imageView.setImageResource(R.drawable.paly_0);
					} else if (index == 1) {
						imageView.setImageResource(R.drawable.paly_1);
					} else if (index == 2) {
						imageView.setImageResource(R.drawable.paly_2);
					}
				}
			}
		};

		public VoicePlayAnimation(ImageView imageView) {
			this.imageView = imageView;
		}

		public void start() {
			new Thread() {
				public void run() {
					while (!stop) {
						try {
							Thread.sleep(300);
						} catch (Exception e) {
						}
						index++;
						if (index > 2) {
							index = 0;
						}
						h.sendEmptyMessage(index);
					}
				}
			}.start();
		}

		public void stop() {
			index = 0;
			stop = true;
			imageView.setImageResource(R.drawable.paly_0);
		}
	}

	/**
	 * 修改文件名
	 */
	OnClickListener attachNameListener = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			// 只是查看，不能修改
			if (attrs.isJustShow) {
				return;
			}
			final FilePath tag = (FilePath) v.getTag();
			final String filepath = tag.filePath;
			final String name = ((TextView) v).getText().toString();
			final DialogUtil dialogUtil = new DialogUtil(activity);
			View view = View.inflate(activity, R.layout.dialog_name_update, null);
			TextView titleTV = (TextView) view.findViewById(R.id.tv_title);
			final EditText nameET = (EditText) view.findViewById(R.id.et_name);
			nameET.setText(name);
			if (filepath.lastIndexOf(".mp3") >= 0) {
				titleTV.setText("修改音频文件文件名");
			} else if (filepath.lastIndexOf(".mp4") >= 0) {
				titleTV.setText("修改视频文件文件名");
			} else if (filepath.lastIndexOf(".jpg") >= 0) {
				titleTV.setText("修改图片文件文件名");
			} else if (filepath.lastIndexOf(".JPG") >= 0) {
				titleTV.setText("修改图片文件文件名");
			} else if (filepath.lastIndexOf(".txt") >= 0) {
				titleTV.setText("修改文本文件文件名");
			} else if (filepath.lastIndexOf(".vr") >= 0) {
				titleTV.setText("修改VR文件文件名");
			}
			view.findViewById(R.id.btn_cancle).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialogUtil.dismissDialog();
					dialogUtil.destroy();
				}
			});
			view.findViewById(R.id.btn_sure).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					String filename = nameET.getText().toString();
					if (TextUtils.isEmpty(filename)) {
						ToastUtils.show(activity, "文件名不能为空");
						return;
					}
					// 重命名
					String newFilePath = filepath.replace(name, filename);
					String newPath = newFilePath, oldPath = filepath;
					File sourceFile = new File(filepath);
					File toFile = new File(newFilePath);
					if (toFile.exists()) {
						ToastUtils.show(activity, "文件名已经存在，重命名失败");
						return;
					}
					if (!sourceFile.renameTo(toFile)) {
						ToastUtils.show(activity, "文件重命名失败");
						return;
					}
					// 事件监听器
					if (null != attachOptListener) {
						attachOptListener.onAttach(AttachOptionListener.OTP_RENAME, newPath, oldPath);
					}
					// 更新附件结果
					((TextView) v).setText(filename);
					tag.filePath = newFilePath;
					if (filepath.lastIndexOf(".mp3") >= 0) {
						result.voiceFilePathList.remove(filepath);
						result.voiceFilePathList.add(newFilePath);
					} else if (filepath.lastIndexOf(".mp4") >= 0) {
						result.videoFilePathList.remove(filepath);
						result.videoFilePathList.add(newFilePath);
					} else if (filepath.lastIndexOf(".jpg") >= 0) {
						result.photoFilePathList.remove(filepath);
						result.photoFilePathList.add(newFilePath);
					} else if (filepath.lastIndexOf(".JPG") >= 0) {
						result.photoFilePathList.remove(filepath);
						result.photoFilePathList.add(newFilePath);
					} else if (filepath.lastIndexOf(".txt") >= 0) {
						result.textFilePathList.remove(filepath);
						result.textFilePathList.add(newFilePath);
					} else if (filepath.lastIndexOf(".vr") >= 0) {
						result.vrFilePathList.remove(filepath);
						result.vrFilePathList.add(newFilePath);
					}
					dialogUtil.dismissDialog();
					dialogUtil.destroy();
				}
			});
			dialogUtil.showAlertDialog(view);
		}
	};

	static class FilePath {
		public String filePath;

		public FilePath(String filePath) {
			super();
			this.filePath = filePath;
		}

	}

	HashMap<String, View> viewmap = new HashMap<>();

	public void addOrRemoveView(ViewGroup viewGroup, String key, View view, boolean add) {
		if (add) {
			viewGroup.addView(view);
			viewmap.put(key, view);
		} else {
			if (null != view) {
				viewGroup.removeView(view);
			} else {
				viewGroup.removeView(viewmap.get(key));
			}
		}
	}

	/**
	 * 添加附件缩列图到容器中
	 * 
	 * @param attachFile
	 * @param attachcode
	 */
	public void addAttachmentInContainer(final String attachFile, final int attachcode) {
		View view = null;
		final FilePath path = new FilePath(attachFile);
		if (attachcode == REQUEST_CODE_PHOTO || attachcode == REQUEST_CODE_SELECT_IMAGES) {// 拍照
			view = View.inflate(getContext(), R.layout.layout_attachment_photo_item, null);
			ImageView imageIV = (ImageView) view.findViewById(R.id.data);
			imageIV.setImageBitmap(ImageHelper.decodeSampledBitmapFromImagePath(attachFile, 120, 80));
			imageIV.setTag(path);
			imageIV.setOnClickListener(attachListener);
		} else if (attachcode == REQUEST_CODE_RECORDE) {// 录音
			view = View.inflate(getContext(), R.layout.layout_attachment_voice_item, null);
			ImageView imageIV = (ImageView) view.findViewById(R.id.data);
			imageIV.setTag(path);
			imageIV.setOnClickListener(attachListener);
		} else if (attachcode == REQUEST_CODE_TEXT) {// 文本
			view = View.inflate(getContext(), R.layout.layout_attachment_text_item, null);
			EditText textET = (EditText) view.findViewById(R.id.data);
			textET.setText(FileUtil.readUTF8(attachFile));
			textET.setTag(path);
			textET.setOnClickListener(attachListener);
		} else if (attachcode == REQUEST_CODE_VEDIO) {// 录像
			view = View.inflate(getContext(), R.layout.layout_attachment_video_item, null);
			ImageView imageIV = (ImageView) view.findViewById(R.id.data);
			Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(attachFile, Images.Thumbnails.MINI_KIND);
			if (null != bitmap) {
				bitmap = ThumbnailUtils.extractThumbnail(bitmap, 120, 80);
				imageIV.setImageBitmap(bitmap);
			}
			imageIV.setTag(path);
			imageIV.setOnClickListener(attachListener);
		} else if (attachcode == REQUEST_CODE_VR) {// VR
			view = View.inflate(getContext(), R.layout.layout_attachment_vr_item, null);
			ImageView imageIV = (ImageView) view.findViewById(R.id.data);
			imageIV.setImageBitmap(ImageHelper.decodeSampledBitmapFromImagePath(attachFile, 120, 80));
			imageIV.setTag(path);
			imageIV.setOnClickListener(attachListener);
		}
		final TextView nameTV = (TextView) view.findViewById(R.id.name);
		nameTV.setText("" + attachFile.substring(attachFile.lastIndexOf("/") + 1, attachFile.lastIndexOf(".")));
		nameTV.setTag(path);
		nameTV.setOnClickListener(attachNameListener);
		// 只是查看
		if (attrs.isJustShow) {
			view.findViewById(R.id.filedelete).setVisibility(View.GONE);
		}
		// 删除代码
		view.findViewById(R.id.filedelete).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (attrs.isJustShow) {
					// 只是查看，不能修改
					return;
				}
				final DialogUtil d = new DialogUtil(activity);
				d.showAlertDialog(nameTV.getText().toString(), new String[] { "删除", "取消" },
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (which == 0) {
									addOrRemoveAttachmentInResult(path.filePath, attachcode, false);
									addOrRemoveView(attachmentContainer, attachFile, null, false);
								}
								d.dismissDialog();
							}
						}, true);
			}
		});
		addOrRemoveView(attachmentContainer, attachFile, view, true);
		addOrRemoveAttachmentInResult(attachFile, attachcode, true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	boolean isAddExist = false;

	// 添加附件到结果容器中或者将附件从件容器中移除
	void addOrRemoveAttachmentInResult(String attachFile, int attachcode, boolean add) {
		if (!add) {
			FileUtil.deleteFileByFilePath(attachFile);
		}
		if (attachcode == REQUEST_CODE_PHOTO) {// 拍照
			if (add) {
				if (null == result.photoFilePathList) {
					result.photoFilePathList = new ArrayList<>();
				}
				if (!result.photoFilePathList.contains(attachFile)) {
					result.photoFilePathList.add(attachFile);
				}
			} else {
				if (null == result.photoFilePathList) {
					return;
				}
				result.photoFilePathList.remove(attachFile);
			}
		} else if (attachcode == REQUEST_CODE_RECORDE) {// 录音
			if (add) {
				if (null == result.voiceFilePathList) {
					result.voiceFilePathList = new ArrayList<>();
				}
				if (!result.voiceFilePathList.contains(attachFile)) {
					result.voiceFilePathList.add(attachFile);
				}
			} else {
				if (null == result.voiceFilePathList) {
					return;
				}
				result.voiceFilePathList.remove(attachFile);
			}
		} else if (attachcode == REQUEST_CODE_TEXT) {// 文本
			if (add) {
				if (null == result.textFilePathList) {
					result.textFilePathList = new ArrayList<>();
				}
				if (!result.textFilePathList.contains(attachFile)) {
					result.textFilePathList.add(attachFile);
				}
			} else {
				if (null == result.textFilePathList) {
					return;
				}
				result.textFilePathList.remove(attachFile);
			}
		} else if (attachcode == REQUEST_CODE_VEDIO) {// 录像
			if (add) {
				if (null == result.videoFilePathList) {
					result.videoFilePathList = new ArrayList<>();
				}
				if (!result.videoFilePathList.contains(attachFile)) {
					result.videoFilePathList.add(attachFile);
				}
			} else {
				if (null == result.videoFilePathList) {
					return;
				}
				result.videoFilePathList.remove(attachFile);
			}
		} else if (attachcode == REQUEST_CODE_VR) {// VR
			if (add) {
				if (null == result.vrFilePathList) {
					result.vrFilePathList = new ArrayList<>();
				}
				if (!result.vrFilePathList.contains(attachFile)) {
					result.vrFilePathList.add(attachFile);
				}
			} else {
				if (null == result.vrFilePathList) {
					return;
				}
				result.vrFilePathList.remove(attachFile);
			}
		} else if (attachcode == REQUEST_CODE_SELECT_IMAGES) {// 选择图片
			if (add) {
				if (null == result.selectImgFilePathList) {
					result.selectImgFilePathList = new ArrayList<>();
				}
				if (!result.selectImgFilePathList.contains(attachFile)) {
					result.selectImgFilePathList.add(attachFile);
				}
			} else {
				if (null == result.selectImgFilePathList) {
					return;
				}
				result.selectImgFilePathList.remove(attachFile);
			}
		}
		// 事件
		if (null != attachOptListener && !isAddExist) {
			if (add) {
				attachOptListener.onAttach(AttachOptionListener.OPT_ADD, attachFile, null);
			} else {
				attachOptListener.onAttach(AttachOptionListener.OPT_DELETE, null, attachFile);
			}
		}
	}

	/**
	 * 附件采集结果
	 * 
	 * @author mr-lao
	 *
	 */
	public static class AttachmentResult {
		public List<String> textFilePathList;
		public List<String> photoFilePathList;
		public List<String> videoFilePathList;
		public List<String> voiceFilePathList;
		public List<String> vrFilePathList;
		public List<String> selectImgFilePathList;
	}

	/**
	 * 附件采集回调 每当拍完一张照片，或者一段视频，或者一段录音，或者记录一段文本，监听器就会回调一次，每次回调数据结果集都包含了之前采集的附件。
	 * 
	 * @author mr-lao
	 *
	 */
	public interface OnAttachmentResultListener {
		public void onAttachmentResult(AttachmentResult result);
	}

	/**
	 * 调用者通过此方法将已经存在的附件添加到组件中，使用组件的功能进行编辑
	 * 注意：附件的后缀名必须为（.txt、.mp4、.mp3、.jpg、.vr）其中的一种
	 * 
	 * @param attachFiles
	 */
	public void addExistsAttachments(ArrayList<String> attachFiles) {
		if (null == attachFiles || attachFiles.isEmpty()) {
			return;
		}
		isAddExist = true;
		for (String filepath : attachFiles) {
			if (filepath.lastIndexOf(".mp3") >= 0) {
				addAttachmentInContainer(filepath, REQUEST_CODE_RECORDE);
			} else if (filepath.lastIndexOf(".mp4") >= 0) {
				addAttachmentInContainer(filepath, REQUEST_CODE_VEDIO);
			} else if (filepath.lastIndexOf(".jpg") >= 0) {
				addAttachmentInContainer(filepath, REQUEST_CODE_PHOTO);
			} else if (filepath.lastIndexOf(".JPG") >= 0) {
				addAttachmentInContainer(filepath, REQUEST_CODE_PHOTO);
			} else if (filepath.lastIndexOf(".png") >= 0) {
				addAttachmentInContainer(filepath, REQUEST_CODE_PHOTO);
			} else if (filepath.lastIndexOf(".txt") >= 0) {
				addAttachmentInContainer(filepath, REQUEST_CODE_TEXT);
			} else if (filepath.lastIndexOf(".vr") >= 0) {
				addAttachmentInContainer(filepath, REQUEST_CODE_VR);
			}
		}
		isAddExist = false;
	}

	/**
	 * 附件采集完毕，手动调用结果回调器通知调用者
	 */
	public void finish() {
		if (resultListener != null) {
			resultListener.onAttachmentResult(result);
		}
	}

	/**
	 * 拍完全景照片之后，需要在调用者的onResume()方法中调用此方法
	 */
	public void onResume() {
		if (attrs != null && attrs.panoramic != null) {
			Panoramic panoramic = attrs.panoramic;
			if (panoramic.getOpenRicohTime() > 0) {
				try {
					RicohUtil.copyPicture(panoramic.getFilePath(), panoramic.getOpenRicohTime());
					if (!FileUtil.checkFilePathExists(panoramic.getFilePath())) {
						panoramic.setFilePath(null);
						return;
					}
					// 显示缩列图
					addAttachmentInContainer(panoramic.getFilePath(), REQUEST_CODE_VR);
				} catch (IOException e) {
					e.printStackTrace();
					panoramic.setFilePath(null);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					attrs.panoramic = null;
				}
			}
		}
	}
}
