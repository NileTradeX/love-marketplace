package com.love.api.common.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class HeaderWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> headers = new HashMap<>();

    public HeaderWrapper(HttpServletRequest request, Map<String, String> headers) {
        super(request);
        this.addAllHeaders(headers);
    }

    public void addAllHeaders(Map<String, String> headers) {
        if (Objects.nonNull(headers) && !headers.isEmpty()) {
            this.headers.putAll(headers);
        }
    }

    @Override
    public String getHeader(String name) {
        String value = headers.get(name);
        if (StringUtils.isNotBlank(value)) {
            return value;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        String value = this.headers.get(name);
        if (StringUtils.isNotBlank(value)) {
            return new Vector<>(Collections.singletonList(value)).elements();
        }
        return super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        if (this.headers.isEmpty()) {
            return super.getHeaderNames();
        }

        Vector<String> vector = new Vector<>(this.headers.keySet());
        Enumeration<String> headerNames = super.getHeaderNames();
        if (Objects.nonNull(headerNames)) {
            while (headerNames.hasMoreElements()) {
                vector.add(headerNames.nextElement());
            }
        }
        return vector.elements();
    }
}
