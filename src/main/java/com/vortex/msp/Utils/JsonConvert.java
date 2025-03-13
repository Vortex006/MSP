package com.vortex.msp.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JsonConvert {

    /**
     * 注册 JavaTimeModule
     */
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // 序列化单个对象为 JSON 字符串
    public static <T> String serializeObject(T object) {
        try {
            objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON序列化失败", e);
        }
    }

    // 序列化对象列表为 JSON 数组字符串
    public static <T> String serializeList(List<T> list) {
        try {
            objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));

            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize list", e);
        }
    }

    // 反序列化 JSON 字符串为单个对象
    public static <T> T deserializeObject(String jsonString, Class<T> valueType) {
        try {
            objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
            return objectMapper.readValue(jsonString, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize object", e);
        }
    }

    public static <T> T deserializeObject(String jsonString, TypeReference<T> valueType) {
        try {
            objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
            return objectMapper.readValue(jsonString, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize object", e);
        }
    }


    // 反序列化 JSON 数组字符串为对象列表 (ArrayList)
    public static <T> ArrayList<T> deserializeList(String jsonArrayString, Class<T> elementType) {
        try {
            objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
            // 使用 TypeFactory 创建带有泛型信息的类型
            return objectMapper.readValue(
                    jsonArrayString,
                    TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, elementType)
            );
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
