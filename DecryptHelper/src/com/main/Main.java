package com.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.utils.XdeHelper;
import com.utils.ZipUtil;

public class Main {
	public static void main(String[] args) {
		formatXdeUtil("/libvpn4.so");
		formatXdeUtil("/libvpn5.so");
		formatXdeUtil("OpenvpnThread");
		formatXdeUtil("LD_LIBRARY_PATH");
		formatXdeUtil("mgmtsocket");
		formatXdeUtil("OpenvpnSvc");
		formatXdeUtil("management /data/data/%s/cache/mgmtsocket unix\n");
		formatXdeUtil("https://api.snapuse.com/v170105/");
		formatXdeUtil(".ser");
		formatXdeUtil("android.conf");
		formatXdeUtil("RecommandRegion");
		formatXdeUtil("curRegion");
		formatXdeUtil("LAST_UPDATE_PROFILE_TIME");
	}
	
	
	public static void formatXdeUtil(String s){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("XEn: ");
		String ss = XdeHelper.xen(s);
		sBuilder.append(ss);
		sBuilder.append("\n");
		sBuilder.append("XDe: ");
		sBuilder.append(XdeHelper.xde(ss));
		sBuilder.append("\n");
		sBuilder.append("===========================");
		System.err.println(sBuilder.toString());
	}
	
	public static void testXdeUtil(){
		String en = XdeHelper.xen("helloworld");
		System.err.println("XEn:"+en);
		System.err.println("XDe:"+XdeHelper.xde(en));
	}

	public static void testZipUtil() {
		// Source apk file
		File sourceFile = new File("source.apk");
		if (!sourceFile.exists()) {
			System.err.println("Source is null exit");
			return;
		}
		byte[] buf = new byte[1024];
		int counts = 0;
		InputStream in = null;
		FileOutputStream fOut = null;
		try {
			in = new FileInputStream(sourceFile);
			fOut = new FileOutputStream(new File("zipped.apk"));

			while ((counts = in.read(buf)) != -1) {
				byte[] bytes = ZipUtil.deflate0(buf);
				fOut.write(bytes, 0, bytes.length);
			}
			fOut.flush();
			in.close();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Write exception");
			return;
		}
		System.err.println("Write success");
	}
}
