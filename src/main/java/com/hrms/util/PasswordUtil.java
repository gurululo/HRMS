package com.hrms.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 密码安全加密工具类
 */
public class PasswordUtil {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    /**
     * 生成随机盐值
     *
     * @return 16进制盐字符串
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

    /**
     * 计算带盐哈希
     *
     * @param password 明文密码
     * @param salt     盐值
     * @return 64位哈希字符串
     */
    public static String encrypt(String password, String salt) {
        if (password == null) {
            password = "";
        }
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(salt.getBytes());
            byte[] hash = digest.digest(password.getBytes());
            return Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not supported", e);
        }
    }

    /**
     * 校验密码
     *
     * @param inputPassword 输入明文
     * @param salt          存储的盐值
     * @param expectedHash  存储的哈希
     * @return 是否匹配
     */
    public static boolean verify(String inputPassword, String salt, String expectedHash) {
        if (inputPassword == null || salt == null || expectedHash == null) {
            return false;
        }
        String calculated = encrypt(inputPassword, salt);
        return calculated.equalsIgnoreCase(expectedHash);
    }
}
