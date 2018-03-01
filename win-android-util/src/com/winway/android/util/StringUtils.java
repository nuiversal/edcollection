package com.winway.android.util;

import android.R.string;
import android.annotation.SuppressLint;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ?????????????
 * 
 * @author zgq
 * 
 */
public class StringUtils {

	private static StringUtils instance;

	private StringUtils() {
	}

	public static StringUtils getInstance() {
		if (instance == null)
			instance = new StringUtils();
		return instance;
	}

	/**
	 * ???????????????(???????)
	 * 
	 * @param arr
	 * @return
	 */
	public String arr2String(String[] arr) {
		if (arr == null || arr.length <= 0) {
			return null;
		}
		String result = null;
		for (int i = 0; i < arr.length; i++) {
			result = i == 0 ? arr[i] : result + "," + arr[i];
		}
		return result;
	}

	/**
	 * ??????????????(???????)
	 * 
	 * @param arr
	 * @return
	 */
	public String arr2String(int[] arr) {
		if (arr == null || arr.length <= 0) {
			return null;
		}
		String result = null;
		for (int i = 0; i < arr.length; i++) {
			result = i == 0 ? arr[i] + "" : result + "," + arr[i];
		}
		return result;
	}

	/**
	 * ???????????????(???????)
	 * 
	 * @param arr
	 * @return
	 */
	public String arr2String(double[] arr) {
		if (arr == null || arr.length <= 0) {
			return null;
		}
		String result = null;
		for (int i = 0; i < arr.length; i++) {
			result = i == 0 ? arr[i] + "" : result + "," + arr[i];
		}
		return result;
	}

	/**
	 * ????????????????(???????)
	 * 
	 * @param arr
	 * @return
	 */
	public String arr2String(long[] arr) {
		if (arr == null || arr.length <= 0) {
			return null;
		}
		String result = null;
		for (int i = 0; i < arr.length; i++) {
			result = i == 0 ? arr[i] + "" : result + "," + arr[i];
		}
		return result;
	}

	/**
	 * ??????.???????????з????????????
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isEmpty(String input) {
		if (input == null || input.equals("")) {
			return true;
		}
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * ?????URL
	 * 
	 * @param input
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public boolean isUrl(String input) {
		if (isEmpty(input)) {
			return false;
		}
		// ????Сд
		input = input.toLowerCase(Locale.getDefault());
		String regex = "^((https|http|ftp|rtsp|mms)?://)" + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp??user@
				+ "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP?????URL- 199.194.52.184
				+ "|" // ????IP??DOMAIN????????
				+ "([0-9a-z_!~*'()-]+\\.)*" // ????- www.
				+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // ????????
				+ "[a-z]{2,6})" // first level domain- .com or .museum
				+ "(:[0-9]{1,4})?" // ???- :80
				+ "((/?)|" // a slash isn't required if there is no file name
				+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
		return input.matches(regex);
	}

	/**
	 * ???????
	 * 
	 * @param input
	 * @return
	 */
	public boolean isPort(String input) {
		if (isEmpty(input)) {
			return false;
		}
		return input.matches("[0-9]{1,4}");
	}

	/**
	 * ?????·??
	 */
	public boolean isDirectory(String input) {
		if (isEmpty(input))
			return false;
		return input.matches("(^//.|^/|^[a-zA-Z])?:?/.+(/$)?");
	}

	/**
	 * ?????????????з???????
	 * 
	 * @param str
	 * @return
	 */
	public String replaceBlank(String str) {
		if (isEmpty(str)) {
			return null;
		}
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}

