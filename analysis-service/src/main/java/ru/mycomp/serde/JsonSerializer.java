package ru.mycomp.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class JsonSerializer<T> implements Serializer<T> {

    private ObjectMapper mapper;

    public JsonSerializer() {
        mapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(String topic, T t) {
        try {
            return mapper.writeValueAsString(t).getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
