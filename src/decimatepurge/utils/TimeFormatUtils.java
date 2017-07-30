package decimatepurge.utils;

public class TimeFormatUtils {

	public static String getTimeFormattedDaysHoursMinutesSeconds(long then) {
		long time = then - System.currentTimeMillis();

		int seconds = (int) (((long)time / 1000) % 60);
		int minutes = (int) (((long)time / (1000 * 60)) % 60);
		int hours = (int) (((long)time / (1000 * 60 * 60)) % 24);
		int days = (int) (((long)time / (1000 * 60 * 60 * 24)));

		return (days == 0 ? "" : days + "d ") + (hours == 0 ? "" : hours + "h ") + (minutes == 0 ? "" : minutes + "m ") + (seconds == 0 ? "" : seconds + "s");
	}
	
	public static String getTimeFormatted(long time) {
		int seconds = (int) (((long)time / 1000) % 60);
		int minutes = (int) (((long)time / (1000 * 60)) % 60);
		int hours = (int) (((long)time / (1000 * 60 * 60)) % 24);
		int days = (int) (((long)time / (1000 * 60 * 60 * 24)));

		return (days == 0 ? "" : days + "d ") + (hours == 0 ? "" : hours + "h ") + (minutes == 0 ? "" : minutes + "m ") + (seconds == 0 ? "" : seconds + "s");
	}
	
	public static String getTimeFormattedDaysHoursMinutesSeconds(long then, String complete) {
		if (System.currentTimeMillis() >= then) {
			return complete;
		}
		return getTimeFormattedDaysHoursMinutesSeconds(then);
	}

	public static String getTimeFormattedMinutesSeconds(long then, String complete) {
		if (System.currentTimeMillis() >= then) {
			return complete;
		}
		long time = then - System.currentTimeMillis();

		int seconds = (int) ((time / 1000) % 60);
		int minutes = (int) ((time / (1000 * 60)) % 60);

		return minutes + "m " + seconds + "s";
	}

}
