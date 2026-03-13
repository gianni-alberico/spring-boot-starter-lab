package io.github.giannialberico;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties("http.logger")
public record HttpLoggerProperties(

        @DefaultValue("false")
        boolean enabled,

        @Name("include-request-body")
        @DefaultValue("false")
        boolean includeRequestBody,

        @Name("include-response-body")
        @DefaultValue("false")
        boolean includeResponseBody
) { }