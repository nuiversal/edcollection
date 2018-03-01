package com.winway.android.edcollection.map;

import java.util.ArrayList;
import java.util.List;

import ocn.himap.base.HiCoordinate;
import ocn.himap.datamanager.HiBaseDataSet;
import ocn.himap.datamanager.HiDataManager;
import ocn.himap.datamanager.HiDataSetType;
import ocn.himap.datamanager.HiElement;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.base.util.CoordsUtils;
import com.winway.android.edcollection.map.entity.SpatialAnalysisDataType;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDypdxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcKgzEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.edcollection.project.vo.EcXsbdzEntityVo;
import com.winway.android.map.entity.MapCache;
import com.winway.android.map.util.GraphicsDrawUtils;

/**
 * 空间分析
 * 
 * @author zgq
 *
 */
public class SpatialAnalysis {
	private static SpatialAnalysis instance;
	/**
	 * 数据查询管理器
	 */
	public static HiDataManager hiDataManager = null;

	public static SpatialAnalysis getInstance() {
		if (instance == null) {
			synchronized (SpatialAnalysis.class) {
				if (instance == null) {
					instance = new SpatialAnalysis();
				}
			}
		}
		return instance;
	}

	/**
	 * 路径点数据集
	 */
	public static HiBaseDataSet pointDataset = new HiBaseDataSet();

	/**
	 * 线路数据集
	 */
	public static HiBaseDataSet lineDataset = new HiBaseDataSet();

	/**
	 * 添加数据集到数据管理器中
	 */
	public void loadDataset2DataManager() {
		if (hiDataManager == null) {
			hiDataManager = new HiDataManager();
		}
		// 路径点
		pointDataset.DataName = "point";
		pointDataset.DataType = HiDataSetType.Point;
		hiDataManager.addDataSet(pointDataset);

		// 线路
		lineDataset.DataName = "line";
		lineDataset.DataType = HiDataSetType.Line;
		hiDataManager.addDataSet(lineDataset);
	}

	/**
	 * 添加元素到数据集
	 * 
	 * @param spatialAnalysisDataType
	 * @param hiElement
	 */
	public void addElement2Dataset(SpatialAnalysisDataType spatialAnalysisDataType, HiElement hiElement) {
		if (spatialAnalysisDataType.equals(SpatialAnalysisDataType.point)) {
			pointDataset.add(hiElement);
		} else if (spatialAnalysisDataType.equals(SpatialAnalysisDataType.line)) {
			lineDataset.add(hiElement);
		}
	}

	/**
	 * 添加元素到数据集
	 * 
	 * @param spatialAnalysisDataType
	 * @param hiElement
	 * @param bussObj
	 */
	public void addElement2DatasetWithCheck(SpatialAnalysisDataType spatialAnalysisDataType, HiElement hiElement,
			Object bussObj) {
		if (spatialAnalysisDataType.equals(SpatialAnalysisDataType.point)) {
			if (isExistPointElement(hiElement.Text) == false) {// 不存在，则新增
				pointDataset.add(hiElement);
			} else {// 存在，则编辑
				if (pointDatasetIndex != -1) {
					HiElement pHiElement = pointDataset.get(pointDatasetIndex);
					if (bussObj instanceof EcNodeEntity) {
						EcNodeEntity ecNodeEntity = (EcNodeEntity) bussObj;
						pHiElement.DataReference = ecNodeEntity;
						double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
						pHiElement.Crds.clear();
						pHiElement.Crds.add(new HiCoordinate(xy[0], xy[1]));
					} else {
						pHiElement.DataReference = bussObj;
					}

				}

			}

		} else if (spatialAnalysisDataType.equals(SpatialAnalysisDataType.line)) {
			lineDataset.add(hiElement);
		}

	}

	/**
	 * 点数据集索引
	 */
	private int pointDatasetIndex = -1;

