package com.rose.mp.gateway.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * <p>
 * 工具类方法 - MD5加密工具类
 * </p>
 * @author lihainuo
 */
@Component
public class MD5Util {
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不可用", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b); // 确保无符号转换
            if (hex.length() == 1) {
                hexString.append('0'); // 补零
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}