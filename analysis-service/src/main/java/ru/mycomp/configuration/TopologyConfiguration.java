package ru.mycomp.configuration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.mycomp.dto.Order;
import ru.mycomp.dto.ValidatedOrder;
import ru.mycomp.serde.AppSerdes;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TopologyConfiguration {
    private final String VALIDATED_ORDER_TOPIC = "validated_order_topic";
    private final AppSerdes appSerdes;

    @Bean
    @SneakyThrows
    public Topology createTopology(StreamsBuilder streamsBuilder) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Serde<String> serdeKey = Serdes.String();
        Serde<Long> serdeLong = Serdes.Long();
        Serde<ValidatedOrder> serdeValue = appSerdes.validatedOrderSerde();

        KStream<String, ValidatedOrder> input = streamsBuilder.
                stream(VALIDATED_ORDER_TOPIC,
                        Consumed.with(serdeKey,serdeValue));

        var duration = Duration.ofMinutes(1);

        input.filter((id, order) -> order.getStatus().equals("ACCC"))
                .map((k, v) -> new KeyValue<>((String) v.getOrder().getCtime().format(formatter), v.getOrder().getAmount()))
                .groupByKey(Grouped.with(serdeKey, serdeLong)).windowedBy(TimeWindows.ofSizeWithNoGrace(duration))
                .reduce(Long::sum, Materialized.with(serdeKey, serdeLong))
                .toStream().foreach((k,v) -> log.info("Временное окно для общей суммы: " + k.window().endTime() + " Общая сумма заказов: " + v.toString()));


        input.filter((id, order) -> order.getStatus().equals("ACCC")).groupBy((k,v) -> v.getOrder().getCtime().format(formatter)).windowedBy(TimeWindows.ofSizeWithNoGrace(duration))
                .count().toStream().foreach((k,v) -> log.info("Временное окно для количества: " + k.window().endTime() + " Одобренных заказов:" + v.toString()));

        Topology topology = streamsBuilder.build();

        System.out.println("===============================");
        System.out.println(topology.describe());
        System.out.println("===============================");

        return topology;
    }
}