	/**
	 * ??????????????????
	 * 
	 * @param arr
	 * @return
	 */
	public int[] parse2IntArray(String[] arr) {
		if (arr == null || arr.length <= 0) {
			return null;
		}
		int array[] = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			array[i] = Integer.parseInt(arr[i]);
		}
		return array;
	}

	/**
	 * ??????????????
	 * 
	 * @param str
	 *            ??????
	 * @param flag
	 *            ??????
	 * @return
	 */
	public int[] parse2IntArray(String str, String flag) {
		return parse2IntArray(str.split(flag));
	}

	/**
	 * ???????????????????
	 * 
	 * @param arr
	 * @return
	 */
	public double[] parse2DoubleArray(String[] arr) {
		if (arr == null || arr.length <= 0) {
			return null;
		}
		double array[] = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			array[i] = Double.parseDouble(arr[i]);
		}
		return array;
	}

	/**
	 * ???????????????
	 * 
	 * @param str
	 *            ??????
	 * @param flag
	 *            ??????
	 * @return
	 */
	public double[] parse2DoubleArray(String str, String flag) {
		return parse2DoubleArray(str.split(flag));
	}

	/**
	 * ????????????????????
	 * 
	 * @param arr
	 * @return
	 */
	public long[] parse2LongArray(String[] arr) {
		if (arr == null || arr.length <= 0) {
			return null;
		}
		long array[] = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			array[i] = Long.parseLong(arr[i]);
		}
		return array;
	}

	/**
	 * ????????????????
	 * 
	 * @param str
	 *            ??????
	 * @param flag
	 *            ??????
	 * @return
	 */
	public long[] parse2LongArray(String str, String flag) {
		return parse2LongArray(str.split(flag));
	}

	/**
	 * ?????????
	 * 
	 * @param chineseStr
	 * @return
	 */
	public boolean isChineseCharacter(String chineseStr) {
		boolean flag = false;
		char[] charArray = chineseStr.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * ??URL?е?·??????·?????б???
	 * 
	 * @param url
	 * @return
	 */
	public static String getEncodeUrl(String url) {
		String result = "";
		String tmpArr[] = url.split("\\/");
		String resArr[] = new String[tmpArr.length - 1];
		for (int i = 0; i < tmpArr.length; i++) {
			if (i < tmpArr.length - 1) {
				resArr[i] = tmpArr[i + 1];
			}

		}
		for (int i = 0; i < resArr.length; i++) {
			try {
				result += "/" + URLEncoder.encode(resArr[i], "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * ??????
	 * 
	 * @param value
	 * @return ???true???????false????????
	 */
	public static boolean isEmpty(Object value) {
		// TODO Auto-generated method stub
		if (value == null) {
			return true;
		} else if (value.getClass().equals(String.class)) {// ??????????
			String tmp = (String) value;
			if (tmp.isEmpty()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * ?ж???????????????????????????
	 * 
	 * @param str1
	 *            ??
	 * @param str2
	 *            ???
	 * @return ???????
	 */
	public static int countStr(String str1, String str2) {
		int counter = 0;
		if (str1.indexOf(str2) == -1) {
			return 0;
		}
		while (str1.indexOf(str2) != -1) {
			counter++;
			str1 = str1.substring(str1.indexOf(str2) + str2.length());
		}
		return counter;
	}

	/**
	 * is null or its length is 0 or it is made by space
	 * 
	 * <pre>
	 * isBlank(null) = true;
	 * isBlank(&quot;&quot;) = true;
	 * isBlank(&quot;  &quot;) = true;
	 * isBlank(&quot;a&quot;) = false;
	 * isBlank(&quot;a &quot;) = false;
	 * isBlank(&quot; a&quot;) = false;
	 * isBlank(&quot;a b&quot;) = false;
	 * </pre>
	 * 
	 * @param str
	 * @return if string is null or its size is 0 or it is made by space, return
	 *         true, else return false.
	 */
	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * is null or its length is 0
	 * 
	 * <pre>
	 * isEmpty(null) = true;
	 * isEmpty(&quot;&quot;) = true;
	 * isEmpty(&quot;  &quot;) = false;
	 * </pre>
	 * 
	 * @param str
	 * @return if string is null or its size is 0, return true, else return
	 *         false.
	 */
	public static boolean isEmpty(CharSequence str) {
		return (str == null || str.length() == 0);
	}

	/**
	 * compare two string
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 * @see ObjectUtils#isEquals(Object, Object)
	 */
	public static boolean isEquals(String actual, String expected) {
		return ObjectUtils.isEquals(actual, expected);
	}

	/**
	 * get length of CharSequence
	 * 
	 * <pre>
	 * length(null) = 0;
	 * length(\"\") = 0;
	 * length(\"abc\") = 3;
	 * </pre>
	 * 
	 * @param str
	 * @return if str is null or empty, return 0, else return
	 *         {@link CharSequence#length()}.
	 */
	public static int length(CharSequence str) {
		return str == null ? 0 : str.length();
	}

	/**
	 * null Object to empty string
	 * 
	 * <pre>
	 * nullStrToEmpty(null) = &quot;&quot;;
	 * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
	 * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static String nullStrToEmpty(Object str) {
		return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
	}

	/**
	 * capitalize first letter
	 * 
	 * <pre>
	 * capitalizeFirstLetter(null)     =   null;
	 * capitalizeFirstLetter("")       =   "";
	 * capitalizeFirstLetter("2ab")    =   "2ab"
	 * capitalizeFirstLetter("a")      =   "A"
	 * capitalizeFirstLetter("ab")     =   "Ab"
	 * capitalizeFirstLetter("Abc")    =   "Abc"
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalizeFirstLetter(String str) {
		if (isEmpty(str)) {
			return str;
		}

		char c = str.charAt(0);
		return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length())
				.append(Character.toUpperCase(c)).append(str.substring(1)).toString();
	}

	/**
	 * encoded in utf-8
	 * 
	 * <pre>
	 * utf8Encode(null)        =   null
	 * utf8Encode("")          =   "";
	 * utf8Encode("aa")        =   "aa";
	 * utf8Encode("????????")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
	 * </pre>
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 *             if an error occurs
	 */
	public static String utf8Encode(String str) {
		if (!isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
			}
		}
		return str;
	}

	/**
	 * encoded in utf-8, if exception, return defultReturn
	 * 
	 * @param str
	 * @param defultReturn
	 * @return
	 */
	public static String utf8Encode(String str, String defultReturn) {
		if (!isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return defultReturn;
			}
		}
		return str;
	}

	/**
	 * get innerHtml from href
	 * 
	 * <pre>
	 * getHrefInnerHtml(null)                                  = ""
	 * getHrefInnerHtml("")                                    = ""
	 * getHrefInnerHtml("mp3")                                 = "mp3";
	 * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
	 * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
	 * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
	 * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
	 * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
	 * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
	 * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
	 * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
	 * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
	 * </pre>
	 * 
	 * @param href
	 * @return <ul>
	 *         <li>if href is null, return ""</li>
	 *         <li>if not match regx, return source</li>
	 *         <li>return the last string that match regx</li>
	 *         </ul>
	 */
	public static String getHrefInnerHtml(String href) {
		if (isEmpty(href)) {
			return "";
		}

		String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
		Pattern hrefPattern = Pattern.compile(hrefReg, Pattern.CASE_INSENSITIVE);
		Matcher hrefMatcher = hrefPattern.matcher(href);
		if (hrefMatcher.matches()) {
			return hrefMatcher.group(1);
		}
		return href;
	}

/**
     * process special char in html
     * 
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     * 
     * @param source
     * @return
     */
	public static String htmlEscapeCharsToString(String source) {
		return StringUtils.isEmpty(source) ? source : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
				.replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
	}

	/**
	 * transform half width char to full width char
	 * 
	 * <pre>
	 * fullWidthToHalfWidth(null) = null;
	 * fullWidthToHalfWidth("") = "";
	 * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
	 * fullWidthToHalfWidth("???????磥??) = "!\"#$%&";
	 * </pre>
	 * 
	 * @param s
	 * @return
	 */
	public static String fullWidthToHalfWidth(String s) {
		if (isEmpty(s)) {
			return s;
		}

		char[] source = s.toCharArray();
		for (int i = 0; i < source.length; i++) {
			if (source[i] == 12288) {
				source[i] = ' ';
				// } else if (source[i] == 12290) {
				// source[i] = '.';
			} else if (source[i] >= 65281 && source[i] <= 65374) {
				source[i] = (char) (source[i] - 65248);
			} else {
				source[i] = source[i];
			}
		}
		return new String(source);
	}

	/**
	 * transform full width char to half width char
	 * 
	 * <pre>
	 * halfWidthToFullWidth(null) = null;
	 * halfWidthToFullWidth("") = "";
	 * halfWidthToFullWidth(" ") = new String(new char[] {12288});
	 * halfWidthToFullWidth("!\"#$%&) = "???????磥??";
	 * </pre>
	 * 
	 * @param s
	 * @return
	 */
	public static String halfWidthToFullWidth(String s) {
		if (isEmpty(s)) {
			return s;
		}

		char[] source = s.toCharArray();
		for (int i = 0; i < source.length; i++) {
			if (source[i] == ' ') {
				source[i] = (char) 12288;
				// } else if (source[i] == '.') {
				// source[i] = (char)12290;
			} else if (source[i] >= 33 && source[i] <= 126) {
				source[i] = (char) (source[i] + 65248);
			} else {
				source[i] = source[i];
			}
		}
		return new String(source);
	}

}
