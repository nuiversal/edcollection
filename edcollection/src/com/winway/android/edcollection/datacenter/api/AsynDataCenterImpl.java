package com.winway.android.edcollection.datacenter.api;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.entity.LineDetailsEntity;
import com.winway.android.edcollection.datacenter.entity.LineDevicesEntity;
import com.winway.android.edcollection.datacenter.entity.NodeAndNodeLineDeviceEntity;
import com.winway.android.edcollection.datacenter.entity.NodeDevicesEntity;
import com.winway.android.edcollection.datacenter.entity.SubStationDetailsEntity;
import com.winway.android.edcollection.datacenter.entity.edp.OrgTreeResult;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcVRRefEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDistBoxEntityVo;
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

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 异步数据中心（方法的回调器是在主线程中进行回调的）
 * @author mr-lao
 *
 */
@SuppressLint("HandlerLeak")
public class AsynDataCenterImpl implements DataCenter {
	/**
	 * 非主线程异常（如果AsynDataCenterImpl不是在主线程中创建的话，则会抛出此异常）
	 * @author mr-lao
	 *
	 */
	@SuppressWarnings("serial")
	public static class NotMainThreadException extends Exception {
		public NotMainThreadException(String string) {
			super(string);
		}
	}

	// 已经被实现的数据中心
	private DataCenter dataCenterImpl;

	public AsynDataCenterImpl(Context context, String projectDBUrl) throws NotMainThreadException {
		init(context, projectDBUrl);
	}

	public AsynDataCenterImpl(DataCenter dataCenterImpl) throws NotMainThreadException {
		init(dataCenterImpl);
	}

	void init(Context context, String projectDBUrl) throws NotMainThreadException {
		init(new DataCenterImpl(context, projectDBUrl));
	}

	void init(DataCenter dataCenterImpl) throws NotMainThreadException {
		if (Looper.getMainLooper() != Looper.myLooper()) {
			throw new NotMainThreadException("AsynDataCenterImpl必须在主线程中创建");
		}
		this.dataCenterImpl = dataCenterImpl;
	}

	private static class AysnCallBack {
		@SuppressWarnings("rawtypes")
		public CallBack callback;
		public Object calldata;
	}

