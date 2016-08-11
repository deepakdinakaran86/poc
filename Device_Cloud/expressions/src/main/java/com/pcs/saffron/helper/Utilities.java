package com.pcs.saffron.helper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class Utilities {

	private static DecimalFormat deciFormat = new DecimalFormat("#0.00");

	public static boolean isNumber(Object obj) {
		if (obj instanceof String)
			return false;
		if (obj instanceof Number)
			return true;
		return false;
	}

	public static Double formatDouble(Object value, int minimumFractionDigits) {
		NumberFormat f = new DecimalFormat("#0.00");
		f.setMinimumFractionDigits(minimumFractionDigits);
		return Double.parseDouble(f.format(value));
	}

	public static void main(String[] args) {
		formatDouble(71422.6629211, 3);
	}

}
