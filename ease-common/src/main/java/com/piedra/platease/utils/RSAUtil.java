package com.piedra.platease.utils;

import com.piedra.platease.constants.Constants;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA密钥对生成工具类
 *
 * @author linwb
 * @since 2017-06-02
 */
public class RSAUtil {
    /* 密钥对生成算法  RSA对应MD5，  DSA对应SHA */
    private static final String KEY_ALGORITHM = "RSA";
    /* 签名算法 */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublic.key";
    private static final String PRIVATE_KEY = "RSAPrivate.key";

    /**
     * 用私钥加密
     * @param data  待加密数据
     * @param key   私钥
     * @return      返回加密后的字节数组
     */
    private static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 利用私钥解密
     * @param data  待解密数据
     * @param key   私钥
     * @return      返回解密后的字节数组
     */
    private static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 用公钥解密
     * @param data  待解密数据
     * @param key   公钥
     * @return      返回解密后的字节数组
     */
    private static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密
     * @param data  待加密数据
     * @param key   公钥
     * @return      返回加密后的字节数组
     */
    private static byte[] encryptByPublicKey(String data, String key) throws Exception {
        // 对公钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * 用私钥解密
     * @param data  待解密数据
     * @param key   私钥
     * @return      返回解密后的字节数组
     */
    public static String decryptByPrivate(String data, String key) throws Exception {
        return new String(decryptByPrivateKey(Base64.decodeBase64(data), key));
    }

    /**
     * 用公钥加密
     * @param data  要加密的数据
     * @param key   公钥
     * @return      返回加密后的字符串
     */
    public static String encryptByPublic(String data, String key) throws Exception {
        return Base64.encodeBase64String(encryptByPublicKey(data, key));
    }

    /**
     * 生成密钥对
     * @return  返回密钥对
     */
    public static Map<String,String> generateKeyPair() throws Exception{
        Map<String,String> keyPairMap = new HashMap<>();

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        keyPairMap.put(Constants.PUBLIC_KEY, Base64.encodeBase64String(keyPair.getPublic().getEncoded()));
        keyPairMap.put(Constants.PRIVATE_KEY, Base64.encodeBase64String(keyPair.getPrivate().getEncoded()));

        return keyPairMap;
    }


    public static void main(String[] args) throws Exception {

        /* ************************ 生成密钥对文件 BEGIN ******************************* */

//        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSAConstants.KEY_ALGORITHM);
//        keyPairGen.initialize(1024);
//        KeyPair keyPair = keyPairGen.generateKeyPair();
//
//        String publicKey = Base64.encodeBase64String(keyPair.getPublic().getEncoded());
//        String privateKey = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
//
//        // TODO 对于不同的系统生成密钥对，一定要使用不同的系统标识。这样文件名才不会重复
        /* syscode指定系统的编码，默认为RSA，还可以为 CK,KAKOU 等 */
//        String sysCode = "RSA";
        /* 这个文件是我们提供给接入系统的文件 */
//        FileUtils.writeLines(new File("o:/RSA" + RSAConstants.PUBLIC_KEY), "UTF-8", Collections.singleton(publicKey), false);
        /* 这两个公钥、私钥文件是我们系统保留的文件。位于resrouces/keypair/目录底下 */
//        FileUtils.writeLines(new File("o:/" + sysCode + RSAConstants.PUBLIC_KEY), "UTF-8", Collections.singleton(publicKey), false);
//        FileUtils.writeLines(new File("o:/" + sysCode + RSAConstants.PRIVATE_KEY), "UTF-8", Collections.singleton(privateKey), false);

        /* ************************ 生成密钥对文件 END ******************************* */

//        String cont = "你好，中国";
//        String enc = PublicKeyUtil.getInstance().encrypt(cont);
//        System.out.println(enc);
//        System.out.println(new String(PrivateKeyUtil.getInstance().decrypt(enc)));
    }

}
