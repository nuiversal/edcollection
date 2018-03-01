package com.winway.android.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Class解释器
 * 
 * @author mr-lao
 * 
 */
public class ClassMenager {
	public static boolean classMenagerLog = false;
	static String TAG = "ClassMenager";
	static Cache cache = new Cache();

	/**
	 * 缓存器
	 * 
	 * @author mr-lao
	 * 
	 */
	static class Cache {
		public final HashMap<String, ClassMessage> cache = new HashMap<String, ClassMessage>();

		public final ClassMessage get(Class<?> cls) {
			return cache.get(cls.getName());
		}

		public final ClassMessage get(String clsName) {
			return cache.get(clsName);
		}

		public final void put(ClassMessage clsMessage) {
			cache.put(clsMessage.className, clsMessage);
		}

		public final void put(String name, ClassMessage clsMessage) {
			cache.put(name, clsMessage);
		}
	}

	/**
	 * 用于存放类解析出来的信息的实体，如类名，字段，方法
	 * 
	 * @author mr-lao
	 * 
	 */
	static class ClassMessage {
		// 类名
		public String className;
		// 类注解
		public Annotation[] classAnnotations;
		// 字段
		public Field[] fileds;
		// 方法
		public Method[] methods;
		// 字段名
		public List<String> fieldNames;
		// 方法名
		public List<String> methodNames;
		// 字段注解
		public HashMap<String, Annotation[]> fieldAnnotations;
		// 方法注解
		public HashMap<String, Annotation[]> methodAnnotations;
	}

	/**
	 * 解释类
	 * 
	 * @param cls
	 */
	private static void scanner(Class<?> cls) {
		ClassMessage clsMessage = new ClassMessage();

		Class<?> superclass = cls.getSuperclass();
		ClassMessage superClsMessage = null;
		if (superclass != Object.class) {
			superClsMessage = cache.get(superclass);
			if (null == superClsMessage) {
				scanner(superclass);
				superClsMessage = cache.get(superclass);
			}
		}
		clsMessage.className = cls.getName();
		if (null != superClsMessage) {
			clsMessage.fileds = mergeArray(cls.getDeclaredFields(), superClsMessage.fileds);
			clsMessage.methods = mergeArray(cls.getDeclaredMethods(), superClsMessage.methods);
			clsMessage.classAnnotations = mergeArray(cls.getAnnotations(),
					superClsMessage.classAnnotations);
		} else {
			clsMessage.fileds = cls.getDeclaredFields();
			clsMessage.methods = cls.getDeclaredMethods();
			clsMessage.classAnnotations = cls.getAnnotations();
		}

		ArrayList<String> fieldNames = new ArrayList<String>();
		HashMap<String, Annotation[]> fieldAnnotations = new HashMap<String, Annotation[]>();
		for (Field f : clsMessage.fileds) {
			fieldNames.add(f.getName());
			fieldAnnotations.put(f.getName(), f.getAnnotations());
		}
		HashMap<String, Annotation[]> methodAnnotations = new HashMap<String, Annotation[]>();
		ArrayList<String> methodNames = new ArrayList<String>();
		for (Method m : clsMessage.methods) {
			methodNames.add(m.getName());
			methodAnnotations.put(m.getName(), m.getAnnotations());
		}

		clsMessage.fieldNames = fieldNames;
		clsMessage.methodNames = methodNames;
		clsMessage.fieldAnnotations = fieldAnnotations;
		clsMessage.methodAnnotations = methodAnnotations;

		cache.put(clsMessage);

	}

