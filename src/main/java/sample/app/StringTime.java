package sample.app;

/**
 * 文字列時刻
 */
public class StringTime {

	/** 時 */
	private final String hour;
	/** 分 */
	private final String minute;

	/**
	 * コンストラクター
	 * @param hour
	 * @param minute
	 */
	public StringTime(String hour, String minute) {
		this.hour = hour;
		this.minute = minute;
	}

	public String getHour() {
		return hour;
	}

	public String getMinute() {
		return minute;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getHour());
		sb.append(":");
		sb.append(getMinute());
		return sb.toString();
	}
}