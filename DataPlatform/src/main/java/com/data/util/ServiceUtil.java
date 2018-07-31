package com.data.util;

import java.util.Calendar;
import java.util.Date;

public class ServiceUtil {
	public static long hash(String[] values) {
		long result = 17;
		for (String v : values) result = 37 * result + v.hashCode();
		return result;
	}

	public static Date[] dateRange(Date start, int unit, int period) {
		Date[] dates = new Date[period];

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		for (int i = 0; i < period; i++) {
			dates[i] = calendar.getTime();
			calendar.add(unit, 1);
		}

		return dates;
	}

	/**
	 * 计算百分比并格式化
	 *
	 * @param numerator   被除数
	 * @param denominator 除数
	 * @return 百分比(保留两位小数)
	 */
	static public String percent(Double numerator, Double denominator) {
		if (denominator == 0) {
			return "0%";
		} else {
			return String.format("%.2f%%", numerator / denominator * 100);
		}
	}
}
