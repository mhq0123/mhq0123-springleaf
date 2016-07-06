package com.mhq0123.springleaf.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName SignatureUtils
 * @date 2016-07-06
 * @memo 数字签名工具类
 */
public class SignatureUtils {
    private static Logger logger = LoggerFactory.getLogger(SignatureUtils.class);

    /**
     * 签名主要算法
     */
    public enum SingnatureAlgorithm {
        RawDSA, SHA1withDSA, MD2withRSA, MD5withRSA, SHA1withRSA, SHA256withRSA, SHA384withRSA, SHA512withRSA
    }

    /**
     * 对数据进行签名
     * @param data
     * @param algorithm
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] data, SingnatureAlgorithm algorithm, PrivateKey privateKey) throws Exception {

        Signature signature;

        // 初始化签名算法
        try {
            signature = Signature.getInstance(algorithm.name());
        } catch (NoSuchAlgorithmException e) {
            logger.error("不支持的签名算法:{}", algorithm);
            throw new Exception("签名算法不被支持", e);
        }

        // 初始化私钥
        try {
            signature.initSign(privateKey);
        } catch (InvalidKeyException e) {
            logger.error("非法的私钥");
            throw new Exception("非法的私钥", e);
        }

        // 进行签名
        try {

            signature.update(data);
            return signature.sign();

        } catch (SignatureException e) {
            logger.error("签名非法");
            throw new Exception("签名非法", e);
        }

    }

    /**
     * 对数据进行签名
     */
    public static boolean verify(byte[] data, SingnatureAlgorithm algorithm, PublicKey publicKey, byte[] sign) throws Exception {

        Signature signature = Signature.getInstance(algorithm.name());
        signature.initVerify(publicKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(sign);

    }
}
