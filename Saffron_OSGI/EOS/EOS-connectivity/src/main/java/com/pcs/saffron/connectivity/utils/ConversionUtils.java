/**
 * 
 */
package com.pcs.saffron.connectivity.utils;


import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;


/**
 * @author aneeshp
 *
 */
public class ConversionUtils {


	private static final String HEXES = "0123456789ABCDEF";
	private static final String DECIMAL_PATTERN_2_PLACES = "#0.00";
	public static final int FORMAT_FOUR_DIGIT = 4;
	public static final int FORMAT_EIGHT_DIGIT = 8;
	public static final int FORMAT_SIXTEEN_DIGIT = 168;

	/**
	 * @param hex
	 * the hexa decimal string to be converted into binary.
	 * 
	 * @return 
	 * the binary string that corresponds to the hexa decimal string.
	 */
	public static Object convertToBinary(String hex) {
		int i = Integer.parseInt(hex,16);
		String binary = Integer.toBinaryString(i);
		int shortBy = 0;
		if(i >= 4){
			shortBy = (binary.length()%4==0) ? 0 : (4-binary.length()%4);	
		}else{
			shortBy = (binary.length()%8==0) ? 0 : (8-binary.length()%8);
		}

		if(shortBy > 0 && shortBy < 16){
			binary = formatBinary(binary, shortBy).toString();
		}
		return binary;
	}

	/**
	 * @param hex
	 * @param formatTo
	 * @return
	 */
	public static Object convertToBinaryExt(String hex,int formatTo) {
		int i = Integer.parseInt(hex,16);
		String binary = Integer.toBinaryString(i);
		int shortBy = 0;
		if(formatTo <= 4){
			shortBy = (binary.length()%4==0) ? 0 : (4-binary.length()%4);	
		}else if(formatTo <= 8){
			shortBy = (binary.length()%8==0) ? 0 : (8-binary.length()%8);
		}else{
			shortBy = (binary.length()%8==0) ? 0 : (16-binary.length()%16);
		}

		if(shortBy > 0 && shortBy < 16){
			binary = formatBinary(binary, shortBy).toString();
		}
		return binary;
	}

	/**
	 * @param binary
	 * @return
	 */
	public static Object convertBinaryToInt(String binary){
		if(binary != null)
			return Integer.parseInt(binary, 2);
		else
			return null;
	}
	/**
	 * @param bin 
	 * the incoming binary string. 
	 * 
	 * @param shortBy 
	 * an integer value that indicates the number of zeros appended to the binary string- 
	 * can have values from 0-3. 
	 * 
	 * @return 
	 * returns the formatted binary string.
	 */
	private static Object formatBinary(String bin,int shortBy){
		String formattedBin = null;
		switch (shortBy) {
		case 1:
			formattedBin = "0"+bin;
			break;
		case 2:
			formattedBin = "00"+bin;
			break;
		case 3:
			formattedBin = "000"+bin;
			break;
		case 4:
			formattedBin = "0000"+bin;
			break;
		case 5:
			formattedBin = "00000"+bin;
			break;
		case 6:
			formattedBin = "000000"+bin;
			break;
		case 7:
			formattedBin = "0000000"+bin;
			break;
		case 8:
			formattedBin = "00000000"+bin;
			break;
		case 9:
			formattedBin = "000000000"+bin;
			break;
		case 10:
			formattedBin = "0000000000"+bin;
			break;
		case 11:
			formattedBin = "00000000000"+bin;
			break;
		case 12:
			formattedBin = "000000000000"+bin;
			break;
		case 13:
			formattedBin = "0000000000000"+bin;
			break;
		case 14:
			formattedBin = "00000000000000"+bin;
			break;
		case 15:
			formattedBin = "000000000000000"+bin;
			break;

		default:
			formattedBin = bin;
			break;
		}
		return formattedBin;
	}

	/**
	 * @param hex
	 * @return
	 */
	public static Object convertToLong(String hex){
		long longVal = Long.parseLong(hex, 16);
		return Long.toString(longVal);
	}

	/**
	 * @param hex
	 * @return
	 */
	public static Object convertToDecimal(String hex){
		long longVal = Long.parseLong(hex, 16);
		String decimalValue = new DecimalFormat(DECIMAL_PATTERN_2_PLACES).format(longVal);
		return decimalValue;
	}
	
	/**
	 * @param hex
	 * @return
	 */
	public static Object convertToDouble(String hex){
		Double longVal = Double.parseDouble(hex);
		String decimalValue = new DecimalFormat(DECIMAL_PATTERN_2_PLACES).format(longVal);
		return decimalValue;
	}

	/**
	 * @param hex
	 * @return
	 */
	public static Object convertToLongEx(String hex){
		Long longVal = Long.parseLong(hex, 16);
		return Long.toString(Math.round(longVal.doubleValue()));
	}

	/**
	 * @param shortIMEI
	 * @return
	 */
	public static Object toLongIMEI(String shortIMEI){
		long highBits = 21197319;
		long converterConst = 16777216;

		long imei = (highBits*converterConst)+(Long.parseLong(shortIMEI));
		return Long.toString(imei);
	}

