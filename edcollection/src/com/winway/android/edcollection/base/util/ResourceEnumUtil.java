package com.winway.android.edcollection.base.util;

import java.util.HashMap;

import com.winway.android.edcollection.base.entity.ResourceEnum;

public class ResourceEnumUtil {
	final static HashMap<String, ResourceEnum> map = new HashMap<String, ResourceEnum>();
	static {
		map.put(ResourceEnum.XL.getValue() + "", ResourceEnum.XL);
		map.put(ResourceEnum.BDZ.getValue() + "", ResourceEnum.BDZ);
		map.put(ResourceEnum.KGZ.getValue() + "", ResourceEnum.KGZ);
		map.put(ResourceEnum.PDS.getValue() + "", ResourceEnum.PDS);
		map.put(ResourceEnum.HWG.getValue() + "", ResourceEnum.HWG);
		map.put(ResourceEnum.XSBDZ.getValue() + "", ResourceEnum.XSBDZ);
		map.put(ResourceEnum.DLFZX.getValue() + "", ResourceEnum.DLFZX);
		map.put(ResourceEnum.DYDLFZX.getValue() + "", ResourceEnum.DYDLFZX);
		map.put(ResourceEnum.DYPDX.getValue() + "", ResourceEnum.DYPDX);
		map.put(ResourceEnum.BYQ.getValue() + "", ResourceEnum.BYQ);
		map.put(ResourceEnum.PG.getValue() + "", ResourceEnum.PG);
		map.put(ResourceEnum.GD.getValue() + "", ResourceEnum.GD);
		map.put(ResourceEnum.SD.getValue() + "", ResourceEnum.SD);
		map.put(ResourceEnum.QJ.getValue() + "", ResourceEnum.QJ);
		map.put(ResourceEnum.ZM.getValue() + "", ResourceEnum.ZM);
		map.put(ResourceEnum.DLC.getValue() + "", ResourceEnum.DLC);
		map.put(ResourceEnum.JKXL.getValue() + "", ResourceEnum.JKXL);
		map.put(ResourceEnum.TLG.getValue() + "", ResourceEnum.TLG);
		map.put(ResourceEnum.DLJ.getValue() + "", ResourceEnum.DLJ);
		map.put(ResourceEnum.JG.getValue() + "", ResourceEnum.JG);
		map.put(ResourceEnum.GT.getValue() + "", ResourceEnum.GT);
		map.put(ResourceEnum.ZJJT.getValue() + "", ResourceEnum.ZJJT);
		map.put(ResourceEnum.DZBZQ.getValue() + "", ResourceEnum.DZBZQ);
		map.put(ResourceEnum.DZBQ.getValue() + "", ResourceEnum.DZBQ);
		map.put(ResourceEnum.AJH.getValue() + "", ResourceEnum.AJH);
		map.put(ResourceEnum.TDBZ.getValue() + "", ResourceEnum.TDBZ);
		map.put(ResourceEnum.BZP.getValue() + "", ResourceEnum.BZP);
		map.put(ResourceEnum.TD.getValue() + "", ResourceEnum.TD);
		map.put(ResourceEnum.TDJM.getValue() + "", ResourceEnum.TDJM);
	}

	/**
	 * 设备的代码，比如，线路的代码为90101，code的值为90101则会返回线路的枚举
	 * @param code
	 * @return
	 */
	public static ResourceEnum get(String code) {
		return map.get(code);
	}

	public static ResourceEnum get(Integer code) {
		return get(code + "");
	}
}
