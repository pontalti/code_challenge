package com.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateConverter {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.US);

	public DateConverter() {
		super();
	}
	
	public static LocalDate strDateConverter(String date) {
		try {
			LocalDate localDate = LocalDate.parse(date.trim(), formatter);
			return localDate;
		} catch (Exception e) {
			
		}
		return null;
	}

}
