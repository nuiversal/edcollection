package com.winway.android.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 日期格式化工具类
 * 
 * @author mr-lao
 *
 */
public class DateParserUtil {

	/**
	 * 将字符串转化成日期类型
	 * 
	 * @param pattern
	 *            日期格式化匹配
	 * @param date
	 *            日期字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date parser(String pattern, String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(date);
	}

	static String datePatternsArray[] = { "yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM", "yyyy/MM",
			"yyyy年MM月dd日", "yyyy年MM月" };
	static String timePatternsArray[] = { "HH:mm:ss", "HH/mm/ss", "HH:mm", "HH/mm", "HH时mm分ss秒",
			"HH时mm分" };

	static ArrayList<String> patternArray = null;

	static void addList(ArrayList<String> list, String[] array) {
		for (String string : array) {
			list.add(string);
		}
	}

	public static ArrayList<String> createPatternArray() {
		if (null == patternArray) {
			patternArray = new ArrayList<String>();
			addList(patternArray, datePatternsArray);
			for (int i = 0; i < datePatternsArray.length; i++) {
				for (int j = 0; j < timePatternsArray.length; j++) {
					String p1 = datePatternsArray[i] + " " + timePatternsArray[j];
					if (patternArray.size() == 0) {
						patternArray.add(p1);
						continue;
					}
					boolean hasadd = false;
					for (int k = 0; k < patternArray.size(); k++) {
						String p2 = patternArray.get(k);
						if (p1.length() > p2.length()) {
							patternArray.add(k, p1);
							hasadd = true;
							break;
						}
					}
					if (!hasadd) {
						patternArray.add(p1);
					}
				}
			}
			addList(patternArray, timePatternsArray);
			patternMap = new HashMap<Integer, ArrayList<String>>();
			for (String str : patternArray) {
				ArrayList<String> list = patternMap.get(str.length());
				if (list == null) {
					list = new ArrayList<String>();
				}
				list.add(str);
				patternMap.put(str.length(), list);
			}
		}
		return patternArray;
	}

	static HashMap<Integer, ArrayList<String>> patternMap = null;

	public static ArrayList<String> getPattern(int length) {
		if (patternMap.containsKey(length)) {
			return patternMap.get(length);
		}
		return patternArray;
	}

	/**
	 * 超强字符串日期转换方法
	 * 
	 * @param defauklPattern
	 *            默认的日期匹配表达式
	 * @param date
	 * @return
	 */
	public static Date superParser(String defauklPattern, String date) {
		Date d = null;
		try {
			d = parser(defauklPattern, date);
		} catch (ParseException e) {
			createPatternArray();
			ArrayList<String> patternArray = getPattern(date.length());
			if (null != patternArray) {
				for (String p : patternArray) {
					try {
						d = parser(p, date);
						if (d != null) {
							break;
						}
					} catch (ParseException e1) {

					}
				}
			}
		}
		if (null == d) {
			try {
				d = new Date(Long.parseLong(date));
			} catch (Exception e) {

			}
		}
		return d;
	}

	public static void main(String[] args) {
		String date = "2016年10月10日 16时40分56秒";
		Date superParser = superParser("", date);
		System.out.println(superParser);
	}
}
