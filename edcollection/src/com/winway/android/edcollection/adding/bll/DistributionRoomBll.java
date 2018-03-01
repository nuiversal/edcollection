package com.winway.android.edcollection.adding.bll;

import android.content.Context;

import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;

public class DistributionRoomBll extends BaseBll<EcDistributionRoomEntity> {

	public DistributionRoomBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	public boolean saveSubstation(EcDistributionRoomEntity ecDistributionRoomEntity) {
		return this.save(ecDistributionRoomEntity);
	}

}
