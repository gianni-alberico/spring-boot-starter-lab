package io.github.giannialberico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(HttpLoggerProperties.class)
@ConditionalOnProperty(prefix = "http.logger", name = "enabled", havingValue = "true")
public class HttpLoggerAutoConfiguration {

    Logger log = LoggerFactory.getLogger(HttpLoggerAutoConfiguration.class);

    @Bean
    public HttpLoggingFilter httpLoggingFilter(HttpLoggerProperties httpLoggerProperties) {
        log.info("registering http logger filter bean");
        return new HttpLoggingFilter(httpLoggerProperties);
    }
}