	/**
	 * 合并数组
	 * 
	 * @param a1
	 * @param a2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] mergeArray(T[]... array) {
		if (null == array || array.length == 0) {
			return null;
		}
		Class<T> cls = null;
		int size = 0;
		for (T[] ar : array) {
			if (null == ar || ar.length == 0) {
				continue;
			}
			size += ar.length;
			if (null == cls) {
				cls = (Class<T>) ar[0].getClass();
			}
		}
		if (size == 0) {
			return null;
		}

		T[] tArray = (T[]) Array.newInstance(cls, size);
		int index = 0;
		for (T[] ar : array) {
			if (null == ar || ar.length == 0) {
				continue;
			}
			for (T a : ar) {
				tArray[index] = a;
				index++;
			}
		}
		return (T[]) tArray;
	}

	/**
	 * 获得ClassManage实体
	 * 
	 * @param cls
	 * @return
	 */
	public static ClassMessage getClassMessage(Class<?> cls) {
		ClassMessage clsMessage = cache.get(cls);
		if (null == clsMessage) {
			scanner(cls);
			clsMessage = cache.get(cls);
		}
		return clsMessage;
	}

	/**
	 * 获得类所有公开方法的方法名
	 * 
	 * @param cls
	 * @return
	 */
	public static List<String> getMethodNames(Class<?> cls) {
		return getClassMessage(cls).methodNames;
	}

	/**
	 * 获取类所有字段名称
	 * 
	 * @param cls
	 * @return
	 */
	public static List<String> getFieldNames(Class<?> cls) {
		return getClassMessage(cls).fieldNames;
	}

	/**
	 * 获取类中所有字段的所有注解
	 * 
	 * @param cls
	 * @return
	 */
	public static HashMap<String, Annotation[]> getAllFileAnnotations(Class<?> cls) {
		return getClassMessage(cls).fieldAnnotations;
	}

	/**
	 * 获取类中所有公有方法的所有注解
	 * 
	 * @param cls
	 * @return
	 */
	public static HashMap<String, Annotation[]> getAllMethodAnnotations(Class<?> cls) {
		return getClassMessage(cls).methodAnnotations;
	}

	/**
	 * 获取字段所有注解
	 * 
	 * @param cls
	 * @param fileName
	 * @return
	 */
	public static Annotation[] getFiledAnnotations(Class<?> cls, String fileName) {
		return getAllFileAnnotations(cls).get(fileName);
	}

	public static final int MODE_FIELD = 0;
	public static final int MODE_METHOD = 1;
	public static final int MODE_FIELD_METHOD = 2;

	/**
	 * 获得字段域
	 * 
	 * @param cls
	 * @param classNames
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static HashMap<String, List<Annotation>> getAnnotations(Class<?> cls, int mode,
			String... classNames) throws ClassNotFoundException {
		HashMap<String, List<Annotation>> map = new HashMap<String, List<Annotation>>();
		HashMap<String, Annotation[]> allFileAnnotations = getAllFileAnnotations(cls);
		HashMap<String, Annotation[]> allMethodAnnotations = getAllMethodAnnotations(cls);
		if (mode == MODE_FIELD || mode == MODE_FIELD_METHOD) {
			fielterMap(map, allFileAnnotations, classNames);
		}
		if (mode == MODE_METHOD || mode == MODE_FIELD_METHOD) {
			fielterMap(map, allMethodAnnotations, classNames);
		}
		return map;
	}

	/**
	 * 过滤
	 * 
	 * @param desc
	 * @param source
	 * @param classNames
	 * @throws ClassNotFoundException
	 */
	static <K, V> void fielterMap(HashMap<K, List<V>> desc, HashMap<K, V[]> source,
			String... classNames) {
		Set<K> keySet = source.keySet();
		for (K key : keySet) {
			V[] vs = source.get(key);
			if (null != vs && vs.length > 0) {
				// 过滤数据，只获取类型符合的数据
				ArrayList<V> list = new ArrayList<V>();
				for (V v : vs) {
					boolean add = false;
					for (String name : classNames) {
						if (v.toString().contains(name)) {
							add = true;
							break;
						}
					}
					if (add) {
						list.add(v);
					}
				}
				if (!list.isEmpty()) {
					desc.put(key, list);
				}
			}
		}
	}

