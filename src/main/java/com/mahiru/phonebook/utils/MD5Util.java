package com.mahiru.phonebook.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author mahiru
 * @version v1.0.0
 * @className MD5Util
 * @description MD5加密算法
 * @date 2024/12/12 14:52
 **/
public class MD5Util {

    /**
     * @param plainText
     * @return java.lang.String
     * @author mahiru
     * @date 2024/12/12 14:52
     * @methodName encrypt
     * @description MD5加密
     */
    public static String encrypt(String plainText) {
        try {
            // 获取 MD5 算法的 MessageDigest 实例
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            // 将字符串转换为字节数组并进行 MD5 加密
            byte[] bytes = md5.digest(plainText.getBytes());

            // 将加密后的字节数组转换为 16 进制格式的字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : bytes) {
                // 将每个字节转换为两位的 16 进制形式，并添加到字符串中
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // 如果发生异常，打印堆栈信息
            e.printStackTrace();
            return null;
        }
    }
}
