package io.github.giannialberico;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

public class HttpLoggingFilter extends OncePerRequestFilter {

    private final List<String> usefulHeaders = List.of("Content-Type", "Accept", "X-Correlation-ID", "User-Agent");
    private final Logger log = LoggerFactory.getLogger(HttpLoggingFilter.class);
    private final HttpLoggerProperties httpLoggerProperties;

    public HttpLoggingFilter(HttpLoggerProperties httpLoggerProperties) {
        this.httpLoggerProperties = httpLoggerProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, 1024);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        // Lighter log without body
        logRequest("INCOMING HTTP REQUEST", request, null, startTime);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long endTime = System.currentTimeMillis();

            String requestBody = getBody(wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding());
            String responseBody = getBody(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding());

            logRequest("HTTP REQUEST", wrappedRequest, requestBody, startTime);
            logResponse(wrappedResponse, responseBody, startTime, endTime);

            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequest(String title, HttpServletRequest request, String requestBody, long startTime) {
        HttpLogFormatter.Builder builder = new HttpLogFormatter.Builder()
                .title(title)
                .date(new Date(startTime))
                .method(request.getMethod())
                .uri(getFormattedURI(request))
                .headers(getRequestHeaders(request));

        if (httpLoggerProperties.includeRequestBody() && requestBody != null) {
            builder.body(requestBody);
        }

        log.info(builder.build().toString());
    }

    private void logResponse(ContentCachingResponseWrapper response, String responseBody, long startTime, long endTime) {
        HttpLogFormatter.Builder builder = new HttpLogFormatter.Builder()
                .title("HTTP RESPONSE")
                .date(new Date(endTime))
                .status(response.getStatus())
                .headers(getResponseHeaders(response))
                .durationInMilli(endTime - startTime)
                .size(formatSize(response.getContentAsByteArray().length));

        if (httpLoggerProperties.includeResponseBody()) {
            builder.body(responseBody);
        }

        log.info(builder.build().toString());
    }

    private String formatSize(int bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        char pre = "KMG".charAt(exp - 1);
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    private String getRequestHeaders(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();

        usefulHeaders.forEach(headerName -> {
            String value = request.getHeader(headerName);
            if (value != null) {
                sb.append(headerName).append(": ").append(value).append("; ");
            }
        });
        return sb.toString();
    }

    private String getResponseHeaders(HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        var headerNames = response.getHeaderNames();

        if (headerNames != null) {
            for (String name : headerNames) {
                String value = response.getHeader(name);
                sb.append(name).append(": ").append(value).append(" | ");
            }
        }
        return sb.toString();
    }

    private String getBody(byte[] buf, String charset) throws UnsupportedEncodingException {
        if (buf.length > 0) {
            return new String(buf, charset);
        }

        return null;
    }

    private String getFormattedURI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();

        if (isStringEmpty(queryString)) {
            return uri;
        }
        return uri + "?" + queryString;
    }

    private boolean isStringEmpty(String str) {
        return str == null || str.isBlank();
    }
}