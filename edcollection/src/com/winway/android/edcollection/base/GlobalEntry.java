package com.winway.android.edcollection.base;

import com.winway.android.edcollection.login.entity.LoginResult;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EmProjectEntity;
import com.winway.android.edcollection.project.entity.EmTasksEntity;
import com.winway.android.edcollection.rtk.entity.RtkLocationInfo;
import com.winway.android.util.FileUtil;

/**
 * 存放一些用于全局共享的静态对象与变量
 * 
 * @author mr-lao
 *
 */
public class GlobalEntry {
	
//	private static String urlPath = "http://192.168.0.76";

	
	private static String caveterPath = "http://120.78.140.100";

	// 外网地址 120.25.84.217z

	/**
	 * 登录地址
	 */
	public static String loginServerUrl = caveterPath + ":8900/";

	/**
	 * 数据服务接口地址
	 */

	public static String dataServerUrl = "http://120.78.138.175:8902/";
//	public static String dataServerUrl = urlPath+":8902/";

	/**
	 * 文件通讯服务
	 */
	//  String uploadUrl = "?
	public static String fileServerUrl = "http://120.78.138.175:8903/";// 文件通讯服务
//	public static String fileServerUrl = "http://192.168.0.76:8903/";// 文件通讯服务

	/**
	 * 当前项目的引用
	 */
	public static EmProjectEntity currentProject = null;
	/**
	 * 路径点递增值
	 */
	public static int node_variance = 100;

	/**
	 * 登录结果
	 */
	public static LoginResult loginResult = null;

	/**
	 * 编辑功能是否打开，false为新增，true表示编辑
	 */
	public static boolean editNode = false;

	/**
	 * 当前编辑节点
	 */
	public static EcNodeEntity currentEditNode = null;

	/**
	 * 是否处于编辑状态下的地图选点状态
	 */
	public static boolean editMapSelectCoord = false;

	/**
	 * 是否有效的凭据,true表示凭据有效，false表示凭据无效
	 */
	public static boolean isEffectiveCredit = false;

	/**
	 * 接口返回结果，表示错误
	 */
	public static final int RESULT_ERROR = -1;
	/**
	 * 接口返回结果，表示成功
	 */
	public static final int RESULT_SUCCESS = 0;

	public static final String DEFAULT_LOGIC_SYS_NO = "WCLOUD_MANAGER";

	/**
	 * 项目db的url
	 */
	public static String prjDbUrl = null;

	/**
	 * rtk获取的当前位置信息
	 */
	public static RtkLocationInfo rtkLocationInfo = null;

	/**
	 * rtk状态,是否使用rtk的位置信息，true表示使用rtk位置信息，false表示非rtk坐标
	 */
	public static boolean isUseRtk = false;

	/**
	 * 全局db的url
	 */
	public static final String globalDbUrl = FileUtil.AppRootPath + "/db/common/global.db";

	/**
	 * 数据缓存db的url
	 */
	public static final String dataCacheDbUrl = FileUtil.AppRootPath + "/db/project/data_cache.db";

	public static final String INTENT_KEY_CHANNEL = "INTENT_KEY_CHANNEL";
	public static final String INTENT_KEY_CHANNEL_DATA = "INTENT_KEY_CHANNEL_DATA";

	/**
	 * 作用：控制所属地市在输入界面中的的显示和显示界面中的显示 如果ssds=false，
	 * 则将采集界面的ssds输入框隐藏掉，台账树详情页面也将ssds显示框隐藏掉
	 * 如果ssds=true，则将采集界面的ssds输入框显示，台账树详情页面也将ssds显示框显示
	 */
	public static boolean ssds = false;
	
	/**
	 * 如果资产性质是true 采集资产性质时是南方电网公司 反之就是国家电网公司，默认南方电网公司
	 */
	public static boolean zcxz = true;
	
	/**
	 * 文件下载路径
	 */
	public static String downLoadDir = FileUtil.AppRootPath + "/download/offline-attach/";
	/**
	 * 当前任务
	 */
	public static EmTasksEntity currentTask;

	/**
	 * 是否使用烽火移动平台
	 */
	public static boolean isUseMobileArk=false;
	
	/** 是否使用TF卡 */
	public static boolean useTfcard = false;
	
	/** 是否使用TF卡*/
	public static String IS_USE_TFCARD;
}
