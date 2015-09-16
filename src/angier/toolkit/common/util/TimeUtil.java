package angier.toolkit.common.util;

import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtil {

	public TimeUtil() {
	}

	// 返回格式化的日期
	public static String getFullDate() {
		String formater = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(formater);
		Date myDate = new Date();
		return format.format(myDate);
	}

	// 返回格式化的日期
	public static String getFullDate(String sDate) {
		try {
			String formater = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date = format.parse(sDate);
			formater = "yyyy-MM-dd";
			format = new SimpleDateFormat(formater);
			return format.format(date);
		} catch (Exception ex) {
			return "";
		}
	}

	// 返回格式化的日期
	public static String getCurDateTime() {
		String formater = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat format = new SimpleDateFormat(formater);
		Date myDate = new Date();
		return format.format(myDate);
	}

	// 返回格式化的日期
	public static String getFullDateTime(String sDate) {
		try {
			String formater = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date = format.parse(sDate);
			return format.format(date);
		} catch (Exception ex) {
			return "";
		}
	}

	// 返回格式化的日期
	public static String getSimpleDate() {
		String formater = "yyyy-M-d";
		SimpleDateFormat format = new SimpleDateFormat(formater);
		Date myDate = new Date();
		return format.format(myDate);
	}

	public static String getNowDate() {
		String formater = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(formater);
		Date nowDate = new Date();
		return format.format(nowDate);
	}

	// 返回格式化的日期
	public static String getDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date da = null;
		try {
			da = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return format.format(da);
	}

	/**
	 * 时间格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param sDate
	 * @param iDay
	 * @return
	 */
	public static String getSomeDate(String sDate, int iDay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(sDate);
			long Time = (date.getTime() / 1000) + 60 * 60 * 24 * iDay;
			date.setTime(Time * 1000);
			return format.format(date);
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 时间格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param sDate
	 * @param min
	 * @return
	 */
	public static String getSomeMin(String sDate, int min) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(sDate);
			long Time = (date.getTime() / 1000) + 60 * min;
			date.setTime(Time * 1000);
			return format.format(date);
		} catch (Exception ex) {
			return "";
		}
	}

	public static String getSomeDatesmall(String sDate, int iDay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(sDate);
			long Time = (date.getTime() / 1000) + 60 * 60 * 24 * iDay;
			date.setTime(Time * 1000);
			return format.format(date);
		} catch (Exception ex) {
			return "";
		}
	}

	// 返回日期的差值

	public static int getNumericDatePeriod(String sDate) {
		int iTime = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(sDate);
			Date date1 = new Date();
			iTime = (int) ((date.getTime() - date1.getTime()) / 1000L);
		} catch (Exception ex) {
		}
		return iTime;
	}

	/**
	 * 计算2个日期之间的天数
	 * 
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public static int getNumDatePeriod(String sDate, String eDate) {
		int iTime = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date sdate = format.parse(sDate);
			Date edate = format.parse(eDate);
			iTime = (int) ((edate.getTime() - sdate.getTime()) / (24 * 3600 * 1000));
		} catch (Exception ex) {
		}
		return iTime;
	}

	// 判断日期的前后
	public static boolean isDateLater(String sDate, String sDate1) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(sDate);
			Date date1 = format.parse(sDate1);
			if (date.after(date1))
				return true;
			else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}

	// 判断日期的前后
	public static boolean isDateLatersmall(String sDate, String sDate1) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(sDate);
			Date date1 = format.parse(sDate1);
			if (date.after(date1))
				return true;
			else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public static String getNextDate() {
		String formater = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(formater);
		Date myDate = new Date();
		myDate.setYear(myDate.getYear() + 1);
		return format.format(myDate);
	}

	public static String getYear() {
		String formater = "yyyy";
		SimpleDateFormat format = new SimpleDateFormat(formater);
		Date nowYear = new Date();
		return format.format(nowYear);
	}

	/**
	 * 返回当前时间的星期字段。注意月和日从 0开始
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static int getWeek(int year, int month, int date) {
		int week = -1;
		try {
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, date);
			week = ca.get(Calendar.DAY_OF_WEEK);
		} catch (Exception e) {
			System.out.println("lrqikan Class TimeUtil getWeek error:"
					+ e.getMessage());
		}
		return week;
	}

	/**
	 * 在指定的时间里加上或者减去 自然月
	 * 
	 * @param sDate
	 * @param month
	 * @return
	 */
	public static String getSomeDateForMonth(String sDate, int month) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
			Date date = format.parse(sDate);
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			ca.add(Calendar.MONTH, month);
			return format.format(ca.getTime());
		} catch (Exception e) {
			System.out.println("lrqikan Class TimeUtil getSomeDateForMonth error:"
							+ e.getMessage());
			return "";
		}
	}

	/**
	 * 当前月的前几个月1号
	 * 
	 * @param n
	 * @return
	 */
	public static String getNMonth1DayBeforeCurrentDay(int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(c.getTime());
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MONTH, n);
		Date date = c.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
		// return ""+c.get(c.YEAR)+"-"+(c.get(c.MONTH)+1)+"-"+c.get(c.DATE);
	}

	public static String getNMonth31DayBeforeCurrentDay(int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(c.getTime());
		c.set(Calendar.DAY_OF_MONTH, 31);
		c.add(Calendar.MONTH, n);
		Date date = c.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
		// return ""+c.get(c.YEAR)+"-"+(c.get(c.MONTH)+1)+"-"+c.get(c.DATE);
	}

	@SuppressWarnings("deprecation")
	public static String getBeforeDate() {
		String formater = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(formater);
		Date myDate = new Date();
		myDate.setDate(myDate.getDate() - 1);
		return format.format(myDate);
	}

	/**
	 * 
	 * @param ts Timestamp
	 * @param patten 如yyyy/MM/dd HH:mm:ss 
	 * @return
	 */
	public static String timestampToStr(Timestamp ts,String patten) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat(patten);
		try {
			tsStr = sdf.format(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}
	
	/**
	 * 
	 * @param dateStr  格式必是yyyy-mm-dd hh:mm:ss
	 * @return
	 */
	public static Timestamp strToTimestamp(String dateStr) {
		Timestamp ts = null;
		try {
			ts = Timestamp.valueOf(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}
	
}
