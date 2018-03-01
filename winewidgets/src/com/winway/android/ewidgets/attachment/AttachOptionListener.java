package com.winway.android.ewidgets.attachment;

/**
 * 附件组件操作事件监听器
 * 目前支持的附件操作的以下3种
 * 1、增加附件
 * 2、修改附件名字 
 * 3、删除附件
 * @author mr-lao
 *
 */
public interface AttachOptionListener {
	/**添加附件*/
	public final static int OPT_ADD = 0;
	/**删除附件*/
	public final static int OPT_DELETE = 1;
	/**重命名附件*/
	public final static int OTP_RENAME = 2;

	/**
	 * 附件文件发生变化回调方法
	 * @param optType   操作类型,目前取值有OPT_ADD、OPT_DELETE、OTP_RENAME三种
	 * @param newPath   附件最新文件路径
	 * @param oldPath   附件的旧文件路径
	 * @example（组件） 
	 * 1、添加附件
	 *   listener.onAttach(OPT_ADD,addFilePath,null)
	 * 2、删除附件
	 *   listener.onAttach(OPT_DELETE,null,deleteFilePath)
	 * 3、重命名附件
	 * 	 listener.onAttach(OTP_RENAME,afterRenameFilePath,beforRenameFilePath)
	 * @example（回调） 
	 * class ExampleAttachOptionListener implements AttachOptionListener{
	 * 		public void onAttach(int optType, String newPath, String oldPath){
	 * 			if(optType =  AttachOptionListener.OPT_ADD){
	 * 				OflineAttach add =  new OflineAttach();
	 * 				add.setFilePath(newPath);
	 * 				dbUtil.save(add);
	 * 			}
	 * 			else if(optType =  AttachOptionListener.OPT_DELETE){
	 * 				dbUtil.delete(where attachFilePath =  oldFilePath);
	 * 			}
	 * 			else if(optType =  AttachOptionListener.OTP_RENAME){
	 * 				OflineAttach update = dbUtil.find(where attachFilePath =  oldFile);
	 * 				update.setFilePath(newPath);
	 * 				dbUtil.update(update);
	 * 			}
	 * 		}
	 * }
	 */
	public void onAttach(int optType, String newPath, String oldPath);
}
