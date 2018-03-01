package com.winway.android.edcollection.adding.dto;

import java.util.List;

import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;

/**
 * 通道设施
 * 
 * @author zgq
 *
 */
public class ChannelDevices {
	private List<EcChannelDgEntity> ecChannelDgEntities;
	private List<EcChannelDlgEntity> ecChannelDlgEntities;
	private List<EcChannelDlgdEntity> ecChannelDlgdEntities;
	private List<EcChannelDlqEntity> ecChannelDlqEntities;
	private List<EcChannelDlsdEntity> ecChannelDlsdEntities;
	private List<EcChannelDlzmEntity> ecChannelDlzmEntities;
	private List<EcChannelJkEntity> ecChannelJkEntities;
	private List<EcChannelDlcEntity> ecChannelDlcEntities;
	public void reset() {
		ecChannelDgEntities = null;
		ecChannelDlgEntities = null;
		ecChannelDlgdEntities = null;
		ecChannelDlqEntities = null;
		ecChannelDlsdEntities = null;
		ecChannelDlzmEntities = null;
		ecChannelJkEntities = null;
		ecChannelDlcEntities = null;
	}

	
	public List<EcChannelDlcEntity> getEcChannelDlcEntities() {
		return ecChannelDlcEntities;
	}


	public void setEcChannelDlcEntities(List<EcChannelDlcEntity> ecChannelDlcEntities) {
		this.ecChannelDlcEntities = ecChannelDlcEntities;
	}


	public List<EcChannelDgEntity> getEcChannelDgEntities() {
		return ecChannelDgEntities;
	}

	public void setEcChannelDgEntities(List<EcChannelDgEntity> ecChannelDgEntities) {
		this.ecChannelDgEntities = ecChannelDgEntities;
	}

	public List<EcChannelDlgEntity> getEcChannelDlgEntities() {
		return ecChannelDlgEntities;
	}

	public void setEcChannelDlgEntities(List<EcChannelDlgEntity> ecChannelDlgEntities) {
		this.ecChannelDlgEntities = ecChannelDlgEntities;
	}

	public List<EcChannelDlgdEntity> getEcChannelDlgdEntities() {
		return ecChannelDlgdEntities;
	}

	public void setEcChannelDlgdEntities(List<EcChannelDlgdEntity> ecChannelDlgdEntities) {
		this.ecChannelDlgdEntities = ecChannelDlgdEntities;
	}

	public List<EcChannelDlqEntity> getEcChannelDlqEntities() {
		return ecChannelDlqEntities;
	}

	public void setEcChannelDlqEntities(List<EcChannelDlqEntity> ecChannelDlqEntities) {
		this.ecChannelDlqEntities = ecChannelDlqEntities;
	}

	public List<EcChannelDlsdEntity> getEcChannelDlsdEntities() {
		return ecChannelDlsdEntities;
	}

	public void setEcChannelDlsdEntities(List<EcChannelDlsdEntity> ecChannelDlsdEntities) {
		this.ecChannelDlsdEntities = ecChannelDlsdEntities;
	}

	public List<EcChannelDlzmEntity> getEcChannelDlzmEntities() {
		return ecChannelDlzmEntities;
	}

	public void setEcChannelDlzmEntities(List<EcChannelDlzmEntity> ecChannelDlzmEntities) {
		this.ecChannelDlzmEntities = ecChannelDlzmEntities;
	}

	public List<EcChannelJkEntity> getEcChannelJkEntities() {
		return ecChannelJkEntities;
	}

	public void setEcChannelJkEntities(List<EcChannelJkEntity> ecChannelJkEntities) {
		this.ecChannelJkEntities = ecChannelJkEntities;
	}

}
