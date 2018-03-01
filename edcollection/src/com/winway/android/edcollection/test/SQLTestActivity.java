package com.winway.android.edcollection.test;

import java.io.File;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.util.FileUtil;

import android.app.Activity;
import android.os.Bundle;

public class SQLTestActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String dburl = FileUtil.getInnerSDCardDir().getAbsolutePath() + "/winway_edcollection/db/project/CD_QTL.db";
		BaseDBUtil baseDBUtil = new BaseDBUtil(this, new File(dburl));
		//String SQL = "select * from ec_line as line,ec_line_label as label where label.line_no = line.ec_line_id and line.ec_line_id = '0ff6066db4dc46969ddaaf8cb9bc1661';";
		String SQL = "select * from ec_line as line,ec_line_label as label where line.ec_line_id = label.line_no";
		try {
			baseDBUtil.excuteSQL(EcLineEntity.class, EcLineLabelEntity.class, SQL);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
