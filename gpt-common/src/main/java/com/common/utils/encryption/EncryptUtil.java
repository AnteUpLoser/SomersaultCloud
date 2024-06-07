package com.common.utils.encryption;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class EncryptUtil {

    private static final String salt = "SomersaultCloud";

    /**
     * 私有化实例
     */
    private EncryptUtil(){}


    //SHAForPwd 还要加用户唯一标识盐 (这里用了邮箱)
    public static String SHAForUserPwd(String input, String email) {
        try {
            //加自定义盐
            input = input+salt+email;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            //转成base64字符串return
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 鉴定用户密码哈希值是否相同
     * @param inputPassword 输入的东西
     * @param real    正确的东西
     */
    public static boolean verifyUserPwd(String inputPassword, String email, String real) {
        inputPassword = inputPassword+salt+email;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //利用加密算法哈希成对应字节数组
            byte[] input = digest.digest(inputPassword.getBytes(StandardCharsets.UTF_8));
            //将正确的Base64密码解成字节数组
            byte[] realBytes = Base64.getDecoder().decode(real);
            //比较两个字节数组
            return MessageDigest.isEqual(input, realBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

}
