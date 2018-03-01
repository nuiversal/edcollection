package com.winway.android.edcollection.adding.controll;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.DeviceAdapter;
import com.winway.android.edcollection.adding.bll.DeviceQueryBll;
import com.winway.android.edcollection.adding.util.TableShowUtil;
import com.winway.android.edcollection.adding.viewholder.DeviceReadQueryViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.datacenter.service.OfflineAttachCenter;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.sensor.label.LabelReadActivity;
import com.winway.android.sensor.nfc.bluetoothnfc.BluetoothNFC_DK_Activity;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 设备读取查询
 * @author winway_cmx
 *
 */
public class DeviceReadQueryControll extends BaseControll<DeviceReadQueryViewHolder>{
	private int REQUEST_LABEL_ID2 = 0x21333334;//高频nfc
	private int REQUEST_LABEL_ID3 = 0x21333332;//高频蓝牙
	private int REQUEST_LABEL_ID  = 0x21333331;//超高频蓝牙 
	private List<EcLineLabelEntity> lineLableList;
	private DeviceQueryBll deviceQueryBll;
	private OfflineAttachCenter center;
	public static boolean isEdit=false;//新数据允许编辑状态，旧数据不允许有编辑状态
	@Override
	public void init() {
		// TODO Auto-generated method stub
		initDatas();
		initEvent();
	}

