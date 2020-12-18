package net.aas.unomi.search.common;

public class UnomiConfiguration {
    private String username;
    private String password;
    private String baseUrl;

    public static UnomiConfiguration defaultConfiguration() {
        return new UnomiConfiguration("karaf", "karaf", "http://localhost:8181/cxs");
    }

    public UnomiConfiguration(String username, String password, String baseUrl) {
        this.username = username;
        this.password = password;
        this.baseUrl = baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
