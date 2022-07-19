package liuyang.testspringbootenv.common.utils.cypher;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加密工具类
 *
 * @author suhj
 * @version 1.0 20190216
 */
public class AesEncryptUtils {
    private static final String ALGORI = "AES";
    private static final int ALGORI_LENGTH = 128;
    //跳过长度不加密
    private static final int SKIP_LENGTH = 2;
    //可配置到Constant中，并读取配置文件注入,16位,自己定义
    private static final String KEY = "infoveriplatform";

    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHMSTR = "AES/CFB/NoPadding";

    // 根据SonarLint修正 begin
    private static SecureRandom random = new SecureRandom();
    private static byte[] bytesIV = new byte[16];
    static {
        random.nextBytes(bytesIV);
    }
    // 根据SonarLint修正 end

    private AesEncryptUtils() {
    }

    /**
     * 加密 默认跳过SKIP_LENGTH开头字符不加密
     *
     * @param content 需要加密内容
     * @return 加密后内容
     * @throws Exception 加密失败异常
     */
    public static String encrypt(String content) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return encrypt(content, SKIP_LENGTH);
    }

    /**
     * 解密 默认跳过SKIP_LENGTH开头字符不加解密
     *
     * @param encryptStr 需要解密字符串
     * @return 返回解密后内容
     * @throws Exception 解密异常
     */
    public static String decrypt(String encryptStr) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return decrypt(encryptStr, SKIP_LENGTH);
    }

    /**
     * 加密 默认跳过SKIP_LENGTH开头字符不加解密
     *
     * @param skip    跳过开头字符数
     * @param content 需要加密内容
     * @return 加密后内容
     * @throws Exception 加密失败异常
     */
    public static String encrypt(String content, int skip) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return content.substring(0, skip) + encrypt(content.substring(skip), KEY);
    }

    /**
     * 解密 默认跳过SKIP_LENGTH开头字符不加解密
     *
     * @param skip       跳过开头字符数
     * @param encryptStr 需要解密字符串
     * @return 返回解密后内容
     * @throws Exception 解密异常
     */
    public static String decrypt(String encryptStr, int skip) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return encryptStr.substring(0, skip) + decrypt(encryptStr.substring(skip), KEY);
    }

    /**
     * 加密
     *
     * @param content    加密的字符串
     * @param encryptKey key值
     * @return 加密后内容
     * @throws Exception 加密失败异常
     */
    private static String encrypt(String content, String encryptKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator kgen = KeyGenerator.getInstance(ALGORI);
        kgen.init(ALGORI_LENGTH);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), ALGORI),
                    new IvParameterSpec(encryptKey.getBytes()));
        /*cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), ALGORI),
                new IvParameterSpec(bytesIV));*/
        byte[] b = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));// old: CHAR_SET = "utf-8"
        // 采用base64算法进行转码,避免出现中文乱码
        return Base64.encodeBase64String(b);
    }

    /**
     * 解密
     *
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     * @return 解密后内容
     * @throws Exception 加密失败异常
     */
    private static String decrypt(String encryptStr, String decryptKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator kgen = KeyGenerator.getInstance(ALGORI);
        kgen.init(ALGORI_LENGTH);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), ALGORI),
                    new IvParameterSpec(decryptKey.getBytes()));
        /*cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), ALGORI),
                new IvParameterSpec(bytesIV));*/
        // 采用base64算法进行转码,避免出现中文乱码
        byte[] encryptBytes = Base64.decodeBase64(encryptStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }
}
