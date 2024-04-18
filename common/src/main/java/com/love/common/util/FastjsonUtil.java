package com.love.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FastjsonUtil {

    static {
        SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer(JSON.DEFFAULT_DATE_FORMAT));
        serializeConfig.put(LocalDate.class, (serializer, object, fieldName, fieldType, features) -> {
            if (Objects.isNull(object)) {
                serializer.writeNull();
            } else {
                LocalDate localDate = ( LocalDate ) object;
                serializer.write(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        });

        serializeConfig.put(LocalDateTime.class, (serializer, object, fieldName, fieldType, features) -> {
            if (Objects.isNull(object)) {
                serializer.writeNull();
            } else {
                LocalDateTime localDateTime = ( LocalDateTime ) object;
                serializer.write(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        });
    }

    public static Map<String, String> flatJson(String json) {
        return json2bean(json, mapType(String.class, String.class));
    }

    private static <T> TypeReference<List<T>> listType(Class<T> type) {
        return new TypeReference<List<T>>(type) {
        };
    }

    private static <K, V> TypeReference<List<Map<K, V>>> listMapType(Class<K> keyType, Class<V> valueType) {
        return new TypeReference<List<Map<K, V>>>(keyType, valueType) {
        };
    }

    private static <K, V> TypeReference<Map<K, V>> mapType(Class<K> keyType, Class<V> valueType) {
        return new TypeReference<Map<K, V>>(keyType, valueType) {
        };
    }

    private static <K, V> TypeReference<Map<K, List<V>>> mapListType(Class<K> keyType, Class<V> elemType) {
        return new TypeReference<Map<K, List<V>>>(keyType, elemType) {
        };
    }


    public static String bean2json(Object object) {
        return JSON.toJSONString(object);
    }

    public static <T> T json2bean(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> T json2bean(String json, Type type) {
        return JSON.parseObject(json, type);
    }

    public static <T> T json2bean(String json, TypeReference<T> type) {
        return JSON.parseObject(json, type);
    }

    /*
    public static <T> List<T> getList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
    */

    public static <T> List<T> getList(String json, Class<T> elemClass) {
        return json2bean(json, listType(elemClass));
    }

    public static <K, V> Map<K, V> getMap(String json, Class<K> keyType, Class<V> valueType) {
        return json2bean(json, mapType(keyType, valueType));
    }

    public static <K, V> List<Map<K, V>> getListMap(String json, Class<K> keyClass, Class<V> valClass) {
        return json2bean(json, listMapType(keyClass, valClass));
    }

    public static <K, V> Map<K, List<V>> getMapList(String json, Class<K> keyClass, Class<V> elemClass) {
        return json2bean(json, mapListType(keyClass, elemClass));
    }
}
