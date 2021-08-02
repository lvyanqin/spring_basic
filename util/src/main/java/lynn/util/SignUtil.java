package lynn.util;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class SignUtil {
    public static final List<String> ignoreKeys = CollectionUtil.toList("sign" , "sign_type");

    public static String genSign(Map<String, Object> map, String secret) {
        StringBuffer data = buildData(map);
        data.append(secret);
        return md5(data.toString());
    }

    public static String md5(String inStr) {
        try {
            return DigestUtils.md5Hex(inStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误");
        }
    }

    /**
     * 拼接数据
     *
     * @return
     */
    public static StringBuffer buildData(Map<String, Object> map) {
        StringBuffer str = new StringBuffer();
        if (map == null || map.isEmpty())
            return str;
        Map<String, Object> resultMap = sortMapByKey(map);
        for (String key : resultMap.keySet()) {
            if(CollectionUtil.contains(ignoreKeys, key)){
                continue;
            }
            Object value = resultMap.get(key);
            str.append(key).append("=").append(value).append("&");
        }
        return str;
    }

    /**
     * 使用 Map按key进行排序(这里重写了比较器的compare方法按升序排序)
     *
     * @param map
     * @return
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<String, Object>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    public static class MapKeyComparator implements Comparator<String> {

        public int compare(String str1, String str2) {
            return str1.compareTo(str2);   //升序排序
            //return str2.compareTo(str1);   降序排序
        }
    }
}

