package com.me.baselib.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.security.*
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

/**
 * dysen.
 * dy.sen@qq.com    11/24/20  9:21 AM

 * Info：RSA非对称加密
 */
object RsaUtils {
    private const val RSA = "RSA"
    private const val RSA_ECB = "RSA/ECB/PKCS1Padding"
    private const val mPubKey =
        """MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCP/3Xrrr5vFFpgLQ+6iY/VVRvsjr++g7EkMeO/cemaYw1nJgBtnXq0zXItr4G4w+3n25LZGoup85fg+uB8xKDqktxMQpxoG3OjJyaP0/tezy92EYVbyiKcDl/93rY0gJGIoHPn0trgEvMVn0tY64hNhKKmXyXltzUM5v6ewXCo1QIDAQAB"""
    private const val mPriKey =
        """MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAI//deuuvm8UWmAtD7qJj9VVG+yOv76DsSQx479x6ZpjDWcmAG2derTNci2vgbjD7efbktkai6nzl+D64HzEoOqS3ExCnGgbc6MnJo/T+17PL3YRhVvKIpwOX/3etjSAkYigc+fS2uAS8xWfS1jriE2EoqZfJeW3NQzm/p7BcKjVAgMBAAECgYAx8B4tBgT08NIG2JnDz0EactGkUD0fDPy52LNrCU5CRGe5hexQF3UejsEyJtOOCCTRa8yZjiMxZVEerOJ9YpOLQML5dXosn1KuW1QNlKJJ5klCwJirjpsRXW3nmTS+SJE2BZT1tMsBslHX0cC2bo2pgfjTrKJ/dS82K9vBNMOyYQJBAMyPaSzB4C5q31fwWOA8HKdLTRoB3tJBJGP4166cHK8ghkuhPXYbQu7zsiXujhCIuJgABEK1gikD3bzpbxjOEZ8CQQC0NVfzqU7HpZS0bBb0plCRGmTeVap7Rnrnaj8RannjG8giaxfTO+QtPS3opCggbSdbFTA949zYjBEIilxv5rkLAkEApqiIldB25xpnUGwe3MHczmfJL1Eqyr4L+e85Oacyr3OeNmvteRYI2ElvcPwEfnAFhMEut3LN0sC1nT3KJ7lsHQJBAIR07RfJK4GtoFbZWCotEO0G1HtjxjJkRLRSMNcxRzP6WKmLw/BchkQGQvdLqb7j3Fijg1kKYmq80UxQvs71bK8CQQCXYBxzLTZMPqExuOCgRsHqvurCOm9QLUJATSsAZKqOHmiotXTovhNwB7dv6T3MuKNdVOkJOUbznzMHz0hAX/vY"""

    /**
     * 加密
     * @param data
     */
    @Throws(java.lang.Exception::class)
    fun encrypt(data: String): String {
        val str: String
        val publicKey: PublicKey? = loadPublicKey(mPubKey)
        val encryptByte: ByteArray? = encryptData(data.toByteArray(), publicKey)
        str = Base64Utils.encode(encryptByte ?: ByteArray(0))
        return str
    }


    /**
     * 解密
     * @param data
     */
    @Throws(java.lang.Exception::class)
    fun decrypt(data: String): String {
        val str: String
        val privateKey: PrivateKey? = loadPrivateKey(mPriKey)
        val decodeByte: ByteArray = Base64Utils.decode(data)
        val decryptByte: ByteArray? = decryptData(decodeByte, privateKey)
        str = String(decryptByte ?: ByteArray(0), Charsets.UTF_8)
        return str
    }

