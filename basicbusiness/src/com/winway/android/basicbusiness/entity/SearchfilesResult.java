package com.winway.android.basicbusiness.entity;

import java.util.List;

public class SearchfilesResult extends MessageBase {
	public static class Searchfile {
		private String id;
		private String appcode;
		private String ownercode;
		private String workno;
		private String filename;
		private Integer length;
		private String uploadtime;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getAppcode() {
			return appcode;
		}

		public void setAppcode(String appcode) {
			this.appcode = appcode;
		}

		public String getOwnercode() {
			return ownercode;
		}

		public void setOwnercode(String ownercode) {
			this.ownercode = ownercode;
		}

		public String getWorkno() {
			return workno;
		}

		public void setWorkno(String workno) {
			this.workno = workno;
		}

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public Integer getLength() {
			return length;
		}

		public void setLength(Integer length) {
			this.length = length;
		}

		public String getUploadtime() {
			return uploadtime;
		}

		public void setUploadtime(String uploadtime) {
			this.uploadtime = uploadtime;
		}

	}

	private List<Searchfile> files;

	public List<Searchfile> getFiles() {
		return files;
	}

	public void setFiles(List<Searchfile> files) {
		this.files = files;
	}

}
