package com.winway.android.edcollection.login.bll;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.baidu.a.a.a.a.c;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.dto.PageDataResult;
import com.winway.android.edcollection.login.dto.ParamInfoResult;
import com.winway.android.edcollection.login.dto.ParamTypeResult;
import com.winway.android.edcollection.login.entity.EdpOrgInfoEntity;
import com.winway.android.edcollection.login.entity.EdpParamInfoEntity;
import com.winway.android.edcollection.login.entity.EdpParamTypeInfoEntity;
import com.winway.android.edcollection.login.entity.LoginResult;
import com.winway.android.network.HttpUtils;
import com.winway.android.network.HttpUtils.CallBack;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.LogUtil;
import com.winway.android.util.NetWorkUtils;
import com.winway.android.util.ProgressUtils;
import com.winway.android.util.SharedPreferencesUtils;
import com.winway.android.util.ToastUtil;

import android.R.menu;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 获取edp相关数据
 * 
 * @author zgq
 *
 */
public class EdpBll extends BaseBll<Object> {

	public EdpBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取数据字典列表
	 * 
	 * @param context
	 */
	public void getParamList(final Context context) {
		if (!NetWorkUtils.isConnected(context)) {
			ToastUtil.show(context, "网络异常", 100);
			return;
		}
		final long currentTimeMillis = System.currentTimeMillis();
		ProgressUtils.getInstance().show("获取数据字典中...", false, context);
		String url = GlobalEntry.loginServerUrl + "edp?action=getdics&typeno=&appkey=" + "&typenos="
				+ getDictTypeNo(context) + "&credit=" + GlobalEntry.loginResult.getCredit();
		final int flagSuccess = 1;// 成功标识
		final int flagFail = 2;// 失败标识
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case flagSuccess: {
					long currentTimeMillis2 = System.currentTimeMillis();
					long time = currentTimeMillis2 - currentTimeMillis;
					LogUtil.i("info", "获取数据字典一共花费："+time+"毫秒");
					ToastUtil.show(context, "获取数据字典成功！", 200);
					ProgressUtils.getInstance().dismiss();
					break;
				}
				case flagFail: {
					ProgressUtils.getInstance().dismiss();
					ToastUtil.show(context, "获取数据字典失败！", 200);
					ProgressUtils.getInstance().dismiss();
					break;
				}

				default:
					break;
				}
			}
		};
		// 获取参数列表
		HttpUtils.doGet(url, new CallBack() {

			@Override
			public void onRequestComplete(String result) {
				// TODO Auto-generated method stub
				long currentTimeMillis = System.currentTimeMillis();
				Gson gson = GsonUtils.build();
				Type listType = new TypeToken<PageDataResult<ParamTypeResult>>() {
				}.getType();
				PageDataResult<ParamTypeResult> pageDataResult = gson.fromJson(result, listType);
				long currentTimeMillis2 = System.currentTimeMillis();
				long time = currentTimeMillis2 - currentTimeMillis;
				LogUtil.i("info", "json转实体花费："+time+"毫秒");
				List<ParamTypeResult> list = pageDataResult.getRows();
				// 入库
				saveParamsToDb(list);
				Message msg = new Message();
				msg.what = flagSuccess;
				handler.sendMessage(msg);
			}

			@Override
			public void onError(Exception exception) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = flagFail;
				handler.sendMessage(msg);
			}
		});
	}
	
	/**
	 * 获取字典的类型编号
	 * 
	 * @param context
	 * @return
	 */
	private String getDictTypeNo(Context context) {
		StringBuilder typeNo = new StringBuilder();
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(context.getAssets().open("dictionary_type_no.xml"));
			if (document == null) {
				return null;
			}
			Element element = document.getDocumentElement();
			NodeList nodeList = element.getElementsByTagName("typeNo");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element lan = (Element) nodeList.item(i);
				typeNo.append(lan.getAttribute("name") + "");
				typeNo.append(",");
			}
			typeNo.deleteCharAt(typeNo.length() - 1);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return typeNo.toString();
	}

	/**
	 * 保存数据字典到数据库
	 * 
	 * @param paramTypelist
	 */
	public void saveParamsToDb(List<ParamTypeResult> paramTypelist) {
		// TODO Auto-generated method stub
		if (paramTypelist == null || paramTypelist.size() < 1) {
			return;
		}
		long currentTimeMillis = System.currentTimeMillis();
		List<EdpParamTypeInfoEntity> paramTypeInfoList = new ArrayList<EdpParamTypeInfoEntity>();
		List<EdpParamInfoEntity> paramInfoList = new ArrayList<EdpParamInfoEntity>();
		for (int i = 0; i < paramTypelist.size(); i++) {
			ParamTypeResult paramTypeResult = paramTypelist.get(i);
			String typeNo = paramTypeResult.getParamTypeNo();
			EdpParamTypeInfoEntity edpParamTypeInfoEntity = new EdpParamTypeInfoEntity();
			edpParamTypeInfoEntity.setParamTypeNo(typeNo);
			edpParamTypeInfoEntity.setParamTypeName(paramTypeResult.getParamTypeName());
			edpParamTypeInfoEntity.setParamTypeId(paramTypeResult.getParamTypeId());
			paramTypeInfoList.add(edpParamTypeInfoEntity);
			List<ParamInfoResult> paramInfoEntities = paramTypeResult.getParams();
			if (paramInfoEntities == null || paramInfoEntities.size() < 1) {
				continue;
			}
			for (int j = 0; j < paramInfoEntities.size(); j++) {
				ParamInfoResult paramInfoResult = paramInfoEntities.get(j);
				EdpParamInfoEntity edpParamInfoEntity = new EdpParamInfoEntity();
				edpParamInfoEntity.setParamName(paramInfoResult.getParamName());
				edpParamInfoEntity.setParamId(paramInfoResult.getParamId());
				edpParamInfoEntity.setParamValue(paramInfoResult.getParamValue());
				edpParamInfoEntity.setOrderNo(paramInfoResult.getOrderNo());
				edpParamInfoEntity.setParamTypeNo(typeNo);
				paramInfoList.add(edpParamInfoEntity);
			}
		}
		
		long currentTimeMillis1 = System.currentTimeMillis();
		this.saveOrUpdate(paramTypeInfoList);
		this.saveOrUpdate(paramInfoList);
		long currentTimeMillis3 = System.currentTimeMillis();
		long time1 = currentTimeMillis3 - currentTimeMillis1;
		LogUtil.i("info", "数据字典保存到数据共花费："+time1+"毫秒");
	}

	/**
	 * 获取字典表中记录数
	 * 
	 * @return
	 */
	public int getParamsCount() {
		String expr = " 1=1";
		List<EdpParamTypeInfoEntity> paramTypeList = this.queryByExpr2(EdpParamTypeInfoEntity.class, expr);
		if (paramTypeList == null || paramTypeList.size() < 1) {
			return 0;
		}
		return paramTypeList == null ? 0 : paramTypeList.size();
	}

	/**
	 * 根据机构标识获取机构信息
	 * 
	 * @param OrgNo
	 * @return
	 */
	public EdpOrgInfoEntity getOrgInfoByOrgNo(String orgNo) {
		String expr = " org_no='" + orgNo + "'";
		List<EdpOrgInfoEntity> list = this.queryByExpr2(EdpOrgInfoEntity.class, expr);
		if (list == null || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 根据字典ID获取字典数据
	 * 
	 * @param paramTypeNo
	 * @param deptId
	 * @return
	 */
	public EdpParamInfoEntity getParamInfoByParamId(String paramId) {
		return this.findById(EdpParamInfoEntity.class, paramId);
	}
}
