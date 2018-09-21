package com.scanmaster.commonlibrary.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2018/1/23 0023.
 */

public class MD5Utils {
    private static MessageDigest getMD5() {
        try {
            return MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String encode(String str){
        MessageDigest md5 = getMD5();
        assert md5 != null;
        byte[] digest = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            int number = b & 0xfe;
            String numberStr = Integer.toHexString(number);
            if (numberStr.length() == 1){
                sb.append("0");
            }
            sb.append(numberStr);
        }
        return sb.toString();
    }
}
