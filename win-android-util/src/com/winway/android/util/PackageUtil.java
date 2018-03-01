package com.winway.android.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

public class PackageUtil {

	/**
	 * 获取Java包里面的所有Java类
	 * @param ctx   
	 * @param entityPackage   Java包名，例如com.winway.android.graphical.data.entity
	 * @param hasChilds    是否将子包下面的实体类也返回
	 * @return
	 */
	public static List<Class<?>> getPackageClasses(Context ctx, String entityPackage, boolean hasChilds) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		try {
			PathClassLoader classLoader = (PathClassLoader) Thread.currentThread().getContextClassLoader();
			DexFile dex = new DexFile(ctx.getPackageResourcePath());
			Enumeration<String> entries = dex.entries();
			while (entries.hasMoreElements()) {
				String entryName = entries.nextElement();

				if (entryName.contains(entityPackage)) {
					if (!hasChilds) {
						String packagegname = entryName.substring(0, entryName.lastIndexOf("."));
						if (packagegname.length() > entityPackage.length()) {
							// 子包
							continue;
						}
					}
					// 疑问：Class.forName(entryName);这种方式不知道为什么返回null
					Class<?> entryClass = Class.forName(entryName, true, classLoader);
					classes.add(entryClass);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}
}