	@SuppressWarnings("unchecked")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			AysnCallBack aysnCallBack = (AysnCallBack) msg.obj;
			aysnCallBack.callback.call(aysnCallBack.calldata);
		};
	};

	private static ExecutorService threadPool = Executors.newFixedThreadPool(3);

	@Override
	public void getOrgTree(String orgId, String logicSysNo, String credit, CallBack<OrgTreeResult> callback) {
		dataCenterImpl.getOrgTree(orgId, logicSysNo, credit, callback);
	}

	void sendHandlerMessage(@SuppressWarnings("rawtypes") CallBack callback, Object data) {
		if (null == callback) {
			return;
		}
		AysnCallBack aysn = new AysnCallBack();
		aysn.callback = callback;
		aysn.calldata = data;
		Message message = Message.obtain();
		message.obj = aysn;
		handler.sendMessage(message);
	}

	@Override
	public List<EcSubstationEntity> getSubStationsList(final String orgId, final String credit,
			final CallBack<List<EcSubstationEntity>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getSubStationsList(orgId, credit, new CallBack<List<EcSubstationEntity>>() {
					@Override
					public void call(List<EcSubstationEntity> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public SubStationDetailsEntity getSubStationDetails(final String subStationId, final String credit,
			final boolean containsLines, final CallBack<SubStationDetailsEntity> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getSubStationDetails(subStationId, credit, containsLines,
						new CallBack<SubStationDetailsEntity>() {
							@Override
							public void call(SubStationDetailsEntity data) {
								sendHandlerMessage(callback, data);
							}
						});
			}
		});
		return null;
	}

	@Override
	public List<EcLineEntity> getSubStationLineList(final String subStationId, final String credit,
			final CallBack<List<EcLineEntity>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getSubStationLineList(subStationId, credit, new CallBack<List<EcLineEntity>>() {
					@Override
					public void call(List<EcLineEntity> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public LineDetailsEntity getLineDetails(final String lineId, final String credit, final boolean containsNodes,
			final CallBack<LineDetailsEntity> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLineDetails(lineId, credit, containsNodes, new CallBack<LineDetailsEntity>() {
					@Override
					public void call(LineDetailsEntity data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<EcNodeEntity> getLineNodeList(final String lineId, final String credit,
			final CallBack<List<EcNodeEntity>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLineNodeList(lineId, credit, new CallBack<List<EcNodeEntity>>() {
					@Override
					public void call(List<EcNodeEntity> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public LineDevicesEntity getLineDevicesList(final String lineId, final String credit,
			final CallBack<LineDevicesEntity> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLineDevicesList(lineId, credit, new CallBack<LineDevicesEntity>() {
					@Override
					public void call(LineDevicesEntity data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcLineLabelEntityVo>> getLineLabelList(final String lineId, final String credit,
			final CallBack<List<DeviceEntity<EcLineLabelEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLineLabelList(lineId, credit, new CallBack<List<DeviceEntity<EcLineLabelEntityVo>>>() {
					@Override
					public void call(List<DeviceEntity<EcLineLabelEntityVo>> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcHwgEntityVo>> getHwgList(final String lineId, final String credit,
			final CallBack<List<DeviceEntity<EcHwgEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getHwgList(lineId, credit, new CallBack<List<DeviceEntity<EcHwgEntityVo>>>() {
					@Override
					public void call(List<DeviceEntity<EcHwgEntityVo>> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcDlfzxEntityVo>> getDlfzxList(final String lineId, final String credit,
			final CallBack<List<DeviceEntity<EcDlfzxEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getDlfzxList(lineId, credit, new CallBack<List<DeviceEntity<EcDlfzxEntityVo>>>() {
					@Override
					public void call(List<DeviceEntity<EcDlfzxEntityVo>> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcDydlfzxEntityVo>> getDydlfzxList(final String lineId, final String credit,
			final CallBack<List<DeviceEntity<EcDydlfzxEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getDydlfzxList(lineId, credit, new CallBack<List<DeviceEntity<EcDydlfzxEntityVo>>>() {
					@Override
					public void call(List<DeviceEntity<EcDydlfzxEntityVo>> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public EcNodeEntity getNodeDetail(final String nodeId, final String credit, final CallBack<EcNodeEntity> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getNodeDetail(nodeId, credit, new CallBack<EcNodeEntity>() {
					@Override
					public void call(EcNodeEntity data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public NodeAndNodeLineDeviceEntity getNodeDetails(final String nodeId, final String credit,
			final boolean containsLineDevices, final boolean containsPathNodeDevices,
			final CallBack<NodeAndNodeLineDeviceEntity> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getNodeDetails(nodeId, credit, containsLineDevices, containsPathNodeDevices,
						new CallBack<NodeAndNodeLineDeviceEntity>() {
							@Override
							public void call(NodeAndNodeLineDeviceEntity data) {
								sendHandlerMessage(callback, data);
							}
						});
			}
		});
		return null;
	}

	@Override
	public LineDevicesEntity getNodeLineDevicesList(final String nodeId, final String credit,
			final CallBack<LineDevicesEntity> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getNodeLineDevicesList(nodeId, credit, new CallBack<LineDevicesEntity>() {
					@Override
					public void call(LineDevicesEntity data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public NodeDevicesEntity getPathNodeDetails(final String nodeId, final String credit,
			final CallBack<NodeDevicesEntity> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getPathNodeDetails(nodeId, credit, new CallBack<NodeDevicesEntity>() {
					@Override
					public void call(NodeDevicesEntity data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<EcLineEntity> getAcrossLineInNode(final String nodeId, final String credit,
			final CallBack<List<EcLineEntity>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getAcrossLineInNode(nodeId, credit, new CallBack<List<EcLineEntity>>() {
					@Override
					public void call(List<EcLineEntity> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public DeviceEntity<EcLineLabelEntityVo> getLabel(final String labelId, final String credit,
			final CallBack<DeviceEntity<EcLineLabelEntityVo>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLabel(labelId, credit, new CallBack<DeviceEntity<EcLineLabelEntityVo>>() {
					@Override
					public void call(DeviceEntity<EcLineLabelEntityVo> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public DeviceEntity<EcDistBoxEntityVo> getDistBox(final String boxId, final String credit,
			final CallBack<DeviceEntity<EcDistBoxEntityVo>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getDistBox(boxId, credit, new CallBack<DeviceEntity<EcDistBoxEntityVo>>() {
					@Override
					public void call(DeviceEntity<EcDistBoxEntityVo> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public DeviceEntity<EcMiddleJointEntityVo> getMiddle(final String middleId, final String credit,
			final CallBack<DeviceEntity<EcMiddleJointEntityVo>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getMiddle(middleId, credit, new CallBack<DeviceEntity<EcMiddleJointEntityVo>>() {
					@Override
					public void call(DeviceEntity<EcMiddleJointEntityVo> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public DeviceEntity<EcWorkWellEntityVo> getWorkWell(final String wellId, final String credit,
			final CallBack<DeviceEntity<EcWorkWellEntityVo>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getWorkWell(wellId, credit, new CallBack<DeviceEntity<EcWorkWellEntityVo>>() {
					@Override
					public void call(DeviceEntity<EcWorkWellEntityVo> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public DeviceEntity<EcDistributionRoomEntityVo> getDistributionRoom(final String roomId, final String credit,
			final CallBack<DeviceEntity<EcDistributionRoomEntityVo>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getDistributionRoom(roomId, credit, new CallBack<DeviceEntity<EcDistributionRoomEntityVo>>() {
					@Override
					public void call(DeviceEntity<EcDistributionRoomEntityVo> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public DeviceEntity<EcSubstationEntityVo> getStation(final String stationId, final String credit,
			final CallBack<DeviceEntity<EcSubstationEntityVo>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getStation(stationId, credit, new CallBack<DeviceEntity<EcSubstationEntityVo>>() {
					@Override
					public void call(DeviceEntity<EcSubstationEntityVo> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public DeviceEntity<EcTransformerEntityVo> getTransformer(final String transformerId, final String credit,
			final CallBack<DeviceEntity<EcTransformerEntityVo>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getTransformer(transformerId, credit, new CallBack<DeviceEntity<EcTransformerEntityVo>>() {
					@Override
					public void call(DeviceEntity<EcTransformerEntityVo> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public DeviceEntity<EcTowerEntityVo> getTower(final String towerId, final String credit,
			final CallBack<DeviceEntity<EcTowerEntityVo>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getTower(towerId, credit, new CallBack<DeviceEntity<EcTowerEntityVo>>() {
					@Override
					public void call(DeviceEntity<EcTowerEntityVo> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public ChannelDataEntity getChannel(final String channelId, final String credit,
			final CallBack<ChannelDataEntity> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getChannel(channelId, credit, new CallBack<ChannelDataEntity>() {
					@Override
					public void call(ChannelDataEntity data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public EcLineNodesEntity getLineNode(final String oid, final String lineId, final String credit,
			final CallBack<EcLineNodesEntity> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLineNode(oid, lineId, credit, new CallBack<EcLineNodesEntity>() {
					@Override
					public void call(EcLineNodesEntity data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<EcLineNodesEntity> getLineNodeListByOid(final String oid, final String credit,
			final CallBack<List<EcLineNodesEntity>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLineNodeListByOid(oid, credit, new CallBack<List<EcLineNodesEntity>>() {
					@Override
					public void call(List<EcLineNodesEntity> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public LineDetailsEntity getLineDetailsByLineNo(final String lineNo, final boolean containsNodes,
			final CallBack<LineDetailsEntity> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLineDetailsByLineNo(lineNo, containsNodes, new CallBack<LineDetailsEntity>() {
					@Override
					public void call(LineDetailsEntity data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcMiddleJointEntityVo>> getLineMiddleList(final String lineId, final String credit,
			final CallBack<List<DeviceEntity<EcMiddleJointEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLineMiddleList(lineId, credit, new CallBack<List<DeviceEntity<EcMiddleJointEntityVo>>>() {
					@Override
					public void call(List<DeviceEntity<EcMiddleJointEntityVo>> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcDistBoxEntityVo>> getLineDistBoxList(final String lineId, final String credit,
			final CallBack<List<DeviceEntity<EcDistBoxEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLineDistBoxList(lineId, credit, new CallBack<List<DeviceEntity<EcDistBoxEntityVo>>>() {
					@Override
					public void call(List<DeviceEntity<EcDistBoxEntityVo>> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcWorkWellEntityVo>> getNodeWellList(final String nodeid, final String credit,
			final CallBack<List<DeviceEntity<EcWorkWellEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getNodeWellList(nodeid, credit, new CallBack<List<DeviceEntity<EcWorkWellEntityVo>>>() {
					@Override
					public void call(List<DeviceEntity<EcWorkWellEntityVo>> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcSubstationEntityVo>> getNodeStationList(final String nodeid, final String credit,
			final CallBack<List<DeviceEntity<EcSubstationEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getNodeStationList(nodeid, credit, new CallBack<List<DeviceEntity<EcSubstationEntityVo>>>() {
					@Override
					public void call(List<DeviceEntity<EcSubstationEntityVo>> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcXsbdzEntityVo>> getNodeXsbdzList(final String nodeid, final String credit,
			final CallBack<List<DeviceEntity<EcXsbdzEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getNodeXsbdzList(nodeid, credit, new CallBack<List<DeviceEntity<EcXsbdzEntityVo>>>() {
					@Override
					public void call(List<DeviceEntity<EcXsbdzEntityVo>> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcKgzEntityVo>> getNodeKgzList(final String nodeid, final String credit,
			final CallBack<List<DeviceEntity<EcKgzEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getNodeKgzList(nodeid, credit, new CallBack<List<DeviceEntity<EcKgzEntityVo>>>() {
					@Override
					public void call(List<DeviceEntity<EcKgzEntityVo>> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcDypdxEntityVo>> getNodeDypdxList(final String nodeid, final String credit,
			final CallBack<List<DeviceEntity<EcDypdxEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getNodeDypdxList(nodeid, credit, new CallBack<List<DeviceEntity<EcDypdxEntityVo>>>() {
					@Override
					public void call(List<DeviceEntity<EcDypdxEntityVo>> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcTowerEntityVo>> getNodeTowerList(final String nodeid, final String credit,
			final CallBack<List<DeviceEntity<EcTowerEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getNodeTowerList(nodeid, credit, new CallBack<List<DeviceEntity<EcTowerEntityVo>>>() {
					@Override
					public void call(List<DeviceEntity<EcTowerEntityVo>> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcTransformerEntityVo>> getNodedTranformerList(final String nodeid, final String credit,
			final CallBack<List<DeviceEntity<EcTransformerEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getNodedTranformerList(nodeid, credit,
						new CallBack<List<DeviceEntity<EcTransformerEntityVo>>>() {
							@Override
							public void call(List<DeviceEntity<EcTransformerEntityVo>> data) {
								sendHandlerMessage(callback, data);
							}
						});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcDistributionRoomEntityVo>> getNodeDistributionRoomList(final String nodeid,
			final String credit, final CallBack<List<DeviceEntity<EcDistributionRoomEntityVo>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getNodeDistributionRoomList(nodeid, credit,
						new CallBack<List<DeviceEntity<EcDistributionRoomEntityVo>>>() {
							@Override
							public void call(List<DeviceEntity<EcDistributionRoomEntityVo>> data) {
								sendHandlerMessage(callback, data);
							}
						});
			}
		});
		return null;
	}

	@Override
	public List<OfflineAttach> getOfflineAttachListByWorkNo(final String workNo, final String credit,
			final CallBack<List<OfflineAttach>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getOfflineAttachListByWorkNo(workNo, credit, new CallBack<List<OfflineAttach>>() {
					@Override
					public void call(List<OfflineAttach> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<EcLineEntity> getLineListByOrgid(final String orgid, final String credit,
			final CallBack<List<EcLineEntity>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLineListByOrgid(orgid, credit, new CallBack<List<EcLineEntity>>() {
					@Override
					public void call(List<EcLineEntity> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public EcLineEntity getLineDeviceTargetLine(final String deviceId, final String credit,
			final CallBack<EcLineEntity> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLineDeviceTargetLine(deviceId, credit, new CallBack<EcLineEntity>() {
					@Override
					public void call(EcLineEntity data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<ChannelDataEntity> getChannelListByLineID(final String lineid, final String credit,
			final CallBack<List<ChannelDataEntity>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getChannelListByLineID(lineid, credit, new CallBack<List<ChannelDataEntity>>() {
					@Override
					public void call(List<ChannelDataEntity> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<ChannelDataEntity> getAllChannel(final String credit, final CallBack<List<ChannelDataEntity>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getAllChannel(credit, new CallBack<List<ChannelDataEntity>>() {
					@Override
					public void call(List<ChannelDataEntity> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<DeviceEntity<EcWorkWellCoverEntity>> getWellCoverList(final String credit, final String wellId,
			final boolean needNode, final CallBack<List<DeviceEntity<EcWorkWellCoverEntity>>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getWellCoverList(credit, wellId, needNode, callback);
			}
		});
		return null;
	}

	@Override
	public List<EcLineLabelEntity> getDeviceLabel(final String credit, final Object deviceObj,
			final CallBack<List<EcLineLabelEntity>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getDeviceLabel(credit, deviceObj, callback);
			}
		});
		return null;
	}

	@Override
	public Object getLabelTargetDevice(final String credit, final String labelno, final CallBack<Object> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getLabelTargetDevice(credit, labelno, callback);
			}
		});
		return null;
	}

	@Override
	public List<EcVRRefEntity> getPanoramicByNodeOid(final String objId, final String credit,
			final CallBack<List<EcVRRefEntity>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getPanoramicByNodeOid(objId, credit, new CallBack<List<EcVRRefEntity>>() {

					@Override
					public void call(List<EcVRRefEntity> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}

	@Override
	public List<EcVRRefEntity> getPanoramicByDeviceId(final String objId,final String credit,final CallBack<List<EcVRRefEntity>> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getPanoramicByDeviceId(objId, credit, new CallBack<List<EcVRRefEntity>>() {

					@Override
					public void call(List<EcVRRefEntity> data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}
	
	@Override
	public EcChannelNodesEntity getChannelNode(final String oid, final String channelId, final String credit,
			final CallBack<EcChannelNodesEntity> callback) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				dataCenterImpl.getChannelNode(oid, channelId, credit, new CallBack<EcChannelNodesEntity>() {
					@Override
					public void call(EcChannelNodesEntity data) {
						sendHandlerMessage(callback, data);
					}
				});
			}
		});
		return null;
	}
}
