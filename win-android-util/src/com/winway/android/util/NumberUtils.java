package com.winway.android.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * ?????????????
 * 
 * @author zgq
 *
 */
public class NumberUtils {
	/**
	 * ?ж????????????
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isInteger(Object value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		String mstr = value.toString();
		Pattern pattern = Pattern.compile("^-?\\d+{1}");
		return pattern.matcher(mstr).matches();
	}

	/**
	 * ?ж????????????(????С??)
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isDigit(Object value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		String mstr = value.toString();
		Pattern pattern = Pattern.compile("^\\d+$|^\\d+\\.\\d+$|-\\d+$");
		return pattern.matcher(mstr).matches();
	}

	/**
	 * ?????????????
	 * 
	 * @param value
	 *            ???????????
	 * @param precision
	 *            ????(С??????λ??)
	 * @return
	 */
	public static String format(Object value, Integer precision) {
		Double number = 0.0;
		if (NumberUtils.isDigit(value)) {
			number = new Double(value.toString());
		}
		precision = (precision == null || precision < 0) ? 2 : precision;
		BigDecimal bigDecimal = new BigDecimal(number);
		return bigDecimal.setScale(precision, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * ?????????????
	 * 
	 * @param value
	 * @return
	 */
	public static String format(Object value) {
		return NumberUtils.format(value, 2);
	}

	/**
	 * ??????Integer???????????????????0
	 * 
	 * @param value
	 * @param replace
	 *            ????0????null???滻?
	 * @return
	 */
	public static Integer parseInteger(Object value, Integer replace) {
		if (!NumberUtils.isInteger(value)) {
			return replace;
		}
		return new Integer(value.toString());
	}

	/**
	 * ??????Integer???????????????????0
	 * 
	 * @param value
	 * @return
	 */
	public static Integer parseInteger(Object value) {
		return NumberUtils.parseInteger(value, 0);
	}

	/**
	 * ??????Long??
	 * 
	 * @param value
	 * @param replace
	 *            ????0????null???滻?
	 * @return
	 */
	public static Long parseLong(Object value, Long replace) {
		if (!NumberUtils.isInteger(value)) {
			return replace;
		}
		return new Long(value.toString());
	}

	/**
	 * ??????Long???????????????????0
	 * 
	 * @param value
	 * @return
	 */
	public static Long parseLong(Object value) {
		return NumberUtils.parseLong(value, 0L);
	}

	/**
	 * ??????Double??
	 * 
	 * @param value
	 * @param replace
	 *            replace ????0????null???滻?
	 * @return
	 */
	public static Double parseDouble(Object value, Double replace) {
		if (!NumberUtils.isDigit(value)) {
			return replace;
		}
		return new Double(value.toString());
	}

	/**
	 * ??????Double???????????????????0
	 * 
	 * @param value
	 * @return
	 */
	public static Double parseDouble(Object value) {
		return NumberUtils.parseDouble(value, 0.0);
	}

	/**
	 * ??char????????????????
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(char value) {
		byte[] bt = new byte[2];
		for (int i = 0; i < bt.length; i++) {
			bt[i] = (byte) (value >>> (i * 8));
		}
		return bt;
	}

	/**
	 * ??short????????????????
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(short value) {
		byte[] bt = new byte[2];
		for (int i = 0; i < bt.length; i++) {
			bt[i] = (byte) (value >>> (i * 8));
		}
		return bt;
	}

	/**
	 * ??int????????????????
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(int value) {
		byte[] bt = new byte[4];
		for (int i = 0; i < bt.length; i++) {
			bt[i] = (byte) (value >>> (i * 8));
		}
		return bt;
	}

	/**
	 * ??long????????????????
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(long value) {
		byte[] bt = new byte[8];
		for (int i = 0; i < bt.length; i++) {
			bt[i] = (byte) (value >>> (i * 8));
		}
		return bt;
	}

	/**
	 * ??short???????????????????????????
	 * 
	 * @param index
	 *            ????
	 * @param values
	 *            ???????
	 * @param value
	 *            ?????????
	 */
	public static void insert(int index, byte[] values, short value) {
		byte[] bt = NumberUtils.toBytes(value);
		System.arraycopy(bt, 0, values, index, 2);
	}

	/**
	 * ??int???????????????????????????
	 * 
	 * @param index
	 *            ????
	 * @param values
	 *            ???????
	 * @param value
	 *            ?????????
	 */
	public static void insert(int index, byte[] values, int value) {
		byte[] bt = NumberUtils.toBytes(value);
		System.arraycopy(bt, 0, values, index, 4);
	}

	/**
	 * ??long???????????????????????????
	 * 
	 * @param index
	 *            ????
	 * @param values
	 *            ???????
	 * @param value
	 *            ?????????
	 */
	public static void insert(int index, byte[] values, long value) {
		byte[] bt = NumberUtils.toBytes(value);
		System.arraycopy(bt, 0, values, index, 8);
	}

	/**
	 * ??????????????
	 * 
	 * @param value
	 *            ????????
	 * @return
	 */
	public static int byteToInt(byte value) {
		if (value < 0) {
			return value + 256;
		}
		return value;
	}
}
