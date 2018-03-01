package com.winway.android.edcollection.adding.bll;

import android.content.Context;

import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;

public class TransformerBll extends BaseBll<EcTransformerEntity> {

	public TransformerBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	public boolean saveSubstation(EcTransformerEntity ecTransformerEntity) {
		return this.save(ecTransformerEntity);
	}

}
