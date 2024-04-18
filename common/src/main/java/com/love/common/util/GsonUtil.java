package com.love.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GsonUtil {

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .disableHtmlEscaping()
            .registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
                @Override
                public void write(JsonWriter out, LocalDate value) throws IOException {
                    if (Objects.isNull(value)) {
                        out.nullValue();
                    } else {
                        out.jsonValue("\"" + value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\"");
                    }
                }

                @Override
                public LocalDate read(JsonReader in) throws IOException {
                    String val = in.nextString();
                    if (Objects.isNull(val) || val.trim().length() == 0) {
                        return null;
                    }
                    return LocalDate.parse(val, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
            })
            .registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                @Override
                public void write(JsonWriter out, LocalDateTime value) throws IOException {
                    if (Objects.isNull(value)) {
                        out.nullValue();
                    } else {
                        out.jsonValue("\"" + value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\"");
                    }
                }

                @Override
                public LocalDateTime read(JsonReader in) throws IOException {
                    String val = in.nextString();
                    if (Objects.isNull(val) || val.trim().length() == 0) {
                        return null;
                    }
                    return LocalDateTime.parse(val, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }
            })
            .create();


    public static <K, T> Type typeOf(Class<K> parentClass, Class<T> elemClass) {
        return TypeToken.getParameterized(parentClass, elemClass).getType();
    }

    private static <T> Type listType(Class<T> elemClass) {
        return TypeToken.getParameterized(List.class, elemClass).getType();
    }

    private static <K, V> Type listMapType(Class<K> keyType, Class<V> valueType) {
        return TypeToken.getParameterized(List.class, mapType(keyType, valueType)).getType();
    }

    private static <K, V> Type mapType(Class<K> keyClass, Class<V> valClass) {
        return TypeToken.getParameterized(Map.class, keyClass, valClass).getType();
    }

    private static <K, V> Type mapListType(Class<K> keyType, Class<V> elemType) {
        return TypeToken.getParameterized(Map.class, keyType, listType(elemType)).getType();
    }


    public static String bean2json(Object object) {
        return gson.toJson(object);
    }

    public static <T> T json2bean(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> T json2bean(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> List<T> getList(String json, Class<T> clazz) {
        List<T> data = json2bean(json, listType(clazz));
        return Objects.isNull(data) ? Collections.emptyList() : data;
    }

    public static <K, V> Map<K, V> getMap(String json, Class<K> keyType, Class<V> valueType) {
        Map<K, V> data = json2bean(json, mapType(keyType, valueType));
        return Objects.isNull(data) ? Collections.emptyMap() : data;
    }

    public static <K, V> List<Map<K, V>> getListMap(String json, Class<K> keyClass, Class<V> valClass) {
        List<Map<K, V>> data = json2bean(json, listMapType(keyClass, valClass));
        return Objects.isNull(data) ? Collections.emptyList() : data;
    }

    public static <K, V> Map<K, List<V>> getMapList(String json, Class<K> keyClass, Class<V> elemClass) {
        Map<K, List<V>> data = json2bean(json, mapListType(keyClass, elemClass));
        return Objects.isNull(data) ? Collections.emptyMap() : data;
    }
}
