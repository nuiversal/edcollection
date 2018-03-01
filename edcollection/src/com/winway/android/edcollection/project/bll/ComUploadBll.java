package com.winway.android.edcollection.project.bll;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.login.controll.SettingServerAddressControll;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.util.FileUtil;
import com.winway.android.util.SharedPreferencesUtils;

/**
 * 离线附件表
 * 
 * @author ly
 *
 */
public class ComUploadBll extends BaseBll<OfflineAttach> {

	public ComUploadBll(Context context, String dbUrl) {
		super(context, dbUrl);

	}

	/**
	 * 保存照片
	 */
	public void savePhotoData(OfflineAttach comUploadEntity) {
		saveOrUpdate(comUploadEntity);
	}

	
	/**
	 * 查询最后一条工井数据
	 * @param sql
	 * @return
	 */
	public EcWorkWellEntity queryTheLastWellData(){
		String sql = "select * from ec_work_well order by CJSJ DESC LIMIT 1";
		try {
			List<EcWorkWellEntity> ecWorkWellEntities = this.excuteQuery(EcWorkWellEntity.class, sql);
			if(ecWorkWellEntities!=null && ecWorkWellEntities.size()>0){
				return ecWorkWellEntities.get(0);
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查找未上传的照片
	 * 
	 * @return
	 */
	public List<OfflineAttach> findNotUploadPictures() {
		List<OfflineAttach> entites = new ArrayList<OfflineAttach>();
		String sql = " IS_UPLOADED = '" + WhetherEnum.NO.getValue() + "'";
		entites = this.queryByExpr(OfflineAttach.class, sql);
		return entites;
	}

	/**
	 * 修改状态
	 * 
	 * @param entity
	 * @param state
	 */
	public void updateUpload(OfflineAttach entity, Integer state) {
		entity.setIsUploaded(state);
		this.update(entity);
	}
	
	@SuppressLint("NewApi") 
	public String getRootPath(String rootPath,Activity mActivity) {
		GlobalEntry.useTfcard = Boolean
				.parseBoolean(SharedPreferencesUtils.get(mActivity, GlobalEntry.IS_USE_TFCARD, false).toString());
		if (GlobalEntry.useTfcard) {
			String path = SettingServerAddressControll.getTFCardPath(mActivity);
			if (TextUtils.isEmpty(path)) {// 判断tf卡是否存在
				rootPath = FileUtil.AppRootPath;
			}else {
				// 获取所有缓存目录
				File[] fs = mActivity.getExternalCacheDirs();
				// 匹配sd卡目录
				for (int i = 0; i < fs.length; i++) {
					String str = fs[i].getAbsolutePath();
					if (str.contains(path)) {
						path = str;// 找到路径了
						break;
					}
				}
				rootPath = path + File.separator;
			}
		}else {
			rootPath = FileUtil.AppRootPath;
		}
		return rootPath;
	}
}
