package com.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Created by ${Eric} on 2016/12/6.
 */
public class ZipUtil {
    /***
     * ½âÑ¹Ëõ
     * @param data
     * @return
     */
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
//                Log.e(MainActivity.TAG,"",e);
            	System.err.println(e.getMessage());
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

    /***
     * Ñ¹Ëõ
     * @param data
     * @return
     */
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
}