	/**
	 * 是否存在点元素
	 * 
	 * @param id
	 * @return
	 */
	private boolean isExistPointElement(String id) {
		if (pointDataset.size() > 0) {
			for (int i = 0; i < pointDataset.size(); i++) {
				HiElement hiElement = pointDataset.get(i);
				if (hiElement.Text.equals(id)) {
					pointDatasetIndex = i;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 查询圆中的点
	 * 
	 * 
	 * @param cCoord
	 *            中心点
	 * @param r
	 *            半径
	 * @param nameArr
	 *            数据集名称
	 * @return
	 */
	public List<HiElement> searchByPoint(HiCoordinate cCoord, double r, ArrayList<String> nameArr) {
		List<HiElement> list = new ArrayList<HiElement>();
		if (hiDataManager == null) {
			hiDataManager = new HiDataManager();
		}
		// 执行查询
		list = hiDataManager.searchByPoint(cCoord, r, nameArr);
		return list;
	}

	/**
	 * 按矩形查询
	 * 
	 * @param crds
	 *            左下角和右上角坐标集
	 * @param nameArr
	 *            数据集名称
	 * @return
	 */
	public List<HiElement> searchByRect(ArrayList<HiCoordinate> crds, ArrayList<String> nameArr) {
		List<HiElement> list = new ArrayList<HiElement>();
		if (hiDataManager == null) {
			hiDataManager = new HiDataManager();
		}
		// 执行查询
		list = hiDataManager.searchByRect(crds, nameArr);
		return list;
	}

	/**
	 * 按多边形查询
	 * 
	 * @param crds
	 * @param nameArr
	 * @return
	 */
	public List<HiElement> searchByPolygon(ArrayList<HiCoordinate> crds, ArrayList<String> nameArr) {
		List<HiElement> list = new ArrayList<HiElement>();
		if (hiDataManager == null) {
			hiDataManager = new HiDataManager();
		}
		// 执行查询
		list = hiDataManager.searchByPolygon(crds, nameArr);
		return list;
	}

	/**
	 * 绘制元素
	 * 
	 * @param pointElementList
	 * @param context
	 */
	public void drawPoints(List<HiElement> pointElementList, Context context) {
		// TODO Auto-generated method stub
		if (pointElementList == null || pointElementList.size() < 1) {
			return;
		}
		for (int i = 0; i < pointElementList.size(); i++) {
			HiElement hiElement = pointElementList.get(i);
			Object dataRef = hiElement.DataReference;
			List<HiCoordinate> coordList = hiElement.Crds;
			if (dataRef instanceof EcNodeEntity) {// 节点
				EcNodeEntity ecNodeEntity = (EcNodeEntity) dataRef;
				double[] coords = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
				Bitmap bitmapStyle1 = null;
				if (ecNodeEntity.getMarkerType() == NodeMarkerType.BSQ.getValue()) {
					bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.biaoshiqiu_n);
				} else if (ecNodeEntity.getMarkerType() == NodeMarkerType.BSD.getValue()) {
					bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.biaoshiding_n);
				} else if (ecNodeEntity.getMarkerType() == NodeMarkerType.XND.getValue()){
					bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.xnd_n);
				} else if (ecNodeEntity.getMarkerType() == NodeMarkerType.GT.getValue()){
					bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower);
				}else {
					bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ajh);
				}
				GraphicsDrawUtils.getInstance().drawPointMarker(ecNodeEntity, ecNodeEntity.getOid(), coords,
						bitmapStyle1, null, null);
			} else if (dataRef instanceof EcDistributionRoomEntityVo) {// 配电室
				EcDistributionRoomEntityVo pds = (EcDistributionRoomEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(),
							R.drawable.peidianfang_n);
					GraphicsDrawUtils.getInstance().drawPointMarker(pds, pds.getObjId(), coords, bitmapStyle1, null,
							null);
				}

			} else if (dataRef instanceof EcDypdxEntityVo) {// 低压配电箱
				EcDypdxEntityVo dypdx = (EcDypdxEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.dypdx);
					GraphicsDrawUtils.getInstance().drawPointMarker(dypdx, dypdx.getObjId(), coords, bitmapStyle1, null,
							null);
				}
			} else if (dataRef instanceof EcKgzEntityVo) {// 开关站
				EcKgzEntityVo kgz = (EcKgzEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pdhwkgx);
					GraphicsDrawUtils.getInstance().drawPointMarker(kgz, kgz.getObjId(), coords, bitmapStyle1, null,
							null);
				}
			} else if (dataRef instanceof EcSubstationEntityVo) {// 变电站
				EcSubstationEntityVo bdz = (EcSubstationEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(),
							R.drawable.biandianzhan_n);
					GraphicsDrawUtils.getInstance().drawPointMarker(bdz, bdz.getEcSubstationId(), coords, bitmapStyle1,
							null, null);
				}
			} else if (dataRef instanceof EcTowerEntityVo) {// 杆塔
				EcTowerEntityVo gt = (EcTowerEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ganta_n);
					GraphicsDrawUtils.getInstance().drawPointMarker(gt, gt.getObjId(), coords, bitmapStyle1, null,
							null);
				}
			} else if (dataRef instanceof EcTransformerEntityVo) {// 变压器
				EcTransformerEntityVo byq = (EcTransformerEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.byq);
					GraphicsDrawUtils.getInstance().drawPointMarker(byq, byq.getObjId(), coords, bitmapStyle1, null,
							null);
				}
			} else if (dataRef instanceof EcWorkWellEntityVo) {// 工井
				EcWorkWellEntityVo gj = (EcWorkWellEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gongjing_n);
					GraphicsDrawUtils.getInstance().drawPointMarker(gj, gj.getObjId(), coords, bitmapStyle1, null,
							null);
				}
			} else if (dataRef instanceof EcXsbdzEntityVo) {// 箱式变电站
				EcXsbdzEntityVo xsbdz = (EcXsbdzEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ghbdz1);
					GraphicsDrawUtils.getInstance().drawPointMarker(xsbdz, xsbdz.getObjId(), coords, bitmapStyle1, null,
							null);
				}
			} else if (dataRef instanceof EcLineLabelEntityVo) {// 电子标签
				EcLineLabelEntityVo bq = (EcLineLabelEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.zhadai_n);
					GraphicsDrawUtils.getInstance().drawPointMarker(bq, bq.getObjId(), coords, bitmapStyle1, null,
							null);
				}
			} else if (dataRef instanceof EcMiddleJointEntityVo) {// 中间接头
				EcMiddleJointEntityVo zjjt = (EcMiddleJointEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pddlzjjt);
					GraphicsDrawUtils.getInstance().drawPointMarker(zjjt, zjjt.getObjId(), coords, bitmapStyle1, null,
							null);
				}
			} else if (dataRef instanceof EcDlfzxEntityVo) {// 电缆分支箱
				EcDlfzxEntityVo dlfzx = (EcDlfzxEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pddlfjx);
					GraphicsDrawUtils.getInstance().drawPointMarker(dlfzx, dlfzx.getObjId(), coords, bitmapStyle1, null,
							null);
				}
			} else if (dataRef instanceof EcDydlfzxEntityVo) {// 低压电缆分支箱
				EcDydlfzxEntityVo dydlfzx = (EcDydlfzxEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.dydld);
					GraphicsDrawUtils.getInstance().drawPointMarker(dydlfzx, dydlfzx.getObjId(), coords, bitmapStyle1,
							null, null);
				}
			} else if (dataRef instanceof EcHwgEntityVo) {// 环网柜
				EcHwgEntityVo hwg = (EcHwgEntityVo) dataRef;
				if (coordList != null && coordList.size() > 0) {
					HiCoordinate hiCoordinate = coordList.get(0);
					double[] coords = new double[] { hiCoordinate.x, hiCoordinate.y };
					Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(),
							R.drawable.huanwanggui_n);
					GraphicsDrawUtils.getInstance().drawPointMarker(hwg, hwg.getObjId(), coords, bitmapStyle1, null,
							null);
				}
			}

		}
		// 刷新地图
		MapCache.map.refresh();

	}

	/**
	 * 移除元素
	 * 
	 * @param elements
	 */
	public void removePoints(List<HiElement> elements) {
		// TODO Auto-generated method stub
		if (elements != null && elements.size() > 0) {
			for (int i = 0; i < elements.size(); i++) {
				HiElement hiElement = elements.get(i);
				Object dataRef = hiElement.DataReference;
				if (dataRef == null) {
					continue;
				}
				if (dataRef instanceof EcNodeEntity) {// 节点
					EcNodeEntity ecNodeEntity = (EcNodeEntity) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(ecNodeEntity.getOid());
				} else if (dataRef instanceof EcDistributionRoomEntityVo) {// 配电室
					EcDistributionRoomEntityVo pds = (EcDistributionRoomEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(pds.getObjId());
				} else if (dataRef instanceof EcDypdxEntityVo) {// 低压配电箱
					EcDypdxEntityVo dypds = (EcDypdxEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(dypds.getObjId());
				} else if (dataRef instanceof EcKgzEntityVo) {// 开关站
					EcKgzEntityVo kgz = (EcKgzEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(kgz.getObjId());
				} else if (dataRef instanceof EcSubstationEntityVo) {// 变电站
					EcSubstationEntityVo bdz = (EcSubstationEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(bdz.getEcSubstationId());
				} else if (dataRef instanceof EcTowerEntityVo) {// 杆塔
					EcTowerEntityVo gt = (EcTowerEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(gt.getObjId());
				} else if (dataRef instanceof EcTransformerEntityVo) {// 变压器
					EcTransformerEntityVo byq = (EcTransformerEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(byq.getObjId());
				} else if (dataRef instanceof EcWorkWellEntityVo) {// 工井
					EcWorkWellEntityVo gj = (EcWorkWellEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(gj.getObjId());
				} else if (dataRef instanceof EcXsbdzEntityVo) {// 箱式变电站
					EcXsbdzEntityVo xsbdz = (EcXsbdzEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(xsbdz.getObjId());
				} else if (dataRef instanceof EcLineLabelEntityVo) {// 电子标签
					EcLineLabelEntityVo dzbq = (EcLineLabelEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(dzbq.getObjId());
				} else if (dataRef instanceof EcMiddleJointEntityVo) {// 中间接头
					EcMiddleJointEntityVo zjjt = (EcMiddleJointEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(zjjt.getObjId());
				} else if (dataRef instanceof EcDlfzxEntityVo) {// 电缆分支箱
					EcDlfzxEntityVo dlfzx = (EcDlfzxEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(dlfzx.getObjId());
				} else if (dataRef instanceof EcDydlfzxEntityVo) {// 低压电缆分支箱
					EcDydlfzxEntityVo dydlfzx = (EcDydlfzxEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(dydlfzx.getObjId());
				} else if (dataRef instanceof EcHwgEntityVo) {// 环网柜
					EcHwgEntityVo hwg = (EcHwgEntityVo) dataRef;
					GraphicsDrawUtils.getInstance().removePoint(hwg.getObjId());
				}

			}
			// 刷新地图
			MapCache.map.refresh();
		}

	}
}
