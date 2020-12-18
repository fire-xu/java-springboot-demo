package net.aas.unomi.kafka;


import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class UnomiEventProducer {
    private HashMap<String, Object> config = null;
    private AtomicInteger counter = new AtomicInteger(0);
    private AtomicInteger threadNo = new AtomicInteger(0);
    private volatile boolean flag = true;

    public UnomiEventProducer(HashMap<String, Object> config) {
        this.config = config;
    }

    public static void main(String[] args) {
        HashMap<String, Object> config = parseFlag(args);
        new UnomiEventProducer(config).start();
    }

    public void start() {
        String msg = generateEvent((String) (config.getOrDefault("sid", "48d0c20e-c491-36ec-9c97-92a2a4173267")));
        Integer times = (Integer) config.getOrDefault("times", -1);
        Integer interval = (Integer) config.getOrDefault("interval", 500);
        Integer threads = (Integer) config.getOrDefault("thread", 1);
        ExecutorService executorService = Executors.newFixedThreadPool(threads,
                r -> {
                    Thread t = new Thread(r, "unomi-prod-" + threadNo.getAndIncrement());
                    t.setDaemon(true);
                    return t;
                });
        if (times < 0) {
            while (true) {
                executorService.submit(new Task(null, msg, counter));
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            CountDownLatch latch = new CountDownLatch(times);
            for (int i = 0; i < times; i++) {
                executorService.execute(new Task(latch, msg, counter));
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                latch.await();
                executorService.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Task implements Runnable {
        CountDownLatch latch = null;
        String msg = "";
        AtomicInteger counter;

        public Task(CountDownLatch latch, String msg, AtomicInteger counter) {
            this.latch = latch;
            this.msg = msg;
            this.counter = counter;
        }

        @Override
        public void run() {
            MsgSender.send(Configuration.UNOMI_TOPIC, "mock", msg);
            System.out.println(Thread.currentThread().getName() + "--" + counter.getAndIncrement());
            if (latch != null) {
                latch.countDown();
            }
        }
    }

    private static HashMap<String, Object> parseFlag(String[] args) {
        HashMap<String, Object> config = new HashMap<>();
        for (String arg : args) {
            if (arg.startsWith("--times")) {
                config.put("times", Integer.parseInt(arg.substring("--times".length() + 1)));
            } else if (arg.startsWith("--i")) {
                config.put("interval", Integer.parseInt(arg.substring("--i".length() + 1)));
            } else if (arg.startsWith("--thread")) {
                config.put("thread", Integer.parseInt(arg.substring("--thread".length() + 1)));
            } else if (arg.startsWith("--sid")) {
                config.put("sid", arg.substring("--sid".length() + 1));
            }
        }
        return config;
    }

    private static String generateEvent(String sid) {
        String msg = "{\"sessionId\":\"$SID\",\"events\":[{\"scope\":\"realEstateManager\",\"eventType\":\"countNumber\",\"source\":{\"itemId\":\"/unomi-tracker-sample-1.html\",\"itemType\":\"page\",\"scope\":\"realEstateManager\"},\"target\":{\"itemId\":\"9a26c9f6-7f0e-9849-b997-90bdd531b9b1\",\"itemType\":\"page\",\"scope\":\"realEstateManager\"}}]}";
        msg = msg.replace("$SID", sid);
        return msg;
    }
}
