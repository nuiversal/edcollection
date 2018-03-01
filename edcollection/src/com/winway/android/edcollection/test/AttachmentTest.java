package com.winway.android.edcollection.test;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.ewidgets.attachment.AttachmentAttrs;
import com.winway.android.ewidgets.attachment.AttachmentInit;
import com.winway.android.ewidgets.attachment.AttachmentView;
import com.winway.android.ewidgets.attachment.AttachmentView.AttachmentResult;
import com.winway.android.ewidgets.attachment.AttachmentView.OnAttachmentResultListener;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.FileUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AttachmentTest extends Activity {
	AttachmentView attach;

	// 已经存在的附件
	private ArrayList<String> exitsFiles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		attach = (AttachmentView) findViewById(R.id.attament);
		// attach.setActivity(this);
		AttachmentInit.initGloble(FileUtil.getInnerSDCardDir().getAbsolutePath() + "/winway-media/video",
				FileUtil.getInnerSDCardDir().getAbsolutePath() + "/winway-media/photo",
				FileUtil.getInnerSDCardDir().getAbsolutePath() + "/winway-media/voice",
				FileUtil.getInnerSDCardDir().getAbsolutePath() + "/winway-media/text",
				FileUtil.getInnerSDCardDir().getAbsolutePath() + "/winway-media/vr");
		AttachmentAttrs attrs = AttachmentAttrs.getSinggleAttr();
		// attrs.hasText = false;
		// attrs.hasVideo = false;
		attrs.activity = this;
		attrs.isJustShow = true;
		/*
		 * attrs.maxPhoto = 2; attrs.maxText = 2; attrs.maxVideo = 2;
		 * attrs.maxVoice = 2;
		 */
		attrs.hasFinish = false;
		attach.setAttachmentAttrs(attrs);
		attach.setOnResultListener(new OnAttachmentResultListener() {
			@Override
			public void onAttachmentResult(AttachmentResult result) {
				if (null == result) {
					return;
				}
				// 全部删了
				for (String path : result.photoFilePathList) {
					if (!exitsFiles.contains(path)) {
						// 增加了
					} else {
						// 没有作改变
						exitsFiles.remove(path);
					}
				}

				TextView textTV = new TextView(getApplication());
				StringBuilder buider = new StringBuilder();
				if (null != result.videoFilePathList) {
					for (String str : result.videoFilePathList) {
						buider.append(str);
					}
					buider.append("\n");
				}
				if (null != result.voiceFilePathList) {
					for (String str : result.voiceFilePathList) {
						buider.append(str);
					}
					buider.append("\n");
				}
				if (null != result.textFilePathList) {
					for (String str : result.textFilePathList) {
						buider.append(str);
					}
					buider.append("\n");
				}
				if (null != result.photoFilePathList) {
					for (String str : result.photoFilePathList) {
						buider.append(str);
					}
					buider.append("\n");
				}
				textTV.setText(buider.toString());
				DialogUtil d = new DialogUtil(AttachmentTest.this);
				d.showAlertDialog(textTV);
			}
		});

		ArrayList<String> attachFiles = new ArrayList<String>();
		attachFiles.add(FileUtil.getInnerSDCardDir().getAbsolutePath() + "/winway-media/video/视频.mp4");
		attachFiles.add(FileUtil.getInnerSDCardDir().getAbsolutePath() + "/winway-media/text/喝茶.txt");
		attachFiles.add(FileUtil.getInnerSDCardDir().getAbsolutePath() + "/winway-media/video/影视.mp4");
		attachFiles.add(FileUtil.getInnerSDCardDir().getAbsolutePath() + "/winway-media/photo/哈哈.jpg");
		attachFiles.add(FileUtil.getInnerSDCardDir().getAbsolutePath() + "/winway-media/voice/mh.mp3");
		// attach.addExistsAttachments(attachFiles);
		attach.addExistsAttachments(exitsFiles);
		Log.i("info", "hello logcat");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		attach.onActivityResult(requestCode, resultCode, data);
	}
}
