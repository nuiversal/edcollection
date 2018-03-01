package com.winway.android.edcollection.test;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.CabinetOrBoxActivity;
import com.winway.android.edcollection.adding.activity.IntermediateHeadActivity;
import com.winway.android.edcollection.adding.activity.LableActivity;
import com.winway.android.edcollection.adding.activity.LineNameActivity;
import com.winway.android.edcollection.adding.activity.MarkerCollectActivity;
import com.winway.android.edcollection.adding.activity.DistributionRoomActivity;
import com.winway.android.edcollection.adding.activity.SubstationActivity;
import com.winway.android.edcollection.adding.activity.TowerActivity;
import com.winway.android.edcollection.adding.activity.TransformerActivity;
import com.winway.android.edcollection.adding.activity.WorkWellActivity;
import com.winway.android.edcollection.base.view.BaseControll;

import android.view.View;
import android.view.View.OnClickListener;

public class TestMainControll extends BaseControll<TestMainViewHolder> {

	@Override
	public void init() {
		viewHolder.getIntermediate().setOnClickListener(orcl);
		viewHolder.getLabel().setOnClickListener(orcl);
		viewHolder.getLinename().setOnClickListener(orcl);
		viewHolder.getTower().setOnClickListener(orcl);
		viewHolder.getTransformer().setOnClickListener(orcl);
		viewHolder.getWorkwell().setOnClickListener(orcl);
		viewHolder.getPower().setOnClickListener(orcl);
		viewHolder.getSub().setOnClickListener(orcl);
		viewHolder.getMark().setOnClickListener(orcl);
		viewHolder.getCabinet().setOnClickListener(orcl);
	}

	private OnClickListener orcl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button1:
				startActivity(LableActivity.class);
				break;
			case R.id.button2:
				startActivity(IntermediateHeadActivity.class);
				break;
			case R.id.button3:
				startActivity(TowerActivity.class);
				break;
			case R.id.button4:
				startActivity(TransformerActivity.class);
				break;
			case R.id.button5:
				startActivity(WorkWellActivity.class);
				break;
			case R.id.button6:
				startActivity(LineNameActivity.class);
				break;
			case R.id.sub:
				startActivity(SubstationActivity.class);
				break;
			case R.id.power:
				startActivity(DistributionRoomActivity.class);
				break;
			case R.id.cabinet:
				startActivity(CabinetOrBoxActivity.class);
				break;
			case R.id.mark:
				startActivity(MarkerCollectActivity.class);
				break;

			default:
				break;
			}

		}
	};
}
