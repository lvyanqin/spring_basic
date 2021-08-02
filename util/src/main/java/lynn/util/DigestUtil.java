package lynn.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;

/**
 * 不可逆加密工具类<br>
 * org.springframework.util.DigestUtils 工具类功能太单一，
 * 使用的是commons-codec.jar来统一实现加密操作。
 */
public class DigestUtil {

    /**
     * md5 加密。返回16进制的字符串,长度32位（不可逆）
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * sha1 加密。返回16进制的字符串,长度40位（不可逆）
     *
     * @param str
     * @return
     */
    public static String sha1(String str) {
        return DigestUtils.sha1Hex(str);
    }

    /**
     * sha256 加密。返回16进制的字符串,长度64位（不可逆）
     *
     * @param str
     * @return
     */
    public static String sha256(String str) {
        return DigestUtils.sha256Hex(str);
    }


    /**
     * hmacMd5 加密。返回16进制的字符串,长度32位
     *
     * @param str
     * @param key 这个key可以先加密，然后调用的时候解密
     * @return
     */
    public static String hmacMd5(String str, String key) {
        return HmacUtils.hmacMd5Hex(key, str);
    }

    /**
     * hmacSha1 加密。返回16进制的字符串,长度40位
     *
     * @param str
     * @param key 这个key可以先加密，然后调用的时候解密
     * @return
     */
    public static String hmacSha1(String str, String key) {
        return HmacUtils.hmacSha1Hex(key, str);
    }

    /**
     * hmacSha256 加密。返回16进制的字符串,长度64位
     *
     * @param str
     * @param key
     * @return
     */
    public static String hmacSha256(String str, String key) {
        return HmacUtils.hmacSha256Hex(key, str);
    }

}
