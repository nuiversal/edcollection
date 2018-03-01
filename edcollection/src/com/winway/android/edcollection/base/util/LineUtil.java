package com.winway.android.edcollection.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.winway.android.edcollection.adding.util.SelectCollectObjPopWindow;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.util.ListUtil;

/**
 * 线路工具类
 * @author mr-lao
 *
 */
public class LineUtil {
	static final HashMap<String, EcLineEntity> linenameMap = new HashMap<>();
	static final HashMap<String, EcLineEntity> linenoMap = new HashMap<>();

	static {
		// 添加未知线路
		EcLineEntity lineEntity = new EcLineEntity();
		lineEntity.setName("未知线路");
		lineEntity.setEcLineId("UNKNOW-LINE");
		linenameMap.put("未知线路", lineEntity);
		linenoMap.put("UNKNOW-LINE", lineEntity);
	}

	public static void addLine(EcLineEntity line) {
		linenameMap.put(line.getName(), line);
		linenoMap.put(line.getEcLineId(), line);
	}

	public static void addLine(List<EcLineEntity> lineList) {
		for (EcLineEntity line : lineList) {
			addLine(line);
		}
	}

	public static EcLineEntity getByLinename(String linename) {
		return linenameMap.get(linename);
	}

	public static EcLineEntity getByLineId(String lineId) {
		return linenoMap.get(lineId);
	}

	public static EcLineEntity get(String linenameOrLineno) {
		if (linenameMap.containsKey(linenameOrLineno)) {
			return linenameMap.get(linenameOrLineno);
		}
		return linenoMap.get(linenameOrLineno);
	}

	public static ArrayList<String> getLinenoList() {
		Set<String> keySet = linenoMap.keySet();
		if (null == keySet || keySet.isEmpty()) {
			return null;
		}
		ArrayList<String> linenoList = new ArrayList<>();
		ListUtil.copyList(linenoList, keySet);
		return linenoList;
	}

	public static ArrayList<String> getLinenameList() {
		Set<String> keySet = linenameMap.keySet();
		if (null == keySet || keySet.isEmpty()) {
			return null;
		}
		ArrayList<String> linenameList = new ArrayList<>();
		ListUtil.copyList(linenameList, keySet);
		return linenameList;
	}

	/**
	 * 获取选中的线路
	 * @return
	 */
	public static List<EcLineEntity> getSelectedLineList() {
		return SelectCollectObjPopWindow.getWaitingLines();
	}

	/**
	 * 获取选中的线路编号
	 * @return
	 */
	public static List<String> getSelectedLinenoList() {
		List<EcLineEntity> lineList = getSelectedLineList();
		if (lineList == null) {
			return null;
		}
		ArrayList<String> noList = new ArrayList<>();
		for (EcLineEntity line : lineList) {
			noList.add(line.getLineNo());
		}
		return noList;
	}
}
