package cn.yznu.rzgskhgl.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 时间工具类
 * 
 * @author 张伟
 * @date 2016-12-2
 */
public class DateUtil {

	/**
	 * 获取系统时间的前6月（2016-1）
	 * 
	 * @return
	 */
	public static List<String> beforeJune() {
		Calendar cal = Calendar.getInstance();
		// int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		/*
		 * int dow = cal.get(Calendar.DAY_OF_WEEK); int dom =
		 * cal.get(Calendar.DAY_OF_MONTH); int doy =
		 * cal.get(Calendar.DAY_OF_YEAR);
		 */

		List<String> m = new ArrayList<String>();
		if (month >= 6) {
			for (int i = 0; i < 6; i++) {
				m.add(i, year + "-" + (month - 5 + i));
			}
		} else if (month == 5) {
			for (int i = 0; i < 6; i++) {
				if (i == 0) {
					m.add(i, (year - 1) + "-" + (month + 7));
				} else {
					m.add(i, year + "-" + (month - 5 + i));
				}
			}
		} else if (month == 4) {
			for (int i = 0; i < 6; i++) {
				if (i <= 1) {
					m.add(i, (year - 1) + "-" + (month + 7 + i));
				} else {
					m.add(i, year + "-" + (month - 5 + i));
				}
			}
		} else if (month == 3) {
			for (int i = 0; i < 6; i++) {
				if (i <= 2) {
					m.add(i, (year - 1) + "-" + (month + 7 + i));
				} else {
					m.add(i, year + "-" + (month - 5 + i));
				}
			}
		} else if (month == 2) {
			for (int i = 0; i < 6; i++) {
				if (i <= 3) {
					m.add(i, (year - 1) + "-" + (month + 7 + i));
				} else {
					m.add(i, year + "-" + (month - 5 + i));
				}
			}
		} else if (month == 1) {
			for (int i = 0; i < 6; i++) {
				if (i <= 4) {
					m.add(i, (year - 1) + "-" + (month + 7 + i));
				} else {
					m.add(i, year + "-" + (month - 5 + i));
				}
			}
		} else {

		}
		return m;
	}
	/**
	 * 获取当前 年月
	 * @return
	 */
	public static String currentYears() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		return year + "-" + month;
	}
	public static void main(String[] args) {
		System.out.println(DateUtil.currentYears());
	}

}
