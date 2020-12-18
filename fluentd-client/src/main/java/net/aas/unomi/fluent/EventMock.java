package net.aas.unomi.fluent;

import org.fluentd.logger.FluentLogger;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class EventMock {
    private FluentLogger log;
    private AtomicInteger counter = new AtomicInteger(0);
//    public BlockingQueue<LogTask> queue = new LinkedBlockingQueue<>();
//
//    static class Consumer {
//        private BlockingQueue<LogTask> queue;
//        private
//    }

    public static void main(String[] args) {
        //TODO 待优化配置加载顺序
        Class<ConfigLoader> configLoaderClass = ConfigLoader.class;
        FlagParser.parse(args);
        new EventMock().start();
    }

    private void start() {
        log = Logger.getLog();
        Integer mockTimes = ConfigLoader.options.getMockTimes();
        Integer mockInterval = ConfigLoader.options.getMockInterval();
        Integer mockThread = ConfigLoader.options.getMockThread();

        if (mockTimes < 0) {
            while (true) {
                mock(mockInterval);
            }
        } else {
            while (mockTimes > 0) {
                mock(mockInterval);
                mockTimes--;
            }
        }
    }

    private void mock(Integer mockInterval) {
        log.log("test", generateEvent());
        System.out.println("send" + counter.incrementAndGet());
        try {
            Thread.sleep(mockInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> generateEvent() {
        Map<String, Object> data = new HashMap<String, Object>();
        String sessionId = ConfigLoader.options.getSessionId() != null ? ConfigLoader.options.getSessionId() : "48d0c20e-c491-36ec-9c97-92a2a4173267";
        data.put("sessionId", sessionId);
        data.put("events", getEvents());
        return data;
    }

    private List<Map<String, Object>> getEvents() {
        List<Map<String, Object>> es = new ArrayList<Map<String, Object>>();
        es.add(getEvent());
        return es;
    }

    private Map<String, Object> getEvent() {
        Map<String, Object> e = new HashMap<>();
        e.put("eventType", "countNumber");
        e.put("scope", "realEstateManager");
        e.put("target", getTarget());
        e.put("source", getSource());
        return e;
    }

    private Map<String, Object> getSource() {
        Map<String, Object> s = new HashMap<>();
        s.put("scope", "realEstateManager");
        s.put("itemId", "/unomi-tracker-sample-1.html");
        s.put("itemType", "page");
        return s;
    }

    private Map<String, Object> getTarget() {
        Map<String, Object> t = new HashMap<>();
        t.put("scope", "realEstateManager");
        t.put("itemId", "9a26c9f6-7f0e-9849-b997-90bdd531b9b1");
        t.put("itemType", "page");
        return t;
    }

//    static class LogTask implements Runnable {
//        FluentLogger log;
//        String tag;
//        Map<String, Object> data;
//
//        @Override
//        public void run() {
//            log.log(tag, data);
//        }
//    }
}
