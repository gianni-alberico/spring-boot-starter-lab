package io.github.giannialberico;

import java.util.Date;

public class HttpLogFormatter {
    private String title;
    private String id;
    private Date date;
    private String method;
    private String uri;
    private String headers;
    private String body;
    private Integer status;
    private String size;
    private Long durationInMilli;

    public HttpLogFormatter(Builder builder) {
        this.title = builder.title;
        this.id = builder.id;
        this.date = builder.date;
        this.method = builder.method;
        this.uri = builder.uri;
        this.headers = builder.headers;
        this.body = builder.body;
        this.status = builder.status;
        this.size = builder.size;
        this.durationInMilli = builder.durationInMilli;
    }

    public static class Builder {
        private String title;
        private String id;
        private Date date;
        private String method;
        private String uri;
        private String headers;
        private String body;
        private Integer status;
        private String size;
        private Long durationInMilli;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder headers(String headers) {
            this.headers = headers;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder size(String size) {
            this.size = size;
            return this;
        }

        public Builder durationInMilli(Long durationInMilli) {
            this.durationInMilli = durationInMilli;
            return this;
        }

        public HttpLogFormatter build() {
            return new HttpLogFormatter(this);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(Date date) { this.date = date; }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setDurationInMilli(Long durationInMilli) {
        this.durationInMilli = durationInMilli;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (title != null)           sb.append("\n---------- ").append(title)          .append(" ----------\n");
        if (id != null)              sb.append("ID       : ")  .append(id)             .append("\n");
        if (date != null)            sb.append("Date     : ")  .append(date.toString()).append("\n");
        if (method != null)          sb.append("Method   : ")  .append(method)         .append("\n");
        if (uri != null)             sb.append("URI      : ")  .append(uri)            .append("\n");
        if (status != null)          sb.append("Status   : ")  .append(status)         .append("\n");
        if (body != null)            sb.append("Body     : ")  .append(body)           .append("\n");
        if (headers != null)         sb.append("Headers  : ")  .append(headers)        .append("\n");
        if (size != null)            sb.append("Size     : ")  .append(size)           .append("\n");
        if (durationInMilli != null) sb.append("Duration : ")  .append(durationInMilli).append(" ms\n");

        return sb.toString();
    }
}
