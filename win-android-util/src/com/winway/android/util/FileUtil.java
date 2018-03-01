package com.winway.android.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

/**
 * 文件操作工具包
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class FileUtil {

	/**
	 * APP根路经，项目中使用时，需要赋初值
	 */
	public static String AppRootPath = null;

	private static String WINWAY_PATH = "winway_dlksh";

	/**
	 * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
	 * 
	 * @param context
	 * @param msg
	 */
	public static void write(Context context, String fileName, String content) {
		if (content == null)
			content = "";

		try {
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(content.getBytes());

			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文本文件
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String read(Context context, String fileName) {
		try {
			FileInputStream in = context.openFileInput(fileName);
			return readInStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String readInStream(InputStream inStream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}

			outStream.close();
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			Log.i("FileTest", e.getMessage());
		}
		return null;
	}

	public static File createFile(String folderPath, String fileName) {
		File destDir = new File(folderPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		return new File(folderPath, fileName + fileName);
	}

	/**
	 * 向手机写图片
	 * 
	 * @param buffer
	 * @param folder
	 * @param fileName
	 * @return
	 */
	public static boolean writeFile(byte[] buffer, String folder, String fileName) {
		boolean writeSucc = false;

		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

		String folderPath = "";
		if (sdCardExist) {
			folderPath = Environment.getExternalStorageDirectory() + File.separator + folder + File.separator;
		} else {
			writeSucc = false;
		}

		File fileDir = new File(folderPath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		File file = new File(folderPath + fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(buffer);
			writeSucc = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return writeSucc;
	}

	/**
	 * 根据文件绝对路径获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (StringUtils.getInstance().isEmpty(filePath))
			return "";
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 根据文件的绝对路径获取文件名但不包含扩展名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameNoFormat(String filePath) {
		if (StringUtils.getInstance().isEmpty(filePath)) {
			return "";
		}
		int point = filePath.lastIndexOf('.');
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1, point);
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (StringUtils.getInstance().isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}

	/**
	 * 获取文件大小
	 * 
	 * @param filePath
	 * @return
	 */
	public static long getFileSize(String filePath) {
		long size = 0;

		File file = new File(filePath);
		if (file != null && file.exists()) {
			size = file.length();
		}
		return size;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param size
	 *            字节
	 * @return
	 */
	public static String getFileSize(long size) {
		if (size <= 0)
			return "0";
		java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
		float temp = (float) size / 1024;
		if (temp >= 1024) {
			return df.format(temp / 1024) + "M";
		} else {
			return df.format(temp) + "K";
		}
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 获取目录文件大小
	 * 
	 * @param dir
	 * @return
	 */
	public static long getDirSize(File dir) {
		if (dir == null) {
			return 0;
		}
		if (!dir.isDirectory()) {
			return 0;
		}
		long dirSize = 0;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				dirSize += file.length();
			} else if (file.isDirectory()) {
				dirSize += file.length();
				dirSize += getDirSize(file); // 递归调用继续统计
			}
		}
		return dirSize;
	}

	/**
	 * 获取目录文件个数
	 * 
	 * @param emojiFragment
	 * @return
	 */
	public long getFileList(File dir) {
		long count = 0;
		File[] files = dir.listFiles();
		count = files.length;
		for (File file : files) {
			if (file.isDirectory()) {
				count = count + getFileList(file);// 递归
				count--;
			}
		}
		return count;
	}

	public static byte[] toBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int ch;
		while ((ch = in.read()) != -1) {
			out.write(ch);
		}
		byte buffer[] = out.toByteArray();
		out.close();
		return buffer;
	}

	/**
	 * 检查文件是否存在
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkFileExists(String name) {
		boolean status;
		if (!name.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + name);
			status = newPath.exists();
		} else {
			status = false;
		}
		return status;
	}

	/**
	 * 检查路径是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean checkFilePathExists(String path) {
		return new File(path).exists();
	}

	/**
	 * 计算SD卡的剩余空间
	 * 
	 * @return 返回-1，说明没有安装sd卡
	 */
	public static long getFreeDiskSpace() {
		String status = Environment.getExternalStorageState();
		long freeSpace = 0;
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				freeSpace = availableBlocks * blockSize / 1024;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return -1;
		}
		return (freeSpace);
	}
	
	/**
	 * 外置SD卡剩余空间大小  
	 * @return
	 */
    public static long getSDFreeSize(String tfPath){    
        StatFs sf = new StatFs(tfPath);     
        //获取单个数据块的大小(Byte)     
        long blockSize = sf.getBlockSize();     
       //空闲的数据块的数量     
        long freeBlocks = sf.getAvailableBlocks();    
        //返回SD卡空闲大小     
        return (freeBlocks * blockSize)/1024 /1024; //单位MB     
      }              

	/**
	 * 新建目录
	 * 
	 * @param directoryName
	 * @return
	 */
	public static boolean createDirectory(String directoryName) {
		boolean status;
		if (!directoryName.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + directoryName);
			status = newPath.mkdir();
			status = true;
		} else
			status = false;
		return status;
	}

	/**
	 * 检查是否安装SD卡
	 * 
	 * @return
	 */
	public static boolean checkSaveLocationExists() {
		String sDCardStatus = Environment.getExternalStorageState();
		boolean status;
		if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
			status = true;
		} else
			status = false;
		return status;
	}

	/**
	 * 检查是否安装外置的SD卡
	 * 
	 * @return
	 */
	public static boolean checkExternalSDExists() {

		Map<String, String> evn = System.getenv();
		return evn.containsKey("SECONDARY_STORAGE");
	}

	/**
	 * 删除目录(包括：目录里的所有文件)
	 * 
	 * @param floderPath
	 *            文件夹路径
	 * @return
	 */
	public static void deleteDirectory(String floderPath) {
		File dir = new File(floderPath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] listFiles = dir.listFiles();
		if (listFiles == null || listFiles.length == 0) {
			// 空文件夹，直接删除
			dir.delete();
		} else {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					deleteDirectory(file.getAbsolutePath());
				} else {
					file.delete();
				}
			}
			dir.delete();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!fileName.equals("")) {

			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + fileName);
			checker.checkDelete(newPath.toString());
			if (newPath.isFile()) {
				try {
					Log.i("DirectoryManager deleteFile", fileName);
					newPath.delete();
					status = true;
				} catch (SecurityException se) {
					se.printStackTrace();
					status = false;
				}
			} else
				status = false;
		} else
			status = false;
		return status;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileNamePath
	 *            文件名（包括路径）
	 * @return
	 */
	public static boolean deleteFileByFilePath(String fileNamePath) {
		boolean status;
		SecurityManager checker = new SecurityManager();
		if (!fileNamePath.equals("")) {
			File newPath = new File(fileNamePath);
			checker.checkDelete(newPath.toString());
			if (newPath.isFile()) {
				try {
					Log.i("DirectoryManager deleteFile", fileNamePath);
					newPath.delete();
					status = true;
				} catch (SecurityException se) {
					se.printStackTrace();
					status = false;
				}
			} else
				status = false;
		} else
			status = false;
		return status;
	}

	/**
	 * 删除空目录
	 * 
	 * 返回 0代表成功 ,1 代表没有删除权限, 2代表不是空目录,3 代表未知错误
	 * 
	 * @return
	 */
	public static int deleteBlankPath(String path) {
		File f = new File(path);
		if (!f.canWrite()) {
			return 1;
		}
		if (f.list() != null && f.list().length > 0) {
			return 2;
		}
		if (f.delete()) {
			return 0;
		}
		return 3;
	}

	/**
	 * 重命名
	 * 
	 * @param oldName
	 * @param newName
	 * @return
	 */
	public static boolean reNamePath(String oldName, String newName) {
		File f = new File(oldName);
		return f.renameTo(new File(newName));
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 */
	public static boolean deleteFileWithPath(String filePath) {
		SecurityManager checker = new SecurityManager();
		File f = new File(filePath);
		checker.checkDelete(filePath);
		if (f.isFile()) {
			Log.i("DirectoryManager deleteFile", filePath);
			f.delete();
			return true;
		}
		return false;
	}

	/**
	 * 清空一个文件夹
	 * 
	 * @param files
	 */
	public static void clearFileWithPath(String filePath) {
		List<File> files = FileUtil.listPathFiles(filePath);
		if (files.isEmpty()) {
			return;
		}
		for (File f : files) {
			if (f.isDirectory()) {
				clearFileWithPath(f.getAbsolutePath());
			} else {
				f.delete();
			}
		}
	}

	/**
	 * 获取SD卡的根目录
	 * 
	 * @return
	 */
	public static String getSDRoot() {

		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	/**
	 * 获取手机外置SD卡的根目录
	 * 
	 * @return
	 */
	public static String getExternalSDRoot() {

		Map<String, String> evn = System.getenv();

		return evn.get("SECONDARY_STORAGE");
	}

	/**
	 * 获取扩展存储路径，TF卡、U盘
	 */
	public static String getTFCardPath() {
		String dir = new String();
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;

				if (line.contains("fat")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						dir = dir.concat(columns[1] + "\n");
					}
				} else if (line.contains("fuse")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						dir = dir.concat(columns[1] + "\n");
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dir;
	}

	/**
	 * 获取扩展存储路径，TF卡、U盘
	 */
	public static List<String> getTFCardPaths() {
		List<String> paths = new ArrayList<String>();
		String extFileStatus = Environment.getExternalStorageState();
		File extFile = Environment.getExternalStorageDirectory();
		if (extFileStatus.endsWith(Environment.MEDIA_UNMOUNTED) && extFile.exists() && extFile.isDirectory()
				&& extFile.canWrite()) {
			paths.add(extFile.getAbsolutePath());
		}
		try {
			// obtain executed result of command line code of 'mount', to judge
			// whether tfCard exists by the result
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("mount");
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			int mountPathIndex = 1;
			while ((line = br.readLine()) != null) {
				// format of sdcard file system: vfat/fuse
				if ((!line.contains("fat") && !line.contains("fuse") && !line.contains("storage"))
						|| line.contains("secure") || line.contains("asec") || line.contains("firmware")
						|| line.contains("shell") || line.contains("obb") || line.contains("legacy")
						|| line.contains("data")) {
					continue;
				}
				String[] parts = line.split(" ");
				int length = parts.length;
				if (mountPathIndex >= length) {
					continue;
				}
				String mountPath = parts[mountPathIndex];
				if (!mountPath.contains("/") || mountPath.contains("data") || mountPath.contains("Data")) {
					continue;
				}
				File mountRoot = new File(mountPath);
				if (!mountRoot.exists() || !mountRoot.isDirectory() || !mountRoot.canWrite()) {
					continue;
				}
				boolean equalsToPrimarySD = mountPath.equals(extFile.getAbsolutePath());
				if (equalsToPrimarySD) {
					continue;
				}
				paths.add(mountPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (paths.isEmpty()) {
			String pat = getSansumgPath();
			if (!TextUtils.isEmpty(pat)) {
				paths.add(pat);
			}
		}
		return paths;
	}

	private static String getSansumgPath() {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			String mount = new String();
			BufferedReader br = new BufferedReader(isr);

			// while ((line = br.readLine()) != null) {
			// mount += line + "\n";
			// }

			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;

				if (line.contains("fat")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						mount = mount.concat("*" + columns[1] + "\n");
					}
				} else if (line.contains("fuse")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						mount = mount.concat(columns[1] + "\n");
					}
				}
			}
			// 处理
			if (mount.equals("")) {
				return "";
			}
			String path = "";
			String[] arr = mount.split("\n");
			for (int i = 0; i < arr.length; i++) {
				String[] t = arr[i].split("/");
				if (t.length > 0) {
					path = "/storage/" + t[t.length - 1];
				}
				File fff = new File(path);
				if (fff.exists()) {
					return path;
				}
			}

			return "";

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 列出root目录下所有子目录
	 * 
	 * @param path
	 * @return 绝对路径
	 */
	public static List<String> listPath(String root) {
		List<String> allDir = new ArrayList<String>();
		SecurityManager checker = new SecurityManager();
		File path = new File(root);
		checker.checkRead(root);
		// 过滤掉以.开始的文件夹
		if (path.isDirectory()) {
			for (File f : path.listFiles()) {
				if (f.isDirectory() && !f.getName().startsWith(".")) {
					allDir.add(f.getAbsolutePath());
				}
			}
		}
		return allDir;
	}

	/**
	 * 获取一个文件夹下的所有文件
	 * 
	 * @param root
	 * @return
	 */
	public static List<File> listPathFiles(String root) {
		File dirFile = new File(root);
		if (!dirFile.exists()) {
			return null;
		}
		File[] listFiles = dirFile.listFiles();
		if (listFiles == null || listFiles.length == 0) {
			return null;
		}

		ArrayList<File> restList = new ArrayList<File>();
		for (File file : listFiles) {
			if (file.isDirectory()) {
				List<File> listPathFiles = listPathFiles(file.getAbsolutePath());
				if (null != listPathFiles) {
					restList.addAll(listPathFiles);
				}
			} else {
				restList.add(file);
			}
		}

		return restList;
	}

	public enum PathStatus {
		SUCCESS, EXITS, ERROR
	}

	/**
	 * 创建目录
	 * 
	 * @param path
	 */
	public static PathStatus createPath(String newPath) {
		File path = new File(newPath);
		if (path.exists()) {
			return PathStatus.EXITS;
		}
		if (path.mkdir()) {
			return PathStatus.SUCCESS;
		} else {
			return PathStatus.ERROR;
		}
	}

	/**
	 * 截取路径名
	 * 
	 * @return
	 */
	public static String getPathName(String absolutePath) {
		int start = absolutePath.lastIndexOf(File.separator) + 1;
		int end = absolutePath.length();
		return absolutePath.substring(start, end);
	}

	/**
	 * 获取应用程序缓存文件夹下的指定目录
	 * 
	 * @param context
	 * @param dir
	 * @return
	 */
	public static String getAppCache(Context context, String dir) {
		String savePath = context.getCacheDir().getAbsolutePath() + "/" + dir + "/";
		File savedir = new File(savePath);
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		savedir = null;
		return savePath;
	}

	public static String getWinwayitPath() {
		// TODO Auto-generated method stub
		return getSDRoot() + File.separator + WINWAY_PATH;
	}

	/**
	 * 获取目录路径下的全部图片的路径
	 * 
	 * @param path
	 * @return
	 */
	public static List<String> getImagesPathfromDir(String path) {
		List<String> it = new ArrayList<String>();
		File f = new File(path);
		File[] files = f.listFiles();

		if (files == null) {
			return null;
		}
		// 将所有文件存入ArrayList中
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			// 判断是一个文件夹还是一个文件
			if (file.isDirectory()) {
				System.out.println("this Directory");
			} else {
				if (getFileIsImage(file.getPath()))
					it.add(file.getPath());
			}

		}
		return it;
	}

	// 判断是否为是图片文件
	private static boolean getFileIsImage(String fName) {
		boolean re = false;

		// 取得扩展名
		String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();

		// 判断图片扩展名
		if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
			re = true;
		}

		return re;
	}

	/**
	 * 获取外置SD卡路径
	 * 
	 * @return 应该就一条记录或空
	 */
	public static List<String> getExtSDCardPath() {
		List<String> lResult = new ArrayList<String>();
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("extSdCard")) {
					String[] arr = line.split(" ");
					String path = arr[1];
					File file = new File(path);
					if (file.isDirectory()) {
						lResult.add(path);
					}
				}
			}
			isr.close();
		} catch (Exception e) {
		}
		return lResult;
	}

	/**
	 * 判断SDCard是否可用
	 * 
	 * @return
	 */
	public static boolean isSDCardEnable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取SD卡路径
	 * 
	 * @return
	 */
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}

	/**
	 * 获取SD卡的剩余容量 单位byte
	 * 
	 * @return
	 */
	public static long getSDCardAllSize() {
		if (isSDCardEnable()) {
			StatFs stat = new StatFs(getSDCardPath());
			// 获取空闲的数据块的数量
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	public static final int SDCARD_EXTERNAL = 1;// 外置内存卡
	public static final int SDCARD_INNER = 2;// 内置内存卡

	/**
	 * 获得设备内存卡目录，如果有外置内存卡在，则默认返回外置内存卡，否则，返回内置内存卡
	 * 
	 * @return
	 */
	public static File getSDCardDir() {
		File file = getExternalSDCardDir();
		if (null == file) {
			file = getInnerSDCardDir();
		}
		return file;
	}

	/**
	 * 获取内部内存卡目录
	 * 
	 * @return
	 */
	public static File getInnerSDCardDir() {
		return Environment.getExternalStorageDirectory();
	}

	private static ArrayList<String> getDevMountList() {
		String str = read("/system/etc/vold.fstab");
		if (null == str || str.length() == 0)
			return null;
		String[] toSearch = str.split(" ");// ("/system/etc/vold.fstab").split("
											// ");
		ArrayList<String> out = new ArrayList<String>();
		for (int i = 0; i < toSearch.length; i++) {
			if (toSearch[i].contains("dev_mount")) {
				if (new File(toSearch[i + 2]).exists()) {
					out.add(toSearch[i + 2]);
				}
			}
		}
		return out;
	}

	/**
	 * 获取外部内存卡目录
	 * 
	 * @return
	 */
	public static File getExternalSDCardDir() {
		String path = null;
		File sdCardFile = null;
		ArrayList<String> devMountList = getDevMountList();
		if (null == devMountList) {
			return null;
		}
		for (String devMount : devMountList) {
			File file = new File(devMount);

			if (file.isDirectory() && file.canWrite()) {
				path = file.getAbsolutePath();

				String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
				File testWritable = new File(path, "test_" + timeStamp);

				if (testWritable.mkdirs()) {
					testWritable.delete();
				} else {
					path = null;
				}
			}
		}
		if (path != null) {
			sdCardFile = new File(path);

		}
		return sdCardFile;
	}

	/**
	 * 获得文件，如果文件存在，则直接返回文件，如果文件不存在，则创建文件再返回
	 * 
	 * @param dir
	 *            目录名
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws IOException
	 */
	public static File getFile(String dir, String fileName) throws IOException {
		// 智能判断dir是相对路径，还是绝对路径（相对，是相对内存卡目录而言，绝对，是包括了内存卡目录）
		if (null == dir) {
			dir = getSDCardPath();
		} else {
//			if (!dir.contains(getSDCardPath())) {
//				dir = getSDCardPath() + "/" + dir;
//			}
		}
		File file = new File(dir, fileName);
		return getFile(file.getAbsolutePath());
	}

	/**
	 * 如果path指向的文件不存在，则创建path指向的文件再返回；如果path指向的文件存在，则直接返回
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static File getFile(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	/**
	 * 保存字符串到文件之中
	 * 
	 * @param dir
	 *            文件目录，建议使用相对路径
	 * @param fileName
	 *            文件名
	 * @param saveMsg
	 *            保存的内容
	 * @return
	 */
	public static boolean save(String dir, String fileName, String saveMsg) {
		try {
			File file = getFile(dir, fileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(saveMsg);
			writer.flush();
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 把比特数组的字符存入文件中
	 * 
	 * @param dir
	 * @param fileName
	 * @param charset
	 * @param data
	 * @return
	 */
	public static boolean save(String dir, String fileName, String charset, byte[] data) {
		try {
			File file = getFile(dir, fileName);
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), charset);
			writer.write(new String(data));
			writer.flush();
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 把比特数组数据存入文件中
	 * 
	 * @param dir
	 * @param fileName
	 * @param data
	 * @return
	 */
	public static boolean save(String dir, String fileName, byte[] data) {
		try {
			File file = getFile(dir, fileName);
			FileOutputStream out = new FileOutputStream(file);
			out.write(data);
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 以UTF-8编码方式保存字符串到文件中
	 * 
	 * @param filepath
	 * @param content
	 */
	public static void saveUTF8(String filepath, String content) {
		File file = new File(filepath);
		save(file.getParentFile().getAbsolutePath(), file.getName(), "UTF-8", content.getBytes());
	}

	/**
	 * 将文件复制到另一个文件中
	 * 
	 * @param fromDir
	 * @param fromFileName
	 * @param toDir
	 * @param toFileName
	 * @return
	 */
	public static boolean copy(String fromDir, String fromFileName, String toDir, String toFileName) {
		return copy(new File(fromDir, fromFileName), new File(toDir, toFileName));
	}

	/**
	 * 将文件复制到另一个文件中
	 * 
	 * @param fromDir
	 * @param fromFileName
	 * @param toDir
	 * @param toFileName
	 * @return
	 */
	public static boolean copy(File fromFile, File toFile) {
		return copy(fromFile.getAbsolutePath(), toFile.getAbsolutePath());
	}

	/**
	 * 将文件复制到另一个文件中
	 * 
	 * @param fromDir
	 * @param fromFileName
	 * @param toDir
	 * @param toFileName
	 * @return
	 */
	public static boolean copy(String fromFilePath, String toFilePath) {
		File fromFile = new File(fromFilePath);
		if (!fromFile.exists()) {// 复制的目标文件不存在，返回错误结果
			return false;
		}
		try {
			File toFile = getFile(toFilePath);
			FileInputStream in = new FileInputStream(fromFile);
			FileOutputStream out = new FileOutputStream(toFile);
			byte[] buff = new byte[1024];
			int n = 0;
			while ((n = in.read(buff)) != -1) {
				out.write(buff, 0, n);
			}
			in.close();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 复制文件夹
	 * 
	 * @param sourceDir
	 * @param targetDir
	 */
	public static void copyDirectiory(String sourceDir, String targetDir) {
		// 新建目标目录
		File sourceF = new File(sourceDir);
		File targetF = new File(targetDir);
		targetF.mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = sourceF.listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(targetF.getAbsolutePath() + File.separator + file[i].getName());
				copy(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}

	/**
	 * 从文件中读取字符数据，采用系统默认编码（安卓设备是UTF-8）
	 * 
	 * @param absolutPath
	 *            绝对路径
	 * @return
	 */
	public static String read(String absolutPath) {
		try {
			File file = new File(absolutPath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			FileReader reader = new FileReader(file);
			char[] buffer = new char[1024];
			int n = 0;
			StringBuilder builder = new StringBuilder();
			while ((n = reader.read(buffer)) != -1) {
				builder.append(new String(buffer, 0, n));
			}
			reader.close();
			return builder.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从文件中读取字符数据，采用系统默认编码（安卓设备是UTF-8）
	 * 
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public static String read(String dir, String fileName) {
		try {
			File file = getFile(dir, fileName);
			FileReader reader = new FileReader(file);
			char[] buffer = new char[1024];
			int n = 0;
			StringBuilder builder = new StringBuilder();
			while ((n = reader.read(buffer)) != -1) {
				builder.append(new String(buffer, 0, n));
			}
			reader.close();
			return builder.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从文件中读取字符数据，采用指定编码
	 * 
	 * @param dir
	 * @param fileName
	 * @param charset
	 * @return
	 */
	public static String read(String dir, String fileName, String charset) {
		try {
			File file = getFile(dir, fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
			char[] buffer = new char[1024];
			int n = 0;
			StringBuilder builder = new StringBuilder();
			while ((n = reader.read(buffer)) != -1) {
				builder.append(new String(buffer, 0, n));
			}
			reader.close();
			return builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从文件中以UTF-8编码方式读取字符串
	 * 
	 * @param filepath
	 * @return
	 */
	public static String readUTF8(String filepath) {
		File file = new File(filepath);
		return read(file.getParentFile().getAbsolutePath(), file.getName(), "UTF-8");
	}

	/**
	 * 
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public static byte[] readByte(String dir, String fileName) {
		try {
			File file = getFile(dir, fileName);
			FileInputStream reader = new FileInputStream(file);
			byte[] datas = new byte[(int) file.length()];
			reader.read(datas);
			reader.close();
			return datas;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得文件夹中的指定后缀名的文件，如果文件后缀名值为空，则返回文件夹内的所有文件
	 * 
	 * @param dir
	 *            指定文件夹
	 * @param suffixNames
	 *            文件后缀名
	 * @return
	 */
	public static File[] getFileFromDir(File dir, final String... suffixNames) {
		if (!dir.isDirectory()) {
			return null;
		}

		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				String sfxname = filename.substring(filename.lastIndexOf(".") + 1);
				for (String sfx : suffixNames) {
					if (sfx.equalsIgnoreCase(sfxname)) {
						return true;
					}
				}
				return false;
			}
		};
		return dir.listFiles(filter);
	}

	/**
	 * 如果文件不存在，则创建文件，创建成功，返回true，失败返回false；如果文件存在，直接返回true
	 * 
	 * @param file
	 * @return
	 */
	public static boolean createFile(File file) {
		if (file.exists()) {
			return true;
		} else {
			try {
				file.createNewFile();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * 如果文件不存在，则创建文件，创建成功，返回true，失败返回false；如果文件存在，直接返回true
	 * 
	 * @param file
	 * @return
	 */
	public static boolean createFile(String file) {
		return createFile(new File(file));
	}

	/**
	 * 把文件流指向的文件内容都由到目标文件中
	 * 
	 * @param in
	 * @param file
	 */
	public static void copy(InputStream in, File file) {
		boolean createFile = createFile(file);
		if (!createFile) {
			return;
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把输入流中的字节写入到输出流中
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void copy(InputStream in, OutputStream out) throws IOException {
		byte buffer[] = new byte[1024];
		int n = -1;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		out.close();
		in.close();
	}

	static class FileDirException extends Exception {
		private static final long serialVersionUID = 1L;

		private String msg;

		public FileDirException(File file) {
			msg = "file " + file.getAbsolutePath() + "is not a dir";
		}

		public FileDirException(String filePath) {
			msg = "file " + filePath + "is not a dir";
		}

		@Override
		public String getMessage() {
			// TODO Auto-generated method stub
			return msg;
		}
	}

}