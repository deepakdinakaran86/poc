package com.pcs.saffron.expressions.util;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

public class Utilities {


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
	
	public static String arrayToString(Object arrayObject){
		int length = Array.getLength(arrayObject);
		Object[] objs = new Object[length];
	    for (int i = 0; i < length; i++) {
	        objs[i]=Array.get(arrayObject, i);
	    }
		return Arrays.toString(objs);
	}

	public static void main(String[] args) {
		formatDouble(71422.6629211, 3);
	}

}
