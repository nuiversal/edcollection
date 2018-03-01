package com.winway.android.basicbusiness.entity;

import java.io.Serializable;
import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 离线附件实体类
 * 
 * @author zgq
 * @since 2017/1/13 11:02:01
 */
@Table(name = "attach_recored")
public class AttachRecoredEntity implements Serializable {

	@Id
	@Column(column = "COM_UPLOAD_ID")
	private String comUploadId;// 附件ID
	@Column(column = "APP_CODE")
	private String appCode;// 应用编号
	@Column(column = "OWNER_CODE")
	private String ownerCode;// 所属表
	@Column(column = "WORK_NO")
	private String workNo;// 记录编号
	@Column(column = "FILE_NAME")
	private String fileName;// 文件名称
	@Column(column = "FILE_PATH")
	private String filePath;// 存储路径
	@Column(column = "IS_UPLOADED")
	private Integer isUploaded;// 是否上传

	public String getComUploadId() {
		return comUploadId;
	}

	public void setComUploadId(String comUploadId) {
		this.comUploadId = comUploadId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getIsUploaded() {
		return isUploaded;
	}

	public void setIsUploaded(Integer isUploaded) {
		this.isUploaded = isUploaded;
	}

}
