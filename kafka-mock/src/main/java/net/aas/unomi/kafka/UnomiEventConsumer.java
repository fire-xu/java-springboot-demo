package net.aas.unomi.kafka;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnomiEventConsumer {
    private final HashMap<String, Object> config;
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    public UnomiEventConsumer(HashMap<String, Object> config) {
        this.config = config;
    }

    public static void main(String[] args) {
        HashMap<String, Object> config = new HashMap<>();
        for (String arg : args) {
            if (arg.startsWith("--url")) {
                config.put("url", Integer.parseInt(arg.substring("--url".length() + 1)));
            }
        }
        new UnomiEventConsumer(config).start();
    }

    private void start() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "unomi");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(Configuration.UNOMI_TOPIC));

        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
            if (buffer.size() > 0) {
                buffer.forEach(record -> executorService.submit(() -> pushToUnomi(record)));
                consumer.commitSync();
                buffer.clear();
            }
        }
    }

    private void pushToUnomi(ConsumerRecord<String, String> record) {
        System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        HttpPost post = new HttpPost((String) (config.getOrDefault("url", "http://localhost:8181/eventcollector")));
        post.setEntity(new StringEntity((record.value()), ContentType.APPLICATION_JSON));
        try {
            CloseableHttpResponse response = httpClient.execute(post);
            System.out.println(response.getStatusLine().getStatusCode());
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
