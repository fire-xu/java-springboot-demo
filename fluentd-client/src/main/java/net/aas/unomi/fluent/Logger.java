package net.aas.unomi.fluent;

import org.fluentd.logger.FluentLogger;


public class Logger {

    private static FluentLogger log;


    public static FluentLogger getLog() {
        if (log == null) {
            System.out.println(ConfigLoader.options.getTagPrefix());
            System.out.println(ConfigLoader.options.getHost());
            System.out.println(ConfigLoader.options.getPort());
            log = FluentLogger.getLogger(
                    ConfigLoader.options.getTagPrefix(),
                    ConfigLoader.options.getHost(),
                    ConfigLoader.options.getPort());
        }
        return log;
    }
}
