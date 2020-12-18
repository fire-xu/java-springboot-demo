package net.aas.unomi.search.common.feign;

import net.aas.unomi.search.event.EventCollector;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class FeignFactoryTest {

    @Test
    public void create() throws InterruptedException {
        FeignFactory factory = new FeignFactory();
        EventCollector e1 = factory.create(EventCollector.class, "http://192.168.6.150");

        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            EventCollector e2 = factory.create(EventCollector.class, "http://192.168.6.150");
            System.out.println(e1 == e2);
            latch.countDown();
        }).start();

        latch.await();
    }
}