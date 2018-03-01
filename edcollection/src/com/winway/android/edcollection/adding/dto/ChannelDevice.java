package com.winway.android.edcollection.adding.dto;

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
public class ChannelDevice {
	private EcChannelDgEntity ecChannelDgEntity;
	private EcChannelDlgEntity ecChannelDlgEntity;
	private EcChannelDlgdEntity ecChannelDlgdEntity;
	private EcChannelDlqEntity ecChannelDlqEntity;
	private EcChannelDlsdEntity ecChannelDlsdEntity;
	private EcChannelDlzmEntity ecChannelDlzmEntity;
	private EcChannelJkEntity ecChannelJkEntity;
	private EcChannelDlcEntity ecChannelDlcEntity;
	public void reset() {
		ecChannelDgEntity = null;
		ecChannelDlgEntity = null;
		ecChannelDlgdEntity = null;
		ecChannelDlqEntity = null;
		ecChannelDlsdEntity = null;
		ecChannelDlzmEntity = null;
		ecChannelJkEntity = null;
		ecChannelDlcEntity = null;
	}

	
	public EcChannelDlcEntity getEcChannelDlcEntity() {
		return ecChannelDlcEntity;
	}

	public void setEcChannelDlcEntity(EcChannelDlcEntity ecChannelDlcEntity) {
		this.ecChannelDlcEntity = ecChannelDlcEntity;
	}



	public EcChannelJkEntity getEcChannelJkEntity() {
		return ecChannelJkEntity;
	}

	public void setEcChannelJkEntity(EcChannelJkEntity ecChannelJkEntity) {
		this.ecChannelJkEntity = ecChannelJkEntity;
	}

	public EcChannelDgEntity getEcChannelDgEntity() {
		return ecChannelDgEntity;
	}

	public void setEcChannelDgEntity(EcChannelDgEntity ecChannelDgEntity) {
		this.ecChannelDgEntity = ecChannelDgEntity;
	}

	public EcChannelDlgEntity getEcChannelDlgEntity() {
		return ecChannelDlgEntity;
	}

	public void setEcChannelDlgEntity(EcChannelDlgEntity ecChannelDlgEntity) {
		this.ecChannelDlgEntity = ecChannelDlgEntity;
	}

	public EcChannelDlgdEntity getEcChannelDlgdEntity() {
		return ecChannelDlgdEntity;
	}

	public void setEcChannelDlgdEntity(EcChannelDlgdEntity ecChannelDlgdEntity) {
		this.ecChannelDlgdEntity = ecChannelDlgdEntity;
	}

	public EcChannelDlqEntity getEcChannelDlqEntity() {
		return ecChannelDlqEntity;
	}

	public void setEcChannelDlqEntity(EcChannelDlqEntity ecChannelDlqEntity) {
		this.ecChannelDlqEntity = ecChannelDlqEntity;
	}

	public EcChannelDlsdEntity getEcChannelDlsdEntity() {
		return ecChannelDlsdEntity;
	}

	public void setEcChannelDlsdEntity(EcChannelDlsdEntity ecChannelDlsdEntity) {
		this.ecChannelDlsdEntity = ecChannelDlsdEntity;
	}

	public EcChannelDlzmEntity getEcChannelDlzmEntity() {
		return ecChannelDlzmEntity;
	}

	public void setEcChannelDlzmEntity(EcChannelDlzmEntity ecChannelDlzmEntity) {
		this.ecChannelDlzmEntity = ecChannelDlzmEntity;
	}

}
