package net.aas.unomi.fluent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    public static final String FLUENT_HOST = "fluent.host";
    public static final String FLUENT_TCP_PORT = "fluent.tcpPort";
    public static final String FLUENT_TAG_PREFIX = "fluent.tagPrefix";
    public static final String MOCK_TIMES = "mock.times";
    public static final String MOCK_INTERVAL = "mock.interval";
    public static final String MOCK_THREAD = "mock.thread";

    public static Options options;

    static {
        try {
            InputStream resource = Logger.class.getClassLoader().getResourceAsStream("fluent.properties");
            Properties properties = new Properties();
            properties.load(resource);
            String host = properties.getProperty(FLUENT_HOST, "localhost");
            String port = properties.getProperty(FLUENT_TCP_PORT, "24224");
            String tagPrefix = properties.getProperty(FLUENT_TAG_PREFIX, "test");

            String mockTimes = properties.getProperty(MOCK_TIMES, "1");
            String mockInterval = properties.getProperty(MOCK_INTERVAL, "1000");
            String mockThread = properties.getProperty(MOCK_THREAD, "1");
            System.out.println("default config");
            options = new Options(host, Integer.parseInt(port), tagPrefix,
                    Integer.parseInt(mockTimes), Integer.parseInt(mockInterval), Integer.parseInt(mockThread));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Options {
        String host;
        Integer port;
        String tagPrefix;
        Integer mockTimes;
        Integer mockInterval;
        Integer mockThread;
        String sessionId;

        public Options(String host, Integer port, String tagPrefix, Integer mockTimes, Integer mockInterval, Integer mockThread) {
            this.host = host;
            this.port = port;
            this.tagPrefix = tagPrefix;
            this.mockTimes = mockTimes;
            this.mockInterval = mockInterval;
            this.mockThread = mockThread;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public void setTagPrefix(String tagPrefix) {
            this.tagPrefix = tagPrefix;
        }

        public void setMockTimes(Integer mockTimes) {
            this.mockTimes = mockTimes;
        }

        public void setMockInterval(Integer mockInterval) {
            this.mockInterval = mockInterval;
        }

        public void setMockThread(Integer mockThread) {
            this.mockThread = mockThread;
        }

        public String getHost() {
            return host;
        }

        public Integer getPort() {
            return port;
        }

        public String getTagPrefix() {
            return tagPrefix;
        }

        public Integer getMockTimes() {
            return mockTimes;
        }

        public Integer getMockInterval() {
            return mockInterval;
        }

        public Integer getMockThread() {
            return mockThread;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getSessionId() {
            return sessionId;
        }
    }
}
