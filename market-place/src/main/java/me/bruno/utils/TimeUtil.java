package me.bruno.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TimeUtil {

	private static final String HOUR_FORMAT = "%02d:%02d:%02d";
	private static final String MINUTE_FORMAT = "%02d:%02d";

	private TimeUtil() {
		throw new RuntimeException("Cannot instantiate a utility class.");
	}

	public static String millisToTimer(Long millis) {
		if (millis == null) return "NONE";

		long seconds = millis / 1000L;

		if (seconds > 3600L) {
			return String.format(HOUR_FORMAT, seconds / 3600L, seconds % 3600L / 60L, seconds % 60L);
		} else {
			return String.format(MINUTE_FORMAT, seconds / 60L, seconds % 60L);
		}
	}

	public static String formatIntoMMSS(int secs) {
		// Calculate the seconds to display:
		int seconds = secs % 60;
		secs -= seconds;

		// Calculate the minutes:
		long minutesCount = secs / 60;
		long minutes = minutesCount % 60;
		minutesCount -= minutes;

		long hours = minutesCount / 60;
		return (hours > 0 ? (hours < 10 ? "0" : "") + hours + ":" : "") + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
	}

	public static String formatLongIntoHHMMSS(Long secs) {
		int unconvertedSeconds = secs.intValue();
		return formatIntoMMSS(unconvertedSeconds);
	}

	/**
	 * Return the amount of seconds from milliseconds.
	 * Note: We explicitly use 1000.0F (float) instead of 1000L (long).
	 *
	 * @param millis the amount of time in milliseconds
	 * @return the seconds
	 */
	public static String millisToSeconds(long millis) {
		return new DecimalFormat("#0.0").format(millis / 1000.0F);
	}

	public static String dateToString(Date date, String secondaryColor) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return new SimpleDateFormat("MMM dd yyyy " + (secondaryColor == null ? "" : secondaryColor) + "(hh:mm aa zz)").format(date);
	}

	public static String millisToRoundedTime(long millis) {
		millis += 1L;

		long seconds = millis / 1000L;
		long minutes = seconds / 60L;
		long hours = minutes / 60L;
		long days = hours / 24L;
		long weeks = days / 7L;
		long months = weeks / 4L;
		long years = months / 12L;

		if (years > 0) {
			return years + " year" + (years == 1 ? "" : "s");
		} else if (months > 0) {
			return months + " month" + (months == 1 ? "" : "s");
		} else if (weeks > 0) {
			return weeks + " week" + (weeks == 1 ? "" : "s");
		} else if (days > 0) {
			return days + " day" + (days == 1 ? "" : "s");
		} else if (hours > 0) {
			return hours + " hour" + (hours == 1 ? "" : "s");
		} else if (minutes > 0) {
			return minutes + " minute" + (minutes == 1 ? "" : "s");
		} else {
			return seconds + " second" + (seconds == 1 ? "" : "s");
		}
	}

	public static int parseTime(String time) {

		if (time.equals("0") || time.isEmpty()) {
			return (0);
		}

		String[] lifeMatch = new String[]{"w", "d", "h", "m", "s"};
		int[] lifeInterval = new int[]{604800, 86400, 3600, 60, 1};
		int seconds = 0;

		for (int i = 0; i < lifeMatch.length; i++) {

			final Matcher matcher = Pattern.compile("([0-9]*)" + lifeMatch[i]).matcher(time);

			while (matcher.find()) {
				seconds += Integer.parseInt(matcher.group(1)) * lifeInterval[i];
			}

		}

		return (seconds);
	}


	/**
	 * Formats time into a detailed format. Example: 600 seconds (10 minutes) displays as '10 minutes'
	 *
	 * @param secs The input time, in seconds.
	 * @return The formatted time.
	 */
	public static String formatIntoDetailedString(int secs) {
		if (secs == 0) {
			return "0 seconds";
		}
		int remainder = secs % 86400;

		int days = secs / 86400;
		int hours = remainder / 3600;
		int minutes = (remainder / 60) - (hours * 60);
		int seconds = (remainder % 3600) - (minutes * 60);

		String fDays = (days > 0 ? " " + days + " day" + (days > 1 ? "s" : "") : "");
		String fHours = (hours > 0 ? " " + hours + " hour" + (hours > 1 ? "s" : "") : "");
		String fMinutes = (minutes > 0 ? " " + minutes + " minute" + (minutes > 1 ? "s" : "") : "");
		String fSeconds = (seconds > 0 ? " " + seconds + " second" + (seconds > 1 ? "s" : "") : "");

		return ((fDays + fHours + fMinutes + fSeconds).trim());
	}

}