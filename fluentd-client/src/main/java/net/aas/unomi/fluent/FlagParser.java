package net.aas.unomi.fluent;

public class FlagParser {
    public static void parse(String[] args) {
        System.out.println("user config");
        // --host --port --tag --times --i --thread --sid
        for (String arg : args) {
//            System.out.println(arg);
            if (arg.startsWith("--host")) {
                ConfigLoader.options.setHost(arg.substring("--host".length() + 1));
            } else if (arg.startsWith("--port")) {
                ConfigLoader.options.setPort(Integer.parseInt(arg.substring("--port".length() + 1)));
            } else if (arg.startsWith("--tag")) {
                ConfigLoader.options.setTagPrefix(arg.substring("--tag".length() + 1));
            } else if (arg.startsWith("--times")) {
                ConfigLoader.options.setMockTimes(Integer.parseInt(arg.substring("--times".length() + 1)));
            } else if (arg.startsWith("--i")) {
                ConfigLoader.options.setMockInterval(Integer.parseInt(arg.substring("--i".length() + 1)));
            } else if (arg.startsWith("--thread")) {
                ConfigLoader.options.setMockThread(Integer.parseInt(arg.substring("--thread".length() + 1)));
            } else if (arg.startsWith("--sid")) {
                ConfigLoader.options.setSessionId(arg.substring("--sid".length() + 1));
            }
        }
    }
}
