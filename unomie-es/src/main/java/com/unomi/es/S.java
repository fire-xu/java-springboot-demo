package com.unomi.es;

import org.checkerframework.checker.units.qual.C;

import java.util.concurrent.ConcurrentHashMap;

public class S {
    public static void main(String[] args) {
        new S().start();
    }

    private void start() {
        T1 t1 = new T1();
        new Thread(() -> {
            t1.run(1);
        }).start();
        new Thread(() -> {
            t1.run(2);
        }).start();
    }
}

class T1 {
    ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

    public void run(int id) {
        System.out.println(id);
        synchronized (this) {
            if (map.containsKey(id)) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                map.put(id, id);
            }
        }
        doWork();
        synchronized (this) {
            map.remove(id);
            this.notifyAll();
        }
    }

    private void doWork() {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}

