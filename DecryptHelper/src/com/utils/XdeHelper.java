package com.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class XdeHelper {
	public static byte[] inflate0(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];

		int error = 0;
		while (!inflater.finished()) {
			int count = 0;
			try {
				count = inflater.inflate(buf);
			} catch (DataFormatException e) {
				error = 1;
				break;
			}
			baos.write(buf, 0, count);
		}
		inflater.end();
		if (error > 0) {
			return null;
		}

		return baos.toByteArray();
	}

	public static byte[] deflate0(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buf);
			baos.write(buf, 0, count);
		}
		deflater.end();
		return baos.toByteArray();
	}

	private static boolean checkXdeInput(byte[] data) {
		return true;
	}

	public static String xde(String s) {
		byte[] data = null;
		try {
			data = s.getBytes("utf-8");
		} catch (Exception e) {
			return null;
		}
		if (!checkXdeInput(data)) {
			return null;
		}
		byte[] xdata = org.apache.commons.codec.binary.Base64.decodeBase64(data);
//		byte[] xdata = Base64.decode(data, Base64.DEFAULT);
		int len = xdata.length;
		data = new byte[len];
		for (int i = 0; i < len; i++) {
			byte b = (byte) (xdata[i] ^ 0xFF);
			data[i] = b;
		}
		data = inflate0(data);
		try {
			return new String(data, "utf-8");
		} catch (Exception e) {
			return null;
		}
	}

	public static String xen(String s) {
		byte[] data = null;
		try {
			data = s.getBytes("utf-8");
		} catch (Exception e) {
			return null;
		}
		if (!checkXdeInput(data)) {
			return null;
		}
		byte[] xdata = deflate0(data);
		int len = xdata.length;
		data = new byte[len];
		for (int i = 0; i < len; i++) {
			byte b = (byte) (xdata[i] ^ 0xFF);
			data[i] = b;
		}
		data = org.apache.commons.codec.binary.Base64.encodeBase64(data);
		try {
			return new String(data, "utf-8");
		} catch (Exception e) {
			return null;
		}
	}
}