    /**
     * 随机生成RSA密钥对(默认密钥长度为1024)
     *
     * @return
     */
    fun generateRSAKeyPair(): KeyPair? {
        return generateRSAKeyPair(1024)
    }

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength
     * 密钥长度，范围：512～2048<br></br>
     * 一般1024
     * @return
     */
    fun generateRSAKeyPair(keyLength: Int): KeyPair? {
        return try {
            val kpg = KeyPairGenerator.getInstance(RSA)
            kpg.initialize(keyLength)
            kpg.genKeyPair()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 用公钥加密 <br></br>
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data
     * 需加密数据的byte数据
     * @param publicKey
     * 公钥
     * @return 加密后的byte型数据
     */
    fun encryptData(data: ByteArray?, publicKey: PublicKey?): ByteArray? {
        return try {
            val cipher = Cipher.getInstance(RSA_ECB)
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            // 传入编码数据并返回编码结果
            cipher.doFinal(data)
        } catch (e: Exception) {
            e.printStackTrace()

            println("加密失败")
            null
        }
    }

    /**
     * 用私钥解密
     *
     * @param encryptedData
     * 经过encryptedData()加密返回的byte数据
     * @param privateKey
     * 私钥
     * @return
     */
    fun decryptData(encryptedData: ByteArray?, privateKey: PrivateKey?): ByteArray? {
        return try {
            val cipher = Cipher.getInstance(RSA_ECB)
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            cipher.doFinal(encryptedData)
        } catch (e: Exception) {
            println(e.message + "解密失败")
            null
        }
    }


    /**
     * 从字符串中加载私钥<br></br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    @Throws(java.lang.Exception::class)
    fun loadPrivateKey(privateKeyStr: String): PrivateKey? {
        return try {
            val buffer: ByteArray = Base64Utils.decode(privateKeyStr)
            val keySpec = PKCS8EncodedKeySpec(buffer)
            val keyFactory = KeyFactory.getInstance(RSA)
            keyFactory.generatePrivate(keySpec) as RSAPrivateKey
        } catch (e: NoSuchAlgorithmException) {
            throw java.lang.Exception("无此算法")
        } catch (e: InvalidKeySpecException) {
            throw java.lang.Exception("私钥非法")
        } catch (e: NullPointerException) {
            throw java.lang.Exception("私钥数据为空")
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr
     * 公钥数据字符串
     * @throws Exception
     * 加载公钥时产生的异常
     */
    @Throws(java.lang.Exception::class)
    fun loadPublicKey(publicKeyStr: String): PublicKey? {
        return try {
            val buffer = Base64Utils.decode(publicKeyStr)
            val keyFactory = KeyFactory.getInstance(RSA)
            val keySpec = X509EncodedKeySpec(buffer)
            keyFactory.generatePublic(keySpec) as RSAPublicKey
        } catch (e: NoSuchAlgorithmException) {
            throw java.lang.Exception("无此算法")
        } catch (e: InvalidKeySpecException) {
            throw java.lang.Exception("公钥非法")
        } catch (e: java.lang.NullPointerException) {
            throw java.lang.Exception("公钥数据为空")
        }
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in
     * 公钥输入流
     * @throws Exception
     * 加载公钥时产生的异常
     */
    @Throws(java.lang.Exception::class)
    fun loadPublicKey(inputstream: InputStream): PublicKey? {
        return try {
            loadPublicKey(readKey(inputstream))
        } catch (e: IOException) {
            throw java.lang.Exception("公钥数据流读取错误")
        } catch (e: java.lang.NullPointerException) {
            throw java.lang.Exception("公钥输入流为空")
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param in
     * 私钥文件名
     * @return 是否成功
     * @throws Exception
     */
    @Throws(java.lang.Exception::class)
    fun loadPrivateKey(inputstream: InputStream): PrivateKey? {
        return try {
            loadPrivateKey(readKey(inputstream))
        } catch (e: IOException) {
            throw java.lang.Exception("私钥数据读取错误")
        } catch (e: java.lang.NullPointerException) {
            throw java.lang.Exception("私钥输入流为空")
        }
    }

    /**
     * 读取密钥信息
     *
     * @param in
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun readKey(inputstream: InputStream): String {
        val br = BufferedReader(InputStreamReader(inputstream))
        var readLine: String? = null
        val sb = StringBuilder()
        while (br.readLine().also { readLine = it } != null) {
            if (readLine!![0] == '-') {
                continue
            } else {
                sb.append(readLine)
                sb.append('\r')
            }
        }
        return sb.toString()
    }

    /**
     * 打印公钥信息
     *
     * @param publicKey
     */
    fun printPublicKeyInfo(publicKey: PublicKey) {
        val rsaPublicKey = publicKey as RSAPublicKey
        println("----------RSAPublicKey----------")
        println("Modulus.length=" + rsaPublicKey.modulus.bitLength())
        println("Modulus=" + rsaPublicKey.modulus.toString())
        println("PublicExponent.length=" + rsaPublicKey.publicExponent.bitLength())
        println("PublicExponent=" + rsaPublicKey.publicExponent.toString())
    }

    fun printPrivateKeyInfo(privateKey: PrivateKey) {
        val rsaPrivateKey = privateKey as RSAPrivateKey
        println("----------RSAPrivateKey ----------")
        println("Modulus.length=" + rsaPrivateKey.modulus.bitLength())
        println("Modulus=" + rsaPrivateKey.modulus.toString())
        println("PrivateExponent.length=" + rsaPrivateKey.privateExponent.bitLength())
        println("PrivatecExponent=" + rsaPrivateKey.privateExponent.toString())
    }
}