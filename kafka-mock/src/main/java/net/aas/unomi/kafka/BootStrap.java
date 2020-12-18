package net.aas.unomi.kafka;

public class BootStrap {
    public static void main(String[] args) {
        //启动
        if ("p".equals(args[0])) {
            UnomiEventProducer.main(args);
        } else if ("c".equals(args[0])) {
            UnomiEventConsumer.main(args);
        }
    }
}
