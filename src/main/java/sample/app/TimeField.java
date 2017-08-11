package sample.app;

import java.time.temporal.ValueRange;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.TextField;

/**
 * 時刻フィールド
 */
class TimeField extends TextField {

	/** 時の最小値 */
	private int minHour = 0;
	/** 時の最大値 */
	private int maxHour = 23;
	/** 分の最小値 */
	private int minMinute = 0;
	/** 時の最大値 */
	private int maxMinute = 59;

	/**
	 * コンストラクター
	 */
	public TimeField() {
		setMaxLength(5);
		setWidth("70px");
		addStyleName("align-center");
		setValueChangeMode(ValueChangeMode.EAGER);

		// フォーカス時の処理
		addFocusListener(e -> {
			String value = getValue();
			setValue(value.replaceAll("[^0-9|:]","")); // 数字とコロン以外は除去
			selectAll();
		});
		// 値変更時の処理
		addValueChangeListener(e -> {
			if (e.isUserOriginated()) {
				String value = e.getValue();
				setValue(value.replaceAll("[^0-9|:]","")); // 数字とコロン以外は除去
			}
		});
		// フォーカス消失時の処理
		addBlurListener(e -> {
			String value = getValue();
			if (value.length() != 0) {
				StringTime t = parseStringTime(value);
				if (t != null) {
					setValue(t.toString());
				}
			}
		});
	}

	/**
	 * コンストラクター
	 * @param caption
	 */
	public TimeField(String caption) {
		this();
		setCaption(caption);
	}

	/**
	 * コンストラクター
	 * @param caption
	 * @param valueChangeListener
	 */
	public TimeField(String caption, ValueChangeListener<String> valueChangeListener) {
		this(caption);
		addValueChangeListener(valueChangeListener);
	}

	public int getMinHour() {
		return minHour;
	}

	public void setMinHour(int minHour) {
		this.minHour = minHour;
	}

	public int getMaxHour() {
		return maxHour;
	}

	public void setMaxHour(int maxHour) {
		this.maxHour = maxHour;
	}

	public int getMinMinute() {
		return minMinute;
	}

	public void setMinMinute(int minMinute) {
		this.minMinute = minMinute;
	}

	public int getMaxMinute() {
		return maxMinute;
	}

	public void setMaxMinute(int maxMinute) {
		this.maxMinute = maxMinute;
	}

	/**
	 * 文字列時刻に変換します。
	 * @param time
	 * @return 文字列時刻
	 */
	private StringTime parseStringTime(String time) {
		if (time == null) {
			return null;
		}
		// 「:」で時分を分ける
		String[] array = time.split(":");
		String h = (array.length > 0 ? array[0].replaceAll("[^0-9]","") : "");
		String m = (array.length > 1 ? array[1].replaceAll("[^0-9]","") : "");

		// 分の値がなく時の文字数が多ければ一部を分へセットする
		int hourLength = 2;
		int minuteLength = 2;
		if (m.length() == 0 && hourLength < h.length()) {
			for (int i = 0; i < minuteLength; i++) {
				h += "0";
			}
			m = h.substring(hourLength, h.length());
		}

		// ゼロ埋め
		while (h.length() < hourLength) {
			h = "0" + h;
		}
		h = h.substring(0, hourLength);
		while (m.length() < minuteLength) {
			m = "0" + m;
		}
		m = m.substring(0, minuteLength);

		// 時が範囲外であれば設定し直す
		if (h.length() != 0) {
			if (!ValueRange.of(getMinHour(), getMaxHour()).isValidValue(Integer.valueOf(h))) {
				h = "00";
			}
		}
		// 分が範囲外であれば設定し直す
		if (m.length() != 0) {
			if (!ValueRange.of(getMinMinute(), getMaxMinute()).isValidValue(Integer.valueOf(m))) {
				m = "00";
			}
		}
		return new StringTime(h, m);
	}
}