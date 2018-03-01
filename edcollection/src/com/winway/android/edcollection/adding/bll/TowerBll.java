package com.winway.android.edcollection.adding.bll;

import android.content.Context;

import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.project.entity.EcTowerEntity;

public class TowerBll extends BaseBll<EcTowerEntity> {

	public TowerBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	public boolean saveSubstation(EcTowerEntity ecTowerEntity) {
		return this.save(ecTowerEntity);
	}

}