	private void initDatas() {
		String projectBDUrl = GlobalEntry.prjDbUrl;
		deviceQueryBll = new DeviceQueryBll(mActivity, FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db");
		center = new OfflineAttachCenter(mActivity, projectBDUrl);
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		viewHolder.getBtn_lable_read().setOnClickListener(ocl);
		viewHolder.getBtn_lable_search().setOnClickListener(ocl);
		viewHolder.getHc_lable_head().getReturnView().setOnClickListener(ocl);
		viewHolder.getHc_lable_head().getOkView().setOnClickListener(ocl);
		viewHolder.getBtn_uhflable_read().setOnClickListener(ocl);
		viewHolder.getLv_deviceList().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				EcLineLabelEntity ecLineLabelEntity = lineLableList.get(position);
				String oid = ecLineLabelEntity.getOid();
				if(oid==null){
					DeviceReadQueryControll.isEdit=false;
					TableShowUtil.showLabelDetailed(ecLineLabelEntity, center, mActivity);
				}else {
					DeviceReadQueryControll.isEdit=true;
					String expr = "OID = '" + oid + "'";
					List<EcNodeEntity> nodes = deviceQueryBll.queryByExpr2(EcNodeEntity.class, expr);
					EcNodeEntity ecNodeEntity = nodes.get(0);
					GlobalEntry.currentEditNode=ecNodeEntity;
					TableShowUtil.showLabelDetailed(ecLineLabelEntity, center, mActivity);
				}
			}
		});
	}
	OnClickListener ocl = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_hflable_read:{//高频读取
				if(hasNfc(mActivity)){ //高频手机带有nfc模块读取
					Intent intent = new Intent(mActivity,BluetoothNFC_DK_Activity.class);
					mActivity.startActivityForResult(intent, REQUEST_LABEL_ID2);
				}else {//高频蓝牙
					Intent intent = new Intent(mActivity, BluetoothNFC_DK_Activity.class);
					//intent.putExtra("isInDevelopment", true);
					mActivity.startActivityForResult(intent, REQUEST_LABEL_ID3);
				}
				
				break;
			}
			case R.id.btn_uhflable_read:{//超高频蓝牙
				AndroidBasicComponentUtils.launchActivityForResult(mActivity,
						LabelReadActivity.class, REQUEST_LABEL_ID);
				break;
			}
			case R.id.btn_lable_search:{//搜索
				String deviceID = viewHolder.getInconLableNo().getEdtTextValue();
				if(!TextUtils.isEmpty(deviceID)){
					 lineLableList = deviceQueryBll.getLineLable1(deviceID);
					if(lineLableList.size()>0 && lineLableList!=null){
						DeviceAdapter deviceAdapter = new DeviceAdapter(mActivity, lineLableList);
						viewHolder.getLv_deviceList().setAdapter(deviceAdapter);
					}else {
						ToastUtil.show(mActivity, "没有搜索到对应的设备信息", 200);
					}
				}else {
					ToastUtil.show(mActivity, "设备ID不能为null", 200);
				}
				break;
			}
			case R.id.tv_head_item_return:{
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok:{
				mActivity.finish();
				break;
			}
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			if(requestCode == REQUEST_LABEL_ID2){//高频nfc
				String rfcId = data.getStringExtra(BluetoothNFC_DK_Activity.RESULT_DATA_KEY);
				if(rfcId!=null){
					lineLableList = deviceQueryBll.getLineLable1(rfcId);
					EcLineLabelEntity ecLineLabelEntity = lineLableList.get(0);
					String oid = ecLineLabelEntity.getOid();
					if(oid==null){
						DeviceReadQueryControll.isEdit=false;
						TableShowUtil.showLabelDetailed(ecLineLabelEntity, center, mActivity);
					}else {
						DeviceReadQueryControll.isEdit=true;
						String expr = "OID = '" + oid + "'";
						List<EcNodeEntity> nodes = deviceQueryBll.queryByExpr2(EcNodeEntity.class, expr);
						EcNodeEntity ecNodeEntity = nodes.get(0);
						GlobalEntry.currentEditNode=ecNodeEntity;
						TableShowUtil.showLabelDetailed(ecLineLabelEntity, center, mActivity);
					}
				}
			}else if (requestCode ==  REQUEST_LABEL_ID3) {//高频蓝牙
				String rfcId = data.getStringExtra(BluetoothNFC_DK_Activity.RESULT_DATA_KEY);
				if(rfcId!=null){
					lineLableList = deviceQueryBll.getLineLable1(rfcId);
					EcLineLabelEntity ecLineLabelEntity = lineLableList.get(0);
					String oid = ecLineLabelEntity.getOid();
					if(oid==null){
						DeviceReadQueryControll.isEdit=false;
						TableShowUtil.showLabelDetailed(ecLineLabelEntity, center, mActivity);
					}else {
						DeviceReadQueryControll.isEdit=true;
						String expr = "OID = '" + oid + "'";
						List<EcNodeEntity> nodes = deviceQueryBll.queryByExpr2(EcNodeEntity.class, expr);
						EcNodeEntity ecNodeEntity = nodes.get(0);
						GlobalEntry.currentEditNode=ecNodeEntity;
						TableShowUtil.showLabelDetailed(ecLineLabelEntity, center, mActivity);
					}
				}
			}else if (requestCode == REQUEST_LABEL_ID) {
				String labelNo = data.getStringExtra(LabelReadActivity.INTENT_KEY_LABEL_ID);
				if(labelNo!=null){
					lineLableList = deviceQueryBll.getLineLable1(labelNo);
					EcLineLabelEntity ecLineLabelEntity = lineLableList.get(0);
					String oid = ecLineLabelEntity.getOid();
					if(oid==null){
						DeviceReadQueryControll.isEdit=false;
						TableShowUtil.showLabelDetailed(ecLineLabelEntity, center, mActivity);
					}else {
						DeviceReadQueryControll.isEdit=true;
						String expr = "OID = '" + oid + "'";
						List<EcNodeEntity> nodes = deviceQueryBll.queryByExpr2(EcNodeEntity.class, expr);
						EcNodeEntity ecNodeEntity = nodes.get(0);
						GlobalEntry.currentEditNode=ecNodeEntity;
						TableShowUtil.showLabelDetailed(ecLineLabelEntity, center, mActivity);
					}
				}
			}
		}
	}
	
	/**
	 * 判断nfc模块是否启用
	 * @param context
	 * @return
	 */
	public static boolean hasNfc(Context context){  
	    boolean bRet=false;  
	    if(context==null)  
	        return bRet;  
	    NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);  
	    NfcAdapter adapter = manager.getDefaultAdapter();  
	    if (adapter != null && adapter.isEnabled()) {  
	        // adapter存在，能启用  
	        bRet=true;  
	    }  
	    return bRet;  
	}
}
