package com.winway.android.edcollection.datacenter.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.datacenter.entity.PageDataResult;
import com.winway.android.edcollection.datacenter.entity.edp.BaseOrgResult;
import com.winway.android.edcollection.datacenter.entity.edp.OrgResult;
import com.winway.android.edcollection.datacenter.entity.edp.OrgResult1;
import com.winway.android.edcollection.datacenter.entity.edp.UserResult;
import com.winway.android.edcollection.login.entity.EdpOrgInfoEntity;
import com.winway.android.edcollection.login.entity.LoginResult;
import com.winway.android.ewidgets.net.entity.ErrorType;
import com.winway.android.ewidgets.net.service.impl.BaseDataPackageLoader;
import com.winway.android.util.GsonUtils;
import android.text.TextUtils;

/**
 * 获取机构树
 * @author mr-lao
 *
 */
public class GetOrgTreeLoader extends BaseDataPackageLoader {
	private BaseBll<EdpOrgInfoEntity> bll;

	public GetOrgTreeLoader(BaseBll<EdpOrgInfoEntity> bll) {
		this.bll = bll;
	}

	@Override
	public boolean canProcessThis(String url, Map<String, String> params,
			Map<String, String> heades) {
		if (null != params && !params.isEmpty()) {
			String action = params.get("action");
			if ("getorgtree".equals(action)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void getRequestString(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		String orgid = params.get("orgid");
		String logic_sys_no = params.get("logic_sys_no");
		LoginResult vertify = new LoginResult();
		vertify.setLogicSysNo(logic_sys_no);
		vertify.setOrgid(orgid);
		PageDataResult<BaseOrgResult> getorgtree = getorgtree(vertify, params);
		if (getorgtree == null || getorgtree.getRows() == null || getorgtree.getRows().isEmpty()) {
			call.error(ErrorType.ERROR_PROCESS_RESPONSE,WayFrom.DATA_PACKAGE);
			return;
		}
		String json = GsonUtils.build().toJson(getorgtree);
		call.callBack(json,WayFrom.DATA_PACKAGE);
	}

	@Override
	public void getRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception {

	}

	@Override
	public void downLoadFile(String url, Map<String, String> params, Map<String, String> headers,
			String filepath, RequestCallBack<File> call) throws Exception {

	}

	/**
	 * 获取机构树
	 * @param vertify
	 * @param style
	 * @return
	 */
	public PageDataResult<BaseOrgResult> getorgtree(LoginResult vertify,
			Map<String, String> parms) {
		return getorgusers(vertify, parms, false);
	}

	private List<EdpOrgInfoEntity> query(String logicNo) {
		/*return bll.queryByExpr(EdpOrgInfoEntity.class,
				"logic_sys_no='" + logicNo + "' and org_sts='1'");*/
		return bll.queryByExpr(EdpOrgInfoEntity.class, "logic_sys_no='" + logicNo + "'");
	}

	private PageDataResult<BaseOrgResult> getorgusers(LoginResult vertify,
			Map<String, String> parms, boolean getuser) {
		String style = parms.get("style");
		PageDataResult<BaseOrgResult> result = new PageDataResult<BaseOrgResult>();

		String orgid = vertify.getOrgid();
		String Logic_sys_no = vertify.getLogicSysNo();

		List<BaseOrgResult> rows = Lists.newArrayList();
		// 机构中的用户
		Map<String, List<UserResult>> userOrgMap = new HashMap<String, List<UserResult>>();
		// 机构列表
		List<OrgResult> orgList = findOrgTree(orgid, Logic_sys_no, userOrgMap);

		if (orgList != null && !orgList.isEmpty()) {
			// style = "1";
			if ("1".equals(style)) {
				List<OrgResult1> rs1List = Lists.newArrayList();
				for (OrgResult r : orgList) {
					getByResult(rs1List, r, "0");
				}
				rows.addAll(rs1List);
			} else {
				rows.addAll(orgList);
			}
			result.setRows(rows);
			result.setTotal(rows.size());
			result.setCode(0);
			result.setMsg("");
		} else {
			result.setCode(-1);
			result.setMsg("无法获取机构树");
		}
		return result;
	}

	private void getByResult(List<OrgResult1> rs1List, OrgResult orgResult, String pid) {
		OrgResult1 rs1 = new OrgResult1(orgResult.getOrgid(), orgResult.getOrgname(),
				orgResult.getCustomno(), pid);
		rs1.setUserlist(orgResult.getUserlist());
		rs1List.add(rs1);
		if (orgResult.getList() != null) {
			for (OrgResult or : orgResult.getList()) {
				getByResult(rs1List, or, orgResult.getOrgid());
			}
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * 获取机构树
	 * @param baseOrgId 顶级orgId
	 * @param logicSysNo
	 * @param getuser 是否获取用户列表
	 * @param roleuserMap 用户和角色对照
	 * @return
	 */
	private List<OrgResult> findOrgTree(String baseOrgId, String logicSysNo,
			Map<String, List<UserResult>> userOrgMap) {
		List<OrgResult> result = Lists.newArrayList();
		// 获取所有机构信息
		List<EdpOrgInfoEntity> orgList = query(logicSysNo);
		if (orgList != null) {
			Map<String, List<OrgResult>> upMap = Maps.newHashMap();
			Map<String, OrgResult> orgMap = Maps.newHashMap();
			List<OrgResult> child;
			OrgResult or;
			for (EdpOrgInfoEntity o : orgList) {
				or = new OrgResult();
				or.setCustomno(o.getCustomNo());
				or.setOrgid(o.getOrgId());
				or.setOrgno(o.getOrgNo());
				or.setOrgname(o.getOrgName());
				or.setUserlist(userOrgMap.get(o.getOrgNo()));
				or.setMapCenter(o.getMapCenter());
				orgMap.put(o.getOrgNo(), or);
				child = upMap.get(o.getUpNo());
				if (child == null) {
					child = new ArrayList<OrgResult>();
					upMap.put(o.getUpNo(), child);
				}
				child.add(or);
				// 获取所有机构
				if (TextUtils.isEmpty(baseOrgId) || baseOrgId.equals("0")) {
					if (o.getUpNo().equals("0")) {
						result.add(or);
					}
				} else if (o.getOrgId().equals(baseOrgId)) {
					result.add(or);
				}
			}
			for (String oid : orgMap.keySet()) {
				or = orgMap.get(oid);
				if (upMap.containsKey(oid)) {
					or.setList(upMap.get(oid));
				} else {
					or.setList(Collections.EMPTY_LIST);
				}
			}
		}
		return result;
	}

}
