package com.data.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sunzhiwei on 2016/11/1.
 */
public class DateUtil {
	public enum WeekDay {
		MON, TUE, WED, THU, FRI, SAT, SUN;
	}

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static int getDayForWeek(String date) throws ParseException {
		int dayOfWeek = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(simpleDateFormat.parse(date));
		if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
			dayOfWeek = 7;
		} else {
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		}
//        System.out.println(dayOfWeek);
		return dayOfWeek;
	}

	public static String addDate(String date, int num) throws ParseException {
		String result = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(simpleDateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + num);
//        System.out.println(simpleDateFormat.format(calendar.getTime()));
		result = simpleDateFormat.format(calendar.getTime());
		return result;
	}

	public static String subtractDate(String date, int num) throws ParseException {
		String result = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(simpleDateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - num);
//        System.out.println(simpleDateFormat.format(calendar.getTime()));
		result = simpleDateFormat.format(calendar.getTime());
		return result;
	}

	public static String subtractMonth(String date, int num) throws ParseException {
		String result = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(simpleDateFormat.parse(date));
		calendar.add(Calendar.MONTH, num);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		result = sf.format(calendar.getTime());
		return result;
	}

	public static void main(String[] args) throws ParseException {
		String date = simpleDateFormat.format(new Date());
		System.out.println(date);

		String month = subtractMonth(date, -12);

		System.out.println(month);

//        String date = "2016-10-31";
		int dayOfWeek = DateUtil.getDayForWeek(date);
//        String endDate = date;
		int preWeek = 2;
		if (dayOfWeek != 7) {
			preWeek -= 1;
		}
		String endDate = subtractDate(date, dayOfWeek);
		System.out.println("endDate : " + endDate);
		// 向前算第几周的那个周一 (num * 7 - 1)，
		endDate = "2016-11-11";
		String startDate = addDate(endDate, 59);
		System.out.println("startDate : " + startDate);
	}
}
