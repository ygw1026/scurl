package com.nhnacademy.domain;

import java.net.URL;
import java.util.Objects;
import java.util.StringJoiner;

import org.apache.commons.cli.Option;

public class HttpRequest {
    private static final String EMPTY_LINE = "";
    private static final HttpMethod DEFAULT_HTTP_METHOD = HttpMethod.GET;
    private static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";

    private String requestTarget;
    private HttpMethod httpMethod;
    private String httpVerion;
    private final Headers headers;
    private String body;

    public HttpRequest(URL url){
        String requestTarget = url.getFile();
        if (requestTarget.isBlank()){
            requestTarget = "/";
        }

        this.requestTarget = requestTarget;
        this.httpMethod = DEFAULT_HTTP_METHOD;
        this.httpVersion = DEFAULT_HTTP_VERSION;
        this.headers = new Headers();
        String host = url.getHost();
        if (url.getPort() != -1) {
            host += (":" + url.getPort());
        }
        this.headers.put(HeaderName.HOST, host);
    }

    public void setValueUsingParams(Option option) {
        String optionValue = option.getValue();

        if(Objects.equals(option.getOpt(), MyOption.HTTP_REQUEST_METHOD.getOptName())) {
            this.httpMethod = httpMethod.find(optionValue);
            return;
        }
        if (Objects.equals(option.getOpt(), MyOption.HEADER.getOptName())) {
            addHeader(new Header(optionValue));
            return;
        }
        if (Objects.equals(option.getOpt(), MyOption.DATA.getOptName())) {
            setBody(optionValue);
            return;
        }
        throw new RuntimeException("선택할 수 없는 option입니다");
    }

    private void setBody(String optionValue) {
        StringJoiner sj = new StringJoiner(",");
        String[] params = optionValue.split("&");
        for (String param : params) {
            String[] keyAndValue = param.split("=");
            sj.add("\"" + keyAndValue[0] + "\"" + ":" + "\"" + keyAndValue[1] + "\"");
        }

        this.body = "{" + sj + "}";
        Header contentTypeHeader = new Header(new HeaderName("Content-Type"), "application/json");
        addHeader(contentTypeHeader);
        Header contentLengthHeader = new Header(new HEaderName("Content-Length"),
            String.valueOf(this.body.getBytes(StandardCharsets.UTF_8).length));
            addHeader(contentLengthHeader);
    }

    private void addHeader(Header header) {
        headers.put(header.getKey(), header.getValue());
    }
}
