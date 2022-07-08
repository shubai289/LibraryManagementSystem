//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.librarySys.utils;
import java.security.MessageDigest;

public class Util {
    public Util() {
    }

    public static String encrypt(String pwd) {
        char[] md5String = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = pwd.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for (byte byte0 : md) {
                str[k++] = md5String[byte0 >>> 4 & 15];
                str[k++] = md5String[byte0 & 15];
            }

            return new String(str);
        } catch (Exception var10) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(encrypt("zzz").toString().length());
        System.out.println(encrypt("admin"));
    }

}
