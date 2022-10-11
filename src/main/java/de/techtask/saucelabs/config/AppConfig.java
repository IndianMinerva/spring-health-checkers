package de.techtask.saucelabs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app")
@Component
public class AppConfig {
    private String url;


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