	/**
	 * This function converts any UTF strings into normal string values (used to convert barcode to string)
	 * @param UTF
	 * @return
	 */
	public static Object UTFtoString(String UTF){
		byte[] bytes = new byte[UTF.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(UTF.substring(2 * i, 2 * i + 2), 16);
		}
		String multi = new String(bytes);
		bytes = null;
		return multi;
	}

	public static String getHex(String text){
		
		StringBuffer buffer = new StringBuffer();
	     int intValue;
	     for(int x = 0; x < text.length(); x++)
	         {
	         int cursor = 0;
	         intValue = text.charAt(x);
	         String binaryChar = new String(Integer.toBinaryString(text.charAt(x)));
	         for(int i = 0; i < binaryChar.length(); i++)
	             {
	             if(binaryChar.charAt(i) == '1')
	                 {
	                 cursor += 1;
	             }
	         }
	         if((cursor % 2) > 0)
	             {
	             intValue += 128;
	         }
	         buffer.append(Integer.toHexString(intValue) + " ");
	     }
	     return buffer.toString();
	}
	
	/**
	 * @param raw
	 * @return
	 */
	public static Object getHex( byte [] raw ) {
		if ( raw == null ) {
			return null;
		}
		final StringBuilder hex = new StringBuilder( 2 * raw.length );
		for ( final byte b : raw ) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4))
			.append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}

	/**
	 * @param value hexa decimal string
	 * @return a signed representation of the hexadecimal string
	 */
	public static Object convertToSignedValue(String value) {
		String sign = value.substring(0,2);
		Integer parseInt = Integer.parseInt(value.substring(2), 16);
		return sign.equalsIgnoreCase("01")?"-"+parseInt.toString():parseInt.toString();
	}

	/**
	 * @param value hexa decimal string
	 * @return ASCII string
	 */
	public static Object convertToASCII(String value){
		StringBuffer ascii = new StringBuffer();
		if((value.length()%2) ==0){
			int stringLength = value.length();
			for (int i = 0; i < stringLength; i=i+2) {
				String curCode = value.substring(0,2);
				value = value.substring(2);
				ascii.append((char)Integer.parseInt(curCode, 16));
			}
		}
		return ascii.toString();
	}

	/**
	 * @param value string
	 * @return ASCII string array
	 */
	public static Object convertToASCIIArray(String value){
		int[] asciiArray = new int[value.length()];
		if(value != null){
			for ( int i = 0; i < value.length(); ++i ) {
				char c = value.charAt( i );
				int j = (int) c;
				asciiArray[i] = j;
			}
		}		
		return asciiArray;
	}
	
	/**
	 * @param s
	 * @return
	 */
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	/**
	 * @param hex
	 * @return
	 */
	public static String convertToString(String hex){
		String data = null;
		try {
			data = new String(Hex.decodeHex(hex.toCharArray()));
		} catch (DecoderException e) {
		}
		return data;
	}

	/**
	 * Splits the data as byte information(two characters=1 byte).
	 * @param data input hexa decimal string
	 * @return
	 */
	public static Object splitAsBytes(String data) {
		int bytesAvailable = data.length()/2;
		String tempData = data;
		String[] byteArray = new String[bytesAvailable];
		for (int i = 0; i < bytesAvailable; i++) {
			String byteInfo = tempData.substring(0, 2);
			tempData = tempData.substring(2);
			byteArray[i]=byteInfo;
		}
		return byteArray;
	}
		
	/**
	 * @param fourHexaData
	 * @return
	 */
	public static Object getTwosComplement(String fourHexaData){
		if(fourHexaData == null){
			return null;
		}else{
			return new BigInteger(fourHexaData,16).intValue(); 
		}
	}
	
	/**
	 * Converts a hexa decimal float representation into a real float value
	 * @param hexaValue
	 * @param roundOfset
	 * @return
	 */
	public static Object convertHexaToFloat(String hexaValue,int roundToPlaces){
		Long intValue = Long.parseLong(hexaValue, 16);
        Float floatValue = Float.intBitsToFloat(intValue.intValue());
        
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMinimumFractionDigits(roundToPlaces);
        
        String floatData = nf.format(floatValue);
        nf = null;
        return floatData;
	}
	
	
	public static String hex_to_binary(String hex) {
	    String hex_char,bin_char,binary;
	    binary = "";
	    int len = hex.length()/2;
	    for(int i=0;i<len;i++){
	        hex_char = hex.substring(2*i,2*i+2);
	        int conv_int = Integer.parseInt(hex_char,16);
	        bin_char = Integer.toBinaryString(conv_int);
	        bin_char = zero_pad_bin_char(bin_char);
	        if(i==0) binary = bin_char; 
	        else binary = binary+bin_char;
	        //out.printf("%s %s\n", hex_char,bin_char);
	    }
	    return binary;
	}
	
	public static String zero_pad_bin_char(String bin_char){
	    int len = bin_char.length();
	    if(len == 8) return bin_char;
	    String zero_pad = "0";
	    for(int i=1;i<8-len;i++) zero_pad = zero_pad + "0"; 
	    return zero_pad + bin_char;
	}
	
	
	public static void main(String[] args) {
System.out.println(convertToString("686920616E656573680A")); 
	}
}
