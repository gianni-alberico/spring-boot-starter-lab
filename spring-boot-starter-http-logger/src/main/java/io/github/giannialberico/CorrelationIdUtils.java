package io.github.giannialberico;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;

import java.util.UUID;

public class CorrelationIdUtils {

    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    public static final String MDC_KEY = "correlationId";

    private CorrelationIdUtils() {}

    public static String getOrGenerate(HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId.isBlank()) {
            return UUID.randomUUID().toString();
        }

        return correlationId;
    }

    public static void addToMdc(String correlationId) {
        MDC.put(MDC_KEY, correlationId);
    }

    public static void clear() {
        MDC.remove(MDC_KEY);
    }
}