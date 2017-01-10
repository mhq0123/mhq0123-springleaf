package org.mhq0123.springleaf.common.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName CipherUtils
 * @date 2016-07-06
 * @memo
 *
 *  加密解密工具:
 * 	    AES加解密
 * 	    RSA加解密
 * 	M   D5加密
 */
public class CipherUtils {
    /**
     * aes加密
     *
     * @param content 需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encryptAES(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * aes解密
     *
     * @param content 待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decryptAES(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA用私钥加密
     * @param data  加密数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptRSAPrivateKey(byte[] data, String key)throws Exception{
        //解密密钥
        byte[] keyBytes = parseHexStr2Byte(key);
        //取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        //对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用私钥解密
     * @param data  加密数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptRSAPrivateKey(byte[] data, String key)throws Exception{
        //对私钥解密
        byte[] keyBytes = parseHexStr2Byte(key);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密
     * @param data  加密数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptRSAPublicKey(byte[] data, String key)throws Exception{
        //对公钥解密
        byte[] keyBytes = parseHexStr2Byte(key);
        //取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥解密
     * @param data  加密数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptRSAPublicKey(byte[] data, String key)throws Exception{
        //对私钥解密
        byte[] keyBytes = parseHexStr2Byte(key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException
     */
    public static String encryptMD5(String str) {
        try {
            //确定计算方法
            MessageDigest md5=MessageDigest.getInstance("MD5");
            //加密后的字符串
            return parseByte2HexStr(md5.digest(str.getBytes("utf-8")));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 初始化RSA密钥
     * @return
     * @throws Exception
     */
    public static Map<String,Object> initRSAKey() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        //公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        RSAPrivateKey privateKey =  (RSAPrivateKey) keyPair.getPrivate();

        Map<String,Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put("RSAPublicKey", publicKey);
        keyMap.put("RSAPrivateKey", privateKey);

        return keyMap;
    }
    public static void main(String[] args){
        System.err.println(encryptPassword("82","123456"));
    }
    /**
     * MD5加密算法 加密密码
     *
     * @author Ronald
     * @param userName
     * @param password
     * @return
     */
    public static String encryptPassword(String userName, String password) {

        String publicKey1 = "mhq0123hjdjdHDUEHWHd3ehgfidhwh23hHJCRIOI4HDHDHFKHFD3dhdhrg2djsHSDFHFEDJGW";
        String publicKey2 = "347djejDETahe3j%3jd*%%2DDE223fdhahfh%^@21hdhfhzhdDFQ3hH7eh32hdE#@s3210qhm";
        String publicKey3 = "mhqd@wjsdj2324shjsQWeh2dsGed/#21ssdEdhawehrcyzhzeje2#@SDajw2D2sjzahd30123";
        String str = publicKey1 + userName + publicKey2 + password + publicKey3;

        return encryptMD5(str);
    }
}