	/**
	 * 获取方法名的所有注解
	 * 
	 * @param cls
	 * @param methodName
	 * @return
	 */
	public static Annotation[] getMethodAnnotations(Class<?> cls, String methodName) {
		return getAllMethodAnnotations(cls).get(methodName);
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	static String firstCharToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static void main(String[] args) {
		String str = firstCharToUpperCase("this is my wife");
		System.out.println(str);
	}

	/**
	 * 返回字段的SET方法
	 * 
	 * @param cls
	 * @param fieldName
	 * @return
	 */
	public static Method getSetMethod(Class<?> cls, String fieldName) {
		return getMethod(cls, "set" + firstCharToUpperCase(fieldName));
	}

	/**
	 * 返回字段的GET方法
	 * 
	 * @param cls
	 * @param fieldName
	 *            类型为非boolean
	 * @return
	 */
	public static Method getGetMethod(Class<?> cls, String fieldName) {
		return getMethod(cls, "get" + firstCharToUpperCase(fieldName));
	}

	/**
	 * 返回字段的GET方法
	 * 
	 * @param cls
	 * @param fieldName
	 *            类型为boolean
	 * @return
	 */
	public static Method getIsMethod(Class<?> cls, String fieldName) {
		return getMethod(cls, "is" + firstCharToUpperCase(fieldName));
	}

	private static HashMap<String, Method> methodMap = new HashMap<String, Method>();

	/**
	 * 根据方法名获取方法(忽略方法名大小写)
	 * 
	 * @param cls
	 * @param methodName
	 * @return
	 */
	public static Method getMethod(Class<?> cls, String methodName) {
		String name = cls.getName() + methodName;
		if (null != methodMap.get(name)) {
			return methodMap.get(name);
		}
		ClassMessage classMessage = getClassMessage(cls);
		if (null == classMessage.methods) {
			return null;
		}
		// 精确匹配
		for (Method m : classMessage.methods) {
			if (m.getName().equals(methodName)) {
				methodMap.put(name, m);
				return m;
			}
		}
		// 忽略方法名大小写匹配
		for (Method m : classMessage.methods) {
			if (m.getName().toLowerCase().equals(methodName.toLowerCase())) {
				methodMap.put(name, m);
				return m;
			}
		}
		return null;
	}

	private static HashMap<String, Field> fieldMap = new HashMap<String, Field>();

	/**
	 * 获取字段
	 * 
	 * @param cls
	 * @param fieldName
	 * @return
	 */
	public static Field getField(Class<?> cls, String fieldName) {
		String name = cls.getName() + fieldName;
		if (null != fieldMap.get(name)) {
			return fieldMap.get(name);
		}
		ClassMessage classMessage = getClassMessage(cls);
		if (null == classMessage.fileds) {
			return null;
		}
		for (Field f : classMessage.fileds) {
			if (f.getName().equals(fieldName)) {
				fieldMap.put(name, f);
				return f;
			}
		}
		return null;
	}

	/**
	 * 此方法只适合用于set方法，方法参数有且只有一个，方法的参数必须为原始数据类型
	 * 
	 * @update 更新时间：2016-11-3 23:24:59
	 * @update-content 增加时间数据类型
	 * @param m
	 * @param value
	 */
	public static void setMethod(Object obj, Method m, String value, SimpleDateFormat sdf) {
		try {
			Object val = null;
			Class<?>[] parameterTypes = m.getParameterTypes();
			Class<?> cls = parameterTypes[0];
			if (cls == Integer.class || cls == int.class) {
				val = Integer.parseInt(value);
			} else if (cls == Double.class || cls == double.class) {
				val = Double.parseDouble(value);
			} else if (cls == Long.class || cls == long.class) {
				val = Long.parseLong(value);
			} else if (cls == Float.class || cls == float.class) {
				val = Float.parseFloat(value);
			} else if (cls == String.class) {
				val = value;
			} else if (cls == Boolean.class || cls == boolean.class) {
				val = Boolean.parseBoolean(value);
			} else if (cls == Date.class) {
				if (null != sdf) {
					try {
						val = sdf.parse(value);
					} catch (ParseException e) {
					}
				}
				if (null == val) {
					val = DateParserUtil.superParser("", value);
				}
			}
			m.invoke(obj, val);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
