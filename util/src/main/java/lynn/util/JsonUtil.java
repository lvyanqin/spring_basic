package lynn.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 阿里巴巴的FastJson
 * com.alibaba.fastjson.annotation.JSONField
 *
 * @JSONField(serialize=false) 在对象响应字段前加注解，这样生成的json也不包含该字段。
 * @ClassName: JsonUtil
 */
public class JsonUtil {

    private static SerializeConfig mapping = new SerializeConfig();

    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        mapping.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        mapping.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        mapping.put(java.sql.Time.class, new SimpleDateFormatSerializer("HH:mm:ss"));
    }

    public static SerializeConfig put(Class<?> clazz, SerializeFilter filter) {
        mapping.addFilter(clazz, filter);
        return mapping;
    }

    public static SerializeConfig put(Class<?> clazz, ObjectSerializer serializer) {
        mapping.put(clazz, serializer);
        return mapping;
    }


    public static <T> T toBean(String jsonString, Class<T> tt) {
        try {
            if (StringUtils.isBlank(jsonString)) {
                return null;
            }

            T t = JSON.parseObject(jsonString, tt);
            return t;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toBean(byte[] bytes, Class<T> tt) {
        try {
            if (bytes == null) {
                return null;
            }

            T t = JSON.parseObject(bytes, tt);
            return t;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String toFormatedJson(Object bean) {
        try {
            return JSON.toJSONString(bean, mapping, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.PrettyFormat);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJSONEnumNoUsingName(Object bean) {
        int features = SerializerFeature.config(JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.WriteEnumUsingName, false);
        return JSONObject.toJSONString(bean, features);
    }

    public static String toJSONString(Object bean, SerializerFeature serializerFeature) {
        return JSONObject.toJSONString(bean, mapping, serializerFeature);
    }


    public static String toJSONString(Object bean) {
        try {
            return JSON.toJSONString(bean, mapping, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJSONString(Object bean, SerializeFilter serializeFilter) {
        try {
            if (serializeFilter != null) {
                return JSON.toJSONString(bean, mapping, serializeFilter, SerializerFeature.DisableCircularReferenceDetect);
            } else {
                return JSON.toJSONString(bean, mapping, SerializerFeature.DisableCircularReferenceDetect);
            }

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    public static byte[] toJsonBytes(Object bean) {
        return JSON.toJSONBytes(bean, mapping, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static byte[] toJSONBytesEnumNoUsingName(Object bean) {
        int features = SerializerFeature.config(JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.WriteEnumUsingName, false);
        return JSONObject.toJSONBytes(bean, features);
    }

    /**
     * 成指定类型集合;
     *
     * @param datas
     * @param clazz
     * @return
     */
    public static <T> List<T> toArray(List<String> datas, Class<T> clazz) {
        if (datas == null) {
            return null;
        }
        List<T> result = new ArrayList<T>();
        for (String obj : datas) {
            result.add(toBean(obj, clazz));
        }
        return result;
    }
    
    /**
     * 将json转为map
     *
     * @param jsonStr
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map jsonStr2Map(String jsonStr) {
        Map map = JSON.parseObject(jsonStr, Map.class);
        return map;
    }

    /**
     * 将json转为T对象
     *
     * @param jsonStr
     * @return
     */
    public static <T> T jsonStr2Obj(String jsonStr, Class<T> clazz) {
        T t = JSON.parseObject(jsonStr, clazz);
        return t;
    }

    /**
     * 将json转为list<Map>
     *
     * @param jsonStr
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<Map> jsonStr2List(String jsonStr) {
        List<Map> list = JSON.parseObject(jsonStr, new TypeReference<List>() {
        });
        return list;
    }

    /**
     * 将json转为list<T> 对象
     *
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> List<T> jsonStr2List(String jsonStr, Class<T> clazz) {
        List<T> list = JSON.parseArray(jsonStr, clazz);
        return list;
    }
}
