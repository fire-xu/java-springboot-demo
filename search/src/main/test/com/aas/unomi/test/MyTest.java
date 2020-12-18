package com.aas.unomi.test;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

public class MyTest {
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();
    @Test
    public void twentyMillis() throws Exception {
        Thread.sleep(20);
    }
}