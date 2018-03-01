package com.winway.android.edcollection.base.bll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.util.NamingConventionsFloatWin;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.login.entity.EdpParamInfoEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EmCDataLogEntity;
import com.winway.android.ewidgets.input.InputComponent;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;

/**
 * 全局业务操作(全局db)
 * 
 * @author zgq
 *
 */
public class GlobalCommonBll extends BaseBll<Object> {

	public GlobalCommonBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * 根据参数类型编号获取字典列表
	 * 
	 * @param paramTypeNo
	 * @return
	 */
	public List<String> getDictionaryNameList(String paramTypeNo) {
		String expr = " param_type_no='" + paramTypeNo + "'";
		List<EdpParamInfoEntity> paramInfoList = this.queryByExpr2(EdpParamInfoEntity.class, expr);
		if (paramInfoList == null) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < paramInfoList.size(); i++) {
			list.add(paramInfoList.get(i).getParamName());
		}
		return list;
	}

	/**
	 * 根据参数获取字典健值
	 * @param key
	 * @return
	 */
	public EdpParamInfoEntity get(String key) {
		return findById(EdpParamInfoEntity.class, key);
	}
	/**
	 * 获取方向
	 * @return
	 */
	public List<String> getDirection(){
		List<String> dList = new ArrayList<>();
		dList.add("东");
		dList.add("南");
		dList.add("西");
		dList.add("北");
		return dList;
	}
	
	/**
	 * 显示命名规范悬浮框
	 * @param mActivity
	 * @param rlNameConventions
	 * @param inConMc
	 */
	public void showNamngConvientionsFloatWin(Activity mActivity,RelativeLayout rlNameConventions,InputComponent inConMc){
		NamingConventionsFloatWin.getInstance()
		.setNameConventionsTitle(mActivity.getString(R.string.channel_namingconventions));
NamingConventionsFloatWin.getInstance().setDirectionList(this.getDirection());
NamingConventionsFloatWin.getInstance().showNamingConvientionsFloatWin(mActivity,
		rlNameConventions, inConMc);
	}
	
	/**
	 * 获取通道类型名称
	 * @param valve
	 * @return
	 */
	public String getChannelTypeName(String valve){
		if(valve.equals("20106")){
			return ResourceEnum.DG.getName();
		}
		if(valve.equals("20101")){
			return ResourceEnum.MG.getName();
		}
		if(valve.equals("20102")){
			return ResourceEnum.GD.getName();
		}
		if(valve.equals("20103")){
			return ResourceEnum.SD.getName();
		}
		if(valve.equals("20104")){
			return ResourceEnum.QJ.getName();
		}
		if(valve.equals("20105")){
			return ResourceEnum.ZM.getName();
		}
		if(valve.equals("20110")){
			return ResourceEnum.JKXL.getName();
		}
		if(valve.equals("20112")){
			return ResourceEnum.DLC.getName();
		}
		return null;
	
		
		/*PG("20101", "排管"), // 排管
		MG("20101", "埋管"), // 埋管
		GD("20102", "沟道"), // 沟道
		SD("20103", "隧道"), // 隧道
		QJ("20104", "桥架"), // 桥架
		ZM("20105", "直埋"), // 直埋
		JKXL("20110", "架空"), // 架空线路
		TLG("20106", "拖拉管"), // 拖拉管
		DG("20106", "顶管"), // 顶管
		DLC("20112", "电缆槽"), // 电缆槽
*/		
	}
	
	public String getChannelTypeValue(String name){
		if(name.equals("顶管") || name.equals("拖拉管")){
			return ResourceEnum.DG.getValue();
		}
		if(name.equals("埋管") || name.equals("排管")){
			return ResourceEnum.MG.getValue();
		}
		if(name.equals("沟道")){
			return ResourceEnum.GD.getValue();
		}
		if(name.equals("隧道")){
			return ResourceEnum.SD.getValue();
		}
		if(name.equals("桥架")){
			return ResourceEnum.QJ.getValue();
		}
		if(name.equals("直埋")){
			return ResourceEnum.ZM.getValue();
		}
		if(name.equals("架空")){
			return ResourceEnum.JKXL.getValue();
		}
		if(name.equals("电缆槽")){
			return ResourceEnum.DLC.getValue();
		}
		return null;
	
		
		/*PG("20101", "排管"), // 排管
		MG("20101", "埋管"), // 埋管
		GD("20102", "沟道"), // 沟道
		SD("20103", "隧道"), // 隧道
		QJ("20104", "桥架"), // 桥架
		ZM("20105", "直埋"), // 直埋
		JKXL("20110", "架空"), // 架空线路
		TLG("20106", "拖拉管"), // 拖拉管
		DG("20106", "顶管"), // 顶管
		DLC("20112", "电缆槽"), // 电缆槽
*/		
	}
	

}
