package com.winway.android.edcollection.project.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;

@Table(name = "ec_work_well")
public class EcWorkWellEntityVo extends EcWorkWellEntity implements Serializable {

	// 不入库
	@Transient
	private List<OfflineAttach> comUploadEntityList;
	private ArrayList<EcWorkWellCoverEntity> jgEntityList;
	private EcLineLabelEntityVo ecLineLabelEntityVo;
	
	public EcLineLabelEntityVo getEcLineLabelEntityVo() {
		return ecLineLabelEntityVo;
	}

	public void setEcLineLabelEntityVo(EcLineLabelEntityVo ecLineLabelEntityVo) {
		this.ecLineLabelEntityVo = ecLineLabelEntityVo;
	}

	@Transient
	/**
	 * 操作标识
	 */
	private int operateMark = 0;// 0表示新增，默认新增；1表示编辑下的新增；2表示编辑下的编辑；3表示关联设备的操作

	public ArrayList<EcWorkWellCoverEntity> getJgEntityList() {
		return jgEntityList;
	}

	public void setJgEntityList(ArrayList<EcWorkWellCoverEntity> jgEntityList) {
		this.jgEntityList = jgEntityList;
	}

	public int getOperateMark() {
		return operateMark;
	}

	public void setOperateMark(int operateMark) {
		this.operateMark = operateMark;
	}

	public List<OfflineAttach> getComUploadEntityList() {
		return comUploadEntityList;
	}

	public void setComUploadEntityList(List<OfflineAttach> comUploadEntityList) {
		this.comUploadEntityList = comUploadEntityList;
	}
}
