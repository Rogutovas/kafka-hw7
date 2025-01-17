package ru.mycomp.serde;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;
import ru.mycomp.dto.ValidatedOrder;

@Component
public class AppSerdes {
    private <T> Serde<T> serde(Class<T> cls) {
        return new Serdes.WrapperSerde<>(new JsonSerializer<>(), new JsonDeserializer<>(cls));
    }
    public Serde<ValidatedOrder> validatedOrderSerde() {
        return serde(ValidatedOrder.class);
    }
}