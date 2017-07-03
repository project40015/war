package decimatepurge.utils;

public class TimeFormatUtils {

	public static String getTimeFormattedDaysHoursMinutesSeconds(long then, String complete) {
		if (System.currentTimeMillis() >= then) {
			return complete;
		}
		long time = then - System.currentTimeMillis();

		int seconds = (int) ((time / 1000) % 60);
		int minutes = (int) ((time / (1000 * 60)) % 60);
		int hours = (int) ((time / (1000 * 60 * 60)) % 24);
		int days = (int) ((time / (1000 * 60 * 60 * 24)));

		return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
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
