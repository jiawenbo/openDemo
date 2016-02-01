package com.youku.uploader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by haize.lhz on 2014/8/18.
 */
public class FileUtil {
    public static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String md5(File file) throws IOException {
        FileInputStream in = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            byte[] buffer = new byte[1024 * 1024 * 5];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                md.update(buffer, 0, len);
            }
            in.close();
            byte[] hash  = md.digest();
            String md5 = toHexString(hash);
            return md5;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String md5(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        byte[] hash  = md.digest();
        String md5 = toHexString(hash);
        return md5;
    }

    /**
     * 将byte数组转化成十六进制字符串
     * @param bytes
     * @return
     */
    public static String bytes2HexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer(16);
        if (bytes == null || bytes.length <= 0)
            return null;
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xff;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                buf.append(0);
            }
            buf.append(hv);
        }
        return buf.toString();
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }
    
    public static byte[] getBytesFromFile(File file) {
		byte[] ret = null;
		try {
			if (file == null) {
				return null;
			}
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
			byte[] b = new byte[4096];
			int n;
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
			in.close();
			out.close();
			ret = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
    
    public static byte[] getSliceBytes(File file, Integer offset, Integer length) {
        byte[] buffer = new byte[length];
        try{
            if (file == null) return null;
            FileInputStream in = new FileInputStream(file);
            in.skip(offset);
            in.read(buffer); 
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
