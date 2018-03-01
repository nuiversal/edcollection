package com.winway.android.edcollection.base.dbVersion.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.base.dbVersion.IDbVersion;
import com.winway.android.edcollection.project.entity.CDbVersionEntity;
import com.winway.android.util.AndroidResUtils;
import com.winway.android.util.LogUtil;

import android.R.xml;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

/**
 * 版本变更实现
 * 
 * @author winway_zgq
 *
 */
public class DbVersionImpl implements IDbVersion {

	@Override
	public List<String> getAppDbVersions(Context context, String fileName) {
		// TODO Auto-generated method stub
		List<String> datas = new ArrayList<>();
		InputStream inputStream = null;
		try {
			XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
			inputStream = AndroidResUtils.getInputStreamFromAsset(context, fileName);
			xmlPullParser.setInput(inputStream, "UTF-8");
			int eventType = xmlPullParser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String tagName = xmlPullParser.getName();
					if (tagName.equals("version")) {
						String value = xmlPullParser.nextText();
						datas.add(value);
					}

				}
				eventType = xmlPullParser.next();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// 处理排序操作
		if (datas.size() > 0) {
			Collections.sort(datas);// 升序
		}
		return datas;
	}

	@Override
	public CDbVersionEntity getDbVersion(Context context, String dbUrl, String componentNo) {
		// TODO Auto-generated method stub
		BaseDBUtil baseDBUtil = new BaseDBUtil(context, dbUrl);
		try {
			baseDBUtil.createTableIfNotExist(CDbVersionEntity.class);
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "select * from C_DB_VERSION where COMPONENT_NO='" + componentNo
				+ "' order by VERSION_1,VERSION_2 desc";
		CDbVersionEntity entity = null;
		try {
			List<CDbVersionEntity> versions = baseDBUtil.excuteQuery(CDbVersionEntity.class, sql);
			if (versions != null && versions.size() > 0) {
				entity = versions.get(0);
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	public boolean handleUpgrade(Context context, String dbUrl, String appFileName, String sqlFoldRootPath,
			String componentNo) {
		// TODO Auto-generated method stub
		// 1、获取本地db的数据库版本
		CDbVersionEntity cDbVersion = getDbVersion(context, dbUrl, componentNo);
		if (cDbVersion == null) {
			return false;
		}
		// 2、获取应用中版本信息
		List<String> appVersions = getAppDbVersions(context, appFileName);
		if (appVersions == null || appVersions.size() < 1) {
			return false;
		}
		// 3、执行升级
		BaseDBUtil baseDBUtil = new BaseDBUtil(context, dbUrl);
		// 3.1 刷选出需要升级的脚本文件名
		List<String> needUpgradeVersions = new ArrayList<>();// 需要升级的版本
		for (int i = 0; i < appVersions.size(); i++) {
			String appVersion = appVersions.get(i);// 格式：4.20170726.1.sql
			String[] strArr = appVersion.split("\\.");
			Integer version1 = Integer.parseInt(strArr[0]);
			Integer version2 = Integer.parseInt(strArr[1]);
			if (cDbVersion.getVersion1() != version1) {
				continue;
			}
			if (cDbVersion.getVersion2() < version2) {// db文件的版本小于程序中版本
				needUpgradeVersions.add(appVersion);
			}
		}

		// 3.2 根据文件名，执行对应升级脚本
		/**
		 * sqls里面的item表示一个升级脚本文件
		 */
		List<String> sqls = new ArrayList<>();// 所有升级的sql语句
		if (needUpgradeVersions.size() < 1) {// 表示不需要升级
			return true;
		}
		for (int i = 0; i < needUpgradeVersions.size(); i++) {
			String sqlfileName = sqlFoldRootPath + needUpgradeVersions.get(i);
			InputStream inputStream = AndroidResUtils.getInputStreamFromAsset(context, sqlfileName);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer sBuffer = new StringBuffer();
			try {
				String sql = "";
				while ((sql = bufferedReader.readLine()) != null) {
					if (TextUtils.isEmpty(sql) || sql.startsWith("#") || sql.startsWith("--") || sql.startsWith("/*")) {
						continue;
					}
					sBuffer.append(sql);
				}
				sqls.add(sBuffer.toString().trim());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
		// 3.3 开启事务执行所有版本的脚本升级文件
		baseDBUtil.begingTransation();
		try {
			// 执行批量数据库操作
			for (String sqlsItem : sqls) {
				if (sqlsItem.isEmpty()) {
					continue;
				}
				String[] sqlsItemArr = sqlsItem.split(";");
				for (String sql : sqlsItemArr) {
					baseDBUtil.excuteSQL(sql);
				}
			}
			baseDBUtil.commitTransation();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} finally {
			baseDBUtil.endTransation();
		}

		// 4、修改db为最新版本
		String maxVersion = needUpgradeVersions.get(needUpgradeVersions.size() - 1);
		String[] maxStrArr = maxVersion.split("\\.");
		Integer maxVersion2 = Integer.parseInt(maxStrArr[1]);
		cDbVersion.setVersion2(maxVersion2);
		try {
			baseDBUtil.saveOrUpdate(cDbVersion);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
