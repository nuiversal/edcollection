package com.winway.android.edcollection.setting.entity;

import java.util.List;

import com.winway.android.edcollection.base.dto.MessageBase;
import com.winway.android.edcollection.project.entity.CDbVersionEntity;

/**
 * 服务端版本信息
 * 
 * @author winway_zgq
 *
 */
public class VersionInfos extends MessageBase {
	private List<CDbVersionEntity> versions;

	public List<CDbVersionEntity> getVersions() {
		return versions;
	}

	public void setVersions(List<CDbVersionEntity> versions) {
		this.versions = versions;
	}
}
